package com.ssl.jv.gip.web.mb.maestros;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="agenciaCargaMB")
@ViewScoped
public class AgenciaCargaMB extends UtilMB {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<AgenciaCarga> agencias;
	private AgenciaCarga seleccionado;
	private AgenciaCarga filtro;
	
	@EJB
	private MaestrosEJB servicio;
	
	private Modo modo;
	
	public AgenciaCargaMB(){}
	
	@PostConstruct
	public void init(){
		agencias = servicio.consultarAgenciasCarga();
	}
	
	public void setSeleccionado(AgenciaCarga seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
	
	public AgenciaCarga getSeleccionado() {
		return seleccionado;
	}
	
	public void nuevo(){
		seleccionado=new AgenciaCarga();
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
		if (this.modo.equals(Modo.CREACION)){
			this.servicio.crearAgenciaCarga(this.seleccionado);
		}else{
			this.servicio.actualizarAgenciaCarga(this.seleccionado);
		}
		
		this.addMensajeInfo("Agencia de Carga almacenada exitosamente");
	}
	
	public boolean isCreacion(){
		if (this.modo!=null && this.modo.equals(Modo.CREACION)){
			return true;
		}else{
			return false;
		}
	}

	public List<AgenciaCarga> getAgencias() {
		return agencias;
	}

	public void setAgencias(List<AgenciaCarga> agencias) {
		this.agencias = agencias;
	}


	public AgenciaCarga getFiltro() {
		return filtro;
	}

	public void setFiltro(AgenciaCarga filtro) {
		this.filtro = filtro;
	}	
	
	

}
