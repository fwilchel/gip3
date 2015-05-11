package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.CuentaContableComprobanteInformeDiarioDTO;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

@ViewScoped
@ManagedBean(name = "comprobanteInformeDiarioFXMB")
public class ComprobanteInformeDiarioFXMB extends UtilMB {

  private static final long serialVersionUID = 226509556071121665L;
  private static final Logger LOGGER = Logger.getLogger(ComprobanteInformeDiarioFXMB.class);
  private FiltroDocumentoDTO filtro;

  @EJB
  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;

  @PostConstruct
  public void init() {
    setFiltro(new FiltroDocumentoDTO());
  }

  public StreamedContent generarReportePDF() {
    StreamedContent reportePDF = null;
    Map<String, Object> parametrosReporte = new HashMap<>();
    SimpleDateFormat sdf;
    try {
      // consulta de facturas de exportacion
      getFiltro().setTipoDocumento((long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
      getFiltro().setOrdenCampo("fechaGeneracion");
      List<Documento> listaFacturasExportacion = this.reportesComercioExteriorEJBLocal.consultarFacturasExportacionFechaTipo(getFiltro());
      for (Documento d : listaFacturasExportacion) {
        if (d.getEstadosxdocumento().getId().getIdEstado() == 11) {
          d.getCliente().setNombre("ANULADA");
        }
        if (d.getEstadosxdocumento().getId().getIdEstado() == 11 || d.getSubtotal() == null) {
          d.setSubtotal(new BigDecimal(0));
        }
        if (d.getEstadosxdocumento().getId().getIdEstado() == 11 || d.getDescuento() == null) {
          d.setDescuento(new BigDecimal(0));
        }
        if (d.getEstadosxdocumento().getId().getIdEstado() == 11 || d.getValorTotal() == null) {
          d.setValorTotal(new BigDecimal(0));
        }
        if (d.getEstadosxdocumento().getId().getIdEstado() == 11 || d.getSubtotal() == null) {
          d.setSubtotal(new BigDecimal(0));
        }
        if (d.getEstadosxdocumento().getId().getIdEstado() == 11 || d.getValorIva5() == null) {
          d.setValorIva5(new BigDecimal(0));
        }
        if (d.getEstadosxdocumento().getId().getIdEstado() == 11 || d.getValorIva10() == null) {
          d.setValorIva10(new BigDecimal(0));
        }
        if (d.getEstadosxdocumento().getId().getIdEstado() == 11 || d.getValorIva16() == null) {
          d.setValorIva16(new BigDecimal(0));
        }
      }
      // consulta de cuentas contables
      Map<String, Object> parametrosConsulta = new HashMap<>();
      parametrosConsulta.put("fechaInicial", (getFiltro().getFechaGeneracionInicio()));
      parametrosConsulta.put("fechaFinal", (getFiltro().getFechaGeneracionFin()));
      parametrosConsulta.put("tipoDocumento", (long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
      parametrosConsulta.put("estadoGenerado", (long) ConstantesDocumento.GENERADO);
      parametrosConsulta.put("estadoImpreso", (long) ConstantesDocumento.IMPRESO);
      List<CuentaContableComprobanteInformeDiarioDTO> listaCuentaContable = reportesComercioExteriorEJBLocal.consultarCuentaContableComprobanteInformeDiarioFX(parametrosConsulta);
      Long idDoc = 0L;
      BigDecimal totalCuentaPrd;
      Long idCuenta = 0L;
      Long idCuenta1 = 0L;
      Long idCuenta2 = 0L;
      BigDecimal totalFinal;
      // la primera vez no hace nada, luego pasa los valores del total con costos al total, para mi, no hace nada.
//      for (int i = 0; i < listaCuentaContable.size(); i++) {
//        if ((idDoc != listaCuentaContable.get(i).getIdCuentaContable()) && (i > 0)) {
//          totalCuentaPrd = listaCuentaContable.get(i - 1).getValorTotalConCostos();
//          listaCuentaContable.get(i - 1).setValorTotal(totalCuentaPrd);
//        }
//        if (i == listaCuentaContable.size() - 1) {
//          totalCuentaPrd = ((CuentaContableComprobanteInformeDiarioDTO) listaCuentaContable.get(i)).getValorTotalConCostos();
//          listaCuentaContable.get(i).setValorTotal(totalCuentaPrd);
//        }
//        idDoc = listaCuentaContable.get(i).getIdDocumento();
//      }
      // crea una lista de las cuentas sin repetir
      List<CuentaContableComprobanteInformeDiarioDTO> listaCuentas = new ArrayList<>();
      for (int n = 0; n < listaCuentaContable.size(); n++) {
        if (!idCuenta.equals(listaCuentaContable.get(n).getIdCuentaContable())) {
          CuentaContableComprobanteInformeDiarioDTO cc = new CuentaContableComprobanteInformeDiarioDTO();
          cc.setIdCuentaContable(listaCuentaContable.get(n).getIdCuentaContable());
          cc.setDescripcionCuentaContable(listaCuentaContable.get(n).getDescripcionCuentaContable());
          cc.setValorTotal(new BigDecimal(0.0));
          listaCuentas.add(cc);
        }
        idCuenta = listaCuentaContable.get(n).getIdCuentaContable();
      }
      // calcula los valores totales para cada cuenta
      for (int x = 0; x < listaCuentas.size(); x++) {
        idCuenta1 = listaCuentas.get(x).getIdCuentaContable();
        totalFinal = new BigDecimal(0);
        for (int z = 0; z < listaCuentaContable.size(); z++) {
          idCuenta2 = listaCuentaContable.get(z).getIdCuentaContable();
          if (idCuenta1.equals(idCuenta2)) {
            // totalFinal =
            // totalFinal.add(listaCuentaContable.get(z).getValorTotal());
            totalFinal = totalFinal.add(listaCuentaContable.get(z).getValorTotalConCostos());
          }
        }
        listaCuentas.get(x).setValorTotal(totalFinal);
      }
      String consecIni = "";
      String consecFin = "";
      int numeroRegistros = 0;
      if (listaFacturasExportacion != null && !listaFacturasExportacion.isEmpty()) {
        numeroRegistros = listaFacturasExportacion.size();
        consecIni = listaFacturasExportacion.get(0).getConsecutivoDocumento();
        consecFin = listaFacturasExportacion.get(listaFacturasExportacion.size() - 1).getConsecutivoDocumento();
      }
      sdf = new SimpleDateFormat("yyyy-MM-dd");
      parametrosReporte.put("size", numeroRegistros);
      parametrosReporte.put("cIni", consecIni);
      parametrosReporte.put("cFin", consecFin);
      parametrosReporte.put("fechaIni", sdf.format(getFiltro().getFechaGeneracionInicio()));
      parametrosReporte.put("fechaFin", sdf.format(getFiltro().getFechaGeneracionFin()));
      parametrosReporte.put("ubicacion", "Oficina Central - Comercio Exterior");
      BigDecimal resSuma1 = new BigDecimal(0);
      BigDecimal resSuma2 = new BigDecimal(0);
      BigDecimal resSuma3 = new BigDecimal(0);
      BigDecimal resSuma4 = new BigDecimal(0);
      BigDecimal resSuma5;
      BigDecimal resSuma6;
      BigDecimal resSuma7;
      for (CuentaContableComprobanteInformeDiarioDTO cuenta : listaCuentas) {
        resSuma1 = resSuma1.add(cuenta.getValorTotal());
      }
      for (Documento doc : listaFacturasExportacion) {
        resSuma2 = resSuma2.add(doc.getDescuento());
        resSuma3 = resSuma3.add(doc.getValorIva16());
        resSuma4 = resSuma4.add(doc.getValorIva10());
      }
      resSuma5 = resSuma1.subtract(resSuma2);
      resSuma6 = resSuma3.add(resSuma4);
      resSuma7 = resSuma5.add(resSuma6);
      parametrosReporte.put("suma1", resSuma1);
      parametrosReporte.put("suma2", resSuma2);
      parametrosReporte.put("suma3", resSuma3);
      parametrosReporte.put("suma4", resSuma4);
      parametrosReporte.put("suma5", resSuma5);
      parametrosReporte.put("suma6", resSuma6);
      parametrosReporte.put("suma7", resSuma7);
      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaCuentas);
      try {
        Hashtable<String, String> parametrosConfigReporte = new Hashtable<>();
        parametrosConfigReporte.put("tipo", "pdf");
        String nombreReporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/report_ComprobanteInfDiarioCE.jasper");
        ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfigReporte, nombreReporte, null, null, null, parametrosReporte, ds);
        reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "report_ComprobanteInfDiarioCE.pdf");
      } catch (Exception e) {
        this.addMensajeError(e);
      }
      return reportePDF;
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
      return null;
    }
  }

  /**
   * @return the filtro
   */
  public FiltroDocumentoDTO getFiltro() {
    return filtro;
  }

  /**
   * @param filtro the filtro to set
   */
  public void setFiltro(FiltroDocumentoDTO filtro) {
    this.filtro = filtro;
  }
}
