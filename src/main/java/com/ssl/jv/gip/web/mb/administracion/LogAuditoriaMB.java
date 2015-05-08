package com.ssl.jv.gip.web.mb.administracion;

import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJBLocal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import java.util.Date;

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
 * @email diego.poveda@softstudio.com
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean(name = "logAuditoriaMB")
@ViewScoped
public class LogAuditoriaMB extends UtilMB {

  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(LogAuditoriaMB.class);
  @EJB
  private AdministracionEJBLocal admonEjb;
  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private Integer language = AplicacionMB.SPANISH;
  private String nombreUsuarioFlt;
  private String nombreFuncionalidadFlt;
  private Date fechaFlt;
  private List<LogAuditoria> listaAuditoria;

  @PostConstruct
  public void init() {
    LOGGER.trace("Metodo: <<init>>");
  }

  public void onConsultEvent() {
    LOGGER.trace("Metodo: <<onConsultEvent>>");
    Map<String, Object> parametros = new HashMap<>();
    if (nombreUsuarioFlt != null && !nombreUsuarioFlt.isEmpty()){
      parametros.put("nombreUsuario", "%" + nombreUsuarioFlt + "%");
    } else {
      parametros.put("nombreUsuario", "%");
    }
    if (nombreFuncionalidadFlt != null && !nombreFuncionalidadFlt.isEmpty()){
      parametros.put("nombreFuncionalidad", "%" + nombreFuncionalidadFlt + "%");
    } else {
      parametros.put("nombreFuncionalidad", "%");
    }
//    if (fechaFlt != null){
//      parametros.put("fecha", nombreUsuarioFlt);
//    } else {
//      parametros.put("fecha", "%");
//    }
    listaAuditoria = admonEjb.consultarLogAuditoria(parametros);
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
   * @return the menu
   */
  public MenuMB getMenu() {
    return menu;
  }

  /**
   * @return the language
   */
  public Integer getLanguage() {
    return language;
  }

  /**
   * @param language the language to set
   */
  public void setLanguage(Integer language) {
    this.language = language;
  }

  /**
   * @return the nombreUsuarioFlt
   */
  public String getNombreUsuarioFlt() {
    return nombreUsuarioFlt;
  }

  /**
   * @param nombreUsuarioFlt the nombreUsuarioFlt to set
   */
  public void setNombreUsuarioFlt(String nombreUsuarioFlt) {
    this.nombreUsuarioFlt = nombreUsuarioFlt;
  }

  /**
   * @return the nombreFuncionalidadFlt
   */
  public String getNombreFuncionalidadFlt() {
    return nombreFuncionalidadFlt;
  }

  /**
   * @param nombreFuncionalidadFlt the nombreFuncionalidadFlt to set
   */
  public void setNombreFuncionalidadFlt(String nombreFuncionalidadFlt) {
    this.nombreFuncionalidadFlt = nombreFuncionalidadFlt;
  }

  /**
   * @return the fechaFlt
   */
  public Date getFechaFlt() {
    return fechaFlt;
  }

  /**
   * @param fechaFlt the fechaFlt to set
   */
  public void setFechaFlt(Date fechaFlt) {
    this.fechaFlt = fechaFlt;
  }

  /**
   * @return the listaAuditoria
   */
  public List<LogAuditoria> getListaAuditoria() {
    return listaAuditoria;
  }

  /**
   * @param listaAuditoria the listaAuditoria to set
   */
  public void setListaAuditoria(List<LogAuditoria> listaAuditoria) {
    this.listaAuditoria = listaAuditoria;
  }

}
