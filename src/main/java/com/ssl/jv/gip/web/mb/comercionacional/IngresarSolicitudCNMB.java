package com.ssl.jv.gip.web.mb.comercionacional;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.ComercioNacionalEJBLocal;
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
 * @email diego.poveda@softstudio.co
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class IngresarSolicitudCNMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(IngresarSolicitudCNMB.class);
  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menuMB;
  private final Integer language = AplicacionMB.SPANISH;
  @EJB
  private ComercioNacionalEJBLocal comercioNacionalEJB;
  private Cliente cliente;
  private Documento solicitud;
  private List<ProductosXDocumento> productosXDocumento;
  private List<ProductosXDocumento> productosXDocumentoSeleccionados;
  private Modo modo;

  @PostConstruct
  public void init() {
	LOGGER.trace("Metodo: <<init>>");
	this.modo = Modo.LISTAR;
	Usuario usuario = menuMB.getUsuario();
	// TODO: como obtengo el cliente desde el usuario?
	// TODO: de donde obtengo el punto de venta
	setCliente(new Cliente(1L));
	this.consultarProductosXCliente();
  }

  public void consultarProductosXCliente() {
	LOGGER.trace("Metodo: <<consultarProductosXCliente>>");
	List<ProductosXCliente> pxcs = getComercioNacionalEJB().consultarProductosXCliente(getCliente().getId());
	if (pxcs != null) {
	  for (ProductosXCliente pxc : pxcs) {
		if (getProductosXDocumento() == null) {
		  setProductosXDocumento(new ArrayList<ProductosXDocumento>());
		}
		ProductosXDocumento pxd = new ProductosXDocumento();
		pxd.setProductosInventario(pxc.getProductosInventario());
		getProductosXDocumento().add(pxd);
	  }
	}
  }

  public void agregarProducto(ProductosXDocumento pxd) {
	LOGGER.trace("Metodo: <<agregarProducto>>");
	if (getProductosXDocumentoSeleccionados() == null) {
	  setProductosXDocumentoSeleccionados(new ArrayList<ProductosXDocumento>());
	}
	getProductosXDocumentoSeleccionados().add(pxd);
  }

  public void validarCantidad(ProductosXDocumento pxd) {
	LOGGER.trace("Metodo: <<validarCantidad>>");
	getProductosXDocumentoSeleccionados().remove(pxd);
  }

  public void removerProductoXCliente(ProductosXDocumento pxd) {
	LOGGER.trace("Metodo: <<removerProductoXCliente>>");
	getProductosXDocumentoSeleccionados().remove(pxd);
  }

  public void ingresarSolicitudComercioNacional() {
	LOGGER.trace("Metodo: <<ingresarSolicitudComercioNacional>>");
  }

  public StreamedContent generarReporte() {
	LOGGER.trace("Metodo: <<generarReporte>>");
	return null;
  }

  public void volver() {
	LOGGER.trace("Metodo: <<volver>>");
	this.reset();
  }

  private void reset() {
	LOGGER.trace("Metodo: <<reset>>");
	modo = Modo.LISTAR;
	setSolicitud(null);
	setProductosXDocumento(null);
	setProductosXDocumentoSeleccionados(null);
  }

  public boolean isModoListar() {
	return modo.equals(Modo.LISTAR);
  }

  public boolean isModoGenerar() {
	return modo.equals(Modo.GENERAR);
  }

  public boolean isModoConfirmacion() {
	return modo.equals(Modo.CONFIRMACION);
  }

  /**
   * @param appMB
   *          the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
	this.appMB = appMB;
  }

  /**
   * @param menuMB
   *          the menuMB to set
   */
  public void setMenuMB(MenuMB menuMB) {
	this.menuMB = menuMB;
  }

  /**
   * @return the comercioNacionalEJB
   */
  public ComercioNacionalEJBLocal getComercioNacionalEJB() {
	return comercioNacionalEJB;
  }

  /**
   * @param comercioNacionalEJB
   *          the comercioNacionalEJB to set
   */
  public void setComercioNacionalEJB(ComercioNacionalEJBLocal comercioNacionalEJB) {
	this.comercioNacionalEJB = comercioNacionalEJB;
  }

  /**
   * @return the cliente
   */
  public Cliente getCliente() {
	return cliente;
  }

  /**
   * @param cliente
   *          the cliente to set
   */
  public void setCliente(Cliente cliente) {
	this.cliente = cliente;
  }

  /**
   * @return the solicitud
   */
  public Documento getSolicitud() {
	return solicitud;
  }

  /**
   * @param solicitud
   *          the solicitud to set
   */
  public void setSolicitud(Documento solicitud) {
	this.solicitud = solicitud;
  }

  /**
   * @return the productosXDocumento
   */
  public List<ProductosXDocumento> getProductosXDocumento() {
	return productosXDocumento;
  }

  /**
   * @param productosXDocumento
   *          the productosXDocumento to set
   */
  public void setProductosXDocumento(List<ProductosXDocumento> productosXDocumento) {
	this.productosXDocumento = productosXDocumento;
  }

  /**
   * @return the productosXDocumentoSeleccionados
   */
  public List<ProductosXDocumento> getProductosXDocumentoSeleccionados() {
	return productosXDocumentoSeleccionados;
  }

  /**
   * @param productosXDocumentoSeleccionados
   *          the productosXDocumentoSeleccionados to set
   */
  public void setProductosXDocumentoSeleccionados(List<ProductosXDocumento> productosXDocumentoSeleccionados) {
	this.productosXDocumentoSeleccionados = productosXDocumentoSeleccionados;
  }
}
