package com.ssl.jv.gip.web.mb.maestros;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Rol;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJB;
import com.ssl.jv.gip.negocio.ejb.ComunEJB;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="ubicacionMB")
@SessionScoped
public class UbicacionMB extends UtilMB{
	
	private List<Ubicacion> ubicacion;
	private Ubicacion seleccionado;
	
	private List<Pais> paises;
	
	private Modo modo;
	
	@EJB
	private MaestrosEJB maestroEjb;	
	
	@EJB
	private ComunEJB comunEJB;
	
	public UbicacionMB(){
		
	}
	
	@PostConstruct
	public void init(){
		ubicacion = maestroEjb.consultarUbicaciones();
		paises = comunEJB.consultarPaises();
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public Ubicacion getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Ubicacion seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
	
	public void nuevo(){
		seleccionado=new Ubicacion();
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
		if (this.modo.equals(Modo.CREACION)){
			this.maestroEjb.crearUbicacion(this.seleccionado);
		}else{
			this.maestroEjb.actualizarUbicacion(this.seleccionado);
		}
		
		this.addMensajeInfo("Ubicacion almacenado exitosamente");
	}
	
	public boolean isCreacion(){
		if (this.modo!=null && this.modo.equals(Modo.CREACION)){
			return true;
		}else{
			return false;
		}
	}

	public List<Ubicacion> getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(List<Ubicacion> ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

}
