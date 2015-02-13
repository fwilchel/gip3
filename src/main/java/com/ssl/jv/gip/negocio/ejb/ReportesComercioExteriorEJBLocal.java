package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.DocTerminosTransporteDTO;
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
   *
   * @return
   */
  List<InstruccionEmbarqueDTO> consultarListadoImprimirInstruccionEmbarque();

  /**
   *
   * @param id
   * @return
   */
  List<DocTerminosTransporteDTO> consultarListadoFacturasPorInstruccionEmabarque(Long id);

  /**
   *
   * @param parametros
   * @return
   */
  public List<Cliente> consultarListadoClientesReporteVentasCE(Map<String, Object> parametros);

  /**
   *
   * @param parametros
   * @return
   */
  public List<ProductosInventario> consultarListadoProductosReporteVentasCE(Map<String, Object> parametros);

  /**
   *
   * @param parametros
   * @return
   */
  public List<DocumentoReporteVentasCEDTO> consultarDocumentosReporteVentasCE(Map<String, Object> parametros);
}
