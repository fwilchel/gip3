package com.ssl.jv.gip.web.mb.ventas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesUbicacion;
import com.ssl.jv.gip.web.mb.util.Numero_a_Letra_Ingles;
import com.ssl.jv.gip.web.util.Numero_a_Letra;
import com.ssl.jv.gip.web.util.Utilidad;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean(name = "generarFacturaMB")
@ViewScoped
public class GenerarFacturaMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(GenerarFacturaMB.class);

  private enum Modo {

	DETALLE, MENSAGE;
  }

  @EJB
  private VentasFacturacionEJBLocal ventasFacturacionEJB;

  @EJB
  private MaestrosEJBLocal maestrosEJB;

  @EJB
  private ComunEJBLocal comunEJB;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  private final Integer language = AplicacionMB.SPANISH;
  /**
   * Consecutivo por el cual se va a realizar la busqueda
   */
  private String consecutivoDocumento;
  /**
   * Tipo de factura a generar
   */
  private List<SelectItem> listaTiposFacturas;
  /**
   * tipo de factura remisionSeleccionada
   */
  private Integer tipoFacturaSeleccionado;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<Documento> listaRemisiones;
  /**
   * Registro remisionSeleccionada
   */
  private Documento remisionSeleccionada;
  /**
   * Factura generada
   */
  private Documento facturaGenerada;
  /**
   *
   */
  private List<ProductosXDocumento> listaProductosXRemision;
  private BigDecimal subtotal = new BigDecimal(0);
  private BigDecimal descuento = new BigDecimal(0);
  private BigDecimal totalIva10 = new BigDecimal(0);
  private BigDecimal totalIva16 = new BigDecimal(0);
  private BigDecimal totalIva5 = new BigDecimal(0);
  private BigDecimal total = new BigDecimal(0);
  private Modo modo;
  /**
   * Valida si se muestra o no el dialogo
   */
  private boolean mostrarDialogo;

  @PostConstruct
  public void init() {
	LOGGER.trace("Metodo: <<init>>");
	cargarTiposFacuras();
  }

  /**
   *
   */
  private void cargarTiposFacuras() {
	LOGGER.trace("Metodo: <<cargarTiposFacuras>>");
	setListaTiposFacturas(new ArrayList<SelectItem>());
	getListaTiposFacturas().add(new SelectItem(ConstantesTipoDocumento.FACTURA, AplicacionMB.getMessage("gfFltLblItmTipoFacturaDirecta", language)));
	getListaTiposFacturas().add(new SelectItem(ConstantesTipoDocumento.FACTURA_ESPECIAL, AplicacionMB.getMessage("gfFltLblItmTipoFacturaEspecial", language)));
	getListaTiposFacturas().add(new SelectItem(ConstantesTipoDocumento.SOLICITUD_PEDIDO, AplicacionMB.getMessage("gfFltLblItmTipoFacturaConsumoServicios", language)));
  }

  /**
   *
   */
  public void consultarListaRemisiones() {
	LOGGER.trace("Metodo: <<consultarListaRemisiones>>");
	setListaRemisiones(ventasFacturacionEJB.consultarRemisionesPendientesPorRecibir(getConsecutivoDocumento()));
  }

  /**
   *
   * @param documento
   */
  public void seleccionarRemision(Documento documento) {
	LOGGER.trace("Metodo: <<seleccionarRemision>>");
	setRemisionSeleccionada(documento);
	modo = Modo.DETALLE;
	if (getRemisionSeleccionada().getSitioEntrega() != null && getRemisionSeleccionada().getSitioEntrega().equals("CS") && getTipoFacturaSeleccionado() != ConstantesTipoDocumento.SOLICITUD_PEDIDO) {
	  addMensajeError(AplicacionMB.getMessage("gfErrorValidacion1", language));
	  setMostrarDialogo(false);
	  LOGGER.debug(AplicacionMB.getMessage("gfErrorValidacion1", language));
	} else if (getTipoFacturaSeleccionado() == ConstantesTipoDocumento.SOLICITUD_PEDIDO && (getRemisionSeleccionada().getSitioEntrega() == null || !remisionSeleccionada.getSitioEntrega().equals("CS"))) {
	  addMensajeError(AplicacionMB.getMessage("gfErrorValidacion2", language));
	  setMostrarDialogo(false);
	  LOGGER.debug(AplicacionMB.getMessage("gfErrorValidacion2", language));
	} else {
	  setMostrarDialogo(true);
	  // lista de productos para el documento de VD relacionado a la remision
	  Documento ventaDirecta = maestrosEJB.consultarDocumentoPorConsecutivo(getRemisionSeleccionada().getObservacionDocumento());
	  List<ProductosXDocumento> listaSugrenciasConsultadas = ventasFacturacionEJB.consultarProductosPorDocumento(ventaDirecta.getId());
	  // lista de productos para la remision seleccionada
	  List<ProductosXDocumento> listaCantidadesRemision = ventasFacturacionEJB.consultarProductosPorDocumento(remisionSeleccionada.getId());
	  listaProductosXRemision = new ArrayList<>();
	  // se cruzan las dos listas para crear una nueva
	  for (ProductosXDocumento pxd : listaSugrenciasConsultadas) {
		for (ProductosXDocumento pxdqty : listaCantidadesRemision) {
		  if (Objects.equals(pxd.getId().getIdProducto(), pxdqty.getId().getIdProducto())) {
			BigDecimal valorUnitatrioMl = pxd.getValorUnitatrioMl();
			BigDecimal cantidad = pxdqty.getCantidad1();
			BigDecimal descuentoProducto = pxd.getDescuentoxproducto();
			BigDecimal descuentoCliente = ventaDirecta.getCliente().getDescuentoCliente();
			BigDecimal desc = ((valorUnitatrioMl.multiply(cantidad)).multiply((descuentoProducto.divide(new BigDecimal(100)))));
			BigDecimal valorTotal = ((valorUnitatrioMl.multiply(cantidad)).subtract(desc));
			BigDecimal descCliente = (valorTotal.multiply(descuentoCliente.divide(new BigDecimal(100))));
			BigDecimal otrosDescuentos = valorTotal.multiply(pxd.getOtrosDescuentos().divide(new BigDecimal(100)));
			BigDecimal iva = (pxd.getIva());
			BigDecimal valorIva = ((valorTotal.subtract(descCliente).subtract(otrosDescuentos)).multiply(pxd.getIva().divide(new BigDecimal(100))));

			ProductosXDocumento p = new ProductosXDocumento();
			p.setValorUnitatrioMl(Utilidad.round(valorUnitatrioMl));
			p.setProductosInventario(pxd.getProductosInventario());
			p.setUnidade(pxd.getUnidade());
			p.setCantidad1(Utilidad.round(cantidad));
			p.setValorTotal(Utilidad.round(valorTotal));
			p.setDescuentoxproducto(Utilidad.round(descuentoProducto));
			p.setIva(Utilidad.round(iva));
			p.setOtrosDescuentos(Utilidad.round(otrosDescuentos));
			if (pxdqty.getCantidad1().compareTo(pxd.getCantidad1()) > 0) {
			  p.setObservacion2("red");
			} else {
			  p.setObservacion2("blank");
			}
			p.setCantidadVD(pxd.getCantidad1());
			// agregar a la lista
			listaProductosXRemision.add(p);
			// totales
			subtotal = Utilidad.round(subtotal.add(valorTotal));
			descuento = Utilidad.round(descuento.add(otrosDescuentos).add(descCliente));
			if (p.getIva().compareTo(new BigDecimal(10.0)) == 0) {
			  totalIva10 = Utilidad.round(totalIva10.add(valorIva));
			} else if (p.getIva().compareTo(new BigDecimal(5.0)) == 0) {
			  totalIva5 = Utilidad.round(totalIva5.add(valorIva));
			} else if (p.getIva().compareTo(new BigDecimal(16.0)) == 0) {
			  totalIva16 = Utilidad.round(totalIva16.add(valorIva));
			}
		  }
		}
	  }
	  total = Utilidad.round(subtotal.add(descuento).add(totalIva10).add(totalIva5).add(totalIva16));
	  LOGGER.debug("Total factura: " + total);
	}
  }

  /**
   *
   */
  public void generarFactura() {
	LOGGER.trace("Metodo: <<generarFactura>>");
	try {
	  Documento factura = new Documento();
	  // tipo de documento y estado
	  Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
	  EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
	  estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.GENERADO);
	  if (tipoFacturaSeleccionado == ConstantesTipoDocumento.FACTURA) {
		estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA);
	  } else {
		estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA_ESPECIAL);
	  }
	  estadosxdocumento.setId(estadosxdocumentoPK);
	  // llenar el objeto documento
	  factura.setEstadosxdocumento(estadosxdocumento);
	  factura.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
	  factura.setObservacionDocumento(this.remisionSeleccionada.getConsecutivoDocumento());
	  factura.setUbicacionOrigen(new Ubicacion(ConstantesUbicacion.UBICACION_DESTINO_DEFAULT));
	  factura.setUbicacionDestino(remisionSeleccionada.getUbicacionDestino());
	  factura.setObservacionDocumento(remisionSeleccionada.getConsecutivoDocumento());
	  Documento ordenDespacho = consultarOrdenDespacho(remisionSeleccionada.getObservacionDocumento());
	  if (ordenDespacho == null) {
		factura.setDocumentoCliente(this.remisionSeleccionada.getDocumentoCliente());
		factura.setObservacion3(null);
	  } else {
		factura.setDocumentoCliente(ordenDespacho.getDocumentoCliente());
		factura.setObservacion3(ordenDespacho.getConsecutivoDocumento());
	  }
	  factura.setCliente(remisionSeleccionada.getCliente());
	  factura.setPuntoVenta(remisionSeleccionada.getPuntoVenta());
	  factura.setSubtotal(subtotal);
	  factura.setDescuento(descuento);
	  factura.setValorIva16(totalIva16);
	  factura.setValorIva10(totalIva10);
	  factura.setValorIva5(totalIva5);
	  factura.setValorTotal(total);
	  factura.setNumeroFactura(remisionSeleccionada.getNumeroFacturaEspecial());
	  factura.setObservacion2(this.remisionSeleccionada.getObservacion2());
	  if (getRemisionSeleccionada().getSitioEntrega() != null && getRemisionSeleccionada().getSitioEntrega().equals("CS")) {
		factura.setSitioEntrega(remisionSeleccionada.getSitioEntrega());
	  } else {
		factura.setSitioEntrega(remisionSeleccionada.getDocumentoCliente());
	  }
	  factura.setFechaEntrega(remisionSeleccionada.getFechaEntrega());
	  factura.setFechaEsperadaEntrega(remisionSeleccionada.getFechaEsperadaEntrega());
	  factura.setDescuentoCliente(remisionSeleccionada.getCliente().getDescuentoCliente());
	  // auditoria
	  LogAuditoria auditoria = new LogAuditoria();
	  auditoria.setIdUsuario(menu.getUsuario().getId());
	  auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
	  // enviar a generar la factura
	  setFacturaGenerada(ventasFacturacionEJB.generarFactura(factura, listaProductosXRemision, remisionSeleccionada, auditoria));
	  LOGGER.debug("Factura generada exitosamente con id: " + getFacturaGenerada().getId() + " y consecutivo: " + getFacturaGenerada().getConsecutivoDocumento());
	  modo = Modo.MENSAGE;
	  addMensajeInfo(formatearCadenaConParametros("gfMsgExitoGenerarFactura", language, getFacturaGenerada().getId().toString(), getFacturaGenerada().getConsecutivoDocumento()));
	  // refrescar lista de remisiones
	  consultarListaRemisiones();
	} catch (Exception ex) {
	  modo = Modo.DETALLE;
	  addMensajeError("Error");
	  LOGGER.error("Error");
	}
  }

  /**
   *
   * @param observacionDocumento
   * @return
   */
  private Documento consultarOrdenDespacho(String observacionDocumento) {
	LOGGER.trace("Metodo: <<consultarOrdenDespacho>>");
	List<Documento> listaOrdenesDespacho;
	listaOrdenesDespacho = ventasFacturacionEJB.consultarOrdenesDespachoPorObservacion(observacionDocumento);
	if (listaOrdenesDespacho != null) {
	  for (Documento ordenDespacho : listaOrdenesDespacho) {
		return ordenDespacho;
	  }
	}
	return null;
  }

  /**
   * Metodo que se encarga de enviar la solicitud de poner en estado impreso el
   * documento.
   */
  public void imprimirFactura() {
	LOGGER.trace("Metodo: <<imprimirFactura>>");
	LOGGER.debug("Actuaizar el numero de factura y estado de la factura");
	facturaGenerada.setNumeroFactura(remisionSeleccionada.getNumeroFactura());
	ventasFacturacionEJB.imprimirFactura(facturaGenerada);
	LOGGER.debug("Factura actualizada a estado impresa");
  }

  /**
   *
   * @return
   */
  public StreamedContent previsuaizarFactura() {
	LOGGER.trace("Metodo: <<previsuaizarFactura>>");
	imprimirFactura();
	StreamedContent reporte = null;
	Map<String, Object> parametrosReporte = new HashMap<>();
	SimpleDateFormat formatoFecha;
	formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	if (facturaGenerada.getCliente() != null) {
	  Cliente cliente = facturaGenerada.getCliente();
	  try {
		cliente = comunEJB.consultarCliente(facturaGenerada.getCliente().getId(), Cliente.BUSCAR_CLIENTE_FETCH_CIUDAD);
	  } catch (PersistenceException ex) {
		LOGGER.debug("Cliente no encontrado", ex);
	  }
	  parametrosReporte.put("cliente", cliente.getNombre());
	  parametrosReporte.put("nit", cliente.getNit());
	  if (cliente.getCiudad() != null) {
		parametrosReporte.put("ciudad", cliente.getCiudad().getNombre() + " - " + cliente.getCiudad().getPais().getNombre());
	  }
	  parametrosReporte.put("direccion", cliente.getDireccion());
	  parametrosReporte.put("telefono", cliente.getTelefono());
	}
	if (facturaGenerada.getPuntoVenta() != null) {
	  parametrosReporte.put("despachado_a", facturaGenerada.getPuntoVenta().getNombre());
	  parametrosReporte.put("direccionpv", facturaGenerada.getPuntoVenta().getDireccion());
	  parametrosReporte.put("telefonopv", "Teléfono: " + facturaGenerada.getPuntoVenta().getTelefono());
	  parametrosReporte.put("ciudadpv", facturaGenerada.getPuntoVenta().getCiudade().getNombre());
	}
	parametrosReporte.put("documento", facturaGenerada.getDocumentoCliente());
	parametrosReporte.put("fecha", formatoFecha.format(getFechaActual()));
	parametrosReporte.put("sku", "A28");
	parametrosReporte.put("numFactura", facturaGenerada.getConsecutivoDocumento());
	parametrosReporte.put("tipoImp", "Original");
	parametrosReporte.put("anulada", "");
	boolean tieneOtrosDescuentos = false;
	List<ProductosXDocumento> pxds = ventasFacturacionEJB.consultarProductosPorDocumentoOrdenadosPorSKU(facturaGenerada.getId());
	for (ProductosXDocumento pxd : pxds) {
	  if (pxd.getOtrosDescuentos() == null || pxd.getOtrosDescuentos() == BigDecimal.ZERO) {
		tieneOtrosDescuentos = true;
		pxd.setObservacion2("(*)");
	  } else {
		pxd.setObservacion2("");
	  }
	}
	parametrosReporte.put("subtotal", facturaGenerada.getSubtotal());
	parametrosReporte.put("descuento", facturaGenerada.getDescuento());
	parametrosReporte.put("valorIva10", facturaGenerada.getValorIva10());
	parametrosReporte.put("valorIva16", facturaGenerada.getValorIva16());
	parametrosReporte.put("valorIva5", facturaGenerada.getValorIva5());
	parametrosReporte.put("valorTotal", facturaGenerada.getValorTotal());
	if (tieneOtrosDescuentos) { // Existe algun registro con descuento adicioanl
	  parametrosReporte.put("descuentoCliente", facturaGenerada.getDescuentoCliente() + "%");
	  parametrosReporte.put("observaciones", "Los productos marcados con (*) incluyen descuento adicional.");
	  parametrosReporte.put("mark", "(*)");
	} else {
	  parametrosReporte.put("descuentoCliente", "");
	  parametrosReporte.put("observaciones", "");
	  parametrosReporte.put("mark", "");
	}
	
	Numero_a_Letra NumLetra = new Numero_a_Letra();
	parametrosReporte.put("valorTotalEnLetras", NumLetra.Convertir(facturaGenerada.getValorTotal().toString(),true));
	JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(pxds, false);
	try {
	  StringBuilder nombreArchivo = new StringBuilder();
	  nombreArchivo.append("FacturaDirecta");
	  nombreArchivo.append("-");
	  nombreArchivo.append(facturaGenerada.getConsecutivoDocumento());
	  nombreArchivo.append(".");
	  nombreArchivo.append("pdf");
	  Hashtable<String, String> parametrosConfiguracionReporte;
	  parametrosConfiguracionReporte = new Hashtable<>();
	  parametrosConfiguracionReporte.put("tipo", "pdf");
	  String reportePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/FacturaDirecta.jasper");
	  ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfiguracionReporte, reportePath, null, null, null, parametrosReporte, ds);
	  reporte = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf", nombreArchivo.toString());
	} catch (Exception e) {
	  this.addMensajeError("Problemas al generar el reporte");
	}
	return reporte;
  }

  /**
   *
   */
  public void cancelar() {
	LOGGER.trace("Metodo: <<cancelar>>");
	this.setRemisionSeleccionada(null);
	this.setConsecutivoDocumento("");
  }

  /**
   *
   * @return
   */
  public boolean showPrintBtn() {
	return (isModoMensage() && (tipoFacturaSeleccionado == ConstantesTipoDocumento.FACTURA));
  }

  /**
   *
   * @return
   */
  public boolean isFacturaDirecta() {
	return getTipoFacturaSeleccionado() == null ? false : getTipoFacturaSeleccionado().equals(ConstantesTipoDocumento.FACTURA);
  }

  /**
   *
   * @return
   */
  public boolean isFacturaEspecial() {
	return getTipoFacturaSeleccionado() == null ? false : getTipoFacturaSeleccionado().equals(ConstantesTipoDocumento.FACTURA_ESPECIAL);
  }

  /**
   *
   * @return
   */
  public boolean isFacturaConsumoServicios() {
	return getTipoFacturaSeleccionado() == null ? false : getTipoFacturaSeleccionado().equals(ConstantesTipoDocumento.SOLICITUD_PEDIDO);
  }

  /**
   *
   * @return
   */
  public boolean isModoDetalle() {
	return modo.equals(Modo.DETALLE);
  }

  /**
   *
   * @return
   */
  public boolean isModoMensage() {
	return modo.equals(Modo.MENSAGE);
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
   * @return the consecutivoDocumento
   */
  public String getConsecutivoDocumento() {
	return consecutivoDocumento;
  }

  /**
   * @param consecutivoDocumento
   *          the consecutivoDocumento to set
   */
  public void setConsecutivoDocumento(String consecutivoDocumento) {
	this.consecutivoDocumento = consecutivoDocumento;
  }

  /**
   * @return the listaTiposFacturas
   */
  public List<SelectItem> getListaTiposFacturas() {
	return listaTiposFacturas;
  }

  /**
   * @param listaTiposFacturas
   *          the listaTiposFacturas to set
   */
  public void setListaTiposFacturas(List<SelectItem> listaTiposFacturas) {
	this.listaTiposFacturas = listaTiposFacturas;
  }

  /**
   * @return the tipoFacturaSeleccionado
   */
  public Integer getTipoFacturaSeleccionado() {
	return tipoFacturaSeleccionado;
  }

  /**
   * @param tipoFacturaSeleccionado
   *          the tipoFacturaSeleccionado to set
   */
  public void setTipoFacturaSeleccionado(Integer tipoFacturaSeleccionado) {
	this.tipoFacturaSeleccionado = tipoFacturaSeleccionado;
  }

  /**
   * @return the listaRemisiones
   */
  public List<Documento> getListaRemisiones() {
	return listaRemisiones;
  }

  /**
   * @param listaRemisiones
   *          the listaRemisiones to set
   */
  public void setListaRemisiones(List<Documento> listaRemisiones) {
	this.listaRemisiones = listaRemisiones;
  }

  /**
   * @return the remisionSeleccionada
   */
  public Documento getRemisionSeleccionada() {
	return remisionSeleccionada;
  }

  /**
   * @param remisionSeleccionada
   *          the remisionSeleccionada to set
   */
  public void setRemisionSeleccionada(Documento remisionSeleccionada) {
	this.remisionSeleccionada = remisionSeleccionada;
  }

  /**
   * @return the facturaGenerada
   */
  public Documento getFacturaGenerada() {
	return facturaGenerada;
  }

  /**
   * @param facturaGenerada
   *          the facturaGenerada to set
   */
  public void setFacturaGenerada(Documento facturaGenerada) {
	this.facturaGenerada = facturaGenerada;
  }

  /**
   * @return the listaProductosXRemision
   */
  public List<ProductosXDocumento> getListaProductosXRemision() {
	return listaProductosXRemision;
  }

  /**
   * @param listaProductosXRemision
   *          the listaProductosXRemision to set
   */
  public void setListaProductosXRemision(List<ProductosXDocumento> listaProductosXRemision) {
	this.listaProductosXRemision = listaProductosXRemision;
  }

  /**
   * @return the subtotal
   */
  public BigDecimal getSubtotal() {
	return subtotal;
  }

  /**
   * @param subtotal
   *          the subtotal to set
   */
  public void setSubtotal(BigDecimal subtotal) {
	this.subtotal = subtotal;
  }

  /**
   * @return the descuento
   */
  public BigDecimal getDescuento() {
	return descuento;
  }

  /**
   * @param descuento
   *          the descuento to set
   */
  public void setDescuento(BigDecimal descuento) {
	this.descuento = descuento;
  }

  /**
   * @return the totalIva10
   */
  public BigDecimal getTotalIva10() {
	return totalIva10;
  }

  /**
   * @param totalIva10
   *          the totalIva10 to set
   */
  public void setTotalIva10(BigDecimal totalIva10) {
	this.totalIva10 = totalIva10;
  }

  /**
   * @return the totalIva16
   */
  public BigDecimal getTotalIva16() {
	return totalIva16;
  }

  /**
   * @param totalIva16
   *          the totalIva16 to set
   */
  public void setTotalIva16(BigDecimal totalIva16) {
	this.totalIva16 = totalIva16;
  }

  /**
   * @return the totalIva5
   */
  public BigDecimal getTotalIva5() {
	return totalIva5;
  }

  /**
   * @param totalIva5
   *          the totalIva5 to set
   */
  public void setTotalIva5(BigDecimal totalIva5) {
	this.totalIva5 = totalIva5;
  }

  /**
   * @return the total
   */
  public BigDecimal getTotal() {
	return total;
  }

  /**
   * @param total
   *          the total to set
   */
  public void setTotal(BigDecimal total) {
	this.total = total;
  }

  public boolean isMostrarDialogo() {
	return mostrarDialogo;
  }

  public void setMostrarDialogo(boolean mostrarDialogo) {
	this.mostrarDialogo = mostrarDialogo;
  }

}
