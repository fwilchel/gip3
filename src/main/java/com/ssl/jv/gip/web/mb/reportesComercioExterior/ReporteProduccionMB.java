package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.negocio.dto.ReporteProduccionDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

@ManagedBean(name = "reporteProduccionCeMB")
@ViewScoped
public class ReporteProduccionMB extends UtilMB {

  private static final long serialVersionUID = -2245622085667338473L;
  private static final Logger LOGGER = Logger.getLogger(ReporteProduccionMB.class);
  @EJB
  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
  private Date fechaInicial;
  private Date fechaFinal;
  private boolean esSolicitudCafe;
  private StreamedContent reporteExcel;

  public StreamedContent getReporteExcel() {
	Map<String, Object> parametrosReporte = new HashMap<>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	parametrosReporte.put("fecha_inicial", sdf.format(fechaInicial));
	parametrosReporte.put("fecha_final", sdf.format(fechaFinal));
	List<ReporteProduccionDTO> registros = new ArrayList<>();
	try {
	  Map<String, Object> parametrosConsulta = new HashMap<>();
	  parametrosConsulta.put("tipoDocumento", (long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
	  parametrosConsulta.put("fechaInicial", fechaInicial);
	  parametrosConsulta.put("fechaFinal", fechaFinal);
	  parametrosConsulta.put("solicitudCafe", esSolicitudCafe);
	  parametrosConsulta.put("estado", Estado.IMPRESO.getCodigo());
	  registros = reportesComercioExteriorEJBLocal.consultarProductosReporteProduccion(parametrosConsulta);
	} catch (Exception e) {
	  LOGGER.error(e.getMessage());
	}
	JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(registros);
	try {
	  Hashtable<String, String> parametrosConfigReporte = new Hashtable<>();
	  parametrosConfigReporte.put("tipo", "xls");
	  String nombreReporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_PCE.jasper");
	  ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfigReporte, nombreReporte, null, null, null, parametrosReporte, ds);
	  reporteExcel = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel", "Reporte_PCE.xls");
	} catch (Exception e) {
	  this.addMensajeError(e);
	}
	return reporteExcel;
  }

  public void setReporteExcel(StreamedContent reporteExcel) {
	this.reporteExcel = reporteExcel;
  }

  public Date getFechaInicial() {
	return fechaInicial;
  }

  public void setFechaInicial(Date fechaInicial) {
	this.fechaInicial = fechaInicial;
  }

  public Date getFechaFinal() {
	return fechaFinal;
  }

  public void setFechaFinal(Date fechaFinal) {
	this.fechaFinal = fechaFinal;
  }

  public boolean isEsSolicitudCafe() {
	return esSolicitudCafe;
  }

  public void setEsSolicitudCafe(boolean esSolicitudCafe) {
	this.esSolicitudCafe = esSolicitudCafe;
  }

}
