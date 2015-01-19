package com.ssl.jv.gip.negocio.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.LugarIncoterm;
import com.ssl.jv.gip.jpa.pojo.MedioTransporte;
import com.ssl.jv.gip.jpa.pojo.MetodoPago;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComextPK;
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
import com.ssl.jv.gip.negocio.dao.IncotermXMedioTransDAOLocal;
import com.ssl.jv.gip.negocio.dao.ItemCostoLogisticoDAOLocal;
import com.ssl.jv.gip.negocio.dao.LugarIncotermDAOLocal;
import com.ssl.jv.gip.negocio.dao.MedioTransporteDAOLocal;
import com.ssl.jv.gip.negocio.dao.MetodoPagoDAOLocal;
import com.ssl.jv.gip.negocio.dao.MonedaDAOLocal;
import com.ssl.jv.gip.negocio.dao.PaisDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosInventarioComextDAOLocal;
import com.ssl.jv.gip.negocio.dao.RangoCostoLogisticoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminoIncotermDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoCanalDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoLoteOICDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoPrecioDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dao.UnidadDAOLocal;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;

/**
 * Session Bean implementation class MaestrosEJB
 */
@Stateless
@LocalBean
public class MaestrosEJB implements MaestrosEJBLocal {

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
	public List<TerminoIncotermXMedioTransporte> consultarTerminoIncotermXMedioTransporte() {
		return incotermXMedioTransDAO
				.consultarTerminoIncotermXMedioTransporte();
	}

	@Override
	public TerminoIncotermXMedioTransporte consultarTerminoIncotermXMedioTransporte(
			Long pId) {
		TerminoIncotermXMedioTransporte entidad = new TerminoIncotermXMedioTransporte();

		try {
			entidad = incotermXMedioTransDAO.findByPK(pId);
		} catch (Exception e) {

		}

		return entidad;
	}

	@Override
	public TerminoIncotermXMedioTransporte crearTerminoIncotermXMedioTransporte(
			TerminoIncotermXMedioTransporte pEntidad) {
		try {
			pEntidad = incotermXMedioTransDAO.add(pEntidad);
		} catch (Exception e) {

		}

		return pEntidad;
	}

	@Override
	public TerminoIncotermXMedioTransporte actualizarTerminoIncotermXMedioTransporte(
			TerminoIncotermXMedioTransporte pEntidad) {
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
			listado = (List<MedioTransporte>) medioTransporteDAO
					.findAllActivoBoolean();
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
		return (List<CategoriasInventario>) this.categoriaInventarioDao
				.findAll();
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

	public Object[] consultarProductos(ProductosInventario pi, int first,
			int pageSize, String sortField, SortOrder sortOrder, boolean count) {
		return this.productoInventarioDao.consultar(pi, first, pageSize,
				sortField, sortOrder, count);
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
	public Cliente crearCliente(Cliente pEntidad) {
		try {
			clienteDao.guardarCliente(pEntidad);
			return pEntidad;
		} catch (Exception e) {
			LOGGER.error(e + " Error creando clientes");
			return null;
		}
	}

	@Override
	public Cliente actualizarCliente(Cliente pEntidad) {
		try {
			clienteDao.update(pEntidad);
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

	public ProductosInventarioComext consultarProductoInventarioComext(
			String sku) {
		try {
			ProductosInventarioComext pi = productosInventarioComextDao
					.findBySku(sku);
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
		return monedaDAO.consultarTodas();
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
	public List<ProductosInventario> consultarProductosInventariosPorUsuarioCategoriaSkuNombreAndEstado(
			ProductosInventarioFiltroDTO filtroDTO) {
		return productoInventarioDao
				.consultarPorCategoriaAndSKUAndNombreAndEstado(filtroDTO);
	}

	@Override
	public List<ProductosInventario> consultarProductosInventariosActivos() {
		return productoInventarioDao.consultarActivos();
	}

	@Override
	public void guardarRelacionProductosClienteComercioExterior(
			List<ProductosXClienteComext> productosXClienteComexts) {
		for (ProductosXClienteComext productosXClienteComext : productosXClienteComexts) {
			if (productosXClienteComext.getProductosInventario().isIncluido()) {
				productosXClienteComext
						.setCliente(clienteDao.findByPK(productosXClienteComext
								.getCliente().getId()));
				if (productosXClienteComext.getId() != null) {
					productoClienteComercioExteriorDAO
							.update(productosXClienteComext);
				} else {
					Number max = productoClienteComercioExteriorDAO
							.consultarMaximoValorColumna("id");
					if (max == null) {
						productosXClienteComext.setId(1L);
					} else {
						productosXClienteComext.setId(max.longValue() + 1);
					}
					productosXClienteComext = productoClienteComercioExteriorDAO
							.add(productosXClienteComext);
				}
			} else if (productosXClienteComext.getId() != null) {
				productoClienteComercioExteriorDAO
						.delete(productosXClienteComext);
			}
		}
	}

	@Override
	public void cargarProductosPorClienteComExtDesdeArchivo(List<String[]> lines) {

		int numLinea = 1;
		List<ProductosXClienteComext> productosXClienteComexts = new ArrayList<ProductosXClienteComext>();
		for (String[] lineFile : lines) {
			productosXClienteComexts.add(getProductoXClienteComExt(numLinea,
					lineFile));
			numLinea++;
		}

		for (ProductosXClienteComext productosXClienteComext : productosXClienteComexts) {
			ProductosXClienteComext consultarPorPK = productoClienteComercioExteriorDAO
					.consultarPorPK(productosXClienteComext.getPk());
			if (consultarPorPK != null) {
				productoClienteComercioExteriorDAO
						.update(productosXClienteComext);
			} else {
				Number max = productoClienteComercioExteriorDAO
						.consultarMaximoValorColumna("id");
				if (max == null) {
					productosXClienteComext.setId(1L);
				} else {
					productosXClienteComext.setId(max.longValue() + 1);
				}
				productoClienteComercioExteriorDAO.add(productosXClienteComext);
			}
		}

	}

	private ProductosXClienteComext getProductoXClienteComExt(int numLinea,
			String[] lineFile) {
		ProductosXClienteComext productosXClienteComext = new ProductosXClienteComext();
		ProductosInventario productoInv = null;
		try {
			productoInv = productoInventarioDao.consultarPorSku(lineFile[0]);
		} catch (Exception e) {
			productoInv = null;
		}
		if (productoInv == null) {
			throw new EJBException(
					String.format(
							"Producto referenciando en la línea %d no existe",
							numLinea));
		}
		Long idProducto = productoInv.getId();
		Long idCliente = Long.parseLong(lineFile[1]);
		ProductosXClienteComextPK pk = new ProductosXClienteComextPK(
				idProducto, idCliente);
		productosXClienteComext.setPk(pk);
		ProductosXClienteComext productoClienteComext = productoClienteComercioExteriorDAO
				.consultarPorPK(pk);
		if (productoClienteComext != null) {
			productosXClienteComext = productoClienteComext;
		} else {
			productosXClienteComext.setProductosInventario(productoInv);
			productosXClienteComext.setCliente(new Cliente(idCliente));
		}
		productosXClienteComext.setPrecio(new BigDecimal(lineFile[6]));
		productosXClienteComext.setIdMoneda(lineFile[7]);
		productosXClienteComext.setIva(new BigDecimal(lineFile[8]));
		productosXClienteComext.setDescuentoxproducto(new BigDecimal(
				lineFile[9]));
		productosXClienteComext
				.setOtrosDescuentos(new BigDecimal(lineFile[10]));
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
		pi.getProductosInventarioComext(); // Para hacer fetch
		pi.getProductosInventarioComext().getTipoLoteoic(); // Para hacer fetch
		pi.getUnidadVenta();// Para hacer fetch
		return pi;
	}
	
	public List<CategoriaCostoLogistico> consultarCategoriasCostosLogisticos(){
		return (List<CategoriaCostoLogistico>)this.categoriaCostoLogisticoDAO.findAll();
	}
	public CategoriaCostoLogistico consultarCategoriaCostoLogistico(Long id){
		return this.categoriaCostoLogisticoDAO.findByPK(id);
	}
	
	public CategoriaCostoLogistico actualizarCategoriaCostoLogistico(CategoriaCostoLogistico ccl){
		this.categoriaCostoLogisticoDAO.update(ccl);
		return ccl;
	}
	public CategoriaCostoLogistico crearCategoriaCostoLogistico(CategoriaCostoLogistico ccl){
		return this.categoriaCostoLogisticoDAO.add(ccl);
	}
	
	public List<ItemCostoLogistico> consultarItemsCostosLogisticos(){
		return (List<ItemCostoLogistico>)this.itemCostoLogisticoDAO.findAll();
	}
	public ItemCostoLogistico consultarItemCostoLogistico(Long id){
		return this.itemCostoLogisticoDAO.findByPK(id);
	}
	public ItemCostoLogistico actualizarItemCostoLogistico(ItemCostoLogistico icl){
		this.itemCostoLogisticoDAO.update(icl);
		return icl;
	}
	public ItemCostoLogistico crearItemCostoLogistico(ItemCostoLogistico icl){
		return this.itemCostoLogisticoDAO.add(icl);
	}
	
	public List<RangoCostoLogistico> consultarRangossCostosLogisticos(ItemCostoLogistico icl){
		return (List<RangoCostoLogistico>)this.rangoCostoLogisticoDAO.findByItem(icl);
	}
	public RangoCostoLogistico consultarRangoCostoLogistico(Long id){
		return this.rangoCostoLogisticoDAO.findByPK(id);
	}
	public RangoCostoLogistico actualizarRangoCostoLogistico(RangoCostoLogistico icl){
		this.rangoCostoLogisticoDAO.update(icl);
		return icl;
	}
	public RangoCostoLogistico crearRangoCostoLogistico(RangoCostoLogistico icl){
		return this.rangoCostoLogisticoDAO.add(icl);
	}
	

}
