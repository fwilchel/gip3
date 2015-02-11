package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
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
   *
   */
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJB;
  /**
   *
   */
  private Date filtroFechaInicial;
  /**
   *
   */
  private Date filtroFechaFinal;
  /**
   * lista de clientes que retorna la consulta a la base de datos
   */
  private List<Cliente> listaClientes;
  /**
   * filtro por nombre la lista de clientes
   */
  private String filtroNombreCliente;
  /**
   * lista de los clientes seleccionados
   */
  private List<Cliente> listaClientesSeleccionados;
  /**
   * lista de clientes que retorna la consulta a la base de datos
   */
  private List<ProductosInventario> listaProductos;
  /**
   * filtro por nombre la lista de clientes
   */
  private String filtroSKUProducto;
  /**
   * lista de los clientes seleccionados
   */
  private List<ProductosInventario> listaProductosSeleccionados;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
  }

  /**
   *
   */
  public void buscarClientes() {
    LOGGER.debug("Metodo: <<buscarClientes>>");
    setListaClientes(comercioExteriorEJB.listadoClientesInstruccionEmbarque(menuMB.getUsuario().getId()));
  }

  /**
   *
   */
  public void asignarClientesSeleccionados() {
    LOGGER.debug("Metodo: <<asignarClientesSeleccionados>>");
  }

  /**
   *
   */
  public void buscarProductos() {
    LOGGER.debug("Metodo: <<buscarProductos>>");
    setListaClientes(comercioExteriorEJB.listadoClientesInstruccionEmbarque(menuMB.getUsuario().getId()));
  }

  /**
   *
   */
  public void asignarProductosSeleccionados() {
    LOGGER.debug("Metodo: <<asignarProductosSeleccionados>>");
  }

  /**
   *
   */
  public void generarReporteVentas() {
    LOGGER.debug("Metodo: <<generarReporteVentas>>");
    // TODO: 
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
   * @return the filtroNombreCliente
   */
  public String getFiltroNombreCliente() {
    return filtroNombreCliente;
  }

  /**
   * @param filtroNombreCliente the filtroNombreCliente to set
   */
  public void setFiltroNombreCliente(String filtroNombreCliente) {
    this.filtroNombreCliente = filtroNombreCliente;
  }

  /**
   * @return the listaClientesSeleccionados
   */
  public List<Cliente> getListaClientesSeleccionados() {
    return listaClientesSeleccionados;
  }

  /**
   * @param listaClientesSeleccionados the listaClientesSeleccionados to set
   */
  public void setListaClientesSeleccionados(List<Cliente> listaClientesSeleccionados) {
    this.listaClientesSeleccionados = listaClientesSeleccionados;
  }

  /**
   * @return the listaProductos
   */
  public List<ProductosInventario> getListaProductos() {
    return listaProductos;
  }

  /**
   * @param listaProductos the listaProductos to set
   */
  public void setListaProductos(List<ProductosInventario> listaProductos) {
    this.listaProductos = listaProductos;
  }

  /**
   * @return the filtroSKUProducto
   */
  public String getFiltroSKUProducto() {
    return filtroSKUProducto;
  }

  /**
   * @param filtroSKUProducto the filtroSKUProducto to set
   */
  public void setFiltroSKUProducto(String filtroSKUProducto) {
    this.filtroSKUProducto = filtroSKUProducto;
  }

  /**
   * @return the listaProductosSeleccionados
   */
  public List<ProductosInventario> getListaProductosSeleccionados() {
    return listaProductosSeleccionados;
  }

  /**
   * @param listaProductosSeleccionados the listaProductosSeleccionados to set
   */
  public void setListaProductosSeleccionados(List<ProductosInventario> listaProductosSeleccionados) {
    this.listaProductosSeleccionados = listaProductosSeleccionados;
  }

}
