package com.ssl.jv.gip.negocio.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.CintaMagneticaDTO;
import com.ssl.jv.gip.negocio.dto.ComprobanteInformeDiarioDTO;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoReporteTxtFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ReporteVentaDTO;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.LogAuditoriaDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

import static com.ssl.jv.gip.web.util.SecurityFilter.LOGGER;
import java.util.Date;

import java.util.HashMap;

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
    LOGGER.debug("Metodo: <<consultarRemisionesPendientesPorRecibir>> parametros / consecutivo ->> {" + consecutivo + "}");
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
  public Documento crearVentaDirecta(Documento documento, LogAuditoria auditoria,
          List<ProductosXDocumento> productos) {
    //Consultar consecutivo
    StringBuilder strConsecutivo = new StringBuilder();
    TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK((long) ConstantesTipoDocumento.VENTA_DIRECTA);

    if (documento.getUbicacionDestino().getId().equals(com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo())) {
      Ubicacion ubicacionOrigen = ubicacionDAOLocal.findByPK(documento.getUbicacionOrigen().getId());
      strConsecutivo.append(tipoDocumento.getAbreviatura() + ubicacionOrigen.getEmpresa().getId());
    } else {
      Ubicacion ubicacionDestino = ubicacionDAOLocal.findByPK(documento.getUbicacionDestino().getId());
      strConsecutivo.append(tipoDocumento.getAbreviatura() + ubicacionDestino.getEmpresa().getId());
    }

    documento.setConsecutivoDocumento(strConsecutivo.toString() + "-"
            + this.documentoDAO.consultarProximoValorSecuencia(strConsecutivo.toString() + "_seq"));
    documento = (Documento) this.documentoDAO.add(documento);
//		auditoria.setIdRegTabla(documento.getId());
//		auditoria.setValorNuevo(documento.getConsecutivoDocumento());
//		this.logAuditoriaDAO.add(auditoria);
    for (ProductosXDocumento pxd : productos) {
      pxd.getId().setIdDocumento(documento.getId());
      this.productoXDocumentoDAO.add(pxd);
    }
    return documento;
  }

  @Override
  public List<Documento> consultarDocumentosOrdenDespacho() {
    LOGGER.debug("Metodo: <<consultarDocumentosOrdenDespacho>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("idEstado", (long) ConstantesDocumento.ACTIVO);
    parametros.put("idTipoDocumento", (long) ConstantesTipoDocumento.VENTA_DIRECTA);
    return documentoDAO.consultarDocumentosPorEstadoPorTipoPorConsecutivo(parametros);
  }

  @Override
  public List<ProductosXDocumento> consultarProductosPorDocumento(Long id) {
    LOGGER.debug("Metodo: <<consultarProductosPorDocumento>>");
    return productoDocumentoDAO.consultarPorDocumento(id);
  }

  @Override
  public void generarOrdenDespacho(Documento documento, List<ProductosXDocumento> listaProductosXDocumento) {
    LOGGER.debug("Metodo: <<generarOrdenDespacho>>");
    Documento ordenDespacho = new Documento();
    ordenDespacho.setFechaEsperadaEntrega(documento.getFechaEsperadaEntrega());
    ordenDespacho.setFechaEntrega(documento.getFechaEntrega());
    ordenDespacho.setSitioEntrega(documento.getSitioEntrega());
    ordenDespacho.setDocumentoCliente(documento.getDocumentoCliente());
    ordenDespacho.setFechaGeneracion(new Date());
    ordenDespacho.setUbicacionOrigen(documento.getUbicacionOrigen());
    ordenDespacho.setUbicacionDestino(documento.getUbicacionDestino());
    TipoDocumento tipoDocumento = new TipoDocumento((long) ConstantesTipoDocumento.ORDEN_DESPACHO);
    Estado estado = new Estado((long) ConstantesDocumento.ACTIVO);
    Estadosxdocumento exd = new Estadosxdocumento();
    exd.setTipoDocumento(tipoDocumento);
    exd.setEstado(estado);
    ordenDespacho.setEstadosxdocumento(exd);
    ordenDespacho.setCliente(documento.getCliente());
    ordenDespacho.setPuntoVenta(documento.getPuntoVenta());
    ordenDespacho.setConsecutivoDocumento(documento.getConsecutivoDocumento());
    ordenDespacho.setProveedore(documento.getProveedore());
    ordenDespacho.setObservacionDocumento(documento.getConsecutivoDocumento());
  }

  @Override
  public List<Documento> consultarOrdenesDespachoPorObservacion(String observacion) {
    LOGGER.debug("Metodo: <<consultarOrdenesDespachoPorObservacion>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("observacionDocumento", observacion);
    parametros.put("idTipoDocumento", (long) ConstantesTipoDocumento.ORDEN_DESPACHO);
    return documentoDAO.buscarPorConsultaNombrada(Documento.FIND_BY_TIPO_DOCUMENTO_AND_OBSERVACION_DOCUMENTO, parametros);
  }

  @Override
  public Documento generarFactura(Documento factura, List<ProductosXDocumento> listaProductos, LogAuditoria auditoria) {
    LOGGER.debug("Metodo: <<generarFactura>>");
    Documento documento = new Documento();
    documento.setFechaEsperadaEntrega(factura.getFechaEsperadaEntrega());
    documento.setUbicacionOrigen(factura.getUbicacionOrigen());
    documento.setUbicacionDestino(factura.getUbicacionDestino());
    documento.setEstadosxdocumento(factura.getEstadosxdocumento());
    documento.setFechaGeneracion(factura.getFechaGeneracion());
    documento.setFechaEntrega(factura.getFechaEntrega());
    documento.setProveedore(factura.getProveedore());
    documento.setObservacionDocumento(factura.getObservacionDocumento());
    documento.setDocumentoCliente(factura.getDocumentoCliente());
    documento.setSitioEntrega(factura.getSitioEntrega());
    if (factura.getCliente() != null) {
      documento.setCliente(factura.getCliente());
      documento.setSubtotal(factura.getSubtotal());
      documento.setDescuento(factura.getDescuento());
      documento.setValorIva16(factura.getValorIva16());
      documento.setValorTotal(factura.getValorTotal());
      documento.setValorIva10(factura.getValorIva10());
      documento.setPuntoVenta(factura.getPuntoVenta());
      documento.setDescuentoCliente(factura.getDescuentoCliente());
      if (factura.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA || factura.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA_ESPECIAL) {
        documento.setNumeroFactura(factura.getNumeroFactura());
        documento.setValorIva5(factura.getValorIva5());
        documento.setObservacion2(factura.getObservacion2());
        documento.setObservacion3(factura.getObservacion3());
      }
    }
    StringBuilder secuencia = new StringBuilder();
    if (factura.getConsecutivoDocumento() == null || factura.getConsecutivoDocumento().isEmpty() || factura.getConsecutivoDocumento().substring(0, 2).equals("OD")) {
      TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(factura.getEstadosxdocumento().getId().getIdTipoDocumento());
      secuencia.append(tipoDocumento.getAbreviatura());
      if (factura.getUbicacionDestino() != null && factura.getUbicacionDestino().getId() == -1) {
        secuencia.append(factura.getUbicacionOrigen().getEmpresa().getId());
      } else {
        Ubicacion ubicacionDestino = ubicacionDAOLocal.findByPK(factura.getUbicacionDestino().getId());
        secuencia.append(ubicacionDestino.getEmpresa().getId());
      }
      Long valorSecuencia = documentoDAO.consultarProximoValorSecuencia(secuencia.toString().concat("_SEQ"));
      secuencia.append("-");
      secuencia.append(valorSecuencia);
      String consecutivoDocumento = secuencia.toString();
      documento.setConsecutivoDocumento(consecutivoDocumento);
      if (factura.getCliente() != null) {
        if (factura.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA) {
          documento.setNumeroFactura(consecutivoDocumento);
        }
      }
    } else {
      // el usuario ha introducido un consecutivo manualmente
      documento.setConsecutivoDocumento(factura.getConsecutivoDocumento());
      if (factura.getCliente() != null) {
        if (factura.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA) {
          documento.setNumeroFactura(factura.getConsecutivoDocumento());
        }
      }
    }
    documento = (Documento) documentoDAO.add(documento);
    return documento;
  }
}
