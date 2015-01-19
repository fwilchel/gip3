package com.ssl.jv.gip.negocio.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;

@Local
public interface DocumentoDAOLocal extends IGenericDAO<Documento> {

	public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(
			Map<String, Object> parametros);

	public List<DocumentoIncontermDTO> consultarDocumentosCostosInconterm();

	public void actualizarDocumentoPorNegociacion(
			DocumentoIncontermDTO documento);

	public void actualizarEstadoDocumento(DocumentoIncontermDTO documento);

	public void actualizarEstadoDocumentoPorConsecutivo(
			DocumentoIncontermDTO documento);

	List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(
			String consecutivoFacturaProforma);

	BigInteger generarListaEmpaque(ListaEmpaqueDTO listaEmpaqueDTO);

	public List<Documento> consultarDocumentosSolicitudPedido(
			String consecutivoDocumento);

	public List<Documento> consultarDocumentosFacturaPF(
			String consecutivoDocumento);

	public List<Documento> consultarDocumentosPorConsecutivoPedido(
			String consecutivoDocumento);

	public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido();
	
	public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido(FiltroConsultaSolicitudDTO filtro);

	public List<Documento> consultarDocumento(Map<String, Object> parametros);

	public ListaEmpaqueDTO consultarDocumentoListaEmpaque(
			String consecutivoDocumento);

	public List<Documento> consultarOrdenesDeDespacho(
			String consecutivoDocumento);

	public List<ProductoImprimirLEDTO> consultarProductoListaEmpaque(
			String consecutivoDocumento);

	public FacturaDirectaDTO consultarDocumentoFacturaDirecta(
			String strConsecutivoDocumento);

	public List<Documento> consultarDocumentosPorTipoDocumentoYEstado(
			FiltroDocumentoDTO filtro);

	public List<Documento> consultarDocumentosPorEstadoTipoDocumentoYConsecutivoDocumento(
			Long idEstado, Long idTipoDocumento, String consecutivoDocumento);

	public List<Documento> consultarDocumentosPorTipoDocumentoConsecutivoDocumentoYEstados(
			Long idTipoDocumento, String consecutivoDocumento,
			Long... idEstados);

	public List<Documento> consultarDocumentosFacturaExportacion(String consecutivoDocumento);
}
