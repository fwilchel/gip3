package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.dto.DocumentoReporteVentasCEDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJB;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;

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
  private ReportesComercioExteriorEJB reportesComercioExteriorEJB;
  /**
   *
   */
  @EJB
  private MaestrosEJBLocal maestrosEJB;
  /**
   *
   */
  private Date filtroFechaInicial;
  /**
   *
   */
  private Date filtroFechaFinal;
  /**
   * filtro por nombre la lista de clientes
   */
  private String filtroNombreCliente;
  /**
   * lista de clientes que retorna la consulta a la base de datos
   */
  private List<Cliente> listaClientes;
  /**
   * lista de los clientes seleccionados
   */
  private List<Cliente> listaClientesSeleccionados;
  /**
   * filtro por sku la lista de productos
   */
  private String filtroSKUProducto;
  /**
   * filtro por nombre la lista de productos
   */
  private String filtroNombreProducto;
  /**
   * lista de clientes que retorna la consulta a la base de datos
   */
  private List<ProductosInventario> listaProductos;
  /**
   * lista de los clientes seleccionados
   */
  private List<ProductosInventario> listaProductosSeleccionados;

  /**
   *
   */
  private LazyDataModel<ProductosInventario> listaProductosLazyModel;
  private String campoOrden;
  private SortOrder orden;
  private ProductosInventario filtro;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
  }

  /**
   *
   */
  public void buscarClientes() {
    LOGGER.debug("Metodo: <<buscarClientes>>");
    Map<String, Object> parametros = new HashMap();
    if (filtroNombreCliente != null && !filtroNombreCliente.isEmpty()) {
      parametros.put("nombre", filtroNombreCliente);
    }
    parametros.put("idUsuario", menuMB.getUsuario().getId());
    setListaClientes(reportesComercioExteriorEJB.consultarListadoClientesReporteVentasCE(parametros));
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
    setListaProductosLazyModel(new LazyProductsDataModel());
    filtro = new ProductosInventario();
    filtro.setDesactivado(true);
//    Map<String, Object> parametros = new HashMap();
    if (filtroSKUProducto != null && !filtroSKUProducto.isEmpty()) {
//      parametros.put("sku", filtroSKUProducto);
      filtro.setSku(filtroSKUProducto);
    }
    if (filtroNombreProducto != null && !filtroNombreProducto.isEmpty()) {
//      parametros.put("nombre", filtroNombreProducto);
      filtro.setNombre(filtroNombreProducto);
    }
//    setListaProductos(reportesComercioExteriorEJB.consultarListadoProductosReporteVentasCE(parametros));
  }

  /**
   *
   */
  public void asignarProductosSeleccionados() {
    LOGGER.debug("Metodo: <<asignarProductosSeleccionados>>");
  }

  /**
   *
   * @return
   */
  public StreamedContent generarReporteVentas() {
    LOGGER.debug("Metodo: <<generarReporteVentas>>");
    Map<String, Object> parametrosConsulta = new HashMap();
    parametrosConsulta.put("fechaInicial", filtroFechaInicial);
    parametrosConsulta.put("fechaFinal", filtroFechaFinal);
    if (listaClientesSeleccionados != null && !listaClientesSeleccionados.isEmpty()) {
      List<Long> idsClientesSeleccionados = new ArrayList<>();
      for (Cliente cliente : listaClientesSeleccionados) {
        idsClientesSeleccionados.add(cliente.getId());
      }
      parametrosConsulta.put("idsClientes", idsClientesSeleccionados);
    }
    if (listaProductosSeleccionados != null && !listaProductosSeleccionados.isEmpty()) {
      List<Long> idsProductosSeleccionados = new ArrayList<>();
      for (ProductosInventario producto : listaProductosSeleccionados) {
        idsProductosSeleccionados.add(producto.getId());
      }
      parametrosConsulta.put("idsProductos", idsProductosSeleccionados);
    }
    List<DocumentoReporteVentasCEDTO> listaDocumentosReporteVentasCEDTO;
    listaDocumentosReporteVentasCEDTO = reportesComercioExteriorEJB.consultarDocumentosReporteVentasCE(parametrosConsulta);
    // generar reporte
    StreamedContent reporteXLS = null;
    Map<String, Object> parametrosReporte = new HashMap<>();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    parametrosReporte.put("fechaInicial", formatoFecha.format(filtroFechaInicial));
    parametrosReporte.put("fechaFinal", formatoFecha.format(filtroFechaFinal));
    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaDocumentosReporteVentasCEDTO, false);
    try {
      Hashtable<String, String> parametrosConfiguracionReporte;
      parametrosConfiguracionReporte = new Hashtable<>();
      parametrosConfiguracionReporte.put("tipo", "xls");
      String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_VCE.jasper");
      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfiguracionReporte, reporte, null, null, null, parametrosReporte, ds);
      reporteXLS = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel ", "Reporte_VCE.xls");
    } catch (Exception e) {
      this.addMensajeError("Problemas al generar el reporte");
    }
    return reporteXLS;
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
      campoOrden = sortField;
      orden = sortOrder;
      Object rta[] = maestrosEJB.consultarProductos(filtro, first, pageSize, sortField, sortOrder, true);
      this.setRowCount(((Long) rta[0]).intValue());
      rta = maestrosEJB.consultarProductos(filtro, first, pageSize, sortField, sortOrder, false);
      datos = (List<ProductosInventario>) rta[1];
      return datos;
    }

  }

}
