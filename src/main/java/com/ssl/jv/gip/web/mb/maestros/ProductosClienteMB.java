package com.ssl.jv.gip.web.mb.maestros;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
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
@ManagedBean(name = "productosClienteMB")
@ViewScoped
public class ProductosClienteMB extends UtilMB {

  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(ProductosClienteMB.class);
  @EJB
  private MaestrosEJBLocal maestrosEJB;
  @EJB
  private ComunEJBLocal comunEJB;
  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private final Integer language = AplicacionMB.SPANISH;
  private String skuFlt;
  private String nombreClienteFlt;
  private String nombreSitioEntregaFlt;
  private String estadoFlt;
  private List<ProductosXCliente> listaProductosXClientes;
  private ProductosXCliente productoXClienteSeleccionado;
  private List<ProductosXCliente> listaProductosXClientesSeleccionados;
  private UploadedFile uploadedFile;
  private Modo modo;

  @PostConstruct
  public void init() {
	LOGGER.trace("Metodo: <<init>>");
	modo = Modo.LISTADO;
	setEstadoFlt("A");
  }

  /**
   *
   */
  public void onConsultEvent() {
	LOGGER.trace("Metodo: <<onConsultEvent>>");
	Map<String, Object> parametros = new HashMap<>();
	parametros.put("sku", getSkuFlt());
	parametros.put("nombreCliente", getNombreClienteFlt());
	parametros.put("nombrePuntoVenta", getNombreSitioEntregaFlt());
	switch (getEstadoFlt()) {
	case "A":
	  parametros.put("activo", true);
	  break;
	case "T":
	  parametros.put("activo", false);
	  break;
	}
	setListaProductosXClientes(maestrosEJB.consultarProductosXCliente(parametros));
  }

  public void onCreateEvent() {
	modo = Modo.CREACION;
	this.setProductoXClienteSeleccionado(new ProductosXCliente());
	this.getProductoXClienteSeleccionado().setCliente(new Cliente());
	this.getProductoXClienteSeleccionado().setProductosInventario(new ProductosInventario());
	this.getProductoXClienteSeleccionado().setPuntoVenta(new PuntoVenta());
	this.getProductoXClienteSeleccionado().setMoneda(new Moneda());
	this.initEdit();
  }

  public void onEditEvent() {
	modo = Modo.EDICION;
	this.initEdit();
	this.getListaProductosXClientesSeleccionados().add(getProductoXClienteSeleccionado());
  }

  private void initEdit() {
	this.setListaProductosXClientesSeleccionados(new ArrayList<ProductosXCliente>());
  }

  public void chooseProducts() {
	Map<String, Object> options = new HashMap<>();
	options.put("modal", true);
	options.put("draggable", false);
	options.put("resizable", false);
	options.put("contentWidth", 800);
	RequestContext.getCurrentInstance().openDialog("seleccionarProductos", options, null);
  }

  public void onProductsChosen(SelectEvent event) {
	List<ProductosInventario> listaProductos = (List<ProductosInventario>) event.getObject();
	for (ProductosInventario producto : listaProductos) {
	  ProductosXCliente tmp = new ProductosXCliente();
	  tmp.setProductosInventario(producto);
	  this.getListaProductosXClientesSeleccionados().add(tmp);
	}
  }

  public void guardar() {
	if (this.isModoCreacion()) {

	} else {

	}
	this.onConsultEvent();
  }

  private void reset() {
	modo = Modo.LISTADO;
	setProductoXClienteSeleccionado(null);
	getListaProductosXClientesSeleccionados().clear();
  }

  public void onBackToListEvent() {
	this.reset();
  }

  /**
   *
   * @return
   */
  public StreamedContent onGenerateEcxelEvent() {
	LOGGER.debug("Metodo: <<onGenerateEcxelEvent>>");
	StreamedContent reporte = null;
	Map<String, Object> parametrosReporte = new HashMap<>();
	parametrosReporte.put("datos", getListaProductosXClientes());
	try {
	  Hashtable<String, String> parametrosConfiguracionReporte;
	  parametrosConfiguracionReporte = new Hashtable<>();
	  parametrosConfiguracionReporte.put("tipo", "jxls");
	  String reportePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/ReporteMaestroPXC.xls");
	  ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfiguracionReporte, reportePath, null, null, null, parametrosReporte, null);
	  reporte = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel", "ReporteMaestroPXC.xls");
	} catch (Exception e) {
	  this.addMensajeError("Problemas al generar el reporte");
	}
	return reporte;
  }

  /**
   *
   * @param fileUploadEvent
   */
  public void handleFileUpload(FileUploadEvent fileUploadEvent) {
	LOGGER.debug("Metodo: <<handleFileUpload>>");
	try {
	  setUploadedFile(fileUploadEvent.getFile());
	  maestrosEJB.crearProductosXClientesDesdeArchivo(getUploadedFile().getContents());
	} catch (IOException e) {
	  this.addMensajeError("Error al leer el archivo");
	} catch (EJBTransactionRolledbackException e) {
	  LOGGER.error(e);
	  Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
	  if (unrollException != null) {
		this.addMensajeError(unrollException.getLocalizedMessage());
	  } else {
		this.addMensajeError("Ocurrio un problema al intentar persistir los datos");
	  }
	} catch (RuntimeException re) {
	  this.addMensajeError(re.getMessage());
	}
  }

  /**
   *
   * @return
   */
  public boolean isModoListado() {
	return modo.equals(Modo.LISTADO);
  }

  /**
   *
   * @return
   */
  public boolean isModoCreacion() {
	return modo.equals(Modo.CREACION);
  }

  /**
   *
   * @return
   */
  public boolean isModoEdicion() {
	return modo.equals(Modo.EDICION);
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
   * @return the skuFlt
   */
  public String getSkuFlt() {
	return skuFlt;
  }

  /**
   * @param skuFlt
   *          the skuFlt to set
   */
  public void setSkuFlt(String skuFlt) {
	this.skuFlt = skuFlt;
  }

  /**
   * @return the nombreClienteFlt
   */
  public String getNombreClienteFlt() {
	return nombreClienteFlt;
  }

  /**
   * @param nombreClienteFlt
   *          the nombreClienteFlt to set
   */
  public void setNombreClienteFlt(String nombreClienteFlt) {
	this.nombreClienteFlt = nombreClienteFlt;
  }

  /**
   * @return the nombreSitioEntregaFlt
   */
  public String getNombreSitioEntregaFlt() {
	return nombreSitioEntregaFlt;
  }

  /**
   * @param nombreSitioEntregaFlt
   *          the nombreSitioEntregaFlt to set
   */
  public void setNombreSitioEntregaFlt(String nombreSitioEntregaFlt) {
	this.nombreSitioEntregaFlt = nombreSitioEntregaFlt;
  }

  /**
   * @return the estadoFlt
   */
  public String getEstadoFlt() {
	return estadoFlt;
  }

  /**
   * @param estadoFlt
   *          the estadoFlt to set
   */
  public void setEstadoFlt(String estadoFlt) {
	this.estadoFlt = estadoFlt;
  }

  /**
   * @return the listaProductosXClientes
   */
  public List<ProductosXCliente> getListaProductosXClientes() {
	return listaProductosXClientes;
  }

  /**
   * @param listaProductosXClientes
   *          the listaProductosXClientes to set
   */
  public void setListaProductosXClientes(List<ProductosXCliente> listaProductosXClientes) {
	this.listaProductosXClientes = listaProductosXClientes;
  }

  /**
   * @return the productoXClienteSeleccionado
   */
  public ProductosXCliente getProductoXClienteSeleccionado() {
	return productoXClienteSeleccionado;
  }

  /**
   * @param productoXClienteSeleccionado
   *          the productoXClienteSeleccionado to set
   */
  public void setProductoXClienteSeleccionado(ProductosXCliente productoXClienteSeleccionado) {
	this.productoXClienteSeleccionado = productoXClienteSeleccionado;
  }

  /**
   * @return the listaProductosXClientesSeleccionados
   */
  public List<ProductosXCliente> getListaProductosXClientesSeleccionados() {
	return listaProductosXClientesSeleccionados;
  }

  /**
   * @param listaProductosXClientesSeleccionados
   *          the listaProductosXClientesSeleccionados to set
   */
  public void setListaProductosXClientesSeleccionados(List<ProductosXCliente> listaProductosXClientesSeleccionados) {
	this.listaProductosXClientesSeleccionados = listaProductosXClientesSeleccionados;
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
