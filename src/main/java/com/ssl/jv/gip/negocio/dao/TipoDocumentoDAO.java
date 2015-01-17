package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.TipoDocumento;

/**
 * Session Bean implementation class TipoDocumentoDAO
 */
@Stateless
@LocalBean
public class TipoDocumentoDAO extends GenericDAO<TipoDocumento> implements
		TipoDocumentoDAOLocal {

	private static final Logger LOGGER = Logger
			.getLogger(TipoDocumentoDAO.class);

	/**
	 * @see GenericDAO#GenericDAO()
	 */
	public TipoDocumentoDAO() {
		this.persistentClass = TipoDocumento.class;
	}

}
