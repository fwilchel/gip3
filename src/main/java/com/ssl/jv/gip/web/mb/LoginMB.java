package com.ssl.jv.gip.web.mb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.HistorialContrasena;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJBLocal;
import com.ssl.jv.gip.web.util.Utilidad;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB extends UtilMB {

  /**
   *
   */
  private static final Logger LOGGER = Logger.getLogger(LoginMB.class);
  private Integer language = AplicacionMB.SPANISH;

  private static final long serialVersionUID = -1947610603374558443L;

  @EJB
  private AdministracionEJBLocal admonEjb;
  private String password;
  private Integer empresa;
  private String email;
  private Usuario usuario;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  @PostConstruct
  public void init() {
	this.empresa = 1;
  }

  public String ingresar() {

	int NUMERO_MAX_LOGIN = 3;

	String remoteAddr = this.getRemoteAddress();

	LOGGER.info("|Client IP address=|" + remoteAddr + " |Identificacion=|" + email);

	usuario = this.admonEjb.findUsuarioByEmail(this.email);
	if (usuario != null) {
	  String pwd = Utilidad.cifrar(this.password);
	  if (usuario.getContrasena().equals(pwd)) {

		if (usuario.getRole() == null) {
		  this.addMensajeError(AplicacionMB.getMessage("usuarioSinRol", language));
		  return null;
		}
		if (usuario.getActivo() == false) {
		  this.addMensajeError(AplicacionMB.getMessage("usuarioInactivo", language));
		  return null;
		}
		if (usuario.getIntentos() >= NUMERO_MAX_LOGIN) {
		  this.addMensajeError(AplicacionMB.getMessage("cuentaBloqueada", language));
		  return null;
		}

		usuario.setIntentos(0L);
		this.admonEjb.actualizarUsuario(usuario);

		List<HistorialContrasena> h = this.admonEjb.consultarHistorialContrasena(usuario);
		if (h.size() > 0) {
		  menu.setMenu(admonEjb.getMenu(usuario.getEmail()));
		  menu.setUsuario(usuario);
		  menu.setLanguage(language);
		  return "introduccion";
		} else {
		  this.addMensajeError(AplicacionMB.getMessage("loginHistorialContrasena", language));
		  return "cambiarContrasena";
		}

	  } else {
		usuario.setIntentos(usuario.getIntentos() + 1);
		this.admonEjb.actualizarUsuario(usuario);
		this.addMensajeError(AplicacionMB.getMessage("vuelvaIntentarlo", language));
		return null;
	  }
	} else {
	  LOGGER.warn("|Client IP address=|" + remoteAddr + " |Identificacion=|" + email + " " + AplicacionMB.getMessage("vuelvaIntentarlo", language));
	  this.addMensajeError(AplicacionMB.getMessage("vuelvaIntentarlo", language));
	  return null;
	}
  }

  public String recordarContrasena() {
	Usuario usuario = null;
	usuario = this.admonEjb.findUsuarioByEmail(this.email);
	if (usuario != null) {
	  this.admonEjb.enviarEmail(usuario.getEmail(), Utilidad.descifrar(usuario.getContrasena()), usuario.getNombre(), usuario.getApellidos());
	  return "login";

	} else {
	  this.addMensajeError(AplicacionMB.getMessage("recordarMensajeError", language));
	  return null;
	}
  }

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

  public Usuario getUsuario() {
	return usuario;
  }

  public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
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

}
