package com.ssl.jv.gip.negocio.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

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
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
import com.ssl.jv.gip.jpa.pojo.RangoCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;
import com.ssl.jv.gip.jpa.pojo.TipoCanal;
import com.ssl.jv.gip.jpa.pojo.TipoLoteoic;
import com.ssl.jv.gip.jpa.pojo.TipoPrecio;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioComextFiltroVO;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;
import java.io.IOException;
import java.util.Map;

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
   *
   * @param filtroVO
   * @return lista de productos por cliente que cumplan las condiciones del filtro
   */
  public List<ProductosXClienteComext> consultarProductosClienteComercioExteriorPorFiltro(ProductosXClienteComExtFiltroVO filtroVO);

  /**
   * @return lista de productos por cliente
   */
  public List<ProductosXClienteComext> consultarProductosClienteComercioExterior();

  /**
   * Consultar IncotermxMedioTrans.
   *
   * @return the list
   */
  public List<TerminoIncotermXMedioTransporte> consultarTerminoIncotermXMedioTransporte();

  /**
   * Consultar TerminoIncotermXMedioTransporte.
   *
   * @param pId the id
   * @return the TerminoIncotermXMedioTransporte
   */
  public TerminoIncotermXMedioTransporte consultarTerminoIncotermXMedioTransporte(Long pId);

  /**
   * Crear TerminoIncotermXMedioTransporte.
   *
   * @param pEntidad the entidad
   * @return the TerminoIncotermXMedioTransporte
   */
  public TerminoIncotermXMedioTransporte crearTerminoIncotermXMedioTransporte(TerminoIncotermXMedioTransporte pEntidad);

  /**
   * Modificar TerminoIncotermXMedioTransporte.
   *
   * @param pEntidad the entidad
   * @return the TerminoIncotermXMedioTransporte
   */
  public TerminoIncotermXMedioTransporte actualizarTerminoIncotermXMedioTransporte(TerminoIncotermXMedioTransporte pEntidad);

  /**
   * Consultar TerminoIncoterm.
   *
   * @return the list
   */
  public List<TerminoIncoterm> consultarTerminoIncotermActivo();

  /**
   * Consultar TerminoIncoterm.
   *
   * @return the list
   */
  public List<MedioTransporte> consultarMedioTransporteActivo();

  /**
   * Consulta todos los agentes de aduana
   *
   * @return lista de agentes de aduana
   */
  public List<AgenteAduana> consultarAgentesAduana();

  /**
   * Crea un agente de aduana
   *
   * @param pEntidad nuevo agente de aduana
   * @return
   */
  public AgenteAduana crearAgenteAduana(AgenteAduana pEntidad);

  /**
   * Actualiza un agente de aduana
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

  public Object[] consultarProductos(ProductosInventario pi, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count);

  /**
   * Consultar clientes.
   *
   * @return the list
   */
  public List<Cliente> consultarClientes();

  public List<Cliente> consultarClientesInternacionales();

  /**
   * Crear clientes
   *
   * @param pEntidad the entidad
   * @return the Cliente
   */
  public Cliente crearCliente(Cliente pEntidad, LogAuditoria auditoria);

  /**
   * Actualizar clientes.
   *
   * @param pEntidad the entidad
   * @return the cliente
   */
  public Cliente actualizarCliente(Cliente pEntidad, LogAuditoria auditoria);

  public List<TipoLoteoic> consultarTipoLotesOic();

  public ProductosInventarioComext consultarProductoInventarioComext(String sku);

  public void crearProductoInventarioComext(ProductosInventarioComext pic);

  public void actualizarProductoInventarioComext(ProductosInventarioComext pic);

  public List<Cliente> consultarClientesActivosPorUsuario(String idUsuario);

  public List<Moneda> consultarMonedas();

  public List<CategoriasInventario> consultarCategoriasInventarios();

  public List<ProductosInventario> consultarProductosInventarios();

  public List<ProductosInventario> consultarProductosInventariosActivos();

  public List<ProductosInventario> consultarProductosInventariosPorUsuarioCategoriaSkuNombreAndEstado(ProductosInventarioFiltroDTO filtroDTO);

  public void guardarRelacionProductosClienteComercioExterior(String idUsuario, List<ProductosXClienteComext> productosXClienteComexts);

  public void cargarProductosPorClienteComExtDesdeArchivo(List<String[]> lines);

  /**
   * Consultar paises.
   *
   * @return the list
   */
  public List<Pais> consultarPaises();

  /**
   * Consultar ciudades por pais.
   *
   * @param idPais the id pais
   * @return the list
   */
  public List<Ciudad> consultarCiudadesPorPais(String idPais);

  /**
   * Consultar tipos canal.
   *
   * @return the list
   */
  public List<TipoCanal> consultarTiposCanal();

  /**
   * Consultar metodos pago.
   *
   * @return the list
   */
  public List<MetodoPago> consultarMetodosPago();

  /**
   * Consultar tipos precio.
   *
   * @return the list
   */
  public List<TipoPrecio> consultarTiposPrecio();

  public ProductosInventario consultarPorSku(String sku);

  public List<CategoriaCostoLogistico> consultarCategoriasCostosLogisticos();

  public CategoriaCostoLogistico consultarCategoriaCostoLogistico(Long id);

  public CategoriaCostoLogistico actualizarCategoriaCostoLogistico(CategoriaCostoLogistico ccl);

  public CategoriaCostoLogistico crearCategoriaCostoLogistico(CategoriaCostoLogistico ccl);

  public List<ItemCostoLogistico> consultarItemsCostosLogisticos();

  public ItemCostoLogistico consultarItemCostoLogistico(Long id);

  public ItemCostoLogistico actualizarItemCostoLogistico(ItemCostoLogistico icl);

  public ItemCostoLogistico crearItemCostoLogistico(ItemCostoLogistico icl);

  public List<RangoCostoLogistico> consultarRangossCostosLogisticos(ItemCostoLogistico icl);

  public RangoCostoLogistico consultarRangoCostoLogistico(Long id);

  public RangoCostoLogistico actualizarRangoCostoLogistico(RangoCostoLogistico icl);

  public RangoCostoLogistico crearRangoCostoLogistico(RangoCostoLogistico icl);

  public void eliminarRangoCostoLogistico(RangoCostoLogistico icl);

  public List<MovimientosInventarioComext> consultarMovimientosInventarioComextsPorSku(String sku);

  public FactsCurrencyConversion getTRMDian(Date fecha);

  public List<ProductosInventario> consultarProductosInventariosPorEstadoCategoriaSkuNombreAndControlStock(ProductosInventarioFiltroDTO filtroDTO);

  public void guardarMovimientosInventarioComercioExterior(List<MovimientosInventarioComext> movimientosInventarioComexts);

  public List<PuntoVenta> consultarPuntoEntrega();

  public List<PuntoVenta> consultarPuntoEntregaPorCliente(Long idCliente);

  public PuntoVenta crearPuntoVenta(PuntoVenta pEntidad);

  public PuntoVenta actualizarPuntoVenta(PuntoVenta pEntidad);

  public List<Ciudad> consultarCiudades();

  public List<ProductosInventarioComext> consultarProductosInventarioComextsParaInventarioComercioFotos(ProductosInventarioComextFiltroVO filtroVO);

  /**
   * Metodo que consulta un documento por consecutivo
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param consecutivo
   * @return
   */
  Documento consultarDocumentoPorConsecutivo(String consecutivo);

  public void cargarPuntoEntregaDesdeArchivo(List<String[]> lines);

  /**
   * Metodo que consulta la lista de los productos por cliente
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param parametros
   * @return
   */
  List<ProductosXCliente> consultarProductosXCliente(Map<String, Object> parametros);

  /**
   * Metodo que crea productos por cliente a partir de un archivo
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param archivo
   * @throws IOException
   */
  void crearProductosXClientesDesdeArchivo(byte[] archivo) throws IOException;

  /**
   * Metodo que crea producto por cliente
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @param pxc
   * @param auditoria
   */
  void crearProductosXClientes(ProductosXCliente pxc, LogAuditoria auditoria);

  /**
   * Metodo que modifica producto por cliente
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @param pxcOld
   * @param pxcNew
   * @email dpoveda@gmail.com
   * @param auditoria
   */
  void modificarProductosXClientes(ProductosXCliente pxcOld, ProductosXCliente pxcNew, LogAuditoria auditoria);
}
