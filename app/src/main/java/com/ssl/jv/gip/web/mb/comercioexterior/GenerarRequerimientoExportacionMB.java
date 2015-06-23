package com.ssl.jv.gip.web.mb.comercioexterior;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.DocumentoRequerimientoExportacionDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

@ManagedBean(name = "generarRequerimientoExportacionMB")
@SessionScoped
public class GenerarRequerimientoExportacionMB extends UtilMB{



	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1427581635726894782L;
	
	private static final Logger LOGGER = Logger.getLogger(GenerarRequerimientoExportacionMB.class);

	/**
	   * The comercio ejb.
	   */
	  @EJB
	  private ComercioExteriorEJB comercioEjb;
	  
	  
	  @EJB
	  private ComercioExteriorEJBLocal comercioExteriorEJBLocal;
	  
	  /**
	   * The lista documentos.
	   */
	  private List<DocumentoRequerimientoExportacionDTO> listaDocumentos = new ArrayList<DocumentoRequerimientoExportacionDTO>();
	  
	  private Integer language = AplicacionMB.SPANISH;
	
	  private boolean seleccionado;
	  private boolean generarRequerimiento;
	  
	  private Date dateFechaDespacho;
	  
	  ComextRequerimientoexportacion comextRequerimientoexportacion;
	  
	
	 @PostConstruct
	  public void init() {
		 
		 
		 
		 Map<String, Object> parametros = new HashMap<String, Object>();
		 parametros.put("idTipoDocumento", (long)ConstantesTipoDocumento.SOLICITUD_PEDIDO);
  	     parametros.put("idEstado", (long) ConstantesDocumento.VERIFICADO);
  	     parametros.put("documentoCliente","Cargue Manual");
  	     
  	     listaDocumentos = comercioEjb.consultarDocumentosSolicitudPedidoRE(parametros);
  	     
  	   
  	   

	  }

	 
	 public void consultarSolicitud(ActionEvent ae) {
		 
		 generarRequerimiento = true;
		   for (DocumentoRequerimientoExportacionDTO dto : listaDocumentos) {
			   
			   if (dto.isSeleccionado()) {
				   
				   System.out.println("doc"+dto.getConsecutivoDocumento());


		           // seleccionados.add(dto);
		          }
			   
			   
		   }
		 
		 
	 }
	 
	 
	 
	 public void guardarRequerimientoExportacion(ActionEvent ae) {

		    try {
		    	
		    	
		    	
		    	comextRequerimientoexportacion = new ComextRequerimientoexportacion();
		    	Cliente cliente =new Cliente();
		    	cliente.setId((long)137);
		    	
		    	
		    	comextRequerimientoexportacion.setIdCliente(cliente);
		    	comextRequerimientoexportacion.setFecha(this.dateFechaDespacho);
		    	comextRequerimientoexportacion.setFechasolicitud(this.dateFechaDespacho);
		    	comextRequerimientoexportacion.setEstado(0);
		    	
		    	AgenteAduana agenteAduana =new AgenteAduana();
		    	
		    	agenteAduana.setId((long) 1);
		    	
		    	comextRequerimientoexportacion.setAgenteAduana(agenteAduana);
		    	
		    	 comercioExteriorEJBLocal.crearRequerimientoExportacion(comextRequerimientoexportacion);
		    	
		    	
		 
	 } catch (Exception e) {
	      LOGGER.error(e);
	      this.addMensajeError(AplicacionMB.getMessage("RequerimientoExportacionError_Crear", language));
	    }
		    
	 }
	 
	 

	/**
	 * @return the listaDocumentos
	 */
	public List<DocumentoRequerimientoExportacionDTO> getListaDocumentos() {
		return listaDocumentos;
	}


	/**
	 * @param listaDocumentos the listaDocumentos to set
	 */
	public void setListaDocumentos(List<DocumentoRequerimientoExportacionDTO> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}


	/**
	 * @return the generarRequerimiento
	 */
	public boolean isGenerarRequerimiento() {
		return generarRequerimiento;
	}


	/**
	 * @param generarRequerimiento the generarRequerimiento to set
	 */
	public void setGenerarRequerimiento(boolean generarRequerimiento) {
		this.generarRequerimiento = generarRequerimiento;
	}


	/**
	 * @return the dateFechaDespacho
	 */
	public Date getDateFechaDespacho() {
		return dateFechaDespacho;
	}


	/**
	 * @param dateFechaDespacho the dateFechaDespacho to set
	 */
	public void setDateFechaDespacho(Date dateFechaDespacho) {
		this.dateFechaDespacho = dateFechaDespacho;
	}

}
