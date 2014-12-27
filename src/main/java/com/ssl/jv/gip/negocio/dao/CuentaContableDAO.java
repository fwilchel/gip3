package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CuentaContable;

@Stateless
@LocalBean
public class CuentaContableDAO extends GenericDAO<CuentaContable> implements CuentaContableDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(CuentaContableDAO.class);
	
	public CuentaContableDAO(){
		this.persistentClass = CuentaContable.class;
	}	
	
}
