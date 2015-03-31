package com.ssl.jv.gip.negocio.ejb;

import static com.ssl.jv.gip.web.util.SecurityFilter.LOGGER;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;
import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.CategoriaCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.FactsCurrencyConversion;
import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.LugarIncoterm;
import com.ssl.jv.gip.jpa.pojo.MedioTransporte;
import com.ssl.jv.gip.jpa.pojo.MetodoPago;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComextPK;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
import com.ssl.jv.gip.jpa.pojo.RangoCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;
import com.ssl.jv.gip.jpa.pojo.TipoCanal;
import com.ssl.jv.gip.jpa.pojo.TipoLoteoic;
import com.ssl.jv.gip.jpa.pojo.TipoPrecio;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dao.AgenciaCargaDAOLocal;
import com.ssl.jv.gip.negocio.dao.AgenteAduanaDAOLocal;
import com.ssl.jv.gip.negocio.dao.CategoriaCostoLogisticoDAOLocal;
import com.ssl.jv.gip.negocio.dao.CategoriaInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.CiudadDAOLocal;
import com.ssl.jv.gip.negocio.dao.ClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.CuentaContableDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.FactsCurrencyConversionDAOLocal;
import com.ssl.jv.gip.negocio.dao.IncotermXMedioTransDAOLocal;
import com.ssl.jv.gip.negocio.dao.ItemCostoLogisticoDAOLocal;
import com.ssl.jv.gip.negocio.dao.LogAuditoriaDAOLocal;
import com.ssl.jv.gip.negocio.dao.LugarIncotermDAOLocal;
import com.ssl.jv.gip.negocio.dao.MedioTransporteDAOLocal;
import com.ssl.jv.gip.negocio.dao.MetodoPagoDAOLocal;
import com.ssl.jv.gip.negocio.dao.MonedaDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientosInventarioComextDAOLocal;
import com.ssl.jv.gip.negocio.dao.PaisDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosInventarioComextDAOLocal;
import com.ssl.jv.gip.negocio.dao.PuntoVentaDAOLocal;
import com.ssl.jv.gip.negocio.dao.RangoCostoLogisticoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminoIncotermDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoCanalDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoLoteOICDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoPrecioDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dao.UnidadDAOLocal;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioComextFiltroVO;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;

/**
 * Session Bean implementation class MaestrosEJB
 *
 * @param <puntoVentaDAO>
 */
@Stateless
@LocalBean
public class MaestrosEJB<puntoVentaDAO> implements MaestrosEJBLocal {

	private static final Logger LOGGER = Logger.getLogger(MaestrosEJB.class);

	@EJB
	private UbicacionDAOLocal ubicacionDAO;

	@EJB
	private AgenciaCargaDAOLocal agenciaCargaDAO;

	@EJB
	private AgenteAduanaDAOLocal agenteAduanaDAO;

	@EJB
	private LugarIncotermDAOLocal lugarIncotermDAO;

	@EJB
	private ProductoClienteComercioExteriorDAOLocal productoClienteComercioExteriorDAO;

	@EJB
	private IncotermXMedioTransDAOLocal incotermXMedioTransDAO;

	@EJB
	private TerminoIncotermDAOLocal terminoIncotermDAO;

	@EJB
	private MedioTransporteDAOLocal medioTransporteDAO;

	@EJB
	private UnidadDAOLocal unidadDao;

	@EJB
	private CuentaContableDAOLocal cuentaContableDao;

	@EJB
	private CategoriaInventarioDAOLocal categoriaInventarioDao;

	@EJB
	private ProductoInventarioDAOLocal productoInventarioDao;

	@EJB
	private ClienteDAOLocal clienteDao;

	@EJB
	private TipoLoteOICDAOLocal tipoLoteOicDao;

	@EJB
	private ProductosInventarioComextDAOLocal productosInventarioComextDao;

	@EJB
	private MonedaDAOLocal monedaDAO;

	@EJB
	private CategoriaInventarioDAOLocal categoriaInventarioDAO;

	@EJB
	private PaisDAOLocal paisDAO;

	@EJB
	private CiudadDAOLocal ciudadDAO;

	@EJB
	private TipoCanalDAOLocal tipoCanalDAO;

	@EJB
	private MetodoPagoDAOLocal metodoPagoDAO;

	@EJB
	private TipoPrecioDAOLocal tipoPrecioDAO;

	@EJB
	private CategoriaCostoLogisticoDAOLocal categoriaCostoLogisticoDAO;

	@EJB
	private ItemCostoLogisticoDAOLocal itemCostoLogisticoDAO;

	@EJB
	private RangoCostoLogisticoDAOLocal rangoCostoLogisticoDAO;

	@EJB
	private MovimientosInventarioComextDAOLocal movimientosInventarioComextDAO;

	@EJB
	private PuntoVentaDAOLocal puntoVentaDao;

	@EJB
	private FactsCurrencyConversionDAOLocal conversionMonedaDAO;

	@EJB
	private DocumentoDAOLocal documentoDAO;

	@EJB
	private LogAuditoriaDAOLocal logAuditoriaDAO;

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
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarUbicaciones(com.
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
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#crearUbicacion(com.ssl.jv
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
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarAgenciasCarga(com
	 * .ssl.jv.gip.jpa.pojo.AgenciaCarga)
	 */
	@Override
	public List<AgenciaCarga> consultarAgenciasCarga(AgenciaCarga pFiltro) {
		try {
			return (List<AgenciaCarga>) agenciaCargaDAO.consultarAgenciaCargaPorFiltro(pFiltro);
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
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#crearLugarIncoterm(com.ssl
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
	public List<ProductosXClienteComext> consultarProductosClienteComercioExteriorPorFiltro(ProductosXClienteComExtFiltroVO filtroVO) {
		return productoClienteComercioExteriorDAO.consultarPorFiltro(filtroVO);
	}

	@Override
	public List<ProductosXClienteComext> consultarProductosClienteComercioExterior() {
		return productoClienteComercioExteriorDAO.consultarTodos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarAgentesAduana(com
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
	public List<TerminoIncotermXMedioTransporte> consultarTerminoIncotermXMedioTransporte() {
		return incotermXMedioTransDAO.consultarTerminoIncotermXMedioTransporte();
	}

	@Override
	public TerminoIncotermXMedioTransporte consultarTerminoIncotermXMedioTransporte(Long pId) {
		TerminoIncotermXMedioTransporte entidad = new TerminoIncotermXMedioTransporte();

		try {
			entidad = incotermXMedioTransDAO.findByPK(pId);
		} catch (Exception e) {

		}

		return entidad;
	}

	@Override
	public TerminoIncotermXMedioTransporte crearTerminoIncotermXMedioTransporte(TerminoIncotermXMedioTransporte pEntidad) {
		try {
			pEntidad = incotermXMedioTransDAO.add(pEntidad);
		} catch (Exception e) {

		}

		return pEntidad;
	}

	@Override
	public TerminoIncotermXMedioTransporte actualizarTerminoIncotermXMedioTransporte(TerminoIncotermXMedioTransporte pEntidad) {
		try {
			incotermXMedioTransDAO.update(pEntidad);
		} catch (Exception e) {

		}

		return pEntidad;
	}

	@Override
	public List<TerminoIncoterm> consultarTerminoIncotermActivo() {
		List<TerminoIncoterm> listado = new ArrayList<TerminoIncoterm>();

		try {
			listado = (List<TerminoIncoterm>) terminoIncotermDAO.findAll();
		} catch (Exception e) {

		}

		return listado;
	}

	@Override
	public List<MedioTransporte> consultarMedioTransporteActivo() {
		List<MedioTransporte> listado = new ArrayList<MedioTransporte>();
		try {
			listado = (List<MedioTransporte>) medioTransporteDAO.findAllActivoBoolean();
		} catch (Exception e) {

		}

		return listado;
	}

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
	public List<Unidad> consultarUnidades() {
		List<Unidad> lista = (List<Unidad>) this.unidadDao.findAll();
		Collections.sort(lista);
		return lista;
	}

	public List<CategoriasInventario> consultarCategoriasInventario() {
		return (List<CategoriasInventario>) this.categoriaInventarioDao.findAll();
	}

	@SuppressWarnings("unchecked")
	public List<CuentaContable> consultarCuentasContables() {
		return (List<CuentaContable>) this.cuentaContableDao.findAll();
	}

	public void actualizarProductoInventario(ProductosInventario pi) {
		this.productoInventarioDao.update(pi);
	}

	public void crearProductoInventario(ProductosInventario pi) {
		this.productoInventarioDao.add(pi);
	}

	public Object[] consultarProductos(ProductosInventario pi, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count) {
		return this.productoInventarioDao.consultar(pi, first, pageSize, sortField, sortOrder, count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> consultarClientes() {
		try {
			return (List<Cliente>) clienteDao.findAll();
		} catch (Exception e) {
			LOGGER.error(e + " Error consultando clientes");
			return null;
		}
	}

	@Override
	public Cliente crearCliente(Cliente pEntidad, LogAuditoria auditoria) {
		try {
			clienteDao.guardarCliente(pEntidad);
			LOGGER.debug("Crear log de auditoria");
			auditoria.setTabla(Cliente.class.getName());
			auditoria.setAccion("CRE");
			auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
			auditoria.setIdRegTabla(pEntidad.getId());
			auditoria = logAuditoriaDAO.add(auditoria);
			LOGGER.debug("Log de auditoria creado con id: " + auditoria.getIdLog());
			return pEntidad;
		} catch (Exception e) {
			LOGGER.error(e + " Error creando clientes");
			return null;
		}
	}

	@Override
	public Cliente actualizarCliente(Cliente pEntidad, LogAuditoria auditoria) {
		try {
			clienteDao.update(pEntidad);
			LOGGER.debug("Crear log de auditoria");
			auditoria.setTabla(Cliente.class.getName());
			auditoria.setAccion("MOD");
			auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
			auditoria.setIdRegTabla(pEntidad.getId());
			auditoria = logAuditoriaDAO.add(auditoria);
			LOGGER.debug("Log de auditoria creado con id: " + auditoria.getIdLog());
			return pEntidad;
		} catch (Exception e) {
			LOGGER.error(e + " Error creando clientes");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TipoLoteoic> consultarTipoLotesOic() {
		return (List<TipoLoteoic>) this.tipoLoteOicDao.findAll();
	}

	public ProductosInventarioComext consultarProductoInventarioComext(String sku) {
		try {
			ProductosInventarioComext pi = productosInventarioComextDao.findBySku(sku);
			return pi;
		} catch (Exception e) {
			return null;
		}
	}

	public void crearProductoInventarioComext(ProductosInventarioComext pic) {
		this.productosInventarioComextDao.add(pic);
	}

	public void actualizarProductoInventarioComext(ProductosInventarioComext pic) {
		this.productosInventarioComextDao.update(pic);
	}

	@Override
	public List<Cliente> consultarClientesActivosPorUsuario(String idUsuario) {
		return clienteDao.consultarActivosPorUsuario(idUsuario);
	}

	@Override
	public List<Moneda> consultarMonedas() {
		return (List<Moneda>) monedaDAO.findAll();
	}

	@Override
	public List<CategoriasInventario> consultarCategoriasInventarios() {
		return categoriaInventarioDAO.findAll();
	}

	@Override
	public List<ProductosInventario> consultarProductosInventarios() {
		return productoInventarioDao.consultarTodos();
	}

	@Override
	public List<ProductosInventario> consultarProductosInventariosPorUsuarioCategoriaSkuNombreAndEstado(ProductosInventarioFiltroDTO filtroDTO) {
		return productoInventarioDao.consultarPorUsuarioCategoriaSKUNombreAndEstado(filtroDTO);
	}

	@Override
	public List<ProductosInventario> consultarProductosInventariosActivos() {
		return productoInventarioDao.consultarActivos();
	}

	@Override
	public void guardarRelacionProductosClienteComercioExterior(String idUsuario, List<ProductosXClienteComext> productosXClienteComexts) {
		for (ProductosXClienteComext productosXClienteComext : productosXClienteComexts) {
			if (productosXClienteComext.getProductosInventario().isIncluido()) {
				productosXClienteComext.setCliente(clienteDao.findByPK(productosXClienteComext.getCliente().getId()));
				if (productosXClienteComext.getId() != null) {
					ProductosXClienteComext productosXClienteComextCons = productoClienteComercioExteriorDAO.consultarPorPK(productosXClienteComext.getPk());
					// "Precio","DescuentoxProducto","Otros_Descuentos","Reg_Sanitario"
					if (!productosXClienteComextCons.getPrecio().equals(productosXClienteComext.getPrecio())) {
						logAuditoriaDAO.add(getLogAuditoria("MOD", 85L, productosXClienteComext.getId(), idUsuario, "Productos_x_Cliente_ComExt", "Precio",
								productosXClienteComextCons.getPrecio().toPlainString(), productosXClienteComext.getPrecio().toPlainString()));
					}

					if (!productosXClienteComextCons.getDescuentoxproducto().equals(productosXClienteComext.getDescuentoxproducto())) {
						logAuditoriaDAO.add(getLogAuditoria("MOD", 85L, productosXClienteComext.getId(), idUsuario, "Productos_x_Cliente_ComExt", "DescuentoxProducto", productosXClienteComextCons
								.getDescuentoxproducto().toPlainString(), productosXClienteComext.getDescuentoxproducto().toPlainString()));
					}

					if (!productosXClienteComextCons.getOtrosDescuentos().equals(productosXClienteComext.getOtrosDescuentos())) {
						logAuditoriaDAO.add(getLogAuditoria("MOD", 85L, productosXClienteComext.getId(), idUsuario, "Productos_x_Cliente_ComExt", "Otros_Descuentos", productosXClienteComextCons
								.getOtrosDescuentos().toPlainString(), productosXClienteComext.getOtrosDescuentos().toPlainString()));
					}

					if (!productosXClienteComextCons.getRegSanitario().equals(productosXClienteComext.getRegSanitario())) {
						logAuditoriaDAO.add(getLogAuditoria("MOD", 85L, productosXClienteComext.getId(), idUsuario, "Productos_x_Cliente_ComExt", "Reg_Sanitario", productosXClienteComextCons.getRegSanitario(),
								productosXClienteComext.getRegSanitario()));
					}
					productoClienteComercioExteriorDAO.update(productosXClienteComext);
				} else {
					Number max = productoClienteComercioExteriorDAO.consultarMaximoValorColumna("id");
					if (max == null) {
						productosXClienteComext.setId(1L);
					} else {
						productosXClienteComext.setId(max.longValue() + 1);
					}
					productosXClienteComext = productoClienteComercioExteriorDAO.add(productosXClienteComext);
					logAuditoriaDAO.add(getLogAuditoria("CRE", 85L, productosXClienteComext.getId(), idUsuario, "Productos_x_Cliente_ComExt", null, null, null));
				}
			} else if (productosXClienteComext.getId() != null) {
				productoClienteComercioExteriorDAO.delete(productosXClienteComext);
				logAuditoriaDAO.add(getLogAuditoria("ELI", 85L, productosXClienteComext.getId(), idUsuario, "Productos_x_Cliente_ComExt", null, null, null));
			}

		}
	}

	private LogAuditoria getLogAuditoria(String accion, Long idFuncionalidad, Long idRegTabla, String idUsuario, String tabla, String campo, String valorAnterior, String valorNuevo) {
		LogAuditoria logAuditoria = new LogAuditoria();
		logAuditoria.setAccion(accion);
		logAuditoria.setIdFuncionalidad(idFuncionalidad);
		logAuditoria.setIdRegTabla(idRegTabla);
		logAuditoria.setIdUsuario(idUsuario);
		logAuditoria.setTabla(tabla);
		logAuditoria.setCampo(campo);
		logAuditoria.setValorAnterior(valorAnterior);
		logAuditoria.setValorNuevo(valorNuevo);
		logAuditoria.setFecha(new Timestamp(System.currentTimeMillis()));
		return logAuditoria;
	}

	@Override
	public void cargarProductosPorClienteComExtDesdeArchivo(List<String[]> lines) {

		int numLinea = 1;
		List<ProductosXClienteComext> productosXClienteComexts = new ArrayList<ProductosXClienteComext>();
		for (String[] lineFile : lines) {
			productosXClienteComexts.add(getProductoXClienteComExt(numLinea, lineFile));
			numLinea++;
		}

		for (ProductosXClienteComext productosXClienteComext : productosXClienteComexts) {
			ProductosXClienteComext consultarPorPK = productoClienteComercioExteriorDAO.consultarPorPK(productosXClienteComext.getPk());
			if (consultarPorPK != null) {
				productoClienteComercioExteriorDAO.update(productosXClienteComext);
			} else {
				Number max = productoClienteComercioExteriorDAO.consultarMaximoValorColumna("id");
				if (max == null) {
					productosXClienteComext.setId(1L);
				} else {
					productosXClienteComext.setId(max.longValue() + 1);
				}
				productoClienteComercioExteriorDAO.add(productosXClienteComext);
			}
		}

	}

	private ProductosXClienteComext getProductoXClienteComExt(int numLinea, String[] lineFile) {
		ProductosXClienteComext productosXClienteComext = new ProductosXClienteComext();
		ProductosInventario productoInv = null;
		try {
			productoInv = productoInventarioDao.consultarPorSku(lineFile[0]);
		} catch (Exception e) {
			productoInv = null;
		}
		if (productoInv == null) {
			throw new EJBException(String.format("Producto referenciando en la línea %d no existe", numLinea));
		}
		Long idProducto = productoInv.getId();
		Long idCliente = Long.parseLong(lineFile[1]);
		ProductosXClienteComextPK pk = new ProductosXClienteComextPK(idProducto, idCliente);
		productosXClienteComext.setPk(pk);
		ProductosXClienteComext productoClienteComext = productoClienteComercioExteriorDAO.consultarPorPK(pk);
		if (productoClienteComext != null) {
			productosXClienteComext = productoClienteComext;
		} else {
			productosXClienteComext.setProductosInventario(productoInv);
			productosXClienteComext.setCliente(new Cliente(idCliente));
		}
		productosXClienteComext.setPrecio(new BigDecimal(lineFile[6]));
		productosXClienteComext.setIdMoneda(lineFile[7]);
		productosXClienteComext.setIva(new BigDecimal(lineFile[8]));
		productosXClienteComext.setDescuentoxproducto(new BigDecimal(lineFile[9]));
		productosXClienteComext.setOtrosDescuentos(new BigDecimal(lineFile[10]));
		productosXClienteComext.setActivo(Boolean.parseBoolean(lineFile[12]));
		if (lineFile.length == 14) {
			productosXClienteComext.setRegSanitario(lineFile[13]);
		}
		return productosXClienteComext;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pais> consultarPaises() {
		return (List<Pais>) paisDAO.findAll();
	}

	@Override
	public List<Ciudad> consultarCiudadesPorPais(String idPais) {
		return ciudadDAO.findByPais(idPais);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoCanal> consultarTiposCanal() {
		return (List<TipoCanal>) tipoCanalDAO.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MetodoPago> consultarMetodosPago() {
		return (List<MetodoPago>) metodoPagoDAO.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoPrecio> consultarTiposPrecio() {
		return (List<TipoPrecio>) tipoPrecioDAO.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductosInventario consultarPorSku(String sku) {
		ProductosInventario pi = productoInventarioDao.consultarPorSku(sku);
		return pi;
	}

	public List<CategoriaCostoLogistico> consultarCategoriasCostosLogisticos() {
		return (List<CategoriaCostoLogistico>) this.categoriaCostoLogisticoDAO.findAll();
	}

	public CategoriaCostoLogistico consultarCategoriaCostoLogistico(Long id) {
		return this.categoriaCostoLogisticoDAO.findByPK(id);
	}

	public CategoriaCostoLogistico actualizarCategoriaCostoLogistico(CategoriaCostoLogistico ccl) {
		this.categoriaCostoLogisticoDAO.update(ccl);
		return ccl;
	}

	public CategoriaCostoLogistico crearCategoriaCostoLogistico(CategoriaCostoLogistico ccl) {
		return this.categoriaCostoLogisticoDAO.add(ccl);
	}

	public List<ItemCostoLogistico> consultarItemsCostosLogisticos() {
		return (List<ItemCostoLogistico>) this.itemCostoLogisticoDAO.findAll();
	}

	public ItemCostoLogistico consultarItemCostoLogistico(Long id) {
		return this.itemCostoLogisticoDAO.findByPK(id);
	}


	
	public ItemCostoLogistico actualizarItemCostoLogistico(ItemCostoLogistico icl) {
		if (icl.getNombrePuertoNal()!=null && icl.getNombrePuertoNal().equals(""))
			icl.setNombrePuertoNal(null);
		if (icl.getNombrePuertosNalInternal()!=null && icl.getNombrePuertosNalInternal().equals(""))
			icl.setNombrePuertosNalInternal(null);

		this.itemCostoLogisticoDAO.update(icl);
		if (icl.getRangoCostoLogisticos() != null) {
			for (RangoCostoLogistico rcl : icl.getRangoCostoLogisticos()) {
				if (rcl.getMoneda() != null && (rcl.getMoneda().getId() == null || rcl.getMoneda().getId().equals(""))) {
					rcl.setMoneda(null);
				}
				if (rcl.getId() != null && rcl.getId() != 0) {
					this.rangoCostoLogisticoDAO.update(rcl);
				} else {
					RangoCostoLogistico rcl2 = this.rangoCostoLogisticoDAO.add(rcl);
					rcl.setId(rcl2.getId());
				}
			}
		}
		return icl;
	}

	public ItemCostoLogistico crearItemCostoLogistico(ItemCostoLogistico icl) {
		if (icl.getNombrePuertoNal()!=null && icl.getNombrePuertoNal().equals(""))
			icl.setNombrePuertoNal(null);
		if (icl.getNombrePuertosNalInternal()!=null && icl.getNombrePuertosNalInternal().equals(""))
			icl.setNombrePuertosNalInternal(null);
		
		ItemCostoLogistico icl2 = this.itemCostoLogisticoDAO.add(icl);
		icl.setId(icl2.getId());
		if (icl.getRangoCostoLogisticos() != null) {
			for (RangoCostoLogistico rcl : icl.getRangoCostoLogisticos()) {
				rcl.setItemCostoLogistico(icl);
				if (rcl.getMoneda() != null && (rcl.getMoneda().getId() == null || rcl.getMoneda().getId().equals(""))) {
					rcl.setMoneda(null);
				}
				if (rcl.getId() != null && rcl.getId() != 0) {
					this.rangoCostoLogisticoDAO.update(rcl);
				} else {
					RangoCostoLogistico rcl2 = this.rangoCostoLogisticoDAO.add(rcl);
					rcl.setId(rcl2.getId());
				}
			}
		}
		return icl;
	}

	public List<RangoCostoLogistico> consultarRangossCostosLogisticos(ItemCostoLogistico icl) {
		return (List<RangoCostoLogistico>) this.rangoCostoLogisticoDAO.findByItem(icl);
	}

	public RangoCostoLogistico consultarRangoCostoLogistico(Long id) {
		return this.rangoCostoLogisticoDAO.findByPK(id);
	}

	public RangoCostoLogistico actualizarRangoCostoLogistico(RangoCostoLogistico icl) {
		this.rangoCostoLogisticoDAO.update(icl);
		return icl;
	}

	public RangoCostoLogistico crearRangoCostoLogistico(RangoCostoLogistico icl) {
		return this.rangoCostoLogisticoDAO.add(icl);
	}

	public void eliminarRangoCostoLogistico(RangoCostoLogistico icl) {
		this.rangoCostoLogisticoDAO.delete(icl);
	}

	@Override
	public List<MovimientosInventarioComext> consultarMovimientosInventarioComextsPorSku(String sku) {
		return movimientosInventarioComextDAO.consultarPorSKU(sku);
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<PuntoVenta> consultarPuntoEntrega(PuntoVenta pi, int
	 * first,int pageSize, String sortField, SortOrder sortOrder, boolean count) {
	 * try { return (List<PuntoVenta>) puntoVentaDao.consultar(pi, first,
	 * pageSize,sortField, sortOrder, count); } catch (Exception e) {
	 * LOGGER.error(e + " Error consultando puntos ventas"); return null; }
	 * 
	 * }
	 */
	@Override
	public List<PuntoVenta> consultarPuntoEntregaPorCliente(Long idCliente) {
		try {
			// return (List<PuntoVenta>) puntoVentaDao.consultar();
			return (List<PuntoVenta>) puntoVentaDao.findByCliente(idCliente);
		} catch (Exception e) {
			LOGGER.error(e + " Error consultando puntos ventas");
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PuntoVenta> consultarPuntoEntrega() {
		try {
			// return (List<PuntoVenta>) puntoVentaDao.consultar();
			return (List<PuntoVenta>) puntoVentaDao.findAll();
		} catch (Exception e) {
			LOGGER.error(e + " Error consultando puntos ventas");
			return null;
		}

	}

	@Override
	public PuntoVenta crearPuntoVenta(PuntoVenta pEntidad) {
		try {
			puntoVentaDao.guardarPuntoVenta(pEntidad);
			return pEntidad;
		} catch (Exception e) {
			LOGGER.error(e + " Error creando punto venta");
			return null;
		}
	}

	@Override
	public PuntoVenta actualizarPuntoVenta(PuntoVenta pEntidad) {
		try {
			puntoVentaDao.update(pEntidad);
			return pEntidad;
		} catch (Exception e) {
			LOGGER.error(e + " Error actualizando punto venta");
			return null;
		}
	}

	@Override
	public List<Ciudad> consultarCiudades() {
		return (List<Ciudad>) ciudadDAO.findAll();
	}

	@Override
	public FactsCurrencyConversion getTRMDian(Date fecha) {
		return conversionMonedaDAO.getTRMDian(fecha);
	}

	@Override
	public List<ProductosInventario> consultarProductosInventariosPorEstadoCategoriaSkuNombreAndControlStock(ProductosInventarioFiltroDTO filtroDTO) {
		return productoInventarioDao.consultarPorEstadoCategoriaSKUNombreAndControlStock(filtroDTO);
	}

	@Override
	public void guardarMovimientosInventarioComercioExterior(List<MovimientosInventarioComext> movimientosInventarioComexts) {
		Date fecha = new Date();
		List<MovimientosInventarioComext> ultimosSaldosMovimientosInventarioComExt = movimientosInventarioComextDAO.getUltimosSaldos();
		for (MovimientosInventarioComext movimientosInventarioComext : movimientosInventarioComexts) {
			for (MovimientosInventarioComext ultimoSaldomovimientosInventarioComext : ultimosSaldosMovimientosInventarioComExt) {
				if (movimientosInventarioComext.getProductosInventarioComext().getProductosInventario().getId()
						.equals(ultimoSaldomovimientosInventarioComext.getProductosInventarioComext().getProductosInventario().getId())) {
					movimientosInventarioComext.setSaldo(movimientosInventarioComext.getCantidad().add(ultimoSaldomovimientosInventarioComext.getSaldo()));
					break;
				}
			}
			movimientosInventarioComext.setFecha(fecha);
			movimientosInventarioComext.setProductosInventarioComext(productosInventarioComextDao.findByPK(movimientosInventarioComext.getProductosInventarioComext().getProductosInventario().getId()));
			movimientosInventarioComextDAO.add(movimientosInventarioComext);
		}
	}

	@Override
	public List<ProductosInventarioComext> consultarProductosInventarioComextsParaInventarioComercioFotos(ProductosInventarioComextFiltroVO filtroVO) {
		return productosInventarioComextDao.consultarPorNombreSKUProductoOCategoria(filtroVO);
	}

	@Override
	public Documento consultarDocumentoPorConsecutivo(String consecutivo) {
		LOGGER.debug("Metodo: <<consultarDocumentoPorConsecutivo>>");
		return documentoDAO.consultarDocumentoPorConsecutivo(consecutivo);
	}

	@Override
	public void cargarPuntoEntregaDesdeArchivo(List<String[]> lines) {

		int numLinea = 1;
		List<PuntoVenta> objPuntoVentas = new ArrayList<PuntoVenta>();
		for (String[] lineFile : lines) {
			objPuntoVentas.add(getPuntoVenta(numLinea, lineFile));

			numLinea++;
		}

		for (PuntoVenta objPuntoVenta : objPuntoVentas) {

			puntoVentaDao.guardarPuntoVenta(objPuntoVenta);

		}

	}

	private PuntoVenta getPuntoVenta(int numLinea, String[] lineFile) {
		// Nombre | Dirección | Id Ciudad | Telefono | Código Barras | Código
		// GIP Cliente | Activo(true-false)

		PuntoVenta objPuntoVenta = new PuntoVenta();
		String nombre = (lineFile[0]);
		String direccion = (lineFile[1]);
		Long idCiudad = Long.parseLong(lineFile[2]);
		String telefono = (lineFile[3]);
		Long codigoBarras = Long.parseLong(lineFile[4]);
		Long codigoGip = Long.parseLong(lineFile[5]);
		Boolean estado = Boolean.parseBoolean(lineFile[6]);

		objPuntoVenta.setNombre(nombre);
		objPuntoVenta.setDireccion(direccion);
		Ciudad objCity = new Ciudad();
		objCity.setId(idCiudad);
		objPuntoVenta.setCiudade(objCity);
		objPuntoVenta.setTelefono(telefono);
		objPuntoVenta.setCodigoBarras(codigoBarras);
		Cliente cliente = new Cliente();
		cliente.setId(codigoGip);
		objPuntoVenta.setCliente(cliente);
		objPuntoVenta.setActivo(estado);

		return objPuntoVenta;
	}

}
