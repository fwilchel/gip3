package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;

@Local
public interface AgenciaCargaDAOLocal extends IGenericDAO<AgenciaCarga>{ 
	
	public List<AgenciaCarga> consultarAgenciaCargaPorFiltro(AgenciaCarga pFiltro);
		

}
