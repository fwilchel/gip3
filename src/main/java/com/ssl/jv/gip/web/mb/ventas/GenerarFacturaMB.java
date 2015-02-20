package com.ssl.jv.gip.web.mb.ventas;

import com.ssl.jv.gip.jpa.pojo.Documento;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Utilidad;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 * <p>
 * Title: GIP</p>
 *
 * <p>
 * Description: GIP</p>
 *
 * <p>
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Soft Studio Ltda.</p>
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

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
    cargarTiposFacuras();
  }

  /**
   * 
   */
  private void cargarTiposFacuras(){
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
  public String getMensajeAdvertencia() {
    LOGGER.debug("Metodo: <<getMensajeAdvertencia>>");
    if (seleccionado == null) {
      return null;
    } else {
      String mensajeAdvertencia = AplicacionMB.getMessage("spaMsgAdvertencia", language);
      String parametros[] = new String[1];
      parametros[0] = seleccionado.getConsecutivoDocumento();
      mensajeAdvertencia = Utilidad.stringFormat(mensajeAdvertencia, parametros);
      return mensajeAdvertencia;
    }
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
    return true;
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
}
