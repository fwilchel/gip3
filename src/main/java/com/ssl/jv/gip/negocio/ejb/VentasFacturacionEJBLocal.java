package com.ssl.jv.gip.negocio.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
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

  List<ProductosXCliente> consultarPorClientePuntoVenta(Long idCliente,
          Long idPuntoVenta);

  /**
   * Crear venta directa.
   *
   * @param documento the documento
   * @param auditoria the auditoria
   * @param productos the productos
   * @return the documento
   */
  Documento crearVentaDirecta(Documento documento, LogAuditoria auditoria,
          List<ProductosXDocumento> productos);

  /**
   * Metodo que consulta los documentos tipo venta_directa en estado activo
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @return
   */
  List<Documento> consultarDocumentosOrdenDespacho();

  /**
   * Metodo que consulta los productos por documento
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param id
   * @return
   */
  List<ProductosXDocumento> consultarProductosPorDocumento(Long id);

  /**
   * Metodo que crea la orden de despacho
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @return 
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param documento
   * @param listaProductosXDocumento
   */
  Documento generarOrdenDespacho(Documento documento, List<ProductosXDocumento> listaProductosXDocumento);

  /**
   * Metodo que consulta los documentos que son ordenes de despacho por observacion.
   *
   * @param observacion
   * @return
   */
  List<Documento> consultarOrdenesDespachoPorObservacion(String observacion);
  
  /**
   * 
   * @param factura
   * @param listaProductos
   * @param remisionRelacionada
   * @param auditoria
   * @return 
   */
  Documento generarFactura(Documento factura, List<ProductosXDocumento> listaProductos, Documento remisionRelacionada, LogAuditoria auditoria);
  
  /**
   * Metodo que cambia e estado y e numero de factura de un documento
   * 
   * @param factura 
   */
  void imprimirFactura(Documento factura);

  /**
   * Metodo que consulta los productos por documento
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param id
   * @return
   */
  List<ProductosXDocumento> consultarProductosPorDocumentoOrdenadosPorSKU(Long id);
}
