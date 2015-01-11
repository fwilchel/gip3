package com.ssl.jv.gip.negocio.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.CuentaContable;
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
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;
import com.ssl.jv.gip.jpa.pojo.TipoCanal;
import com.ssl.jv.gip.jpa.pojo.TipoLoteoic;
import com.ssl.jv.gip.jpa.pojo.TipoPrecio;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dao.AgenciaCargaDAO;
import com.ssl.jv.gip.negocio.dao.AgenteAduanaDAO;
import com.ssl.jv.gip.negocio.dao.CategoriaInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.CiudadDAOLocal;
import com.ssl.jv.gip.negocio.dao.ClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.CuentaContableDAOLocal;
import com.ssl.jv.gip.negocio.dao.IncotermXMedioTransDAO;
import com.ssl.jv.gip.negocio.dao.LugarIncotermDAO;
import com.ssl.jv.gip.negocio.dao.MedioTransporteDAO;
import com.ssl.jv.gip.negocio.dao.MetodoPagoDAOLocal;
import com.ssl.jv.gip.negocio.dao.MonedaDAOLocal;
import com.ssl.jv.gip.negocio.dao.PaisDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAO;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosInventarioComextDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminoIncotermDAO;
import com.ssl.jv.gip.negocio.dao.TipoCanalDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoLoteOICDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoPrecioDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAO;
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
	private IncotermXMedioTransDAO incotermXMedioTransDAO;

	@EJB
	private TerminoIncotermDAO terminoIncotermDAO;

	@EJB
	private MedioTransporteDAO medioTransporteDAO;

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
	public void cargarProductosPorClienteComExtDesdeArchivo(
			InputStream inputStream) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			String line = null;
			List<String[]> lines = new ArrayList<String[]>();
			boolean error = true;
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					error = false;
					String[] values = line.split("\\|");
					if (values.length < 13 || values.length > 14) {
						throw new RuntimeException("Archivo inválido");
					}

					if (values[0].trim().isEmpty()) {
						error = true;
					}

					try {
						Long.parseLong(values[1]);
						new BigDecimal(values[6]);
						new BigDecimal(values[8]);
						new BigDecimal(values[9]);
						new BigDecimal(values[10]);
						Boolean.parseBoolean(values[12]);
					} catch (NumberFormatException e) {
						error = true;
					}

					lines.add(values);
				}
			}
			reader.close();

			if (error) {
				throw new RuntimeException(
						"Archivo con errores en sus registros");
			}

			int numLinea = 1;
			List<ProductosXClienteComext> productosXClienteComexts = new ArrayList<ProductosXClienteComext>();
			for (String[] lineFile : lines) {
				productosXClienteComexts.add(getProductoXClienteComExt(
						numLinea, lineFile));
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
					productoClienteComercioExteriorDAO
							.add(productosXClienteComext);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
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
		return (List<Pais>)paisDAO.findAll();
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
	
	
}
