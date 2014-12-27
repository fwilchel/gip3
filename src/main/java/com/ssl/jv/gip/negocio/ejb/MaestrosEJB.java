package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

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
import com.ssl.jv.gip.negocio.dao.AgenciaCargaDAO;
import com.ssl.jv.gip.negocio.dao.AgenteAduanaDAO;
import com.ssl.jv.gip.negocio.dao.CategoriaInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.CuentaContableDAOLocal;
import com.ssl.jv.gip.negocio.dao.LugarIncotermDAO;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAO;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAO;
import com.ssl.jv.gip.negocio.dao.UnidadDAOLocal;



/**
 * Session Bean implementation class MaestrosEJB
 */
@Stateless
@LocalBean
public class MaestrosEJB implements MaestrosEJBLocal {

	private static final Logger LOGGER = Logger.getLogger(MaestrosEJB.class);

	@EJB
	private UbicacionDAO ubicacionDAO;

	@EJB
	private AgenciaCargaDAO agenciaCargaDAO;
	
	@EJB
	private AgenteAduanaDAO agenteAduanaDAO;

	@EJB
	private LugarIncotermDAO lugarIncotermDAO;

	@EJB
	private ProductoClienteComercioExteriorDAO productoClienteComercioExteriorDAO;

	@EJB
	private UnidadDAOLocal unidadDao;
	
	@EJB
	private CuentaContableDAOLocal cuentaContableDao;
	
	@EJB
	private CategoriaInventarioDAOLocal categoriaInventarioDao;

	@EJB
	private ProductoInventarioDAOLocal productoInventarioDao;
	
	/**
	 * Default constructor.
	 */
	public MaestrosEJB() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarUbicaciones()
	 */
	@Override
	public List<Ubicacion> consultarUbicaciones() {

		List<Ubicacion> listado = new ArrayList<Ubicacion>();

		try {
			listado = (List<Ubicacion>) ubicacionDAO.findAll();
		} catch (Exception e) {

		}

		return listado;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarUbicaciones(com.
	 * ssl.jv.gip.jpa.pojo.Ubicacion)
	 */
	@Override
	public List<Ubicacion> consultarUbicaciones(Ubicacion pFiltro) {

		List<Ubicacion> listado = new ArrayList<Ubicacion>();

		try {
			listado = ubicacionDAO.consultarUbicacionPorFiltro(pFiltro);
		} catch (Exception e) {

		}

		return listado;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarUbicacion(java.lang
	 * .Long)
	 */
	@Override
	public Ubicacion consultarUbicacion(Long pId) {

		Ubicacion entidad = new Ubicacion();

		try {
			entidad = ubicacionDAO.findByPK(pId);
		} catch (Exception e) {

		}

		return entidad;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#crearUbicacion(com.ssl.jv
	 * .gip.jpa.pojo.Ubicacion)
	 */
	@Override
	public Ubicacion crearUbicacion(Ubicacion pEntidad) {

		try {
			pEntidad = ubicacionDAO.add(pEntidad);
		} catch (Exception e) {

		}

		return pEntidad;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#actualizarUbicacion(com.ssl
	 * .jv.gip.jpa.pojo.Ubicacion)
	 */
	@Override
	public Ubicacion actualizarUbicacion(Ubicacion pEntidad) {

		try {
			ubicacionDAO.update(pEntidad);
		} catch (Exception e) {

		}

		return pEntidad;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarAgenciasCarga()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AgenciaCarga> consultarAgenciasCarga() {
		try {
			return (List<AgenciaCarga>) agenciaCargaDAO.findAll();
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando agencias de carga");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarAgenciasCarga(com
	 * .ssl.jv.gip.jpa.pojo.AgenciaCarga)
	 */
	@Override
	public List<AgenciaCarga> consultarAgenciasCarga(AgenciaCarga pFiltro) {
		try {
			return (List<AgenciaCarga>) agenciaCargaDAO
					.consultarAgenciaCargaPorFiltro(pFiltro);
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando agencias de carga por filtro");
			return null;
		}
	}

	@Override
	public AgenciaCarga crearAgenciaCarga(AgenciaCarga pEntidad) {
		try {
			return agenciaCargaDAO.add(pEntidad);
		} catch (Exception e) {
			LOGGER.error(e + "Error creando agencia de carga");
			return null;
		}
	}

	@Override
	public AgenciaCarga actualizarAgenciaCarga(AgenciaCarga pEntidad) {
		try {
			agenciaCargaDAO.update(pEntidad);
			return pEntidad;
		} catch (Exception e) {
			LOGGER.error(e + "Error actualizando agencia de carga");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarLugarIncoterm()
	 */
	@Override
	public List<LugarIncoterm> consultarLugarIncoterm() {

		List<LugarIncoterm> listado = new ArrayList<LugarIncoterm>();

		try {
			listado = (List<LugarIncoterm>) lugarIncotermDAO.findAll();
		} catch (Exception e) {

		}

		return listado;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarLugarIncoterm(java
	 * .lang.Long)
	 */
	@Override
	public LugarIncoterm consultarLugarIncoterm(Long pId) {

		LugarIncoterm entidad = new LugarIncoterm();

		try {
			entidad = lugarIncotermDAO.findByPK(pId);
		} catch (Exception e) {

		}

		return entidad;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#crearLugarIncoterm(com.ssl
	 * .jv.gip.jpa.pojo.LugarIncoterm)
	 */
	@Override
	public LugarIncoterm crearLugarIncoterm(LugarIncoterm pEntidad) {

		try {
			pEntidad = lugarIncotermDAO.add(pEntidad);
		} catch (Exception e) {

		}

		return pEntidad;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#actualizarLugarIncoterm(com
	 * .ssl.jv.gip.jpa.pojo.LugarIncoterm)
	 */
	@Override
	public LugarIncoterm actualizarLugarIncoterm(LugarIncoterm pEntidad) {

		try {
			lugarIncotermDAO.update(pEntidad);
		} catch (Exception e) {

		}

		return pEntidad;

	}

	@Override
	public List<ProductosXClienteComext> consultarProductosClienteComercioExteriorPorFiltro(
			ProductosXClienteComExtFiltroVO filtroVO) {
		return productoClienteComercioExteriorDAO.consultarPorFiltro(filtroVO);
	}

	@Override
	public List<ProductosXClienteComext> consultarProductosClienteComercioExterior() {
		return productoClienteComercioExteriorDAO.consultarTodos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarAgentesAduana(com
	 * .ssl.jv.gip.jpa.pojo.AgenciaCarga)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AgenteAduana> consultarAgentesAduana() {
		try {
			return (List<AgenteAduana>) agenteAduanaDAO.findAll();
		} catch (Exception e) {
			LOGGER.error(e + " Error consultando agentes de aduana");
			return null;
		}
	}

	@Override
	public AgenteAduana crearAgenteAduana(AgenteAduana pEntidad) {
		try {
			return agenteAduanaDAO.add(pEntidad);
		} catch (Exception e) {
			LOGGER.error(e + " Error creando agente de aduana");
			return null;
		}
	}

	@Override
	public AgenteAduana actualizarAgenteAduana(AgenteAduana pEntidad) {
		try {
			agenteAduanaDAO.update(pEntidad);
			return pEntidad;
		} catch (Exception e) {
			LOGGER.error(e + " Error actualizando agente de aduana");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Unidad> consultarUnidades(){
		return (List<Unidad>)this.unidadDao.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public List<CategoriasInventario> consultarCategoriasInventario(){
		return (List<CategoriasInventario>)this.categoriaInventarioDao.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public List<CuentaContable> consultarCuentasContables(){
		return (List<CuentaContable>)this.cuentaContableDao.findAll();
	}
	
    public void actualizarProductoInventario(ProductosInventario pi){
    	this.productoInventarioDao.update(pi);
    }

    public void crearProductoInventario(ProductosInventario pi){
   		this.productoInventarioDao.add(pi);
    }

}
