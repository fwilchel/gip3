package com.ssl.jv.gip.web.mb.maestros;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: GIP
 * </p>
 * <p>
 * Description: GIP
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: Soft Studio Ltda.
 * </p>
 *
 * @author Diego Poveda
 * @email diego.poveda@softstudio.com
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean(name = "productosClienteMB")
@ViewScoped
public class ProductosClienteMB extends UtilMB {

  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(ProductosClienteMB.class);
  @EJB
  private MaestrosEJBLocal maestrosEJB;
  @EJB
  private ComunEJBLocal comunEJB;
  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private final Integer language = AplicacionMB.SPANISH;
  private String skuFlt;
  private String nombreClienteFlt;
  private String nombreSitioEntregaFlt;
  private boolean estadoFlt;
  private List<ProductosXCliente> productoXClienteLista;
  private ProductosXCliente productoXClienteSeleccionado;

  private enum Modo {

    DETALLE, MENSAGE;
  }

  @PostConstruct
  public void init() {
    LOGGER.trace("Metodo: <<init>>");
  }

  /**
   *
   */
  public void consultarProductos() {
    LOGGER.trace("Metodo: <<consultarProductos>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("sku", skuFlt);
    parametros.put("nombreCliente", nombreClienteFlt);
//    parametros.put("nombrePuntoVenta", nombreSitioEntregaFlt);
    parametros.put("activo", estadoFlt);
    productoXClienteLista = maestrosEJB.consultarProductosXCliente(parametros);
  }

  /**
   *
   * @param documento
   */
  public void seleccionarProducto(Documento documento) {
    LOGGER.trace("Metodo: <<seleccionarRemision>>");
  }

  /**
   * @param appMB the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  /**
   * @param menu the menu to set
   */
  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  /**
   * @return the skuFlt
   */
  public String getSkuFlt() {
    return skuFlt;
  }

  /**
   * @param skuFlt the skuFlt to set
   */
  public void setSkuFlt(String skuFlt) {
    this.skuFlt = skuFlt;
  }

  /**
   * @return the nombreClienteFlt
   */
  public String getNombreClienteFlt() {
    return nombreClienteFlt;
  }

  /**
   * @param nombreClienteFlt the nombreClienteFlt to set
   */
  public void setNombreClienteFlt(String nombreClienteFlt) {
    this.nombreClienteFlt = nombreClienteFlt;
  }

  /**
   * @return the nombreSitioEntregaFlt
   */
  public String getNombreSitioEntregaFlt() {
    return nombreSitioEntregaFlt;
  }

  /**
   * @param nombreSitioEntregaFlt the nombreSitioEntregaFlt to set
   */
  public void setNombreSitioEntregaFlt(String nombreSitioEntregaFlt) {
    this.nombreSitioEntregaFlt = nombreSitioEntregaFlt;
  }

  /**
   * @return the estadoFlt
   */
  public boolean isEstadoFlt() {
    return estadoFlt;
  }

  /**
   * @param estadoFlt the estadoFlt to set
   */
  public void setEstadoFlt(boolean estadoFlt) {
    this.estadoFlt = estadoFlt;
  }

  /**
   * @return the productoXClienteLista
   */
  public List<ProductosXCliente> getProductoXClienteLista() {
    return productoXClienteLista;
  }

  /**
   * @param productoXClienteLista the productoXClienteLista to set
   */
  public void setProductoXClienteLista(List<ProductosXCliente> productoXClienteLista) {
    this.productoXClienteLista = productoXClienteLista;
  }

  /**
   * @return the productoXClienteSeleccionado
   */
  public ProductosXCliente getProductoXClienteSeleccionado() {
    return productoXClienteSeleccionado;
  }

  /**
   * @param productoXClienteSeleccionado the productoXClienteSeleccionado to set
   */
  public void setProductoXClienteSeleccionado(ProductosXCliente productoXClienteSeleccionado) {
    this.productoXClienteSeleccionado = productoXClienteSeleccionado;
  }
}
