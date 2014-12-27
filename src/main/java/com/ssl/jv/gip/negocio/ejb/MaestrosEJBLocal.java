package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;
import com.ssl.jv.gip.jpa.pojo.LugarIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;

// TODO: Auto-generated Javadoc
/**
 * The Interface MaestrosEJBLocal.
 */
@Local
public interface MaestrosEJBLocal {
	
	/**
	 * Consultar ubicaciones.
	 *
	 * @return the list
	 */
	public List<Ubicacion> consultarUbicaciones();

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
	public Ubicacion actualizarUbicacion(Ubicacion pEntidad);
	
	/**
	 * Consultar agencias carga.
	 *
	 * @return the list
	 */
	public List<AgenciaCarga> consultarAgenciasCarga();
	
	/**
	 * Consultar agencias carga.
	 *
	 * @param pFiltro the filtro
	 * @return the list
	 */
	public List<AgenciaCarga> consultarAgenciasCarga(AgenciaCarga pFiltro);
	
	/**
	 * Crear agencia carga.
	 *
	 * @param pEntidad the entidad
	 * @return the agencia carga
	 */
	public AgenciaCarga crearAgenciaCarga(AgenciaCarga pEntidad);
	
	/**
	 * Actualizar agencia carga.
	 *
	 * @param pEntidad the entidad
	 * @return the agencia carga
	 */
	public AgenciaCarga actualizarAgenciaCarga(AgenciaCarga pEntidad);
	
	/**
	 * Consultar LugarIncoterm.
	 *
	 * @return the list
	 */
	public List<LugarIncoterm> consultarLugarIncoterm();
	
	/**
	 * Consultar LugarIncoterm.
	 *
	 * @param pId the id
	 * @return the LugarIncoterm
	 */
	public LugarIncoterm consultarLugarIncoterm(Long pId);
	
	/**
	 * Crear LugarIncoterm.
	 *
	 * @param pEntidad the entidad
	 * @return the LugarIncoterm
	 */
	public LugarIncoterm crearLugarIncoterm(LugarIncoterm pEntidad);
	
	/**
	 * Modificar LugarIncoterm.
	 *
	 * @param pEntidad the entidad
	 * @return the LugarIncoterm
	 */
	public LugarIncoterm actualizarLugarIncoterm(LugarIncoterm pEntidad);
	
	/**
	 * Consultar IncotermxMedioTrans.
	 *
	 * @return the list
	 */
	public List<TerminoIncoterm> consultarTerminoIncotermXMedioTransporte();
	
	/**
	 * Consultar TerminoIncotermXMedioTransporte.
	 *
	 * @param pId the id
	 * @return the TerminoIncotermXMedioTransporte
	 */
	public TerminoIncoterm consultarTerminoIncotermXMedioTransporte(Long pId);
	
	/**
	 * Crear TerminoIncotermXMedioTransporte.
	 *
	 * @param pEntidad the entidad
	 * @return the TerminoIncotermXMedioTransporte
	 */
	public TerminoIncoterm crearTerminoIncotermXMedioTransporte(TerminoIncoterm pEntidad);
	
	/**
	 * Modificar TerminoIncotermXMedioTransporte.
	 *
	 * @param pEntidad the entidad
	 * @return the TerminoIncotermXMedioTransporte
	 */
	public TerminoIncoterm actualizarTerminoIncotermXMedioTransporte(TerminoIncoterm pEntidad);
	
}
