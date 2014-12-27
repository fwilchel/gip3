package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;
import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.jpa.pojo.LugarIncoterm;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;

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
	 * @param pFiltro
	 *            the filtro
	 * @return the list
	 */
	public List<Ubicacion> consultarUbicaciones(Ubicacion pFiltro);

	/**
	 * Consultar ubicacion.
	 *
	 * @param pId
	 *            the id
	 * @return the ubicacion
	 */
	public Ubicacion consultarUbicacion(Long pId);

	/**
	 * Crear ubicacion.
	 *
	 * @param pEntidad
	 *            the entidad
	 * @return the ubicacion
	 */
	public Ubicacion crearUbicacion(Ubicacion pEntidad);

	/**
	 * Modificar ubicacion.
	 *
	 * @param pEntidad
	 *            the entidad
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
	 * @param pFiltro
	 *            the filtro
	 * @return the list
	 */
	public List<AgenciaCarga> consultarAgenciasCarga(AgenciaCarga pFiltro);

	/**
	 * Crear agencia carga.
	 *
	 * @param pEntidad
	 *            the entidad
	 * @return the agencia carga
	 */
	public AgenciaCarga crearAgenciaCarga(AgenciaCarga pEntidad);

	/**
	 * Actualizar agencia carga.
	 *
	 * @param pEntidad
	 *            the entidad
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
	 * @param pId
	 *            the id
	 * @return the LugarIncoterm
	 */
	public LugarIncoterm consultarLugarIncoterm(Long pId);

	/**
	 * Crear LugarIncoterm.
	 *
	 * @param pEntidad
	 *            the entidad
	 * @return the LugarIncoterm
	 */
	public LugarIncoterm crearLugarIncoterm(LugarIncoterm pEntidad);

	/**
	 * Modificar LugarIncoterm.
	 *
	 * @param pEntidad
	 *            the entidad
	 * @return the LugarIncoterm
	 */
	public LugarIncoterm actualizarLugarIncoterm(LugarIncoterm pEntidad);

	/**
	 * 
	 * @param filtroVO
	 * @return lista de productos por cliente que cumplan las condiciones del
	 *         filtro
	 */
	public List<ProductosXClienteComext> consultarProductosClienteComercioExteriorPorFiltro(
			ProductosXClienteComExtFiltroVO filtroVO);

	/**
	 * @return lista de productos por cliente
	 */
	public List<ProductosXClienteComext> consultarProductosClienteComercioExterior();


	/**Consulta todos los agentes de aduana
	 * 
	 * @return lista de agentes de aduana
	 */
	public List<AgenteAduana> consultarAgentesAduana();

	/**Crea un agente de aduana
	 * 
	 * @param pEntidad nuevo agente de aduana
	 * @return
	 */
	public AgenteAduana crearAgenteAduana(AgenteAduana pEntidad);

	/**Actualiza un agente de aduana
	 * 
	 * @param pEntidad agente actualizado
	 * @return 
	 */
	public AgenteAduana actualizarAgenteAduana(AgenteAduana pEntidad);
	
	public List<Unidad> consultarUnidades();
	
	public List<CategoriasInventario> consultarCategoriasInventario();
	
	public List<CuentaContable> consultarCuentasContables();
	
    public void actualizarProductoInventario(ProductosInventario pi);
    
    public void crearProductoInventario(ProductosInventario pi);

}
