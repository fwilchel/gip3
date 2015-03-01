package com.ssl.jv.gip.web.mb.devoluciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.FiltroProductoDTO;
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
	
	private List <ProductosInventario> listaProductos;
	
	private List <Ubicacion> listaUbicaciones;
	
	private Long ubicacionSeleccionada;
	
	@PostConstruct
	public void init() {
		this.listaCategorias = maestrosEjb.consultarCategoriasInventario();
		this.listaUbicaciones = maestrosEjb.consultarUbicaciones();
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


	public List<ProductosInventario> getListaProductos() {
		return listaProductos;
	}


	public void setListaProductos(List<ProductosInventario> listaProductos) {
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
	
	
	

}
