package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.Numero_a_Letra_Ingles;

@ManagedBean(name = "comprobanteInformeDiarioFXMB")
@ViewScoped
public class ComprobanteInformeDiarioFXMB extends UtilMB{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 226509556071121665L;
	
	private Date fechaInicial;
	
	private Date fechaFinal;
	
	private StreamedContent reportePDF;
	
	private List<Documento> listaFacturasExportacion;
	
	@PostConstruct
	public void init() {
	
	}
	
	public StreamedContent getReportePDF() {

		Map<String, Object> parametros = new HashMap<String, Object>();
		
		
		
		
		
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaFacturasExportacion);

		try {
			Hashtable<String, String> parametrosR=new Hashtable<String, String>();
			parametrosR.put("tipo", "pdf");
			String reporte=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_FX.jasper");
			ByteArrayOutputStream os=(ByteArrayOutputStream)com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
			reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "Report_FX.pdf");

		} catch (Exception e) {
			this.addMensajeError(e);
		}
		return reportePDF;

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
	
	

}
