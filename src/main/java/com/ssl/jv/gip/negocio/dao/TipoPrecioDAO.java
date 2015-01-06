package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.TipoPrecio;


@Stateless
@LocalBean
public class TipoPrecioDAO extends GenericDAO<TipoPrecio> implements TipoPrecioDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(TipoPrecioDAO.class);
	
	public TipoPrecioDAO(){
		this.persistentClass = TipoPrecio.class;
	}
	
	

}
