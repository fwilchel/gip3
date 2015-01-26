package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.primefaces.context.RequestContext;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.ShipmentConditions;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.negocio.dto.InstruccionesEmbarqueDTO;
import com.ssl.jv.gip.negocio.ejb.TerminosTransporteEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.maestros.ProductosMB;
import com.ssl.jv.gip.web.mb.util.EFleteExterno;

/**
 * <p>Title: Terminos de transporte</p>
 *
 * <p>Description: Bean para manipular la informacion de terminos de transporte</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Sebastian Gamba Pinilla
 * @email sebas.gamba02@gmail.com
 * @phone 311 89376670
 * @version 1.0
 */
@ManagedBean(name="terminosTransporteMB")
@SessionScoped
public class TerminosTransporteMB extends UtilMB{
	
	private static final long serialVersionUID = 8651086016315269355L;
	private static final Logger LOGGER = Logger.getLogger(TerminosTransporteMB.class);
	
	@EJB
	private TerminosTransporteEJBLocal terminosTransporteEjb;
	
	private List<ShipmentConditions> terminosTransporteList;
	
	private ShipmentConditions selectedShipmentCond;
	
	private InstruccionesEmbarqueDTO instruccionesEmbarqueDTO;
	
	private List<AgenteAduana> agenteAduanaSelectList = null;
	private List<ModalidadEmbarque> modalidadEmbarqueList = null;
	private List<TerminoIncoterm> incotermList = null;
	private SelectItem selectedAduanaAgent1;
	private SelectItem selectedAduanaAgent2;
	private String selectedIdPais;
	private EFleteExterno[] extFeesList;
	private List<Ciudad> ciudadesList;
	private Date selectedFechaETA;
	
	private String viewAnswer;
	

	private TerminosTransporte selectedTerminosTransporte;
	
	private Integer language=AplicacionMB.SPANISH;
	
	public TerminosTransporteMB(){
	}
	
	@PostConstruct
	public void init(){
		terminosTransporteList = terminosTransporteEjb.consultarListadoTerminosTransporte();
		
		selectedTerminosTransporte = null;
		
		// Listado de agentes de aduana disponibles
		agenteAduanaSelectList = terminosTransporteEjb.consultarAgentesAduanaActivos();
		
		//Listado de modalidades de embarque
		modalidadEmbarqueList = terminosTransporteEjb.consultarModalidadesEmbarque();
		
		//Listado de incoterm de decpacho
		incotermList = terminosTransporteEjb.consultarIncoterms();
		
		extFeesList = EFleteExterno.values();
		ciudadesList = null;
	}

	public List<ShipmentConditions> getTerminosTransporteList() {
		return terminosTransporteList;
	}
	
	public String getViewAnswer() {
		return viewAnswer;
	}
	
	public void setViewAnswer(String viewAnswer) {
		this.viewAnswer = viewAnswer;
	}
	
	public void setTerminosTransporteList(
			List<ShipmentConditions> terminosTransporteList) {
		this.terminosTransporteList = terminosTransporteList;
	}

	public ShipmentConditions getSelectedShipmentCond() {
		return selectedShipmentCond;
	}

	public void setSelectedShipmentCond(ShipmentConditions selectedShipmentCond) {
		this.selectedShipmentCond = selectedShipmentCond;
	}

	public InstruccionesEmbarqueDTO getInstruccionesEmbarqueDTO() {
		return instruccionesEmbarqueDTO;
	}

	public void setInstruccionesEmbarqueDTO(
			InstruccionesEmbarqueDTO instruccionesEmbarqueDTO) {
		this.instruccionesEmbarqueDTO = instruccionesEmbarqueDTO;
	}

	public SelectItem getSelectedAduanaAgent1() {
		return selectedAduanaAgent1;
	}

	public void setSelectedAduanaAgent1(SelectItem selectedAduanaAgent1) {
		this.selectedAduanaAgent1 = selectedAduanaAgent1;
	}

	public List<AgenteAduana> getAgenteAduanaSelectList() {
		return agenteAduanaSelectList;
	}

	public List<ModalidadEmbarque> getModalidadEmbarqueList() {
		return modalidadEmbarqueList;
	}

	public List<TerminoIncoterm> getIncotermList() {
		return incotermList;
	}

	public EFleteExterno[] getExtFeesList() {
		return extFeesList;
	}

	public List<Ciudad> getCiudadesList() {
		return ciudadesList;
	}

	public SelectItem getSelectedAduanaAgent2() {
		return selectedAduanaAgent2;
	}

	public void setSelectedAduanaAgent2(SelectItem selectedAduanaAgent2) {
		this.selectedAduanaAgent2 = selectedAduanaAgent2;
	}

	public TerminosTransporte getSelectedTerminosTransporte() {
		return selectedTerminosTransporte;
	}

	public void setSelectedTerminosTransporte(
			TerminosTransporte selectedTerminosTransporte) {
		this.selectedTerminosTransporte = selectedTerminosTransporte;
	}

	public Date getSelectedFechaETA() {
		return selectedFechaETA;
	}

	public void setSelectedFechaETA(Date selectedFechaETA) {
		this.selectedFechaETA = selectedFechaETA;
	}

	public String getSelectedIdPais() {
		return selectedIdPais;
	}

	public void setSelectedIdPais(String selectedIdPais) {
		this.selectedIdPais = selectedIdPais;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	/**
	 * Metodo que consulta las instrucciones de embarque
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */ 
	public void getShipmentConditionsById(){
		LOGGER.info("Consultando la informacion para el termino de transporte : " 
				+ selectedShipmentCond.getId());
		try {
			instruccionesEmbarqueDTO = terminosTransporteEjb.
					consultarTerminosTransportePorId(selectedShipmentCond.getId());
			if(instruccionesEmbarqueDTO != null && instruccionesEmbarqueDTO.getTerminosTransporte() != null
					&& instruccionesEmbarqueDTO.getTerminosTransporte().getDocumentos() != null){
				selectedFechaETA = instruccionesEmbarqueDTO.getTerminosTransporte().getDocumentos().get(0).getFechaEta(); 
			}

			this.loadCiudades();
			viewAnswer = "";
		} catch (Exception e) {
			this.addMensajeError(AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
			LOGGER.error(e);
		}
	}
	
	/**
	 * Metodo que obtiene las ciudades de una lista
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @return
	 */ 
	public void loadCiudades(){
		if(instruccionesEmbarqueDTO != null && instruccionesEmbarqueDTO.getTerminosTransporte() != null
				&& instruccionesEmbarqueDTO.getTerminosTransporte().getCiudade() != null){
			ciudadesList = AplicacionMB.getCiudadesList(
					instruccionesEmbarqueDTO.getTerminosTransporte().getCiudade().getIdPais());
		}
	}
	
	/**
	 * Metodo que guarda los cambios sobre una instruccion de embarque seleccionada
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */ 
	public void actualizarInstruccionEmbarque(){
		try {
			TerminosTransporte tt = instruccionesEmbarqueDTO.getTerminosTransporte(); 
			if(!(tt.getIdAgenteAduana1() == null || tt.getIdAgenteAduana2() == null ||
					tt.getMesEmbarque() == null || tt.getPuertoEmbarque() == null ||
					tt.getFechaEmbarque() == null || tt.getNaviera() == null ||
					tt.getLinea() == null || tt.getBuque() == null ||
					tt.getSeguro() == null || tt.getTipoContenedor() == null ||
					tt.getCantidadContenedores() == null || tt.getNumeroContenedor() == null ||
					tt.getSellosSeg() == null || tt.getPrecintos() == null ||
					tt.getModalidadEmbarque() == null || tt.getTerminoIncoterm() == null ||
					tt.getObservacion() == null || tt.getObservacion2() == null ||
					selectedFechaETA == null || tt.getNumeroBooking() == null ||
					tt.getFleteExterno() == null || tt.getCiudade() == null ||
					tt.getCiudade().getIdPais() == null)){
				
				for(Documento doc : tt.getDocumentos()){
					doc.setFechaEta(this.selectedFechaETA);
				}
				terminosTransporteEjb.actualizarInstruccionEmbarque(tt);
				terminosTransporteList = terminosTransporteEjb.consultarListadoTerminosTransporte();
				RequestContext.getCurrentInstance().execute("PF('successDlg').show()");
			}
			
			
		} catch (Exception e) {
			this.addMensajeError("Ocurrio un error al actualizar la instruccion de embarque, intente de nuevo mas tarde");
		}
	}
}
