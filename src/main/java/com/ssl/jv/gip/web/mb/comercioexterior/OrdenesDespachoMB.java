package com.ssl.jv.gip.web.mb.comercioexterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoODDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;

/**
 * Managed Bean para ordenes de despacho
 *
 * @author Daniel Cortes
 * @version 1.0
 * @email danicorc@gmail.com
 *
 */
@ManagedBean(name = "ordenesDespachoMB")
@ViewScoped
public class OrdenesDespachoMB extends UtilMB {

  private static final long serialVersionUID = 1L;

  private Timestamp currentTimeStamp;
  private String consecutivoDocumento;
  private boolean deshabilitado = false;
  private List<Documento> documentos;
  private static List<ProductoODDTO> productos;
  private Documento seleccionado;
  private Documento filtro;
  private double totalCantidad = 0;
  private double totalCantidadPorEmbalaje = 0;
  private double totalCantidadCajas = 0;
  private double muestrasFITOANTICO = 0;
  private double muestrasCalidades = 0;
  private StreamedContent reporteExcel;
  private StreamedContent reportePDF;

  private JasperPrint jasperPrint;

  @EJB
  private ComercioExteriorEJBLocal comercioEjb;

  private Integer language = AplicacionMB.SPANISH;

  public OrdenesDespachoMB() {
  }

  @PostConstruct
  public void init() {
    currentTimeStamp = new Timestamp(System.currentTimeMillis());
  }

  public String buscarDocumentos() {
    documentos = this.comercioEjb.consultarFP(consecutivoDocumento, (long) ConstantesDocumento.APROBADA, (long) ConstantesDocumento.ASIGNADA);
    this.deshabilitado = false;
    return null;
  }

  public void consultarOrdenDeDespacho() {
    productos = comercioEjb.consultarProductoPorDocumentoOrdenDespacho(seleccionado.getId(), seleccionado.getCliente().getId(), seleccionado.getDocumentoXNegociacions().get(0).getSolicitudCafe());
    totalCantidad = 0;
    totalCantidadCajas = 0;
    totalCantidadPorEmbalaje = 0;
    muestrasFITOANTICO = 0;
    muestrasCalidades = 0;
    for (ProductoODDTO p : productos) {
      p.setCantidadCajas(p.getCantidad().divide(p.getCantidadPorEmbalaje()));
      this.totalCantidad += p.getCantidad().doubleValue();
      this.totalCantidadCajas += p.getCantidadCajas().doubleValue();
      this.totalCantidadPorEmbalaje += p.getCantidadPorEmbalaje().doubleValue();
      this.muestrasCalidades += p.getMuestrasCalidades().doubleValue();
      this.muestrasFITOANTICO += p.getMuestrasFITOYANTICO().doubleValue();
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public JasperPrint reportBuilder() throws JRException {
    SimpleDateFormat formatoFecha;
    formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    Map parametros = new HashMap();
    parametros.put("cliente", seleccionado.getCliente().getNombre());
    parametros.put("nit", seleccionado.getCliente().getNit());
    parametros.put("ciudad", seleccionado.getCliente().getCiudad().getNombre());
    parametros.put("direccion", seleccionado.getCliente().getDireccion());
    parametros.put("telefono", seleccionado.getCliente().getTelefono());
    parametros.put("contacto", seleccionado.getCliente().getContacto());
    parametros.put("documento", seleccionado.getDocumentoCliente());
    parametros.put("fecha", formatoFecha.format(seleccionado.getFechaGeneracion()));
    parametros.put("numFactura", seleccionado.getConsecutivoDocumento());
    parametros.put("fechaCargue", seleccionado.getFechaEntrega() != null ? formatoFecha.format(seleccionado.getFechaEntrega()) : null);
    parametros.put("solicitud", seleccionado.getObservacionDocumento());
    parametros.put("observacionDoc", seleccionado.getObservacion2());
    parametros.put("observacionMar", seleccionado.getDocumentoXNegociacions().get(0).getObservacionesMarcacion2());
    parametros.put("despacho", seleccionado.getSitioEntrega());
    parametros.put("totalCantidad", totalCantidad);
    parametros.put("totalUnidadXEmbalaje", totalCantidadPorEmbalaje);
    parametros.put("totalCantidadCajas", totalCantidadCajas);
    parametros.put("totalMuestrasFito", muestrasFITOANTICO);
    parametros.put("totalMuestrasCalidades", muestrasCalidades);

    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(productos);

    String report = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_OD.jasper");

    return JasperFillManager.fillReport(report, parametros, beanCollectionDataSource);
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public boolean isDeshabilitado() {
    return deshabilitado;
  }

  public void setDeshabilitado(boolean deshabilitado) {
    this.deshabilitado = deshabilitado;
  }

  public List<Documento> getDocumentos() {
    return documentos;
  }

  public void setDocumentos(List<Documento> documentos) {
    this.documentos = documentos;
  }

  public List<ProductoODDTO> getProductos() {
    return productos;
  }

  public static void setProductos(List<ProductoODDTO> productos2) {
    productos = productos2;
  }

  public Documento getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(Documento seleccionado) {
    this.seleccionado = seleccionado;
  }

  public Documento getFiltro() {
    return filtro;
  }

  public void setFiltro(Documento filtro) {
    this.filtro = filtro;
  }

  public double getTotalCantidad() {
    return totalCantidad;
  }

  public void setTotalCantidad(double totalCantidad) {
    this.totalCantidad = totalCantidad;
  }

  public double getTotalCantidadPorEmbalaje() {
    return totalCantidadPorEmbalaje;
  }

  public void setTotalCantidadPorEmbalaje(double totalCantidadPorEmbalaje) {
    this.totalCantidadPorEmbalaje = totalCantidadPorEmbalaje;
  }

  public double getTotalCantidadCajas() {
    return totalCantidadCajas;
  }

  public void setTotalCantidadCajas(double totalCantidadCajas) {
    this.totalCantidadCajas = totalCantidadCajas;
  }

  public double getMuestrasFITOANTICO() {
    return muestrasFITOANTICO;
  }

  public void setMuestrasFITOANTICO(double muestrasFITOANTICO) {
    this.muestrasFITOANTICO = muestrasFITOANTICO;
  }

  public double getMuestrasCalidades() {
    return muestrasCalidades;
  }

  public void setMuestrasCalidades(double muestrasCalidades) {
    this.muestrasCalidades = muestrasCalidades;
  }

  public Integer getLanguage() {
    return language;
  }

  public void setLanguage(Integer language) {
    this.language = language;
  }

  public Timestamp getCurrentTimeStamp() {
    return currentTimeStamp;
  }

  public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
    this.currentTimeStamp = currentTimeStamp;
  }

  public StreamedContent getReporteExcel() throws ClassNotFoundException, IOException, JRException {
    comercioEjb.generarReporteOrdenDespachoExcel(reportBuilder(), seleccionado.getId());
    ByteArrayOutputStream os = (ByteArrayOutputStream) comercioEjb.generar(jasperPrint, "", "xls");
    reporteExcel = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel ", "OrdenDespacho.xls");
    return this.reporteExcel;
  }

  public void setReporteExcel(StreamedContent reporteExcel) {
    this.reporteExcel = reporteExcel;
  }

  public StreamedContent getReportePDF() throws ClassNotFoundException, IOException, JRException {
    comercioEjb.generarReporteOrdenDespachoPDF(reportBuilder(), seleccionado.getId());
    ByteArrayOutputStream os = (ByteArrayOutputStream) comercioEjb.generar(jasperPrint, "A", "pdf");
    reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf", "OD" + seleccionado.getId());
    return reportePDF;
  }

  public void setReportePDF(StreamedContent reportePDF) {
    this.reportePDF = reportePDF;
  }
}
