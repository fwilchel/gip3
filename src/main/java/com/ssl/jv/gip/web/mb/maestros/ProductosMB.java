package com.ssl.jv.gip.web.mb.maestros;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.TipoLoteoic;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**
 * <p>Title: GIP</p>
 *
 * <p>Description: GIP</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Fredy Giovanny Wilches López
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name="productosMB")
@SessionScoped
public class ProductosMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780795923623719268L;

	private static final Logger LOGGER = Logger.getLogger(ProductosMB.class);

	private LazyDataModel<ProductosInventario> modelo;
	private ProductosInventario seleccionado;
	private ProductosInventarioComext seleccionado2;
	private ProductosInventario filtro;
	private List<Unidad> unidades;
	private List<CuentaContable> cuentasContables;
	private List<SelectItem> categorias;
	private List<TipoLoteoic> tiposLotesOic;
	private String campoOrden;
	private SortOrder orden;
	
	private Modo modo;
	private Modo modoDetalle;
	
	@EJB
	private MaestrosEJBLocal maestrosEjb;
	
	@ManagedProperty(value="#{aplicacionMB}")
	private AplicacionMB appMB;
	
	private Integer language=AplicacionMB.SPANISH;
	private StreamedContent reporteExcel;
	
	public ProductosMB(){
		
	}
	
	@PostConstruct
	public void init(){
		modelo = new LazyProductsDataModel();
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
		cuentasContables = maestrosEjb.consultarCuentasContables();
		filtro = new ProductosInventario();
		filtro.setPais(new Pais());
		filtro.setCategoriasInventario(new CategoriasInventario());
		filtro.setDesactivado(true);
		this.tiposLotesOic = this.maestrosEjb.consultarTipoLotesOic();
	}

	public AplicacionMB getAppMB() {
		return appMB;
	}

	public void setAppMB(AplicacionMB appMB) {
		this.appMB = appMB;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public ProductosInventario getFiltro() {
		return filtro;
	}

	public void setFiltro(ProductosInventario filtro) {
		this.filtro = filtro;
	}

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	public List<CuentaContable> getCuentasContables() {
		return cuentasContables;
	}

	public void setCuentasContables(List<CuentaContable> cuentasContables) {
		this.cuentasContables = cuentasContables;
	}

	public MaestrosEJBLocal getMaestrosEjb() {
		return maestrosEjb;
	}

	public void setMaestrosEjb(MaestrosEJBLocal maestrosEjb) {
		this.maestrosEjb = maestrosEjb;
	}

	public ProductosInventario getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(ProductosInventario seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
		this.seleccionado2 = this.maestrosEjb.consultarProductoInventarioComext(this.seleccionado.getSku());
		this.modoDetalle = Modo.EDICION;
		if (seleccionado2==null){
			this.seleccionado2 = new ProductosInventarioComext();
			this.seleccionado2.setCuentaContable(new CuentaContable());
			this.seleccionado2.setTipoLoteoic(new TipoLoteoic());
			this.seleccionado2.setUnidadEmbalaje(new Unidad());
			this.seleccionado2.setIdProducto(this.seleccionado.getId());
			this.modoDetalle = Modo.CREACION;
		}
		if (seleccionado2.getUnidadEmbalaje()==null)
			this.seleccionado2.setUnidadEmbalaje(new Unidad());
	}
	
	public LazyDataModel<ProductosInventario> getModelo() {
		return modelo;
	}

	public void setModelo(LazyDataModel<ProductosInventario> modelo) {
		this.modelo = modelo;
	}

	public ProductosInventarioComext getSeleccionado2() {
		return seleccionado2;
	}

	public void setSeleccionado2(ProductosInventarioComext seleccionado2) {
		this.seleccionado2 = seleccionado2;
	}

	public List<TipoLoteoic> getTiposLotesOic() {
		return tiposLotesOic;
	}

	public void setTiposLotesOic(List<TipoLoteoic> tiposLotesOic) {
		this.tiposLotesOic = tiposLotesOic;
	}

	public void nuevo(){
		seleccionado=new ProductosInventario();
		seleccionado.setCategoriasInventario(new CategoriasInventario());
		seleccionado.setUnidadDespacho(new Unidad());
		seleccionado.setUnidadVenta(new Unidad());
		seleccionado.setUnidadReceta(new Unidad());
		seleccionado.setPais(new Pais());
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
		try{
			this.seleccionado.setPais(this.appMB.getPais(this.seleccionado.getPais().getId()));
			if (this.modo.equals(Modo.CREACION)){
				this.seleccionado.setAbc("A");
				this.seleccionado.setFactorUdUv(new BigDecimal(1));
				this.maestrosEjb.crearProductoInventario(this.seleccionado);
				this.modo = Modo.EDICION;
			}else{
				this.maestrosEjb.actualizarProductoInventario(this.seleccionado);
			}
			
			this.addMensajeInfo(AplicacionMB.getMessage("UsuarioExitoPaginaTexto", language));
		}catch(EJBTransactionRolledbackException e){
			if (this.isException(e, "productosinventario_pkey")){
				this.addMensajeError("formaDlg:codigo", AplicacionMB.getMessage("MaestroInventarioErrorPaginaBotonYaExiste", language));
			}else{
				this.addMensajeError(AplicacionMB.getMessage("MaestroInventarioErrorPaginaBoton", language));
			}
			LOGGER.error(e);
		}catch(Exception e){
			this.addMensajeError(AplicacionMB.getMessage("MaestroInventarioErrorPaginaBoton", language));
			LOGGER.error(e);
			
		}
	}
	
	public boolean isCreacion(){
		if (this.modo!=null && this.modo.equals(Modo.CREACION)){
			return true;
		}else{
			return false;
		}
	}

	public List<SelectItem> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<SelectItem> categorias) {
		this.categorias = categorias;
	}
	
	public void guardarDetalle(){
		if (this.seleccionado2.getFechaCreado()==null)
			this.seleccionado2.setFechaCreado(new Date());
		if (this.modoDetalle.equals(Modo.CREACION))
			this.maestrosEjb.crearProductoInventarioComext(this.seleccionado2);
		else
			this.maestrosEjb.actualizarProductoInventarioComext(this.seleccionado2);
		this.addMensajeInfo(AplicacionMB.getMessage("MaestroInventarioExitoPaginaBoton", language));
		
	}
	
	private class LazyProductsDataModel extends LazyDataModel<ProductosInventario>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 283497341126330045L;
		private List<ProductosInventario> datos;
		
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
	
	
}
