package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.RequerimientosXDocumento;


@Stateless
@LocalBean
public class RequerimientoxdocumentoDAO extends GenericDAO<RequerimientosXDocumento> implements  RequerimientoxdocumentoDAOLocal {
	
	public RequerimientoxdocumentoDAO () {
	    this.persistentClass = RequerimientosXDocumento.class;
	  }
	
}
