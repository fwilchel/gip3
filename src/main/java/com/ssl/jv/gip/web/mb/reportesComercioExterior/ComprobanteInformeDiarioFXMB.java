package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
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
	
	private List<ProductosXDocumento> listaProductosxDocumento;

	private FiltroDocumentoDTO filtroDocumentoDTO;

	@EJB
	private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;

	private static final Logger LOGGER = Logger
			.getLogger(ReImprimirFacturaExpoMB.class);

	@PostConstruct
	public void init() {

	}

	public StreamedContent getReportePDF() {

		Map<String, Object> parametros = new HashMap<String, Object>();

		try {
			filtroDocumentoDTO
			.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
			filtroDocumentoDTO.setFechaInicio(fechaInicial);
			filtroDocumentoDTO.setFechaFin(fechaFinal);
			this.listaFacturasExportacion = this.reportesComercioExteriorEJBLocal.consultarFacturasExportacionFechaTipo(filtroDocumentoDTO);
			this.listaProductosxDocumento = new ArrayList<ProductosXDocumento>();
			for (Documento doc:listaFacturasExportacion)
			 {
				this.listaProductosxDocumento.addAll(this.reportesComercioExteriorEJBLocal.consultarProductosPorDocumento(doc.getId()));
				 							 
			 }

			int idDoc = 0;
			double totalCuentaPrd = 0;
			Long idCuenta = 0L;
			Long idCuenta1 = 0L;
			Long idCuenta2 = 0L;
			double totalFinal = 0;
			
			
			
			String consecIni = "";
			String consecFin = "";
			
			if(listaFacturasExportacion != null && !listaFacturasExportacion.isEmpty()){
				consecIni = listaFacturasExportacion.get(0).getConsecutivoDocumento();
				consecFin = listaFacturasExportacion.get(listaFacturasExportacion.size() - 1).getConsecutivoDocumento();
			}
			
			 parametros.put("size", listaFacturasExportacion.size());
			 parametros.put("cIni", consecIni);
			 parametros.put("cFin", consecFin);
			 parametros.put("fechaIni", this.fechaInicial.toString());
			 parametros.put("fechaFin", this.fechaFinal.toString());
			 parametros.put("ubicacion", "Oficina Central - Comercio Exterior");
			 
			 Double resSuma1 = 0.0;
			 Double resSuma2 = 0.0;		 
			 Double resSuma3 = 0.0;
			 Double resSuma4 = 0.0;		 
			 Double resSuma5 = 0.0;		 
			 Double resSuma6 = 0.0;
			 Double resSuma7 = 0.0;
			 
			 for (ProductosXDocumento prod:listaProductosxDocumento) {
				 resSuma1 = resSuma1 + prod.getValorTotal().doubleValue();
				 							 
			 }
			 
			 for (Documento doc:listaFacturasExportacion)
			 {
				 resSuma2 = resSuma2 + doc.getDescuento().doubleValue();
				 resSuma3 = resSuma3 + doc.getValorIva16().doubleValue();
				 resSuma4 = resSuma4 + doc.getValorIva10().doubleValue();
				 							 
			 }	 
			 
			 resSuma5 = resSuma1 - resSuma2;
			 resSuma6 = resSuma3 + resSuma4;
			 resSuma7 = resSuma5 + resSuma6;
			 
			 parametros.put("suma1", resSuma1);
			 parametros.put("suma2", resSuma2);
			 parametros.put("suma3", resSuma3);
			 parametros.put("suma4", resSuma4);
			 parametros.put("suma5", resSuma5);		 
			 parametros.put("suma6", resSuma6);
			 parametros.put("suma7", resSuma7);

		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}




		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaProductosxDocumento);

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
