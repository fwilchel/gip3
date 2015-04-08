package com.ssl.jv.gip.web.mb.reportesComercioExterior;

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

import com.ssl.jv.gip.negocio.dto.DocumentoCintaTestigoMagneticaDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;

/**
 * <p>
 * Title: GIP
 * </p>
 *
 * <p>
 * Description: GIP
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 *
 * <p>
 * Company: Soft Studio Ltda.
 * </p>
 *
 * @author Diego Poveda
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean(name = "generarReporteCintaTestigoMagneticaMB")
@ViewScoped
public class GenerarReporteCintaTestigoMagneticaMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;
  private static final Logger LOGGER = Logger.getLogger(GenerarReporteCintaTestigoMagneticaMB.class);
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
  private Date filtroFechaInicial;
  /**
   *
   */
  private Date filtroFechaFinal;

  @PostConstruct
  public void init() {
	LOGGER.debug("Metodo: <<init>>");
  }

  /**
   *
   * @return
   */
  public StreamedContent generarReporte() {
	LOGGER.debug("Metodo: <<generarReporte>>");
	Map<String, Object> parametros = new HashMap<>();
	parametros.put("fechaInicial", getFiltroFechaInicial());
	parametros.put("fechaFinal", getFiltroFechaFinal());
	List<DocumentoCintaTestigoMagneticaDTO> listaDocumentos;
	listaDocumentos = reportesComercioExteriorEJB.consultarDocumentosReporteCintaTestigoMagnetica(parametros);
	for (DocumentoCintaTestigoMagneticaDTO d : listaDocumentos) {
	  if (d.getSubtotal() == 0) {
		d.setSubtotal(d.getValorTotal());
	  }
	}
	// generar reporte
	StreamedContent reporteXLS = null;
	Map<String, Object> parametrosReporte = new HashMap<>();
	SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
	String consecutivoInicial;
	String consecutivoFinal;
	Integer sizeList;
	DocumentoCintaTestigoMagneticaDTO tmp;
	sizeList = listaDocumentos.size();
	tmp = listaDocumentos.get(0);
	consecutivoInicial = tmp.getConsecutivo();
	tmp = listaDocumentos.get(sizeList - 1);
	consecutivoFinal = tmp.getConsecutivo();
	parametrosReporte.put("size", sizeList);
	parametrosReporte.put("cIni", consecutivoInicial);
	parametrosReporte.put("cFin", consecutivoFinal);
	parametrosReporte.put("fechaIni", formatoFecha.format(getFiltroFechaInicial()));
	parametrosReporte.put("fechaFin", formatoFecha.format(getFiltroFechaFinal()));
	parametrosReporte.put("ubicacion", "Oficina Central - Comercio Exterior");
	JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaDocumentos, false);
	try {
	  Hashtable<String, String> parametrosConfiguracionReporte;
	  parametrosConfiguracionReporte = new Hashtable<>();
	  parametrosConfiguracionReporte.put("tipo", "pdf");
	  String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/report_CintaMagneticaCE.jasper");
	  ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfiguracionReporte, reporte, null, null, null, parametrosReporte, ds);
	  reporteXLS = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "CintaTestigoMagneticaFX.pdf");
	} catch (Exception e) {
	  this.addMensajeError("Problemas al generar el reporte");
	}
	return reporteXLS;
  }

  /**
   * @param appMB
   *          the appMB to set
   */
  public void setAppMB(AplicacionMB appMB) {
	this.appMB = appMB;
  }

  /**
   * @param menuMB
   *          the menuMB to set
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
   * @param filtroFechaInicial
   *          the filtroFechaInicial to set
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
   * @param filtroFechaFinal
   *          the filtroFechaFinal to set
   */
  public void setFiltroFechaFinal(Date filtroFechaFinal) {
	this.filtroFechaFinal = filtroFechaFinal;
  }

}
