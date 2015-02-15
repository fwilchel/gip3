package com.ssl.jv.gip.web.mb.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.Numero_a_Letra_Ingles;


/**
 * The Class SolicitudPedidoMB.
 */
@ManagedBean(name="reporteDocumentosMB")
@ViewScoped
public class ReporteDocumentosMB extends UtilMB{

	/** The lista documentos. */
	private ArrayList<DocumentoIncontermDTO> listaDocumentos = new ArrayList<DocumentoIncontermDTO>();
	
	private List<ProductosXDocumento> listaProductosDocumento;
	
	/** The lista ubicaciones. */
	private List<Ubicacion> listaUbicaciones;

	/** The seleccionado. */
	private DocumentoIncontermDTO seleccionado;
	
	/** The filtro. */
	private FiltroConsultaSolicitudDTO filtro;

	private StreamedContent reporteExcel;
	
	private String strUbicaciones;
	
	private String strUbicacionesUsuario;
	
	private Long intUbicacion;

	/** The comercio ejb. */
	@EJB
	private ComercioExteriorEJB comercioEjb;	
	
	@EJB
	private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	
	/** The menu. */
	@ManagedProperty(value="#{menuMB}")
	 private MenuMB menu;

	/**
	 * Instantiates a new ingresar costos inconterm mb.
	 */
	public ReporteDocumentosMB(){

	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init(){
		filtro = new FiltroConsultaSolicitudDTO();
	}

	/**
	 * Consultar documentos.
	 */
	public void consultarDocumentos(){
		filtro.setUbicaciones(getStrUbicaciones());
		listaDocumentos = (ArrayList<DocumentoIncontermDTO>) comercioEjb.consultarDocumentosGeneral(filtro);		
	}
	


	/**
	 * Gets the seleccionado.
	 *
	 * @return the seleccionado
	 */
	public DocumentoIncontermDTO getSeleccionado() {
		return seleccionado;
	}

	/**
	 * Sets the seleccionado.
	 *
	 * @param seleccionado the new seleccionado
	 */
	public void setSeleccionado(DocumentoIncontermDTO seleccionado) {
		this.seleccionado = seleccionado;
	}

	/**
	 * Consultar solicitud pedido.
	 *
	 * @return the string
	 */
	public String consultarSolicitudPedido(){
		//Consultar la lista inconterm poor cliente
		listaProductosDocumento = this.reportesComercioExteriorEJBLocal.consultarProductosPorDocumento(seleccionado.getIdDocumento());

		return "";
	}
	
	/**
	 * Cancelar.
	 */
	public void cancelar(){
		listaProductosDocumento = null;
		seleccionado = new DocumentoIncontermDTO();
	}


	/**
	 * Gets the lista documentos.
	 *
	 * @return the lista documentos
	 */
	public ArrayList<DocumentoIncontermDTO> getListaDocumentos() {
		return listaDocumentos;
	}

	/**
	 * Sets the lista documentos.
	 *
	 * @param listaDocumentos the new lista documentos
	 */
	public void setListaDocumentos(ArrayList<DocumentoIncontermDTO> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	/**
	 * Gets the menu.
	 *
	 * @return the menu
	 */
	public MenuMB getMenu() {
		return menu;
	}

	/**
	 * Sets the menu.
	 *
	 * @param menu the new menu
	 */
	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	/**
	 * Gets the filtro.
	 *
	 * @return the filtro
	 */
	public FiltroConsultaSolicitudDTO getFiltro() {
		return filtro;
	}

	/**
	 * Sets the filtro.
	 *
	 * @param filtro the new filtro
	 */
	public void setFiltro(FiltroConsultaSolicitudDTO filtro) {
		this.filtro = filtro;
	}


	public StreamedContent getReporteExcel() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		 SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		 String fechaStringGeneracion = ft.format(seleccionado.getFechaGeneracion());

		  parametros.put("cliente",seleccionado.getClientesNombre());		 
		  parametros.put("nit",seleccionado.getClientesNit());
		  parametros.put("ciudad",seleccionado.getLugarIncoterm());		 
		  parametros.put("direccion",seleccionado.getClientesDireccion());
		  parametros.put("telefono",seleccionado.getClientesTelefono());
		  parametros.put("contacto",seleccionado.getClientesContacto());
		  parametros.put("documento",seleccionado.getDocumentoCliente());
		  parametros.put("fecha",fechaStringGeneracion);
		  parametros.put("numFactura", seleccionado.getConsecutivoDocumento());
		  parametros.put("dblCantidadContenedores40", seleccionado.getCantidadContenedores40());
		  parametros.put("solicitud", seleccionado.getClientesNombre());
		  parametros.put("observacionDoc",seleccionado.getObservacionDocumento());
		  parametros.put("observacionMar", seleccionado.getObservacionesMarcacion2());
		  parametros.put("strLugarIncoterm", seleccionado.getLugarIncoterm());
		  parametros.put("strNombreIncoterm", seleccionado.getDescripcionTerminoIncoterm());
		  parametros.put("dblCantidadContenedores20", seleccionado.getCantidadContenedores20());
		  parametros.put("dtmFechaDespacho",seleccionado.getFechaEsperadaEntrega());
		  parametros.put("incoterm", seleccionado.getDescripcionTerminoIncoterm());
		  parametros.put("lugarIncoterm", "(" + seleccionado.getLugarIncoterm() + ")");
		  
		
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaProductosDocumento);
		try {
			
			Hashtable<String, String> parametrosR=new Hashtable<String, String>();
			parametrosR.put("tipo", "xls");
			String reporte=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_SP.jasper");
			ByteArrayOutputStream os=(ByteArrayOutputStream)com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
			reporteExcel = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel", "SolicitudPedido"+seleccionado.getConsecutivoDocumento()+".xls");
			
		} catch (Exception e) {
			this.addMensajeError(e);
		}
		return reporteExcel;
	}

	public void setReporteExcel(StreamedContent reporteExcel) {
		this.reporteExcel = reporteExcel;
	}

	public List<Ubicacion> getListaUbicaciones() {
		if(listaUbicaciones==null){
			strUbicaciones = "";
			listaUbicaciones = comercioEjb.consultarUbicacionesPorUsuario(menu.getUsuario().getId());
			for(Ubicacion ubicacion: listaUbicaciones){
				if (strUbicaciones.length() == 0){
					strUbicaciones = " " + ubicacion.getId();
				}				
				else{
					strUbicaciones += "," + ubicacion.getId();
				}					
			}
			strUbicacionesUsuario = strUbicaciones;
		}
		return listaUbicaciones;
	}

	public void setListaUbicaciones(List<Ubicacion> listaUbicaciones) {
		this.listaUbicaciones = listaUbicaciones;
	}

	public String getStrUbicaciones() {
		return strUbicaciones;
	}

	public void setStrUbicaciones(String strUbicaciones) {
		this.strUbicaciones = strUbicaciones;
	}

	public String getStrUbicacionesUsuario() {
		return strUbicacionesUsuario;
	}

	public void setStrUbicacionesUsuario(String strUbicacionesUsuario) {
		this.strUbicacionesUsuario = strUbicacionesUsuario;
	}

	public Long getIntUbicacion() {
		return intUbicacion;
	}

	public void setIntUbicacion(Long intUbicacion) {
		this.intUbicacion = intUbicacion;
		/*
		 * Si la ubicación escogida es diferente a Todas, la asignamos a
		 * strUbicaciones De lo contrario asignamos a strUbicaciones la lista de
		 * ubicaciones a las cuales tiene acceso el usuario
		 */
		if (intUbicacion > 0)
			strUbicaciones = " " + intUbicacion;
		else
			strUbicaciones = strUbicacionesUsuario;
	}

	public List<ProductosXDocumento> getListaProductosDocumento() {
		return listaProductosDocumento;
	}

	public void setListaProductosDocumento(
			List<ProductosXDocumento> listaProductosDocumento) {
		this.listaProductosDocumento = listaProductosDocumento;
	}

	


}
