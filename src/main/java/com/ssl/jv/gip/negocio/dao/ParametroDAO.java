package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Parametro;


@Stateless
@LocalBean
public class ParametroDAO extends GenericDAO<Parametro> implements ParametroDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(PaisDAO.class);
	
	public ParametroDAO(){
		this.persistentClass = Parametro.class;
	}	
	
}
