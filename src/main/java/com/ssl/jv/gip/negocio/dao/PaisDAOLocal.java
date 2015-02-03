package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Pais;

@Local
public interface PaisDAOLocal extends IGenericDAO<Pais>{

	public List<Pais> findByRegional();
	
	public List<Pais> findByPaisTodos();
	
}
