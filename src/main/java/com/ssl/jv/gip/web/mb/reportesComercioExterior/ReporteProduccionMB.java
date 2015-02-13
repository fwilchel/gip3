package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.UtilMB;


@ManagedBean(name = "reporteProduccionCeMB")
@ViewScoped
public class ReporteProduccionMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2245622085667338473L;
	
	@EJB
	private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	
	private Date fechaInicial;

	private Date fechaFinal;
	
	private boolean esSolicitudCafe;
	
	private StreamedContent reportePDF;
	
	@PostConstruct
	public void init() {

	}

	
	private static final Logger LOGGER = Logger
			.getLogger(ReporteProduccionMB.class);

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


	public StreamedContent getReportePDF() {
		return reportePDF;
	}


	public void setReportePDF(StreamedContent reportePDF) {
		this.reportePDF = reportePDF;
	}
	
	

}
