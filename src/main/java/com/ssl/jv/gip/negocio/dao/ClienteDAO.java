package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.negocio.dto.ClienteFiltroVO;

@Stateless
@LocalBean
public class ClienteDAO extends GenericDAO<Cliente> implements ClienteDAOLocal {

	private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class);

	/**
	 * Default constructor.
	 */
	public ClienteDAO() {
		this.persistentClass = Cliente.class;
	}

	@Override
	public List<Cliente> consultarPorFiltro(ClienteFiltroVO filtroVO) {
		// CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		// CriteriaQuery<Cliente> query = criteriaBuilder
		// .createQuery(Cliente.class);
		// Root<Cliente> from = query.from(Cliente.class);
		// from.join("tipoCanal", JoinType.LEFT).join("usuarios",
		// JoinType.LEFT).join("agenteAduana", JoinType.LEFT).join("metodoPago",
		// JoinType.LEFT);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> consultarActivosPorUsuario(String idUsuario) {
		Query query = em
				.createNamedQuery(Cliente.CLIENTE_ACTIVO_FIND_BY_USUARIO);
		query.setParameter("idUsuario", idUsuario);
		return query.getResultList();
	}

}
