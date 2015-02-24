package com.ssl.jv.gip.web.mb.maestros;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.exportarExcel;

@ManagedBean(name="maestroInventarioMB")
@SessionScoped
public class ListadoMaestroInventarioMB extends UtilMB{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ProductosMB.class);

	private ProductosInventario filtro;
	private LazyDataModel<ProductosInventario> modelo;
	private String campoOrden;
	private SortOrder orden;
	private List<SelectItem> categorias;
	private ProductosInventario seleccionado;
	private List<Unidad> unidades;
	private StreamedContent reporteExcel;
	
	@EJB
	private MaestrosEJBLocal maestrosEjb;
	
	@ManagedProperty(value="#{aplicacionMB}")
	private AplicacionMB appMB;
	
	@PostConstruct
	public void init(){
		filtro = new ProductosInventario();
		filtro.setPais(new Pais());
		filtro.setCategoriasInventario(new CategoriasInventario());
		filtro.setDesactivado(true);
		List<CategoriasInventario> categorias = maestrosEjb.consultarCategoriasInventario();
		this.categorias = new ArrayList<SelectItem>();
		for (CategoriasInventario ci:categorias){
			SelectItemGroup sig=new SelectItemGroup(ci.getNombre());
			this.categorias.add(sig);
			SelectItem hijos[]=new SelectItem[ci.getCategoriasInventarios().size()];
			for (int i=0; i<hijos.length; i++){
				hijos[i]=new SelectItem(ci.getCategoriasInventarios().get(i).getId(), ci.getCategoriasInventarios().get(i).getNombre());
			}
			sig.setSelectItems(hijos);
		}
		unidades= maestrosEjb.consultarUnidades();
	}
	
	public AplicacionMB getAppMB() {
		return appMB;
	}

	public void setAppMB(AplicacionMB appMB) {
		this.appMB = appMB;
	}
	
	public void loadProductos(){
		modelo = new LazyProductsDataModel();
	}

	public ProductosInventario getFiltro() {
		return filtro;
	}

	public void setFiltro(ProductosInventario filtro) {
		this.filtro = filtro;
	}
	public LazyDataModel<ProductosInventario> getModelo() {
		return modelo;
	}
	
	public void setModelo(LazyDataModel<ProductosInventario> modelo) {
		this.modelo = modelo;
	}
	
	public String getCampoOrden() {
		return campoOrden;
	}
	
	public void setCampoOrden(String campoOrden) {
		this.campoOrden = campoOrden;
	}
	
	public SortOrder getOrden() {
		return orden;
	}
	
	public void setOrden(SortOrder orden) {
		this.orden = orden;
	}
	
	public List<SelectItem> getCategorias() {
		return categorias;
	}
	
	public void setCategorias(List<SelectItem> categorias) {
		this.categorias = categorias;
	}
	
	public ProductosInventario getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(ProductosInventario selccionado) {
		this.seleccionado = selccionado;
	}



	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}
	
	public StreamedContent getReporteExcel() {
		try {
			Object rta[]=maestrosEjb.consultarProductos(filtro, 0, 10, campoOrden, orden, true);
			Long cuantos=(Long)rta[0];
			rta=maestrosEjb.consultarProductos(filtro, 0, cuantos.intValue(), campoOrden, orden, false);
			List<ProductosInventario> datos=(List<ProductosInventario>)rta[1];
			Hashtable<String, String> parametrosR=new Hashtable<String, String>();
			parametrosR.put("tipo", "jxls");
			 
			Map<String, Object> parametrosReporte=new HashMap<String, Object>();
			parametrosReporte.put("datos", datos);
			
			if (this.filtro.getPais()!=null && this.filtro.getPais().getId()!=null){
				filtro.setPais(this.appMB.getPais(filtro.getPais().getId()));
			}
			if (this.filtro.getCategoriasInventario()!=null && this.filtro.getCategoriasInventario().getId()!=null){
				fg:for (SelectItem sig:this.categorias){
					SelectItemGroup grupo=(SelectItemGroup)sig;
					for (SelectItem si:grupo.getSelectItems()){
						if (((Long)si.getValue()).equals(this.filtro.getCategoriasInventario().getId())){
							this.filtro.getCategoriasInventario().setNombre(si.getLabel());
							break fg;
						}
					}
				}
			}
			
			parametrosReporte.put("filtro", filtro);
			parametrosReporte.put("fecha", new Date());
			
			String reporte=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/maestroInventario.xls");
			ByteArrayOutputStream os=(ByteArrayOutputStream)com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametrosReporte, null);
			reporteExcel = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel ", "maestroInventario.xls");
			
		} catch (Exception e) {
			this.addMensajeError(e);
		}
		return this.reporteExcel;
	}
	
	public void setReporteExcel(StreamedContent reporteExcel) {
		this.reporteExcel = reporteExcel;
	}

	private class LazyProductsDataModel extends LazyDataModel<ProductosInventario>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 283497341126330045L;
		private List<ProductosInventario> datos = null;
		
		@Override
	    public Object getRowKey(ProductosInventario pi) {
	        return pi.getId();
	    }
		
		@Override
	    public ProductosInventario getRowData(String rowKey) {
	        for(ProductosInventario pi : datos) {
	            if(pi.getId().toString().equals(rowKey))
	                return pi;
	        }
	        return null;
	    }
		
		public List<ProductosInventario> getDatos(){
			return datos;
		}

		
		@Override
	    public List<ProductosInventario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
			campoOrden = sortField;
			orden=sortOrder;
			Object rta[]=maestrosEjb.consultarProductos(filtro, first, pageSize, sortField, sortOrder, true);
			this.setRowCount(((Long)rta[0]).intValue());
			rta=maestrosEjb.consultarProductos(filtro, first, pageSize, sortField, sortOrder, false);
			datos=(List<ProductosInventario>)rta[1];
			return datos;
		}

	}
}
