package com.ssl.jv.gip.web.mb.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.MedioTransporte;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name = "terminoIncotermXMedioTransMB")
@ViewScoped
public class TerminoIncotermXMedioTransMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = Logger.getLogger(TerminoIncotermXMedioTransMB.class);

  private Integer language = AplicacionMB.SPANISH;

  private List<TerminoIncotermXMedioTransporte> lista;

  private TerminoIncotermXMedioTransporte seleccionado;

  private Modo modo;

  @EJB
  private MaestrosEJB maestroEjb;

  private List<TerminoIncoterm> listaTerminoIncoterm;

  private List<MedioTransporte> listaMedioTransporte;

  public TerminoIncotermXMedioTransMB() {
  }

  @PostConstruct
  public void init() {
    lista = maestroEjb.consultarTerminoIncotermXMedioTransporte();
    listaTerminoIncoterm = new ArrayList<TerminoIncoterm>();
    listaMedioTransporte = new ArrayList<MedioTransporte>();
  }

  public String modificar() {

    return "";
  }

  public void nuevo() {
    seleccionado = new TerminoIncotermXMedioTransporte();
    seleccionado.setTerminoIncoterm(new TerminoIncoterm());
    seleccionado.setMedioTransporte(new MedioTransporte());
    this.modo = Modo.CREACION;
  }

  public void guardar() {
    try {
      if (this.modo.equals(Modo.CREACION)) {
        this.seleccionado = this.maestroEjb.crearTerminoIncotermXMedioTransporte(this.seleccionado);
        if (this.lista == null) {
          this.lista = new ArrayList<TerminoIncotermXMedioTransporte>();
        }
        lista = maestroEjb.consultarTerminoIncotermXMedioTransporte();
        this.nuevo();
      } else {
        this.maestroEjb.actualizarTerminoIncotermXMedioTransporte(this.seleccionado);
        lista = maestroEjb.consultarTerminoIncotermXMedioTransporte();
      }

      this.addMensajeInfo("TerminoIncotermXMedioTransporte almacenado exitosamente");

    } catch (EJBTransactionRolledbackException e) {
      if (this.isException(e, "dist_termino_incoterm_x_medio_transporte_key")) {
        this.addMensajeError(AplicacionMB.getMessage("maestroTerminoIncotermMedioTransUnique", language));
      }
      LOGGER.error(e);
    } catch (Exception e) {
      this.addMensajeError(AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
      LOGGER.error(e);
    }

  }

  public boolean isCreacion() {
    if (this.modo != null && this.modo.equals(Modo.CREACION)) {
      return true;
    } else {
      return false;
    }
  }

  public Modo getModo() {
    return modo;
  }

  public void setModo(Modo modo) {
    this.modo = modo;
  }

  public TerminoIncotermXMedioTransporte getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(TerminoIncotermXMedioTransporte seleccionado) {
    this.seleccionado = seleccionado;
    this.modo = Modo.EDICION;
  }

  public List<TerminoIncotermXMedioTransporte> getLista() {
    return lista;
  }

  public void setLista(List<TerminoIncotermXMedioTransporte> lista) {
    this.lista = lista;
  }

  public List<TerminoIncoterm> getListaTerminoIncoterm() {
    if (listaTerminoIncoterm == null || listaTerminoIncoterm.isEmpty()) {
      listaTerminoIncoterm = maestroEjb.consultarTerminoIncotermActivo();
    }
    return listaTerminoIncoterm;
  }

  public void setListaTerminoIncoterm(List<TerminoIncoterm> listaTerminoIncoterm) {
    this.listaTerminoIncoterm = listaTerminoIncoterm;
  }

  public List<MedioTransporte> getListaMedioTransporte() {
    if (listaMedioTransporte == null || listaMedioTransporte.isEmpty()) {
      listaMedioTransporte = maestroEjb.consultarMedioTransporteActivo();
    }
    return listaMedioTransporte;
  }

  public void setListaMedioTransporte(List<MedioTransporte> listaMedioTransporte) {
    this.listaMedioTransporte = listaMedioTransporte;
  }

}
