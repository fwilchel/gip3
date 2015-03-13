package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.DocumentoRecibirDevolucionDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDevolucionDTO;
import com.ssl.jv.gip.negocio.dto.UbicacionRecibirDevolucionDTO;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import static com.ssl.jv.gip.web.util.SecurityFilter.LOGGER;

/**
 * Session Bean implementation class DevolucionesEJB
 */
@Stateless
@LocalBean
public class DevolucionesEJB implements DevolucionesEJBLocal {

  @EJB
  private UbicacionDAOLocal ubicacionDAO;

  @EJB
  private ProductoInventarioDAOLocal productoInventarioDAO;

  @EJB
  private DocumentoDAOLocal documentoDAO;

  @EJB
  private TipoDocumentoDAOLocal tipoDocumentoDAOLocal;

  @EJB
  private ProductosXDocumentoDAOLocal productoXDocumentoDAO;

  @Override
  public List<Ubicacion> consultarPorIds(List<Long> ids) {
    return ubicacionDAO.consultarPorIds(ids);
  }

  @Override
  public List<ProductoDevolucionDTO> consultarProductosInventarioPorPais(List<String> paises) {
    return productoInventarioDAO.consultarActivosPorPaises(paises);
  }

  @Override
  public List<ProductoDevolucionDTO> consultarProductosActivos() {
    return productoInventarioDAO.consultarActivosDev();
  }

  @Override
  public List<Ubicacion> consultarUbicacionesOrdenadas() {
    return ubicacionDAO.consultarTodasOrdenadas();
  }

  @Override
  public List<Ubicacion> consultarUbicacionesQueSonTiendaPorUsuario(String usuario) {
    return ubicacionDAO.consultarUbicacionesQueSonTiendaPorUsuario(usuario);
  }

  /* (non-Javadoc)
   * @see com.ssl.jv.gip.negocio.ejb.DevolucionesEJBLocal#consultarUbicacionesRecibirDevolucionPorUsuario(java.lang.String)
   */
  @Override
  public List<UbicacionRecibirDevolucionDTO> consultarUbicacionesRecibirDevolucionPorUsuario(String usuario) {
    return ubicacionDAO.consultarUbicacionesRecibirDevolucionPorUsuario(usuario);
  }

  @Override
  public List<DocumentoRecibirDevolucionDTO> consultarDocumentosRecibirDevolucion(String bodega) {
    return documentoDAO.consultarDocumentosRecibirDevolucion(bodega);
  }

  @Override
  public Documento ingresarDevolucionTiendaBodega(Documento documentoOrigen, List<ProductosXDocumento> listaProductos) {
    LOGGER.trace("Metodo: <<ingresarDevolucionTiendaBodega>>");
    LOGGER.debug("Crea el objeto devolucion");
    Documento devolucion = new Documento();
    devolucion.setFechaEsperadaEntrega(documentoOrigen.getFechaEsperadaEntrega());
    devolucion.setUbicacionOrigen(documentoOrigen.getUbicacionOrigen());
    devolucion.setUbicacionDestino(documentoOrigen.getUbicacionDestino());
    devolucion.setEstadosxdocumento(documentoOrigen.getEstadosxdocumento());
    devolucion.setFechaGeneracion(documentoOrigen.getFechaGeneracion());
    devolucion.setFechaEntrega(documentoOrigen.getFechaEntrega());
    devolucion.setProveedore(documentoOrigen.getProveedore());
    devolucion.setObservacionDocumento(documentoOrigen.getObservacionDocumento());
    devolucion.setDocumentoCliente(documentoOrigen.getDocumentoCliente());
    devolucion.setSitioEntrega(documentoOrigen.getSitioEntrega());
    if (documentoOrigen.getCliente() != null) {
      devolucion.setCliente(documentoOrigen.getCliente());
      devolucion.setSubtotal(documentoOrigen.getSubtotal());
      devolucion.setDescuento(documentoOrigen.getDescuento());
      devolucion.setValorIva16(documentoOrigen.getValorIva16());
      devolucion.setValorTotal(documentoOrigen.getValorTotal());
      devolucion.setValorIva10(documentoOrigen.getValorIva10());
      devolucion.setPuntoVenta(documentoOrigen.getPuntoVenta());
      devolucion.setDescuentoCliente(documentoOrigen.getDescuentoCliente());
      if (documentoOrigen.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA || documentoOrigen.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA_ESPECIAL) {
        devolucion.setNumeroFactura(documentoOrigen.getNumeroFactura());
        devolucion.setValorIva5(documentoOrigen.getValorIva5());
        devolucion.setObservacion2(documentoOrigen.getObservacion2());
        devolucion.setObservacion3(documentoOrigen.getObservacion3());
      }
    }
    StringBuilder secuencia = new StringBuilder();
    if (documentoOrigen.getConsecutivoDocumento() == null || documentoOrigen.getConsecutivoDocumento().isEmpty() || documentoOrigen.getConsecutivoDocumento().substring(0, 2).equals("OD")) {
      TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(documentoOrigen.getEstadosxdocumento().getId().getIdTipoDocumento());
      secuencia.append(tipoDocumento.getAbreviatura());
      if (documentoOrigen.getUbicacionDestino() != null && documentoOrigen.getUbicacionDestino().getId() == -1) {
        secuencia.append(documentoOrigen.getUbicacionOrigen().getEmpresa().getId());
      } else {
        Ubicacion ubicacionDestino = ubicacionDAO.findByPK(documentoOrigen.getUbicacionDestino().getId());
        secuencia.append(ubicacionDestino.getEmpresa().getId());
      }
      Long valorSecuencia = documentoDAO.consultarProximoValorSecuencia(secuencia.toString().concat("_SEQ"));
      secuencia.append("-");
      secuencia.append(valorSecuencia);
      String consecutivoDocumento = secuencia.toString();
      devolucion.setConsecutivoDocumento(consecutivoDocumento);
      if (documentoOrigen.getCliente() != null) {
        if (documentoOrigen.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA) {
          devolucion.setNumeroFactura(consecutivoDocumento);
        }
      }
    } else {
      // el usuario ha introducido un consecutivo manualmente
      devolucion.setConsecutivoDocumento(documentoOrigen.getConsecutivoDocumento());
      if (documentoOrigen.getCliente() != null) {
        if (documentoOrigen.getEstadosxdocumento().getId().getIdTipoDocumento().intValue() == ConstantesTipoDocumento.FACTURA) {
          devolucion.setNumeroFactura(documentoOrigen.getConsecutivoDocumento());
        }
      }
    }
    devolucion = (Documento) documentoDAO.add(devolucion);
    LOGGER.debug("Factura creada con id: " + devolucion.getId());
    LOGGER.debug("Crea los productos relacionados a la devolucion");
    for (ProductosXDocumento pxd : listaProductos) {
      LOGGER.debug("Crear producto para la devolucion");
      ProductosXDocumentoPK productosXDocumentoPK = new ProductosXDocumentoPK();
      productosXDocumentoPK.setIdDocumento(devolucion.getId());
      productosXDocumentoPK.setIdProducto(pxd.getProductosInventario().getId());
      pxd = productoXDocumentoDAO.add(pxd);
      LOGGER.debug("Producto creado con idProducto: " + pxd.getId().getIdProducto() + " y idDocumento: " + pxd.getId().getIdDocumento());
    }
    LOGGER.debug("Productos creados satisfactoriamente");
    return devolucion;
  }

}
