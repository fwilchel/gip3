package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Proveedor;

/**
 * Session Bean implementation class ProveedorDAO
 */
@Stateless
@LocalBean
public class ProveedorDAO extends GenericDAO<Proveedor> implements
	ProveedorDAOLocal {

	private static final Logger LOGGER = Logger
			.getLogger(ProveedorDAO.class);

	/**
	 * @see GenericDAO#GenericDAO()
	 */
	public ProveedorDAO() {
		this.persistentClass = Proveedor.class;
	}

}
