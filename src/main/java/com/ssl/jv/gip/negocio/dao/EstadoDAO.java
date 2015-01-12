package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Estado;

/**
 * Session Bean implementation class EstadoDAO
 */
@Stateless
@LocalBean
public class EstadoDAO extends GenericDAO<Estado> implements EstadoDAOLocal {

	private static final Logger LOGGER = Logger.getLogger(EstadoDAO.class);

	/**
	 * Default constructor.
	 */
	public EstadoDAO() {
		this.persistentClass = Estado.class;
	}

}
