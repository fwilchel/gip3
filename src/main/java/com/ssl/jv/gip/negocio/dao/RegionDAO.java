package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Region;

@Stateless
@LocalBean
public class RegionDAO extends GenericDAO<Region>{

	private static final Logger LOGGER = Logger.getLogger(RegionDAO.class);
	
	public RegionDAO(){
		this.persistentClass = Region.class;
	}	
	
	public List<Region> findByRegional(String pais){
		try{
			return this.em.createNamedQuery("Region.findByPais").setParameter("pais", pais).getResultList();
    	}catch(NoResultException e){
    		LOGGER.warn(e);
    		return null;
    	}
	}
	
}
