package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dao.ClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.MuestrasXLoteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAO;
import com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal;
import com.ssl.jv.gip.negocio.dto.DocTerminosTransporteDTO;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.InstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoReporteVentasCEDTO;
import static com.ssl.jv.gip.web.util.SecurityFilter.LOGGER;
import java.util.Map;

/**
 * Session Bean implementation class ReportesComercioExteriorEJB
 */
@Stateless
@LocalBean
public class ReportesComercioExteriorEJB implements ReportesComercioExteriorEJBLocal {

  @EJB
  private DocumentoDAOLocal documentoDAO;

  @EJB
  private ProductosXDocumentoDAO productosXDocumentoDAO;

  @EJB
  private MuestrasXLoteDAOLocal muestrasXLoteDAOLocal;

  @EJB
  private TerminosTransporteDAOLocal terminosTransporteDAO;

  @EJB
  private ClienteDAOLocal clienteDAO;

  @EJB
  private ProductoInventarioDAOLocal productoInventarioDAO;

  /**
   * Default constructor.
   */
  public ReportesComercioExteriorEJB() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public List<Documento> consultarFacturasExportacion(FiltroDocumentoDTO filtro) {
    return documentoDAO.consultarDocumentosPorTipoDocumentoYEstados(filtro);
  }

  @Override
  public List<ProductosXDocumento> consultarProductosPorDocumento(Long id) {
    return productosXDocumentoDAO.consultarPorDocumento(id);
  }

  @Override
  public List<Muestrasxlote> consultarMuestrasPorCantidad(BigDecimal cantidad) {
    return muestrasXLoteDAOLocal.consultarMuestrasPorCantidad(cantidad);
  }

  @Override
  public List<Documento> consultarFacturasExportacionFechaTipo(
          FiltroDocumentoDTO filtro) {
    return documentoDAO.consultarDocumentosPorTipoDocumentoYFechas(filtro);
  }

  @Override
  public List<Documento> consultarDocumentosPorTipoDocumentoEstadoTipoCafe(FiltroDocumentoDTO filtro) {
    return documentoDAO.consultarDocumentosPorTipoDocumentoEstadoTipoCafe(filtro);
  }

  @Override
  public List<InstruccionEmbarqueDTO> consultarListadoImprimirInstruccionEmbarque() {
    LOGGER.debug("Metodo: <<consultarListadoImprimirInstruccionEmbarque>>");
    return terminosTransporteDAO.obtenerListadoImprimirInstruccionEmbarque();
  }

  @Override
  public List<DocTerminosTransporteDTO> consultarListadoFacturasPorInstruccionEmabarque(Long id) {
    LOGGER.debug("Metodo: <<consultarListadoFacturasPorInstruccionEmabarque>>");
    String idTmp = String.valueOf(id);
    return terminosTransporteDAO.getDocumentosTerminosTranporteById(idTmp);
  }

  @Override
  public List<Cliente> consultarListadoClientesReporteVentasCE(Map<String, Object> parametros) {
    LOGGER.debug("Metodo: <<consultarListadoClientesReporteVentasCE>>");
    return clienteDAO.consultarListadoClientesReporteVentasCE(parametros);
  }

  @Override
  public List<ProductosInventario> consultarListadoProductosReporteVentasCE(Map<String, Object> parametros) {
    LOGGER.debug("Metodo: <<consultarListadoProductosReporteVentasCE>>");
    return productoInventarioDAO.consultarListadoProductosReporteVentasCE(parametros);
  }

  @Override
  public List<DocumentoReporteVentasCEDTO> consultarDocumentosReporteVentasCE(Map<String, Object> parametros) {
    LOGGER.debug("Metodo: <<consultarProductosReporteVentasCE>>");
    return documentoDAO.consultarDocumentosReporteVentasCE(parametros);
  }

  @Override
  public List<Documento> consultarDocumentosPorTipoDocumentoEstadoSolicitudCafeFechas(
          FiltroDocumentoDTO filtro) {
    return documentoDAO.consultarDocumentosPorTipoDocumentoEstadoSolicitudCafeFechas(filtro);
  }
}
