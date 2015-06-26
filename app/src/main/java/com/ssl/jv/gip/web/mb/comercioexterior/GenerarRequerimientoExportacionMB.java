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
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
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
	  
	  private List<ModalidadEmbarque>listaModalidadEmbarque= new  ArrayList<ModalidadEmbarque>();
	  
	  private Integer language = AplicacionMB.SPANISH;
	
	  private boolean seleccionado;
	  private boolean generarRequerimiento;
	  
	  private Date dateFechaDespacho;
	  
	  ComextRequerimientoexportacion comextRequerimientoexportacion;
	  
	  private int idModalidadEmbarque;
	  private List<String> puertosNacionales;
	  private String puertoNal;
	  private String puertoLlegada="";
	  
	  private String tipoContenedor;
	  
	  private List<SelectItem> tipoContenedores = new ArrayList<SelectItem>();
	  
	  private SelectItem[] listaIncotermDespacho;
	  private String incotermDespacho;
	  private String flete="Prepagado";
	  
	  private List<SelectItem> listaFlete= new ArrayList<SelectItem>();
	  
	  
	  //boolean needsSuboptions = false;//getter
	  
	  //List<String> options;

	  public void listener() {
		  flete="A Cobrar";
	  }
	  
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
		 
		 
		   
		   listaModalidadEmbarque=comercioEjb.findModalidadEmbarque();
		   this.puertosNacionales = this.comercioEjb.consultarPuertosNacionales();
		   
		   
		    this.tipoContenedores.add(new SelectItem("20", "20"));
		    this.tipoContenedores.add(new SelectItem("40", "40"));
		    this.tipoContenedores.add(new SelectItem("Carga Suelta", "Carga Suelta"));
		   
		    this.listaFlete.add(new SelectItem("A Cobrar", "A Cobrar"));
		    this.listaFlete.add(new SelectItem("Prepagado", "Prepagado"));
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
		    	
		    	
		    	
		    	 System.out.println("puertoNacional:"+puertoNal);
		    	 System.out.println("idModalidadEmbarque:"+idModalidadEmbarque);
		    	 System.out.println("idModalidadEmbarque:"+tipoContenedor);
		    	
		    	
		    	//comextRequerimientoexportacion.setModalidadembarque(modalidadembarque);
		    	
		    	//AgenteAduana agenteAduana =new AgenteAduana();
		    	//agenteAduana.setId((long) 1);
		    	comextRequerimientoexportacion.setPuertosalida(puertoNal);
		    	comextRequerimientoexportacion.setModalidadembarque(idModalidadEmbarque);
		    	comextRequerimientoexportacion.setTipocontenedores(tipoContenedor);
		    	comextRequerimientoexportacion.setTerminoincoterm(incotermDespacho);
		    	
		    	 
		    	 
		    	 
		    	 comercioExteriorEJBLocal.crearRequerimientoExportacion(comextRequerimientoexportacion);
		    	
		    	
		 
	 } catch (Exception e) {
	      LOGGER.error(e);
	      this.addMensajeError(AplicacionMB.getMessage("RequerimientoExportacionError_Crear", language));
	    }
		    
	 }
	
	
	
	public SelectItem[] getListaIncotermDespacho() {
	    if (listaIncotermDespacho == null) {
	      List<TerminoIncoterm> lista = comercioExteriorEJBLocal.findTerminoIncotermAll();
	      listaIncotermDespacho = new SelectItem[lista.size()];

	      for (int i = 0; i < lista.size(); i++) {
	        //listaIncotermDespacho[i] = new SelectItem(new Long(((TerminoIncoterm) lista.get(i)).getId()), new String(((TerminoIncoterm) lista.get(i)).getDescripcion()));
	        listaIncotermDespacho[i] = new SelectItem( new String(((TerminoIncoterm) lista.get(i)).getDescripcion()), new String(((TerminoIncoterm) lista.get(i)).getDescripcion()));
	        
	      }
	    }
	    return listaIncotermDespacho;
	  }

	  public void setListaIncotermDespacho(SelectItem[] listaIncotermDespacho) {
	    this.listaIncotermDespacho = listaIncotermDespacho;
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
	 /**
	 * @return the listaModalidadEmbarque
	 */
	public List<ModalidadEmbarque> getListaModalidadEmbarque() {
		return listaModalidadEmbarque;
	}


	/**
	 * @param listaModalidadEmbarque the listaModalidadEmbarque to set
	 */
	public void setListaModalidadEmbarque(
			List<ModalidadEmbarque> listaModalidadEmbarque) {
		this.listaModalidadEmbarque = listaModalidadEmbarque;
	}


	

	/**
	 * @return the puertosNacionales
	 */
	public List<String> getPuertosNacionales() {
		return puertosNacionales;
	}


	/**
	 * @param puertosNacionales the puertosNacionales to set
	 */
	public void setPuertosNacionales(List<String> puertosNacionales) {
		this.puertosNacionales = puertosNacionales;
	}


	


	/**
	 * @return the puertoLlegada
	 */
	public String getPuertoLlegada() {
		return puertoLlegada;
	}


	/**
	 * @param puertoLlegada the puertoLlegada to set
	 */
	public void setPuertoLlegada(String puertoLlegada) {
		this.puertoLlegada = puertoLlegada;
	}


	


	/**
	 * @return the tipoContenedores
	 */
	public List<SelectItem> getTipoContenedores() {
		return tipoContenedores;
	}


	/**
	 * @param tipoContenedores the tipoContenedores to set
	 */
	public void setTipoContenedores(List<SelectItem> tipoContenedores) {
		this.tipoContenedores = tipoContenedores;
	}


	/**
	 * @return the puertoNal
	 */
	public String getPuertoNal() {
		return puertoNal;
	}


	/**
	 * @param puertoNal the puertoNal to set
	 */
	public void setPuertoNal(String puertoNal) {
		this.puertoNal = puertoNal;
	}


	/**
	 * @return the idModalidadEmbarque
	 */
	public int getIdModalidadEmbarque() {
		return idModalidadEmbarque;
	}


	/**
	 * @param idModalidadEmbarque the idModalidadEmbarque to set
	 */
	public void setIdModalidadEmbarque(int idModalidadEmbarque) {
		this.idModalidadEmbarque = idModalidadEmbarque;
	}


	/**
	 * @return the tipoContenedor
	 */
	public String getTipoContenedor() {
		return tipoContenedor;
	}


	/**
	 * @param tipoContenedor the tipoContenedor to set
	 */
	public void setTipoContenedor(String tipoContenedor) {
		this.tipoContenedor = tipoContenedor;
	}


	/**
	 * @return the incotermDespacho
	 */
	public String getIncotermDespacho() {
		return incotermDespacho;
	}


	/**
	 * @param incotermDespacho the incotermDespacho to set
	 */
	public void setIncotermDespacho(String incotermDespacho) {
		this.incotermDespacho = incotermDespacho;
	}


	/**
	 * @return the flete
	 */
	public String getFlete() {
		return flete;
	}


	/**
	 * @param flete the flete to set
	 */
	public void setFlete(String flete) {
		this.flete = flete;
	}


	/**
	 * @return the listaFlete
	 */
	public List<SelectItem> getListaFlete() {
		return listaFlete;
	}


	/**
	 * @param listaFlete the listaFlete to set
	 */
	public void setListaFlete(List<SelectItem> listaFlete) {
		this.listaFlete = listaFlete;
	}


	

}
