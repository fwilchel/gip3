package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;

/**
 * Session Bean implementation class ProductosXDocumentoDAO
 */
@Stateless
@LocalBean
public class ProductosXDocumentoDAO extends GenericDAO<ProductosXDocumento>
		implements ProductosXDocumentoDAOLocal {

	private static final Logger LOGGER = Logger
			.getLogger(ProductosXDocumentoDAO.class);

	/**
	 * @see GenericDAO#GenericDAO()
	 */
	public ProductosXDocumentoDAO() {
		this.persistentClass = ProductosXDocumento.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductosXDocumento> consultarPorDocumento(Long id) {
		Query query = em
				.createNamedQuery(ProductosXDocumento.FIND_BY_DOCUMENTO);
		query.setParameter("idDocumento", id);
		return query.getResultList();
	}

}
