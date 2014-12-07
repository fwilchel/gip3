package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;

@Local
public interface MaestrosEJBLocal {

	/**
	 * Consultar ubicaciones.
	 *
	 * @param pFiltro the filtro
	 * @return the list
	 */
	public List<Ubicacion> consultarUbicaciones(Ubicacion pFiltro);
	
	/**
	 * Consultar ubicacion.
	 *
	 * @param pId the id
	 * @return the ubicacion
	 */
	public Ubicacion consultarUbicacion(Long pId);
	
	/**
	 * Crear ubicacion.
	 *
	 * @param pEntidad the entidad
	 * @return the ubicacion
	 */
	public Ubicacion crearUbicacion(Ubicacion pEntidad);
	
	/**
	 * Modificar ubicacion.
	 *
	 * @param pEntidad the entidad
	 * @return the ubicacion
	 */
	public Ubicacion modificarUbicacion(Ubicacion pEntidad);
	
}
