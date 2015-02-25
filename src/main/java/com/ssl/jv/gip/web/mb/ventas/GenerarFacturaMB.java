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
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Utilidad;

/**
 * <p>
 * Title: GIP
 * </p>
 *
 * <p>
 * Description: GIP
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 *
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
   * tipo de factura seleccionado
   */
  private Integer tipoFacturaSeleccionado;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<Documento> listaDocumentos;
  /**
   * Registro seleccionado
   */
  private Documento seleccionado;

  private BigDecimal subtotal = new BigDecimal(123456789);
  private BigDecimal descuento = new BigDecimal(189);
  private BigDecimal totalIva10 = new BigDecimal(123456789);
  private BigDecimal totalIva16 = new BigDecimal(6789);
  private BigDecimal totalIva5 = new BigDecimal(123456789);
  private BigDecimal total = new BigDecimal(16789);

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
    listaTiposFacturas = new ArrayList<>();
    listaTiposFacturas.add(new SelectItem(ConstantesTipoDocumento.FACTURA, AplicacionMB.getMessage("gfFltLblItmTipoFacturaDirecta", language)));
    listaTiposFacturas.add(new SelectItem(ConstantesTipoDocumento.FACTURA_ESPECIAL, AplicacionMB.getMessage("gfFltLblItmTipoFacturaEspecial", language)));
    listaTiposFacturas.add(new SelectItem(ConstantesTipoDocumento.SOLICITUD_PEDIDO, AplicacionMB.getMessage("gfFltLblItmTipoFacturaConsumoServicios", language)));
  }

  /**
   *
   */
  public void buscar() {
    LOGGER.debug("Metodo: <<buscar>>");
    setListaDocumentos(ventasFacturacionEJB.consultarRemisionesPendientesPorRecibir(getConsecutivoDocumento()));
  }

  /**
   *
   * @param documento
   */
  public void seleccionarRemision(Documento documento) {
    LOGGER.debug("Metodo: <<seleccionarRemision>>");
    seleccionado = documento;
    seleccionado.setTerminosTransportes(new ArrayList<TerminosTransporte>());
    seleccionado.setFechaGeneracion(getFechaActual());
    
    if (seleccionado.getSitioEntrega() != null && seleccionado.getSitioEntrega().equals("CS") && tipoFacturaSeleccionado != ConstantesTipoDocumento.SOLICITUD_PEDIDO) {
      modo = Modo.MENSAGE;
      addMensajeError("msgDetalle", AplicacionMB.getMessage("gfErrorValidacion1", language));
    } else if (tipoFacturaSeleccionado == ConstantesTipoDocumento.SOLICITUD_PEDIDO && (seleccionado.getSitioEntrega() == null || !seleccionado.getSitioEntrega().equals("CS"))) {
      modo = Modo.MENSAGE;
      addMensajeError("msgDetalle", AplicacionMB.getMessage("gfErrorValidacion2", language));
    } else {
      modo = Modo.DETALLE;
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
    this.seleccionado = null;
    this.consecutivoDocumento = "";
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
    return tipoFacturaSeleccionado == null ? false : tipoFacturaSeleccionado.equals(ConstantesTipoDocumento.FACTURA);
  }

  /**
   *
   * @return
   */
  public boolean isFacturaEspecial() {
    LOGGER.debug("Metodo: <<isFacturaEspecial>>");
    return tipoFacturaSeleccionado == null ? false : tipoFacturaSeleccionado.equals(ConstantesTipoDocumento.FACTURA_ESPECIAL);
  }

  /**
   *
   * @return
   */
  public boolean isFacturaConsumoServicios() {
    LOGGER.debug("Metodo: <<isFacturaConsumoServicios>>");
    return tipoFacturaSeleccionado == null ? false : tipoFacturaSeleccionado.equals(ConstantesTipoDocumento.SOLICITUD_PEDIDO);
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
   * @return the listaDocumentos
   */
  public List<Documento> getListaDocumentos() {
    return listaDocumentos;
  }

  /**
   * @param listaDocumentos the listaDocumentos to set
   */
  public void setListaDocumentos(List<Documento> listaDocumentos) {
    this.listaDocumentos = listaDocumentos;
  }

  /**
   * @return the seleccionado
   */
  public Documento getSeleccionado() {
    return seleccionado;
  }

  /**
   * @param seleccionado the seleccionado to set
   */
  public void setSeleccionado(Documento seleccionado) {
    this.seleccionado = seleccionado;
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
