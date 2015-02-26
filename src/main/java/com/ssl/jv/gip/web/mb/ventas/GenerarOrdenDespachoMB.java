package com.ssl.jv.gip.web.mb.ventas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;

/**
 * <p>Title: GIP</p>
 * <p>Description: GIP</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Diego Poveda
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean(name = "generarOrdenDespachoMB")
@ViewScoped
public class GenerarOrdenDespachoMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(GenerarOrdenDespachoMB.class);

  @EJB
  private VentasFacturacionEJBLocal ventasFacturacionEJB;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  private final Integer language = AplicacionMB.SPANISH;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<Documento> listaDocumentos;
  /**
   * Registro seleccionado
   */
  private Documento seleccionado;
  /**
   *
   */
  private List<ProductosXDocumento> listaProductosXDocumento;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
    cargarListaDocumentos();
  }

  /**
   *
   */
  public void cargarListaDocumentos() {
    LOGGER.debug("Metodo: <<cargarListaDocumentos>>");
    setListaDocumentos(ventasFacturacionEJB.consultarDocumentosOrdenDespacho());
  }

  /**
   *
   * @param documento
   */
  public void seleccionarRemision(Documento documento) {
    LOGGER.debug("Metodo: <<seleccionarRemision>>");
    seleccionado = documento;
    seleccionado.setTerminosTransportes(new ArrayList<TerminosTransporte>());
    cargarListaProductosXDocumento();
  }

  private void cargarListaProductosXDocumento() {
    LOGGER.debug("Metodo: <<cargarListaProductosXDocumento>>");
    setListaProductosXDocumento(ventasFacturacionEJB.consultarProductosPorDocumento(seleccionado.getId()));
  }

  /**
   *
   */
  public void generarVistaPrevia() {
    LOGGER.debug("Metodo: <<generarVistaPrevia>>");

  }

  /**
   *
   */
  public void generarExcel() {
    LOGGER.debug("Metodo: <<generarExcel>>");

  }

  /**
   *
   */
  public void generarOrdenDespacho() {
    LOGGER.debug("Metodo: <<generarOrdenDespacho>>");

  }

  /**
   *
   */
  public void cancelar() {
    LOGGER.debug("Metodo: <<cancelar>>");
    this.seleccionado = null;
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
   * @return the listaProductosXDocumento
   */
  public List<ProductosXDocumento> getListaProductosXDocumento() {
    return listaProductosXDocumento;
  }

  /**
   * @param listaProductosXDocumento the listaProductosXDocumento to set
   */
  public void setListaProductosXDocumento(List<ProductosXDocumento> listaProductosXDocumento) {
    this.listaProductosXDocumento = listaProductosXDocumento;
  }

}
