package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Empresa;

@Stateless
@LocalBean
public class EmpresaDAO extends GenericDAO<Empresa> implements EmpresaDAOLocal{

private static final Logger LOGGER = Logger.getLogger(EmpresaDAO.class);
	
	public EmpresaDAO(){
		this.persistentClass = Empresa.class;
	}	
	
}
