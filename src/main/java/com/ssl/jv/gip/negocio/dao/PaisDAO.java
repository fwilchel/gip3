package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Pais;

@Stateless
@LocalBean
public class PaisDAO extends GenericDAO<Pais>{

	private static final Logger LOGGER = Logger.getLogger(PaisDAO.class);
	
	public PaisDAO(){
		this.persistentClass = Pais.class;
	}	
	
}
