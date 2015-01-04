package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ShipmentConditions;
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
	
	private Integer language=AplicacionMB.SPANISH;
	
	public TerminosTransporteMB(){
	}
	
	@PostConstruct
	public void init(){
		terminosTransporteList = terminosTransporteEjb.consultarListadoTerminosTransporte();
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
}
