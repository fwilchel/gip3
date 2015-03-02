package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Moneda;

@Stateless
@LocalBean
public class MonedaDAO extends GenericDAO<Moneda> implements MonedaDAOLocal {

	private static final Logger LOGGER = Logger.getLogger(MonedaDAO.class);

	/**
	 * @see GenericDAO#GenericDAO()
	 */
	public MonedaDAO() {
		this.persistentClass = Moneda.class;
	}


}
