package com.ssl.jv.gip.negocio.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;



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
	public void actualizarDocumentoPorNegociacion(DocumentoIncontermDTO documento);
	
	/**
	 * Consultar lista inconterm por cliente.
	 *
	 * @param idCliente the id cliente
	 * @return the list
	 */
	public List<TerminoIncoterm> consultarListaIncontermPorCliente(Long idCliente);
	
	/**
	 * Consultar documentos solicitud pedido.
	 *
	 * @return the list
	 */
	public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido();
	
	/**
	 * Consultar lista solicitudes pedido.
	 *
	 * @return the list
	 */
	public List<ProductoPorClienteComExtDTO> consultarListaSolicitudesPedido(Long idDocumento, Long idCliente);
	
	/**
	 * Consultar lista productos por cliente ce.
	 *
	 * @param idCliente the id cliente
	 * @param idsProductos the ids productos
	 * @param solicitudCafe the solicitud cafe
	 * @return the list
	 */
	public List<ProductoPorClienteComExtDTO> consultarListaProductosPorClienteCE(Long idCliente, String idsProductos, Boolean solicitudCafe);
	
	
	/**
	 * Guardar solicitud pedido.
	 *
	 * @param documento the documento
	 * @param listaSolicitudPedido the lista solicitud pedido
	 */
	public void guardarSolicitudPedido(DocumentoIncontermDTO documento, List<ProductoPorClienteComExtDTO> listaSolicitudPedido);
	
	/**
	 * Consultar ubicaciones por usuario.
	 *
	 * @param idUsuario the id usuario
	 * @return the list
	 */
	public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario);

	public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(Map<String, Object> parametros);
	
	public List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(Map<String, Object> parametros);

	public List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(List<DocumentoLotesContribucionCafeteriaDTO> documentos);

	public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(String consecutivoFacturaProforma);
	
	public List<Documento> consultarDocumento(Map<String, Object> parametros);
	public ListaEmpaqueDTO consultarDocumentoListaEmpaque (String consecutivoDocumento);


	public List<ProductoImprimirLEDTO> consultarProductoListaEmpaque (String strConsecutivoDocumento) ;
	

	public List<Documento> consultarDocumentosSolicitudPedido(String consecutivoDocumento);
	public List<Documento> consultarDocumentosFacturaPF(String consecutivoDocumento);
	
	public List<ProductoGenerarFacturaPFDTO> consultarProductoPorDocumentoGenerarFacturaProforma(Long idDocumento, Long idCliente);
	public List<ProductoAsignarLoteOICDTO> consultarProductoPorDocumentoAsignarLotesOIC(Long idDocumento, Long idCliente);
	public List<ProductoLoteAsignarLoteOICDTO> consultarProductoPorDocumentoLoteAsignarLotesOIC(Long idDocumento, Long idCliente);
	
	List<ProductoDTO> consultarProductoPorDocumento(ListaEmpaqueDTO listaEmpaqueDTO);

	BigInteger generarListaEmpaque(ListaEmpaqueDTO listaEmpaqueDTO);

	Documento consultarDocumentoPorId(Long pId);

	void generarListaEmpaque(ProductoDTO productoDTO);
	public Documento crearFactura(Documento documento, LogAuditoria auditoria, DocumentoXNegociacion documentoPorNegociacion, List<ProductosXDocumento> productos);
	public List<DocumentoXLotesoic> guardarLotes(List<DocumentoXLotesoic> lista, Documento documento);
	public Documento crearSolicitudPedido(Documento documento, LogAuditoria auditoria, DocumentoXNegociacion documentoPorNegociacion, List<ProductosXDocumento> productos, List<MovimientosInventarioComext> mice);
	public Hashtable<Long, BigDecimal> consultarUltimosSaldos();
	
	public ProductosXClienteComext consultarPorClienteSku(Long idCliente, String sku);

}
