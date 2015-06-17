package com.ssl.jv.gip.negocio.ejb;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.CuentaContableComprobanteInformeDiarioDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoCintaTestigoMagneticaDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoReporteVentasCEDTO;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.InstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductosInformeTiendaLineaDTO;
import com.ssl.jv.gip.negocio.dto.ReporteProduccionDTO;

@Local
public interface ReportesComercioExteriorEJBLocal {

  public List<Documento> consultarFacturasExportacionReimprimir(Map<String, Object> parametros);

  public List<ProductosXDocumento> consultarProductosPorDocumento(Long id);

  public List<Muestrasxlote> consultarMuestrasPorCantidad(BigDecimal cantidad);

  public List<Documento> consultarFacturasExportacionFechaTipo(FiltroDocumentoDTO filtro);

  public List<Documento> consultarDocumentosPorTipoDocumentoEstadoTipoCafe(FiltroDocumentoDTO filtro);

  /**
   * Metodo que obtiene la lista de instrucciones de embarque
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @return
   */
  List<InstruccionEmbarqueDTO> consultarListadoInstruccionesEmbarque();

  /**
   * Metodo que obtiene ek detalle de una instrucciones de embarque
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param id
   * @return
   */
  InstruccionEmbarqueDTO consultarDetalleInstruccionEmbarque(Long id);

  /**
   * Metodo que consulta la lista de clientes para el reporte de ventas de CE
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param parametros
   * @return
   */
  List<Cliente> consultarListadoClientesReporteVentasCE(Map<String, Object> parametros);

  /**
   * Metodo que consulta la lista de productos para el reporte de ventas de CE
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param parametros
   * @return
   */
  List<ProductosInventario> consultarListadoProductosReporteVentasCE(Map<String, Object> parametros);

  /**
   * Metodo que consulta los documentos del reporte de ventas de CE
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param parametros
   * @return
   */
  public List<DocumentoReporteVentasCEDTO> consultarDocumentosReporteVentasCE(Map<String, Object> parametros);

  public List<Documento> consultarDocumentosPorTipoDocumentoEstadoSolicitudCafeFechas(FiltroDocumentoDTO filtro);

  public List<DocumentoXNegociacion> consultarDocumentoXNegociacionxDocumento(
      Long idDocumento);

  /**
   * Metodo que consulta los documentos del reporte de cinta testigo magnetica
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param parametros
   * @return
   */
  List<DocumentoCintaTestigoMagneticaDTO> consultarDocumentosReporteCintaTestigoMagnetica(Map<String, Object> parametros);

  public List<DocumentoXLotesoic> consultarPorConsecutivoDocumento(
      String consecutivoDocumento);

  /**
   * Metodo que consulta los productos del reporte de produccion
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param parametros
   * @return
   */
  List<ReporteProduccionDTO> consultarProductosReporteProduccion(Map<String, Object> parametros);

  /**
   * Metodo que consulta los productos del reporte de tienda en linea fx
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param idDocumento
   * @return
   */
  List<ProductosInformeTiendaLineaDTO> consultarProductosPorListaEmpaque(Long idDocumento);

  /**
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param parametros
   * @return
   */
  List<CuentaContableComprobanteInformeDiarioDTO> consultarCuentaContableComprobanteInformeDiarioFX(Map<String, Object> parametros);

  /**
   * Metodo que consulta una factura FX para su reimpresion
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param id
   * @return
   */
  Documento consultarFacturaFXReimprimir(Long id);

  /**
   * Metodo que consulta el consecutivo de la orden relacionada a la factura FX
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param id id de la factura FX
   * @return consecutivo de la orden relacionada a la factura fx
   */
  String consultarConsecutivoOrdenFacturaFX(Long id);

List<ProductosXDocumento> consultarProductosPorDocumentoReporte(Long id);

//List<ComextRequerimientoexportacion> consultarComextRequerimientoExportacionConsecutivo(Map<String, Object> parametros);

List<ComextRequerimientoexportacion> consultarComextRequerimientoExportacion();

List<ComextRequerimientoexportacion> consultarComextRequerimientoExportacionConsecutivo(
		Long id);

ComextRequerimientoexportacion consultarComextRequerimientoExportacionDetalle(
		Long id);

}
