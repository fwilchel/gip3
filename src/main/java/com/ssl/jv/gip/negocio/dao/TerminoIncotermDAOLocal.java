package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;

@Local
public interface TerminoIncotermDAOLocal extends IGenericDAO<TerminoIncoterm>{
	
	public List<TerminoIncoterm> consultarListaIncontermPorCliente(Long idCliente);
}
