package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import com.ssl.jv.gip.jpa.pojo.LiquidacionDocumento;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.UtilMB;

/**
 * <p>
 * Title: GIP
 * </p>
 * <p>
 * Description: GIP
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: Soft Studio Ltda.
 * </p>
 *
 * @author Fredy Wilches
 * @email fredy.wilches@softstudio.co
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name = "reporteCLMB")
@ViewScoped
public class ReporteCLMB extends UtilMB {

	private static final long serialVersionUID = -2245622085667338473L;
	private static final Logger LOGGER = Logger.getLogger(ReporteCLMB.class);
	@EJB
	private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;

	private StreamedContent reportePDF;

	private List<LiquidacionDocumento> solicitudes;

	@PostConstruct
	public void init() {
		this.solicitudes = this.reportesComercioExteriorEJBLocal
				.consultarDocumentosCL();
	}

	public StreamedContent getReportePDF() {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			List<Long> solicitudesSeleccionadas=new ArrayList<Long>();
			for (LiquidacionDocumento ld:this.solicitudes){
				if (ld.getSeleccionada()!=null && ld.getSeleccionada())
					solicitudesSeleccionadas.add(ld.getLiquidacionCostoLogistico().getId());
			}
			parametros.put("ids", solicitudesSeleccionadas.toArray(new Long[0]));
			/*JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					solicitudes);*/
			Hashtable<String, String> parametrosR = new Hashtable<String, String>();
			parametrosR.put("tipo", "pdf");
			String reporte = FacesContext.getCurrentInstance()
					.getExternalContext()
					.getRealPath("/reportes/Report_CL.jasper");
			ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes
					.generar(parametrosR, reporte, null, null, null,
							parametros, null);

			reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(
					os.toByteArray()), "application/pdf ", "Report_CL.pdf");
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return reportePDF;
	}

	public void setReportePDF(StreamedContent reportePDF) {
		this.reportePDF = reportePDF;
	}

	public List<LiquidacionDocumento> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<LiquidacionDocumento> solicitudes) {
		this.solicitudes = solicitudes;
	}

}
