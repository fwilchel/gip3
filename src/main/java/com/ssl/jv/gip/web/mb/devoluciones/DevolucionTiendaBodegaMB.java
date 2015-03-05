package com.ssl.jv.gip.web.mb.devoluciones;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.ejb.DevolucionesEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
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
  private DevolucionesEJBLocal devolucionesEJB;

  @EJB
  private MaestrosEJBLocal maestrosEJB;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  private final Integer language = AplicacionMB.SPANISH;
  /**
   * listado de ubicaciones
   */
  private List<Ubicacion> listaUbicaciones;
  /**
   * ubicacion seleccionada
   */
  private Ubicacion ubicacionDestinoSeleccionada;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<Documento> listaDocumentos;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
    cargarListaUbicaciones();
  }

  /**
   *
   */
  private void cargarListaUbicaciones() {
    LOGGER.debug("Metodo: <<cargarListaUbicaciones>>");
    setListaUbicaciones(devolucionesEJB.consultarUbicacionesQueSonTiendaPorUsuario(menu.getUsuario().getId()));
    if(getListaUbicaciones() != null){
      setUbicacionDestinoSeleccionada(getListaUbicaciones().get(0));
    }
  }

  /**
   *
   */
  public void buscar() {
    LOGGER.debug("Metodo: <<buscar>>");
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
   * @param menu the menu to set
   */
  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  /**
   * @return the listaUbicaciones
   */
  public List<Ubicacion> getListaUbicaciones() {
    return listaUbicaciones;
  }

  /**
   * @param listaUbicaciones the listaUbicaciones to set
   */
  public void setListaUbicaciones(List<Ubicacion> listaUbicaciones) {
    this.listaUbicaciones = listaUbicaciones;
  }

  /**
   * @return the ubicacionDestinoSeleccionada
   */
  public Ubicacion getUbicacionDestinoSeleccionada() {
    return ubicacionDestinoSeleccionada;
  }

  /**
   * @param ubicacionDestinoSeleccionada the ubicacionDestinoSeleccionada to set
   */
  public void setUbicacionDestinoSeleccionada(Ubicacion ubicacionDestinoSeleccionada) {
    this.ubicacionDestinoSeleccionada = ubicacionDestinoSeleccionada;
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

}
