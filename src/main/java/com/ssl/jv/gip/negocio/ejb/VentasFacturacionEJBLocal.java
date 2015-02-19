package com.ssl.jv.gip.negocio.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;

@Local
public interface VentasFacturacionEJBLocal {

  FacturaDirectaDTO consultarDocumentoFacturaDirecta(String strConsecutivoDocumento);

  //List<Documento> consultarDocumento(Map<String, Object> parametros);
  List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(
          String consecutivoDocumento);

  List<Documento> consultarDocumento(Map<String, Object> parametros,
          Long[] idEstados);

  //List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(String strConsecutivoDocumento);
  /**
   * Metodo que consulta los documentos tipo factura en estado pendientes por recibir y puede filtrar por el consecutivo
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param consecutivo
   * @return
   */
  List<Documento> consultarRemisionesPendientesPorRecibir(String consecutivo);
}
