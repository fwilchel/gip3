package com.ssl.jv.gip.web.mb.maestros;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import thirdparty.mortennobel.ResampleOp;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioComextFiltroVO;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Parametro;

/**
 * @author Juan Jose Buzon
 *
 */
@ManagedBean(name = "inventarioComercioFotosMB")
@SessionScoped
public class InventarioComercioFotosMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = 891641226527986872L;
  private static final Logger LOGGER = Logger.getLogger(InventarioComercioExteriorMB.class);

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private String idUsuario;

  private Integer language = AplicacionMB.SPANISH;
  private Modo modo;

  private ProductosInventarioComextFiltroVO filtroVO;
  private List<ProductosInventarioComext> productosInventarioComexts;
  private ProductosInventarioComext seleccionado;

  private List<SelectItem> categoriasInventarios;

  @EJB
  private MaestrosEJBLocal maestrosEJBLocal;
  @EJB
  private ComunEJBLocal comunEJBLocal;
  private String rutaFotoPequena;
  private String rutaFotoMediana;
  private String rutaFotoGrande;

  private String pathFotos;
  private String pathServidor;

  @PostConstruct
  public void init() {
    try {
      idUsuario = menu.getUsuario().getId();
      filtroVO = new ProductosInventarioComextFiltroVO();
      productosInventarioComexts = new ArrayList<ProductosInventarioComext>();
      cargarCategoriasInventario();
      cargarParametros();
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
    }
  }

  private void cargarParametros() {
    String[] nombresParametros = new String[]{Parametro.PATH_FOTOS.getNombre(), Parametro.PATH_SERVIDOR.getNombre()};
    List<com.ssl.jv.gip.jpa.pojo.Parametro> parametros = this.comunEJBLocal.consultarParametroPorNombres(nombresParametros);
    for (com.ssl.jv.gip.jpa.pojo.Parametro parametro : parametros) {
      if (Parametro.PATH_SERVIDOR.getNombre().equals(parametro.getNombre())) {
        this.pathServidor = parametro.getValor();
      } else {
        this.pathFotos = parametro.getValor();
      }
    }
  }

  private void cargarCategoriasInventario() {
    List<CategoriasInventario> categorias = maestrosEJBLocal.consultarCategoriasInventarios();
    SelectItemGroup group = null;
    categoriasInventarios = new ArrayList<SelectItem>();
    for (CategoriasInventario categoriasInventario : categorias) {
      group = new SelectItemGroup(categoriasInventario.getNombre());
      group.setSelectItems(getSelectItems(categoriasInventario.getCategoriasInventarios()));
      categoriasInventarios.add(group);
    }
  }

  private SelectItem[] getSelectItems(List<CategoriasInventario> categoriasInventarios) {
    List<SelectItem> items = new ArrayList<SelectItem>();
    SelectItem item = null;
    for (CategoriasInventario categoriasInventario : categoriasInventarios) {
      item = new SelectItem(categoriasInventario.getId(), categoriasInventario.getNombre());
      items.add(item);
    }
    return items.toArray(new SelectItem[categoriasInventarios.size()]);
  }

  public void consultarProductosInventarioComExt() {
    try {
      FacesContext context = FacesContext.getCurrentInstance();
      // Map map = context.getExternalContext().getRequestParameterMap();

      if ("".equals(filtroVO.getSkuProducto())) {
        filtroVO.setSkuProducto("%");
      } else {
        filtroVO.setSkuProducto("%" + filtroVO.getSkuProducto() + "%");
      }

      if ("".equals(filtroVO.getNombreProducto())) {
        filtroVO.setNombreProducto("%");
      } else {
        filtroVO.setNombreProducto("%" + filtroVO.getNombreProducto() + "%");
      }

      productosInventarioComexts = maestrosEJBLocal.consultarProductosInventarioComextsParaInventarioComercioFotos(filtroVO);

      for (ProductosInventarioComext productosInventarioComext : productosInventarioComexts) {
        File f = new File(this.pathFotos + "med_" + productosInventarioComext.getProductosInventario().getSku() + ".jpg");
        if (f.exists()) {
          productosInventarioComext.setRutaFoto1("med_" + productosInventarioComext.getProductosInventario().getSku() + ".jpg");
        } else {
          productosInventarioComext.setRutaFoto1("med_JV_LOGO.jpg");
        }

        if (productosInventarioComext.getDescripcion() == null || productosInventarioComext.getDescripcion().equals("")) {
          productosInventarioComext.setDescripcion("N/A");
        }

        if (productosInventarioComext.getNombrePrdProveedor() == null || productosInventarioComext.getNombrePrdProveedor().equals("")) {
          productosInventarioComext.setNombrePrdProveedor("N/A");
        }
      }

    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e, RuntimeException.class);
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
      modo = Modo.EDITAR;

      // FacesContext context = FacesContext.getCurrentInstance();
      // Map<String, String> map = context.getExternalContext()
      // .getRequestParameterMap();
      String sku = seleccionado.getProductosInventario().getSku();

      rutaFotoGrande = pathServidor + "grande_" + sku + ".jpg";
      rutaFotoMediana = pathServidor + "med_" + sku + ".jpg";
      rutaFotoPequena = pathServidor + "peq_" + sku + ".jpg";

      outcome = "modificar_foto_ce";
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
    }
    return outcome;
  }

  public void cargarArchivo(FileUploadEvent fileUploadEvent) {
    try {
      String path2 = "", strfileName = "", ext = "", ext2 = "";
      int ancho, alto;

      UploadedFile uploadedFile = fileUploadEvent.getFile();
      int inicio = uploadedFile.getFileName().indexOf(".");
      int fin = uploadedFile.getFileName().length();
      ext = uploadedFile.getFileName().substring(inicio, fin).toLowerCase();
      ext2 = uploadedFile.getFileName().substring(inicio + 1, fin).toLowerCase();

      String rutaFoto = seleccionado.getProductosInventario().getSku() + ext;

      path2 = "grande_" + seleccionado.getProductosInventario().getSku() + ext;
      String rutaFoto3 = path2;
      strfileName = pathFotos + path2;
      System.out.println("ruta a cargar: " + strfileName);

      File dst = new File(strfileName);
      byte[] src = uploadedFile.getContents();

      writeFileToServer(src, dst);

      path2 = "med_" + seleccionado.getProductosInventario().getSku() + ext;
      String rutaFoto1 = path2;
      strfileName = pathFotos + path2;

      ancho = 165;
      alto = 181;

      writeFileToServer2(src, dst, strfileName, ancho, ext2, alto);

      path2 = "peq_" + seleccionado.getProductosInventario().getSku() + ext;
      String rutaFoto2 = path2;
      strfileName = pathFotos + path2;
      ancho = 51;
      alto = 77;

      writeFileToServer2(src, dst, strfileName, ancho, ext2, alto);

      seleccionado.setRutaFoto1(rutaFoto1);
      seleccionado.setRutaFoto2(rutaFoto2);
      seleccionado.setRutaFoto3(rutaFoto3);

      this.maestrosEJBLocal.actualizarProductoInventarioComext(seleccionado);

      this.addMensajeInfo("Archivo cargado con éxito");

    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        this.addMensajeError(e);
      }
    }
  }

  public void writeFileToServer(byte[] src, File dst) throws IOException {

    InputStream in = new ByteArrayInputStream(src);
    OutputStream out = new FileOutputStream(dst);

    // Transfer bytes from in to out
    byte[] buf = new byte[1024];
    int len;
    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }

    in.close();
    out.close();

  }

  public void writeFileToServer2(byte[] src, File dst, String strfileName, int ancho, String ext2, int alto) throws IOException {
    InputStream in = new ByteArrayInputStream(src);
    BufferedImage src2 = ImageIO.read(in);

    int nuevo_ancho = (src2.getWidth() * alto) / src2.getHeight();

    ResampleOp resampleOp = new ResampleOp(nuevo_ancho, alto);
    BufferedImage rescaled = resampleOp.filter(src2, null);

    ImageIO.write(rescaled, ext2, new File(strfileName));

    in.close();
  }

  public String volver() {
    return "listado_maestro_inventario_ce2";
  }

  public MenuMB getMenu() {
    return menu;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public ProductosInventarioComextFiltroVO getFiltroVO() {
    return filtroVO;
  }

  public void setFiltroVO(ProductosInventarioComextFiltroVO filtroVO) {
    this.filtroVO = filtroVO;
  }

  public List<ProductosInventarioComext> getProductosInventarioComexts() {
    return productosInventarioComexts;
  }

  public void setProductosInventarioComexts(List<ProductosInventarioComext> productosInventarioComexts) {
    this.productosInventarioComexts = productosInventarioComexts;
  }

  public ProductosInventarioComext getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(ProductosInventarioComext seleccionado) {
    this.seleccionado = seleccionado;
  }

  public List<SelectItem> getCategoriasInventarios() {
    return categoriasInventarios;
  }

  public void setCategoriasInventarios(List<SelectItem> categoriasInventarios) {
    this.categoriasInventarios = categoriasInventarios;
  }

  public String getRutaFotoPequena() {
    return rutaFotoPequena;
  }

  public void setRutaFotoPequena(String rutaFotoPequena) {
    this.rutaFotoPequena = rutaFotoPequena;
  }

  public String getRutaFotoMediana() {
    return rutaFotoMediana;
  }

  public void setRutaFotoMediana(String rutaFotoMediana) {
    this.rutaFotoMediana = rutaFotoMediana;
  }

  public String getRutaFotoGrande() {
    return rutaFotoGrande;
  }

  public void setRutaFotoGrande(String rutaFotoGrande) {
    this.rutaFotoGrande = rutaFotoGrande;
  }

  public String getPathFotos() {
    return pathFotos;
  }

  public void setPathFotos(String pathFotos) {
    this.pathFotos = pathFotos;
  }

  public String getPathServidor() {
    return pathServidor;
  }

  public void setPathServidor(String pathServidor) {
    this.pathServidor = pathServidor;
  }
}
