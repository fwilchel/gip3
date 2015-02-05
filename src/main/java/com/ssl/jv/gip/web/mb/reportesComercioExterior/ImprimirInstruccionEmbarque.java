package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ViewScoped;

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
@ManagedBean(name = "imprimirInstruccionEmbarque")
@ViewScoped
public class ImprimirInstruccionEmbarque extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(ImprimirInstruccionEmbarque.class);

  @EJB
  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEjb;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  private final Integer language = AplicacionMB.SPANISH;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<TerminosTransporte> listaTerminosTransporte;
  /**
   * Registro seleccionado
   */
  private TerminosTransporte seleccionado;
  /**
   *
   */
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJB;
  /**
   *
   */
  private Cliente cliente;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
    consultarListaTerminosTransporte();
  }

  /**
   *
   */
  public void consultarListaTerminosTransporte() {
    LOGGER.debug("Metodo: <<consultarListaTerminosTransporte>>");
    setListaTerminosTransporte(reportesComercioExteriorEjb.obtenerListadoImprimirInstruccionEmbarque());
  }

  /**
   *
   * @param terminoTransporte
   */
  public void seleccionarTerminoTransporte(TerminosTransporte terminoTransporte) {
    LOGGER.debug("Metodo: <<seleccionarTerminoTransporte>>");
    setSeleccionado(terminoTransporte);
    setCliente(comercioExteriorEJB.consultarClientePorId(9L));
  }

  public void imprimirInstruccionEmbarque() {
    LOGGER.debug("Metodo: <<imprimirInstruccionEmbarque>>");
    // TODO: 
  }

  /**
   *
   */
  public void cancelar() {
    LOGGER.debug("Metodo: <<cancelar>>");
    this.setSeleccionado(null);
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
   * @return the listaTerminosTransporte
   */
  public List<TerminosTransporte> getListaTerminosTransporte() {
    return listaTerminosTransporte;
  }

  /**
   * @param listaTerminosTransporte the listaTerminosTransporte to set
   */
  public void setListaTerminosTransporte(List<TerminosTransporte> listaTerminosTransporte) {
    this.listaTerminosTransporte = listaTerminosTransporte;
  }

  /**
   * @return the seleccionado
   */
  public TerminosTransporte getSeleccionado() {
    return seleccionado;
  }

  /**
   * @param seleccionado the seleccionado to set
   */
  public void setSeleccionado(TerminosTransporte seleccionado) {
    this.seleccionado = seleccionado;
  }

  /**
   * @return the cliente
   */
  public Cliente getCliente() {
    return cliente;
  }

  /**
   * @param cliente the cliente to set
   */
  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }
}
