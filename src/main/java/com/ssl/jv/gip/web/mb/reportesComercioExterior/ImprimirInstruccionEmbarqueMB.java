package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import com.ssl.jv.gip.negocio.dto.DocTerminosTransporteDTO;
import com.ssl.jv.gip.negocio.dto.InstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.DefaultStreamedContent;
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
@ManagedBean(name = "imprimirInstruccionEmbarque")
@ViewScoped
public class ImprimirInstruccionEmbarqueMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(ImprimirInstruccionEmbarqueMB.class);

  @EJB
  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEjb;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  private final Integer language = AplicacionMB.SPANISH;
  /**
   * lista de documentos que retorna la consulta a la base de datos
   */
  private List<InstruccionEmbarqueDTO> listaTerminosTransporte;
  /**
   * Registro seleccionado
   */
  private InstruccionEmbarqueDTO seleccionado;
  /**
   *
   */
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJB;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
    consultarListadoImprimirInstruccionEmbarque();
  }

  /**
   *
   */
  public void consultarListadoImprimirInstruccionEmbarque() {
    LOGGER.debug("Metodo: <<consultarListadoImprimirInstruccionEmbarque>>");
    setListaTerminosTransporte(reportesComercioExteriorEjb.consultarListadoInstruccionesEmbarque());
  }

  /**
   *
   * @param seleccionado
   */
  public void seleccionarInstruccionEmbarque(InstruccionEmbarqueDTO seleccionado) {
    LOGGER.debug("Metodo: <<seleccionarTerminoTransporte>>");
    setSeleccionado(seleccionado);
    // consultar el detalle del registro
    setSeleccionado(reportesComercioExteriorEjb.consultarDetalleInstruccionEmbarque(seleccionado.getId()));
  }

  /**
   *
   * @return
   */
  public StreamedContent imprimirInstruccionEmbarque() {
    LOGGER.debug("Metodo: <<imprimirInstruccionEmbarque>>");
    StreamedContent reportePDF = null;
    Map<String, Object> parametrosReporte = new HashMap<>();
    JRBeanCollectionDataSource ds = null;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    parametrosReporte.put("consecutivosSPs", seleccionado.getConsecutivoSPs());
    parametrosReporte.put("mesEmbarque", seleccionado.getMesEmbarque());
    parametrosReporte.put("sociedad1", seleccionado.getAgenteAduana1Nombre());
    parametrosReporte.put("sociedad2", seleccionado.getAgenteAduana2Nombre());
    parametrosReporte.put("puertoEmbarque", seleccionado.getPuertoEmbarque());
    parametrosReporte.put("fechaEmbarque", formatoFecha.format(seleccionado.getFechaEmbarque()));
    parametrosReporte.put("naviera", seleccionado.getNaviera());
    parametrosReporte.put("linea", seleccionado.getLinea());
    parametrosReporte.put("buque", seleccionado.getBuque());
    parametrosReporte.put("pais_ciudad_dst", seleccionado.getPaisDestinoNombre() + " - " + seleccionado.getCiudadDestinoNombre());
    parametrosReporte.put("fleteExternoP", seleccionado.getFleteExterno().equals("P") ? "XXX" : "");
    parametrosReporte.put("fleteExternoC", seleccionado.getFleteExterno().equals("C") ? "XXX" : "");
    parametrosReporte.put("seguro", seleccionado.getSeguro());
    parametrosReporte.put("direccionCliente", seleccionado.getClienteDireccion());
    parametrosReporte.put("nombreCliente", seleccionado.getClienteNombre());
    parametrosReporte.put("ciudad_pais_cli", seleccionado.getClientePais() + " - " + seleccionado.getClienteCiudad());
    parametrosReporte.put("observacion1", seleccionado.getObservacion());
    parametrosReporte.put("tipoContenedor", seleccionado.getTipoContenedor());
    parametrosReporte.put("cantidadContenedores", seleccionado.getCantidadContenedores());
    parametrosReporte.put("numeroContenedor", seleccionado.getNumeroContenedor());
    parametrosReporte.put("sellosSeg", seleccionado.getSelloSeguridad());
    parametrosReporte.put("precintos", seleccionado.getPrecintos());
    BigDecimal sumMercadeo = new BigDecimal(0);
    BigDecimal sumCafe = new BigDecimal(0);
    if (seleccionado.getListaFacturas() != null) {
      for (DocTerminosTransporteDTO factura : seleccionado.getListaFacturas()) {
        if (factura.getLote().equals("MERCADEO")) {
          if (factura.getCantCajas() != null) {
            sumMercadeo = sumMercadeo.add(factura.getCantCajas());
          }
        } else {
          if (factura.getCantCajas() != null) {
            sumCafe = sumCafe.add(factura.getCantCajas());
          }
        }
      }
    }
    parametrosReporte.put("sumaCafe", sumCafe);
    parametrosReporte.put("consecutivosLEs", seleccionado.getConsecutivoLEs());
    parametrosReporte.put("sumaMercadeo", sumMercadeo);
    parametrosReporte.put("consecutivos", seleccionado.getConsecutivo());
    parametrosReporte.put("modalidadEmbarque", seleccionado.getModalidadEmbarqueDescripcion());
    parametrosReporte.put("booking", seleccionado.getNumeroBooking());
    parametrosReporte.put("incotermDespacho", seleccionado.getIncotermDespachoDecripcion());
    parametrosReporte.put("observacion2", seleccionado.getObservacion2());
    if (seleccionado.getListaFacturas() != null) {
      ds = new JRBeanCollectionDataSource(seleccionado.getListaFacturas());
    }
    try {
      Hashtable<String, String> parametrosConfiguracionReporte;
      parametrosConfiguracionReporte = new Hashtable<>();
      parametrosConfiguracionReporte.put("tipo", "pdf");
      String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_IE.jasper");
      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfiguracionReporte, reporte, null, null, null, parametrosReporte, ds);
      reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "Reporte_IE.pdf");
    } catch (Exception e) {
      this.addMensajeError(e);
    }
    return reportePDF;
  }

  /**
   *
   */
  public void cancelar() {
    LOGGER.debug("Metodo: <<cancelar>>");
    this.setSeleccionado(null);
  }

  /**
   *
   * @param fleteExterno
   * @return
   */
  public String obtenerLabelFleteExterno(String fleteExterno) {
    LOGGER.debug("Metodo: <<obtenerLabelFleteExterno>>");
    if (fleteExterno.equalsIgnoreCase("P")) {
      return AplicacionMB.getMessage("iieDtlLblFleteExternoP", language);
    } else {
      return AplicacionMB.getMessage("iieDtlLblFleteExternoC", language);
    }
  }

  /**
   * @param appMB the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  /**
   * @return the listaTerminosTransporte
   */
  public List<InstruccionEmbarqueDTO> getListaTerminosTransporte() {
    return listaTerminosTransporte;
  }

  /**
   * @param listaTerminosTransporte the listaTerminosTransporte to set
   */
  public void setListaTerminosTransporte(List<InstruccionEmbarqueDTO> listaTerminosTransporte) {
    this.listaTerminosTransporte = listaTerminosTransporte;
  }

  /**
   * @return the seleccionado
   */
  public InstruccionEmbarqueDTO getSeleccionado() {
    return seleccionado;
  }

  /**
   * @param seleccionado the seleccionado to set
   */
  public void setSeleccionado(InstruccionEmbarqueDTO seleccionado) {
    this.seleccionado = seleccionado;
  }

}
