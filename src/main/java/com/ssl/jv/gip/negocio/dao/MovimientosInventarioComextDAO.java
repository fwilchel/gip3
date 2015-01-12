package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;

@Stateless
@LocalBean
public class MovimientosInventarioComextDAO extends GenericDAO<MovimientosInventarioComext> implements MovimientosInventarioComextDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(MovimientosInventarioComextDAO.class);
	
	public MovimientosInventarioComextDAO(){
		this.persistentClass = MovimientosInventarioComext.class;
	}
	
	@Override
	public List<MovimientosInventarioComext> getUltimosSaldos(){
		try{
			return this.em.createNamedQuery("MovimientosInventarioComext.ultimosSaldos").getResultList();
    	}catch(NoResultException e){
    		LOGGER.warn(e);
    		return null;
    	}
	}
	
	
}
