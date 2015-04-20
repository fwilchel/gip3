package com.ssl.jv.gip.web.mb.ventas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;

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
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean(name = "generarOrdenDespachoMB")
@ViewScoped
public class GenerarOrdenDespachoMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(GenerarOrdenDespachoMB.class);

  @EJB
  private VentasFacturacionEJBLocal ventasFacturacionEJB;

  @EJB
  private ComunEJBLocal comunEJB;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  private final Integer language = AplicacionMB.SPANISH;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<Documento> listaVentasDirectas;
  /**
   * Registro ventaDirectaSeleccionada
   */
  private Documento ventaDirectaSeleccionada;
  /**
   *
   */
  private List<ProductosXDocumento> listaProductosXVentaDirecta;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
    consultarListaVentasDirectas();
  }

  /**
   *
   */
  public void consultarListaVentasDirectas() {
    LOGGER.debug("Metodo: <<consultarListaVentasDirectas>>");
    setListaVentasDirectas(ventasFacturacionEJB.consultarDocumentosOrdenDespacho());
    LOGGER.debug("listaVentasDirectas.size(): " + listaVentasDirectas.size());
  }

  /**
   *
   * @param documento
   */
  public void seleccionarVentaDirecta(Documento documento) {
    LOGGER.debug("Metodo: <<seleccionarVentaDirecta>>");
    ventaDirectaSeleccionada = documento;
    if (ventaDirectaSeleccionada.getPuntoVenta() != null) {
      StringBuilder sb = new StringBuilder();
      sb.append(ventaDirectaSeleccionada.getPuntoVenta().getDireccion());
      sb.append("---");
      sb.append(ventaDirectaSeleccionada.getPuntoVenta().getNombre());
      sb.append("---");
      sb.append(ventaDirectaSeleccionada.getPuntoVenta().getCiudade().getNombre());
      sb.append("---Tel: ");
      sb.append(ventaDirectaSeleccionada.getPuntoVenta().getTelefono());
      ventaDirectaSeleccionada.setSitioEntrega(sb.toString());
    }
    consultarListaProductosXVentaDirecta();
  }

  private void consultarListaProductosXVentaDirecta() {
    LOGGER.debug("Metodo: <<consultarListaProductosXVentaDirecta>>");
    setListaProductosXVentaDirecta(ventasFacturacionEJB.consultarProductosPorDocumento(ventaDirectaSeleccionada.getId()));
  }

  /**
   *
   * @return
   */
  public StreamedContent generarVistaPrevia() {
    LOGGER.debug("Metodo: <<generarVistaPrevia>>");
    StreamedContent reporte = null;
    Map<String, Object> parametrosReporte = new HashMap<>();
    SimpleDateFormat formatoFecha;
    parametrosReporte.put("id", ventaDirectaSeleccionada.getId());
    parametrosReporte.put("consecutivo", ventaDirectaSeleccionada.getConsecutivoDocumento());
    formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    parametrosReporte.put("fechaGeneracion", formatoFecha.format(getFechaActual()));
    parametrosReporte.put("ordenCompraCliente", ventaDirectaSeleccionada.getDocumentoCliente());
    parametrosReporte.put("entidadFacturar", ventaDirectaSeleccionada.getCliente() == null ? "" : ventaDirectaSeleccionada.getCliente().getNombre());
    parametrosReporte.put("nombre", ventaDirectaSeleccionada.getPuntoVenta() == null ? "" : ventaDirectaSeleccionada.getPuntoVenta().getNombre());
    parametrosReporte.put("direccion", ventaDirectaSeleccionada.getPuntoVenta() == null ? "" : ventaDirectaSeleccionada.getPuntoVenta().getDireccion());
    parametrosReporte.put("telefono", ventaDirectaSeleccionada.getPuntoVenta() == null ? "" : ventaDirectaSeleccionada.getPuntoVenta().getTelefono());
    parametrosReporte.put("ciudad", ventaDirectaSeleccionada.getPuntoVenta() == null ? "" : ventaDirectaSeleccionada.getPuntoVenta().getCiudade().getNombre());
    formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    parametrosReporte.put("fechaMinEntrega", ventaDirectaSeleccionada.getFechaEsperadaEntrega() == null ? "" : formatoFecha.format(ventaDirectaSeleccionada.getFechaEsperadaEntrega()));
    parametrosReporte.put("fechaMaxEntrega", ventaDirectaSeleccionada.getFechaEntrega() == null ? "" : formatoFecha.format(ventaDirectaSeleccionada.getFechaEntrega()));
    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaProductosXVentaDirecta, false);
    try {
      Hashtable<String, String> parametrosConfiguracionReporte;
      parametrosConfiguracionReporte = new Hashtable<>();
      parametrosConfiguracionReporte.put("tipo", "pdf");
      String reportePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/ReporteOrdenDespachoVF.jasper");
      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfiguracionReporte, reportePath, null, null, null, parametrosReporte, ds);
      reporte = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf", "ReporteOrdenDespachoVF.pdf");
    } catch (Exception e) {
      this.addMensajeError("Problemas al generar el reporte");
    }
    return reporte;
  }

  /**
   *
   * @return
   */
  public StreamedContent generarExcel() {
    LOGGER.debug("Metodo: <<generarExcel>>");
    StreamedContent reporte = null;
    Map<String, Object> parametrosReporte = new HashMap<>();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
    parametrosReporte.put("ubicacionDestino", ventaDirectaSeleccionada.getUbicacionDestino() == null ? "" : ventaDirectaSeleccionada.getUbicacionDestino().getNombre());
    parametrosReporte.put("id", ventaDirectaSeleccionada.getId());
    parametrosReporte.put("consecutivo", ventaDirectaSeleccionada.getConsecutivoDocumento());
    parametrosReporte.put("entidadFacturar", ventaDirectaSeleccionada.getCliente() == null ? "" : ventaDirectaSeleccionada.getCliente().getNombre());
    parametrosReporte.put("fechaMinEntrega", ventaDirectaSeleccionada.getFechaEsperadaEntrega() == null ? "" : formatoFecha.format(ventaDirectaSeleccionada.getFechaEsperadaEntrega()));
    parametrosReporte.put("fechaMaxEntrega", ventaDirectaSeleccionada.getFechaEntrega() == null ? "" : formatoFecha.format(ventaDirectaSeleccionada.getFechaEntrega()));
    parametrosReporte.put("sitioEntrega", ventaDirectaSeleccionada.getSitioEntrega());
    parametrosReporte.put("ordenCompraCliente", ventaDirectaSeleccionada.getDocumentoCliente());
    parametrosReporte.put("datos", listaProductosXVentaDirecta);
    try {
      StringBuilder nombreArchivo = new StringBuilder();
      nombreArchivo.append(ventaDirectaSeleccionada.getConsecutivoDocumento());
      nombreArchivo.append("-");
      nombreArchivo.append(formatoFecha.format(getFechaActual()));
      nombreArchivo.append(".");
      nombreArchivo.append("xls");
      Hashtable<String, String> parametrosConfiguracionReporte;
      parametrosConfiguracionReporte = new Hashtable<>();
      parametrosConfiguracionReporte.put("tipo", "jxls");
      String reportePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/ReporteOrdenDespachoVF.xls");
      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfiguracionReporte, reportePath, null, null, null, parametrosReporte, null);
      reporte = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel", nombreArchivo.toString());
    } catch (Exception e) {
      this.addMensajeError("Problemas al generar el reporte");
    }
    return reporte;
  }

  /**
   *
   */
  public void generarOrdenDespacho() {
    LOGGER.debug("Metodo: <<generarOrdenDespacho>>");
    try {
      ventasFacturacionEJB.generarOrdenDespacho(ventaDirectaSeleccionada, listaProductosXVentaDirecta);
      this.addMensajeInfo(AplicacionMB.getMessage("godMsgExito", language));
      consultarListaVentasDirectas();
    } catch (Exception ex) {
      this.addMensajeError(AplicacionMB.getMessage("godMsgError", language));
    }
  }

  /**
   *
   */
  public void cancelar() {
    LOGGER.debug("Metodo: <<cancelar>>");
    this.ventaDirectaSeleccionada = null;
  }

  /**
   *
   * @return
   */
  public Date getFechaActual() {
    return new Date();
  }

  /**
   * @param appMB the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  /**
   * @return the listaVentasDirectas
   */
  public List<Documento> getListaVentasDirectas() {
    return listaVentasDirectas;
  }

  /**
   * @param listaVentasDirectas the listaVentasDirectas to set
   */
  public void setListaVentasDirectas(List<Documento> listaVentasDirectas) {
    this.listaVentasDirectas = listaVentasDirectas;
  }

  /**
   * @return the ventaDirectaSeleccionada
   */
  public Documento getVentaDirectaSeleccionada() {
    return ventaDirectaSeleccionada;
  }

  /**
   * @param ventaDirectaSeleccionada the ventaDirectaSeleccionada to set
   */
  public void setVentaDirectaSeleccionada(Documento ventaDirectaSeleccionada) {
    this.ventaDirectaSeleccionada = ventaDirectaSeleccionada;
  }

  /**
   * @return the listaProductosXVentaDirecta
   */
  public List<ProductosXDocumento> getListaProductosXVentaDirecta() {
    return listaProductosXVentaDirecta;
  }

  /**
   * @param listaProductosXVentaDirecta the listaProductosXVentaDirecta to set
   */
  public void setListaProductosXVentaDirecta(List<ProductosXDocumento> listaProductosXVentaDirecta) {
    this.listaProductosXVentaDirecta = listaProductosXVentaDirecta;
  }

}
