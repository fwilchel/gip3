package com.ssl.jv.gip.web.mb.devoluciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.FiltroProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDevolucionDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.negocio.ejb.DevolucionesEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;

@ManagedBean(name = "devolucionClienteTiendaMB")
@ViewScoped
public class DevolucionClienteTiendaMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4547719350295240598L;
	
	private static final Logger LOGGER = Logger
			.getLogger(DevolucionClienteTiendaMB.class);
	
	@EJB
	private DevolucionesEJBLocal devolucionesEJBLocal;
	
	/** The comercio ejb. */
	@EJB
	private ComercioExteriorEJB comercioEjb;	
	
	@EJB
	private MaestrosEJBLocal maestrosEjb;
	
	/** The menu. */
	@ManagedProperty(value="#{menuMB}")
	private MenuMB menu;
	
	private Date fechaActual;
	
	
	private List<CategoriasInventario> listaCategorias;
	
	private List <ProductoDevolucionDTO> listaProductos;
	
	private List <ProductoDevolucionDTO> listaProductosSeleccionados;
	
	private List <Ubicacion> listaUbicaciones;
	
	private Long ubicacionSeleccionada;
	
	private List<SelectItem> categoriasInventarios;
	
	@PostConstruct
	public void init() {
		this.cargarCategoriasInventario();
		this.listaUbicaciones = devolucionesEJBLocal.consultarUbicacionesOrdenadas();
		fechaActual = new Date();
	}
	
	
	public void consultarProductos(){
		List <Ubicacion> ubicacionesUsuario = comercioEjb.consultarUbicacionesPorUsuario(menu.getUsuario().getId());
		List<Long> idsUbicacion = new ArrayList<Long>();
		
		for(Ubicacion ubicacion : ubicacionesUsuario){
			idsUbicacion.add(ubicacion.getId());
			
		}
		
		List <Ubicacion> ubicaciones = devolucionesEJBLocal.consultarPorIds(idsUbicacion);
		
		List<String> paises = new ArrayList<String>();
		boolean todosPaises = false;
		
		for(Ubicacion ubicacion : ubicaciones){
			
			if(ubicacion.getRegione().getPais().getId().equals("PAISES_TODOS")){
				todosPaises = true;
			}else{
				paises.add(ubicacion.getRegione().getPais().getId());
			}
			
		}
		
		if(todosPaises){
			this.listaProductos = devolucionesEJBLocal.consultarProductosActivos();
		}else{
			this.listaProductos = devolucionesEJBLocal.consultarProductosInventarioPorPais(paises);
		}		
		
	}
	
	
	public void seleccionarProductos(){
		this.listaProductosSeleccionados = new ArrayList<ProductoDevolucionDTO>();
		for(ProductoDevolucionDTO prod :listaProductos){
			if(prod.isIncluido()){
				this.listaProductosSeleccionados.add(prod);
			}
		}
	}
	
	
	
	
	
	private void cargarCategoriasInventario() {
		List<CategoriasInventario> categorias = maestrosEjb
				.consultarCategoriasInventarios();
		SelectItemGroup group = null;
		categoriasInventarios = new ArrayList<SelectItem>();
		for (CategoriasInventario categoriasInventario : categorias) {
			group = new SelectItemGroup(categoriasInventario.getNombre());
			group.setSelectItems(getSelectItems(categoriasInventario
					.getCategoriasInventarios()));
			categoriasInventarios.add(group);
		}

	}

	private SelectItem[] getSelectItems(
			List<CategoriasInventario> categoriasInventarios) {
		List<SelectItem> items = new ArrayList<SelectItem>();
		SelectItem item = null;
		for (CategoriasInventario categoriasInventario : categoriasInventarios) {
			item = new SelectItem(categoriasInventario.getNombre(),
					categoriasInventario.getNombre());
			items.add(item);
		}
		return items.toArray(new SelectItem[categoriasInventarios.size()]);
	}




	public List<CategoriasInventario> getListaCategorias() {
		return listaCategorias;
	}


	public void setListaCategorias(List<CategoriasInventario> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}


	public Date getFechaActual() {
		return fechaActual;
	}


	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}


	public List<ProductoDevolucionDTO> getListaProductos() {
		return listaProductos;
	}


	public void setListaProductos(List<ProductoDevolucionDTO> listaProductos) {
		this.listaProductos = listaProductos;
	}


	public List<Ubicacion> getListaUbicaciones() {
		return listaUbicaciones;
	}


	public void setListaUbicaciones(List<Ubicacion> listaUbicaciones) {
		this.listaUbicaciones = listaUbicaciones;
	}


	public Long getUbicacionSeleccionada() {
		return ubicacionSeleccionada;
	}


	public void setUbicacionSeleccionada(Long ubicacionSeleccionada) {
		this.ubicacionSeleccionada = ubicacionSeleccionada;
	}


	public MenuMB getMenu() {
		return menu;
	}


	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}


	public List<SelectItem> getCategoriasInventarios() {
		return categoriasInventarios;
	}


	public void setCategoriasInventarios(List<SelectItem> categoriasInventarios) {
		this.categoriasInventarios = categoriasInventarios;
	}


	public List<ProductoDevolucionDTO> getListaProductosSeleccionados() {
		return listaProductosSeleccionados;
	}


	public void setListaProductosSeleccionados(
			List<ProductoDevolucionDTO> listaProductosSeleccionados) {
		this.listaProductosSeleccionados = listaProductosSeleccionados;
	}
	
	
	

}
