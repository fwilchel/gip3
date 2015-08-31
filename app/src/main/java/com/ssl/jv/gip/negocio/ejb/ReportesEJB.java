package com.ssl.jv.gip.negocio.ejb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dao.ComextFormatoNovedadesDAO;
import com.ssl.jv.gip.negocio.dao.ComextRequerimientoExportacionDAOLocal;
import com.ssl.jv.gip.negocio.dao.CuentaContableDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientosInventarioComextDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;
import com.ssl.jv.gip.negocio.dto.CuentaContableDTO;
import com.ssl.jv.gip.negocio.dto.DetalleDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

/**
 * Session Bean implementation class ReportesEJB
 */
@Stateless
@LocalBean
public class ReportesEJB implements ReportesEJBLocal {

  private static final Logger LOGGER = Logger.getLogger(ReportesEJB	.class);

  @EJB
  private ComextFormatoNovedadesDAO comextFormatoNovedadesDAO;

  @EJB
  private DocumentoDAOLocal documentoDAO;

  @EJB
  private ProductosXDocumentoDAOLocal productosXDocumentoDAOLocal;

  @EJB
  private CuentaContableDAOLocal cuentaContableDAOLocal;

  @EJB
  private MovimientosInventarioComextDAOLocal movimientosInventarioComextDAOLocal;

  @EJB
  private ComextRequerimientoExportacionDAOLocal comextRequerimientoExportacionDAOLocal;

  @Override
  public List<ComextFormatoNovedadesDTO> consultarComextFormatoNovedades() {
    return comextFormatoNovedadesDAO.consultarComextFormatoNovedades();
  }

  @Override
  public List<Documento> consultarFacturasProformasActivasAprobadasOAsignadasPorConsecutivo(String consecutivo) {
    return documentoDAO.consultarDocumentosPorTipoDocumentoConsecutivoDocumentoYEstados((long) ConstantesTipoDocumento.FACTURA_PROFORMA, consecutivo, Estado.ACTIVO.getCodigo(), Estado.APROBADA.getCodigo(), Estado.ASIGNADA.getCodigo());
  }

  @Override
  public List<Documento> consultarDocumentosParaGenerarFacturaExportacion(String consecutivoDocumento) {
    return documentoDAO.consultarDocumentosParaGenerarFacturaExportacion(consecutivoDocumento);
  }

  @Override
  public List<ProductosXDocumento> consultarProductosXDocumentosFacturaProformaPorDocumentoYCliente(Long idDocumento, Long idCliente) {
    return productosXDocumentoDAOLocal.consultarPorDocumentoYCliente(idDocumento, idCliente);
  }

  @Override
  public List<CuentaContableDTO> consultarReporteFacturasFX(String consecDoc, String fechaIni, String fechaFin) {
    return cuentaContableDAOLocal.consultarReporteFacturasFX(consecDoc, fechaIni, fechaFin);
  }

  @Override
  public List<CuentaContableDTO> consultarReporteFacturasFD(String consecDoc, String fechaIni, String fechaFin) {
    return cuentaContableDAOLocal.consultarReporteFacturasFD(consecDoc, fechaIni, fechaFin);
  }

  @Override
  public List<MovimientosInventarioComext> consultarMovimientosInventarioComextsPorSKU(String sku, boolean ultimoSaldo) {
    return movimientosInventarioComextDAOLocal.consultarPorSKU(sku, ultimoSaldo);
  }

  @Override
  public List<ProductosXDocumento> consultarProductosXDocumentosFacturaProformaPorDocumento(Long idDocumento) {
    return productosXDocumentoDAOLocal.consultarPorDocumento(idDocumento);
  }

  @Override
  public List<DetalleDocumentoDTO> consultarDetalleDocumentos(FiltroConsultaSolicitudDTO filtro) {
	LOGGER.debug("Metodo: <<consultarDetalleDocumentos>>");
	String select = "SELECT row_number() OVER() AS id, doc.consecutivo_documento AS consecutivoDocumento, pi.sku AS sku, pi.nombre AS descripcionProducto, pxd.cantidad1 AS cantidad, e.nombre AS estadoDocumento, td.nombre AS tipoDocumento, doc.fecha_generacion AS fechaGeneracion, ud.nombre AS ubicacionDestino, uo.nombre AS ubicacionOrigen, doc.observacion_documento AS observaciones ";
	String from = "FROM documentos doc INNER JOIN productosxdocumentos pxd ON doc.id = pxd.id_documento INNER JOIN productos_inventario pi ON pxd.id_producto = pi.id INNER JOIN estados e ON doc.id_estado = e.id INNER JOIN tipo_documento td ON doc.id_tipo_documento = td.id INNER JOIN ubicaciones uo ON doc.id_ubicacion_origen = uo.id INNER JOIN ubicaciones ud ON doc.id_ubicacion_destino = ud.id ";
    StringBuilder where = new StringBuilder();
    where.append("WHERE 1 = 1 ");
    DateFormat df;
    if (filtro.getConsecutivoDocumento() != null && !filtro.getConsecutivoDocumento().isEmpty()) {
      where.append(" AND UPPER(doc.consecutivo_documento) LIKE UPPER('%");
      where.append(filtro.getConsecutivoDocumento());
      where.append("%')");
    }
    if (filtro.getTipoDocumento() != null && filtro.getTipoDocumento().compareTo(new Long(0)) != 0) {
      where.append(" AND doc.id_tipo_documento = ");
      where.append(filtro.getTipoDocumento());
    }
    if (filtro.getFechaInicio() != null) {
      df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
      where.append(" AND doc.fecha_generacion >= '");
      where.append(df.format(filtro.getFechaInicio()));
      where.append("'");
    }
    if (filtro.getFechaFinal() != null) {
      df = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
      where.append(" AND doc.fecha_generacion <= '");
      where.append(df.format(filtro.getFechaFinal()));
      where.append("'");
    }
    if (filtro.getIdEstado() != null && filtro.getIdEstado().compareTo(new Long(0)) != 0) {
      where.append(" AND doc.id_estado = ");
      where.append(filtro.getIdEstado());
    }
    String query = select + from + where.toString();
    return documentoDAO.buscarPorConsultaNativa(query, DetalleDocumentoDTO.class, null);
  }
  
  @Override
  public List<ProductosXDocumento> consultarProductosXDocumentosFacturaProformaPorDocumento_PICE(Long idDocumento) {
	return productosXDocumentoDAOLocal.consultarPorDocumento_PICE(idDocumento);
  }

}
