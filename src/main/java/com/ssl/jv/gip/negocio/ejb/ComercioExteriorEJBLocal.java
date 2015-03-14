package com.ssl.jv.gip.negocio.ejb;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.AutorizarDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.CostoLogisticoDTO;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoCostosLogisticosDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoPorLotesInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoODDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import org.primefaces.model.SortOrder;

/**
 * The Interface ComercioExteriorLocal.
 */
@Local
public interface ComercioExteriorEJBLocal {

  /**
   * Consultar documentos por lote oic.
   *
   * @return the list
   */
  public List<DocumentoXLotesoic> consultarDocumentosPorLoteOIC();

  /**
   * Reiniciar consecutivo lote oic.
   *
   * @return the integer
   */
  public Integer reiniciarConsecutivoLoteOIC();

  /**
   * Consultar cliente por id.
   *
   * @param idCliente the id cliente
   * @return the cliente
   */
  public Cliente consultarClientePorId(Long idCliente);

  /**
   * Consultar documentos costos inconterm
   *
   * @return the list
   */
  public List<DocumentoIncontermDTO> consultarDocumentosCostosInconterm();

  /**
   * Actualizar documento por negociacion.
   *
   * @param documento the documento
   */
  public void actualizarDocumentoPorNegociacion(
          DocumentoIncontermDTO documento);

  /**
   * Consultar lista inconterm por cliente.
   *
   * @param idCliente the id cliente
   * @return the list
   */
  public List<TerminoIncoterm> consultarListaIncontermPorCliente(
          Long idCliente);

  /**
   * Consultar documentos solicitud pedido.
   *
   * @return the list
   */
  public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido();

  /**
   * Consultar documentos solicitud pedido.
   *
   * @return the list
   */
  public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido(
          FiltroConsultaSolicitudDTO filtro);

  /**
   * Consultar documentos general.
   *
   * @param filtro the filtro
   * @return the list
   */
  public List<DocumentoIncontermDTO> consultarDocumentosGeneral(
          FiltroConsultaSolicitudDTO filtro);

  /**
   * Metodo que consulta los documentos por uno o varios de los campos del filtro y adicionalmente paginados
   * 
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param filtro
   * @param first
   * @param pageSize
   * @param sortField
   * @param sortOrder
   * @param count
   * @return 
   */
  Object[] consultarDocumentosGeneral(FiltroConsultaSolicitudDTO filtro, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count);

  /**
   * Consultar documentos aprobar solicitud pedido.
   *
   * @return the list
   */
  public List<DocumentoIncontermDTO> consultarDocumentosAprobarSolicitudPedido();

  /**
   * Consultar lista solicitudes pedido.
   *
   * @return the list
   */
  public List<ProductoPorClienteComExtDTO> consultarListaSolicitudesPedido(
          Long idDocumento, Long idCliente);

  /**
   * Consultar lista productos por cliente ce.
   *
   * @param idCliente the id cliente
   * @param idsProductos the ids productos
   * @param solicitudCafe the solicitud cafe
   * @return the list
   */
  public List<ProductoPorClienteComExtDTO> consultarListaProductosPorClienteCE(
          Long idCliente, String idsProductos, Boolean solicitudCafe);

  /**
   * Guardar solicitud pedido.
   *
   * @param documento the documento
   * @param listaSolicitudPedido the lista solicitud pedido
   */
  public void guardarSolicitudPedido(DocumentoIncontermDTO documento,
          List<ProductoPorClienteComExtDTO> listaSolicitudPedido);

  /**
   * Actualizar estado documento.
   *
   * @param documento the documento
   */
  public void actualizarEstadoDocumento(DocumentoIncontermDTO documento);

  /**
   * Consultar ubicaciones por usuario.
   *
   * @param idUsuario the id usuario
   * @return the list
   */
  public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario);

  public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(
          Map<String, Object> parametros);

  public List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(
          Map<String, Object> parametros);

  public List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(
          List<DocumentoLotesContribucionCafeteriaDTO> documentos);

  public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(
          String consecutivoFacturaProforma);

  public List<Documento> consultarDocumento(Map<String, Object> parametros,
          Long[] idEstados);

  public ListaEmpaqueDTO consultarDocumentoListaEmpaque(
          String consecutivoDocumento);

  public List<ProductoImprimirLEDTO> consultarProductoListaEmpaque(
          String strConsecutivoDocumento);

  public List<Documento> consultarDocumentosSolicitudPedido(
          String consecutivoDocumento);

  public List<Documento> consultarDocumentosFacturaPF(
          String consecutivoDocumento);

  public List<ProductoGenerarFacturaPFDTO> consultarProductoPorDocumentoGenerarFacturaProforma(
          Long idDocumento, Long idCliente);

  public List<ProductoAsignarLoteOICDTO> consultarProductoPorDocumentoAsignarLotesOIC(
          Long idDocumento, Long idCliente);

  public List<ProductoLoteAsignarLoteOICDTO> consultarProductoPorDocumentoLoteAsignarLotesOIC(
          Long idDocumento, Long idCliente);

  List<ProductoDTO> consultarProductoPorDocumento(
          ListaEmpaqueDTO listaEmpaqueDTO);

  BigInteger generarListaEmpaque(ListaEmpaqueDTO listaEmpaqueDTO);

  Documento consultarDocumentoPorId(Long pId);

  void generarListaEmpaque(ProductoDTO productoDTO);

  public Documento crearFactura(Documento documento, LogAuditoria auditoria,
          DocumentoXNegociacion documentoPorNegociacion,
          List<ProductosXDocumento> productos, Documento original);

  public Documento crearFacturaExportacion(Documento documento, LogAuditoria auditoria,
          DocumentoXNegociacion documentoPorNegociacion,
          List<ProductosXDocumento> productos, Documento original);

  public List<DocumentoXLotesoic> guardarLotes(
          List<DocumentoXLotesoic> lista, Documento documento);

  public Documento crearSolicitudPedido(Documento documento,
          LogAuditoria auditoria,
          DocumentoXNegociacion documentoPorNegociacion,
          List<ProductosXDocumento> productos,
          List<MovimientosInventarioComext> mice);

  public Hashtable<Long, BigDecimal> consultarUltimosSaldos();

  public ProductosXClienteComext consultarPorClienteSku(Long idCliente,
          String sku);

  public List<Documento> consultarDocumentosActivosPorTipoDocumentoYConsecutivoDocumento(
          Long idTipoDocumento, String consecutivoDocumento);

  public List<ProductosInventario> consultarProductosInventariosPorSkus(
          List<String> skus);

  public List<ProductosXDocumento> consultarProductosXDocumentosPorDocumento(
          Long idDocumento);

  public void modificarListaEmpaque(Documento documento,
          List<ProductosXDocumento> productosXDocumentos);

  public List<Documento> consultarFacturasDeExportacion();

  public List<Documento> consultarFacturasDeExportacionFiltro(
          Documento documento);

  public void actualizarFacturaDeExportacionFiltro(Documento documento);

  public List<AutorizarDocumentoDTO> consultarDocumentosAutorizar(
          String consecutivoDocumento);

  public void cambiarEstadoFacturaProforma(List<AutorizarDocumentoDTO> listado);

  /**
   *
   * @return
   */
  List<Documento> consultarTodosLosDocumentos();

  /**
   *
   * @param consecutivoDocumento
   * @return
   */
  List<Documento> consultarSolicitudesPedidoPorAnular(
          String consecutivoDocumento);

  /**
   *
   * @param documento
   */
  void anularSolicitudPedido(Documento documento);

  public List<Documento> consultarFacturasProformasParaGenerarListaEmpaque(
          String consecutivoDocumento);

  public List<Cliente> listadoClientesInstruccionEmbarque(String idUsuario);

  public List<DocumentoInstruccionEmbarqueDTO> listadoDocumentosInstruccionEmbarque(
          Long idCliente);

  public List<DocumentoPorLotesInstruccionEmbarqueDTO> consultarDocumentosPorLotes(
          String strDocs, String strDocsMerca);

  public List<AgenteAduana> consultarAgenteAduana();

  public List<Pais> findByPaisTodos();

  public List<TerminoIncoterm> findTerminoIncotermAll();

  public List<Ciudad> findCiudadesAll(String idPais);

  public List<ModalidadEmbarque> findModalidadEmbarque();

  public TerminosTransporte updateTerminoTransporte(
          TerminosTransporte terminosTransporte);

  public String guardarInstruccionEmbarque(
          List<DocumentoInstruccionEmbarqueDTO> listadoDocumentos,
          TerminosTransporte terminosTransporte);

  public void generarListaEmpaque(Documento documento,
          DocumentoXNegociacion documentoXNegociacion,
          List<ProductoDTO> productoDTOs);

  public List<CostoLogisticoDTO> generarCostosLogisticos(Long idCliente, List<Long> documentos, TerminoIncotermXMedioTransporte terminoIncoterm, String puerto, String puertos, Long idCurrency, String pais);

  public List<Documento> consultarListaEmpaquesParaAsignarDatosTL(
          String consecutivo);

  public List<DocumentoXLotesoic> consultarDocumentosXLotesOICParaAsignarDatosTL(
          String consecutivo);

  public void asignarDatosTL(Documento listaEmpaque,
          List<DocumentoXLotesoic> documentoXLotesoics);

  public List<DocumentoIncontermDTO> consultarDocumentosAutorizadosParaModificarFacturaProforma();

  public List<ProductoPorClienteComExtDTO> consultarListaProductosClienteFacturaProforma(
          Long idDocumento, Long idCliente);

  public String modificarFacturaProforma(DocumentoIncontermDTO documento, List<ProductoPorClienteComExtDTO> listado);

  void actualizarEstadoDocumento(Documento documento);

  public void actualizarEstadoDocumentoPorId(Documento documento);

  public List<String> consultarPuertosNacionales();

  public List<String> consultarPuertosInternacionales(String idPais);

  public List<DocumentoCostosLogisticosDTO> consultarDocumentosCostosLogisticos(Long idCliente);

  public List<Documento> consultarFacturasDeExportacionEstado(String consecutivoDocumento);

  public List<ProductoODDTO> consultarProductoPorDocumentoOrdenDespacho(
          Long idDocumento, Long idCliente, Boolean cafe);

  public List<Documento> consultarFP(String consecutivoDocumento, Long estado1,
          Long estado2);

  public int actualizarCostosLogisticos(Long idDocumento, Long idTerminoIncoterm, BigDecimal valorFob, BigDecimal valorFletes, BigDecimal valorSeguros);

  public void generarReporteOrdenDespachoPDF(JasperPrint jasperPrint, Long id)
          throws ClassNotFoundException, IOException, JRException;

  public void generarReporteOrdenDespachoExcel(JasperPrint jasperPrint, Long id)
          throws ClassNotFoundException, IOException, JRException;

  public OutputStream generar(JasperPrint jasperPrint, String nombre, String tipo) throws JRException, IOException;

}
