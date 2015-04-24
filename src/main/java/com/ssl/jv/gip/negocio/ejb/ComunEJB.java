package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Empresa;
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Parametro;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.Proveedor;
import com.ssl.jv.gip.jpa.pojo.Region;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dao.ClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.EmpresaDAO;
import com.ssl.jv.gip.negocio.dao.EstadoDAOLocal;
import com.ssl.jv.gip.negocio.dao.MonedaDAOLocal;
import com.ssl.jv.gip.negocio.dao.PaisDAO;
import com.ssl.jv.gip.negocio.dao.ParametroDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProveedorDAOLocal;
import com.ssl.jv.gip.negocio.dao.RegionDAO;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAO;
import com.ssl.jv.gip.negocio.dao.UnidadDAOLocal;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Session Bean implementation class ComunEJB
 */
@Stateless
@LocalBean
public class ComunEJB implements ComunEJBLocal {

  private static final Logger LOGGER = Logger.getLogger(ComunEJB.class);

  @EJB
  private PaisDAO paisDao;

  @EJB
  private EmpresaDAO empresaDao;

  @EJB
  private RegionDAO regionDao;

  @EJB
  private UbicacionDAO ubicacionDao;

  @EJB
  private EstadoDAOLocal estadoDAOLocal;

  @EJB
  private TipoDocumentoDAOLocal tipoDocumentoDAOLocal;

  @EJB
  private ProveedorDAOLocal proveedorDAOLocal;

  @EJB
  private UnidadDAOLocal unidadDAOLocal;

  @EJB
  private MonedaDAOLocal monedaDAOLocal;

  @EJB
  private ParametroDAOLocal parametroDAOLocal;

  @EJB
  private DocumentoDAOLocal documentoDAO;

  @EJB
  private ClienteDAOLocal clienteDAO;
  
  @EJB
  private ProductoInventarioDAOLocal productoDAO;

  /**
   * Default constructor.
   */
  public ComunEJB() {
    // TODO Auto-generated constructor stub
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ssl.jv.gip.negocio.ejb.ComunEJBLocal#consultarEmpresas()
   */
  @Override
  public List<Empresa> consultarEmpresas() {

    List<Empresa> listado = new ArrayList<Empresa>();

    try {
      listado = (List<Empresa>) empresaDao.findAll();
    } catch (Exception e) {

    }

    return listado;

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ssl.jv.gip.negocio.ejb.ComunEJBLocal#consultarRegiones()
   */
  @Override
  public List<Region> consultarRegiones(String pais) {

    List<Region> listado = new ArrayList<Region>();

    try {
      listado = (List<Region>) regionDao.findByRegional(pais);
    } catch (Exception e) {

    }

    return listado;

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ssl.jv.gip.negocio.ejb.ComunEJBLocal#consultarPaises()
   */
  @Override
  public List<Pais> consultarPaises() {

    List<Pais> listado = new ArrayList<Pais>();

    try {
      listado = (List<Pais>) paisDao.findByRegional();
    } catch (Exception e) {

    }

    return listado;

  }

  @Override
  public List<Pais> consultarPaisesTodos() {

    List<Pais> listado = new ArrayList<Pais>();

    try {
      listado = (List<Pais>) paisDao.findAll();
    } catch (Exception e) {

    }

    return listado;

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ssl.jv.gip.negocio.ejb.ComunEJBLocal#consultarBodegasAbastecedoras()
   */
  @Override
  public List<Ubicacion> consultarBodegasAbastecedoras() {

    List<Ubicacion> listado = new ArrayList<Ubicacion>();

    try {

      Ubicacion filtro = new Ubicacion();
      filtro.setEsTienda(false);

      listado = ubicacionDao.consultarUbicacionPorFiltro(filtro);

    } catch (Exception e) {

    }

    return listado;

  }

  @Override
  public List<Estado> consultarEstados() {
    return (List<Estado>) estadoDAOLocal.findAll();
  }

  @Override
  public List<TipoDocumento> consultarTiposDocumentos() {
    return (List<TipoDocumento>) tipoDocumentoDAOLocal.findAll();
  }

  @Override
  public List<Proveedor> consultarProveedores() {
    return (List<Proveedor>) proveedorDAOLocal.findAll();
  }

  @Override
  public List<Unidad> consultarUnidades() {
    return (List<Unidad>) unidadDAOLocal.findAll();
  }

  @Override
  public List<Moneda> consultarMonedas() {
    // TODO Auto-generated method stub
    return (List<Moneda>) monedaDAOLocal.findAll();
  }

  @Override
  public Parametro consultarParametroPorNombre(String nombre) {
    return parametroDAOLocal.consultarParametroPorNombre(nombre);
  }

  @Override
  public List<Parametro> consultarParametroPorNombres(String... nombres) {
    return parametroDAOLocal.consultarParametroPorNombres(nombres);
  }

  @Override
  public List<Documento> consultarDocumentos(FiltroDocumentoDTO filtro) {
    LOGGER.trace("Metodo: <<consultarDocumentos (filtro)>>");
    return documentoDAO.consultarDocumentos(filtro);
  }

  @Override
  public Cliente consultarCliente(Long id, String nquery) {
    LOGGER.trace("Metodo: <<consultarCliente>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("id", id);
    return clienteDAO.buscarRegistroPorConsultaNombrada(nquery, parametros);
  }

  @Override
  public ProductosInventario consultarProducto(Long id) {
    LOGGER.trace("Metodo: <<consultarProducto>>");
    return productoDAO.findByPK(id);
  }
}
