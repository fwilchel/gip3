package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CategoriaCostoLogistico;

@Stateless
@LocalBean
public class CategoriaCostoLogisticoDAO extends GenericDAO<CategoriaCostoLogistico> implements CategoriaCostoLogisticoDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(CategoriaCostoLogisticoDAO.class);
	
	public CategoriaCostoLogisticoDAO(){
		this.persistentClass = CategoriaCostoLogistico.class;
	}	

}
