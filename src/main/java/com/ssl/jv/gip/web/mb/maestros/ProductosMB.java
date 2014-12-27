package com.ssl.jv.gip.web.mb.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
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

	private List<ProductosInventario> productos;
	private ProductosInventario seleccionado;
	private ProductosInventario filtro;
	private List<Unidad> unidades;
	private List<CuentaContable> cuentasContables;
	private List<CategoriasInventario> categorias;
	
	private Modo modo;
	
	@EJB
	private MaestrosEJBLocal maestrosEjb;
	
	@ManagedProperty(value="#{aplicacionMB}")
	private AplicacionMB appMB;
	
	private Integer language=AplicacionMB.SPANISH;
	
	public ProductosMB(){
		
	}
	
	@PostConstruct
	public void init(){
		productos = new ArrayList<ProductosInventario>();
		categorias = maestrosEjb.consultarCategoriasInventario();
		unidades= maestrosEjb.consultarUnidades();
		cuentasContables = maestrosEjb.consultarCuentasContables();
		filtro = new ProductosInventario();
		filtro.setPais(new Pais());
		filtro.setCategoriasInventario(new CategoriasInventario());
	}

	/*public AdministracionEJB getAdmonEjb() {
		return admonEjb;
	}

	public void setAdmonEjb(AdministracionEJB admonEjb) {
		this.admonEjb = admonEjb;
	}*/

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

	public List<ProductosInventario> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductosInventario> productos) {
		this.productos = productos;
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

	public List<CategoriasInventario> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriasInventario> categorias) {
		this.categorias = categorias;
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
				this.maestrosEjb.crearProductoInventario(this.seleccionado);
				this.productos.add(this.seleccionado);
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
	

}
