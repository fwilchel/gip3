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
import com.ssl.jv.gip.web.util.Modo;

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
	  private String puertoLlegada;
	  
	  private String tipoContenedor;
	  
	  private List<SelectItem> tipoContenedores = new ArrayList<SelectItem>();
	  
	  private SelectItem[] listaIncotermDespacho;
	  private String incotermDespacho;
	  private String flete="";
	  
	  private List<SelectItem> listaFlete= new ArrayList<SelectItem>();
	  private Boolean estadoPuertoLlegada=false;
	  private Boolean estadoPuertoSalida=false;
	  private Modo modo;
	  //private String camposActualizar;
	  private List<SelectItem> listaEmision= new ArrayList<SelectItem>();
	  private String emision="";
	  private String direccionEntrega="";
	  private Boolean estadoDireccionEntrega=true;
	  private Boolean estadoZipCode=true;
	  private String zipCode="";
	  private String contacto="";
	  private Boolean estadoContacto=true;
	  private String telefonoEmail="";
	  private Boolean estadoTelefonoEmail=true;
	  
	  private String servicecontract="";
	  private String darazonsocial="";
	  private String dacontacto="";
	  private String datelefonoemail="";
	  private String crazonsocial="";
	  private String cnit="";
	  private String cdireccion="";
	  private String ctelefono="";
	  private String cciudadpais="";
	  
	  
	  private Boolean estadoservicecontract=true;
	  private Boolean estadodarazonsocial=true;
	  private Boolean estadodacontacto=true;
	  private Boolean estadodatelefonoemail=true;
	  
	  
	  private String nrazonsocial="";
	  private String nnit="";
	  private String ndireccion="";
	  private String ntelefono="";
	  private String nciudadpais="";
	  
	  
	  private List<SelectItem> listaCertificado= new ArrayList<SelectItem>();
	  private String Certificado;
	  private String dancual;
	  //private String danobservaciones;
	  private String danobscuales;
	  private Boolean danobservaciones;
	  
	  private String danopciones;
	  
	  //boolean needsSuboptions = false;//getter
	  
	  //List<String> options;

	  /**
	 * @return the listaCertificado
	 */
	public List<SelectItem> getListaCertificado() {
		return listaCertificado;
	}

	/**
	 * @param listaCertificado the listaCertificado to set
	 */
	public void setListaCertificado(List<SelectItem> listaCertificado) {
		this.listaCertificado = listaCertificado;
	}

	public void listener() {
		  
		  System.out.println("incotermDespacho: "+incotermDespacho);
		  System.out.println("puertoNacional: "+getPuertoNal());
		  
		  estadoDireccionEntrega=true;
		 
		  
		 
		  
		  if (incotermDespacho.equals("FOB") || incotermDespacho.equals("FCA") || incotermDespacho.equals("EXW"))
		  {		  
			  flete="Prepagado";
			  estadoPuertoLlegada=true;
			  estadoPuertoSalida=false;
			  puertoLlegada="";
			  puertoNal=getPuertoNal();
			  
			  
			  estadoservicecontract=false;
			  estadodarazonsocial=false;
			  estadodacontacto=false;
			  estadodatelefonoemail=false;
			  
			  
	      }
		  else
		  {
			  flete="A Cobrar";
		      estadoPuertoLlegada=false;
		      estadoPuertoSalida=true;
		      puertoNal="Sin seleccionar";
		      
		      servicecontract="";
			  darazonsocial="";
			  dacontacto="";
			  datelefonoemail="";
		      
		      estadoservicecontract=true;
			  estadodarazonsocial=true;
			  estadodacontacto=true;
			  estadodatelefonoemail=true;
			  
			  
		  }  	  
		  
		  
		  if (incotermDespacho.equals("DAP") || incotermDespacho.equals("DDP") )
		 {
			  
			  estadoDireccionEntrega=false;
			  estadoZipCode=false;
			  estadoTelefonoEmail=false;
			  estadoContacto=false;
		 }
		  
		  else
		
		 if (!incotermDespacho.equals("DAP") || !incotermDespacho.equals("DDP") )
			 
		 {
			 direccionEntrega="";
			 zipCode="";
			 telefonoEmail="";
			 contacto="";
		 }
		  
			  
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
		    
		    this.listaEmision.add(new SelectItem("Origen", "Origen"));
		    this.listaEmision.add(new SelectItem("Destino", "Destino"));
		    
		    this.listaCertificado.add (new SelectItem("Certificado Origen", "Certificado Origen"));
		    this.listaCertificado.add (new SelectItem("Certificado Transaccion Organico", "Certificado Transaccion Organico"));
		    this.listaCertificado.add (new SelectItem("Certificado Libre Venta", "Certificado Libre Venta"));
		    this.listaCertificado.add (new SelectItem("Apostilla", "Apostilla"));
		    this.listaCertificado.add (new SelectItem("Otro", "Otro"));
		    
		    
		   
		    
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
		    	comextRequerimientoexportacion.setFlete(flete);
		    	
		    	
		    	
		    	 System.out.println("puertoNacional:"+puertoNal);
		    	 System.out.println("idModalidadEmbarque:"+idModalidadEmbarque);
		    	 System.out.println("idModalidadEmbarque:"+tipoContenedor);
		    	
		    	
		    	//comextRequerimientoexportacion.setModalidadembarque(modalidadembarque);
		    	
		    	//AgenteAduana agenteAduana =new AgenteAduana();
		    	//agenteAduana.setId((long) 1);
		    	comextRequerimientoexportacion.setPuertosalida(puertoNal);
		    	comextRequerimientoexportacion.setPuertollegada(puertoLlegada);
		    	
		    	ModalidadEmbarque modalidadEmbarque = new ModalidadEmbarque(); 
		    	modalidadEmbarque.setId((long) idModalidadEmbarque);
		    	
		    	comextRequerimientoexportacion.setModalidadembarque(modalidadEmbarque);
		    	comextRequerimientoexportacion.setTipocontenedores(tipoContenedor);
		    	comextRequerimientoexportacion.setTerminoincoterm(incotermDespacho);
		    	
		    	 
		    	comextRequerimientoexportacion.setCciudadpais(cciudadpais);
		    	comextRequerimientoexportacion.setCdireccion(cdireccion);
		    	comextRequerimientoexportacion.setCnit(cnit);
		    	comextRequerimientoexportacion.setCrazonsocial(crazonsocial);
		    	comextRequerimientoexportacion.setCtelefono(ctelefono);
		    
		    	
		    	comextRequerimientoexportacion.setDacontacto(dacontacto);
		    	//comextRequerimientoexportacion.setDancual(dancual);
		    	//comextRequerimientoexportacion.setDanobscuales(danobscuales);
		    	//comextRequerimientoexportacion.setDanobservaciones(danobservaciones);
		    	//comextRequerimientoexportacion.setDanopciones(danopciones);
		    	comextRequerimientoexportacion.setDarazonsocial(darazonsocial);
		    	comextRequerimientoexportacion.setDatelefonoemail(datelefonoemail);
		    	
		    	comextRequerimientoexportacion.setServicecontract(servicecontract);
		    	
		    	comextRequerimientoexportacion.setDireccionentregabi(direccionEntrega);
		    	comextRequerimientoexportacion.setContactobi(contacto);
		    	comextRequerimientoexportacion.setEmisionbi(emision);
		    	comextRequerimientoexportacion.setZipcodebi(zipCode);
		    	comextRequerimientoexportacion.setTelefonoemailbi(telefonoEmail);
		    	
		    	comextRequerimientoexportacion.setNciudadpais(nciudadpais);
		    	comextRequerimientoexportacion.setNdireccion(ndireccion);
		    	comextRequerimientoexportacion.setNnit(nnit);
		    	comextRequerimientoexportacion.setNrazonsocial(nrazonsocial);
		    	comextRequerimientoexportacion.setNtelefono(ntelefono);
		    	
		    	comextRequerimientoexportacion.setDancual(dancual);
		    	comextRequerimientoexportacion.setDanobservaciones(danobservaciones);
		    	comextRequerimientoexportacion.setDanopciones(danopciones);
		   	  
		   	  
		    	

		   	  
		   	  
		    	
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
	 
	  
	  public boolean isCreacion() {
		    if (this.modo != null && this.modo.equals(Modo.CREAR)) {
		      return true;
		    } else {
		      return false;
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

	/**
	 * @return the estadoPuertoLlegada
	 */
	public Boolean getEstadoPuertoLlegada() {
		return estadoPuertoLlegada;
	}

	/**
	 * @param estadoPuertoLlegada the estadoPuertoLlegada to set
	 */
	public void setEstadoPuertoLlegada(Boolean estadoPuertoLlegada) {
		this.estadoPuertoLlegada = estadoPuertoLlegada;
	}

	/**
	 * @return the estadoPuertoSalida
	 */
	public Boolean getEstadoPuertoSalida() {
		return estadoPuertoSalida;
	}

	/**
	 * @param estadoPuertoSalida the estadoPuertoSalida to set
	 */
	public void setEstadoPuertoSalida(Boolean estadoPuertoSalida) {
		this.estadoPuertoSalida = estadoPuertoSalida;
	}


	/**
	 * @return the listaEmision
	 */
	public List<SelectItem> getListaEmision() {
		return listaEmision;
	}

	/**
	 * @param listaEmision the listaEmision to set
	 */
	public void setListaEmision(List<SelectItem> listaEmision) {
		this.listaEmision = listaEmision;
	}

	/**
	 * @return the emision
	 */
	public String getEmision() {
		return emision;
	}

	/**
	 * @param emision the emision to set
	 */
	public void setEmision(String emision) {
		this.emision = emision;
	}

	/**
	 * @return the direccionEntrega
	 */
	public String getDireccionEntrega() {
		return direccionEntrega;
	}

	/**
	 * @param direccionEntrega the direccionEntrega to set
	 */
	public void setDireccionEntrega(String direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

	/**
	 * @return the estadoDireccionEntrega
	 */
	public Boolean getEstadoDireccionEntrega() {
		return estadoDireccionEntrega;
	}

	/**
	 * @param estadoDireccionEntrega the estadoDireccionEntrega to set
	 */
	public void setEstadoDireccionEntrega(Boolean estadoDireccionEntrega) {
		this.estadoDireccionEntrega = estadoDireccionEntrega;
	}

	/**
	 * @return the estadoZipCode
	 */
	public Boolean getEstadoZipCode() {
		return estadoZipCode;
	}

	/**
	 * @param estadoZipCode the estadoZipCode to set
	 */
	public void setEstadoZipCode(Boolean estadoZipCode) {
		this.estadoZipCode = estadoZipCode;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}

	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the estadoContacto
	 */
	public Boolean getEstadoContacto() {
		return estadoContacto;
	}

	/**
	 * @param estadoContacto the estadoContacto to set
	 */
	public void setEstadoContacto(Boolean estadoContacto) {
		this.estadoContacto = estadoContacto;
	}

	/**
	 * @return the estadoTelefonoEmail
	 */
	public Boolean getEstadoTelefonoEmail() {
		return estadoTelefonoEmail;
	}

	/**
	 * @param estadoTelefonoEmail the estadoTelefonoEmail to set
	 */
	public void setEstadoTelefonoEmail(Boolean estadoTelefonoEmail) {
		this.estadoTelefonoEmail = estadoTelefonoEmail;
	}

	/**
	 * @return the telefonoEmail
	 */
	public String getTelefonoEmail() {
		return telefonoEmail;
	}

	/**
	 * @param telefonoEmail the telefonoEmail to set
	 */
	public void setTelefonoEmail(String telefonoEmail) {
		this.telefonoEmail = telefonoEmail;
	}

	/**
	 * @return the servicecontract
	 */
	public String getServicecontract() {
		return servicecontract;
	}

	/**
	 * @param servicecontract the servicecontract to set
	 */
	public void setServicecontract(String servicecontract) {
		this.servicecontract = servicecontract;
	}

	/**
	 * @return the darazonsocial
	 */
	public String getDarazonsocial() {
		return darazonsocial;
	}

	/**
	 * @param darazonsocial the darazonsocial to set
	 */
	public void setDarazonsocial(String darazonsocial) {
		this.darazonsocial = darazonsocial;
	}

	/**
	 * @return the dacontacto
	 */
	public String getDacontacto() {
		return dacontacto;
	}

	/**
	 * @param dacontacto the dacontacto to set
	 */
	public void setDacontacto(String dacontacto) {
		this.dacontacto = dacontacto;
	}

	/**
	 * @return the datelefonoemail
	 */
	public String getDatelefonoemail() {
		return datelefonoemail;
	}

	/**
	 * @param datelefonoemail the datelefonoemail to set
	 */
	public void setDatelefonoemail(String datelefonoemail) {
		this.datelefonoemail = datelefonoemail;
	}

	/**
	 * @return the crazonsocial
	 */
	public String getCrazonsocial() {
		return crazonsocial;
	}

	/**
	 * @param crazonsocial the crazonsocial to set
	 */
	public void setCrazonsocial(String crazonsocial) {
		this.crazonsocial = crazonsocial;
	}

	/**
	 * @return the cnit
	 */
	public String getCnit() {
		return cnit;
	}

	/**
	 * @param cnit the cnit to set
	 */
	public void setCnit(String cnit) {
		this.cnit = cnit;
	}

	/**
	 * @return the cdireccion
	 */
	public String getCdireccion() {
		return cdireccion;
	}

	/**
	 * @param cdireccion the cdireccion to set
	 */
	public void setCdireccion(String cdireccion) {
		this.cdireccion = cdireccion;
	}

	/**
	 * @return the ctelefono
	 */
	public String getCtelefono() {
		return ctelefono;
	}

	/**
	 * @param ctelefono the ctelefono to set
	 */
	public void setCtelefono(String ctelefono) {
		this.ctelefono = ctelefono;
	}

	/**
	 * @return the cciudadpais
	 */
	public String getCciudadpais() {
		return cciudadpais;
	}

	/**
	 * @param cciudadpais the cciudadpais to set
	 */
	public void setCciudadpais(String cciudadpais) {
		this.cciudadpais = cciudadpais;
	}

	/**
	 * @return the estadoservicecontract
	 */
	public Boolean getEstadoservicecontract() {
		return estadoservicecontract;
	}

	/**
	 * @param estadoservicecontract the estadoservicecontract to set
	 */
	public void setEstadoservicecontract(Boolean estadoservicecontract) {
		this.estadoservicecontract = estadoservicecontract;
	}

	/**
	 * @return the estadodarazonsocial
	 */
	public Boolean getEstadodarazonsocial() {
		return estadodarazonsocial;
	}

	/**
	 * @param estadodarazonsocial the estadodarazonsocial to set
	 */
	public void setEstadodarazonsocial(Boolean estadodarazonsocial) {
		this.estadodarazonsocial = estadodarazonsocial;
	}

	/**
	 * @return the estadodacontacto
	 */
	public Boolean getEstadodacontacto() {
		return estadodacontacto;
	}

	/**
	 * @param estadodacontacto the estadodacontacto to set
	 */
	public void setEstadodacontacto(Boolean estadodacontacto) {
		this.estadodacontacto = estadodacontacto;
	}

	/**
	 * @return the estadodatelefonoemail
	 */
	public Boolean getEstadodatelefonoemail() {
		return estadodatelefonoemail;
	}

	/**
	 * @param estadodatelefonoemail the estadodatelefonoemail to set
	 */
	public void setEstadodatelefonoemail(Boolean estadodatelefonoemail) {
		this.estadodatelefonoemail = estadodatelefonoemail;
	}

	/**
	 * @return the nrazonsocial
	 */
	public String getNrazonsocial() {
		return nrazonsocial;
	}

	/**
	 * @param nrazonsocial the nrazonsocial to set
	 */
	public void setNrazonsocial(String nrazonsocial) {
		this.nrazonsocial = nrazonsocial;
	}

	/**
	 * @return the nnit
	 */
	public String getNnit() {
		return nnit;
	}

	/**
	 * @param nnit the nnit to set
	 */
	public void setNnit(String nnit) {
		this.nnit = nnit;
	}

	/**
	 * @return the ndireccion
	 */
	public String getNdireccion() {
		return ndireccion;
	}

	/**
	 * @param ndireccion the ndireccion to set
	 */
	public void setNdireccion(String ndireccion) {
		this.ndireccion = ndireccion;
	}

	/**
	 * @return the ntelefono
	 */
	public String getNtelefono() {
		return ntelefono;
	}

	/**
	 * @param ntelefono the ntelefono to set
	 */
	public void setNtelefono(String ntelefono) {
		this.ntelefono = ntelefono;
	}

	/**
	 * @return the nciudadpais
	 */
	public String getNciudadpais() {
		return nciudadpais;
	}

	/**
	 * @param nciudadpais the nciudadpais to set
	 */
	public void setNciudadpais(String nciudadpais) {
		this.nciudadpais = nciudadpais;
	}

	/**
	 * @return the certificado
	 */
	public String getCertificado() {
		return Certificado;
	}

	/**
	 * @param certificado the certificado to set
	 */
	public void setCertificado(String certificado) {
		Certificado = certificado;
	}

	/**
	 * @return the dancual
	 */
	public String getDancual() {
		return dancual;
	}

	/**
	 * @param dancual the dancual to set
	 */
	public void setDancual(String dancual) {
		this.dancual = dancual;
	}



	/**
	 * @return the danobscuales
	 */
	public String getDanobscuales() {
		return danobscuales;
	}

	/**
	 * @param danobscuales the danobscuales to set
	 */
	public void setDanobscuales(String danobscuales) {
		this.danobscuales = danobscuales;
	}

	/**
	 * @return the danOpciones
	 */
	public String getDanopciones() {
		return danopciones;
	}

	/**
	 * @param danOpciones the danOpciones to set
	 */
	public void setDanopciones(String danopciones) {
		this.danopciones = danopciones;
	}

	/**
	 * @return the danobservaciones
	 */
	public Boolean getDanobservaciones() {
		return danobservaciones;
	}

	/**
	 * @param danobservaciones the danobservaciones to set
	 */
	public void setDanobservaciones(Boolean danobservaciones) {
		this.danobservaciones = danobservaciones;
	}


	

}
