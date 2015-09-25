package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Utilidad;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

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
@Named
@ViewScoped
public class AnularSPMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(AnularSPMB.class);
  private final Integer language = AplicacionMB.SPANISH;
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEjb;
  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  /**
   * Consecutivo por el cual se va a realizar la busqueda
   */
  private String consecutivoDocumento;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<Documento> listaDocumentos;
  /**
   * Registro seleccionado
   */
  private Documento seleccionado;

  public AnularSPMB() {
  }

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
  }

  /**
   *
   */
  public void buscar() {
    LOGGER.debug("Metodo: <<buscar>>");
    // traer documentos q no esten en estado ANULADO, CERRADO.
    // y q no se haya generado la FP
    setListaDocumentos(comercioExteriorEjb.consultarSolicitudesPedidoPorAnular(getConsecutivoDocumento()));
  }

  /**
   *
   * @param documento
   */
  public void seleccionarSolicitudPedido(Documento documento) {
    LOGGER.debug("Metodo: <<seleccionarSolicitudPedido>>");
    seleccionado = documento;
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
      comercioExteriorEjb.anularDocumentoComercioExterior(seleccionado, auditoria);
      listaDocumentos.remove(seleccionado);
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

  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public List<Documento> getListaDocumentos() {
    return listaDocumentos;
  }

  public void setListaDocumentos(List<Documento> listaDocumentos) {
    this.listaDocumentos = listaDocumentos;
  }

  public Documento getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(Documento seleccionado) {
    this.seleccionado = seleccionado;
  }
}
