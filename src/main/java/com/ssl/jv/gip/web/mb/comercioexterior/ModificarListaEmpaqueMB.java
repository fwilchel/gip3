package com.ssl.jv.gip.web.mb.comercioexterior;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;

/**
 * @author Juan Jose Buzon
 *
 */
@ManagedBean(name = "modificarListaEmpaqueMB")
@SessionScoped
public class ModificarListaEmpaqueMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = 4940573106269006240L;

  private static final Logger LOGGER = Logger
      .getLogger(ModificarListaEmpaqueMB.class);

  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJBLocal;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private String idUsuario;

  private List<Documento> listaEmpaques;

  private Integer language = AplicacionMB.SPANISH;
  private Modo modo;

  private Documento seleccionado;

  private Date fechaActual;
  private String consecutivoDocumento;

  private List<ProductosInventario> productosInventarios;

  private List<ProductosXDocumento> productosXDocumentos;

  private BigDecimal totalCantidad;
  private BigDecimal totalPallets;
  private BigDecimal totalCajas;
  private BigDecimal totalPesoNeto;
  private BigDecimal totalPesoBruto;

  @PostConstruct
  public void init() {
    productosInventarios = new ArrayList<ProductosInventario>();
    productosXDocumentos = new ArrayList<ProductosXDocumento>();
    totalCantidad = BigDecimal.ZERO;
    totalPallets = BigDecimal.ZERO;
    totalCajas = BigDecimal.ZERO;
    totalPesoNeto = BigDecimal.ZERO;
    totalPesoBruto = BigDecimal.ZERO;
    fechaActual = new Date();
  }

  public void consultarListaEmpaques(ActionEvent actionEvent) {
    try {
      consecutivoDocumento = "%" + consecutivoDocumento + "%";
      listaEmpaques = comercioExteriorEJBLocal
          .consultarDocumentosActivosPorTipoDocumentoYConsecutivoDocumento(
              (long) ConstantesTipoDocumento.LISTA_EMPAQUE,
              consecutivoDocumento);
    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e,
          ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e,
            RuntimeException.class);
        if (unrollException != null) {
          this.addMensajeError(unrollException.getLocalizedMessage());
        } else {
          this.addMensajeError(e);
        }
      }
    }
  }

  public String editar() {
    String outcome = null;
    try {
      this.modo = Modo.EDITAR;
      this.productosInventarios = new ArrayList<ProductosInventario>();
      this.productosXDocumentos = new ArrayList<ProductosXDocumento>();
      totalCantidad = BigDecimal.ZERO;
      totalPallets = BigDecimal.ZERO;
      totalCajas = BigDecimal.ZERO;
      totalPesoNeto = BigDecimal.ZERO;
      totalPesoBruto = BigDecimal.ZERO;
      outcome = "modificar_lista_empaque";
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
    }
    return outcome;
  }

  public void cargarArchivo(FileUploadEvent fileUploadEvent) {
    try {
      UploadedFile uploadedFile = fileUploadEvent.getFile();
      // this.comercioExteriorEJBLocal

      List<String[]> lines = obtenerDatosDesdeArchivo(uploadedFile
          .getContents());

      if (lines.isEmpty()) {
        this.addMensajeError("Archivo vacío");
      } else {
        List<String> skus = new ArrayList<String>();
        for (String[] line : lines) {
          skus.add(line[0]);
        }
        List<ProductosInventario> productos = comercioExteriorEJBLocal
            .consultarProductosInventariosPorSkus(skus);

        this.productosInventarios = obtenerProductosInexistentes(skus,
            productos);

        this.productosXDocumentos = comercioExteriorEJBLocal
            .consultarProductosXDocumentosPorDocumento(seleccionado
                .getId());
        for (ProductosXDocumento productosXDocumento : productosXDocumentos) {
          actualizarConDatosDeArchivo(productosXDocumento, lines);
          totalCantidad = totalCantidad.add(productosXDocumento
              .getCantidad1());
          totalPallets = totalPallets.add(productosXDocumento
              .getCantidadPalletsItem());
          totalCajas = totalCajas.add(productosXDocumento
              .getCantidadCajasItem());
          totalPesoNeto = totalPesoNeto.add(productosXDocumento
              .getTotalPesoNetoItem());
          totalPesoBruto = totalPesoBruto.add(productosXDocumento
              .getTotalPesoBrutoItem());
        }

        this.addMensajeInfo("Vista preliminar cargada con éxito");
      }

    } catch (IOException e) {
      this.addMensajeError("Error al leer el archivo");
    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e,
          ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e,
            RuntimeException.class);
        if (unrollException != null) {
          this.addMensajeError(unrollException.getLocalizedMessage());
        } else {
          this.addMensajeError(e);
        }
      }
    }
  }

  private void actualizarConDatosDeArchivo(
      ProductosXDocumento productosXDocumento, List<String[]> lines) {
    for (String[] line : lines) {
      if (productosXDocumento.getProductosInventario().getSku()
          .equals(line[0])) {
        productosXDocumento.setCantidadPalletsItem(new BigDecimal(
            line[1].trim()));
        productosXDocumento.setCantidadCajasItem(new BigDecimal(line[2]
            .trim()));
        productosXDocumento.setTotalPesoNetoItem(new BigDecimal(line[3]
            .trim()));
        productosXDocumento.setTotalPesoBrutoItem(new BigDecimal(
            line[4].trim()));
        break;
      }
    }

  }

  private List<ProductosInventario> obtenerProductosInexistentes(
      List<String> skus, List<ProductosInventario> productos) {
    List<ProductosInventario> inexistentes = new ArrayList<ProductosInventario>();
    boolean existe = false;
    for (String sku : skus) {
      existe = false;
      for (ProductosInventario productosInventario : productos) {
        if (sku.equals(productosInventario.getSku())) {
          existe = true;
          break;
        }
      }

      if (!existe) {
        inexistentes.add(getProductoInexistente(sku));
      }
    }
    return inexistentes;
  }

  private ProductosInventario getProductoInexistente(String sku) {
    ProductosInventario productosInventario = new ProductosInventario();
    productosInventario.setSku(sku);
    productosInventario.setNombre("PRODUCTO NO EXISTE");
    return productosInventario;
  }

  private List<String[]> obtenerDatosDesdeArchivo(byte[] archivo)
      throws IOException {
    List<String[]> lines = new ArrayList<String[]>();
    BufferedReader reader = new BufferedReader(new InputStreamReader(
        new ByteArrayInputStream(archivo)));
    boolean error = false;
    String message = null;
    int numLinea = 0;
    String line = reader.readLine();
    while ((line = reader.readLine()) != null) {
      numLinea++;
      if (!line.isEmpty()) {
        String[] values = line.split("\\|");
        if (values.length != 5) {
          message = "Error de estructura en la línea " + numLinea;
          error = true;
          break;
        }

        // if (values[0].trim().isEmpty()) {
        // mensaje = "Error de datos en el archivo";
        // error = true;
        // break;
        // }
        try {
          new BigDecimal(values[1].trim());
          new BigDecimal(values[2].trim());
          new BigDecimal(values[3].trim());
          new BigDecimal(values[4].trim());
        } catch (NumberFormatException e) {
          message = "Error de datos en la línea " + numLinea;
          error = true;
          break;
        }

        lines.add(values);
      }
    }
    reader.close();

    if (error) {
      throw new RuntimeException(message);
    }

    return lines;
  }

  public String modificarListaEmpaque() {
    String outcome = null;
    try {

      List<DocumentoXNegociacion> documentoXNegociacions = seleccionado
          .getDocumentoXNegociacions();
      if (!documentoXNegociacions.isEmpty()) {
        for (DocumentoXNegociacion documentoXNegociacion : documentoXNegociacions) {
          documentoXNegociacion.setTotalPallets(totalPallets);
          documentoXNegociacion.setTotalTendidos(totalCajas);
          documentoXNegociacion.setTotalPesoBruto(totalPesoBruto);
          documentoXNegociacion.setTotalPesoNeto(totalPesoNeto);
        }

      }

      List<DocumentoXLotesoic> documentoXLotesoics = seleccionado
          .getDocumentoXLotesoics();
      if (!documentoXLotesoics.isEmpty()) {
        for (DocumentoXLotesoic documentoXLotesoic : documentoXLotesoics) {
          documentoXLotesoic.setTotalCajas(totalCajas);
          documentoXLotesoic.setTotalPesoNeto(totalPesoNeto);
        }
      }

      comercioExteriorEJBLocal.modificarListaEmpaque(seleccionado,
          productosXDocumentos);

      consecutivoDocumento = null;
      this.modo = Modo.CREAR;
      outcome = "listado_LE4";
    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e,
          ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e,
            RuntimeException.class);
        if (unrollException != null) {
          this.addMensajeError(unrollException.getLocalizedMessage());
        } else {
          this.addMensajeError(e);
        }
      }
    }
    return outcome;
  }

  public boolean isCreacion() {
    if (this.modo != null && this.modo.equals(Modo.CREAR)) {
      return true;
    } else {
      return false;
    }
  }

  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public List<Documento> getListaEmpaques() {
    return listaEmpaques;
  }

  public void setListaEmpaques(List<Documento> listaEmpaques) {
    this.listaEmpaques = listaEmpaques;
  }

  public Modo getModo() {
    return modo;
  }

  public void setModo(Modo modo) {
    this.modo = modo;
  }

  public Documento getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(Documento seleccionado) {
    this.seleccionado = seleccionado;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public Date getFechaActual() {
    return fechaActual;
  }

  public void setFechaActual(Date fechaActual) {
    this.fechaActual = fechaActual;
  }

  public MenuMB getMenu() {
    return menu;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  public BigDecimal getTotalCantidad() {
    return totalCantidad;
  }

  public void setTotalCantidad(BigDecimal totalCantidad) {
    this.totalCantidad = totalCantidad;
  }

  public BigDecimal getTotalPallets() {
    return totalPallets;
  }

  public void setTotalPallets(BigDecimal totalPallets) {
    this.totalPallets = totalPallets;
  }

  public BigDecimal getTotalCajas() {
    return totalCajas;
  }

  public void setTotalCajas(BigDecimal totalCajas) {
    this.totalCajas = totalCajas;
  }

  public BigDecimal getTotalPesoNeto() {
    return totalPesoNeto;
  }

  public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
    this.totalPesoNeto = totalPesoNeto;
  }

  public BigDecimal getTotalPesoBruto() {
    return totalPesoBruto;
  }

  public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
    this.totalPesoBruto = totalPesoBruto;
  }

  public List<ProductosInventario> getProductosInventarios() {
    return productosInventarios;
  }

  public void setProductosInventarios(
      List<ProductosInventario> productosInventarios) {
    this.productosInventarios = productosInventarios;
  }

  public List<ProductosXDocumento> getProductosXDocumentos() {
    return productosXDocumentos;
  }

  public void setProductosXDocumentos(
      List<ProductosXDocumento> productosXDocumentos) {
    this.productosXDocumentos = productosXDocumentos;
  }

}
