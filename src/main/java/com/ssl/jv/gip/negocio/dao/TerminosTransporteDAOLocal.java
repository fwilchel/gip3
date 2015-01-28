package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.ShipmentConditions;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.dto.InstruccionesEmbarqueDTO;

/**
 * <p>Title: TerminosTransporteDAOLocal</p>
 *
 * <p>Description: Interfaz local del DAO para la informacion de terminos de transporte</p>
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
@Local
public interface TerminosTransporteDAOLocal extends IGenericDAO<TerminosTransporte>{
	
	/**
	 * Metodo que obtiene el listado de los terminos de transporte por cliente
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email sebas.gamba02@gmail.com
	 * @phone 311 8376670
	 * @return
	 */ 
	List<ShipmentConditions> getAllShipmentConditions();
	
	/**
	 * Metodo que consulta la informacion de una instruccion de embarque por su id
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email sebas.gamba02@gmail.com
	 * @phone 311 8376670
	 * @param shipCondId
	 * @return
	 */ 
	InstruccionesEmbarqueDTO getShipmentConditionsById(String shipCondId);
	
	/**
	 * Metodo que consulta una instruccion de embarque por su identificador
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email sebas.gamba02@gmail.com
	 * @phone 311 8376670
	 * @param idTerminosTrans
	 * @return
	 */ 
	TerminosTransporte getById(Long idTerminosTrans);
	
	/**
	 * Metodo que retorna todas las modalidades de embarque
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email sebas.gamba02@gmail.com
	 * @phone 311 8376670
	 * @return
	 */ 
	List<ModalidadEmbarque> getAllShipmentMod();
	
	/**
	 * Metodo que retorna los documentos de una instruccion de embarque
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email sebas.gamba02@gmail.com
	 * @phone 311 8376670
	 * @param idShipmntCond
	 * @return
	 */ 
	List<Documento> getDocumentsByShipCondId(Long idShipmntCond);
	
	/**
	 * Metodo que actualiza un termino de transporte y lo retorna
	 * 
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email sebas.gamba02@gmail.com
	 * @phone 311 8376670
	 * @param terminosTransporte
	 * @return
	 */ 
	TerminosTransporte updateTerminoTransporte(TerminosTransporte terminosTransporte);
}
