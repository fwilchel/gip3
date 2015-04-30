package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.BodegasLogica;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.TipoMovimiento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.CintaMagneticaDTO;
import com.ssl.jv.gip.negocio.dto.ComprobanteInformeDiarioDTO;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoReporteTxtFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ReporteVentaDTO;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.LogAuditoriaDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.web.mb.util.ConstantesBodegas;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesInventario;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesUbicacion;

import static com.ssl.jv.gip.web.util.SecurityFilter.LOGGER;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import mc_style.functions.soap.sap.document.sap_com.ZVI_MM_EXT_SAP_PEDIDOProxy;

/**
 * Session Bean implementation class VentasFacturacionEJB
 */
@Stateless
@LocalBean
public class VentasFacturacionEJB implements VentasFacturacionEJBLocal {

  /**
   * Default constructor.
   */
  public VentasFacturacionEJB() {
    // TODO Auto-generated constructor stub
  }

  @EJB
  private DocumentoDAOLocal documentoDAO;

  @EJB
  private ProductosXDocumentoDAOLocal productoDocumentoDAO;

  @EJB
  private ProductoClienteDAOLocal productoClienteDAO;

  @EJB
  private LogAuditoriaDAOLocal logAuditoriaDAO;

  @EJB
  private ProductosXDocumentoDAOLocal productoXDocumentoDAO;

  @EJB
  private UbicacionDAOLocal ubicacionDAOLocal;

  @EJB
  private TipoDocumentoDAOLocal tipoDocumentoDAOLocal;

  @EJB
  private MaestrosEJBLocal maestrosEJB;

  @EJB
  private MovimientoInventarioDAOLocal movimientoInventarioDAO;
  /**
   * Proxy para conexion con SAP
   */
  private ZVI_MM_EXT_SAP_PEDIDOProxy proxy;

  @Override
  public FacturaDirectaDTO consultarDocumentoFacturaDirecta(String strConsecutivoDocumento) {
    return documentoDAO.consultarDocumentoFacturaDirecta(strConsecutivoDocumento);
  }

  @Override
  public List<Documento> consultarDocumento(Map<String, Object> parametros, Long[] idEstados) {
    // TODO Auto-generated method stub
    return documentoDAO.consultarDocumento(parametros, idEstados);
  }

  @Override
  public List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(String strConsecutivoDocumento) {
    // TODO Auto-generated method stub
    return productoDocumentoDAO.consultarProductoFacturaDirecta(strConsecutivoDocumento);
  }

  public List<CintaMagneticaDTO> consultarCintaTestigoMagnetica(Map<String, Object> parametros) {
    // TODO Auto-generated method stub
    return documentoDAO.consultarCintaTestigoMagnetica(parametros);
  }

  public List<ComprobanteInformeDiarioDTO> consultarComprobanteInformeDiario(Map<String, Object> parametros) {
    // TODO Auto-generated method stub
    return documentoDAO.consultarComprobanteInformeDiario(parametros);
  }

  public List<ReporteVentaDTO> consultarReporteVentasFD(Map<String, Object> parametros) {
    // TODO Auto-generated method stub
    return documentoDAO.consultarReporteVentasFD(parametros);
  }

  public List<ProductoReporteTxtFacturaDirectaDTO> consultarReporteTxtVentasFD(Map<String, Object> parametros) {
    // TODO Auto-generated method stub
    return productoDocumentoDAO.consultarReporteTxtVentasFD(parametros);
  }

  @Override
  public List<Documento> consultarRemisionesPendientesPorRecibir(String consecutivo) {
    LOGGER.trace("Metodo: <<consultarRemisionesPendientesPorRecibir>> parametros / consecutivo ->> {" + consecutivo + "}");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("idEstado", (long) ConstantesDocumento.PENDIENTE_POR_RECIBIR);
    parametros.put("idTipoDocumento", (long) ConstantesTipoDocumento.REMISION);
    parametros.put("consecutivoDocumento", consecutivo);
    return documentoDAO.consultarDocumentosPorEstadoPorTipoPorConsecutivo(parametros);
  }

  @Override
  public List<ProductosXCliente> consultarPorClientePuntoVenta(Long idCliente,
      Long idPuntoVenta) {
    return productoClienteDAO.consultarPorClientePuntoVenta(idCliente, idPuntoVenta);
  }

  /* (non-Javadoc)
   * @see com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal#crearVentaDirecta(com.ssl.jv.gip.jpa.pojo.Documento, com.ssl.jv.gip.jpa.pojo.LogAuditoria, java.util.List)
   */
  @Override
  public Documento crearVentaDirecta(Documento ventaDirecta, LogAuditoria auditoria,
      List<ProductosXDocumento> productos) {
    //Consultar consecutivo
    StringBuilder strConsecutivo = new StringBuilder();
    TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK((long) ConstantesTipoDocumento.VENTA_DIRECTA);

    if (ventaDirecta.getUbicacionDestino().getId().equals(com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo())) {
      Ubicacion ubicacionOrigen = ubicacionDAOLocal.findByPK(ventaDirecta.getUbicacionOrigen().getId());
      strConsecutivo.append(tipoDocumento.getAbreviatura() + ubicacionOrigen.getEmpresa().getId());
    } else {
      Ubicacion ubicacionDestino = ubicacionDAOLocal.findByPK(ventaDirecta.getUbicacionDestino().getId());
      strConsecutivo.append(tipoDocumento.getAbreviatura() + ubicacionDestino.getEmpresa().getId());
    }

    ventaDirecta.setConsecutivoDocumento(strConsecutivo.toString() + "-"
        + this.documentoDAO.consultarProximoValorSecuencia(strConsecutivo.toString() + "_seq"));
    ventaDirecta = (Documento) this.documentoDAO.add(ventaDirecta);
//		auditoria.setIdRegTabla(ventaDirecta.getId());
//		auditoria.setValorNuevo(ventaDirecta.getConsecutivoDocumento());
//		this.logAuditoriaDAO.add(auditoria);
    for (ProductosXDocumento pxd : productos) {
      pxd.getId().setIdDocumento(ventaDirecta.getId());
      this.productoXDocumentoDAO.add(pxd);
    }
    return ventaDirecta;
  }

  @Override
  public List<Documento> consultarDocumentosOrdenDespacho() {
    LOGGER.trace("Metodo: <<consultarDocumentosOrdenDespacho>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("idEstado", (long) ConstantesDocumento.ACTIVO);
    parametros.put("idTipoDocumento", (long) ConstantesTipoDocumento.VENTA_DIRECTA);
    return documentoDAO.consultarDocumentosPorEstadoPorTipoPorConsecutivo(parametros);
  }

  @Override
  public List<ProductosXDocumento> consultarProductosPorDocumento(Long id) {
    LOGGER.trace("Metodo: <<consultarProductosPorDocumento>>");
    return productoDocumentoDAO.consultarPorDocumento(id);
  }

  @Override
  public Documento generarOrdenDespacho(Documento ventaDirecta, List<ProductosXDocumento> listaProductosXDocumento) {
    LOGGER.trace("Metodo: <<generarOrdenDespacho>>");
    LOGGER.debug("Crea el objeto ordenDespacho");
    Documento ordenDespacho = new Documento();
    ordenDespacho.setFechaEsperadaEntrega(ventaDirecta.getFechaEsperadaEntrega());
    ordenDespacho.setFechaEntrega(ventaDirecta.getFechaEntrega());
    ordenDespacho.setSitioEntrega(ventaDirecta.getSitioEntrega());
    ordenDespacho.setDocumentoCliente(ventaDirecta.getDocumentoCliente());
    ordenDespacho.setFechaGeneracion(new Date());
    ordenDespacho.setUbicacionOrigen(ventaDirecta.getUbicacionOrigen());
    ordenDespacho.setUbicacionDestino(ventaDirecta.getUbicacionDestino());
    TipoDocumento tipoDocumento = new TipoDocumento((long) ConstantesTipoDocumento.ORDEN_DESPACHO);
    Estado estado = new Estado((long) ConstantesDocumento.ACTIVO);
    Estadosxdocumento exd = new Estadosxdocumento();
    exd.setTipoDocumento(tipoDocumento);
    exd.setEstado(estado);
    ordenDespacho.setEstadosxdocumento(exd);
    ordenDespacho.setCliente(ventaDirecta.getCliente());
    ordenDespacho.setPuntoVenta(ventaDirecta.getPuntoVenta());
    ordenDespacho.setConsecutivoDocumento(ventaDirecta.getConsecutivoDocumento());
    ordenDespacho.setObservacionDocumento(ventaDirecta.getConsecutivoDocumento());

//    //TODO: se debe obtener la secuencia para orden despacho [select nextval('od1_seq') AS SEQ]
//    Long secuenciaOrdenDespacho = 78965L;
//    StringBuilder logRespuesta = new StringBuilder();
//    StringBuilder logStatus = new StringBuilder();
//    //el cargue por defecto es exitoso, pero si al guno de los productos no se creo en SAP, pasa a falso
//    boolean cargueExitoso = true;
//    // Objetos que se deben enviar a SAP
//    ZcaStPedidosCompra[] compra = new ZcaStPedidosCompra[listaProductosXDocumento.size()];
//    ZcaStPedidosCompraRta[] rta = new ZcaStPedidosCompraRta[listaProductosXDocumento.size()];
//    int index = 0;
//    for (ProductosXDocumento pxd : listaProductosXDocumento) {
//      compra[index] = new ZcaStPedidosCompra();
//      compra[index].setAlmacen("A1");
//      compra[index].setCantidad(pxd.getCantidad1());
//      compra[index].setEvento("CREAR");
//      compra[index].setOrigen("GIP");
//      compra[index].setProceso("S");
//      compra[index].setPrecio(new BigDecimal(0));
//      compra[index].setFechaDoc(Utilidad.formatearFecha(new Date(), "yyyy.MM.dd"));
//      compra[index].setFechaEntrega(Utilidad.formatearFecha(pxd.getFechaEntrega(), "yyyy.MM.dd"));
//      compra[index].setHoraEntrega(Utilidad.formatearFecha(new Date(), "HH:mm:ss"));
//      compra[index].setPedidoExt("G" + secuenciaOrdenDespacho);
//      compra[index].setInterlocutor(ventaDirecta.getPuntoVenta().getCodDespachoSap());
//      compra[index].setMaterial(pxd.getProductosInventario().getSku());
//      index++;
//    }
//    rta[0] = new ZcaStPedidosCompraRta();
//    rta[0].setOrint("");
//    rta[0].setBukrs("");
//    rta[0].setCantidad(null);
//    rta[0].setEbeln("");
//    rta[0].setEvento("");
//    rta[0].setFechaDoc("");
//    rta[0].setFechaEntrega("");
//    rta[0].setFechaProceso("");
//    rta[0].setHoraEntrega("");
//    rta[0].setHoraProceso("");
//    rta[0].setInterlocutor("");
//    rta[0].setMaterial("");
//    rta[0].setPedidoExt("");
//    rta[0].setRespuesta("");
//    rta[0].setStatu("");
//    rta[0].setType("");
//    TableOfZcaStPedidosCompraHolder compraHolder = new TableOfZcaStPedidosCompraHolder(compra);
//    TableOfZcaStPedidosCompraRtaHolder rtaHolder = new TableOfZcaStPedidosCompraRtaHolder(rta);
//    try {
//      LOGGER.debug("Envia la informacion para crear ventaDirecta en SAP");
//      proxy.zmmInterfazIcgCreaPedido(compraHolder, rtaHolder);
//      if (rtaHolder.value != null && rtaHolder.value.length > 0) {
//        for (ZcaStPedidosCompraRta value : rtaHolder.value) {
//          LOGGER.debug("Respuesta: " + value.getRespuesta());
//          LOGGER.debug("Estado: " + value.getStatu());
//          switch (value.getStatu()) {
//            case "ER":
//              cargueExitoso = false;
//              logRespuesta.append("Log: Error cargue Orden Despacho a SAP: Respuesta: ");
//              logRespuesta.append(value.getRespuesta());
//              logRespuesta.append(" || ");
//              logStatus.append("Estado: ");
//              logStatus.append(value.getStatu());
//              logStatus.append(" || ");
//              LOGGER.debug(logRespuesta.toString());
//              LOGGER.debug(logStatus.toString());
//              break;
//            case "OK":
//              logRespuesta.append("Respuesta: ");
//              logRespuesta.append(value.getRespuesta());
//              logRespuesta.append(" || ");
//              logStatus.append("Estado: ");
//              logStatus.append(value.getStatu());
//              logStatus.append(" || ");
//              LOGGER.debug(logRespuesta.toString());
//              LOGGER.debug(logStatus.toString());
//              ordenDespacho.setObservacion2(logRespuesta.toString());
//              break;
//          }
//        }
//        if (cargueExitoso) {
    LOGGER.debug("Crear la orden de despacho");
    ordenDespacho = documentoDAO.add(ordenDespacho);
    LOGGER.debug("Orden de despacho creada exitosamente con e id: " + ordenDespacho.getId());
    LOGGER.debug("Crea los productos relacionados a la orden de despacho");
    for (ProductosXDocumento pxd : listaProductosXDocumento) {
      LOGGER.debug("Crear producto para la orden de despacho");
      ProductosXDocumentoPK productosXDocumentoPK = new ProductosXDocumentoPK();
      productosXDocumentoPK.setIdDocumento(ordenDespacho.getId());
      productosXDocumentoPK.setIdProducto(pxd.getProductosInventario().getId());
      pxd = productoXDocumentoDAO.add(pxd);
      LOGGER.debug("Producto creado con idProducto: " + pxd.getId().getIdProducto() + " y idDocumento: " + pxd.getId().getIdDocumento());
    }
    LOGGER.debug("Productos creados satisfactoriamente");
    LOGGER.debug("Actualizar la venta directa");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("estado", (long) ConstantesDocumento.DESPACHADO);
    parametros.put("observacionDocumento", ordenDespacho.getId().toString());
    parametros.put("id", (long) ventaDirecta.getId());
    documentoDAO.ejecutarConsultaNativa(Documento.ACTUALIZAR_ESTADO_Y_OBSERVACION, parametros);
    LOGGER.debug("Venta directa actualizada");
//        } else {
//          // restabecer la secuencia.
//          //mensage de error
//          //forward = "errorGenerarOrdenDespacho";
//        }
//      }
//    } catch (Exception ex) {
//      // restabecer la secuencia.
//      //mensage de error
//      //forward = "errorGenerarOrdenDespacho";
//    }
    return ordenDespacho;
  }

  @Override
  public List<Documento> consultarOrdenesDespachoPorObservacion(String observacion) {
    LOGGER.trace("Metodo: <<consultarOrdenesDespachoPorObservacion>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("observacionDocumento", observacion);
    parametros.put("idTipoDocumento", (long) ConstantesTipoDocumento.ORDEN_DESPACHO);
    return documentoDAO.buscarPorConsultaNombrada(Documento.FIND_BY_TIPO_DOCUMENTO_AND_OBSERVACION_DOCUMENTO, parametros);
  }

  @Override
  public Documento generarFactura(Documento ventaDirecta, List<ProductosXDocumento> listaProductos, Documento remisionRelacionada, LogAuditoria auditoria) {
    LOGGER.trace("Metodo: <<generarFactura>>");
    LOGGER.debug("Crear factura");
    Documento factura = new Documento();
    factura.setFechaEsperadaEntrega(ventaDirecta.getFechaEsperadaEntrega());
    factura.setUbicacionOrigen(ventaDirecta.getUbicacionOrigen());
    factura.setUbicacionDestino(ventaDirecta.getUbicacionDestino());
    factura.setEstadosxdocumento(ventaDirecta.getEstadosxdocumento());
    factura.setFechaGeneracion(ventaDirecta.getFechaGeneracion());
    factura.setFechaEntrega(ventaDirecta.getFechaEntrega());
    factura.setProveedore(ventaDirecta.getProveedore());
    factura.setObservacionDocumento(ventaDirecta.getObservacionDocumento());
    factura.setDocumentoCliente(ventaDirecta.getDocumentoCliente());
    factura.setSitioEntrega(ventaDirecta.getSitioEntrega());
    if (ventaDirecta.getCliente() != null) {
      factura.setCliente(ventaDirecta.getCliente());
      factura.setSubtotal(ventaDirecta.getSubtotal());
      factura.setDescuento(ventaDirecta.getDescuento());
      factura.setValorIva16(ventaDirecta.getValorIva16());
      factura.setValorTotal(ventaDirecta.getValorTotal());
      factura.setValorIva10(ventaDirecta.getValorIva10());
      factura.setPuntoVenta(ventaDirecta.getPuntoVenta());
      factura.setDescuentoCliente(ventaDirecta.getDescuentoCliente());
      if (ventaDirecta.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA || ventaDirecta.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA_ESPECIAL) {
        factura.setNumeroFactura(ventaDirecta.getNumeroFactura());
        factura.setValorIva5(ventaDirecta.getValorIva5());
        factura.setObservacion2(ventaDirecta.getObservacion2());
        factura.setObservacion3(ventaDirecta.getObservacion3());
      }
    }
    StringBuilder secuencia = new StringBuilder();
    if (ventaDirecta.getConsecutivoDocumento() == null || ventaDirecta.getConsecutivoDocumento().isEmpty() || ventaDirecta.getConsecutivoDocumento().substring(0, 2).equals("OD")) {
      TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(ventaDirecta.getEstadosxdocumento().getId().getIdTipoDocumento());
      secuencia.append(tipoDocumento.getAbreviatura());
      if (ventaDirecta.getUbicacionDestino() != null && ventaDirecta.getUbicacionDestino().getId() == -1) {
        secuencia.append(ventaDirecta.getUbicacionOrigen().getEmpresa().getId());
      } else {
        Ubicacion ubicacionDestino = ubicacionDAOLocal.findByPK(ventaDirecta.getUbicacionDestino().getId());
        secuencia.append(ubicacionDestino.getEmpresa().getId());
      }
      Long valorSecuencia = documentoDAO.consultarProximoValorSecuencia(secuencia.toString().concat("_SEQ"));
      secuencia.append("-");
      secuencia.append(valorSecuencia);
      String consecutivoDocumento = secuencia.toString();
      factura.setConsecutivoDocumento(consecutivoDocumento);
      if (ventaDirecta.getCliente() != null) {
        if (ventaDirecta.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA) {
          factura.setNumeroFactura(consecutivoDocumento);
        }
      }
    } else {
      // el usuario ha introducido un consecutivo manualmente
      factura.setConsecutivoDocumento(ventaDirecta.getConsecutivoDocumento());
      if (ventaDirecta.getCliente() != null) {
        if (ventaDirecta.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA) {
          factura.setNumeroFactura(ventaDirecta.getConsecutivoDocumento());
        }
      }
    }
    factura = (Documento) documentoDAO.add(factura);
    LOGGER.debug("Factura creada con id: " + factura.getId());
    LOGGER.debug("Crear log de auditoria");
    auditoria.setTabla(Documento.class.getName());
    auditoria.setAccion("CRE");
    auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
    auditoria.setIdRegTabla(factura.getId());
    auditoria = logAuditoriaDAO.add(auditoria);
    LOGGER.debug("Log de auditoria creado con id: " + auditoria.getIdLog());
    LOGGER.debug("Crear los productos para la factura");
    for (ProductosXDocumento pxd : listaProductos) {
      pxd.setInformacion(Boolean.FALSE);
      pxd.setCalidad(Boolean.FALSE);
      pxd.setFechaEstimadaEntrega(new Timestamp(System.currentTimeMillis()));
      pxd.setFechaEntrega(new Timestamp(System.currentTimeMillis()));
      ProductosXDocumentoPK productosXDocumentoPK = new ProductosXDocumentoPK();
      productosXDocumentoPK.setIdDocumento(factura.getId());
      productosXDocumentoPK.setIdProducto(pxd.getProductosInventario().getId());
      pxd.setId(productosXDocumentoPK);
      pxd.setMoneda(new Moneda("COP"));
      pxd.setCantidad2(BigDecimal.ZERO);
      pxd.setBodegasLogica1(new BodegasLogica(ConstantesBodegas.DEFAULT));
      pxd.setBodegasLogica2(new BodegasLogica(ConstantesBodegas.DEFAULT));
      pxd.setValorUnitarioUsd(BigDecimal.ZERO);
      pxd = productoXDocumentoDAO.add(pxd);
      LOGGER.debug("Producto creado con idProducto: " + pxd.getId().getIdProducto() + " y idDocumento: " + pxd.getId().getIdDocumento());
    }
    LOGGER.debug("Productos creados exitosamente");
    LOGGER.debug("Consultar los productos de un ventaDirecta por su consecutivo [informacion relacionada a los movimientos]");
    Documento documentoRelacionado = maestrosEJB.consultarDocumentoPorConsecutivo(remisionRelacionada.getConsecutivoDocumento());
    List<ProductosXDocumento> pxds = this.consultarProductosPorDocumento(documentoRelacionado.getId());
    LOGGER.debug("Ejecutar despacho");
    LOGGER.debug("Actualizar orden despacho con consecutivo: " + remisionRelacionada.getObservacionDocumento());
    Documento ordenDespacho = documentoDAO.consultarDocumentoPorConsecutivo(remisionRelacionada.getObservacionDocumento());
    ordenDespacho.getEstadosxdocumento().setEstado(new Estado((long) ConstantesDocumento.CERRADO));
    ordenDespacho.setDocumentoCliente(remisionRelacionada.getDocumentoCliente());
    documentoDAO.actualizarEstadoDocumentoPorId(ordenDespacho);
    LOGGER.debug("Orden despacho actualizada");
    LOGGER.debug("Crear los movimientos de salida para la orden de despacho con id: " + ordenDespacho.getId());
    for (ProductosXDocumento pxd : pxds) {
      MovimientosInventario movimientosSalida = new MovimientosInventario();
      movimientosSalida.setDocumento(ordenDespacho);
      movimientosSalida.setFecha(new Timestamp(System.currentTimeMillis()));
      movimientosSalida.setTipoMovimiento(new TipoMovimiento(ConstantesInventario.SALIDAS));
      movimientosSalida.setUbicacionOrigen(ordenDespacho.getUbicacionDestino());
      movimientosSalida.setUbicacionDestino(new Ubicacion(ConstantesUbicacion.EXTERNA));
      movimientosSalida.setBodegasLogica1(new BodegasLogica(ConstantesBodegas.DEFAULT));
      movimientosSalida.setBodegasLogica2(new BodegasLogica(ConstantesBodegas.DEFAULT));
      movimientosSalida.setCantidad(pxd.getCantidad1());
      movimientosSalida.setMoneda(pxd.getMoneda());
      movimientosSalida.setProductosInventario(pxd.getProductosInventario());
      movimientosSalida.setUnidade(pxd.getUnidade());
      movimientosSalida.setValorUnitarioMl(0);
      movimientosSalida.setValotUnitarioUsd(0);
      movimientosSalida = movimientoInventarioDAO.add(movimientosSalida);
      LOGGER.debug("Movimiento de salida creado con id: " + movimientosSalida.getId());
    }
    LOGGER.debug("Movimientos de salida creados exitosamente");
    LOGGER.debug("Orden despacho ejecutada exitosamente");
    LOGGER.debug("Crear los movimientos a la remision seleccionada con id: " + remisionRelacionada.getId());
    for (ProductosXDocumento pxd : pxds) {
      MovimientosInventario movimiento = new MovimientosInventario();
      movimiento.setFecha(new Timestamp(System.currentTimeMillis()));
      movimiento.setUbicacionOrigen(new Ubicacion(ConstantesUbicacion.TRANSITO));
      movimiento.setUbicacionDestino(remisionRelacionada.getUbicacionDestino());
      movimiento.setBodegasLogica1(new BodegasLogica(ConstantesBodegas.DEFAULT));
      movimiento.setBodegasLogica2(new BodegasLogica(ConstantesBodegas.DEFAULT));
      movimiento.setProductosInventario(pxd.getProductosInventario());
      movimiento.setDocumento(remisionRelacionada);
      movimiento.setUnidade(pxd.getUnidade());
      movimiento.setCantidad(pxd.getCantidad1());
      movimiento.setMoneda(pxd.getMoneda());
      movimiento.setValorUnitarioMl(0);
      movimiento.setValotUnitarioUsd(0);
      MovimientosInventario movimientoEntrada = movimiento;
      movimientoEntrada.setTipoMovimiento(new TipoMovimiento(ConstantesInventario.ENTRADAS));
      movimientoEntrada = movimientoInventarioDAO.add(movimientoEntrada);
      LOGGER.debug("Movimiento de entrada creado con id: " + movimientoEntrada.getId());
      MovimientosInventario movimientoSalida = movimiento;
      movimientoSalida.setTipoMovimiento(new TipoMovimiento(ConstantesInventario.SALIDAS));
      movimientoSalida = movimientoInventarioDAO.add(movimientoSalida);
      LOGGER.debug("Movimiento de salida creado con id: " + movimientoSalida.getId());
    }
    LOGGER.debug("Movimientos creados exitosamente");
    LOGGER.debug("Actualizar estado de la remision con id: " + remisionRelacionada.getId());
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("id_estado", (long) ConstantesDocumento.RECIBIDO);
    parametros.put("id", (long) remisionRelacionada.getId());
    documentoDAO.ejecutarConsultaNativa(Documento.ACTUALIZAR_ESTADO_DOCUMENTO, parametros);
    LOGGER.debug("Remision actualizada exitosamente");
    factura.getCliente().getCiudad();
    factura.getPuntoVenta();
    return factura;
  }

  @Override
  public void imprimirFactura(Documento factura) {
    LOGGER.trace("Metodo: <<imprimirFactura>>");
    LOGGER.debug("Actualizar numero de factura y estado de la factura con id: " + factura.getId());
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("estado", (long) ConstantesDocumento.IMPRESO);
    parametros.put("numeroFactura", factura.getNumeroFactura());
    parametros.put("id", (long) factura.getId());
    documentoDAO.ejecutarConsultaNativa(Documento.ACTUALIZAR_ESTADO_Y_NUMERO_FACTURA, parametros);
    LOGGER.debug("Factura actualizada exitosamente");
  }

  @Override
  public List<ProductosXDocumento> consultarProductosPorDocumentoOrdenadosPorSKU(Long id) {
    LOGGER.trace("Metodo: <<consultarProductosPorDocumentoOrdenadosPorSKU>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("idDocumento", id);
    return documentoDAO.buscarPorConsultaNombrada(ProductosXDocumento.FIND_BY_DOCUMENTO_ORDER_BY_SKU, parametros);
  }

  @Override
  public Documento generarConsumoServicios(Documento ventaDirecta, List<ProductosXDocumento> listaProductos, LogAuditoria auditoria) {
    LOGGER.trace("Metodo: <<generarFactura>>");
    LOGGER.debug("Crear factura");
    // tipo de documento y estado
    Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
    EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
    estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.ACTIVO);
    estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.VENTA_DIRECTA);
    estadosxdocumento.setId(estadosxdocumentoPK);
    // llenar el objeto documento
    ventaDirecta.setEstadosxdocumento(estadosxdocumento);
    ventaDirecta.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
    ventaDirecta.setUbicacionOrigen(new Ubicacion(ConstantesUbicacion.EXTERNA));
    ventaDirecta.setDescuentoCliente(new BigDecimal(0.0));
    ventaDirecta.setSitioEntrega("CS");
    ventaDirecta.setNumeroFactura("0");// TODO: revisar esto, pq aunque en la db esta por defecto "0", al parecer no lo está tomando.
    StringBuilder secuencia = new StringBuilder();
    if (ventaDirecta.getConsecutivoDocumento() == null || ventaDirecta.getConsecutivoDocumento().isEmpty() || ventaDirecta.getConsecutivoDocumento().substring(0, 2).equals("OD")) {
      TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(ventaDirecta.getEstadosxdocumento().getId().getIdTipoDocumento());
      secuencia.append(tipoDocumento.getAbreviatura());
      if (ventaDirecta.getUbicacionDestino() != null && ventaDirecta.getUbicacionDestino().getId() == -1) {
        secuencia.append(ventaDirecta.getUbicacionOrigen().getEmpresa().getId());
      } else {
        Ubicacion ubicacionDestino = ubicacionDAOLocal.findByPK(ventaDirecta.getUbicacionDestino().getId());
        secuencia.append(ubicacionDestino.getEmpresa().getId());
      }
      Long valorSecuencia = documentoDAO.consultarProximoValorSecuencia(secuencia.toString().concat("_SEQ"));
      secuencia.append("-");
      secuencia.append(valorSecuencia);
      String consecutivoDocumento = secuencia.toString();
      ventaDirecta.setConsecutivoDocumento(consecutivoDocumento);
      if (ventaDirecta.getCliente() != null) {
        if (ventaDirecta.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA) {
          ventaDirecta.setNumeroFactura(consecutivoDocumento);
        }
      }
    } else {
      // el usuario ha introducido un consecutivo manualmente
      if (ventaDirecta.getCliente() != null) {
        if (ventaDirecta.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA) {
          ventaDirecta.setNumeroFactura(ventaDirecta.getConsecutivoDocumento());
        }
      }
    }
    ventaDirecta = (Documento) documentoDAO.add(ventaDirecta);
    LOGGER.debug("Factura creada con id: " + ventaDirecta.getId());
    LOGGER.debug("Crear log de auditoria");
    auditoria.setTabla(Documento.class.getName());
    auditoria.setAccion("CRE");
    auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
    auditoria.setIdRegTabla(ventaDirecta.getId());
    auditoria = logAuditoriaDAO.add(auditoria);
    LOGGER.debug("Log de auditoria creado con id: " + auditoria.getIdLog());
    LOGGER.debug("Crear los productos para la factura");
    for (ProductosXDocumento pxd : listaProductos) {
      pxd.setInformacion(Boolean.FALSE);
      pxd.setCalidad(Boolean.FALSE);
      pxd.setFechaEstimadaEntrega(ventaDirecta.getFechaEsperadaEntrega());
      pxd.setFechaEntrega(ventaDirecta.getFechaEntrega());
      ProductosXDocumentoPK productosXDocumentoPK = new ProductosXDocumentoPK();
      productosXDocumentoPK.setIdDocumento(ventaDirecta.getId());
      productosXDocumentoPK.setIdProducto(pxd.getProductosInventario().getId());
      pxd.setId(productosXDocumentoPK);
      pxd.setUnidade(pxd.getProductosInventario().getUnidadVenta());
      pxd.setMoneda(new Moneda("COP"));
      pxd.setBodegasLogica1(new BodegasLogica(ConstantesBodegas.DEFAULT));
      pxd.setBodegasLogica2(new BodegasLogica(ConstantesBodegas.DEFAULT));
      // consultar el ultimo costo y setearlo en ml
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("idProducto", pxd.getProductosInventario().getId());
      parametros.put("fecha", new Timestamp(System.currentTimeMillis()));
      Double costoML = productoDocumentoDAO.buscarRegistroPorConsultaNativa("SELECT costo_ml FROM costos WHERE id_producto = :idProducto AND fecha <= :fecha ORDER BY fecha DESC LIMIT 1 OFFSET 0", parametros);
      if (costoML != null) {
        pxd.setValorUnitatrioMl(new BigDecimal(costoML));
      } else {
        pxd.setValorUnitatrioMl(BigDecimal.ZERO);
      }
      pxd.setValorUnitarioUsd(BigDecimal.ZERO);
      pxd.setIva(BigDecimal.ZERO);
      pxd.setDescuentoxproducto(BigDecimal.ZERO);
      pxd.setOtrosDescuentos(BigDecimal.ZERO);
      pxd = productoXDocumentoDAO.add(pxd);
      LOGGER.debug("Producto creado con idProducto: " + pxd.getId().getIdProducto() + " y idDocumento: " + pxd.getId().getIdDocumento());
    }
    LOGGER.debug("Productos creados exitosamente");
    ventaDirecta.getCliente().getCiudad();
    ventaDirecta.getPuntoVenta();
    return ventaDirecta;
  }
}
