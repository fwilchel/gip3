package com.ssl.jv.gip.web.mb.reportesComercioExterior;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.UtilMB;



@ManagedBean(name = "generarReporteRequerimientoExportacionMB")
@ViewScoped
public class GenerarReporteRequerimientoExportacionMB extends UtilMB{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -252500691354376469L;
	private static final Logger LOGGER = Logger.getLogger(GenerarReporteRequerimientoExportacionMB.class);
	
	
	 
	 
	  
	 @EJB
	  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	  private Long filtroConsecutivoDocumento;
	  private List<ComextRequerimientoexportacion> listaRequerimientoExportacion;
	  private ComextRequerimientoexportacion seleccionado;



	@PostConstruct
	  public void init() {
	  }

	  public void consultarRequerimientoExportacion() {
	    try {
	     // Map<String, Object> parametros = new HashMap<>();
	     // parametros.put("tipoDocumento", (long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
	      //parametros.put("estado", Arrays.asList(Estado.IMPRESO.getCodigo(), Estado.ANULADO.getCodigo()));
	      /*if (filtroConsecutivoDocumento == null || filtroConsecutivoDocumento.isEmpty()) {
	        parametros.put("id", "%");
	      } else {*/
	        //parametros.put("id", "%" + filtroConsecutivoDocumento + "%");
	      
	      System.out.println("id_documento:"+filtroConsecutivoDocumento);
	       // parametros.put("id", filtroConsecutivoDocumento);
	        //this.listaFacturasExportacion = this.reportesComercioExteriorEJBLocal.consultarFacturasExportacionReimprimir(parametros);
	      //}
	      //long idDocumento=17L;
	      
	      //parametros.put("idDocumento", (long) 17L);
	      
	        System.out.println("id_documento:"+filtroConsecutivoDocumento);
	      
	        if (filtroConsecutivoDocumento == null || filtroConsecutivoDocumento == 0) {
	        
	        	this.listaRequerimientoExportacion=(this.reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacion());
	        	
	        	//this.listaRequerimientoExportacion=ComextRequerimientoexportacion.FIND_ALL();
	        	
	        }
	        else
	        {
	         this.listaRequerimientoExportacion=(this.reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacionConsecutivo(filtroConsecutivoDocumento));
	        }
	        
	      
	    } catch (Exception e) {
	      LOGGER.error(e);
	      this.addMensajeError(e);
	    }
	  }
	  
	  
	  public ComextRequerimientoexportacion getSeleccionado() {
		  System.out.println("consecutivo get:"+seleccionado.getId());
		    return seleccionado;
		  }

		  public void setSeleccionado(ComextRequerimientoexportacion seleccionado) {
		   // this.seleccionado = (ComextRequerimientoexportacion) reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacionConsecutivo(this.seleccionado.getId());
			  
			  System.out.println("consecutivo set:"+seleccionado.getId());
			  
		    this.seleccionado = reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacionDetalle(seleccionado.getId());
		  }
	  
	  
		  
		

		/**
		 * @return the filtroConsecutivoDocumento
		 */
		public Long getFiltroConsecutivoDocumento() {
			return filtroConsecutivoDocumento;
		}

		/**
		 * @param filtroConsecutivoDocumento the filtroConsecutivoDocumento to set
		 */
		public void setFiltroConsecutivoDocumento(Long filtroConsecutivoDocumento) {
			this.filtroConsecutivoDocumento = filtroConsecutivoDocumento;
		}

		
		public List<ComextRequerimientoexportacion> getListaRequerimientoExportacion() {
			return listaRequerimientoExportacion;
		}

	
		public void setListaRequerimientoExportacion(
				List<ComextRequerimientoexportacion> listaRequerimientoExportacion) {
			this.listaRequerimientoExportacion = listaRequerimientoExportacion;
		}

	

		

}
