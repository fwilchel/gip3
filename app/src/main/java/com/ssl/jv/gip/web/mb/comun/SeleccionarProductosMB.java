package com.ssl.jv.gip.web.mb.comun;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.UtilMB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

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
@ManagedBean(name = "seleccionarProductosMB")
@ViewScoped
public class SeleccionarProductosMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(SeleccionarProductosMB.class);
  @EJB
  private MaestrosEJBLocal maestrosEJB;
  private Long filtroIdCategoria;
  private String filtroSKUProducto;
  private String filtroNombreProducto;
  private List<ProductosInventario> listaProductos;
  private List<ProductosInventario> listaProductosSeleccionados;
  private LazyDataModel<ProductosInventario> listaProductosLazyModel;
  private ProductosInventario filtro;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
  }

  /**
   *
   */
  public void buscarProductos() {
    LOGGER.debug("Metodo: <<buscarProductos>>");
    setListaProductosLazyModel(new LazyProductsDataModel());
    filtro = new ProductosInventario();
    filtro.setDesactivado(true);
    if (filtroIdCategoria != null && filtroIdCategoria != 0) {
      filtro.setCategoriasInventario(new CategoriasInventario());
      filtro.getCategoriasInventario().setId(filtroIdCategoria);
    }
    if (filtroSKUProducto != null && !filtroSKUProducto.isEmpty()) {
      filtro.setSku(filtroSKUProducto);
    }
    if (filtroNombreProducto != null && !filtroNombreProducto.isEmpty()) {
      filtro.setNombre(filtroNombreProducto);
    }
  }

  /**
   *
   */
  public void asignarProductosSeleccionados() {
    LOGGER.debug("Metodo: <<asignarProductosSeleccionados>>");
    RequestContext.getCurrentInstance().closeDialog(listaProductosSeleccionados);
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
   * @return the filtroIdCategoria
   */
  public Long getFiltroIdCategoria() {
    return filtroIdCategoria;
  }

  /**
   * @param filtroIdCategoria the filtroIdCategoria to set
   */
  public void setFiltroIdCategoria(Long filtroIdCategoria) {
    this.filtroIdCategoria = filtroIdCategoria;
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
   * @return the filtroNombreProducto
   */
  public String getFiltroNombreProducto() {
    return filtroNombreProducto;
  }

  /**
   * @param filtroNombreProducto the filtroNombreProducto to set
   */
  public void setFiltroNombreProducto(String filtroNombreProducto) {
    this.filtroNombreProducto = filtroNombreProducto;
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

  /**
   * @return the listaProductosLazyModel
   */
  public LazyDataModel<ProductosInventario> getListaProductosLazyModel() {
    return listaProductosLazyModel;
  }

  /**
   * @param listaProductosLazyModel the listaProductosLazyModel to set
   */
  public void setListaProductosLazyModel(LazyDataModel<ProductosInventario> listaProductosLazyModel) {
    this.listaProductosLazyModel = listaProductosLazyModel;
  }

  /**
   * Implementacion de LazyModel
   */
  private class LazyProductsDataModel extends LazyDataModel<ProductosInventario> {

    /**
     *
     */
    private static final long serialVersionUID = 283497341126330045L;
    private List<ProductosInventario> datos;

    @Override
    public Object getRowKey(ProductosInventario pi) {
      LOGGER.debug("Metodo: <<getRowKey>>");
      return pi.getId();
    }

    @Override
    public ProductosInventario getRowData(String rowKey) {
      LOGGER.debug("Metodo: <<getRowData>>");
      for (ProductosInventario pi : datos) {
        if (pi.getId().toString().equals(rowKey)) {
          return pi;
        }
      }
      return null;
    }

    @Override
    public List<ProductosInventario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
      LOGGER.debug("Metodo: <<load>>");
      Object rta[] = maestrosEJB.consultarProductos(filtro, first, pageSize, sortField, sortOrder, true);
      this.setRowCount(((Long) rta[0]).intValue());
      rta = maestrosEJB.consultarProductos(filtro, first, pageSize, sortField, sortOrder, false);
      datos = (List<ProductosInventario>) rta[1];
      return datos;
    }

  }

}
