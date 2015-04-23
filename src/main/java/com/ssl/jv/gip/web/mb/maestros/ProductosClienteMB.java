package com.ssl.jv.gip.web.mb.maestros;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

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
  private String estadoFlt;
  private List<ProductosXCliente> productoXClienteLista;
  private ProductosXCliente productoXClienteSeleccionado;
  private List<ProductosXCliente> productoXClienteListaSeleccionados;
  private Modo modo;

  @PostConstruct
  public void init() {
    LOGGER.trace("Metodo: <<init>>");
    modo = Modo.LISTADO;
    estadoFlt = "A";
  }

  /**
   *
   */
  public void onConsultEvent() {
    LOGGER.trace("Metodo: <<onConsultEvent>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("sku", skuFlt);
    parametros.put("nombreCliente", nombreClienteFlt);
    parametros.put("nombrePuntoVenta", nombreSitioEntregaFlt);
    switch (estadoFlt) {
      case "A":
        parametros.put("activo", true);
        break;
      case "T":
        parametros.put("activo", false);
        break;
    }
    productoXClienteLista = maestrosEJB.consultarProductosXCliente(parametros);
  }

  public void onCreateEvent() {
    this.modo = Modo.CREACION;
    this.productoXClienteSeleccionado = new ProductosXCliente();
    this.productoXClienteSeleccionado.setCliente(new Cliente());
    this.productoXClienteSeleccionado.setProductosInventario(new ProductosInventario());
    this.productoXClienteSeleccionado.setPuntoVenta(new PuntoVenta());
    this.productoXClienteSeleccionado.setMoneda(new Moneda());
    this.initEdit();
  }

  public void onEditEvent() {
    this.modo = Modo.EDICION;
    this.initEdit();
    this.productoXClienteListaSeleccionados = new ArrayList<>();
    this.productoXClienteListaSeleccionados.add(productoXClienteSeleccionado);
  }

  private void initEdit() {
    // cargar listas e inicializar objetos compartidos
  }

  public void guardar() {
    if(this.isModoCreacion()) {
      
    } else {
      
    }
    this.onConsultEvent();
  }
  
  private void reset(){
	modo = Modo.LISTADO;
    productoXClienteSeleccionado = null;
    productoXClienteListaSeleccionados.clear();
  }

  public void onBackToListEvent() {
	this.reset();
  }

  /**
  *
  * @return
  */
 public StreamedContent onGenerateEcxelEvent() {
   LOGGER.debug("Metodo: <<onGenerateEcxelEvent>>");
   StreamedContent reporte = null;
   Map<String, Object> parametrosReporte = new HashMap<>();
   parametrosReporte.put("datos", productoXClienteLista);
   try {
     Hashtable<String, String> parametrosConfiguracionReporte;
     parametrosConfiguracionReporte = new Hashtable<>();
     parametrosConfiguracionReporte.put("tipo", "jxls");
     String reportePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/ReporteMaestroPXC.xls");
     ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfiguracionReporte, reportePath, null, null, null, parametrosReporte, null);
     reporte = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel", "ReporteMaestroPXC.xls");
   } catch (Exception e) {
     this.addMensajeError("Problemas al generar el reporte");
   }
   return reporte;
 }

  /**
   * 
   * @return 
   */
  public boolean isModoListado(){
    return modo.equals(Modo.LISTADO);
  }

  /**
   * 
   * @return 
   */
  public boolean isModoCreacion(){
    return modo.equals(Modo.CREACION);
  }

  /**
   *
   * @return
   */
  public boolean isModoEdicion() {
    return modo.equals(Modo.EDICION);
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
  public String getEstadoFlt() {
    return estadoFlt;
  }

  /**
   * @param estadoFlt the estadoFlt to set
   */
  public void setEstadoFlt(String estadoFlt) {
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

  /**
   * @return the productoXClienteListaSeleccionados
   */
  public List<ProductosXCliente> getProductoXClienteListaSeleccionados() {
    return productoXClienteListaSeleccionados;
  }

  /**
   * @param productoXClienteListaSeleccionados the productoXClienteListaSeleccionados to set
   */
  public void setProductoXClienteListaSeleccionados(List<ProductosXCliente> productoXClienteListaSeleccionados) {
    this.productoXClienteListaSeleccionados = productoXClienteListaSeleccionados;
  }
}
