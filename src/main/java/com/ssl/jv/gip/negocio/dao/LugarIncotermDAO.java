package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.LugarIncoterm;

@Stateless
@LocalBean
public class LugarIncotermDAO extends GenericDAO<LugarIncoterm>{
	
	private static final Logger LOGGER = Logger.getLogger(LugarIncotermDAO.class);
	
	public LugarIncotermDAO(){
		this.persistentClass = LugarIncoterm.class;
	}
	
}
