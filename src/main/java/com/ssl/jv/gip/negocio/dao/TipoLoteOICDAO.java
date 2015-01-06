package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.TipoLoteoic;

@Stateless
@LocalBean
public class TipoLoteOICDAO extends GenericDAO<TipoLoteoic> implements TipoLoteOICDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(TipoLoteOICDAO.class);
	
	public TipoLoteOICDAO(){
		this.persistentClass = TipoLoteoic.class;
	}	
	
}
