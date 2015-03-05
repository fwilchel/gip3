package com.ssl.jv.gip.web.mb.devoluciones;

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
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import org.primefaces.model.StreamedContent;

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
@ManagedBean(name = "devolucionTiendaBodegaMB")
@ViewScoped
public class DevolucionTiendaBodegaMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(DevolucionTiendaBodegaMB.class);

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
   */
  public void ingresarDevouciones() {
    LOGGER.debug("Metodo: <<ingresarDevouciones>>");

  }

  /**
   *
   * @return
   */
  public StreamedContent generarVistaPrevia() {
    LOGGER.debug("Metodo: <<generarVistaPrevia>>");
    return null;
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

}
