package com.ssl.jv.gip.web.mb.maestros;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.TipoMovimiento;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**
 * @author Diego Poveda
 *
 */
@ManagedBean(name = "inventarioComercioExteriorMB")
@SessionScoped
public class InventarioComercioExteriorMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2386637213204199200L;

  private static final Logger LOGGER = Logger.getLogger(InventarioComercioExteriorMB.class);
  @EJB
  private MaestrosEJBLocal maestrosEJBLocal;
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEjb;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private String idUsuario;
  private String sku;
  private Modo modo;
  private List<MovimientosInventarioComext> movimientosInventario;
  private List<MovimientosInventarioComext> movimientosInventarioNuevos;
  private ProductosInventarioFiltroDTO productosInventarioFiltroDTO;
  private List<ProductosInventario> productos;
  private List<SelectItem> categoriasInventarios;
  private Map<Long, BigDecimal> ultimosSaldosInventario;

  @PostConstruct
  public void init() {
    modo = Modo.LISTAR;
    idUsuario = menu.getUsuario().getId();
    movimientosInventario = new ArrayList<>();
    productosInventarioFiltroDTO = new ProductosInventarioFiltroDTO();
    productos = new ArrayList<>();
    movimientosInventarioNuevos = new ArrayList<>();
    cargarCategoriasInventario();
    consultarSaldosInventarioComercioExterior();
  }

  public void consultarSaldosInventarioComercioExterior() {
    ultimosSaldosInventario = this.comercioExteriorEjb.consultarUltimosSaldos();
  }

  public void reset() {
    movimientosInventarioNuevos = new ArrayList<>();
  }

  public void consultarMovimientosInventarioComExt(ActionEvent actionEvent) {
    try {
      movimientosInventario = maestrosEJBLocal.consultarMovimientosInventarioComextsPorSku(sku);
    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e, RuntimeException.class);
        if (unrollException != null) {
          this.addMensajeError(unrollException.getLocalizedMessage());
        } else {
          this.addMensajeError(e);
        }
      }
    }
  }

  public String activarModoIngresarMovimientos() {
    modo = Modo.EDITAR;
    return null;
  }

  public String activarModoConsultarMovimientos() {
    modo = Modo.LISTAR;
    return null;
  }

  public void agregarProducto(ProductosInventario producto) {
    if (producto != null) {
      for (MovimientosInventarioComext m : movimientosInventarioNuevos) {
        if (m.getProductosInventarioComext().getProductosInventario().getId().equals(producto.getId())) {
          addMensajeWarn("El producto " + producto.getSku() + " ya se encuentra en la lista.");
          break;
        }
      }
      MovimientosInventarioComext movimiento = new MovimientosInventarioComext();
      movimiento.setProductosInventarioComext(producto.getProductosInventarioComext());
      movimiento.setConsecutivoDocumento("MANUAL");
      movimiento.setTipoMovimiento(new TipoMovimiento(1L));
      movimiento.setCantidad(BigDecimal.ONE);
      movimiento.setFecha(new Date());
      BigDecimal ultimoSaldo = ultimosSaldosInventario.get(producto.getProductosInventarioComext().getIdProducto());
      movimiento.setSaldo(ultimoSaldo != null ? ultimoSaldo : BigDecimal.ZERO);
      movimientosInventarioNuevos.add(movimiento);
      productos.remove(producto);
    }
  }

  public void eliminarMovimiento(MovimientosInventarioComext movimiento) {
    if (movimiento != null) {
      movimientosInventarioNuevos.remove(movimiento);
    }
  }

  public void guardarMovimientos() {
    try {
      if (movimientosInventarioNuevos.isEmpty()) {
        this.addMensajeError("No se han incluído productos");
        return;
      }
      // auditoria
      LogAuditoria auditoria = new LogAuditoria();
      auditoria.setIdUsuario(menu.getUsuario().getId());
      auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
      maestrosEJBLocal.guardarMovimientosInventarioComercioExterior(movimientosInventarioNuevos, auditoria);
      reset();
      consultarSaldosInventarioComercioExterior();
      addMensajeInfo("Se han creado correctamente las movimientos");
    } catch (Exception e) {
      addMensajeError("No fue posible crear los movimientos");
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        this.addMensajeError(e.getMessage());
      }
    }
  }

  private void cargarCategoriasInventario() {
    List<CategoriasInventario> categorias = maestrosEJBLocal.consultarCategoriasInventarios();
    SelectItemGroup group;
    categoriasInventarios = new ArrayList<>();
    for (CategoriasInventario categoriasInventario : categorias) {
      group = new SelectItemGroup(categoriasInventario.getNombre());
      group.setSelectItems(getSelectItems(categoriasInventario.getCategoriasInventarios()));
      categoriasInventarios.add(group);
    }
  }

  private SelectItem[] getSelectItems(List<CategoriasInventario> categoriasInventarios) {
    List<SelectItem> items = new ArrayList<>();
    SelectItem item;
    for (CategoriasInventario categoriasInventario : categoriasInventarios) {
      item = new SelectItem(categoriasInventario.getId(), categoriasInventario.getNombre());
      items.add(item);
    }
    return items.toArray(new SelectItem[categoriasInventarios.size()]);
  }

  public void buscarProductosInventarios(ActionEvent event) {
    try {
      productosInventarioFiltroDTO.setEstado(true);
      if (productosInventarioFiltroDTO.getIdCategoria() == -1) {
        productosInventarioFiltroDTO.setIdCategoria(null);
      }
      productosInventarioFiltroDTO.setControlStock(true);
      productos = maestrosEJBLocal.consultarProductosInventariosPorEstadoCategoriaSkuNombreAndControlStock(productosInventarioFiltroDTO);
    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e, RuntimeException.class);
        if (unrollException != null) {
          this.addMensajeError(unrollException.getLocalizedMessage());
        } else {
          this.addMensajeError(e);
        }
      }
    }
  }

  public boolean isModoLista() {
    return modo == Modo.LISTAR;
  }

  public boolean isModoEditar() {
    return modo == Modo.EDITAR;
  }

  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public MenuMB getMenu() {
    return menu;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  public List<MovimientosInventarioComext> getMovimientosInventario() {
    return movimientosInventario;
  }

  public void setMovimientosInventario(List<MovimientosInventarioComext> movimientosInventario) {
    this.movimientosInventario = movimientosInventario;
  }

  public ProductosInventarioFiltroDTO getProductosInventarioFiltroDTO() {
    return productosInventarioFiltroDTO;
  }

  public void setProductosInventarioFiltroDTO(ProductosInventarioFiltroDTO productosInventarioFiltroDTO) {
    this.productosInventarioFiltroDTO = productosInventarioFiltroDTO;
  }

  public List<ProductosInventario> getProductos() {
    return productos;
  }

  public void setProductos(List<ProductosInventario> productos) {
    this.productos = productos;
  }

  public List<SelectItem> getCategoriasInventarios() {
    return categoriasInventarios;
  }

  public void setCategoriasInventarios(List<SelectItem> categoriasInventarios) {
    this.categoriasInventarios = categoriasInventarios;
  }

  public List<MovimientosInventarioComext> getMovimientosInventarioNuevos() {
    return movimientosInventarioNuevos;
  }

  public void setMovimientosInventarioNuevos(List<MovimientosInventarioComext> movimientosInventarioNuevos) {
    this.movimientosInventarioNuevos = movimientosInventarioNuevos;
  }

}
