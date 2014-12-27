package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;

@Stateless
@LocalBean
public class TerminoIncotermDAO extends GenericDAO<TerminoIncoterm> implements TerminoIncotermDAOLocal {
	
	public TerminoIncotermDAO() {
		this.persistentClass = TerminoIncoterm.class;
	}

}
