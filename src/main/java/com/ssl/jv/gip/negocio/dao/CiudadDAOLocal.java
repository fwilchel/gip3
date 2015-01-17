package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Ciudad;

@Local
public interface CiudadDAOLocal extends IGenericDAO<Ciudad>{
	
	public List<Ciudad> findByPais(String idPais);

}
