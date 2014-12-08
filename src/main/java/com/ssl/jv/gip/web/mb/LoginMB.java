package com.ssl.jv.gip.web.mb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJB;
import com.ssl.jv.gip.web.util.Utilidad;


@ManagedBean(name="loginMB")
@SessionScoped
public class LoginMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1947610603374558443L;

	@EJB
	private AdministracionEJB admonEjb;
	
	private String password;
	private Integer empresa;
	private String email;
	
	@Inject
	private MenuMB menu;

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}
	
	@PostConstruct
	public void init(){
		this.empresa = 1;
	}
	

	public String ingresar(){
		Usuario u=this.admonEjb.findUsuarioByEmail(this.email);
		if (u!=null){
			String pwd=Utilidad.encriptar(this.password);
			if (u.getContrasena().equals(pwd)){
				
				if (u.getRole()==null){
					this.addMensajeError("El usuario no tiene perfil asociado");
					return null;
				}
				if (u.getActivo()==false){
					this.addMensajeError("El usuario está inactivo");
					return null;
				}
				menu.setMenu(admonEjb.getMenu(u.getEmail()));
				menu.setUsuario(u);
				return "introduccion";
				//MenuMB menu=(MenuMB)Utilidades.getManagedBean("menuMB");
				//Aplicacion app=(Aplicacion)Utilidades.getManagedBean("aplicacionMB");
			}else{
				this.addMensajeError("Contraseña inválida");
				return null;
			}
		}else{
			this.addMensajeError("Usuario inexistente");
			return null;
		}
	}
	
	public static void main(String args[]){

		
		
	}

}
