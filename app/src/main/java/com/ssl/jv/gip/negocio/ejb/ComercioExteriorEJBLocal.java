package com.ssl.jv.gip.negocio.ejb;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.LiquidacionCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.RequerimientosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.AutorizarDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ComextRequerimientoexportacionDTO;
import com.ssl.jv.gip.negocio.dto.CostoLogisticoDTO;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoCostosLogisticosDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoPorLotesInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoRequerimientoExportacionDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoODDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.negocio.dto.ProductoSolicitudPedidoDTO;

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
	List<DocumentoXLotesoic> consultarDocumentosPorLoteOIC();

	/**
	 * Reiniciar consecutivo lote oic.
	 *
	 * @return the integer
	 */
	Integer reiniciarConsecutivoLoteOIC();

	/**
	 * Consultar cliente por id.
	 *
	 * @param idCliente
	 *          the id cliente
	 * @return the cliente
	 */
	Cliente consultarClientePorId(Long idCliente);

	/**
	 * Consultar documentos costos inconterm
	 *
	 * @return the list
	 */
	List<DocumentoIncontermDTO> consultarDocumentosCostosInconterm();

	/**
	 * Actualizar documento por negociacion.
	 *
	 * @param documento
	 *          the documento
	 */
	void actualizarDocumentoPorNegociacion(DocumentoIncontermDTO documento);

	/**
	 * Consultar lista inconterm por cliente.
	 *
	 * @param idCliente
	 *          the id cliente
	 * @return the list
	 */
	List<TerminoIncoterm> consultarListaIncontermPorCliente(Long idCliente);

	/**
	 * Consultar documentos solicitud pedido.
	 *
	 * @return the list
	 */
	List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido();

	/**
	 * Consultar documentos solicitud pedido.
	 *
	 * @return the list
	 */
	List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido(FiltroConsultaSolicitudDTO filtro);

	/**
	 * Consultar documentos general.
	 *
	 * @param filtro
	 *          the filtro
	 * @return the list
	 */
	List<DocumentoIncontermDTO> consultarDocumentosGeneral(FiltroConsultaSolicitudDTO filtro);

	/**
	 * Metodo que consulta los documentos por uno o varios de los campos del
	 * filtro y adicionalmente paginados
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
	List<DocumentoIncontermDTO> consultarDocumentosAprobarSolicitudPedido();

	/**
	 * Consultar lista solicitudes pedido.
	 *
	 * @return the list
	 */
	List<ProductoPorClienteComExtDTO> consultarListaSolicitudesPedido(Long idDocumento, Long idCliente);

	/**
	 * Consultar lista productos por cliente ce.
	 *
	 * @param idCliente
	 *          the id cliente
	 * @param idsProductos
	 *          the ids productos
	 * @param solicitudCafe
	 *          the solicitud cafe
	 * @return the list
	 */
	List<ProductoPorClienteComExtDTO> consultarListaProductosPorClienteCE(Long idCliente, String idsProductos, Boolean solicitudCafe);

	/**
	 * Guardar solicitud pedido.
	 *
	 * @param documento
	 *          the documento
	 * @param listaSolicitudPedido
	 *          the lista solicitud pedido
	 */
	void guardarSolicitudPedido(DocumentoIncontermDTO documento, List<ProductoPorClienteComExtDTO> listaSolicitudPedido);

	void modificarSP(DocumentoIncontermDTO sp, DocumentoXNegociacion dxn, List<ProductosXDocumento> productosSeleccionados, LogAuditoria auditoria) throws Exception;

	void modificarFP(Documento fp, List<ProductosXDocumento> productosSeleccionados, LogAuditoria auditoria) throws Exception;

	/**
	 * Actualizar estado documento.
	 *
	 * @param documento
	 *          the documento
	 */
	void actualizarEstadoDocumento(DocumentoIncontermDTO documento);

	/**
	 * Consultar ubicaciones por usuario.
	 *
	 * @param idUsuario
	 *          the id usuario
	 * @return the list
	 */
	List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario);

	List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(Map<String, Object> parametros);

	List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(Map<String, Object> parametros);

	List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(List<DocumentoLotesContribucionCafeteriaDTO> documentos);

	List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(String consecutivoFacturaProforma);

	List<Documento> consultarDocumento(Map<String, Object> parametros, Long[] idEstados);

	ListaEmpaqueDTO consultarDocumentoListaEmpaque(String consecutivoDocumento);

	List<ProductoImprimirLEDTO> consultarProductoListaEmpaque(String strConsecutivoDocumento);

	List<Documento> consultarDocumentosSolicitudPedido(String consecutivoDocumento);

	List<Documento> consultarDocumentosFacturaPF(String consecutivoDocumento);

	List<ProductoGenerarFacturaPFDTO> consultarProductoPorDocumentoGenerarFacturaProforma(Long idDocumento, Long idCliente);

	List<ProductoAsignarLoteOICDTO> consultarProductoPorDocumentoAsignarLotesOIC(Long idDocumento, Long idCliente);

	List<ProductoLoteAsignarLoteOICDTO> consultarProductoPorDocumentoLoteAsignarLotesOIC(Long idDocumento, Long idCliente);

	List<ProductoDTO> consultarProductoPorDocumento(ListaEmpaqueDTO listaEmpaqueDTO);

	// Documento generarListaEmpaque(ListaEmpaqueDTO listaEmpaqueDTO);
	Documento consultarDocumentoPorId(Long pId);

	Documento consultarDocumentoComercioExterior(Long id);

	void generarListaEmpaque(ProductoDTO productoDTO);

	Documento crearFacturaProforma(Documento fp, LogAuditoria auditoria, DocumentoXNegociacion dxn, List<ProductosXDocumento> productos, Documento sp);

	Documento crearFacturaExportacion(Documento documento, LogAuditoria auditoria, DocumentoXNegociacion documentoPorNegociacion, List<ProductosXDocumento> productos, Documento original);

	List<DocumentoXLotesoic> guardarLotes(List<DocumentoXLotesoic> lista, Documento documento);

	Documento generarSP(Documento sp, LogAuditoria auditoria, DocumentoXNegociacion dxn, List<ProductoSolicitudPedidoDTO> pxd);

	Map<Long, BigDecimal> consultarUltimosSaldos();

	ProductosXClienteComext consultarPorClienteSku(Long idCliente, String sku);

	List<Documento> consultarDocumentosActivosPorTipoDocumentoYConsecutivoDocumento(Long idTipoDocumento, String consecutivoDocumento);

	List<ProductosInventario> consultarProductosInventariosPorSkus(List<String> skus);

	List<ProductosXDocumento> consultarProductosXDocumentosPorDocumento(Long idDocumento);

	void modificarListaEmpaque(Documento documento, List<ProductosXDocumento> productosXDocumentos);

	List<Documento> consultarFacturasDeExportacion();

	List<Documento> consultarFacturasDeExportacionFiltro(Documento documento);

	void actualizarFacturaDeExportacionFiltro(Documento documento);

	List<AutorizarDocumentoDTO> consultarDocumentosAutorizar(String consecutivoDocumento);

	void cambiarEstadoFacturaProforma(List<AutorizarDocumentoDTO> listado, LogAuditoria auditoria);

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
	List<Documento> consultarSolicitudesPedidoPorAnular(String consecutivoDocumento);

	/**
	 *
	 * @param documento
	 */
	void anularSolicitudPedido(Documento documento);

	List<Documento> consultarFacturasProformasParaGenerarListaEmpaque(String consecutivoDocumento);

	List<Cliente> listadoClientesInstruccionEmbarque(String idUsuario);

	List<DocumentoInstruccionEmbarqueDTO> listadoDocumentosInstruccionEmbarque(Long idCliente);

	List<DocumentoPorLotesInstruccionEmbarqueDTO> consultarDocumentosPorLotes(String strDocs, String strDocsMerca);

	List<AgenteAduana> consultarAgenteAduana();

	List<Pais> findByPaisTodos();

	List<TerminoIncoterm> findTerminoIncotermAll();

	List<Ciudad> findCiudadesAll(String idPais);

	List<ModalidadEmbarque> findModalidadEmbarque();

	TerminosTransporte updateTerminoTransporte(TerminosTransporte terminosTransporte);

	String guardarInstruccionEmbarque(List<DocumentoInstruccionEmbarqueDTO> listadoDocumentos, TerminosTransporte terminosTransporte);

	Documento generarListaEmpaque(Documento documento, DocumentoXNegociacion documentoXNegociacion, List<ProductoDTO> productoDTOs);

	List<CostoLogisticoDTO> generarCostosLogisticos(Long idCliente, List<Long> documentos, TerminoIncotermXMedioTransporte terminoIncoterm, String puerto, String puertos, Long idCurrency, String pais,
			Integer tipoContenedor1, BigDecimal cantidad1, Integer tipoContenedor2, BigDecimal cantidad2, BigDecimal valorTotal);

	List<Documento> consultarListaEmpaquesParaAsignarDatosTL(String consecutivo);

	List<DocumentoXLotesoic> consultarDocumentosXLotesOICParaAsignarDatosTL(String consecutivo);

	void asignarDatosTL(Documento listaEmpaque, List<DocumentoXLotesoic> documentoXLotesoics);

	List<DocumentoIncontermDTO> consultarDocumentosAutorizadosParaModificarFacturaProforma();

	List<ProductoPorClienteComExtDTO> consultarListaProductosClienteFacturaProforma(Long idDocumento, Long idCliente);

	String modificarFacturaProforma(DocumentoIncontermDTO documento, List<ProductoPorClienteComExtDTO> listado, LogAuditoria auditoria);

	void actualizarEstadoDocumento(Documento documento);

	void actualizarEstadoDocumentoPorId(Documento documento);

	List<String> consultarPuertosNacionales();

	List<String> consultarPuertosInternacionales(String idPais);

	List<DocumentoCostosLogisticosDTO> consultarDocumentosCostosLogisticos(Long idCliente);

	List<Documento> consultarFacturasDeExportacionEstado(String consecutivoDocumento);

	List<ProductoODDTO> consultarProductoPorDocumentoOrdenDespacho(Long idDocumento, Long idCliente, Boolean cafe);

	List<Documento> consultarFP(String consecutivoDocumento);

	// int actualizarCostosLogisticos(Long idDocumento, Long
	// idTerminoIncoterm, BigDecimal valorFob, BigDecimal valorFletes, BigDecimal
	// valorSeguros, LiquidacionCostoLogistico lcl);
	int actualizarCostosLogisticos(BigDecimal valorTotal, BigDecimal fob, BigDecimal fletes, BigDecimal seguros, List<DocumentoCostosLogisticosDTO> documentos, LiquidacionCostoLogistico lcl);

	void generarReporteOrdenDespachoPDF(JasperPrint jasperPrint, Long id) throws ClassNotFoundException, IOException, JRException;

	void generarReporteOrdenDespachoExcel(JasperPrint jasperPrint, Long id) throws ClassNotFoundException, IOException, JRException;

	OutputStream generar(JasperPrint jasperPrint, String nombre, String tipo) throws JRException, IOException;

	void actualizarEstadoDocumento(Long id, Long estado);

	List<String> obtenerListaConsecutivosPorTipoLoteIOC(Long idTipoLoteIOC);

	/**
	 *
	 * @param documento
	 */
	void anularFacturaFX(Documento documento);

	List<Documento> consultarDocumentosSP(String consecutivoDocumento);

	List<Documento> consultarDocumentosOD(String consecutivoDocumento);

	void asignarLotesOIC(Documento fp);

	Documento consultarFX(Long id);

	List<DocumentoRequerimientoExportacionDTO> consultarDocumentosSolicitudPedidoRE(Map<String, Object> parametros);

	ComextRequerimientoexportacion crearRequerimientoExportacion(ComextRequerimientoexportacion comextRequerimientoexportacion);

	List<ComextRequerimientoexportacionDTO> crearMarcacionEspecial(String id);

	void crearReqxprod(List<ComextRequerimientoexportacionDTO> productos, long idrequerimiento, boolean selectedMarcacionEspecial);

	ComextRequerimientoexportacion actualizarRequerimientoExportacion(ComextRequerimientoexportacion comextRequerimientoexportacion);

	void crearRequerimientoxdocumento(List<DocumentoRequerimientoExportacionDTO> listaDocumentos, long idrequerimiento);

	List<RequerimientosXDocumento> consultarComextRequerimientoExportacionXDocumento(Long id);

	// void actualizarComextRequerimientoExportacionEstado(Long id);
	void actualizarComextRequerimientoExportacionEstado(DocumentoRequerimientoExportacionDTO requerimiento);

	List<ComextRequerimientoexportacion> consultarComextRequerimientoExportacionEstado(Integer idEstado);

	List<ComextRequerimientoexportacion> consultarComextRequerimientoExportacionEstadoXConsecutivo(Integer idEstado, Long id);

	void actualizarReqxprod(List<ComextRequerimientoexportacionDTO> productos, long idrequerimiento, boolean selectedMarcacionEspecial);

	/**
	 *
	 * @param filtro
	 * @return
	 */
	List<ProductosXClienteComext> consultarProductosActivosXCliente(Map<String, Object> filtro);

	/**
	 *
	 * @param sp
	 * @param cliente
	 * @return
	 */
	List<ProductosXDocumento> consultarProductosSP(Long sp, Long cliente);

	/**
	 *
	 * @param fp
	 * @return
	 */
	List<ProductosXDocumento> consultarProductosDocumentoComercioExterior(Long fp);

    /**
     * 
     * @param documento
   * @param auditoria
     * @throws Exception 
     */
    void anularDocumentoComercioExterior(Documento documento, LogAuditoria auditoria) throws Exception;
}
