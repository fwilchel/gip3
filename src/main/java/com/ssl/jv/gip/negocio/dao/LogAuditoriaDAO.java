package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.LogAuditoria;

@Stateless
@LocalBean
public class LogAuditoriaDAO extends GenericDAO<LogAuditoria> implements LogAuditoriaDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(LogAuditoriaDAO.class);
	
	public LogAuditoriaDAO(){
		this.persistentClass = LogAuditoria.class;
	}	
	
}
