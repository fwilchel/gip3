package com.ssl.jv.gip.web.mb.maestros;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name = "agenciaCargaMB")
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

  private Integer language = AplicacionMB.SPANISH;

  private Modo modo;

  public AgenciaCargaMB() {
  }

  @PostConstruct
  public void init() {
    agencias = servicio.consultarAgenciasCarga();
  }

  public void setSeleccionado(AgenciaCarga seleccionado) {
    this.seleccionado = seleccionado;
    this.modo = Modo.EDITAR;
  }

  public AgenciaCarga getSeleccionado() {
    return seleccionado;
  }

  public void nuevo() {
    seleccionado = new AgenciaCarga();
    this.modo = Modo.CREAR;
  }

  public void guardar() {
    try {
      if (this.modo.equals(Modo.CREAR)) {
        this.servicio.crearAgenciaCarga(this.seleccionado);
        this.agencias.add(0, this.seleccionado);
        this.modo = Modo.EDITAR;
      } else {
        this.servicio.actualizarAgenciaCarga(this.seleccionado);
      }

      this.addMensajeInfo("Agencia de Carga almacenada exitosamente");
    } catch (Exception ex) {
      this.addMensajeError(AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
    }
  }

  public boolean isCreacion() {
    if (this.modo != null && this.modo.equals(Modo.CREAR)) {
      return true;
    } else {
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
