package com.ssl.jv.gip.web.mb.ventas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesUbicacion;
import com.ssl.jv.gip.web.util.Utilidad;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * <p>
 * Title: GIP
 * </p>
 * <p>
 * Description: GIP
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: Soft Studio Ltda.
 * </p>
 *
 * @author Diego Poveda
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean(name = "generarFacturaMB")
@ViewScoped
public class GenerarFacturaMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(GenerarFacturaMB.class);

  private enum Modo {

    DETALLE, MENSAGE;
  }

  @EJB
  private VentasFacturacionEJBLocal ventasFacturacionEJB;

  @EJB
  private MaestrosEJBLocal maestrosEJB;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  private final Integer language = AplicacionMB.SPANISH;
  /**
   * Consecutivo por el cual se va a realizar la busqueda
   */
  private String consecutivoDocumento;
  /**
   * Tipo de factura a generar
   */
  private List<SelectItem> listaTiposFacturas;
  /**
   * tipo de factura remisionSeleccionada
   */
  private Integer tipoFacturaSeleccionado;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<Documento> listaRemisiones;
  /**
   * Registro remisionSeleccionada
   */
  private Documento remisionSeleccionada;
  /**
   *
   */
  private List<ProductosXDocumento> listaProductosXRemision;

  private BigDecimal subtotal = new BigDecimal(0);
  private BigDecimal descuento = new BigDecimal(0);
  private BigDecimal totalIva10 = new BigDecimal(0);
  private BigDecimal totalIva16 = new BigDecimal(0);
  private BigDecimal totalIva5 = new BigDecimal(0);
  private BigDecimal total = new BigDecimal(0);

  private Modo modo;

  @PostConstruct
  public void init() {
    LOGGER.trace("Metodo: <<init>>");
    cargarTiposFacuras();
  }

  /**
   *
   */
  private void cargarTiposFacuras() {
    LOGGER.trace("Metodo: <<cargarTiposFacuras>>");
    setListaTiposFacturas(new ArrayList<SelectItem>());
    getListaTiposFacturas().add(new SelectItem(ConstantesTipoDocumento.FACTURA, AplicacionMB.getMessage("gfFltLblItmTipoFacturaDirecta", language)));
    getListaTiposFacturas().add(new SelectItem(ConstantesTipoDocumento.FACTURA_ESPECIAL, AplicacionMB.getMessage("gfFltLblItmTipoFacturaEspecial", language)));
    getListaTiposFacturas().add(new SelectItem(ConstantesTipoDocumento.SOLICITUD_PEDIDO, AplicacionMB.getMessage("gfFltLblItmTipoFacturaConsumoServicios", language)));
  }

  /**
   *
   */
  public void consultarListaRemisiones() {
    LOGGER.trace("Metodo: <<consultarListaRemisiones>>");
    setListaRemisiones(ventasFacturacionEJB.consultarRemisionesPendientesPorRecibir(getConsecutivoDocumento()));
  }

  /**
   *
   * @param documento
   */
  public void seleccionarRemision(Documento documento) {
    LOGGER.trace("Metodo: <<seleccionarRemision>>");
    setRemisionSeleccionada(documento);
    if (getRemisionSeleccionada().getSitioEntrega() != null && getRemisionSeleccionada().getSitioEntrega().equals("CS") && getTipoFacturaSeleccionado() != ConstantesTipoDocumento.SOLICITUD_PEDIDO) {
      addMensajeError(AplicacionMB.getMessage("gfErrorValidacion1", language));
      LOGGER.debug(AplicacionMB.getMessage("gfErrorValidacion1", language));
    } else if (getTipoFacturaSeleccionado() == ConstantesTipoDocumento.SOLICITUD_PEDIDO && (getRemisionSeleccionada().getSitioEntrega() == null || !remisionSeleccionada.getSitioEntrega().equals("CS"))) {
      addMensajeError(AplicacionMB.getMessage("gfErrorValidacion2", language));
      LOGGER.debug(AplicacionMB.getMessage("gfErrorValidacion2", language));
    } else {
      modo = Modo.DETALLE;
      // lista de productos para el documento de VD relacionado a la remision
      Documento documentoRelacionado = maestrosEJB.consultarDocumentoPorConsecutivo(getRemisionSeleccionada().getObservacionDocumento());
      List<ProductosXDocumento> listaSugrenciasConsultadas = ventasFacturacionEJB.consultarProductosPorDocumento(documentoRelacionado.getId());
      // lista de productos para la remision seleccionada
      List<ProductosXDocumento> listaCantidadesRemision = ventasFacturacionEJB.consultarProductosPorDocumento(remisionSeleccionada.getId());
      listaProductosXRemision = new ArrayList<>();
      // se cruzan las dos listas para crear una nueva
      for (ProductosXDocumento pxd : listaSugrenciasConsultadas) {
        for (ProductosXDocumento pxdqty : listaCantidadesRemision) {
          if (Objects.equals(pxd.getId().getIdProducto(), pxdqty.getId().getIdProducto())) {
            BigDecimal valorUnitatrioMl = pxd.getValorUnitatrioMl();
            BigDecimal cantidad = pxdqty.getCantidad1();
            BigDecimal descuentoProducto = pxd.getDescuentoxproducto();
            BigDecimal descuentoCliente = documentoRelacionado.getCliente().getDescuentoCliente();
            BigDecimal desc = ((valorUnitatrioMl.multiply(cantidad)).multiply((descuentoProducto.divide(new BigDecimal(100)))));
            BigDecimal valorTotal = ((valorUnitatrioMl.multiply(cantidad)).subtract(desc));
            BigDecimal descCliente = (valorTotal.multiply(descuentoCliente.divide(new BigDecimal(100))));
            BigDecimal otrosDescuentos = valorTotal.multiply(pxd.getOtrosDescuentos().divide(new BigDecimal(100)));
            BigDecimal iva = (pxd.getIva());
            BigDecimal valorIva = ((valorTotal.subtract(descCliente).subtract(otrosDescuentos)).multiply(pxd.getIva().divide(new BigDecimal(100))));

            ProductosXDocumento p = new ProductosXDocumento();
            p.setValorUnitatrioMl(Utilidad.round(valorUnitatrioMl));
            p.setProductosInventario(pxd.getProductosInventario());
            p.setUnidade(pxd.getUnidade());
            p.setCantidad1(Utilidad.round(cantidad));
            p.setValorTotal(Utilidad.round(valorTotal));
            p.setDescuentoxproducto(Utilidad.round(descuentoProducto));
            p.setIva(Utilidad.round(iva));
            p.setOtrosDescuentos(Utilidad.round(otrosDescuentos));
            if (pxd.getCantidad1().compareTo(pxdqty.getCantidad1()) <= 0) {
              p.setObservacion2("blank");
            } else {
              p.setObservacion2("red");
            }
            // agregar a la lista
            listaProductosXRemision.add(p);
            // totales
            subtotal = Utilidad.round(subtotal.add(valorTotal));
            descuento = Utilidad.round(descuento.add(otrosDescuentos).add(descCliente));
            if (p.getIva().compareTo(new BigDecimal(10.0)) == 0) {
              totalIva10 = Utilidad.round(totalIva10.add(valorIva));
            } else if (p.getIva().compareTo(new BigDecimal(5.0)) == 0) {
              totalIva5 = Utilidad.round(totalIva5.add(valorIva));
            } else if (p.getIva().compareTo(new BigDecimal(16.0)) == 0) {
              totalIva16 = Utilidad.round(totalIva16.add(valorIva));
            }
          }
        }
      }
      total = subtotal.add(descuento).add(totalIva10).add(totalIva5).add(totalIva16);
      LOGGER.debug("Total factura: " + total);
//      RequestContext.getCurrentInstance().openDialog("dlgDetalle");
//      RequestContext.getCurrentInstance().update(":dlgDetalle");
    }
  }

  /**
   *
   */
  public void generarFactura() {
    LOGGER.trace("Metodo: <<generarFactura>>");
    try {
      Documento factura = new Documento();
      // tipo de documento y estado
      Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
      EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
      estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.GENERADO);
      if (tipoFacturaSeleccionado == ConstantesTipoDocumento.FACTURA) {
        estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA);
      } else {
        estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA_ESPECIAL);
      }
      estadosxdocumento.setId(estadosxdocumentoPK);
      // llenar el objeto documento
      factura.setEstadosxdocumento(estadosxdocumento);
      factura.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
      factura.setObservacionDocumento(this.remisionSeleccionada.getConsecutivoDocumento());
      factura.setUbicacionOrigen(new Ubicacion(ConstantesUbicacion.UBICACION_DESTINO_DEFAULT));
      factura.setUbicacionDestino(remisionSeleccionada.getUbicacionDestino());
      factura.setObservacionDocumento(remisionSeleccionada.getConsecutivoDocumento());
      Documento ordenDespacho = consultarOrdenDespacho(remisionSeleccionada.getObservacionDocumento());
      if (ordenDespacho == null) {
        factura.setDocumentoCliente(this.remisionSeleccionada.getDocumentoCliente());
        factura.setObservacion3(null);
      } else {
        factura.setDocumentoCliente(ordenDespacho.getDocumentoCliente());
        factura.setObservacion3(ordenDespacho.getConsecutivoDocumento());
      }
      factura.setCliente(this.remisionSeleccionada.getCliente());
      factura.setPuntoVenta(this.remisionSeleccionada.getPuntoVenta());
      factura.setSubtotal(subtotal);
      factura.setDescuento(descuento);
      factura.setValorIva16(totalIva16);
      factura.setValorIva10(totalIva10);
      factura.setValorIva5(totalIva5);
      factura.setValorTotal(total);
      factura.setNumeroFactura(null);
      factura.setObservacion2(this.remisionSeleccionada.getObservacion2());
      if (getRemisionSeleccionada().getSitioEntrega() != null && getRemisionSeleccionada().getSitioEntrega().equals("CS")) {
        factura.setSitioEntrega(remisionSeleccionada.getSitioEntrega());
      } else {
        factura.setSitioEntrega(remisionSeleccionada.getDocumentoCliente());
      }
      factura.setFechaEntrega(null);//TODO: de donde os saco o no van
      factura.setFechaEsperadaEntrega(null);//TODO: de donde os saco o no van
      factura.setDescuentoCliente(null);//TODO: de donde os saco o no van
      // auditoria
      LogAuditoria auditoria = new LogAuditoria();
      auditoria.setIdUsuario(menu.getUsuario().getId());
      auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
      // enviar a generar la factura
      factura = ventasFacturacionEJB.generarFactura(factura, listaProductosXRemision, remisionSeleccionada, auditoria);
      LOGGER.debug("Factura generada exitosamente con id: " + factura.getId() + " y consecutivo: " + factura.getConsecutivoDocumento());
      modo = Modo.MENSAGE;
      addMensajeInfo(formatearCadenaConParametros("gfMsgExitoGenerarFactura", language, factura.getId().toString(), factura.getConsecutivoDocumento()));
      // refrescar lista de remisiones
      consultarListaRemisiones();
    } catch (Exception ex) {
      modo = Modo.DETALLE;
      addMensajeError("Error");
      LOGGER.error("Error");
    }
  }

  /**
   *
   * @param observacionDocumento
   * @return
   */
  private Documento consultarOrdenDespacho(String observacionDocumento) {
    LOGGER.trace("Metodo: <<consultarOrdenDespacho>>");
    List<Documento> listaOrdenesDespacho;
    listaOrdenesDespacho = ventasFacturacionEJB.consultarOrdenesDespachoPorObservacion(observacionDocumento);
    if (listaOrdenesDespacho != null) {
      for (Documento ordenDespacho : listaOrdenesDespacho) {
        return ordenDespacho;
      }
    }
    return null;
  }

  /**
   *
   */
  public void imprimirFactura() {
    LOGGER.trace("Metodo: <<imprimirFactura>>");

  }

  /**
   *
   */
  public void cancelar() {
    LOGGER.trace("Metodo: <<cancelar>>");
    this.setRemisionSeleccionada(null);
    this.setConsecutivoDocumento("");
  }

  /**
   *
   * @return
   */
  public Date getFechaActual() {
    return new Date();
  }

  /**
   *
   * @return
   */
  public boolean isFacturaDirecta() {
    return getTipoFacturaSeleccionado() == null ? false : getTipoFacturaSeleccionado().equals(ConstantesTipoDocumento.FACTURA);
  }

  /**
   *
   * @return
   */
  public boolean isFacturaEspecial() {
    return getTipoFacturaSeleccionado() == null ? false : getTipoFacturaSeleccionado().equals(ConstantesTipoDocumento.FACTURA_ESPECIAL);
  }

  /**
   *
   * @return
   */
  public boolean isFacturaConsumoServicios() {
    return getTipoFacturaSeleccionado() == null ? false : getTipoFacturaSeleccionado().equals(ConstantesTipoDocumento.SOLICITUD_PEDIDO);
  }

  /**
   *
   * @return
   */
  public boolean isModoDetalle() {
    return modo.equals(Modo.DETALLE);
  }

  /**
   *
   * @return
   */
  public boolean isModoMensage() {
    return modo.equals(Modo.MENSAGE);
  }

  /**
   * @param appMB the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  /**
   * @param menu the menu to set
   */
  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  /**
   * @return the consecutivoDocumento
   */
  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  /**
   * @param consecutivoDocumento the consecutivoDocumento to set
   */
  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  /**
   * @return the listaTiposFacturas
   */
  public List<SelectItem> getListaTiposFacturas() {
    return listaTiposFacturas;
  }

  /**
   * @param listaTiposFacturas the listaTiposFacturas to set
   */
  public void setListaTiposFacturas(List<SelectItem> listaTiposFacturas) {
    this.listaTiposFacturas = listaTiposFacturas;
  }

  /**
   * @return the tipoFacturaSeleccionado
   */
  public Integer getTipoFacturaSeleccionado() {
    return tipoFacturaSeleccionado;
  }

  /**
   * @param tipoFacturaSeleccionado the tipoFacturaSeleccionado to set
   */
  public void setTipoFacturaSeleccionado(Integer tipoFacturaSeleccionado) {
    this.tipoFacturaSeleccionado = tipoFacturaSeleccionado;
  }

  /**
   * @return the listaRemisiones
   */
  public List<Documento> getListaRemisiones() {
    return listaRemisiones;
  }

  /**
   * @param listaRemisiones the listaRemisiones to set
   */
  public void setListaRemisiones(List<Documento> listaRemisiones) {
    this.listaRemisiones = listaRemisiones;
  }

  /**
   * @return the remisionSeleccionada
   */
  public Documento getRemisionSeleccionada() {
    return remisionSeleccionada;
  }

  /**
   * @param remisionSeleccionada the remisionSeleccionada to set
   */
  public void setRemisionSeleccionada(Documento remisionSeleccionada) {
    this.remisionSeleccionada = remisionSeleccionada;
  }

  /**
   * @return the listaProductosXRemision
   */
  public List<ProductosXDocumento> getListaProductosXRemision() {
    return listaProductosXRemision;
  }

  /**
   * @param listaProductosXRemision the listaProductosXRemision to set
   */
  public void setListaProductosXRemision(List<ProductosXDocumento> listaProductosXRemision) {
    this.listaProductosXRemision = listaProductosXRemision;
  }

  /**
   * @return the subtotal
   */
  public BigDecimal getSubtotal() {
    return subtotal;
  }

  /**
   * @param subtotal the subtotal to set
   */
  public void setSubtotal(BigDecimal subtotal) {
    this.subtotal = subtotal;
  }

  /**
   * @return the descuento
   */
  public BigDecimal getDescuento() {
    return descuento;
  }

  /**
   * @param descuento the descuento to set
   */
  public void setDescuento(BigDecimal descuento) {
    this.descuento = descuento;
  }

  /**
   * @return the totalIva10
   */
  public BigDecimal getTotalIva10() {
    return totalIva10;
  }

  /**
   * @param totalIva10 the totalIva10 to set
   */
  public void setTotalIva10(BigDecimal totalIva10) {
    this.totalIva10 = totalIva10;
  }

  /**
   * @return the totalIva16
   */
  public BigDecimal getTotalIva16() {
    return totalIva16;
  }

  /**
   * @param totalIva16 the totalIva16 to set
   */
  public void setTotalIva16(BigDecimal totalIva16) {
    this.totalIva16 = totalIva16;
  }

  /**
   * @return the totalIva5
   */
  public BigDecimal getTotalIva5() {
    return totalIva5;
  }

  /**
   * @param totalIva5 the totalIva5 to set
   */
  public void setTotalIva5(BigDecimal totalIva5) {
    this.totalIva5 = totalIva5;
  }

  /**
   * @return the total
   */
  public BigDecimal getTotal() {
    return total;
  }

  /**
   * @param total the total to set
   */
  public void setTotal(BigDecimal total) {
    this.total = total;
  }

}
