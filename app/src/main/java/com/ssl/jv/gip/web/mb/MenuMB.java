package com.ssl.jv.gip.web.mb;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.ssl.jv.gip.jpa.pojo.Funcionalidad;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
import com.ssl.jv.gip.jpa.pojo.Usuario;

@ManagedBean(name = "menuMB")
@SessionScoped
public class MenuMB extends UtilMB {

  private static final Logger LOGGER = Logger.getLogger(MenuMB.class);
  /**
   *
   */
  private static final long serialVersionUID = -7266340433347174042L;
  private Usuario usuario;
  private PuntoVenta puntoVenta;
  private MenuModel modelo;
  private String opcionActual;
  private List<Funcionalidad> opciones;
  private Integer language = AplicacionMB.SPANISH;
  private Long idOpcionActual;
  private String tema = "default";

  public MenuMB() {

  }

  public Integer getLanguage() {
    return language;
  }

  public void setLanguage(Integer language) {
    this.language = language;
  }

  public MenuModel getModelo() {
    return modelo;
  }

  public void setModelo(MenuModel modelo) {
    this.modelo = modelo;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  /**
   * @return the puntoVenta
   */
  public PuntoVenta getPuntoVenta() {
    return puntoVenta;
  }

  /**
   * @param puntoVenta the puntoVenta to set
   */
  public void setPuntoVenta(PuntoVenta puntoVenta) {
    this.puntoVenta = puntoVenta;
  }

  public String getOpcionActual() {
    return opcionActual;
  }

  public void setOpcionActual(String opcionActual) {
    this.opcionActual = opcionActual;
  }

  public Long getIdOpcionActual() {
    return idOpcionActual;
  }

  public void setIdOpcionActual(Long idOpcionActual) {
    this.idOpcionActual = idOpcionActual;
  }

  /**
   * Baja de memoria los MB
   */
  public String cerrar() {
    return "introduccion";
  }

  public void setMenu(List<Funcionalidad> opciones) {
    this.opciones = opciones;
    modelo = new DefaultMenuModel();

    for (Funcionalidad o : opciones) {
      if (o.getFuncionalidade() == null) {

        DefaultSubMenu sm = new DefaultSubMenu();
        List<Funcionalidad> hijos = getHijos(o, opciones, sm);
        if (hijos.size() == 0) {
          /*MenuItem mi=new MenuItem();
           mi.setLabel(o.getOpcion_1());
           mi.setActionCommand(o.getUrl());*/
        } else {
          sm.setLabel(o.getNombre());
          modelo.addElement(sm);
          o.setFuncionalidades(hijos);
        }

      }
    }
  }

  private List<Funcionalidad> getHijos(Funcionalidad padre, List<Funcionalidad> opciones, DefaultSubMenu sm) {
    List<Funcionalidad> hijos = new ArrayList<Funcionalidad>();
    for (Funcionalidad o : opciones) {
      if (o.getFuncionalidade() != null && padre.getId().equals(o.getFuncionalidade().getId())) {
        hijos.add(o);

        DefaultSubMenu sm2 = new DefaultSubMenu();
        List<Funcionalidad> hijos2 = getHijos(o, opciones, sm2);
        if (hijos2.size() == 0) {
          DefaultMenuItem mi = new DefaultMenuItem();
          mi.setValue(o.getNombre());
          //mi.setIcon(o.getIcono());
          mi.setCommand("#{menuMB.ingresoOpcion}");
          mi.setImmediate(true);
          mi.setParam("opcion", o.getRuta());
          mi.setAjax(false);
          sm.addElement(mi);
        } else {

          sm.setLabel(o.getNombre());
          modelo.addElement(sm);
          o.setFuncionalidades(hijos2);
        }
      }
    }

    return hijos;
  }

  public static MethodExpression createAction(String actionExpression, Class<?> returnType) {
    FacesContext context = FacesContext.getCurrentInstance();
    return context.getApplication().getExpressionFactory()
            .createMethodExpression(context.getELContext(), actionExpression, returnType, new Class[0]);
  }

  public static MethodExpressionActionListener createActionListener(String actionListenerExpression) {
    FacesContext context = FacesContext.getCurrentInstance();
    return new MethodExpressionActionListener(context.getApplication().getExpressionFactory()
            .createMethodExpression(context.getELContext(), actionListenerExpression, null, new Class[]{ActionEvent.class}));
  }

  public String ingresoOpcion() {

    LOGGER.debug("*********** IngresoOpcion");
    FacesContext fc = FacesContext.getCurrentInstance();
    ExternalContext ec = fc.getExternalContext();
    HttpServletRequest req = (HttpServletRequest) ec.getRequest();
    for (Funcionalidad o : opciones) {
      if (o.getRuta() != null && o.getRuta().equals(req.getParameter("opcion"))) {
        this.opcionActual = o.getNombre();
        this.idOpcionActual = o.getId();
        liberarRecursos();
        break;
      }
    }
    LOGGER.debug("*********** IngresoOpcion *********" + this.opcionActual + "*******" + req.getParameter("opcion"));
    return req.getParameter("opcion");
  }

  public String salir() {
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

    FacesContext context = null;
    context = FacesContext.getCurrentInstance();
    ExternalContext external = context.getExternalContext();
    HttpSession session = (HttpSession) external.getSession(false);
    session.invalidate();
    this.addMensajeInfo(AplicacionMB.getMessage("sesionCerrada", language));
    if (usuario != null) {
      LOGGER.info("Client IP address=|" + remoteAddr + " |Identificacion=|" + usuario.getEmail() + " |Autenticación|sesión cerrada correctamente");
    }
    if (this.tema!=null && this.tema.equals("cn"))
    	return "salirCN";
    else
    	return "salir";

  }

  public List<Funcionalidad> getOpciones() {
    return opciones;
  }

  public void setOpciones(List<Funcionalidad> opciones) {
    this.opciones = opciones;
  }

  /**
   * Metodo para liberar los bean de sessión de la aplicación al cambiar de menú
   */
  private void liberarRecursos() {
    FacesContext fc = FacesContext.getCurrentInstance();
    ExternalContext ec = fc.getExternalContext();
    HttpServletRequest req = (HttpServletRequest) ec.getRequest();
    HttpSession session = req.getSession(false);
    if (session != null) {
      Enumeration attrNames = session.getAttributeNames();
      while (attrNames.hasMoreElements()) {
        String nombre = (String) attrNames.nextElement();
        Class superClase = session.getAttribute(nombre).getClass().getSuperclass();
        if (superClase != null && !"menuMB".equals(nombre) && buscarSuperClaseAbstract(superClase)) {
          LOGGER.debug("Removiendo : " + nombre);
          session.removeAttribute(nombre);
        }
      }
    }
  }

  private boolean buscarSuperClaseAbstract(Class superClase) {
    if (superClase == null || superClase.equals(Object.class)) {
      return false;
    }

    if (superClase.equals(UtilMB.class)) {
      return true;
    } else {

      return buscarSuperClaseAbstract(superClase.getSuperclass());
    }
  }

  /**
   * @return the tema
   */
  public String getTema() {
    return tema;
  }

  /**
   * @param tema the tema to set
   */
  public void setTema(String tema) {
    this.tema = tema;
  }

}
