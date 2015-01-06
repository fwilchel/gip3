package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ShipmentConditions;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.jpa.pojo.Usuario;

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
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @return
	 */ 
	List<ShipmentConditions> getAllShipmentConditions();
	
	/**
	 * Metodo que consulta el cliente segun el identificador de una instruccion de embarque
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @param shipCondId
	 * @return
	 */ 
	Cliente getClientByShipmentConditionsId(String shipCondId);
}
