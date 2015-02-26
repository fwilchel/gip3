package com.ssl.jv.gip.web.mb.ventas;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesEJBLocal;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Utilidad;


/**
 * The Class IngresarVentaDirectaMB.
 */
@ManagedBean(name = "ingresarVentaDirectaMB")
@ViewScoped
public class IngresarVentaDirectaMB extends UtilMB {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5093870535116322203L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(IngresarVentaDirectaMB.class);
	
	@ManagedProperty(value="#{menuMB}")
	private MenuMB menu;

	/** The reportes comercio exterior ejb local. */
	@EJB
	private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	
	/** The reportes ejb local. */
	@EJB
	private ReportesEJBLocal reportesEJBLocal;

	/** The comercio exterior ejb local. */
	@EJB
	private ComercioExteriorEJBLocal comercioExteriorEJBLocal;
	
	@EJB
	private MaestrosEJBLocal maestrosEJBLocal;
	
	@EJB
	private VentasFacturacionEJBLocal ventasFacturacionEJBLocal;	

	
	/** The language. */
	private Integer language = AplicacionMB.SPANISH;
	
	private List<ProductosXCliente> listaProductosXCliente;
	
	private List<ProductoPorClienteDTO> listaProductosXClienteDTO = new ArrayList<ProductoPorClienteDTO>();
	
	private List<ProductoPorClienteDTO> listaProductosXClienteSeleccionadosDTO = new ArrayList<ProductoPorClienteDTO>();
	
	private List<Ubicacion> listaUbicaciones;
	
	private List<Cliente> listaClientes;
	
	private List<PuntoVenta> listaPuntoVenta = new ArrayList<PuntoVenta>();
	
	private Date fechaActual;
	
	private Date fechaEntrega;
	
	private Date fechaEsperadaEntrega;
	
	private String strDocumentoCliente;
	
	private Long intUbicacion;
	
	private Long intIdCliente;
	
	private Long intIdPuntoVenta;
	
	private String consecutivoDocumento;
	
	/** The str descripcion. */
	private String strDescripcion;
	

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {

		try {
			fechaActual = new Date();
			Calendar fecha = new GregorianCalendar();
			int diaActual = fecha.get(Calendar.DAY_OF_MONTH);
			int diaEntrega = diaActual + 2;
			fecha.set(Calendar.DAY_OF_MONTH, diaEntrega);
			fechaEntrega = fecha.getTime();
			fechaEsperadaEntrega = fecha.getTime();
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}

	}
	
	
	public void crearVentaDirecta(){
		Documento documento = new Documento();
		documento.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
		Estadosxdocumento estadosxdocumento=new Estadosxdocumento();
		EstadosxdocumentoPK estadosxdocumentoPK=new EstadosxdocumentoPK();
		estadosxdocumentoPK.setIdEstado((long)ConstantesDocumento.ACTIVO);
		estadosxdocumentoPK.setIdTipoDocumento((long)ConstantesTipoDocumento.VENTA_DIRECTA);
		estadosxdocumento.setId(estadosxdocumentoPK);
		documento.setEstadosxdocumento(estadosxdocumento);
		documento.setObservacionDocumento(this.consecutivoDocumento);
		documento.setUbicacionDestino(new Ubicacion());
		documento.setUbicacionOrigen(new Ubicacion());
		documento.getUbicacionDestino().setId(this.intUbicacion);
		documento.getUbicacionOrigen().setId(com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo());
		Cliente cliente = new Cliente();
		cliente.setId(intIdCliente);
		documento.setCliente(cliente);
//		documento.setPuntoVenta(intIdPuntoVenta);
		documento.setDocumentoCliente(this.strDocumentoCliente);
		
		if(listaProductosXClienteSeleccionadosDTO!=null && !listaProductosXClienteSeleccionadosDTO.isEmpty()){
			documento.setDescuentoCliente(listaProductosXClienteSeleccionadosDTO.get(0).getDescuentoCliente());
		}
		
		documento.setFechaEntrega(this.fechaEntrega);
		documento.setFechaEsperadaEntrega(this.fechaEsperadaEntrega);
		documento.setNumeroFactura("0");
		
		LogAuditoria auditoria=new LogAuditoria();
		auditoria.setIdUsuario(menu.getUsuario().getId());
		auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
		auditoria.setTabla(Documento.class.getName());
		auditoria.setAccion("CRE");
		auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
		
			
		List<ProductosXDocumento> productos=new ArrayList<ProductosXDocumento>();
		
		for(ProductoPorClienteDTO pxc:this.listaProductosXClienteSeleccionadosDTO){
			
			ProductosXDocumento productoDocumento = new ProductosXDocumento();
			productoDocumento.setInformacion(false);
			productoDocumento.setCalidad(false);
			productoDocumento.setFechaEstimadaEntrega(documento.getFechaEsperadaEntrega());
			productoDocumento.setFechaEntrega(documento.getFechaEntrega());
			productoDocumento.setId(new ProductosXDocumentoPK());
			productoDocumento.getId().setIdProducto(pxc.getIdProducto());
			productoDocumento.setCantidad1(pxc.getCantidadUno());    	      				
				
			Unidad u = new Unidad();
			u.setId(pxc.getIdUnidad());
			productoDocumento.setUnidade(u); // unidad de venta

			Moneda moneda = new Moneda();
			moneda.setId("COP");
			productoDocumento.setMoneda(moneda); 
			
			productoDocumento.setCantidad2(BigDecimal.ZERO);
			productoDocumento.setValorUnitatrioMl(pxc.getPrecioMl());
			productoDocumento.setValorUnitarioUsd(pxc.getPrecioUsd());
			productoDocumento.setIva(pxc.getIva());
			productoDocumento.setDescuentoxproducto(pxc.getDescuentoxproducto());
			productoDocumento.setOtrosDescuentos(pxc.getOtrosDescuentos());

			productos.add(productoDocumento);
		}
		
		documento=this.ventasFacturacionEJBLocal.crearVentaDirecta(documento, auditoria, productos);
		
		String mensaje = AplicacionMB.getMessage("VentasVDExito_Crear", language);
		String parametros[]=new String[2];
		parametros[0]=""+documento.getId();
		parametros[1]=documento.getConsecutivoDocumento();
		mensaje=Utilidad.stringFormat(mensaje, parametros);
		
		this.addMensajeInfo(mensaje);
		
		cancelar();

	}

	/**
	 * Consultar facturas exportacion.
	 */
	public void consultarProductosXCliente() {
		try {
			listaProductosXClienteDTO = new ArrayList<ProductoPorClienteDTO>();
			listaProductosXClienteSeleccionadosDTO = new ArrayList<ProductoPorClienteDTO>();
			
			this.listaProductosXCliente = ventasFacturacionEJBLocal
					.consultarPorClientePuntoVenta(intIdCliente, intIdPuntoVenta);
			
			if(listaProductosXCliente!=null && !listaProductosXCliente.isEmpty()){
				for(ProductosXCliente producto:listaProductosXCliente){
					ProductoPorClienteDTO productoDTO = new ProductoPorClienteDTO();
					productoDTO.setCantidadUno(BigDecimal.ZERO);
					productoDTO.setDescuentoxproducto(producto.getDescuentoxproducto());
					productoDTO.setIva(producto.getIva());
					productoDTO.setNombre(producto.getProductosInventario().getNombre());
					productoDTO.setOtrosDescuentos(producto.getOtrosDescuentos());
					productoDTO.setPrecioMl(producto.getPrecioMl());
					productoDTO.setSeleccionado(false);
					productoDTO.setSku(producto.getProductosInventario().getSku());
					productoDTO.setDescuentoCliente(producto.getCliente().getDescuentoCliente());
					productoDTO.setIdProducto(producto.getProductosInventario().getId());
					productoDTO.setIdUnidad(producto.getProductosInventario().getUnidadVenta().getId());
					productoDTO.setPrecioUsd(producto.getPrecioUsd());
					listaProductosXClienteDTO.add(productoDTO);
				}
				
			}
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}
	
	public void seleccionarProductos(){
		if(listaProductosXClienteDTO!=null && !listaProductosXClienteDTO.isEmpty()){
			for(ProductoPorClienteDTO producto:listaProductosXClienteDTO){
				if(producto.getSeleccionado()){
					listaProductosXClienteSeleccionadosDTO.add(producto);					
				}
			}
			
			if(listaProductosXClienteSeleccionadosDTO!=null && !listaProductosXClienteSeleccionadosDTO.isEmpty()){
				for(ProductoPorClienteDTO productoSeleccionado:listaProductosXClienteSeleccionadosDTO){
					listaProductosXClienteDTO.remove(productoSeleccionado);
				}
			}					
		}
	}
	
	/**
	 * Cancelar.
	 */
	public void cancelar(){
		listaProductosXClienteDTO = new ArrayList<ProductoPorClienteDTO>();
		listaProductosXClienteSeleccionadosDTO = new ArrayList<ProductoPorClienteDTO>();
		intIdCliente = null;
		intIdPuntoVenta = null;
		intUbicacion = null;
		consecutivoDocumento = "";
		strDocumentoCliente = "";
		listaPuntoVenta = new ArrayList<PuntoVenta>();
		
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public Integer getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(Integer language) {
		this.language = language;
	}

	/**
	 * Gets the fecha actual.
	 *
	 * @return the fecha actual
	 */
	public Date getFechaActual() {
		return fechaActual;
	}

	/**
	 * Sets the fecha actual.
	 *
	 * @param fechaActual the new fecha actual
	 */
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	/**
	 * Gets the consecutivo documento.
	 *
	 * @return the consecutivo documento
	 */
	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	/**
	 * Sets the consecutivo documento.
	 *
	 * @param consecutivoDocumento the new consecutivo documento
	 */
	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	/**
	 * Gets the str descripcion.
	 *
	 * @return the str descripcion
	 */
	public String getStrDescripcion() {
		return strDescripcion;
	}

	/**
	 * Sets the str descripcion.
	 *
	 * @param strDescripcion the new str descripcion
	 */
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}


	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}
	
	public void cargarPuntosVenta(){
		if(intIdCliente!=null){
			listaPuntoVenta = maestrosEJBLocal.consultarPuntoEntregaPorCliente(intIdCliente);
		}else{
			listaPuntoVenta = new ArrayList<PuntoVenta>();
		}
	}
	
	
	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Date getFechaEsperadaEntrega() {
		return fechaEsperadaEntrega;
	}

	public void setFechaEsperadaEntrega(Date fechaEsperadaEntrega) {
		this.fechaEsperadaEntrega = fechaEsperadaEntrega;
	}

	public String getStrDocumentoCliente() {
		return strDocumentoCliente;
	}

	public void setStrDocumentoCliente(String strDocumentoCliente) {
		this.strDocumentoCliente = strDocumentoCliente;
	}

	/**
	 * Gets the lista ubicaciones.
	 *
	 * @return the lista ubicaciones
	 */
	public List<Ubicacion> getListaUbicaciones() {
		if(listaUbicaciones==null){
			listaUbicaciones = comercioExteriorEJBLocal.consultarUbicacionesPorUsuario(menu.getUsuario().getId());
		}
		return listaUbicaciones;
	}

	/**
	 * Sets the lista ubicaciones.
	 *
	 * @param listaUbicaciones the new lista ubicaciones
	 */
	public void setListaUbicaciones(List<Ubicacion> listaUbicaciones) {
		this.listaUbicaciones = listaUbicaciones;
	}

	public Long getIntUbicacion() {
		return intUbicacion;
	}

	public void setIntUbicacion(Long intUbicacion) {
		this.intUbicacion = intUbicacion;
	}

	public Long getIntIdCliente() {
		return intIdCliente;
	}

	public void setIntIdCliente(Long intIdCliente) {
		this.intIdCliente = intIdCliente;
	}

	public List<Cliente> getListaClientes() {
		if(listaClientes==null){
			listaClientes = maestrosEJBLocal.consultarClientes();
		}
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<PuntoVenta> getListaPuntoVenta() {
		return listaPuntoVenta;
	}

	public void setListaPuntoVenta(List<PuntoVenta> listaPuntoVenta) {
		this.listaPuntoVenta = listaPuntoVenta;
	}

	public Long getIntIdPuntoVenta() {
		return intIdPuntoVenta;
	}

	public void setIntIdPuntoVenta(Long intIdPuntoVenta) {
		this.intIdPuntoVenta = intIdPuntoVenta;
	}

	public List<ProductosXCliente> getListaProductosXCliente() {
		return listaProductosXCliente;
	}

	public void setListaProductosXCliente(
			List<ProductosXCliente> listaProductosXCliente) {
		this.listaProductosXCliente = listaProductosXCliente;
	}

	public List<ProductoPorClienteDTO> getListaProductosXClienteDTO() {
		return listaProductosXClienteDTO;
	}

	public void setListaProductosXClienteDTO(
			List<ProductoPorClienteDTO> listaProductosXClienteDTO) {
		this.listaProductosXClienteDTO = listaProductosXClienteDTO;
	}

	public List<ProductoPorClienteDTO> getListaProductosXClienteSeleccionadosDTO() {
		return listaProductosXClienteSeleccionadosDTO;
	}

	public void setListaProductosXClienteSeleccionadosDTO(
			List<ProductoPorClienteDTO> listaProductosXClienteSeleccionadosDTO) {
		this.listaProductosXClienteSeleccionadosDTO = listaProductosXClienteSeleccionadosDTO;
	}
	
	


}
