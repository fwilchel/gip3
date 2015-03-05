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
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
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
    LOGGER.debug("Metodo: <<init>>");
    cargarTiposFacuras();
  }

  /**
   *
   */
  private void cargarTiposFacuras() {
    LOGGER.debug("Metodo: <<init>>");
    setListaTiposFacturas(new ArrayList<SelectItem>());
    getListaTiposFacturas().add(new SelectItem(ConstantesTipoDocumento.FACTURA, AplicacionMB.getMessage("gfFltLblItmTipoFacturaDirecta", language)));
    getListaTiposFacturas().add(new SelectItem(ConstantesTipoDocumento.FACTURA_ESPECIAL, AplicacionMB.getMessage("gfFltLblItmTipoFacturaEspecial", language)));
    getListaTiposFacturas().add(new SelectItem(ConstantesTipoDocumento.SOLICITUD_PEDIDO, AplicacionMB.getMessage("gfFltLblItmTipoFacturaConsumoServicios", language)));
  }

  /**
   *
   */
  public void buscar() {
    LOGGER.debug("Metodo: <<buscar>>");
    setListaRemisiones(ventasFacturacionEJB.consultarRemisionesPendientesPorRecibir(getConsecutivoDocumento()));
  }

  /**
   *
   * @param documento
   */
  public void seleccionarRemision(Documento documento) {
    LOGGER.debug("Metodo: <<seleccionarRemision>>");
    setRemisionSeleccionada(documento);
    if (getRemisionSeleccionada().getSitioEntrega() != null && getRemisionSeleccionada().getSitioEntrega().equals("CS") && getTipoFacturaSeleccionado() != ConstantesTipoDocumento.SOLICITUD_PEDIDO) {
      modo = Modo.MENSAGE;
      addMensajeError("msgDetalle", AplicacionMB.getMessage("gfErrorValidacion1", language));
    } else if (getTipoFacturaSeleccionado() == ConstantesTipoDocumento.SOLICITUD_PEDIDO && (getRemisionSeleccionada().getSitioEntrega() == null || !remisionSeleccionada.getSitioEntrega().equals("CS"))) {
      modo = Modo.MENSAGE;
      addMensajeError("msgDetalle", AplicacionMB.getMessage("gfErrorValidacion2", language));
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
            BigDecimal valorUnitatrioMl = pxd.getValorUnitatrioMl().setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal cantidad = pxdqty.getCantidad1().setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal descuentoProducto = pxd.getDescuentoxproducto().setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal descuentoCliente = documentoRelacionado.getCliente().getDescuentoCliente().setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal desc = ((valorUnitatrioMl.multiply(cantidad)).multiply((descuentoProducto.divide(new BigDecimal(100))))).setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal valorTotal = ((valorUnitatrioMl.multiply(cantidad)).subtract(desc)).setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal descCliente = (valorTotal.multiply(descuentoCliente.divide(new BigDecimal(100)))).setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal otrosDescuentos = valorTotal.multiply(pxd.getOtrosDescuentos().divide(new BigDecimal(100))).setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal iva = (pxd.getIva()).setScale(1, BigDecimal.ROUND_HALF_UP);
            BigDecimal valorIva = ((valorTotal.subtract(descCliente).subtract(otrosDescuentos)).multiply(pxd.getIva().divide(new BigDecimal(100)))).setScale(1, BigDecimal.ROUND_HALF_UP);

            ProductosXDocumento p = new ProductosXDocumento();
            p.setValorUnitatrioMl(valorUnitatrioMl);
            p.setProductosInventario(pxd.getProductosInventario());
            p.setUnidade(pxd.getUnidade());
            p.setCantidad1(cantidad);
            p.setValorTotal(valorTotal);
            p.setDescuentoxproducto(descuentoProducto);
            p.setIva(iva);
            p.setOtrosDescuentos(otrosDescuentos);
            if (pxd.getCantidad1().compareTo(pxdqty.getCantidad1()) <= 0) {
              p.setObservacion2("blank");
            } else {
              p.setObservacion2("red");
            }
            // agregar a la lista
            listaProductosXRemision.add(p);
            // totales
            subtotal = subtotal.add(valorTotal);
            descuento = descuento.add(otrosDescuentos).add(descCliente);
            if (p.getIva().compareTo(new BigDecimal(10.0)) == 0) {
              totalIva10 = totalIva10.add(valorIva);
            } else if (p.getIva().compareTo(new BigDecimal(5.0)) == 0) {
              totalIva5 = totalIva5.add(valorIva);
            } else if (p.getIva().compareTo(new BigDecimal(16.0)) == 0) {
              totalIva16 = totalIva16.add(valorIva);
            }
          }
        }
      }
      total = subtotal.add(descuento).add(totalIva10).add(totalIva5).add(totalIva16);
    }
  }

  /**
   *
   */
  public void generarFactura() {
    LOGGER.debug("Metodo: <<generarFactura>>");

  }

  /**
   *
   */
  public void imprimirFactura() {
    LOGGER.debug("Metodo: <<imprimirFactura>>");

  }

  /**
   *
   */
  public void cancelar() {
    LOGGER.debug("Metodo: <<cancelar>>");
    this.setRemisionSeleccionada(null);
    this.setConsecutivoDocumento("");
  }

  /**
   *
   * @return
   */
  public Date getFechaActual() {
    LOGGER.debug("Metodo: <<getFechaActual>>");
    return new Date();
  }

  /**
   *
   * @return
   */
  public boolean isFacturaDirecta() {
    LOGGER.debug("Metodo: <<isFacturaDirecta>>");
    return getTipoFacturaSeleccionado() == null ? false : getTipoFacturaSeleccionado().equals(ConstantesTipoDocumento.FACTURA);
  }

  /**
   *
   * @return
   */
  public boolean isFacturaEspecial() {
    LOGGER.debug("Metodo: <<isFacturaEspecial>>");
    return getTipoFacturaSeleccionado() == null ? false : getTipoFacturaSeleccionado().equals(ConstantesTipoDocumento.FACTURA_ESPECIAL);
  }

  /**
   *
   * @return
   */
  public boolean isFacturaConsumoServicios() {
    LOGGER.debug("Metodo: <<isFacturaConsumoServicios>>");
    return getTipoFacturaSeleccionado() == null ? false : getTipoFacturaSeleccionado().equals(ConstantesTipoDocumento.SOLICITUD_PEDIDO);
  }

  /**
   *
   * @return
   */
  public boolean isModoDetalle() {
    LOGGER.debug("Metodo: <<isModoDetalle>>");
    return modo.equals(Modo.DETALLE);
  }

  /**
   *
   * @return
   */
  public boolean isModoMensage() {
    LOGGER.debug("Metodo: <<isModoMensage>>");
    return modo.equals(Modo.MENSAGE);
  }

  /**
   * @param appMB the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
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
