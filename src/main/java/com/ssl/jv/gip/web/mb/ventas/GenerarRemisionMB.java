package com.ssl.jv.gip.web.mb.ventas;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;

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
@ManagedBean(name = "generarRemisionMB")
@ViewScoped
public class GenerarRemisionMB extends UtilMB {

  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(GenerarRemisionMB.class);
  @EJB
  private VentasFacturacionEJBLocal ventasFacturacionEJB;
  @EJB
  private ComunEJBLocal comunEJB;
  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private final Integer language = AplicacionMB.SPANISH;
  private Date fechaEntrega;
  private Long idCliente;
  private List<Documento> listaDocumentosVD;
  private Documento documentoVDSeleccionado;
  private List<ProductosXDocumento> listaProductosXDocumento;
  private boolean mostrarDialogo;
  private Modo modo;
  private UploadedFile uploadedFile;

  @PostConstruct
  public void init() {
	LOGGER.trace("Metodo: <<init>>");
	modo = Modo.LISTAR;
	fechaEntrega = new Date();
  }

  public void onConsultEvent() {
	LOGGER.trace("Metodo: <<consultarListaDocumentos>>");
	FiltroDocumentoDTO filtro = new FiltroDocumentoDTO();
	filtro.setCliente(getIdCliente());
	filtro.setTipoDocumento((long) ConstantesTipoDocumento.VENTA_DIRECTA);
	filtro.setEstado((long) ConstantesDocumento.ACTIVO);
	setListaDocumentosVD(comunEJB.consultarDocumentos(filtro));
  }

  public void onEditEvent() {
	LOGGER.trace("Metodo: <<seleccionarDocumento>>");
	modo = Modo.GENERAR;
  }

  public void handleFileUpload(FileUploadEvent fileUploadEvent) {
	LOGGER.trace("Metodo: <<handleFileUpload>>");
	try {
	  setUploadedFile(fileUploadEvent.getFile());
	  listaProductosXDocumento = ventasFacturacionEJB.consultarProductosXDocumentoValidadosContraArchivo(getDocumentoVDSeleccionado(), getUploadedFile().getContents());
	} catch (Exception e) {
	  if (e.getCause().toString().contains("Error en el archivo")){
		addMensajeError("Error en el archivo");
	  }
	  if (e.getCause().toString().contains("Error de estructura en la línea")){
		addMensajeError("Error de estructura del archivo");
	  }
	  if (e.getCause().toString().contains("Error de datos en la línea")){
		addMensajeError("Error de datos");
	  }
	  if (e.getCause().toString().contains("Error de SKUs")){
		addMensajeError("Error - En el archivo existen SKUs inexistentes en la VD.");
	  }
	  if (e.getCause().toString().contains("Error de Cantidades")){
		addMensajeError("Error - En el archivo existen cantidades mayores a las existentes en la VD.");
	  }
	}
  }

  public void generarRemision() {
	LOGGER.trace("Metodo: <<generarRemision>>");
	// auditoria
	LogAuditoria auditoria = new LogAuditoria();
	auditoria.setIdUsuario(menu.getUsuario().getId());
	auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
	try {
	  Documento remision = ventasFacturacionEJB.generarRemision(documentoVDSeleccionado, listaProductosXDocumento, auditoria);
	  LOGGER.debug("Documento generado exitosamente con id: " + remision.getId() + " y consecutivo: " + remision.getConsecutivoDocumento());
	  addMensajeInfo(formatearCadenaConParametros("VentasRMExito_Crear", language, remision.getId().toString(), remision.getConsecutivoDocumento()));
	  onConsultEvent();
	  init();
	  reset();
	} catch (Exception ex) {
	  addMensajeError("No fue posible generar la Remisión");
	  LOGGER.error("No fue posible generar la Remisión");
	}
  }

  public void onBackToListEvent() {
	LOGGER.trace("Metodo: <<onBackToListEvent>>");
	this.reset();
  }

  private void reset() {
	LOGGER.trace("Metodo: <<reset>>");
	modo = Modo.LISTAR;
	documentoVDSeleccionado = null;
	listaProductosXDocumento = null;
	uploadedFile = null;
  }

  /**
   *
   * @return
   */
  public boolean isModoListar() {
	return modo.equals(Modo.LISTAR);
  }

  /**
   *
   * @return
   */
  public boolean isModoGenerar() {
	return modo.equals(Modo.GENERAR);
  }

  /**
   * @param appMB
   *          the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
	this.appMB = appMB;
  }

  /**
   * @param menu
   *          the menu to set
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
   * @return the fechaEntrega
   */
  public Date getFechaEntrega() {
	return fechaEntrega;
  }

  /**
   * @param fechaEntrega
   *          the fechaEntrega to set
   */
  public void setFechaEntrega(Date fechaEntrega) {
	this.fechaEntrega = fechaEntrega;
  }

  /**
   * @return the idCliente
   */
  public Long getIdCliente() {
	return idCliente;
  }

  /**
   * @param idCliente
   *          the idCliente to set
   */
  public void setIdCliente(Long idCliente) {
	this.idCliente = idCliente;
  }

  /**
   * @return the listaDocumentosVD
   */
  public List<Documento> getListaDocumentosVD() {
	return listaDocumentosVD;
  }

  /**
   * @param listaDocumentosVD
   *          the listaDocumentosVD to set
   */
  public void setListaDocumentosVD(List<Documento> listaDocumentosVD) {
	this.listaDocumentosVD = listaDocumentosVD;
  }

  /**
   * @return the documentoVDSeleccionado
   */
  public Documento getDocumentoVDSeleccionado() {
	return documentoVDSeleccionado;
  }

  /**
   * @param documentoVDSeleccionado
   *          the documentoVDSeleccionado to set
   */
  public void setDocumentoVDSeleccionado(Documento documentoVDSeleccionado) {
	this.documentoVDSeleccionado = documentoVDSeleccionado;
  }

  /**
   * @return the listaProductosXDocumento
   */
  public List<ProductosXDocumento> getListaProductosXDocumento() {
	return listaProductosXDocumento;
  }

  /**
   * @param listaProductosXDocumento
   *          the listaProductosXDocumento to set
   */
  public void setListaProductosXDocumento(List<ProductosXDocumento> listaProductosXDocumento) {
	this.listaProductosXDocumento = listaProductosXDocumento;
  }

  /**
   * @return the mostrarDialogo
   */
  public boolean isMostrarDialogo() {
	return mostrarDialogo;
  }

  /**
   * @param mostrarDialogo
   *          the mostrarDialogo to set
   */
  public void setMostrarDialogo(boolean mostrarDialogo) {
	this.mostrarDialogo = mostrarDialogo;
  }

  /**
   * @return the uploadedFile
   */
  public UploadedFile getUploadedFile() {
	return uploadedFile;
  }

  /**
   * @param uploadedFile
   *          the uploadedFile to set
   */
  public void setUploadedFile(UploadedFile uploadedFile) {
	this.uploadedFile = uploadedFile;
  }
}
