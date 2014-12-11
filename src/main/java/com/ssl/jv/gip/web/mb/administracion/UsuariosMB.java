package com.ssl.jv.gip.web.mb.administracion;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="usuariosMB")
@SessionScoped
public class UsuariosMB extends UtilMB{
	
	private List<Usuario> usuarios;
	private Usuario seleccionado;
	
	private Modo modo;
	
	@EJB
	private AdministracionEJB admonEjb;	
	
	public UsuariosMB(){
		
	}
	
	
	@PostConstruct
	public void init(){
		usuarios = admonEjb.consultarUsuarios();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
		if (this.modo.equals(Modo.CREACION)){
			this.admonEjb.crearUsuario(this.seleccionado);
		}else{
			this.admonEjb.actualizarUsuario(this.seleccionado);
		}
		
		this.addMensajeInfo("Usuario almacenado exitosamente");
	}
	
	

}
