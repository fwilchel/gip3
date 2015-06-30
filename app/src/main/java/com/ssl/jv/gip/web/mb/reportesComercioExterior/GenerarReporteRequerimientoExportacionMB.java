package com.ssl.jv.gip.web.mb.reportesComercioExterior;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dao.ComextRequerimientoExportacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.ComextRequerimientoexportacionDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.dto.ReporteReimprimirFacturaDTO;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.Numero_a_Letra_Ingles;



@ManagedBean(name = "generarReporteRequerimientoExportacionMB")
@ViewScoped
public class GenerarReporteRequerimientoExportacionMB extends UtilMB{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -252500691354376469L;
	private static final Logger LOGGER = Logger.getLogger(GenerarReporteRequerimientoExportacionMB.class);
	
	
	 
	private StreamedContent reportePDF;
	  
	 @EJB
	  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	  private Long filtroConsecutivoDocumento;
	  private List<ComextRequerimientoexportacion> listaRequerimientoExportacion;
	  private ComextRequerimientoexportacion seleccionado;
	  
	  private List<ComextRequerimientoexportacionDTO> listaMarcacionEspecial;
	  
	  @EJB
	  private ComextRequerimientoExportacionDAOLocal comextRequerimientoExportacionDAOLocal; 

	  private List<AgenteAduana> listaAgentesAduana;
	  
	  
	  @EJB
	  private MaestrosEJBLocal servicio;

	  private AgenteAduana selAgenteAduana;

	@PostConstruct
	  public void init() {
	  }

	  public void consultarRequerimientoExportacion() {
	    try {
	     //   System.out.println("id_documento:"+filtroConsecutivoDocumento);
	      //  System.out.println("id_documento:"+filtroConsecutivoDocumento);
	      
	        if (filtroConsecutivoDocumento == null || filtroConsecutivoDocumento == 0) {
	        	this.listaRequerimientoExportacion=(this.reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacion());
	        }
	        else
	        {
	         this.listaRequerimientoExportacion=this.reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacionConsecutivo(filtroConsecutivoDocumento);
	        }
	        
	      
	    } catch (Exception e) {
	      LOGGER.error(e);
	      this.addMensajeError(e);
	    }
	  }
	  
	  
	  public ComextRequerimientoexportacion getSeleccionado() {
		    return seleccionado;
		  }

		  public void setSeleccionado(ComextRequerimientoexportacion seleccionado) {
		     
			  
		    this.seleccionado = reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacionDetalle(seleccionado.getId());
		    listaMarcacionEspecial= reportesComercioExteriorEJBLocal.consultarMarcacionEspecial(seleccionado.getId());
		    

		    		
		  }
		  
		  
		  public StreamedContent getReportePDF() {
			    Map<String, Object> parametros = new HashMap<String, Object>();
			   
			    /**
			     * ********** Llenado de parametros ************
			     */
			    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
			    String fechaStringSolicitud = ft.format(this.seleccionado.getFechasolicitud());
			    
			    parametros.put("documento", this.seleccionado.getId());
			    parametros.put("fechaSolicitud", fechaStringSolicitud);
			    
			    parametros.put("modalidadEmbarque", this.seleccionado.getModalidadembarque().getDescripcion());
			    
			    parametros.put("puertoSalida",    this.seleccionado.getPuertosalida());
			    parametros.put("tipoContenedor",    this.seleccionado.getTipocontenedores());
			    parametros.put("terminoIncoterm",    this.seleccionado.getTerminoincoterm());
			    parametros.put("flete",    this.seleccionado.getFlete());
			    parametros.put("EmisionBl",    this.seleccionado.getEmisionbi());
			    parametros.put("Direccion",    this.seleccionado.getDireccionentregabi());
			    parametros.put("zipcode",    this.seleccionado.getZipcodebi());
			    parametros.put("contacto",    this.seleccionado.getContactobi());
			    parametros.put("telefonoEmail",    this.seleccionado.getTelefonoemailbi());
			
			    parametros.put("serviceContract",    this.seleccionado.getServicecontract());
			    
			    parametros.put("DarazonSocial",    this.seleccionado.getDarazonsocial());
			    parametros.put("Dacontacto",    this.seleccionado.getDacontacto());
			    parametros.put("Datelefono",    this.seleccionado.getDatelefonoemail());
			    
			    
			    parametros.put("CrazonSocial",    this.seleccionado.getCrazonsocial());
			    parametros.put("Ccontacto",    this.seleccionado.getContactobi());
			    parametros.put("Ctelefono",    this.seleccionado.getCtelefono());
			    parametros.put("Cdireccion",    this.seleccionado.getCdireccion());
			    parametros.put("Cnit",    this.seleccionado.getCnit());
			    parametros.put("Cciudadpais",    this.seleccionado.getCciudadpais());
			    
			    parametros.put("NrazonSocial",    this.seleccionado.getNrazonsocial());
			    parametros.put("Nnit",    this.seleccionado.getNnit());
			    parametros.put("Ndireccion",    this.seleccionado.getNdireccion());
			    parametros.put("Ntelefono",    this.seleccionado.getNtelefono());
			    parametros.put("Nciudadpais",    this.seleccionado.getNciudadpais());
			    parametros.put("Cotradireccion",    this.seleccionado.getCotradireccion());
			    
			    parametros.put("Danopciones",    this.seleccionado.getDanopciones());
			    parametros.put("Dacual",    this.seleccionado.getDancual());
			    
			    
			    String Observacion=""; 
			    if  (this.seleccionado.getDanobservaciones()==true)
			    {
			    	Observacion="Si";
			    }
			    else
			    	Observacion="No";
			    	
			    	parametros.put("Daobservaciones",   Observacion);
			    	
			    	parametros.put("Daobscuales",    this.seleccionado.getDanobscuales());
			    	
			    	
	            /* <p:selectOneMenu id="agente"
	                             value="#{generarReporteRequerimientoExportacionMB.seleccionado.puertosalida}"
	                             label="#{msg.reporteRequerimientoExportacion_PuertoSalida}" required="false" disabled = "true">
	              <f:selectItem itemLabel="#{msg.generalSinSeleccionar}"
	                            itemValue="" />
	              <f:selectItems value="#{clienteMB.listaAgentesAduana}"
	                             var="agente" itemValue="#{agente.id}"
	                             itemLabel="#{agente.lugarEntrega}" />
	            </p:selectOneMenu>
			    */
			    
			    System.out.println("documento:"+ this.seleccionado.getId());
			    System.out.println("fecha:"+ this.seleccionado.getFechasolicitud());
			    //System.out.println("ID AGENTE ADUANA:"+  this.seleccionado.getAgenteAduana().getId());
			    //System.out.println("AGENTE ADUANA:"+  this.seleccionado.getAgenteAduana().getLugarEntrega());
			    
			    
			  /*  listaAgentesAduana = new ArrayList<AgenteAduana>(); 
			    
			    
			    		
			    		
			    		String ciudadBuscado = "Buenaventura";
		        int pos = listaAgentesAduana.indexOf(ciudadBuscado);
		        System.out.println(" se ha encontrado en la posición: "+pos);		
			    		
			    for (AgenteAduana a : listaAgentesAduana) {
			        
			    	System.out.println("id:"+ a.getId());
			    	System.out.println("lugar entrega:"+ a.getLugarEntrega());
			        
			      }
			    */
			    
			    		//this.seleccionado.getAgenteAduana().getLugarEntrega());
			    
			    
			    
			    
			    
			    
			    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaMarcacionEspecial);
			    try {
			      Hashtable<String, String> parametrosR = new Hashtable<String, String>();
			      parametrosR.put("tipo", "pdf");
			      String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/ReporteRE.jasper");
			      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
			      reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "ReporteRE.pdf");
			    } catch (Exception e) {
			      this.addMensajeError(e);
			    }
			    return reportePDF;
			  }
	  
	  
		  
		  
		  public List<AgenteAduana> getListaAgentesAduana() {
			    if (listaAgentesAduana == null || listaAgentesAduana.isEmpty()) {
			      listaAgentesAduana = servicio.consultarAgentesAduana();
			      
			      

			      Collections.sort(listaAgentesAduana, new Comparator<AgenteAduana>() {
			        @Override
			        public int compare(AgenteAduana agenteAduana, AgenteAduana agenteAduana2) {

			          return agenteAduana.getNombre().compareTo(agenteAduana2.getNombre());
			        }
			      });
			      
			      
			      
			    }
			    return listaAgentesAduana;
			  }

			  public void setListaAgentesAduana(List<AgenteAduana> listaAgentesAduana) {
			    this.listaAgentesAduana = listaAgentesAduana;
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

		/**
		 * @return the listaMarcacionEspecial
		 */
		public List<ComextRequerimientoexportacionDTO> getListaMarcacionEspecial() {
			return listaMarcacionEspecial;
		}

		/**
		 * @param listaMarcacionEspecial the listaMarcacionEspecial to set
		 */
		public void setListaMarcacionEspecial(
				List<ComextRequerimientoexportacionDTO> listaMarcacionEspecial) {
			this.listaMarcacionEspecial = listaMarcacionEspecial;
		}

	

		

}
