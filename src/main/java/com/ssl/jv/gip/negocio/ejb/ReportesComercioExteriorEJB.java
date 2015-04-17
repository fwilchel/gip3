package com.ssl.jv.gip.negocio.ejb;

import static com.ssl.jv.gip.web.util.SecurityFilter.LOGGER;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dao.ClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoLotesOICDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXNegociacionDAOLocal;
import com.ssl.jv.gip.negocio.dao.MuestrasXLoteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAO;
import com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal;
import com.ssl.jv.gip.negocio.dto.CuentaContableComprobanteInformeDiarioDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoCintaTestigoMagneticaDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoReporteVentasCEDTO;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.InstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.LoteTerminoTransporteDTO;
import com.ssl.jv.gip.negocio.dto.ProductosInformeTiendaLineaDTO;
import com.ssl.jv.gip.negocio.dto.ReporteProduccionDTO;

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

  @EJB
  private DocumentoXNegociacionDAOLocal documentoXNegociacionDAO;

  @EJB
  private DocumentoLotesOICDAOLocal documentoLotesOICDAO;

  /**
   * Default constructor.
   */
  public ReportesComercioExteriorEJB() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public List<Documento> consultarFacturasExportacionReimprimir(Map<String, Object> parametros) {
    return documentoDAO.buscarPorConsultaNombrada(Documento.FIND_DOCUMENTOS_FX_REIMPRIMIR, parametros);
  }

  @Override
  public List<ProductosXDocumento> consultarProductosPorDocumento(Long id) {
    return productosXDocumentoDAO.consultarPorDocumentoConColecciones(id);
  }

  @Override
  public List<Muestrasxlote> consultarMuestrasPorCantidad(BigDecimal cantidad) {
    return muestrasXLoteDAOLocal.consultarMuestrasPorCantidad(cantidad);
  }

  @Override
  public List<Documento> consultarFacturasExportacionFechaTipo(FiltroDocumentoDTO filtro) {
    return documentoDAO.consultarDocumentos(filtro);
  }

  @Override
  public List<Documento> consultarDocumentosPorTipoDocumentoEstadoTipoCafe(FiltroDocumentoDTO filtro) {
    return documentoDAO.consultarDocumentos(filtro);
  }

  @Override
  public List<InstruccionEmbarqueDTO> consultarListadoInstruccionesEmbarque() {
    LOGGER.debug("Metodo: <<consultarListadoInstruccionesEmbarque>>");
    return terminosTransporteDAO.obtenerListadoInstruccionesEmbarque();
  }

  @Override
  public InstruccionEmbarqueDTO consultarDetalleInstruccionEmbarque(Long id) {
    LOGGER.debug("Metodo: <<consultarDetalleInstruccionEmbarque>>");
    InstruccionEmbarqueDTO documento = terminosTransporteDAO.obtenerDetalleInstruccionEmbarque(id);
    Map<String, Object> parametros;
    List<LoteTerminoTransporteDTO> listaLotes;
    List<Long> tipoSolicitudCafe = null;
    List<Long> tipoMercadeo = null;
    {
      String DOCUMENTOS_POR_TERMINO_TRASPORTE = "SELECT documento_x_negociacion.id_documento, documento_x_negociacion.solicitud_cafe FROM terminos_transporte_x_documento inner join documento_x_negociacion on terminos_transporte_x_documento.id_documento = documento_x_negociacion.id_documento WHERE terminos_transporte_x_documento.id_terminos_transporte = :idTermino";
      parametros = new HashMap<>();
      parametros.put("idTermino", id);
      List<Object[]> listaIds = terminosTransporteDAO.buscarPorConsultaNativa(DOCUMENTOS_POR_TERMINO_TRASPORTE, parametros);
      if (listaIds != null) {
        for (Object[] obj : listaIds) {
          if (obj[1].equals(true)) {
            if (tipoSolicitudCafe == null) {
              tipoSolicitudCafe = new ArrayList<>();
            }
            tipoSolicitudCafe.add(((BigInteger) obj[0]).longValue());
          } else {
            if (tipoMercadeo == null) {
              tipoMercadeo = new ArrayList<>();
            }
            tipoMercadeo.add(((BigInteger) obj[0]).longValue());
          }
        }
      }
    }
    {
      parametros = new HashMap<>();
      StringBuilder query = new StringBuilder();
      if (tipoSolicitudCafe != null) {
        query.append(LoteTerminoTransporteDTO.LISTADO_LOTES_TERMINO_TRASPORTE_TIPO_SOLICITUD_CAFE);
        parametros.put("documento", tipoSolicitudCafe);
      }
      if (tipoSolicitudCafe != null && tipoMercadeo != null) {
        query.append(LoteTerminoTransporteDTO.UNION_ALL);
      }
      if (tipoMercadeo != null) {
        query.append(LoteTerminoTransporteDTO.LISTADO_LOTES_TERMINO_TRASPORTE_DOCUMENTO_TIPO_MERCADEO);
        parametros.put("documentosMercadeo", tipoMercadeo);
      }
      if (tipoSolicitudCafe != null || tipoMercadeo != null) {
        query.append(LoteTerminoTransporteDTO.ORDER_BY);
        LOGGER.debug("query: " + query.toString());
        listaLotes = terminosTransporteDAO.buscarPorConsultaNativa(query.toString(), LoteTerminoTransporteDTO.class, parametros);
        StringBuilder consecutivoSPs = new StringBuilder();
        StringBuilder consecutivo = new StringBuilder();
        StringBuilder consecutivoLEs = new StringBuilder();
        BigDecimal sumMercadeo = new BigDecimal(0);
        BigDecimal sumCafe = new BigDecimal(0);
        for (int i = 0; i < listaLotes.size(); i++) {
          LoteTerminoTransporteDTO lote = listaLotes.get(i);
          if (i > 0) {
            if (i > 0) {
              consecutivoSPs.append(" / ");
              consecutivo.append(" / ");
              consecutivoLEs.append(" / ");
            }
            consecutivoSPs.append(lote.getSitioEntrega());
            consecutivo.append(lote.getConsecutivoDocumento());
            consecutivoLEs.append(lote.getObservacionDocumento());
            documento.setFechaETA(lote.getFechaEta());
          }
          if (lote.getConsecutivoLote().equals("MERCADEO")) {
            if (lote.getTotalCajas() != null) {
              sumMercadeo = sumMercadeo.add(lote.getTotalCajas());
            }
          } else {
            if (lote.getTotalCajas() != null) {
              sumCafe = sumCafe.add(lote.getTotalCajas());
            }
          }
        }
        documento.setConsecutivoSPs(consecutivoSPs.toString());
        documento.setConsecutivo(consecutivo.toString());
        documento.setConsecutivoLEs(consecutivoLEs.toString());
        documento.setSumaSolicitudCafe(sumCafe);
        documento.setSumaMercadeo(sumMercadeo);
        documento.setListaLotes(listaLotes);
      }
    }
    return documento;
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
  public List<Documento> consultarDocumentosPorTipoDocumentoEstadoSolicitudCafeFechas(FiltroDocumentoDTO filtro) {
    return documentoDAO.consultarDocumentos(filtro);
  }

  @Override
  public List<DocumentoXNegociacion> consultarDocumentoXNegociacionxDocumento(Long idDocumento) {
    return documentoXNegociacionDAO.consultarDocumentoXNegociacionPorIdDocumento(idDocumento);
  }

  @Override
  public List<DocumentoCintaTestigoMagneticaDTO> consultarDocumentosReporteCintaTestigoMagnetica(Map<String, Object> parametros) {
    LOGGER.debug("Metodo: <<consultarDocumentosReporteCintaTestigoMagnetica>>");
    return documentoDAO.consultarDocumentosReporteCintaTestigoMagnetica(parametros);
  }

  @Override
  public List<DocumentoXLotesoic> consultarPorConsecutivoDocumento(String consecutivoDocumento) {
    return documentoLotesOICDAO.consultarPorConsecutivoDocumento(consecutivoDocumento);
  }

  @Override
  public List<ReporteProduccionDTO> consultarProductosReporteProduccion(Map<String, Object> parametros) {
    return productosXDocumentoDAO.buscarPorConsultaNativa(ReporteProduccionDTO.BUSCAR_PRODUCTOS_REPORTE_PRODUCCION, ReporteProduccionDTO.class, parametros);
  }

  @Override
  public List<ProductosInformeTiendaLineaDTO> consultarProductosPorListaEmpaque(Long idDocumento) {
    LOGGER.debug("Metodo: <<consultarDetalleInstruccionEmbarque>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("idDocumento", idDocumento);
    return productosXDocumentoDAO.buscarPorConsultaNativa(ProductosInformeTiendaLineaDTO.PRODUCTOS_POR_LISTA_EMPAQUE, ProductosInformeTiendaLineaDTO.class, parametros);
  }

  @Override
  public List<CuentaContableComprobanteInformeDiarioDTO> consultarCuentaContableComprobanteInformeDiarioFX(Map<String, Object> parametros) {
    LOGGER.debug("Metodo: <<CuentaContableComprobanteInformeDiarioDTO>>");
    return documentoDAO.buscarPorConsultaNativa(CuentaContableComprobanteInformeDiarioDTO.CUENTA_CONTABLE_COMPROBANTE_INFORME_DIARIO, CuentaContableComprobanteInformeDiarioDTO.class, parametros);
  }

  @Override
  public Documento consultarFacturaFXReimprimir(Long id) {
    LOGGER.debug("Metodo: <<consultarFacturaFXReimprimir>>");
    if (id == null) {
      throw new IllegalArgumentException("El parametro <<observacionDocumento>> es requerido");
    } else {
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("id", id);
      return documentoDAO.buscarRegistroPorConsultaNombrada(Documento.FIND_DOCUMENTO_FX_REIMPRIMIR_BY_ID, parametros);
    }
  }

  @Override
  public String consultarConsecutivoOrdenFacturaFX(Long id) {
    LOGGER.debug("Metodo: <<consultarConsecutivoOrdenFacturaFX>>");
    if (id == null) {
      throw new IllegalArgumentException("El parametro <<observacionDocumento>> es requerido");
    } else {
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("id", id);
      return documentoDAO.buscarRegistroPorConsultaNativa(Documento.BUSCAR_CONSECUTIVO_ORDEN_FACTURA_FX, parametros);
    }
  }

}
