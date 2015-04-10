package com.ssl.jv.gip.web.mb.comercioexterior;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TipoMovimiento;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.ProductoSolicitudPedidoDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Utilidad;

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
@ManagedBean(name = "ingresarSolicitudPedidoMB")
@SessionScoped
public class IngresarSolicitudPedidoMB extends UtilMB {

  private static final Logger LOGGER = Logger.getLogger(IngresarSolicitudPedidoMB.class);
  private Long idCliente;
  private Long idTerminoIncoterm;
  private List<SelectItem> clientes;
  private List<SelectItem> terminosIncoterm;
  private Boolean solicitudCafe;
  private String nombreArchivo;
  private List<ProductoSolicitudPedidoDTO> productos;
  private boolean deshabilitado = true;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  @EJB
  private MaestrosEJBLocal maestrosEjb;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  @EJB
  private ComercioExteriorEJBLocal comercioEjb;

  private Integer language = AplicacionMB.SPANISH;

  public IngresarSolicitudPedidoMB() {

  }

  public AplicacionMB getAppMB() {
	return appMB;
  }

  public void setAppMB(AplicacionMB appMB) {
	this.appMB = appMB;
  }

  public Long getIdCliente() {
	return idCliente;
  }

  public void setIdCliente(Long idCliente) {
	this.idCliente = idCliente;
  }

  public MenuMB getMenu() {
	return menu;
  }

  public void setMenu(MenuMB menu) {
	this.menu = menu;
  }

  public Long getIdTerminoIncoterm() {
	return idTerminoIncoterm;
  }

  public void setIdTerminoIncoterm(Long idTerminoIncoterm) {
	this.idTerminoIncoterm = idTerminoIncoterm;
  }

  public List<SelectItem> getClientes() {
	return clientes;
  }

  public void setClientes(List<SelectItem> clientes) {
	this.clientes = clientes;
  }

  public List<SelectItem> getTerminosIncoterm() {
	return terminosIncoterm;
  }

  public void setTerminosIncoterm(List<SelectItem> terminosIncoterm) {
	this.terminosIncoterm = terminosIncoterm;
  }

  public Boolean getSolicitudCafe() {
	return solicitudCafe;
  }

  public void setSolicitudCafe(Boolean solicitudCafe) {
	this.solicitudCafe = solicitudCafe;
  }

  public String getNombreArchivo() {
	return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
	this.nombreArchivo = nombreArchivo;
  }

  public List<ProductoSolicitudPedidoDTO> getProductos() {
	return productos;
  }

  public void setProductos(List<ProductoSolicitudPedidoDTO> productos) {
	this.productos = productos;
  }

  public boolean isDeshabilitado() {
	return deshabilitado;
  }

  public void setDeshabilitado(boolean deshabilitado) {
	this.deshabilitado = deshabilitado;
  }

  @PostConstruct
  public void init() {

	List<Cliente> listaClientes = this.maestrosEjb.consultarClientes();
	Collections.sort(listaClientes);
	this.clientes = new ArrayList<>();
	for (Cliente c : listaClientes) {
	  this.clientes.add(new SelectItem(c.getId(), c.getNombre().toUpperCase()));
	}

	this.terminosIncoterm = new ArrayList<>();

  }

  public void cargarTerminos() {
	this.terminosIncoterm = new ArrayList<>();
	List<TerminoIncoterm> tis = this.comercioEjb.consultarListaIncontermPorCliente(this.idCliente);
	for (TerminoIncoterm ti : tis) {
	  this.terminosIncoterm.add(new SelectItem(ti.getId(), ti.getDescripcion()));
	}
	if (this.terminosIncoterm.size() > 0) {
	  this.idTerminoIncoterm = tis.get(0).getId();
	} else {
	  this.idTerminoIncoterm = 0L;
	}
  }

  public void handleFileUpload(FileUploadEvent event) {
	if (this.idCliente == null || this.idCliente == 0) {
	  this.addMensajeError("Debe seleccionar Cliente y Término Incoterm");
	  return;
	}
	this.nombreArchivo = event.getFile().getFileName();
	Hashtable<Long, BigDecimal> saldos = this.comercioEjb.consultarUltimosSaldos();
	Hashtable<String, BigDecimal> datos = new Hashtable<>();
	this.productos = new ArrayList<>();
	try {
	  boolean errorValidacion = false;
	  BufferedReader di = new BufferedReader(new InputStreamReader(event.getFile().getInputstream()));
	  String linea;
	  for (int numeroRegistros = 0;; numeroRegistros++) {
		linea = di.readLine();
		if (linea == null) {
		  if (numeroRegistros == 0) {
			errorValidacion = true;
		  }
		  break;
		}
		StringTokenizer st = new StringTokenizer(linea, "|");
		String sku = st.nextToken().trim();
		BigDecimal cantidad = new BigDecimal(st.nextToken().trim());
		datos.put(sku, cantidad);
		ProductoSolicitudPedidoDTO dto = new ProductoSolicitudPedidoDTO();
		dto.setObservaciones("OK");
		dto.setSku(sku);
		ProductosInventario pi = null;
		try {
		  pi = this.maestrosEjb.consultarPorSku(sku);
		} catch (EJBTransactionRolledbackException ex) {
		  errorValidacion = true;
		  if (this.isException(ex, "No entity found for query")) {
			LOGGER.error("Producto identificado con sku: " + sku + " , no existe en producto o productos por cliente", ex);
		  }
		}
		if (pi == null) {
		  dto.setSeleccionado(false);
		  dto.setDesactivado(true);
		  dto.setEstilo("rojoNegrita");
		  dto.setNombre("PRODUCTO NO EXISTE");
		  dto.setControlStock(false);
		  dto.setObservaciones("N/A");
		  this.addMensajeError("SKUs inexistentes en el maestro productoxcliente");
		} else {
		  dto.setSeleccionado(true);
		  dto.setDesactivado(false);
		  if (!pi.getDesactivado()) {
			dto.setEstilo("naranjaNegrita");
			dto.setSeleccionado(false);
			dto.setDesactivado(true);
		  } else {
			dto.setEstilo("verdeNegrita");
		  }
		  // dto.setCategoria
		  dto.setCantidad(cantidad);
		  dto.setNombre(pi.getNombre());
		  dto.setId(pi.getId());
		  dto.setControlStock(pi.getProductosInventarioComext().getControlStock());
		  dto.setIdUnidad(pi.getUnidadVenta().getId());
		  // dto.setUnidad(unidad);
		  // dto.setPais();
		  dto.setProductoInventarioComext(pi.getProductosInventarioComext());
		  ProductosXClienteComext pcce = this.comercioEjb.consultarPorClienteSku(this.idCliente, sku);
		  if (pcce == null) {
			dto.setObservaciones("N/A");
		  }
		}
		if (pi != null && pi.getProductosInventarioComext() != null && pi.getProductosInventarioComext().getTipoLoteoic() != null) {
		  if (this.solicitudCafe && pi.getProductosInventarioComext().getTipoLoteoic().getId().equals(5L)) {
			this.addMensajeError("Tipo Lote de los productos no corresponde con el Tipo de Solicitud");
			errorValidacion = true;
		  } else if (!this.solicitudCafe && !pi.getProductosInventarioComext().getTipoLoteoic().getId().equals(5L)) {
			this.addMensajeError("Tipo Lote de los productos no corresponde con el Tipo de Solicitud");
			errorValidacion = true;
		  }
		}
		this.productos.add(dto);
		// Consultar saldos
		if (dto.getControlStock() != null && dto.getControlStock()) {
		  BigDecimal saldo = saldos.get(dto.getId());
		  dto.setSaldoAnterior(saldo);
		  if (saldo == null) {
			dto.setEstilo("rojoNegrita");
			dto.setSeleccionado(false);
			dto.setDesactivado(true);
			errorValidacion = true;
		  } else {
			dto.setSaldo(saldo.subtract(dto.getCantidad()));
			if (dto.getSaldo().doubleValue() < 0) {
			  dto.setEstilo("rojoNegrita");
			  errorValidacion = true;
			}
		  }
		}
	  }
	  if (errorValidacion) {
		this.deshabilitado = true;
	  } else {
		this.deshabilitado = false;
	  }
	} catch (IOException e) {
	  this.addMensajeError(e);
	}
  }

  public String generarSolicitudPedido() {

	Documento documento = new Documento();
	Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
	EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
	estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.ACTIVO);
	estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.SOLICITUD_PEDIDO);
	estadosxdocumento.setId(estadosxdocumentoPK);
	documento.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
	documento.setEstadosxdocumento(estadosxdocumento);
	documento.setObservacionDocumento(null);
	documento.setValorTotal(new BigDecimal(0));
	documento.setCliente(new Cliente());
	documento.getCliente().setId(this.idCliente);
	documento.setDocumentoCliente("Cargue Manual");
	documento.setNumeroFactura("0");

	LogAuditoria auditoria = new LogAuditoria();
	auditoria.setIdUsuario(menu.getUsuario().getId());
	auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
	auditoria.setTabla(Documento.class.getName());
	auditoria.setAccion("CRE");
	auditoria.setCampo(null);
	auditoria.setValorAnterior(null);
	auditoria.setFecha(new Timestamp(System.currentTimeMillis()));

	DocumentoXNegociacion dxn = new DocumentoXNegociacion();
	dxn.setPk(new DocumentoXNegociacionPK());
	dxn.getPk().setIdTerminoIncoterm(this.idTerminoIncoterm);

	dxn.setCostoEntrega(new BigDecimal(0));
	dxn.setCostoSeguro(new BigDecimal(0));
	dxn.setCostoFlete(new BigDecimal(0));
	dxn.setOtrosGastos(new BigDecimal(0));
	dxn.setObservacionesMarcacion2(null);
	dxn.setTotalPesoNeto(new BigDecimal(0));
	dxn.setTotalPesoBruto(new BigDecimal(0));
	dxn.setTotalTendidos(new BigDecimal(0));
	dxn.setTotalPallets(new BigDecimal(0));
	dxn.setCantidadDiasVigencia(0);
	dxn.setSolicitudCafe(this.solicitudCafe);
	dxn.setCantidadContenedoresDe20(new BigDecimal(0));
	dxn.setCantidadContenedoresDe40(new BigDecimal(0));
	dxn.setLugarIncoterm(null);
	dxn.setCantidadEstibas(0);
	dxn.setPesoBrutoEstibas(0);

	List<ProductosXDocumento> productos = new ArrayList<>();
	List<MovimientosInventarioComext> mice = new ArrayList<>();
	for (ProductoSolicitudPedidoDTO pxc : this.productos) {
	  if (pxc.isSeleccionado()) {
		ProductosXDocumento productoDocumento = new ProductosXDocumento();
		productoDocumento.setInformacion(false);
		productoDocumento.setCalidad(false);
		productoDocumento.setFechaEstimadaEntrega(documento.getFechaGeneracion());
		productoDocumento.setFechaEntrega(documento.getFechaGeneracion());
		productoDocumento.setId(new ProductosXDocumentoPK());
		productoDocumento.getId().setIdProducto(pxc.getId());
		productoDocumento.setCantidad1(pxc.getCantidad());

		Unidad u = new Unidad();
		u.setId(pxc.getIdUnidad());
		productoDocumento.setUnidade(u); // unidad de venta

		Moneda moneda = new Moneda();
		moneda.setId("USD");

		productoDocumento.setMoneda(moneda);
		productoDocumento.setCantidad2(new BigDecimal(0));
		productoDocumento.setValorUnitatrioMl(new BigDecimal(0));
		productoDocumento.setValorUnitarioUsd(new BigDecimal(0));
		productoDocumento.setValorTotal(new BigDecimal(0));
		productoDocumento.setTotalPesoNetoItem(new BigDecimal(0));

		productoDocumento.setTotalPesoBrutoItem(new BigDecimal(0));

		productoDocumento.setCantidadCajasItem(new BigDecimal(0));
		productoDocumento.setCantidadPalletsItem(new BigDecimal(0));
		productoDocumento.setCantidadXEmbalaje(new BigDecimal(0));

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 2);

		productoDocumento.setFechaEstimadaEntrega(new Timestamp(c.getTimeInMillis()));
		productoDocumento.setFechaEntrega(new Timestamp(c.getTimeInMillis()));

		productos.add(productoDocumento);
		if (pxc.getProductoInventarioComext().getControlStock() != null && pxc.getProductoInventarioComext().getControlStock()) {
		  MovimientosInventarioComext mi = new MovimientosInventarioComext();
		  mi.setCantidad(pxc.getCantidad());
		  mi.setConsecutivoDocumento(documento.getConsecutivoDocumento());
		  mi.setFecha(new Timestamp(System.currentTimeMillis()));
		  mi.setId(null); // Para que tome por BD secuencia
		  mi.setProductosInventarioComext(pxc.getProductoInventarioComext());
		  mi.setSaldo(pxc.getSaldo());
		  mi.setTipoMovimiento(new TipoMovimiento());
		  mi.getTipoMovimiento().setId(1L);
		  mice.add(mi);
		}
	  }
	}
	documento = this.comercioEjb.crearSolicitudPedido(documento, auditoria, dxn, productos, mice);
	String mensaje = AplicacionMB.getMessage("VentasSPExito_Crear", language);
	String parametros[] = new String[2];
	parametros[0] = "" + documento.getId();
	parametros[1] = documento.getConsecutivoDocumento();
	mensaje = Utilidad.stringFormat(mensaje, parametros);

	this.addMensajeInfo(mensaje);
	this.deshabilitado = true;
	return null;

  }
}
