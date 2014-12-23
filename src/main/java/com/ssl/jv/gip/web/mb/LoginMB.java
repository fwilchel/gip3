package com.ssl.jv.gip.web.mb;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJB;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJBLocal;
import com.ssl.jv.gip.web.util.Utilidad;


@ManagedBean(name="loginMB")
@SessionScoped
public class LoginMB extends UtilMB {

	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(LoginMB.class);
	private Integer language=AplicacionMB.SPANISH;
	
	private static final long serialVersionUID = -1947610603374558443L;

	@EJB
	private AdministracionEJBLocal admonEjb;
	
	private String password;
	private Integer empresa;
	private String email;
	
	@ManagedProperty(value="#{menuMB}")
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
		
		int NUMERO_MAX_LOGIN=3;
		
		HttpServletRequest request = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
		String remoteAddr = request.getRemoteAddr();
		String HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";
		String x;
		String IP = request.getHeader("X-Forwarded-For");
		  
		if ((x = request.getHeader(HEADER_X_FORWARDED_FOR)) != null) {
		    remoteAddr = x;
		    int idx = remoteAddr.indexOf(',');
		    if (idx > -1) {
		        remoteAddr = remoteAddr.substring(0, idx);
		    }
		}
		
		LOGGER.info("|Client IP address=|"+remoteAddr+" |Identificacion=|"+email);
		
		Usuario u=this.admonEjb.findUsuarioByEmail(this.email);
		if (u!=null){
			String pwd=Utilidad.cifrar(this.password);
			if (u.getContrasena().equals(pwd)){
				
				if (u.getRole()==null){
					this.addMensajeError(AplicacionMB.getMessage("usuarioSinRol", language));
					return null;
				}
				if (u.getActivo()==false){
					this.addMensajeError(AplicacionMB.getMessage("usuarioInactivo", language));
					return null;
				}
				if (u.getIntentos()>=NUMERO_MAX_LOGIN){
					this.addMensajeError(AplicacionMB.getMessage("cuentaBloqueada", language));
					return null;
				}
				
				u.setIntentos(0L);
				this.admonEjb.actualizarUsuario(u);

				menu.setMenu(admonEjb.getMenu(u.getEmail()));
				menu.setUsuario(u);
				menu.setLanguage(language);
				return "introduccion";
				//MenuMB menu=(MenuMB)Utilidades.getManagedBean("menuMB");
				//Aplicacion app=(Aplicacion)Utilidades.getManagedBean("aplicacionMB");
			}else{
				u.setIntentos(u.getIntentos()+1);
				this.admonEjb.actualizarUsuario(u);
				this.addMensajeError(AplicacionMB.getMessage("vuelvaIntentarlo", language));
				return null;
			}
		}else{
			LOGGER.warn("|Client IP address=|"+remoteAddr+" |Identificacion=|"+email+" "+AplicacionMB.getMessage("vuelvaIntentarlo", language));
			this.addMensajeError(AplicacionMB.getMessage("vuelvaIntentarlo", language));
			return null;
		}
	}

	public String recordarContrasena() {
		Usuario usuario = null;
		usuario = this.admonEjb.findUsuarioByEmail(this.email);
		if (usuario != null) {
			this.admonEjb.enviarEmail(usuario.getEmail(), Utilidad.descifrar(usuario
					.getContrasena()), usuario.getNombre(), usuario
					.getApellidos());
			return "login";

		} else {
			this.addMensajeError(AplicacionMB.getMessage("recordarMensajeError", language));
			return null;
		}
	}

	
}
