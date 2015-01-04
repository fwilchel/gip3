package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Moneda;

/**
 * Session Bean implementation class MonedaDAO
 */
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Moneda> consultarTodas() {
		Query query = em.createNamedQuery(Moneda.MONEDA_FIND_ALL);
		return query.getResultList();
	}

}
