package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Rol;

@Stateless
@LocalBean
public class RolDAO extends GenericDAO<Rol> implements RolDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(RolDAO.class);
	
	public RolDAO(){
		this.persistentClass = Rol.class;
	}
	
	
}
