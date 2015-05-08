package com.ssl.jv.gip.negocio.ejb;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.BodegasLogica;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.EstadoDAOLocal;
import com.ssl.jv.gip.negocio.dao.PaisDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.util.BodegaLogica;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

/**
 * Session Bean implementation class AbastecimientoEJB
 */
@Stateless
@LocalBean
public class AbastecimientoEJB implements AbastecimientoEJBLocal {

  @EJB
  private DocumentoDAOLocal documentoDAOLocal;

  @EJB
  private UbicacionDAOLocal ubicacionDAOLocal;

  @EJB
  private ProductosXDocumentoDAOLocal productosXDocumentoDAOLocal;

  @EJB
  private TipoDocumentoDAOLocal tipoDocumentoDAOLocal;

  @EJB
  private EstadoDAOLocal estadoDAOLocal;

  @EJB
  private PaisDAOLocal paisDAOLocal;

  @EJB
  private ProductoInventarioDAOLocal productoInventarioDAOLocal;

  /**
   * Default constructor.
   */
  public AbastecimientoEJB() {

  }

  @Override
  public List<Documento> consultarDocumentosPorTipoYEstado(
      FiltroDocumentoDTO filtroDocumentoDTO) {
    return documentoDAOLocal
        .consultarDocumentosPorTipoDocumentoYEstado(filtroDocumentoDTO);
  }

  @Override
  public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario) {
    return ubicacionDAOLocal.consultarUbicacionesPorUsuario(idUsuario);
  }

  @Override
  public List<ProductosXDocumento> consultarProductosXDocumentosPorDocumento(
      Long idDocumento) {
    return productosXDocumentoDAOLocal.consultarPorDocumento(idDocumento);
  }

  @Override
  public Documento guardarSugerenciaCompra(Documento sugerencia,
      List<ProductosXDocumento> productosXSugerencia) {
    if (sugerencia.getId() == null) {
      TipoDocumento tipoDocumento = tipoDocumentoDAOLocal
          .findByPK(sugerencia.getEstadosxdocumento().getId()
              .getIdTipoDocumento());
      Ubicacion ubicacion = null;
      if (com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo().equals(
          sugerencia.getUbicacionDestino().getId())) {
        ubicacion = ubicacionDAOLocal.findByPK(sugerencia
            .getUbicacionOrigen().getId());
      } else {
        ubicacion = ubicacionDAOLocal.findByPK(sugerencia
            .getUbicacionDestino().getId());
      }
      String prefijoConsecutivo = tipoDocumento.getAbreviatura()
          + ubicacion.getEmpresa().getId();
      Long valorSecuencia = documentoDAOLocal
          .consultarProximoValorSecuencia(prefijoConsecutivo + "_seq");
      sugerencia.setConsecutivoDocumento(prefijoConsecutivo + "-"
          + valorSecuencia);

      sugerencia.getEstadosxdocumento().setEstado(
          estadoDAOLocal.findByPK(sugerencia.getEstadosxdocumento()
              .getId().getIdEstado()));

      sugerencia = (Documento) documentoDAOLocal.add(sugerencia);
    } else {
      documentoDAOLocal.update(sugerencia);
    }
    for (ProductosXDocumento productosXDocumento : productosXSugerencia) {
      if (productosXDocumento.getProductosInventario().isIncluido()) {
        productosXDocumento.setFechaEntrega(sugerencia
            .getFechaEsperadaEntrega());
        productosXDocumento.setFechaEstimadaEntrega(sugerencia
            .getFechaEsperadaEntrega());
        ProductosXDocumentoPK id = productosXDocumento.getId();
        if (id.getIdDocumento() == null) {
          id.setIdDocumento(sugerencia.getId());
          productosXDocumento.setId(id);
          productosXDocumentoDAOLocal.add(productosXDocumento);
        } else {
          productosXDocumentoDAOLocal.update(productosXDocumento);
        }
      } else {
        productosXDocumentoDAOLocal.delete(productosXDocumento);
      }
    }
    return sugerencia;
  }

  @Override
  public void importarSugerenciasCompra(byte[] archivo) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          new ByteArrayInputStream(archivo)));
      String line = null;
      List<String[]> lines = new ArrayList<String[]>();
      boolean error = true;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      while ((line = reader.readLine()) != null) {
        if (!line.isEmpty()) {
          error = false;
          String[] values = line.split(";");
          if (values.length != 5) {
            throw new RuntimeException("Archivo inválido");
          }

          if (values[1].trim().isEmpty()
              || values[2].trim().isEmpty()) {
            error = true;
          }

          try {
            Long.parseLong(values[0]);
            new BigDecimal(values[3]);
            sdf.parse(values[4]);
          } catch (NumberFormatException e) {
            error = true;
          } catch (ParseException e) {
            error = true;
          }

          lines.add(values);
        }
      }
      reader.close();

      if (error) {
        throw new RuntimeException(
            "Archivo con errores en sus registros");
      }

      if (!lines.isEmpty()) {
        Documento documento = crearDocumento(lines.get(0), sdf);
        documento = (Documento) documentoDAOLocal.add(documento);
        List<ProductosXDocumento> productosXDocumentos = new ArrayList<ProductosXDocumento>();
        int numLinea = 0;
        for (String[] lineFile : lines) {
          numLinea++;
          productosXDocumentos.add(crearProductoXDocumento(numLinea,
              lineFile, documento));
        }

        for (ProductosXDocumento productosXDocumento : productosXDocumentos) {
          productosXDocumentoDAOLocal.add(productosXDocumento);
        }
      }
    } catch (IOException e) {
      throw new EJBException("Error al leer el archivo");
    }
  }

  private ProductosXDocumento crearProductoXDocumento(int numLinea,
      String[] lineFile, Documento documento) {
    ProductosXDocumento productosXDocumento = new ProductosXDocumento();
    ProductosXDocumentoPK id = new ProductosXDocumentoPK();
    id.setIdDocumento(documento.getId());
    ProductosInventario productosInventario = productoInventarioDAOLocal
        .consultarPorSku(lineFile[2]);
    if (productosInventario == null) {
      throw new EJBException(String.format(
          "El Producto refrenciado en la línea %d no existe",
          numLinea));
    }
    id.setIdProducto(productosInventario.getId());
    productosXDocumento.setId(id);
    productosXDocumento.setUnidade(productosInventario.getUnidadVenta());

    productosXDocumento
        .setFechaEntrega(documento.getFechaEsperadaEntrega());
    productosXDocumento.setFechaEstimadaEntrega(documento
        .getFechaEsperadaEntrega());

    Pais pais = paisDAOLocal.findByPK(lineFile[1]);
    if (pais == null) {
      throw new EJBException(String.format(
          "El Pais refrenciado en la línea %d no existe", numLinea));
    }
    productosXDocumento.setMoneda(pais.getMoneda());

    productosXDocumento.setCantidad1(new BigDecimal(lineFile[3]));

    BodegasLogica bodegasLogicaDefault = new BodegasLogica(
        BodegaLogica.DEFAULT.getCodigo());
    productosXDocumento.setBodegasLogica1(bodegasLogicaDefault);
    productosXDocumento.setBodegasLogica2(bodegasLogicaDefault);
    productosXDocumento.setCantidad2(BigDecimal.ZERO);
    productosXDocumento.setValorUnitatrioMl(BigDecimal.ZERO);
    productosXDocumento.setValorUnitarioUsd(BigDecimal.ZERO);
    productosXDocumento.setCalidad(false);
    productosXDocumento.setInformacion(false);
    productosXDocumento.setDescuentoxproducto(BigDecimal.ZERO);

    return productosXDocumento;
  }

  private Documento crearDocumento(String[] values, SimpleDateFormat sdf) {
    Documento sugerencia = new Documento();
    sugerencia
        .setUbicacionDestino(new Ubicacion(Long.parseLong(values[0])));
    sugerencia.setUbicacionOrigen(new Ubicacion(
        com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo()));

    Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
    EstadosxdocumentoPK id = new EstadosxdocumentoPK();
    id.setIdEstado(Estado.ACTIVO.getCodigo());
    id.setIdTipoDocumento((long) ConstantesTipoDocumento.SUGERENCIA_COMPRAS);
    estadosxdocumento.setId(id);

    sugerencia.setEstadosxdocumento(estadosxdocumento);
    sugerencia.setFechaGeneracion(new Date());

    Date fechaEntrega;
    try {
      fechaEntrega = sdf.parse(values[4]);
      sugerencia.setFechaEntrega(fechaEntrega);
      sugerencia.setFechaEsperadaEntrega(fechaEntrega);
    } catch (ParseException e) {
    }

    sugerencia.setNumeroFactura("0");

    TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(sugerencia
        .getEstadosxdocumento().getId().getIdTipoDocumento());
    Ubicacion ubicacion = null;
    if (com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo().equals(
        sugerencia.getUbicacionDestino().getId())) {
      ubicacion = ubicacionDAOLocal.findByPK(sugerencia
          .getUbicacionOrigen().getId());
    } else {
      ubicacion = ubicacionDAOLocal.findByPK(sugerencia
          .getUbicacionDestino().getId());
    }
    String prefijoConsecutivo = tipoDocumento.getAbreviatura()
        + ubicacion.getEmpresa().getId();
    Long valorSecuencia = documentoDAOLocal
        .consultarProximoValorSecuencia(prefijoConsecutivo + "_seq");
    sugerencia.setConsecutivoDocumento(prefijoConsecutivo + "-"
        + valorSecuencia);

    return sugerencia;
  }

}
