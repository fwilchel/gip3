package com.ssl.jv.gip.negocio.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.AutorizarDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.CintaMagneticaDTO;
import com.ssl.jv.gip.negocio.dto.ComprobanteInformeDiarioDTO;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoCostosLogisticosDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoReporteVentasCEDTO;
import com.ssl.jv.gip.negocio.dto.ReporteVentaDTO;

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

  public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(
			String consecutivoFacturaProforma);

  public BigInteger generarListaEmpaque(ListaEmpaqueDTO listaEmpaqueDTO);

  public List<Documento> consultarDocumentosSolicitudPedido(
          String consecutivoDocumento);

  public List<Documento> consultarDocumentosFacturaPF(
          String consecutivoDocumento);

  public List<Documento> consultarDocumentosPorConsecutivoPedido(
          String consecutivoDocumento);

  public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido();

  public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido(
          FiltroConsultaSolicitudDTO filtro);
  
  public List<DocumentoIncontermDTO> consultarDocumentosGeneral(
			FiltroConsultaSolicitudDTO filtro);

  public List<DocumentoIncontermDTO> consultarDocumentosAprobarSolicitudPedido();

  public List<Documento> consultarDocumento(Map<String, Object> parametros,
          Long... idEstados);

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

  public List<Documento> consultarDocumentosFacturaExportacion(
          String consecutivoDocumento);

  public List<Documento> consultarDocumentosPorTipoDocumentoYEstados(
          FiltroDocumentoDTO filtro);

  public List<Documento> consultarDocumentosPorEstadoTipoDocumentoYConsecutivoDocumento(
          Long idEstado, Long idTipoDocumento, String consecutivoDocumento);

  public List<Documento> consultarDocumentosPorTipoDocumentoConsecutivoDocumentoYEstados(
          Long idTipoDocumento, String consecutivoDocumento,
          Long... idEstados);

  public List<AutorizarDocumentoDTO> consultarDocumentosAutorizar(
          String consecutivoDocumento);

  public void cambiarEstadoFacturaProforma(List<AutorizarDocumentoDTO> listado);

  public List<CintaMagneticaDTO> consultarCintaTestigoMagnetica(
          Map<String, Object> parametros);

  public List<ComprobanteInformeDiarioDTO> consultarComprobanteInformeDiario(
          Map<String, Object> parametros);

  public List<ReporteVentaDTO> consultarReporteVentasFD(
          Map<String, Object> parametros);

	/**
	 *
	 * @return
	 */
	public List<Documento> consultarTodosLosDocumentos();

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
	public void anularSolicitudPedido(Documento documento);

  public List<DocumentoInstruccionEmbarqueDTO> consultarDocumentosInstruccionEmbarque(
          Long idCliente);

  public List<Documento> consultarDocumentosPorTipoDocumentoYFechas(
          FiltroDocumentoDTO filtro);

  public List<Documento> consultarDocumentosParaGenerarListaEmpaque(
          String consecutivoDocumento);

  public List<Documento> consultarDocumentosParaGenerarFacturaExportacion(
          String consecutivoDocumento);

  public Documento consultarDocumentoPorConsecutivo(String consecutivo);

  public List<Documento> consultarDocumentosDespacharMercancia(
          String consecutivo);

  public List<Documento> consultarDocumentosPorTipoDocumentoEstadoTipoCafe(
          FiltroDocumentoDTO filtro);

  public List<Documento> consultarDocumentosPorTipoDocumentoEstadoSolicitudCafeAndConsecutivo(
          FiltroDocumentoDTO filtro);

  public void actualizarEstadoDocumentoPorConsecutivo(Documento documento);
	
  public void actualizarEstadoDocumentoPorId(Documento documento);

  public List<DocumentoIncontermDTO> consultarDocumentosAutorizadosParaModificarFacturaProforma();

  public String modificarFacturaProforma(DocumentoIncontermDTO documento, List<ProductoPorClienteComExtDTO> listado);

  public List<Documento> consultaFP(String consecutivoDocumento);

  public List<Documento> consultarDocumentosPorTipoDocumentoEstadoSolicitudCafeFechas(
          FiltroDocumentoDTO filtro);

  /**
   *
   * @param parametros
   * @return
   */
  public List<DocumentoReporteVentasCEDTO> consultarDocumentosReporteVentasCE(Map<String, Object> parametros);
  
  public List<Documento> consultarDocumentosPorObservacionDocumento(
          String observacionDocumento);
  
  public List<Object[]> consultarAuditoriaEstadoModificacionFacturaProforma(DocumentoIncontermDTO documento);
  
  public List<DocumentoCostosLogisticosDTO> consultarDocumentosCostosLogisticos(Long idCliente);

  public List<Documento> consultarDocumentosFacturaExportacionEstado(String consecutivoDocumento);

  public List<Documento> consultarDocumentosFacturaExportacionEstado();

}
