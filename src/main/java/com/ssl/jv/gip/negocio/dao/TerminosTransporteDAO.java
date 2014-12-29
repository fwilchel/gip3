package com.ssl.jv.gip.negocio.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ShipmentConditions;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.web.util.ErrorConstants;

/**
 * <p>Title: TerminosTransporteDAO</p>
 *
 * <p>Description: DAO para la consulta de los terminos de transporte</p>
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
@Stateless
@LocalBean
public class TerminosTransporteDAO extends GenericDAO<TerminosTransporte> implements TerminosTransporteDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(TerminosTransporteDAO.class);

	public TerminosTransporteDAO(){
		this.persistentClass = TerminosTransporte.class;
	}

	/**
	 * @see com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal#getAllShipmentConditions()
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public List<ShipmentConditions> getAllShipmentConditions() {
		LOGGER.info("Entrando al metodo getAllShipmentConditions...");
		List<ShipmentConditions> answer = null;
		try {
			Query query = this.em.createNativeQuery("SELECT "
														+ " terminos_transporte.id, " 
														+ " terminos_transporte.fecha_embarque, "  
														+ " cli.nombre as nombre_cliente "
													+ " FROM terminos_transporte "
														+ " JOIN terminos_transporte_x_documento ON terminos_transporte_x_documento.id_terminos_transporte = terminos_transporte.id "  
														+ " JOIN documentos doc ON terminos_transporte_x_documento.id_documento = doc.id " 
														+ " JOIN clientes cli ON doc.id_cliente = cli.id " 
													 + " GROUP BY terminos_transporte.id, terminos_transporte.fecha_embarque, cli.nombre "
													 + " ORDER BY id DESC", ShipmentConditions.class);
			
			answer = query.getResultList();
		} catch (NoResultException ne){
			LOGGER.error(ErrorConstants.NO_RESULT.getDescription());
		} catch (Exception e) {
			LOGGER.error(ErrorConstants.UNHANDLED_EXCEPTION.getDescription());
		}
		return answer;
	}

	/**
	 * @see com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal#getClientByShipmentConditionsId(java.lang.String)
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public Cliente getClientByShipmentConditionsId(String shipCondId) {
		LOGGER.info("Entrando al metodo getClientByShipmentConditionsId...");
		Cliente answer = null;
		try {
			Query query = this.em.createNativeQuery("SELECT DISTINCT cli.* FROM clientes cli "
					+ " JOIN documentos doc ON doc.id_cliente = cli.id "
					+ " JOIN terminos_transporte_x_documento ON terminos_transporte_x_documento.id_documento = doc.id "
					+ " JOIN terminos_transporte ON terminos_transporte_x_documento.id_terminos_transporte = terminos_transporte.id "
					+ " WHERE terminos_transporte.id = :id");
			query.setParameter("id", Long.parseLong(shipCondId));
			answer = (Cliente) query.getSingleResult();
		} catch (NoResultException ne){
			LOGGER.error(ErrorConstants.NO_RESULT.getDescription());
		} catch (Exception e) {
			LOGGER.error(ErrorConstants.UNHANDLED_EXCEPTION.getDescription());
		}
		return answer;
	}	
}
