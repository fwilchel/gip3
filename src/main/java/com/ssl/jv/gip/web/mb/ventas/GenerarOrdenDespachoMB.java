package com.ssl.jv.gip.web.mb.ventas;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * <p>
 * Title: GIP</p>
 * <p>
 * Description: GIP</p>
 * <p>
 * Copyright: Copyright (c) 2014</p>
 * <p>
 * Company: Soft Studio Ltda.</p>
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

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  private final Integer language = AplicacionMB.SPANISH;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<Documento> listaDocumentos;
  /**
   * Registro ventaDirectaSeleccionada
   */
  private Documento ventaDirectaSeleccionada;
  /**
   *
   */
  private List<ProductosXDocumento> listaProductosXDocumento;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
    cargarListaDocumentos();
  }

  /**
   *
   */
  public void cargarListaDocumentos() {
    LOGGER.debug("Metodo: <<cargarListaDocumentos>>");
    setListaDocumentos(ventasFacturacionEJB.consultarDocumentosOrdenDespacho());
  }

  /**
   *
   * @param documento
   */
  public void seleccionarDocumento(Documento documento) {
    LOGGER.debug("Metodo: <<seleccionarDocumento>>");
    ventaDirectaSeleccionada = documento;
    // TODO: concatenar valores del punto de venta para sobreescribir sitio de entrega.
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
    cargarListaProductosXDocumento();
  }

  private void cargarListaProductosXDocumento() {
    LOGGER.debug("Metodo: <<cargarListaProductosXDocumento>>");
    setListaProductosXDocumento(ventasFacturacionEJB.consultarProductosPorDocumento(ventaDirectaSeleccionada.getId()));
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
    formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    parametrosReporte.put("fechaGeneracion", formatoFecha.format(getFechaActual()));
    parametrosReporte.put("ordenCompraCliente", ventaDirectaSeleccionada.getDocumentoCliente());
    parametrosReporte.put("entidadFacturar", ventaDirectaSeleccionada.getCliente().getNombre());
    parametrosReporte.put("nombre", ventaDirectaSeleccionada.getPuntoVenta().getNombre());
    parametrosReporte.put("direccion", ventaDirectaSeleccionada.getPuntoVenta().getDireccion());
    parametrosReporte.put("telefono", ventaDirectaSeleccionada.getPuntoVenta().getTelefono());
    parametrosReporte.put("ciudad", ventaDirectaSeleccionada.getPuntoVenta().getCiudade().getNombre());
    formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    parametrosReporte.put("fechaMinEntrega", formatoFecha.format(ventaDirectaSeleccionada.getFechaEsperadaEntrega()));
    parametrosReporte.put("fechaMaxEntrega", formatoFecha.format(ventaDirectaSeleccionada.getFechaEntrega()));
    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaProductosXDocumento, false);
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
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    parametrosReporte.put("ubicacionDestino", ventaDirectaSeleccionada.getUbicacionDestino().getNombre());
    parametrosReporte.put("id", ventaDirectaSeleccionada.getId());
    parametrosReporte.put("consecutivo", ventaDirectaSeleccionada.getConsecutivoDocumento());
    parametrosReporte.put("entidadFacturar", ventaDirectaSeleccionada.getCliente().getNombre());
    parametrosReporte.put("fechaMinEntrega", (ventaDirectaSeleccionada.getFechaEsperadaEntrega()));
    parametrosReporte.put("fechaMaxEntrega", (ventaDirectaSeleccionada.getFechaEntrega()));
    parametrosReporte.put("sitioEntrega", ventaDirectaSeleccionada.getSitioEntrega());
    parametrosReporte.put("ordenCompraCliente", ventaDirectaSeleccionada.getDocumentoCliente());
    parametrosReporte.put("datos", listaProductosXDocumento);
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
      ventasFacturacionEJB.generarOrdenDespacho(ventaDirectaSeleccionada, listaProductosXDocumento);
      this.addMensajeInfo(AplicacionMB.getMessage("godMsgExito", language));
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
    LOGGER.debug("Metodo: <<getFechaActual>>");
    return new Date();
  }

  /**
   * @param appMB the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  /**
   * @return the listaDocumentos
   */
  public List<Documento> getListaDocumentos() {
    return listaDocumentos;
  }

  /**
   * @param listaDocumentos the listaDocumentos to set
   */
  public void setListaDocumentos(List<Documento> listaDocumentos) {
    this.listaDocumentos = listaDocumentos;
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
