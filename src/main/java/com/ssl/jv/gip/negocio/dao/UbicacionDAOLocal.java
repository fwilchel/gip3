package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;

@Local
public interface UbicacionDAOLocal extends IGenericDAO<Ubicacion>{ 

	public List<Ubicacion> consultarUbicacionPorFiltro(Ubicacion pFiltro);
	
	public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario);

	public List<Ubicacion> consultarPorIds(List<Long> ids);
	
	public List<Ubicacion> consultarTodasOrdenadas();
	
	
}
