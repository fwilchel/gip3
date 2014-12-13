package com.ssl.jv.gip.web.mb.administracion;

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
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="usuariosMB")
@SessionScoped
public class UsuariosMB extends UtilMB{
	
	private List<Usuario> usuarios;
	private Usuario seleccionado;
	private List<Rol> roles;
	
	private Modo modo;
	
	@EJB
	private AdministracionEJB admonEjb;	
	
	public UsuariosMB(){
		
	}
	
	@PostConstruct
	public void init(){
		usuarios = admonEjb.consultarUsuarios();
		roles = admonEjb.consultarRoles();
		Collections.sort(roles);
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Usuario getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Usuario seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
	
	public void nuevo(){
		seleccionado=new Usuario();
		seleccionado.setRole(new Rol());
		seleccionado.setPais(new Pais());
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
		if (this.modo.equals(Modo.CREACION)){
			this.seleccionado.setFechaIngreso(new Timestamp(new Date().getTime()));
			this.admonEjb.crearUsuario(this.seleccionado);
		}else{
			this.admonEjb.actualizarUsuario(this.seleccionado);
		}
		
		this.addMensajeInfo("Usuario almacenado exitosamente");
	}
	
	public boolean isCreacion(){
		if (this.modo!=null && this.modo.equals(Modo.CREACION)){
			return true;
		}else{
			return false;
		}
	}

}
