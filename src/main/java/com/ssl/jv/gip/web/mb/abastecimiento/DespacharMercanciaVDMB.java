package com.ssl.jv.gip.web.mb.abastecimiento;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.BodegasLogica;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.ProductoDespacharMercanciaDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.DespachoMercanciaEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

@ManagedBean(name = "despacharMercanciaMB")
@ViewScoped
public class DespacharMercanciaVDMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private Timestamp currentTimeStamp;
  private String consecutivoDocumento;
  private List<Documento> documentos;
  private List<ProductoDespacharMercanciaDTO> productos;
  private List<ProductoDespacharMercanciaDTO> productosSeleccionados;
  List<Estado> estados;
  private Documento seleccionado;
  private Documento filtro;
  private boolean listo;
  private StreamedContent reporteExcel;
  private StreamedContent reportePDF;

  private JasperPrint jasperPrint;

  @EJB
  private DespachoMercanciaEJBLocal despachoMercancia;
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJB;
  @EJB
  private ComunEJBLocal comunEJBlocal;

  private Integer language = AplicacionMB.SPANISH;

  @PostConstruct
  public void init() {
    currentTimeStamp = new Timestamp(System.currentTimeMillis());
    estados = comunEJBlocal.consultarEstados();
  }

  public String buscarDocumentos() {
    documentos = despachoMercancia.consultarVentasDirectas(consecutivoDocumento);
    return null;
  }

  public void consultarProductosVentaDirecta() {
    productos = despachoMercancia.consultarProductoPorDocumento(seleccionado.getId() + "", seleccionado.getCliente().getId() + "");
  }

  public void despacharVentaDirecta() {
    List<Unidad> unidades = comunEJBlocal.consultarUnidades();
    List<Moneda> monedas = comunEJBlocal.consultarMonedas();
    List<ProductosXDocumento> pxd = comercioExteriorEJB.consultarProductosXDocumentosPorDocumento(seleccionado.getId());
    BodegasLogica bodegas = new BodegasLogica();
    bodegas.setId(0L);
    bodegas.setNombre("Default");
    bodegas.setTipoBodega("default");
    if (seleccionado.getEstadosxdocumento().getTipoDocumento().getId() == ConstantesTipoDocumento.ORDEN_DESPACHO) {

    } else {
      try {
        List<Estado> estados = comunEJBlocal.consultarEstados();

        for (Estado estado : estados) {
          if (estado.getId() == ConstantesDocumento.CERRADO) {
            seleccionado.getEstadosxdocumento().setEstado(estado);
            this.comercioExteriorEJB.actualizarEstadoDocumento(this.seleccionado);
          }
        }
        this.addMensajeInfo(AplicacionMB.getMessage("UsuarioExitoPaginaTexto", language));
      } catch (Exception ex) {
        ex.printStackTrace();
        this.addMensajeError(AplicacionMB.getMessage("NivelInventarioError", language));
      }
      try {
        MovimientosInventario movimiento = new MovimientosInventario();
        movimiento.setDocumento(seleccionado);
        movimiento.setFecha(new Timestamp(System.currentTimeMillis()));
        movimiento.setUbicacionOrigen(seleccionado.getUbicacionOrigen());
        movimiento.setUbicacionDestino(seleccionado.getUbicacionDestino());// Externa
        movimiento.setBodegasLogica1(bodegas);
        movimiento.setBodegasLogica2(bodegas);
        for (ProductoDespacharMercanciaDTO p : productosSeleccionados) {
          movimiento.setCantidad(p.getCantidadAdespachar());
          for (Moneda moneda : monedas) {
            if (moneda.getId() == p.getMoneda()) {
              movimiento.setMoneda(moneda);
            }
          }
          for (ProductosXDocumento prod : pxd) {
            if (prod.getId().getIdProducto() + "" == p.getId()) {
              movimiento.setProductosInventario(prod.getProductosInventario());
            }
          }
          for (Unidad unidad : unidades) {
            if (unidad.getAbreviacion() == p.getUnidadVenta()) {
              movimiento.setUnidade(unidad);
            }
          }
          movimiento.setValorUnitarioMl(0);
          movimiento.setValotUnitarioUsd(0);
          try {
            despachoMercancia.crearMovimientoInventario(movimiento);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        this.addMensajeInfo(AplicacionMB.getMessage("UsuarioExitoPaginaTexto", language));
      } catch (Exception ex) {
        ex.printStackTrace();
        this.addMensajeError(AplicacionMB.getMessage("NivelInventarioError", language));
      }
    }
    // reload list
    documentos = despachoMercancia.consultarVentasDirectas(consecutivoDocumento);
    // reset
    seleccionado = null;
    consecutivoDocumento = null;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public JasperPrint reportBuilder() throws JRException {
    Map parametros = new HashMap();
    parametros.put("id", seleccionado.getId());
    parametros.put("consecutivoDocumento", seleccionado.getConsecutivoDocumento());
    parametros.put("entidadAfacturar", "");
    parametros.put("SitioEntrega", "");
    parametros.put("OrdenCompraCliente", "");
    parametros.put("FechaMin", seleccionado.getFechaEsperadaEntrega().toString());
    parametros.put("FechaMax", seleccionado.getFechaEntrega().toString());

    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(productos);

    String report = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_VD.jasper");

    return JasperFillManager.fillReport(report, parametros, beanCollectionDataSource);
  }

  public List<Documento> getDocumentos() {
    return documentos;
  }

  public void setDocumentos(List<Documento> documentos) {
    this.documentos = documentos;
  }

  public List<ProductoDespacharMercanciaDTO> getProductos() {
    return productos;
  }

  public void setProductos(List<ProductoDespacharMercanciaDTO> productos) {
    this.productos = productos;
  }

  /**
   * @return the productosSeleccionados
   */
  public List<ProductoDespacharMercanciaDTO> getProductosSeleccionados() {
    return productosSeleccionados;
  }

  /**
   * @param productosSeleccionados the productosSeleccionados to set
   */
  public void setProductosSeleccionados(List<ProductoDespacharMercanciaDTO> productosSeleccionados) {
    this.productosSeleccionados = productosSeleccionados;
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

  public DespachoMercanciaEJBLocal getDespachoMercancia() {
    return despachoMercancia;
  }

  public void setDespachoMercancia(DespachoMercanciaEJBLocal despachoMercancia) {
    this.despachoMercancia = despachoMercancia;
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

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public boolean isListo() {
    return listo;
  }

  public void setListo(boolean listo) {
    this.listo = listo;
  }

  public StreamedContent getReporteExcel() throws ClassNotFoundException, IOException, JRException {
    comercioExteriorEJB.generarReporteOrdenDespachoExcel(reportBuilder(), seleccionado.getId());
    ByteArrayOutputStream os = (ByteArrayOutputStream) comercioExteriorEJB.generar(jasperPrint, "", "xls");
    reporteExcel = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel ", "OrdenDespacho.xls");
    return this.reporteExcel;
  }

  public void setReporteExcel(StreamedContent reporteExcel) {
    this.reporteExcel = reporteExcel;
  }

  public StreamedContent getReportePDF() throws ClassNotFoundException, IOException, JRException {
    comercioExteriorEJB.generarReporteOrdenDespachoPDF(reportBuilder(), seleccionado.getId());
    ByteArrayOutputStream os = (ByteArrayOutputStream) comercioExteriorEJB.generar(jasperPrint, "A", "pdf");
    reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf", "OD" + seleccionado.getId());
    return reportePDF;
  }

  public void setReportePDF(StreamedContent reportePDF) {
    this.reportePDF = reportePDF;
  }

}
