package com.ssl.jv.gip.web.mb.administracion;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Rol;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJB;
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
 * @author Fredy Giovanny Wilches L�pez
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name="usuariosMB")
@SessionScoped
public class UsuariosMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780795923623719268L;

	private static final Logger LOGGER = Logger.getLogger(UsuariosMB.class);

	private List<Usuario> usuarios;
	private Usuario seleccionado;
	private List<Rol> roles;
	
	private Modo modo;
	
	@EJB
	private AdministracionEJB admonEjb;
	
	@ManagedProperty(value="#{aplicacionMB}")
	private AplicacionMB appMB;
	
	private Integer language=AplicacionMB.SPANISH;
	
	public UsuariosMB(){
		
	}
	
	@PostConstruct
	public void init(){
		usuarios = admonEjb.consultarUsuarios();
		roles = admonEjb.consultarRoles();
		Collections.sort(roles);
	}

	public AdministracionEJB getAdmonEjb() {
		return admonEjb;
	}

	public void setAdmonEjb(AdministracionEJB admonEjb) {
		this.admonEjb = admonEjb;
	}

	public AplicacionMB getAppMB() {
		return appMB;
	}

	public void setAppMB(AplicacionMB appMB) {
		this.appMB = appMB;
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
		seleccionado.setIntentos(0L);
		this.modo=Modo.CREACION;
	}
	
	
	
	public void guardar(){
		try{
			this.seleccionado.setPais(this.appMB.getPais(this.seleccionado.getPais().getId()));
			this.seleccionado.setRole(this.getRol(this.seleccionado.getRole().getIdRol()));
			if (this.modo.equals(Modo.CREACION)){
				this.seleccionado.setFechaIngreso(new Timestamp(new Date().getTime()));
				this.admonEjb.crearUsuario(this.seleccionado);
				this.usuarios.add(this.seleccionado);
				this.modo = Modo.EDICION;
			}else{
				this.admonEjb.actualizarUsuario(this.seleccionado);
			}
			
			this.addMensajeInfo(AplicacionMB.getMessage("UsuarioExitoPaginaTexto", language));
		}catch(EJBTransactionRolledbackException e){
			if (this.isException(e, "usuarios_pkey")){
				this.addMensajeError("formaDlg:identificacion", AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
			}else if (this.isException(e, "un_usuarios_1")){
				this.addMensajeError("formaDlg:email", AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
			}else{
				this.addMensajeError(AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
			}
			LOGGER.error(e);
		}catch(Exception e){
			this.addMensajeError(AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
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
	
	public Rol getRol(Long id){
		for (Rol r:this.roles){
			if (r.getIdRol().equals(id))
				return r;
		}
		return null;
	}

}
