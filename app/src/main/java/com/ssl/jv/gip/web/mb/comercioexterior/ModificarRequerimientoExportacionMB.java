package com.ssl.jv.gip.web.mb.comercioexterior;


//import java.sql.Timestamp;


import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;
//import java.util.HashMap;
//import java.util.Hashtable;
import java.util.List;
//import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;






import com.ssl.jv.gip.jpa.pojo.Cliente;
//import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
//import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.jpa.pojo.Estado;
//import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.MetodoPago;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.RequerimientosXDocumento;
//import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
//import com.ssl.jv.gip.jpa.pojo.Reqxproducto;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TipoPrecio;
import com.ssl.jv.gip.negocio.dto.ComextRequerimientoexportacionDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoRequerimientoExportacionDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
//import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Utilidad;



@ManagedBean(name = "modificarRequerimientoExportacionMB")
@SessionScoped
public class ModificarRequerimientoExportacionMB extends UtilMB{



	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2108969050625705174L;

	/**
	 * 
	 */
	
	
	private static final Logger LOGGER = Logger.getLogger(ModificarRequerimientoExportacionMB.class);

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
	
	  
	  //private boolean generarRequerimiento;
	  
	  private Date dateFechaDespacho;
	  
	  ComextRequerimientoexportacion comextRequerimientoexportacion;
	  
	  private int idModalidadEmbarque;
	  private List<String> puertosNacionales  = new ArrayList<String>();
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
	  private String cotradireccion="";
	  
	  
	  private Boolean estadoservicecontract=true;
	  private Boolean estadodarazonsocial=true;
	  private Boolean estadodacontacto=true;
	  private Boolean estadodatelefonoemail=true;
	  
	  
	  private String nrazonsocial="";
	  private String nnit="";
	  private String ndireccion="";
	  private String ntelefono="";
	  private String nciudadpais="";
	  
	  
	  
	  //private String[] certificado;
	  private String dancual;
	  //private String danobservaciones;
	  private String danobscuales;
	  private Boolean danobservaciones;
	  
	  private List<String> danopciones;
	  
	  private boolean deshabilitado = true;
	  
	  private long idcliente;
	  
	  private long idtipoprecio;
	  
	  private long idmetodopago;
	  
	  private ComextRequerimientoexportacion RequerimientoExportacionGenerado;
	  
	  
	  private boolean selectedMarcacionEspecial=false;  
	  
	  
	  
	  private List<ComextRequerimientoexportacionDTO>  listareqxproducto = new ArrayList<ComextRequerimientoexportacionDTO>();
	  
	  private String strDocs;
	  
	  @EJB
	  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	  private Long filtroConsecutivoDocumento;
	  private List<ComextRequerimientoexportacion> listaRequerimientoExportacion;
	  
	  private ComextRequerimientoexportacion seleccionado;
	   
	
	  
	  private List<ComextRequerimientoexportacionDTO> listaMarcacionEspecial;
	  
	  //private String[] certificado;
	  
	  
	  private ArrayList<String> defaultSelectedItems = new ArrayList<String>();
	  private List<String> listaCertificado= new ArrayList<String>();
	  
	  
	  private List<String> certificado;

	  private List<RequerimientosXDocumento> listaRequerimientoxDocumento;
	  
	  
	

	public void terminoIncoterms() {
		  
		  
		  estadoDireccionEntrega=true;
		 
		  
		 
		  
		  if (incotermDespacho.equals("FOB") || incotermDespacho.equals("FCA") || incotermDespacho.equals("EXW"))
		  {		  
			  
			  flete="A Cobrar";
			  
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
			  flete="Prepagado";
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

		 
		 
		
	  }

	 
	 public void consultarRequerimientoExportacion() {
		    try {
		      
		        if (filtroConsecutivoDocumento == null || filtroConsecutivoDocumento == 0) {
		        	//this.listaRequerimientoExportacion=(this.reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacion());
		        	
		        	
		        	
		        	this.listaRequerimientoExportacion=(comercioExteriorEJBLocal.consultarComextRequerimientoExportacionEstado(ConstantesDocumento.ACTIVO));
		        	
		        	
		        }
		        
		        else
		        {
		         //this.listaRequerimientoExportacion=this.reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacionConsecutivo(filtroConsecutivoDocumento);
		        	this.listaRequerimientoExportacion=(comercioExteriorEJBLocal.consultarComextRequerimientoExportacionEstadoXConsecutivo(ConstantesDocumento.ACTIVO,filtroConsecutivoDocumento));
		        	
		        }
		        
		      
		    } catch (Exception e) {
		      LOGGER.error(e);
		      this.addMensajeError(e);
		    }
		  }
	 
		 



	public void guardarRequerimientoExportacion() {

		    try {
		    	
		    	
		    	
		    	comextRequerimientoexportacion = new ComextRequerimientoexportacion();
		    	
		    	
		    	comextRequerimientoexportacion.setId(seleccionado.getId());
		    	comextRequerimientoexportacion.setFecha(seleccionado.getFecha());
		    	comextRequerimientoexportacion.setFechasolicitud(seleccionado.getFechasolicitud());
		    	
		    	
		    	comextRequerimientoexportacion.setEstado(seleccionado.getEstado());
		    	comextRequerimientoexportacion.setFlete(seleccionado.getFlete());
		    	comextRequerimientoexportacion.setPuertosalida(seleccionado.getPuertosalida());
		    	comextRequerimientoexportacion.setPuertollegada(seleccionado.getPuertollegada());
		    	
		    	ModalidadEmbarque modalidadEmbarque = new ModalidadEmbarque(); 
		    	modalidadEmbarque.setId((long) seleccionado.getModalidadembarque().getId());
		    	
		    	comextRequerimientoexportacion.setModalidadembarque(modalidadEmbarque);
		    	comextRequerimientoexportacion.setTipocontenedores(seleccionado.getTipocontenedores());
		    	comextRequerimientoexportacion.setTerminoincoterm(seleccionado.getTerminoincoterm());
		    	
		    	 
		    	comextRequerimientoexportacion.setCciudadpais(seleccionado.getCciudadpais());
		    	comextRequerimientoexportacion.setCdireccion(seleccionado.getCdireccion());
		    	comextRequerimientoexportacion.setCnit(seleccionado.getCnit());
		    	comextRequerimientoexportacion.setCrazonsocial(seleccionado.getCrazonsocial());
		    	comextRequerimientoexportacion.setCtelefono(seleccionado.getCtelefono());
		    
		    	
		    	comextRequerimientoexportacion.setDacontacto(seleccionado.getDacontacto());
		    	//comextRequerimientoexportacion.setDancual(dancual);
		    	//comextRequerimientoexportacion.setDanobscuales(danobscuales);
		    	//comextRequerimientoexportacion.setDanobservaciones(danobservaciones);
		    	//comextRequerimientoexportacion.setDanopciones(danopciones);
		    	comextRequerimientoexportacion.setDarazonsocial(seleccionado.getDarazonsocial());
		    	comextRequerimientoexportacion.setDatelefonoemail(seleccionado.getDatelefonoemail());
		    	
		    	comextRequerimientoexportacion.setServicecontract(seleccionado.getServicecontract());
		    	
		    	comextRequerimientoexportacion.setDireccionentregabi(seleccionado.getDireccionentregabi());
		    	comextRequerimientoexportacion.setContactobi(seleccionado.getContactobi());
		    	comextRequerimientoexportacion.setEmisionbi(seleccionado.getEmisionbi());
		    	comextRequerimientoexportacion.setZipcodebi(seleccionado.getZipcodebi());
		    	comextRequerimientoexportacion.setTelefonoemailbi(seleccionado.getTelefonoemailbi());
		    	
		    	comextRequerimientoexportacion.setNciudadpais(seleccionado.getNciudadpais());
		    	comextRequerimientoexportacion.setNdireccion(seleccionado.getNdireccion());
		    	comextRequerimientoexportacion.setNnit(seleccionado.getNnit());
		    	comextRequerimientoexportacion.setNrazonsocial(seleccionado.getNrazonsocial());
		    	comextRequerimientoexportacion.setNtelefono(seleccionado.getNtelefono());
		    	
		    	comextRequerimientoexportacion.setDancual(seleccionado.getDancual());
		    	comextRequerimientoexportacion.setDanobservaciones(seleccionado.getDanobservaciones());
		    	
		    	comextRequerimientoexportacion.setCotradireccion(seleccionado.getCotradireccion());
		    	
		    	String campo="[";
		    	for (String s:defaultSelectedItems){
		    		campo+='"'+s+'"'+",";
		    	}
		    	if (defaultSelectedItems.size()>0)
		    		campo=campo.substring(0, campo.length()-1);
		    	campo+="]";
		    	comextRequerimientoexportacion.setDanopciones(campo);
		    	
		    	
		   	    
		    	comextRequerimientoexportacion.setDanobscuales(seleccionado.getDanobscuales());
		    	
		    	Cliente cliente =new Cliente();
		    	cliente.setId(seleccionado.getCliente().getId());
		    	
		    	comextRequerimientoexportacion.setCliente(cliente);
		    	comextRequerimientoexportacion.setTipoprecio(seleccionado.getTipoprecio());
		    	comextRequerimientoexportacion.setMetodopago(seleccionado.getMetodopago());
		    	
		    	
		    	//System.out.println("puerto salida: "+seleccionado.getPuertosalida());

		   	 
		   	  
		    	
		   	 RequerimientoExportacionGenerado= comercioExteriorEJBLocal.actualizarRequerimientoExportacion(comextRequerimientoexportacion);
		   	//generarRequerimiento = false;	 
		   	
		    	 
		    	 //String mensaje = AplicacionMB.getMessage("RequerimientoExportacionExito_Crear", language);
		    	    //String parametros[] = new String[1];
		    	    //parametros[0] = "" + RequerimientoExportacionGenerado.getId();
		    	    //mensaje = Utilidad.stringFormat(mensaje, parametros);

		    	   // this.addMensajeInfo(mensaje);
		    	    
		    	    this.addMensajeInfo("Se modifico el Requerimiento de Exportacion exitosamente");
		    	
		    	   // System.out.println("tamaño lista reqxprod: "+listareqxproducto.size());
		    	    
		    	    
		    	  
		    	    
		    	   /* if (listareqxproducto.size()==0 || listareqxproducto.isEmpty())
		    	    {
		    	    	
		    	    	listareqxproducto =comercioEjb.crearMarcacionEspecial(strDocs);
		    	    }*/
		    	    
		    	   /* for (ComextRequerimientoexportacionDTO rxp : listareqxproducto) {
		    	    	
		    	    	
		    	    	 System.out.println("rxp"+rxp.getSku()+"--"+rxp.getCajamaster()+"--"+rxp.getObservaciones());
		    	    }*/
		    	
		    	    
		    	  System.out.println("Ingresa a actualizar reqxprod");
		    	  //graba en la base de datos tabla reqxproducto el lista de productos asociados con el requerimiento de exportacion
		          comercioExteriorEJBLocal.actualizarReqxprod(listaMarcacionEspecial ,RequerimientoExportacionGenerado.getId() ,true);
		          
		          
		          
		          //listareqxproducto =  new ArrayList<ComextRequerimientoexportacionDTO>();
		          //listaDocumentos = new ArrayList<DocumentoRequerimientoExportacionDTO>();
		          
		       
		          
		    	 
		 
	 } catch (Exception e) {
	      LOGGER.error(e);
	      this.addMensajeError(AplicacionMB.getMessage("RequerimientoExportacionError_Crear", language));
	    }
		    
	 }
	
	
	public void aprobarRequerimientoExportacion(ActionEvent ae) {

		//seleccionado.setIdEstado(new Long(ConstantesDocumento.APROBADA));
	    //System.out.println("REQ_ID: "+seleccionado.getId());
	    listaRequerimientoxDocumento=this.comercioExteriorEJBLocal.consultarComextRequerimientoExportacionXDocumento(seleccionado.getId());
	    
	    
	     for (RequerimientosXDocumento rxd : listaRequerimientoxDocumento)
	     {
    	
		   	//System.out.println("rxd"+rxd.getId().getDocumentoId()+"--"+rxd.getId().getRequerimientoexportacionId());
		   	DocumentoIncontermDTO doc = new DocumentoIncontermDTO();
		   	doc.setIdEstado(new Long(ConstantesDocumento.APROBADA));
		   	doc.setIdDocumento(rxd.getId().getDocumentoId());
		   	comercioEjb.actualizarEstadoDocumento(doc);
		   			   	
	     }
	     
	     this.addMensajeInfo("Se aprobo Requerimiento de Exportacion y  Solicitud de Pedido exitosamente");
		    
	    
	     //listaMarcacionEspecial= reportesComercioExteriorEJBLocal.consultarMarcacionEspecial(seleccionado.getId());
	     
	     
	     
	     DocumentoRequerimientoExportacionDTO requerimiento = new DocumentoRequerimientoExportacionDTO();
	     requerimiento.setIdEstado(new Long(ConstantesDocumento.APROBADA));
	     requerimiento.setIdDocumento(seleccionado.getId());
	 	comercioEjb.actualizarComextRequerimientoExportacionEstado(requerimiento );
	}
	    
	    
	
	
	
	
	public SelectItem[] getListaIncotermDespacho() {
	    if (listaIncotermDespacho == null) {
	      List<TerminoIncoterm> lista = comercioExteriorEJBLocal.findTerminoIncotermAll();
	      listaIncotermDespacho = new SelectItem[lista.size()];

	      for (int i = 0; i < lista.size(); i++) {
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

	

	/**
	 * @return the cotradireccion
	 */
	public String getCotradireccion() {
		return cotradireccion;
	}

	/**
	 * @param cotradireccion the cotradireccion to set
	 */
	public void setCotradireccion(String cotradireccion) {
		this.cotradireccion = cotradireccion;
	}

	
	/**
	 * @return the deshabilitado
	 */
	public boolean isDeshabilitado() {
		return deshabilitado;
	}

	/**
	 * @param deshabilitado the deshabilitado to set
	 */
	public void setDeshabilitado(boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	

	/**
	 * @return the idcliente
	 */
	public long getIdcliente() {
		return idcliente;
	}

	/**
	 * @param idcliente the idcliente to set
	 */
	public void setIdcliente(long idcliente) {
		this.idcliente = idcliente;
	}

	/**
	 * @return the idtipoprecio
	 */
	public long getIdtipoprecio() {
		return idtipoprecio;
	}

	/**
	 * @param idtipoprecio the idtipoprecio to set
	 */
	public void setIdtipoprecio(long idtipoprecio) {
		this.idtipoprecio = idtipoprecio;
	}

	/**
	 * @return the idmetodopago
	 */
	public long getIdmetodopago() {
		return idmetodopago;
	}

	/**
	 * @param idmetodopago the idmetodopago to set
	 */
	public void setIdmetodopago(long idmetodopago) {
		this.idmetodopago = idmetodopago;
	}

	/**
	 * @return the selectedMarcacionEspecial
	 */
	public boolean isSelectedMarcacionEspecial() {
		return selectedMarcacionEspecial;
	}

	/**
	 * @param selectedMarcacionEspecial the selectedMarcacionEspecial to set
	 */
	public void setSelectedMarcacionEspecial(boolean selectedMarcacionEspecial) {
		this.selectedMarcacionEspecial = selectedMarcacionEspecial;
	}

	/**
	 * @return the listareqxproducto
	 */
	public List<ComextRequerimientoexportacionDTO> getListareqxproducto() {
		return listareqxproducto;
	}

	/**
	 * @param listareqxproducto the listareqxproducto to set
	 */
	public void setListareqxproducto(
			List<ComextRequerimientoexportacionDTO> listareqxproducto) {
		this.listareqxproducto = listareqxproducto;
	}


	

	
	public void consultarReqxproducto() {
		
		 System.out.println("listaRE reder:"+listareqxproducto.size());
		 
		 System.out.println("selectedMarcacionEspecial:"+selectedMarcacionEspecial);
		
		 if(selectedMarcacionEspecial==true)
		 {
		   //permite listar la consulta de MarcacionEspecial sin diligenciar 
		    listareqxproducto =comercioEjb.crearMarcacionEspecial(strDocs);
		 }
		 else
			 if(selectedMarcacionEspecial==false)
			 {
			   //permite listar la consulta de MarcacionEspecial sin diligenciar 
			    listareqxproducto.clear();
			 }
			 
		    System.out.println("listaRE:"+listareqxproducto.size()); 
		
	}

	

	/**
	 * @return the strDocs
	 */
	public String getStrDocs() {
		return strDocs;
	}

	/**
	 * @param strDocs the strDocs to set
	 */
	public void setStrDocs(String strDocs) {
		this.strDocs = strDocs;
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
	

	 public ComextRequerimientoexportacion getSeleccionado() {
		    return seleccionado;
		  }

		  public void setSeleccionado(ComextRequerimientoexportacion seleccionado) {
		     
			  
			  //System.out.println("documento: "+seleccionado.getId());
			  
			  tipoContenedores.clear();
			  listaFlete.clear();
			  listaEmision.clear();
			  listaCertificado.clear();
			  defaultSelectedItems.clear();
			  listaModalidadEmbarque.clear();
			  puertosNacionales.clear();
			  
			   
			   
			   
			    
			  
		    this.seleccionado = reportesComercioExteriorEJBLocal.consultarComextRequerimientoExportacionDetalle(seleccionado.getId());
		    listaMarcacionEspecial= reportesComercioExteriorEJBLocal.consultarMarcacionEspecial(seleccionado.getId());
		    
		    
		    
		    
		    
		    
		    this.tipoContenedores.add(new SelectItem("veinte", "20"));
		    this.tipoContenedores.add(new SelectItem("cuarenta", "40"));
		    this.tipoContenedores.add(new SelectItem("Carga Suelta", "Carga Suelta"));
		   
		    this.listaFlete.add(new SelectItem("cobrar", "cobrar"));
		    this.listaFlete.add(new SelectItem("prepagado", "prepagado"));
		    
		    this.listaEmision.add(new SelectItem("Origen", "Origen"));
		    this.listaEmision.add(new SelectItem("Destino", "Destino"));
		    
		    
		    this.listaCertificado.add ("Certificado Origen");
		    this.listaCertificado.add ("Certificado Transaccion Organico");
		    this.listaCertificado.add ("Certificado Libre Venta");
		    this.listaCertificado.add ("Apostilla");
		    this.listaCertificado.add ("Otro");
		    
		    
		    listaModalidadEmbarque=comercioEjb.findModalidadEmbarque();
			this.puertosNacionales = this.comercioEjb.consultarPuertosNacionales();
		    

		    
			
		   if (!this.seleccionado.getDanopciones().isEmpty() || !this.seleccionado.getDanopciones().equals(null) || !this.seleccionado.getDanopciones().contains("") || this.seleccionado.getDanopciones()!="")
		   {
		   StringTokenizer stt=new StringTokenizer(this.seleccionado.getDanopciones().substring(1, this.seleccionado.getDanopciones().length()-1), ",");
		   
		  
		   while (stt.hasMoreTokens()){
			   String s = stt.nextToken();
			   s=s.substring(1, s.length()-1);
			   //System.out.println("certificado 1:"+ s);
			   defaultSelectedItems.add(s);
		   }
		   }
		
		   //if (this.seleccionado.getTipocontenedores()==
		   
		   //this.seleccionado.setTipocontenedores(); 
		   
		    
		   
		      
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

		/**
		 * @return the danopciones
		 */
		public List<String> getDanopciones() {
			return danopciones;
		}

		/**
		 * @param danopciones the danopciones to set
		 */
		public void setDanopciones(List<String> danopciones) {
			this.danopciones = danopciones;
		}

		/**
		 * @return the certificado
		 */
		public List<String> getCertificado() {
			return certificado;
		}

		/**
		 * @param certificado the certificado to set
		 */
		public void setCertificado(List<String> certificado) {
			this.certificado = certificado;
		}

		/**
		 * @return the defaultSelectedItems
		 */
		public ArrayList<String> getDefaultSelectedItems() {
			
			//SelectItem s1 =  new SelectItem();
	        //s1.setValue("Apostilla");
	        //defaultSelectedItems.add(s1);
			
			return defaultSelectedItems;
		}

		/**
		 * @param defaultSelectedItems the defaultSelectedItems to set
		 */
		public void setDefaultSelectedItems(ArrayList<String> defaultSelectedItems) {
			this.defaultSelectedItems = defaultSelectedItems;
		}

		/**
		 * @return the listaCertificado
		 */
		public List<String> getListaCertificado() {
			return listaCertificado;
		}

		/**
		 * @param listaCertificado the listaCertificado to set
		 */
		public void setListaCertificado(List<String> listaCertificado) {
			this.listaCertificado = listaCertificado;
		}

		/**
		 * @return the listaRequerimientoxDocumento
		 */
		public List<RequerimientosXDocumento> getListaRequerimientoxDocumento() {
			return listaRequerimientoxDocumento;
		}

		/**
		 * @param listaRequerimientoxDocumento the listaRequerimientoxDocumento to set
		 */
		public void setListaRequerimientoxDocumento(
				List<RequerimientosXDocumento> listaRequerimientoxDocumento) {
			this.listaRequerimientoxDocumento = listaRequerimientoxDocumento;
		}

		

	

		
		
		
}
