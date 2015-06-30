package com.ssl.jv.gip.web.mb.devoluciones;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.BodegasLogica;
import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.ProductoDevolucionDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.negocio.ejb.DevolucionesEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "devolucionClienteTiendaMB")
@ViewScoped
public class DevolucionClienteTiendaMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -4547719350295240598L;

  private static final Logger LOGGER = Logger.getLogger(DevolucionClienteTiendaMB.class);

  @EJB
  private DevolucionesEJBLocal devolucionesEJBLocal;

  /**
   * The comercio ejb.
   */
  @EJB
  private ComercioExteriorEJB comercioEjb;

  @EJB
  private MaestrosEJBLocal maestrosEjb;

  /**
   * The menu.
   */
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  private List<CategoriasInventario> listaCategorias;

  private List<ProductoDevolucionDTO> listaProductos;

  private List<ProductoDevolucionDTO> listaProductosSeleccionados;

  private List<Ubicacion> listaUbicaciones;

  private Map<Long, Ubicacion> ubicaciones;

  private Ubicacion ubicacionSeleccionada;

  private List<SelectItem> categoriasInventarios;

  @EJB
  private VentasFacturacionEJBLocal ventasFacturacionEJBLocal;

  @PostConstruct
  public void init() {
    this.cargarCategoriasInventario();
    this.cargarListaUbicaciones();
    this.ubicacionSeleccionada = new Ubicacion();
  }

  private void cargarListaUbicaciones() {
    LOGGER.debug("Metodo: <<cargarListaUbicaciones>>");
    this.listaUbicaciones = devolucionesEJBLocal.consultarUbicacionesOrdenadas();
    if (listaUbicaciones != null) {
      ubicaciones = new HashMap<>();
      for (Ubicacion ubicacion : getListaUbicaciones()) {
        ubicaciones.put(ubicacion.getId(), ubicacion);
      }
    }
  }

  public void onUbicacionChange() {
    if (ubicacionSeleccionada != null) {
      ubicacionSeleccionada = ubicaciones.get(ubicacionSeleccionada.getId());
    }
  }

  public void consultarProductos() {
    List<Ubicacion> ubicacionesUsuario = comercioEjb.consultarUbicacionesPorUsuario(menu.getUsuario().getId());
    List<Long> idsUbicacion = new ArrayList<>();

    for (Ubicacion ubicacion : ubicacionesUsuario) {
      idsUbicacion.add(ubicacion.getId());
    }

    List<Ubicacion> ubicacionesPorIds = devolucionesEJBLocal.consultarPorIds(idsUbicacion);

    List<String> paises = new ArrayList<>();
    boolean todosPaises = false;

    for (Ubicacion ubicacion : ubicacionesPorIds) {

      if (ubicacion.getRegione().getPais().getId().equals("PAISES_TODOS")) {
        todosPaises = true;
      } else {
        paises.add(ubicacion.getRegione().getPais().getId());
      }

    }

    if (todosPaises) {
      this.listaProductos = devolucionesEJBLocal.consultarProductosActivos();
    } else {
      this.listaProductos = devolucionesEJBLocal.consultarProductosInventarioPorPais(paises);
    }

  }

  public void seleccionarProductos() {
    this.listaProductosSeleccionados = new ArrayList<>();
    for (ProductoDevolucionDTO prod : listaProductos) {
      if (prod.isIncluido()) {
        this.listaProductosSeleccionados.add(prod);
      }
    }
  }

  public void devolverProductos(ActionEvent actionEvent) {
    try {
      Documento documento = new Documento();
      documento.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
      Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
      EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
      estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.PENDIENTE_POR_RECIBIR);
      estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.DEVOLUCION);
      estadosxdocumento.setId(estadosxdocumentoPK);
      documento.setEstadosxdocumento(estadosxdocumento);

      //documento.setObservacionDocumento(this.consecutivoDocumento);
      documento.setUbicacionDestino(new Ubicacion());
      documento.setUbicacionOrigen(new Ubicacion());
      documento.getUbicacionDestino().setId(this.ubicacionSeleccionada.getId());
      documento.getUbicacionOrigen().setId(com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo());
      documento.setNumeroFactura("0");

      LogAuditoria auditoria = new LogAuditoria();
      auditoria.setIdUsuario(menu.getUsuario().getId());
      auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
      auditoria.setTabla("Documentos");
      auditoria.setAccion("CRE");
      auditoria.setFecha(new Timestamp(System.currentTimeMillis()));

      List<ProductosXDocumento> productos = new ArrayList<>();

      for (ProductoDevolucionDTO pxc : this.listaProductosSeleccionados) {
        if (pxc.isIncluido()) {

          BodegasLogica bodega = new BodegasLogica();
          bodega.setId(0l);

          ProductosXDocumento productoDocumento = new ProductosXDocumento();
          productoDocumento.setInformacion(false);
          productoDocumento.setCalidad(false);
          productoDocumento.setFechaEstimadaEntrega(getFechaActual());
          productoDocumento.setFechaEntrega(getFechaActual());
          productoDocumento.setCantidad2(BigDecimal.ZERO);
          productoDocumento.setBodegasLogica1(bodega);
          productoDocumento.setBodegasLogica2(bodega);
          productoDocumento.setValorUnitatrioMl(BigDecimal.ZERO);
          productoDocumento.setValorUnitarioUsd(BigDecimal.ZERO);

          productoDocumento.setId(new ProductosXDocumentoPK());
          productoDocumento.getId().setIdProducto(pxc.getId());
          productoDocumento.setCantidad1(new BigDecimal(pxc.getCantidadDevolver()));

          Unidad u = new Unidad();
          u.setId(pxc.getUnidadId());
          productoDocumento.setUnidade(u); // unidad de venta

          Moneda moneda = new Moneda();
          moneda.setId("COP");
          productoDocumento.setMoneda(moneda);

          productos.add(productoDocumento);
        }
      }

      documento = this.ventasFacturacionEJBLocal.crearVentaDirecta(documento, auditoria, productos);

      this.addMensajeInfo("La mercancía ha sido recibida satisfactoriamente");

      this.listaProductos = new ArrayList<>();
      this.listaProductosSeleccionados = new ArrayList<>();
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError("Ha ocurrido un error guardando la devolución.");
    }
  }

  public StreamedContent generarVistaPrevia() {
    LOGGER.debug("Metodo: <<generarVistaPrevia>>");
    StreamedContent reporte = null;
    Map<String, Object> parametrosReporte = new HashMap<>();
    parametrosReporte.put("fechaGeneracion", getFechaActual());
    parametrosReporte.put("tiendaOrigen", ubicacionSeleccionada.getNombre());
    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaProductosSeleccionados, false);
    try {
      Hashtable<String, String> parametrosConfiguracionReporte;
      parametrosConfiguracionReporte = new Hashtable<>();
      parametrosConfiguracionReporte.put("tipo", "pdf");
      String reportePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/DevolucionMercanciaClienteTienda.jasper");
      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfiguracionReporte, reportePath, null, null, null, parametrosReporte, ds);
      reporte = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf", "DevolucionMercanciaClienteTienda.pdf");
    } catch (Exception e) {
      this.addMensajeError("Problemas al generar el reporte");
    }
    return reporte;
  }

  private void cargarCategoriasInventario() {
    List<CategoriasInventario> categorias = maestrosEjb.consultarCategoriasInventarios();
    SelectItemGroup group = null;
    categoriasInventarios = new ArrayList<>();
    for (CategoriasInventario categoriasInventario : categorias) {
      group = new SelectItemGroup(categoriasInventario.getNombre());
      group.setSelectItems(getSelectItems(categoriasInventario.getCategoriasInventarios()));
      categoriasInventarios.add(group);
    }

  }

  private SelectItem[] getSelectItems(List<CategoriasInventario> categoriasInventarios) {
    List<SelectItem> items = new ArrayList<>();
    SelectItem item = null;
    for (CategoriasInventario categoriasInventario : categoriasInventarios) {
      item = new SelectItem(categoriasInventario.getNombre(), categoriasInventario.getNombre());
      items.add(item);
    }
    return items.toArray(new SelectItem[categoriasInventarios.size()]);
  }

  public List<CategoriasInventario> getListaCategorias() {
    return listaCategorias;
  }

  public void setListaCategorias(List<CategoriasInventario> listaCategorias) {
    this.listaCategorias = listaCategorias;
  }

  public List<ProductoDevolucionDTO> getListaProductos() {
    return listaProductos;
  }

  public void setListaProductos(List<ProductoDevolucionDTO> listaProductos) {
    this.listaProductos = listaProductos;
  }

  public List<Ubicacion> getListaUbicaciones() {
    return listaUbicaciones;
  }

  public void setListaUbicaciones(List<Ubicacion> listaUbicaciones) {
    this.listaUbicaciones = listaUbicaciones;
  }

  public Ubicacion getUbicacionSeleccionada() {
    return ubicacionSeleccionada;
  }

  public void setUbicacionSeleccionada(Ubicacion ubicacionSeleccionada) {
    this.ubicacionSeleccionada = ubicacionSeleccionada;
  }

  public MenuMB getMenu() {
    return menu;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  public List<SelectItem> getCategoriasInventarios() {
    return categoriasInventarios;
  }

  public void setCategoriasInventarios(List<SelectItem> categoriasInventarios) {
    this.categoriasInventarios = categoriasInventarios;
  }

  public List<ProductoDevolucionDTO> getListaProductosSeleccionados() {
    return listaProductosSeleccionados;
  }

  public void setListaProductosSeleccionados(List<ProductoDevolucionDTO> listaProductosSeleccionados) {
    this.listaProductosSeleccionados = listaProductosSeleccionados;
  }

}
