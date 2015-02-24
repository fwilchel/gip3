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
	
	/** The modo. */
	private Modo modo;

	/** The seleccionado. */
	private Documento seleccionado;

	/** The lista productos documento. */
	private List<ProductosXDocumento> listaProductosDocumento;
	
	private List<ProductoGenerarFacturaPFDTO> productos;

	/** The total cantitad1. */
	private BigDecimal totalCantitad1 = BigDecimal.ZERO;

	/** The total valor total. */
	private BigDecimal totalValorTotal = BigDecimal.ZERO;

	/** The total cantidad x embalaje. */
	private BigDecimal totalCantidadXEmbalaje = BigDecimal.ZERO;

	/** The total cantidad cajas. */
	private BigDecimal totalCantidadCajas = BigDecimal.ZERO;

	/** The total peso neto. */
	private BigDecimal totalPesoNeto = BigDecimal.ZERO;

	/** The total peso bruto. */
	private BigDecimal totalPesoBruto = BigDecimal.ZERO;

	/** The total costo entrega. */
	private BigDecimal totalCostoEntrega = BigDecimal.ZERO;

	/** The total costo seguro. */
	private BigDecimal totalCostoSeguro = BigDecimal.ZERO;

	/** The total costo flete. */
	private BigDecimal totalCostoFlete = BigDecimal.ZERO;

	/** The total pallets. */
	private BigDecimal totalPallets = BigDecimal.ZERO;

	/** The total otros gastos. */
	private BigDecimal totalOtrosGastos = BigDecimal.ZERO;

	/** The total costos. */
	private BigDecimal totalCostos = BigDecimal.ZERO;

	/** The total valor neg. */
	private BigDecimal totalValorNeg = BigDecimal.ZERO;

	/** The lista muestras. */
	private List<Muestrasxlote> listaMuestras;

	/** The lista producto totales. */
	private List<ProductoLoteAsignarLoteOICDTO> listaProductoTotales;

	
	
	
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
	
	
	
	
	/** The consecutivo documento. */
	private String consecutivoDocumento;
	
	/** The str descripcion. */
	private String strDescripcion;
	
	/** The render imprimir factura. */
	private Boolean renderImprimirFactura=false;

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
	
	/**
	 * Crear factura exp.
	 */
	public void crearFacturaExp(){
		Documento documento = new Documento();
		documento.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
		Estadosxdocumento estadosxdocumento=new Estadosxdocumento();
		EstadosxdocumentoPK estadosxdocumentoPK=new EstadosxdocumentoPK();
		estadosxdocumentoPK.setIdEstado((long)ConstantesDocumento.GENERADO);
		estadosxdocumentoPK.setIdTipoDocumento((long)ConstantesTipoDocumento.FACTURA_EXPORTACION);
		estadosxdocumento.setId(estadosxdocumentoPK);
		documento.setEstadosxdocumento(estadosxdocumento);
		documento.setObservacionDocumento(this.seleccionado.getConsecutivoDocumento());
		documento.setUbicacionDestino(new Ubicacion());
		documento.setUbicacionOrigen(new Ubicacion());
		documento.getUbicacionDestino().setId(this.seleccionado.getUbicacionOrigen().getId());
		documento.getUbicacionOrigen().setId(this.seleccionado.getUbicacionOrigen().getId());
		documento.setCliente(this.seleccionado.getCliente());
		documento.setValorTotal(this.totalValorNeg);
		documento.setDocumentoCliente(this.seleccionado.getDocumentoCliente());
		documento.setFechaEsperadaEntrega(this.seleccionado.getFechaEsperadaEntrega());
		documento.setNumeroFactura("0");
		
		LogAuditoria auditoria=new LogAuditoria();
		auditoria.setIdUsuario(menu.getUsuario().getId());
		auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
		auditoria.setTabla(Documento.class.getName());
		auditoria.setAccion("CRE");
		auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
		
		DocumentoXNegociacion dxn = new DocumentoXNegociacion();
		dxn.setPk(new DocumentoXNegociacionPK());
		dxn.getPk().setIdTerminoIncoterm(this.seleccionado.getDocumentoXNegociacions().get(0).getTerminoIncoterm().getId());
		dxn.setCostoEntrega(this.seleccionado.getDocumentoXNegociacions().get(0).getCostoEntrega());
		dxn.setCostoSeguro(this.seleccionado.getDocumentoXNegociacions().get(0).getCostoSeguro());
		dxn.setCostoFlete(this.seleccionado.getDocumentoXNegociacions().get(0).getCostoFlete());
		dxn.setOtrosGastos(this.seleccionado.getDocumentoXNegociacions().get(0).getOtrosGastos());
		dxn.setObservacionesMarcacion2(this.strDescripcion);
		dxn.setTotalPesoNeto(this.seleccionado.getDocumentoXNegociacions().get(0).getTotalPesoNeto());
		dxn.setTotalPesoBruto(this.seleccionado.getDocumentoXNegociacions().get(0).getTotalPesoBruto());
		dxn.setTotalTendidos(this.seleccionado.getDocumentoXNegociacions().get(0).getTotalTendidos());
		dxn.setTotalPallets(this.seleccionado.getDocumentoXNegociacions().get(0).getTotalPallets());
		dxn.setCantidadDiasVigencia(this.seleccionado.getDocumentoXNegociacions().get(0).getCantidadDiasVigencia());
		dxn.setSolicitudCafe(this.seleccionado.getDocumentoXNegociacions().get(0).getSolicitudCafe());
		dxn.setCantidadContenedoresDe20(this.seleccionado.getDocumentoXNegociacions().get(0).getCantidadContenedoresDe20());
		dxn.setCantidadContenedoresDe40(this.seleccionado.getDocumentoXNegociacions().get(0).getCantidadContenedoresDe40());
		dxn.setLugarIncoterm(this.seleccionado.getDocumentoXNegociacions().get(0).getLugarIncoterm());
		dxn.setCantidadEstibas(0);
		dxn.setPesoBrutoEstibas(0);
		
		List<ProductosXDocumento> productos=new ArrayList<ProductosXDocumento>();
		
		for(ProductoGenerarFacturaPFDTO pxc:this.productos){
			
			ProductosXDocumento productoDocumento = new ProductosXDocumento();
			productoDocumento.setInformacion(false);
			productoDocumento.setCalidad(false);
			productoDocumento.setFechaEstimadaEntrega(documento.getFechaGeneracion());
			productoDocumento.setFechaEntrega(documento.getFechaGeneracion());
			productoDocumento.setId(new ProductosXDocumentoPK());
			productoDocumento.getId().setIdProducto(pxc.getId().longValue());
			productoDocumento.setCantidad1(pxc.getCantidad());    	      				
				
			Unidad u = new Unidad();
			u.setId(pxc.getIdUnidad().longValue());
			productoDocumento.setUnidade(u); // unidad de venta

			Moneda moneda = new Moneda();
			moneda.setId("USD");

			productoDocumento.setMoneda(moneda); 	    	      				
			productoDocumento.setCantidad2(new BigDecimal(0));
			productoDocumento.setValorUnitatrioMl(new BigDecimal(0));
			productoDocumento.setValorUnitarioUsd(pxc.getPrecio());
			productoDocumento.setValorTotal(pxc.getValorTotal());
			productoDocumento.setTotalPesoNetoItem(pxc.getTotalPesoNeto());

			productoDocumento.setTotalPesoBrutoItem(pxc.getTotalPesoBruto());
			productoDocumento.setCantidadCajasItem(pxc.getTotalCajas());
			productoDocumento.setCantidadPalletsItem(pxc.getTotalCajasPallet());
			productoDocumento.setCantidadXEmbalaje(pxc.getTotalCajasTendido());
			
			Calendar c=Calendar.getInstance();
			c.add(Calendar.DATE, 2);
			
			productoDocumento.setFechaEstimadaEntrega(new Timestamp(c.getTimeInMillis()));	
			productoDocumento.setFechaEntrega(new Timestamp(c.getTimeInMillis()));

			productos.add(productoDocumento);
		}
		documento=this.comercioExteriorEJBLocal.crearFacturaExportacion(documento, auditoria, dxn, productos, this.seleccionado);
		DocumentoIncontermDTO vDoc = new DocumentoIncontermDTO();
		vDoc.setIdDocumento(documento.getId());
		vDoc.setIdEstado(new Long(ConstantesDocumento.RECIBIDO));
		comercioExteriorEJBLocal.actualizarEstadoDocumento(vDoc);
		
		String mensaje = AplicacionMB.getMessage("VentasFXExito_Crear", language);
		String parametros[]=new String[2];
		parametros[0]=""+documento.getId();
		parametros[1]=documento.getConsecutivoDocumento();
		mensaje=Utilidad.stringFormat(mensaje, parametros);
		
		this.addMensajeInfo(mensaje);
		this.renderImprimirFactura = true;
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
	 * Gets the modo.
	 *
	 * @return the modo
	 */
	public Modo getModo() {
		return modo;
	}

	/**
	 * Sets the modo.
	 *
	 * @param modo the new modo
	 */
	public void setModo(Modo modo) {
		this.modo = modo;
	}

	/**
	 * Gets the seleccionado.
	 *
	 * @return the seleccionado
	 */
	public Documento getSeleccionado() {
		return seleccionado;
	}

	/**
	 * Sets the seleccionado.
	 *
	 * @param seleccionado the new seleccionado
	 */
	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
		try{
			
			this.totalCantitad1 = BigDecimal.ZERO;
			this.totalValorTotal = BigDecimal.ZERO;
			this.totalCantidadXEmbalaje = BigDecimal.ZERO;
			this.totalCantidadCajas = BigDecimal.ZERO;
			this.totalPesoNeto  = BigDecimal.ZERO;
			this.totalPesoBruto = BigDecimal.ZERO;	
			
			listaProductosDocumento = this.reportesComercioExteriorEJBLocal.consultarProductosPorDocumento(seleccionado.getId());	
			for(ProductosXDocumento pr:listaProductosDocumento){
				this.totalCantitad1 = this.totalCantitad1.add(pr.getCantidad1());
				this.totalValorTotal = this.totalValorTotal.add(pr.getValorTotal());
				this.totalCantidadXEmbalaje = this.totalCantidadXEmbalaje.add(pr.getCantidadXEmbalaje());
				this.totalCantidadCajas = this.totalCantidadCajas.add(pr.getCantidadCajasItem());
				this.totalPesoNeto  = this.totalPesoNeto.add(pr.getTotalPesoNetoItem());
				this.totalPesoBruto = this.totalPesoBruto.add(pr.getTotalPesoBrutoItem());				
			}

			//this.listaMuestras = this.reportesComercioExteriorEJBLocal.consultarMuestrasPorCantidad(this.seleccionado.getDocumentoXLotesoics().get(0).getTotalCantidad());
			this.listaProductoTotales = comercioExteriorEJBLocal.consultarProductoPorDocumentoLoteAsignarLotesOIC(this.seleccionado.getId(), this.seleccionado.getCliente().getId());


			if(this.seleccionado.getDocumentoXNegociacions() != null && !this.seleccionado.getDocumentoXNegociacions().isEmpty()){
				this.totalCostoEntrega = this.seleccionado.getDocumentoXNegociacions().get(0).getCostoEntrega();
				this.totalCostoSeguro = this.seleccionado.getDocumentoXNegociacions().get(0).getCostoSeguro();
				this.totalCostoFlete = this.seleccionado.getDocumentoXNegociacions().get(0).getCostoFlete();
				this.totalOtrosGastos = this.seleccionado.getDocumentoXNegociacions().get(0).getOtrosGastos();

				this.totalCostos = totalCostoEntrega.add(totalCostoSeguro).add(totalCostoFlete).add(totalOtrosGastos);

				this.totalPallets = this.seleccionado.getDocumentoXNegociacions().get(0).getTotalPallets();
			}

			this.totalValorNeg = this.totalValorTotal.add(totalCostos);

			productos=comercioExteriorEJBLocal.consultarProductoPorDocumentoGenerarFacturaProforma(this.seleccionado.getId(), this.seleccionado.getCliente().getId());

		}catch(Exception e){

		}
	}


	/**
	 * Gets the lista productos documento.
	 *
	 * @return the lista productos documento
	 */
	public List<ProductosXDocumento> getListaProductosDocumento() {
		return listaProductosDocumento;
	}

	/**
	 * Sets the lista productos documento.
	 *
	 * @param listaProductosDocumento the new lista productos documento
	 */
	public void setListaProductosDocumento(
			List<ProductosXDocumento> listaProductosDocumento) {
		this.listaProductosDocumento = listaProductosDocumento;
	}

	/**
	 * Gets the total cantitad1.
	 *
	 * @return the total cantitad1
	 */
	public BigDecimal getTotalCantitad1() {
		return totalCantitad1;
	}

	/**
	 * Sets the total cantitad1.
	 *
	 * @param totalCantitad1 the new total cantitad1
	 */
	public void setTotalCantitad1(BigDecimal totalCantitad1) {
		this.totalCantitad1 = totalCantitad1;
	}

	/**
	 * Gets the total valor total.
	 *
	 * @return the total valor total
	 */
	public BigDecimal getTotalValorTotal() {
		return totalValorTotal;
	}

	/**
	 * Sets the total valor total.
	 *
	 * @param totalValorTotal the new total valor total
	 */
	public void setTotalValorTotal(BigDecimal totalValorTotal) {
		this.totalValorTotal = totalValorTotal;
	}

	/**
	 * Gets the total cantidad x embalaje.
	 *
	 * @return the total cantidad x embalaje
	 */
	public BigDecimal getTotalCantidadXEmbalaje() {
		return totalCantidadXEmbalaje;
	}

	/**
	 * Sets the total cantidad x embalaje.
	 *
	 * @param totalCantidadXEmbalaje the new total cantidad x embalaje
	 */
	public void setTotalCantidadXEmbalaje(BigDecimal totalCantidadXEmbalaje) {
		this.totalCantidadXEmbalaje = totalCantidadXEmbalaje;
	}

	/**
	 * Gets the total cantidad cajas.
	 *
	 * @return the total cantidad cajas
	 */
	public BigDecimal getTotalCantidadCajas() {
		return totalCantidadCajas;
	}

	/**
	 * Sets the total cantidad cajas.
	 *
	 * @param totalCantidadCajas the new total cantidad cajas
	 */
	public void setTotalCantidadCajas(BigDecimal totalCantidadCajas) {
		this.totalCantidadCajas = totalCantidadCajas;
	}

	/**
	 * Gets the total peso neto.
	 *
	 * @return the total peso neto
	 */
	public BigDecimal getTotalPesoNeto() {
		return totalPesoNeto;
	}

	/**
	 * Sets the total peso neto.
	 *
	 * @param totalPesoNeto the new total peso neto
	 */
	public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
		this.totalPesoNeto = totalPesoNeto;
	}

	/**
	 * Gets the total peso bruto.
	 *
	 * @return the total peso bruto
	 */
	public BigDecimal getTotalPesoBruto() {
		return totalPesoBruto;
	}

	/**
	 * Sets the total peso bruto.
	 *
	 * @param totalPesoBruto the new total peso bruto
	 */
	public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
		this.totalPesoBruto = totalPesoBruto;
	}

	/**
	 * Gets the lista muestras.
	 *
	 * @return the lista muestras
	 */
	public List<Muestrasxlote> getListaMuestras() {
		return listaMuestras;
	}

	/**
	 * Sets the lista muestras.
	 *
	 * @param listaMuestras the new lista muestras
	 */
	public void setListaMuestras(List<Muestrasxlote> listaMuestras) {
		this.listaMuestras = listaMuestras;
	}

	/**
	 * Gets the lista producto totales.
	 *
	 * @return the lista producto totales
	 */
	public List<ProductoLoteAsignarLoteOICDTO> getListaProductoTotales() {
		return listaProductoTotales;
	}

	/**
	 * Sets the lista producto totales.
	 *
	 * @param listaProductoTotales the new lista producto totales
	 */
	public void setListaProductoTotales(
			List<ProductoLoteAsignarLoteOICDTO> listaProductoTotales) {
		this.listaProductoTotales = listaProductoTotales;
	}

	/**
	 * Gets the total costos.
	 *
	 * @return the total costos
	 */
	public BigDecimal getTotalCostos() {
		return totalCostos;
	}

	/**
	 * Sets the total costos.
	 *
	 * @param totalCostos the new total costos
	 */
	public void setTotalCostos(BigDecimal totalCostos) {
		this.totalCostos = totalCostos;
	}

	/**
	 * Gets the total costo entrega.
	 *
	 * @return the total costo entrega
	 */
	public BigDecimal getTotalCostoEntrega() {
		return totalCostoEntrega;
	}

	/**
	 * Sets the total costo entrega.
	 *
	 * @param totalCostoEntrega the new total costo entrega
	 */
	public void setTotalCostoEntrega(BigDecimal totalCostoEntrega) {
		this.totalCostoEntrega = totalCostoEntrega;
	}

	/**
	 * Gets the total costo seguro.
	 *
	 * @return the total costo seguro
	 */
	public BigDecimal getTotalCostoSeguro() {
		return totalCostoSeguro;
	}

	/**
	 * Sets the total costo seguro.
	 *
	 * @param totalCostoSeguro the new total costo seguro
	 */
	public void setTotalCostoSeguro(BigDecimal totalCostoSeguro) {
		this.totalCostoSeguro = totalCostoSeguro;
	}

	/**
	 * Gets the total costo flete.
	 *
	 * @return the total costo flete
	 */
	public BigDecimal getTotalCostoFlete() {
		return totalCostoFlete;
	}

	/**
	 * Sets the total costo flete.
	 *
	 * @param totalCostoFlete the new total costo flete
	 */
	public void setTotalCostoFlete(BigDecimal totalCostoFlete) {
		this.totalCostoFlete = totalCostoFlete;
	}

	/**
	 * Gets the total otros gastos.
	 *
	 * @return the total otros gastos
	 */
	public BigDecimal getTotalOtrosGastos() {
		return totalOtrosGastos;
	}

	/**
	 * Sets the total otros gastos.
	 *
	 * @param totalOtrosGastos the new total otros gastos
	 */
	public void setTotalOtrosGastos(BigDecimal totalOtrosGastos) {
		this.totalOtrosGastos = totalOtrosGastos;
	}

	/**
	 * Gets the total valor neg.
	 *
	 * @return the total valor neg
	 */
	public BigDecimal getTotalValorNeg() {
		return totalValorNeg;
	}

	/**
	 * Sets the total valor neg.
	 *
	 * @param totalValorNeg the new total valor neg
	 */
	public void setTotalValorNeg(BigDecimal totalValorNeg) {
		this.totalValorNeg = totalValorNeg;
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

	/**
	 * Gets the render imprimir factura.
	 *
	 * @return the render imprimir factura
	 */
	public Boolean getRenderImprimirFactura() {
		return renderImprimirFactura;
	}

	/**
	 * Sets the render imprimir factura.
	 *
	 * @param renderImprimirFactura the new render imprimir factura
	 */
	public void setRenderImprimirFactura(Boolean renderImprimirFactura) {
		this.renderImprimirFactura = renderImprimirFactura;
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public List<ProductoGenerarFacturaPFDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoGenerarFacturaPFDTO> productos) {
		this.productos = productos;
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
