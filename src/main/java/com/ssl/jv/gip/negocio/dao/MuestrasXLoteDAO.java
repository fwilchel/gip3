package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;

@Stateless
@LocalBean
public class MuestrasXLoteDAO extends GenericDAO<Muestrasxlote> implements MuestrasXLoteDAOLocal {
	
	private static final Logger LOGGER = Logger.getLogger(MuestrasXLoteDAO.class);
	
	public MuestrasXLoteDAO(){
		this.persistentClass = Muestrasxlote.class;
	}

	@Override
	public List<Muestrasxlote> consultarMuestrasPorCantidad(BigDecimal cantidad) {
		Query query = em
				.createNamedQuery("Muestrasxlote.findByCantidad");
		query.setParameter("cantidad",  cantidad.longValue());
		query.setParameter("cantidad1", cantidad.longValue());
		return query.getResultList();
	}

}
