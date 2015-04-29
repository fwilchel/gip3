package com.ssl.jv.gip.web.mb.ventas;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import java.util.Objects;

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
@ManagedBean(name = "ingresarConsumoServicioMB")
@ViewScoped
public class IngresarConsumoServicioMB extends UtilMB {

  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(IngresarConsumoServicioMB.class);
  @EJB
  private VentasFacturacionEJBLocal ventasFacturacionEJB;
  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private final Integer language = AplicacionMB.SPANISH;
  private Documento ventaDirecta;
  private List<ProductosXDocumento> listaProductosXDocumento;

  @PostConstruct
  public void init() {
    LOGGER.trace("Metodo: <<init>>");
    ventaDirecta = new Documento();
    ventaDirecta.setCliente(new Cliente());
    ventaDirecta.setPuntoVenta(new PuntoVenta());
    listaProductosXDocumento = new ArrayList<>();
  }

  public void chooseProducts() {
    LOGGER.trace("Metodo: <<chooseProducts>>");
    Map<String, Object> options = new HashMap<>();
    options.put("modal", true);
    options.put("draggable", false);
    options.put("resizable", false);
    options.put("contentWidth", 800);
    RequestContext.getCurrentInstance().openDialog("seleccionarProductos", options, null);
  }

  public void onProductsChosen(SelectEvent event) {
    LOGGER.trace("Metodo: <<onProductsChosen>>");
    List<ProductosInventario> listaProductos = (List<ProductosInventario>) event.getObject();
    for (ProductosInventario producto : listaProductos) {
      boolean incluido = false;
      for (ProductosXDocumento pxd : listaProductosXDocumento) {
        if (Objects.equals(producto.getId(), pxd.getProductosInventario().getId())) {
          incluido = true;
          break;
        }
      }
      if (!incluido) {
        ProductosXDocumento tmp = new ProductosXDocumento();
        tmp.setProductosInventario(producto);
        this.getListaProductosXDocumento().add(tmp);
      }
    }
  }

  public void onRemoveEvent(ProductosXDocumento registro) {
    LOGGER.trace("Metodo: <<onRemoveEvent>>");
    listaProductosXDocumento.remove(registro);
  }

  public void ingresarConsumoServicio() {
    LOGGER.trace("Metodo: <<ingresarConsumoServicio>>");
    // auditoria
    LogAuditoria auditoria = new LogAuditoria();
    auditoria.setIdUsuario(menu.getUsuario().getId());
    auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
    try {
      Documento documentoGenerado = ventasFacturacionEJB.generarConsumoServicios(ventaDirecta, listaProductosXDocumento, auditoria);
    } catch (Exception ex) {
      addMensajeError("Error");
      LOGGER.error("Error");
    }
  }

  private void reset() {
    LOGGER.trace("Metodo: <<reset>>");
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
   * @return the ventaDirecta
   */
  public Documento getVentaDirecta() {
    return ventaDirecta;
  }

  /**
   * @param ventaDirecta the ventaDirecta to set
   */
  public void setVentaDirecta(Documento ventaDirecta) {
    this.ventaDirecta = ventaDirecta;
  }

  /**
   * @return the listaProductosXDocumento
   */
  public List<ProductosXDocumento> getListaProductosXDocumento() {
    return listaProductosXDocumento;
  }

  /**
   * @param listaProductosXDocumento the listaProductosXDocumento to set
   */
  public void setListaProductosXDocumento(List<ProductosXDocumento> listaProductosXDocumento) {
    this.listaProductosXDocumento = listaProductosXDocumento;
  }
}
