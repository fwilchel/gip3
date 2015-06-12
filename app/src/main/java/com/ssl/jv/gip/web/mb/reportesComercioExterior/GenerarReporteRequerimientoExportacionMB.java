package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;



@ManagedBean(name = "generarReporteRequerimientoExportacionMB")
@ViewScoped
public class GenerarReporteRequerimientoExportacionMB {
	
	 @EJB
	  private List<ComextRequerimientoexportacion> listaRequerimientoExportacion;
	  
	 
	  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	
	  private int filtroConsecutivoDocumento;





	@PostConstruct
	  public void init() {
	  }

	  public void consultarRequerimientoExportacion() {
	    try {
	      Map<String, Object> parametros = new HashMap<>();
	     // parametros.put("tipoDocumento", (long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
	      //parametros.put("estado", Arrays.asList(Estado.IMPRESO.getCodigo(), Estado.ANULADO.getCodigo()));
	      /*if (filtroConsecutivoDocumento == null || filtroConsecutivoDocumento.isEmpty()) {
	        parametros.put("id", "%");
	      } else {*/
	        //parametros.put("id", "%" + filtroConsecutivoDocumento + "%");
	      
	      System.out.println("id_documento:"+filtroConsecutivoDocumento);
	        parametros.put("id", filtroConsecutivoDocumento);
	        //this.listaFacturasExportacion = this.reportesComercioExteriorEJBLocal.consultarFacturasExportacionReimprimir(parametros);
	      //}
	      //long idDocumento=17L;
	      
	      //parametros.put("idDocumento", (long) 17L);
	      
	        System.out.println("id_documento:"+filtroConsecutivoDocumento);
	      listaRequerimientoExportacion = this.reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacion(parametros);
	      
	      
	      System.out.println("tamaño_doc:"+ listaRequerimientoExportacion.size());
	      
	      //items = this.maestrosEjb.consultarItemsCostosLogisticos();
	      
	      
	    } catch (Exception e) {
	      //LOGGER.error(e);
	      //this.addMensajeError(e);
	    }
	  }
	  
		
		/**
		 * @return the filtroConsecutivoDocumento
		 */
		public int getFiltroConsecutivoDocumento() {
			return filtroConsecutivoDocumento;
		}

		/**
		 * @param filtroConsecutivoDocumento the filtroConsecutivoDocumento to set
		 */
		public void setFiltroConsecutivoDocumento(int filtroConsecutivoDocumento) {
			this.filtroConsecutivoDocumento = filtroConsecutivoDocumento;
		}

		/**
		 * @return the listaRequerimientoExportacion
		 */
		public List<ComextRequerimientoexportacion> getListaRequerimientoExportacion() {
			return listaRequerimientoExportacion;
		}

		/**
		 * @param listaRequerimientoExportacion the listaRequerimientoExportacion to set
		 */
		public void setListaRequerimientoExportacion(
				List<ComextRequerimientoexportacion> listaRequerimientoExportacion) {
			this.listaRequerimientoExportacion = listaRequerimientoExportacion;
		}
	  
		

}
