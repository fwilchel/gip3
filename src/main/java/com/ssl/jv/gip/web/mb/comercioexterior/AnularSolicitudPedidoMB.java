package com.ssl.jv.gip.web.mb.comercioexterior;

import com.ssl.jv.gip.jpa.pojo.Documento;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Utilidad;
import java.util.List;
import javax.ejb.EJBTransactionRolledbackException;
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
@ManagedBean(name = "anularSolicitudPedidoMB")
@ViewScoped
public class AnularSolicitudPedidoMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(AnularSolicitudPedidoMB.class);

  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEjb;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  private final Integer language = AplicacionMB.SPANISH;
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

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
  }

  /**
   *
   */
  public void buscar() {
    LOGGER.debug("Metodo: <<buscar>>");
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
      comercioExteriorEjb.anularSolicitudPedido(seleccionado);
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
      // TODO: retirar
      this.addMensajeWarn("msgConfirmacion", mensajeAdvertencia);
      return mensajeAdvertencia;
    }
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
}