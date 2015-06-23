package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.BodegasLogica;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.LogAuditoriaDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.web.mb.util.ConstantesBodegas;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesUbicacion;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

@Stateless
@LocalBean
public class ComercioNacionalEJB implements ComercioNacionalEJBLocal {

  private static final Logger LOGGER = Logger.getLogger(ComercioNacionalEJB.class);
  @EJB
  private ProductoClienteDAOLocal productoClienteDAO;
  @EJB
  private TipoDocumentoDAOLocal tipoDocumentoDAOLocal;
  @EJB
  private UbicacionDAOLocal ubicacionDAOLocal;
  @EJB
  private DocumentoDAOLocal documentoDAO;
  @EJB
  private LogAuditoriaDAOLocal logAuditoriaDAO;
  @EJB
  private ProductosXDocumentoDAOLocal productoXDocumentoDAO;

  @Override
  public List<ProductosXCliente> consultarProductosXCliente(Long idCliente, Long idPuntoVenta) {
    LOGGER.trace("Metodo: <<consultarProductosXCliente>>");
    if (idCliente == null || idPuntoVenta == null) {
      throw new IllegalArgumentException();
    }
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("idCliente", idCliente);
    parametros.put("idPuntoVenta", idPuntoVenta);
    return productoClienteDAO.buscarPorConsultaNombrada(ProductosXCliente.NQ_BUSCAR_PRODUCTOS_X_CLIENTE_Y_PUNTO_VENTA_ACTIVOS, parametros);
  }

  @Override
  public Documento ingresarSolicitudComercioNacional(Documento solicitud, List<ProductosXDocumento> productosXDocumento, LogAuditoria auditoria) {
    LOGGER.trace("Metodo: <<ingresarSolicitudComercioNacional>>");
    LOGGER.debug("Crear solicitud");
    Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
    EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
    estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.ACTIVO);
    estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.VENTA_DIRECTA);
    estadosxdocumento.setId(estadosxdocumentoPK);
    solicitud.setEstadosxdocumento(estadosxdocumento);
    solicitud.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
    solicitud.setFechaEsperadaEntrega(solicitud.getFechaEntrega());
    solicitud.setUbicacionOrigen(solicitud.getPuntoVenta().getUbicacion() == null ? new Ubicacion(1L) : solicitud.getPuntoVenta().getUbicacion());
    solicitud.setUbicacionDestino(solicitud.getPuntoVenta().getUbicacion() == null ? new Ubicacion(1L) : solicitud.getPuntoVenta().getUbicacion());
    solicitud.setNumeroFactura("0");
    solicitud.setDescuentoCliente(solicitud.getCliente().getDescuentoCliente());
    // obtener consecutivo documento
    StringBuilder secuencia = new StringBuilder();
    TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(solicitud.getEstadosxdocumento().getId().getIdTipoDocumento());
    secuencia.append(tipoDocumento.getAbreviatura());
    if (solicitud.getUbicacionDestino() != null && solicitud.getUbicacionDestino().getId().equals(ConstantesUbicacion.EXTERNA)) {
      Ubicacion ubicacionOrigen = ubicacionDAOLocal.findByPK(solicitud.getUbicacionOrigen().getId());
      secuencia.append(ubicacionOrigen.getEmpresa().getId());
    } else {
      Ubicacion ubicacionDestino = ubicacionDAOLocal.findByPK(solicitud.getUbicacionDestino().getId());
      secuencia.append(ubicacionDestino.getEmpresa().getId());
    }
    Long valorSecuencia = documentoDAO.consultarProximoValorSecuencia(secuencia.toString().concat("_SEQ"));
    secuencia.append("-");
    secuencia.append(valorSecuencia);
    String consecutivoDocumento = secuencia.toString();
    solicitud.setConsecutivoDocumento(consecutivoDocumento);
    solicitud = (Documento) documentoDAO.add(solicitud);
    LOGGER.debug("VD creada con id: " + solicitud.getId());
    LOGGER.debug("Crear log de auditoria");
    auditoria.setTabla("Documentos");
    auditoria.setAccion("CRE");
    auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
    auditoria.setIdRegTabla(solicitud.getId());
    auditoria.setValorNuevo(solicitud.getConsecutivoDocumento());
    auditoria = logAuditoriaDAO.add(auditoria);
    LOGGER.debug("Log de auditoria creado con id: " + auditoria.getIdLog());
    LOGGER.debug("Crear los productos para la remision");
    for (ProductosXDocumento pxd : productosXDocumento) {
      ProductosXDocumentoPK productosXDocumentoPK = new ProductosXDocumentoPK();
      productosXDocumentoPK.setIdDocumento(solicitud.getId());
      productosXDocumentoPK.setIdProducto(pxd.getProductosInventario().getId());
      pxd.setId(productosXDocumentoPK);
      pxd.setInformacion(Boolean.FALSE);
      pxd.setCalidad(Boolean.FALSE);
      pxd.setFechaEstimadaEntrega(solicitud.getFechaEsperadaEntrega());
      pxd.setFechaEntrega(solicitud.getFechaEntrega());
      pxd.setMoneda(new Moneda("COP"));
      pxd.setCantidad2(BigDecimal.ZERO);
      pxd.setBodegasLogica1(new BodegasLogica(ConstantesBodegas.DEFAULT));
      pxd.setBodegasLogica2(new BodegasLogica(ConstantesBodegas.DEFAULT));
      pxd = productoXDocumentoDAO.add(pxd);
      LOGGER.debug("Producto creado con idProducto: " + pxd.getId().getIdProducto() + " y idDocumento: " + pxd.getId().getIdDocumento());
    }
    LOGGER.debug("Productos creados exitosamente");
    return solicitud;
  }
}
