package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Pais;

@Stateless
@LocalBean
public class PaisDAO extends GenericDAO<Pais> implements PaisDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(PaisDAO.class);
	
	public PaisDAO(){
		this.persistentClass = Pais.class;
	}	
	
	public List<Pais> findByRegional(){
		try{
			return this.em.createNamedQuery("Pais.findByRegional").getResultList();
    	}catch(NoResultException e){
    		LOGGER.warn(e);
    		return null;
    	}
	}
	
}
