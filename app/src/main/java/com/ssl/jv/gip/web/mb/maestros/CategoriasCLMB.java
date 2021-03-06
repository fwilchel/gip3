package com.ssl.jv.gip.web.mb.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CategoriaCostoLogistico;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.util.ECampoAcumula;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

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
 * @author Fredy Giovanny Wilches López
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name = "categoriasCLMB")
@SessionScoped
public class CategoriasCLMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(CategoriasCLMB.class);

  private List<CategoriaCostoLogistico> categorias;
  private CategoriaCostoLogistico seleccionado;
  private List<SelectItem> campoAcumula;

  private Modo modo;

  @EJB
  private MaestrosEJBLocal maestrosEjb;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  private Integer language = AplicacionMB.SPANISH;

  public CategoriasCLMB() {

  }

  @PostConstruct
  public void init() {
    categorias = this.maestrosEjb.consultarCategoriasCostosLogisticos();
    campoAcumula = new ArrayList<SelectItem>();
    for (ECampoAcumula eca : ECampoAcumula.values()) {
      this.campoAcumula.add(new SelectItem(eca.name(), eca.getDescripcion()));
    }
  }

  public AplicacionMB getAppMB() {
    return appMB;
  }

  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  public Modo getModo() {
    return modo;
  }

  public void setModo(Modo modo) {
    this.modo = modo;
  }

  public MaestrosEJBLocal getMaestrosEjb() {
    return maestrosEjb;
  }

  public void setMaestrosEjb(MaestrosEJBLocal maestrosEjb) {
    this.maestrosEjb = maestrosEjb;
  }

  public CategoriaCostoLogistico getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(CategoriaCostoLogistico seleccionado) {
    this.seleccionado = seleccionado;
    this.modo = Modo.EDITAR;
  }

  public void nuevo() {
    seleccionado = new CategoriaCostoLogistico();
    this.modo = Modo.CREAR;
  }

  public void guardar() {
    try {
      if (this.seleccionado.getOrden() == 0) {
        this.seleccionado.setOrden(null);
      }
      if (this.modo.equals(Modo.CREAR)) {
        this.seleccionado = this.maestrosEjb.crearCategoriaCostoLogistico(this.seleccionado);
        this.categorias.add(this.seleccionado);
        this.modo = Modo.EDITAR;
      } else {
        this.seleccionado = this.maestrosEjb.actualizarCategoriaCostoLogistico(this.seleccionado);
      }
      this.addMensajeInfo(AplicacionMB.getMessage("categoriasCLGuardado", language));
    } catch (EJBTransactionRolledbackException e) {
      if (this.isException(e, "categorias_costos_logisticos_pkey")) {
        this.addMensajeError("formaDlg:codigo", AplicacionMB.getMessage("maestroCategoriaCLMsgValidatioId", language));
      }
      if (this.isException(e, "categorias_costos_logisticos_orden_unique")) {
        this.addMensajeError("formaDlg:orden", AplicacionMB.getMessage("maestroCategoriaCLMsgValidationOrden", language));
      }
      LOGGER.error(e);
    }
  }

  public boolean isCreacion() {
    if (this.modo != null && this.modo.equals(Modo.CREAR)) {
      return true;
    } else {
      return false;
    }
  }

  public List<CategoriaCostoLogistico> getCategorias() {
    return categorias;
  }

  public void setCategorias(List<CategoriaCostoLogistico> categorias) {
    this.categorias = categorias;
  }

  public List<SelectItem> getCampoAcumula() {
    return campoAcumula;
  }

  public void setCampoAcumula(List<SelectItem> campoAcumula) {
    this.campoAcumula = campoAcumula;
  }

}
