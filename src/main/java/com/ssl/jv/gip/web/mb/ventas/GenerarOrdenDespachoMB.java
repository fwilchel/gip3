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
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
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
   * Registro seleccionado
   */
  private Documento seleccionado;
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
    seleccionado = documento;
    // TODO: concatenar valores del punto de venta para sobreescribir sitio de entrega.
    if (seleccionado.getPuntoVenta() != null) {
      StringBuilder sb = new StringBuilder();
      sb.append(seleccionado.getPuntoVenta().getDireccion());
      sb.append("---");
      sb.append(seleccionado.getPuntoVenta().getNombre());
      sb.append("---");
      sb.append(seleccionado.getPuntoVenta().getCiudade().getNombre());
      sb.append("---Tel: ");
      sb.append(seleccionado.getPuntoVenta().getTelefono());
      seleccionado.setSitioEntrega(sb.toString());
    }
    cargarListaProductosXDocumento();
  }

  private void cargarListaProductosXDocumento() {
    LOGGER.debug("Metodo: <<cargarListaProductosXDocumento>>");
    setListaProductosXDocumento(ventasFacturacionEJB.consultarProductosPorDocumento(seleccionado.getId()));
  }

  /**
   *
   * @return
   */
  public StreamedContent generarVistaPrevia() {
    LOGGER.debug("Metodo: <<generarVistaPrevia>>");
    StreamedContent reporte = null;
    Map<String, Object> parametrosReporte = new HashMap<>();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    parametrosReporte.put("id", seleccionado.getId());
    parametrosReporte.put("fechaGeneracion", getFechaActual());
    parametrosReporte.put("ordenCompraCliente", seleccionado.getDocumentoCliente());
    parametrosReporte.put("entidadFacturar", seleccionado.getCliente().getNombre());
    parametrosReporte.put("nombre", seleccionado.getPuntoVenta().getNombre());
    parametrosReporte.put("direccion", seleccionado.getPuntoVenta().getDireccion());
    parametrosReporte.put("telefono", seleccionado.getPuntoVenta().getTelefono());
    parametrosReporte.put("ciudad", seleccionado.getPuntoVenta().getCiudade().getNombre());
    parametrosReporte.put("fechaMinEntrega", (seleccionado.getFechaEsperadaEntrega()));
    parametrosReporte.put("fechaMaxEntrega", (seleccionado.getFechaEntrega()));
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
    parametrosReporte.put("ubicacionDestino", seleccionado.getUbicacionDestino().getNombre());
    parametrosReporte.put("id", seleccionado.getId());
    parametrosReporte.put("consecutivo", seleccionado.getConsecutivoDocumento());
    parametrosReporte.put("entidadFacturar", seleccionado.getCliente().getNombre());
    parametrosReporte.put("fechaMinEntrega", (seleccionado.getFechaEsperadaEntrega()));
    parametrosReporte.put("fechaMaxEntrega", (seleccionado.getFechaEntrega()));
    parametrosReporte.put("sitioEntrega", seleccionado.getSitioEntrega());
    parametrosReporte.put("ordenCompraCliente", seleccionado.getDocumentoCliente());
    parametrosReporte.put("datos", listaProductosXDocumento);
    try {
      StringBuilder nombreArchivo = new StringBuilder();
      nombreArchivo.append(seleccionado.getConsecutivoDocumento());
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
//    try{
    ventasFacturacionEJB.generarOrdenDespacho(seleccionado, listaProductosXDocumento);
    this.addMensajeInfo(AplicacionMB.getMessage("godMsgExito", language));
//    }catch(){
    this.addMensajeError(AplicacionMB.getMessage("godMsgError", language));
//    }
  }

  /**
   *
   */
  public void cancelar() {
    LOGGER.debug("Metodo: <<cancelar>>");
    this.seleccionado = null;
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
   * @return the seleccionado
   */
  public Documento getSeleccionado() {
    return seleccionado;
  }

  /**
   * @param seleccionado the seleccionado to set
   */
  public void setSeleccionado(Documento seleccionado) {
    this.seleccionado = seleccionado;
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
