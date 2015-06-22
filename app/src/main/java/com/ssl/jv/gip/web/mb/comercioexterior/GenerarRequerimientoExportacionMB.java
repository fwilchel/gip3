package com.ssl.jv.gip.web.mb.comercioexterior;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



import javax.faces.event.ActionEvent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.DocumentoInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
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

	/**
	   * The comercio ejb.
	   */
	  @EJB
	  private ComercioExteriorEJB comercioEjb;
	  
	  /**
	   * The lista documentos.
	   */
	  private List<Documento> listaDocumentos = new ArrayList<Documento>();
	  
	  private boolean generarRequerimiento;
	
	  private boolean seleccionado;
	
	 @PostConstruct
	  public void init() {
		 
		 
		 
		 Map<String, Object> parametros = new HashMap<String, Object>();
		 parametros.put("idTipoDocumento", (long)ConstantesTipoDocumento.SOLICITUD_PEDIDO);
  	     parametros.put("idEstado", (long) ConstantesDocumento.VERIFICADO);
  	     listaDocumentos = comercioEjb.consultarDocumentosSolicitudPedidoRE(parametros);
  	     
  	   
  	   

	  }

	 
	 public void consultarSolicitud(ActionEvent ae) {
		 
		 
		   for (Documento dto : listaDocumentos) {
			   
			   
		   }
		 
		 
	 }
	 
	 

	/**
	 * @return the listaDocumentos
	 */
	public List<Documento> getListaDocumentos() {
		return listaDocumentos;
	}


	/**
	 * @param listaDocumentos the listaDocumentos to set
	 */
	public void setListaDocumentos(List<Documento> listaDocumentos) {
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

}
