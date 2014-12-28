package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Ciudad;

@Stateless
@LocalBean
public class CiudadDAO extends GenericDAO<Ciudad> implements CiudadDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(CiudadDAO.class);
	
	public CiudadDAO(){
		this.persistentClass = Ciudad.class;
		
	}

}
