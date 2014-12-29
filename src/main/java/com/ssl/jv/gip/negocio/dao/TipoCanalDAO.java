package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.TipoCanal;


@Stateless
@LocalBean
public class TipoCanalDAO extends GenericDAO<TipoCanal> implements TipoCanalDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(CiudadDAO.class);
	
	public TipoCanalDAO(){
		this.persistentClass = TipoCanal.class;
	}
	

}
