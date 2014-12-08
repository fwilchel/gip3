package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;

@Local
public interface UbicacionDAOLocal extends IGenericDAO<Ubicacion>{

	public List<Ubicacion> consultarUbicacionPorFiltro(Ubicacion pFiltro);
	
	
}
