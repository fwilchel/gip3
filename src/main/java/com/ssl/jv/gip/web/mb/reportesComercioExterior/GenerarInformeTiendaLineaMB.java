package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ReporteInformeTiendaLineaDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.mb.util.Numero_a_Letra_Ingles;

@ManagedBean(name = "generarInformeTiendaLineaMB")
@ViewScoped
public class GenerarInformeTiendaLineaMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5283083824411596113L;
	
	@EJB
	private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	
	private static final Logger LOGGER = Logger
			.getLogger(GenerarInformeTiendaLineaMB.class);
	
	private FiltroDocumentoDTO filtroDocumentoDTO;
	
	private List<Documento> listaFacturasExportacion;
	
	private Documento seleccionado;
	
	private StreamedContent reporteExcel;
	
	@PostConstruct
	public void init() {

		try {
			filtroDocumentoDTO = new FiltroDocumentoDTO();
			this.consultarFacturasExportacion();
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}

	}

	private void consultarFacturasExportacion() {
		try {
			filtroDocumentoDTO
			.setIdTipoDocumento((long) ConstantesTipoDocumento.LISTA_EMPAQUE);
			filtroDocumentoDTO.setIdEstado(Estado.ASIGNADA.getCodigo());
			this.listaFacturasExportacion = this.reportesComercioExteriorEJBLocal
					.consultarDocumentosPorTipoDocumentoEstadoTipoCafe(filtroDocumentoDTO);
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	public FiltroDocumentoDTO getFiltroDocumentoDTO() {
		return filtroDocumentoDTO;
	}

	public void setFiltroDocumentoDTO(FiltroDocumentoDTO filtroDocumentoDTO) {
		this.filtroDocumentoDTO = filtroDocumentoDTO;
	}

	public List<Documento> getListaFacturasExportacion() {
		return listaFacturasExportacion;
	}

	public void setListaFacturasExportacion(List<Documento> listaFacturasExportacion) {
		this.listaFacturasExportacion = listaFacturasExportacion;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
	}
	
	public StreamedContent getReporteExcel() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		 List<ProductosXDocumento> listaProductosDocumento = this.reportesComercioExteriorEJBLocal.consultarProductosPorDocumento(seleccionado.getId());
		 List<ReporteInformeTiendaLineaDTO> registros = new ArrayList<ReporteInformeTiendaLineaDTO>();
		 
		 for(ProductosXDocumento prod:listaProductosDocumento){
			 ReporteInformeTiendaLineaDTO registro = new ReporteInformeTiendaLineaDTO();
			 
			 if(this.seleccionado.getDocumentoXLotesoics() != null && !this.seleccionado.getDocumentoXLotesoics().isEmpty()){
				 registro.setDocXLotesOICAsignacion(this.seleccionado.getDocumentoXLotesoics().get(0).getAsignacion());
				 registro.setDocXLotesOICAviso(this.seleccionado.getDocumentoXLotesoics().get(0).getAviso());
				 registro.setDocXLotesOICConsec(this.seleccionado.getDocumentoXLotesoics().get(0).getConsecutivo());
				 registro.setDocXLotesOICPedido(this.seleccionado.getDocumentoXLotesoics().get(0).getPedido());	
				 registro.setTipoLoteOICDesc(this.seleccionado.getDocumentoXLotesoics().get(0).getTipoLoteoic().getDescripcion());
			 }
			 registro.setTotaCajasItem(prod.getCantidadCajasItem().doubleValue());
			 registro.setTotalPesoBrutoItem(prod.getTotalPesoBrutoItem().doubleValue());
			 registro.setTotalPesoNetoItem(prod.getTotalPesoNetoItem().doubleValue());			 
		 }
		
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(registros);
		try {
			
			Hashtable<String, String> parametrosR=new Hashtable<String, String>();
			parametrosR.put("tipo", "xls");
			String reporte=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_ITL.jasper");
			ByteArrayOutputStream os=(ByteArrayOutputStream)com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
			reporteExcel = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel", "ReporteTiendaLinea"+seleccionado.getConsecutivoDocumento()+".xls");
			
		} catch (Exception e) {
			this.addMensajeError(e);
		}
		return reporteExcel;
	}

	
	

}
