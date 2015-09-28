package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Utilidad;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

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
@Named
@ViewScoped
public class AnularSPMB extends UtilMB {

  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(AnularSPMB.class);
  private final Integer language = AplicacionMB.SPANISH;
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEjb;
  @EJB
  private ComunEJBLocal comunEJB;
  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private FiltroDocumentoDTO filtro;
  private List<Documento> listaSP;
  private Documento sp;

  @PostConstruct
  public void init() {
	LOGGER.debug("Metodo: <<init>>");
	this.filtro = new FiltroDocumentoDTO();
  }

  public void consultarListaSP() {
	filtro.setTipoDocumento(new Long(ConstantesTipoDocumento.SOLICITUD_PEDIDO));
	filtro.setEstado((long) ConstantesDocumento.ACTIVO, (long) ConstantesDocumento.VERIFICADO);
	filtro.setOrdenCampo("consecutivoDocumento DESC");
	this.listaSP = comunEJB.consultarDocumentos(filtro);
	//setListaSP(comercioExteriorEjb.consultarSolicitudesPedidoPorAnular(getConsecutivoDocumento()));
  }

  /**
   *
   * @param documento
   */
  public void seleccionarSolicitudPedido(Documento documento) {
	LOGGER.debug("Metodo: <<seleccionarSolicitudPedido>>");
	sp = documento;
  }

  /**
   *
   */
  public void anularSolicitudPedido() {
	LOGGER.debug("Metodo: <<anularSolicitudPedido>>");
	try {
	  LogAuditoria auditoria = new LogAuditoria();
	  auditoria.setIdUsuario(menu.getUsuario().getId());
	  auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
	  comercioExteriorEjb.anularDocumentoComercioExterior(sp, auditoria);
	  listaSP.remove(sp);
	  this.addMensajeInfo("listMsgs", AplicacionMB.getMessage("spaMsgExito", language));
	} catch (EJBTransactionRolledbackException e) {
	  // TODO: validar que excepciones se denen tener en cuenta.
	  this.addMensajeError(AplicacionMB.getMessage("spaMsgError", language));
	  LOGGER.error(e);
	} catch (Exception e) {
	  this.addMensajeError(AplicacionMB.getMessage("spaMsgError", language));
	  LOGGER.error(e);
	}
  }

  /**
   *
   */
  public void cancelar() {
	LOGGER.debug("Metodo: <<cancelar>>");
	this.sp = null;
  }

  /**
   *
   * @return
   */
  public String getMensajeAdvertencia() {
	LOGGER.debug("Metodo: <<getMensajeAdvertencia>>");
	if (sp == null) {
	  return null;
	} else {
	  String mensajeAdvertencia = AplicacionMB.getMessage("spaMsgAdvertencia", language);
	  String parametros[] = new String[1];
	  parametros[0] = sp.getConsecutivoDocumento();
	  mensajeAdvertencia = Utilidad.stringFormat(mensajeAdvertencia, parametros);
	  return mensajeAdvertencia;
	}
  }

  public void setAppMB(AplicacionMB appMB) {
	this.appMB = appMB;
  }

  public void setMenu(MenuMB menu) {
	this.menu = menu;
  }

  public FiltroDocumentoDTO getFiltro() {
	return filtro;
  }

  public void setFiltro(FiltroDocumentoDTO filtro) {
	this.filtro = filtro;
  }

  public List<Documento> getListaSP() {
	return listaSP;
  }

  public void setListaSP(List<Documento> listaSP) {
	this.listaSP = listaSP;
  }

  public Documento getSp() {
	return sp;
  }

  public void setSp(Documento sp) {
	this.sp = sp;
  }
}
