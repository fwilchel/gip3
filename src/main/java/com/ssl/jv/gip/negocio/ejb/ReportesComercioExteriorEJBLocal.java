package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.InstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoReporteVentasCEDTO;
import java.util.Map;

@Local
public interface ReportesComercioExteriorEJBLocal {

  public List<Documento> consultarFacturasExportacion(FiltroDocumentoDTO filtro);

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
  public InstruccionEmbarqueDTO consultarDetalleInstruccionEmbarque(Long id);

  /**
   * Metodo que consulta la lista de clientes para el reporte de ventas de CE
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param parametros
   * @return
   */
  public List<Cliente> consultarListadoClientesReporteVentasCE(Map<String, Object> parametros);

  /**
   * Metodo que consulta la lista de productos para el reporte de ventas de CE
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param parametros
   * @return
   */
  public List<ProductosInventario> consultarListadoProductosReporteVentasCE(Map<String, Object> parametros);

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
}
