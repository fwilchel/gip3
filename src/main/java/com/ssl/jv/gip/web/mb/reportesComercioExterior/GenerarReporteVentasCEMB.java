package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.negocio.dto.DocTerminosTransporteDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ViewScoped;

/**
 * <p>
 * Title: GIP</p>
 *
 * <p>
 * Description: GIP</p>
 *
 * <p>
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Soft Studio Ltda.</p>
 *
 * @author Diego Poveda
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean(name = "generarReporteVentasCE")
@ViewScoped
public class GenerarReporteVentasCEMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(GenerarReporteVentasCEMB.class);

  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEjb;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menuMB;

  /**
   * lista de clientes que retorna la consulta a la base de datos
   */
  private List<Cliente> listaClientes;
  /**
   * Registro seleccionado
   */
  private Cliente seleccionado;
  /**
   *
   */
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJB;
  /**
   *
   */
  private List<DocTerminosTransporteDTO> listaFacturas;
  /**
   *
   */
  private Date filtroFechaInicial;
  /**
   *
   */
  private Date filtroFechaFinal;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
  }

  /**
   *
   */
  public void buscarClientes() {
    LOGGER.debug("Metodo: <<buscarClientes>>");
    listaClientes = comercioExteriorEJB.listadoClientesInstruccionEmbarque(menuMB.getUsuario().getId());
  }

  /**
   *
   */
  public void buscarProductos() {
    LOGGER.debug("Metodo: <<buscarProductos>>");
  }

  /**
   *
   * @param seleccionado
   */
  public void seleccionarCliente(Cliente seleccionado) {
    LOGGER.debug("Metodo: <<seleccionarCliente>>");
    setSeleccionado(seleccionado);
  }

  public void generarReporteVentas() {
    LOGGER.debug("Metodo: <<generarReporteVentas>>");
    // TODO: 
  }

  /**
   *
   */
  public void cancelar() {
    LOGGER.debug("Metodo: <<cancelar>>");
    this.setSeleccionado(null);
  }

  /**
   *
   * @return
   */
  public Date getFechaActual() {
    LOGGER.debug("Metodo: <<getFechaActual>>");
    return new Date();
  }

  /**
   * @param appMB the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  /**
   * @param menuMB the menuMB to set
   */
  public void setMenuMB(MenuMB menuMB) {
    this.menuMB = menuMB;
  }

  /**
   * @return the listaClientes
   */
  public List<Cliente> getListaClientes() {
    return listaClientes;
  }

  /**
   * @param listaClientes the listaClientes to set
   */
  public void setListaClientes(List<Cliente> listaClientes) {
    this.listaClientes = listaClientes;
  }

  /**
   * @return the seleccionado
   */
  public Cliente getSeleccionado() {
    return seleccionado;
  }

  /**
   * @param seleccionado the seleccionado to set
   */
  public void setSeleccionado(Cliente seleccionado) {
    this.seleccionado = seleccionado;
  }

  /**
   * @return the listaFacturas
   */
  public List<DocTerminosTransporteDTO> getListaFacturas() {
    return listaFacturas;
  }

  /**
   * @param listaFacturas the listaFacturas to set
   */
  public void setListaFacturas(List<DocTerminosTransporteDTO> listaFacturas) {
    this.listaFacturas = listaFacturas;
  }

  /**
   * @return the filtroFechaInicial
   */
  public Date getFiltroFechaInicial() {
    return filtroFechaInicial;
  }

  /**
   * @param filtroFechaInicial the filtroFechaInicial to set
   */
  public void setFiltroFechaInicial(Date filtroFechaInicial) {
    this.filtroFechaInicial = filtroFechaInicial;
  }

  /**
   * @return the filtroFechaFinal
   */
  public Date getFiltroFechaFinal() {
    return filtroFechaFinal;
  }

  /**
   * @param filtroFechaFinal the filtroFechaFinal to set
   */
  public void setFiltroFechaFinal(Date filtroFechaFinal) {
    this.filtroFechaFinal = filtroFechaFinal;
  }

}
