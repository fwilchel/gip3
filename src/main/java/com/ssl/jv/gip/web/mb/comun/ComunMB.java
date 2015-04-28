package com.ssl.jv.gip.web.mb.comun;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

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
@ManagedBean(name = "comunMB")
@ViewScoped
public class ComunMB extends UtilMB {

  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(ComunMB.class);
  @EJB
  private MaestrosEJBLocal maestrosEJB;
  @EJB
  private ComunEJBLocal comunEJB;
  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  public List<Cliente> obtenerListaClientes(String idUsuario) {
    return maestrosEJB.consultarClientesActivosPorUsuario(idUsuario);
  }

  public List<Moneda> obtenerListaMonedas() {
    return maestrosEJB.consultarMonedas();
  }

  public List<PuntoVenta> obtenerListaPuntosVenta(Long idCliente) {
    return maestrosEJB.consultarPuntoEntregaPorCliente(idCliente);
  }

  public List<SelectItem> obtenerListaCategoriasInventarios() {
    List<SelectItem> categoriasInventarios = null;
    List<CategoriasInventario> categorias = maestrosEJB.consultarCategoriasInventarios();
    SelectItemGroup group = null;
    categoriasInventarios = new ArrayList<>();
    for (CategoriasInventario categoriasInventario : categorias) {
      group = new SelectItemGroup(categoriasInventario.getNombre());
      List<SelectItem> items = new ArrayList<>();
      SelectItem item = null;
      group.setSelectItems(getSelectItems(categoriasInventario.getCategoriasInventarios()));
      categoriasInventarios.add(group);
    }
    return categoriasInventarios;
  }

  private SelectItem[] getSelectItems(List<CategoriasInventario> categoriasInventarios) {
    List<SelectItem> items = new ArrayList<>();
    SelectItem item = null;
    for (CategoriasInventario categoriasInventario : categoriasInventarios) {
      item = new SelectItem(categoriasInventario.getId(), categoriasInventario.getNombre());
      items.add(item);
    }
    return items.toArray(new SelectItem[categoriasInventarios.size()]);
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

}
