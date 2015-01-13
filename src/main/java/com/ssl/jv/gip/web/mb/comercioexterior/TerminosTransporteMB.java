package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.ShipmentConditions;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.negocio.dto.InstruccionesEmbarqueDTO;
import com.ssl.jv.gip.negocio.ejb.TerminosTransporteEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.maestros.ProductosMB;

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
	
	private List<SelectItem> agenteAduanaSelectList = null;
	private List<SelectItem> modalidadEmbarqueList = null;
	private List<SelectItem> incotermList = null;
	private SelectItem selectedAduanaAgent1;
	private SelectItem selectedAduanaAgent2;
	private String selectedIdPais;
	
	private TerminosTransporte selectedTerminosTransporte;
	
	private Integer language=AplicacionMB.SPANISH;
	
	public TerminosTransporteMB(){
	}
	
	@PostConstruct
	public void init(){
		terminosTransporteList = terminosTransporteEjb.consultarListadoTerminosTransporte();
		
		selectedTerminosTransporte = null;
		
		// Listado de agentes de aduana disponibles
		List<AgenteAduana> agentesAduana = terminosTransporteEjb.consultarAgentesAduanaActivos();
		if(agentesAduana != null){
			for(AgenteAduana aa : agentesAduana){
				SelectItem item = new SelectItem();
				item.setLabel(aa.getNombre());
				item.setValue(aa.getId());
			
				if(agenteAduanaSelectList == null){
					agenteAduanaSelectList = new ArrayList<SelectItem>();
				}
				
				agenteAduanaSelectList.add(item);
			}
		}
		
		//Listado de modalidades de embarque
		List<ModalidadEmbarque> modalidadesEmbarque = terminosTransporteEjb.consultarModalidadesEmbarque();
		if(modalidadesEmbarque != null){
			for(ModalidadEmbarque me : modalidadesEmbarque){
				SelectItem item = new SelectItem();
				item.setLabel(me.getDescripcion());
				item.setValue(me.getId());
				
				if(modalidadEmbarqueList == null){
					modalidadEmbarqueList = new ArrayList<SelectItem>();
				}
				modalidadEmbarqueList.add(item);
			}
		}
		
		//Listado de incoterm de decpacho
		List<TerminoIncoterm> incotermShList = terminosTransporteEjb.consultarIncoterms();
		if(incotermShList != null){
			for(TerminoIncoterm ti : incotermShList){
				SelectItem item = new SelectItem();
				item.setLabel(ti.getDescripcion());
				item.setValue(ti);
				
				if(incotermList == null){
					incotermList = new ArrayList<SelectItem>();
				}
				
				incotermList.add(item);
			}
		}
	}

	public List<ShipmentConditions> getTerminosTransporteList() {
		return terminosTransporteList;
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

	public List<SelectItem> getAgenteAduanaSelectList() {
		return agenteAduanaSelectList;
	}

	public void setAgenteAduanaSelectList(List<SelectItem> agenteAduanaSelectList) {
		this.agenteAduanaSelectList = agenteAduanaSelectList;
	}

	public SelectItem getSelectedAduanaAgent1() {
		return selectedAduanaAgent1;
	}

	public void setSelectedAduanaAgent1(SelectItem selectedAduanaAgent1) {
		this.selectedAduanaAgent1 = selectedAduanaAgent1;
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

	public List<SelectItem> getModalidadEmbarqueList() {
		return modalidadEmbarqueList;
	}

	public List<SelectItem> getIncotermList() {
		return incotermList;
	}

	public void setIncotermList(List<SelectItem> incotermList) {
		this.incotermList = incotermList;
	}

	public String getSelectedIdPais() {
		return selectedIdPais;
	}

	public void setSelectedIdPais(String selectedIdPais) {
		this.selectedIdPais = selectedIdPais;
	}

	public void setModalidadEmbarqueList(List<SelectItem> modalidadEmbarqueList) {
		this.modalidadEmbarqueList = modalidadEmbarqueList;
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
	public List<SelectItem> getCiudades(){
		return AplicacionMB.getCiudadesList(selectedIdPais);
	}
}
