package com.ssl.jv.gip.web.mb.maestros;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ssl.jv.gip.jpa.pojo.LugarIncoterm;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="lugarIncotermMB")
@SessionScoped
public class LugarIncotermMB extends UtilMB{
	
	private List<LugarIncoterm> lugarIncoterm;
	private LugarIncoterm seleccionado;
	
	private Modo modo;
	
	@EJB
	private MaestrosEJB maestroEjb;	
	
	public LugarIncotermMB(){
		
	}
	
	@PostConstruct
	public void init(){
		lugarIncoterm = maestroEjb.consultarLugarIncoterm();
	}
	
	public String modificar(){
		
		return "";
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public LugarIncoterm getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(LugarIncoterm seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
	
	public void nuevo(){
		seleccionado=new LugarIncoterm();
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
		if (this.modo.equals(Modo.CREACION)){
			this.maestroEjb.crearLugarIncoterm(this.seleccionado);
		}else{
			this.maestroEjb.actualizarLugarIncoterm(this.seleccionado);
		}
		
		this.addMensajeInfo("LugarIncoterm almacenado exitosamente");
	}
		
	public boolean isCreacion(){
		if (this.modo!=null && this.modo.equals(Modo.CREACION)){
			return true;
		}else{
			return false;
		}
	}

	public List<LugarIncoterm> getLugarIncoterm() {
		return lugarIncoterm;
	}

	public void setLugarIncoterm(List<LugarIncoterm> lugarIncoterm) {
		this.lugarIncoterm = lugarIncoterm;
	}

}
