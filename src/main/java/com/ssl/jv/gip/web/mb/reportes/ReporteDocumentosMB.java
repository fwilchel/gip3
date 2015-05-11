package com.ssl.jv.gip.web.mb.reportes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.Proveedor;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.exportarExcel;

import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * The Class SolicitudPedidoMB.
 */
@ManagedBean(name = "reporteDocumentosMB")
@ViewScoped
public class ReporteDocumentosMB extends UtilMB {

  private static final Logger LOGGER = Logger.getLogger(ReporteDocumentosMB.class);
  /**
   * The lista documentos.
   */
  private ArrayList<DocumentoIncontermDTO> listaDocumentos = new ArrayList<DocumentoIncontermDTO>();

  /**
   * The lista productos documento.
   */
  private List<ProductosXDocumento> listaProductosDocumento;

  /**
   * The lista ubicaciones.
   */
  private List<Ubicacion> listaUbicaciones;

  /**
   * The lista tipo documento.
   */
  private List<TipoDocumento> listaTipoDocumento;

  /**
   * The lista estado.
   */
  private List<Estado> listaEstado;

  /**
   * The lista proveedor.
   */
  private List<Proveedor> listaProveedor;

  /**
   * The seleccionado.
   */
  private DocumentoIncontermDTO seleccionado;

  /**
   * The filtro.
   */
  private FiltroConsultaSolicitudDTO filtro;

  /**
   * The str ubicaciones.
   */
  private String strUbicaciones;

  /**
   * The str ubicaciones usuario.
   */
  private String strUbicacionesUsuario;

  /**
   * The int ubicacion.
   */
  private Long intUbicacion;

  /**
   * The comercio ejb.
   */
  @EJB
  private ComercioExteriorEJB comercioEjb;

  /**
   * The reportes comercio exterior ejb local.
   */
  @EJB
  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;

  @EJB
  private ComunEJBLocal comunEJBLocal;

  /**
   * The menu.
   */
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  private LazyDataModel<DocumentoIncontermDTO> listaDocumentosLazyModel;

  /**
   * Instantiates a new ingresar costos inconterm mb.
   */
  public ReporteDocumentosMB() {

  }

  /**
   * Inits the.
   */
  @PostConstruct
  public void init() {
    filtro = new FiltroConsultaSolicitudDTO();
  }

  /**
   * Consultar documentos.
   */
  public void consultarDocumentos() {
    LOGGER.trace("Metodo: consultarDocumentos()");
    setListaDocumentosLazyModel(new LazyDocumentoDataModel());
    filtro.setUbicaciones(getStrUbicaciones());
  }

  /**
   * Gets the seleccionado.
   *
   * @return the seleccionado
   */
  public DocumentoIncontermDTO getSeleccionado() {
    return seleccionado;
  }

  /**
   * Sets the seleccionado.
   *
   * @param seleccionado the new seleccionado
   */
  public void setSeleccionado(DocumentoIncontermDTO seleccionado) {
    this.seleccionado = seleccionado;
  }

  /**
   * Consultar solicitud pedido.
   *
   * @return the string
   */
  public String consultarSolicitudPedido() {
    //Consultar la lista inconterm poor cliente
    listaProductosDocumento = this.reportesComercioExteriorEJBLocal.consultarProductosPorDocumento(seleccionado.getIdDocumento());
    return "";
  }

  /**
   * Hacer excel.
   *
   * @throws FileNotFoundException the file not found exception
   */
  public void hacerExcel() throws FileNotFoundException {
    int numeroFilaTope = 60000;
    int numeroIncial = 0;
    try {
      // create a new file
      // FileOutputStream out = new
      // FileOutputStream("reporteProveedores.xls");
      // create a new workbook
      HSSFWorkbook wb = new HSSFWorkbook();

      // declare a row object reference
      HSSFRow r = null;
      HSSFRow r1 = null, r2 = null, r3 = null, r4 = null, r5 = null, r6 = null, r7 = null, r3a = null;
      // declare a cell object reference
      HSSFCell c = null;
      HSSFCell c0 = null, c1 = null, c2 = null, c3 = null, c4 = null, c5 = null, c6 = null, c7 = null, c8 = null, c9 = null, c10 = null;
      HSSFCell c11 = null, c12 = null, c12a = null, c13 = null, c14 = null, c15 = null, c16 = null, c17 = null, c18 = null, c12b = null;
      // create 3 cell styles
      HSSFCellStyle cs = wb.createCellStyle();
      HSSFCellStyle cs2 = wb.createCellStyle();
      HSSFCellStyle cs3 = wb.createCellStyle();
      HSSFCellStyle cs4 = wb.createCellStyle();

      HSSFDataFormat df = wb.createDataFormat();

      // Definicion de variables para el response
      HttpServletResponse response = null;
      FacesContext context = null;
      HttpServletRequest request = null;

      context = FacesContext.getCurrentInstance();
      ExternalContext external = context.getExternalContext();
      response = (HttpServletResponse) external.getResponse();
      request = (HttpServletRequest) external.getRequest();
			// fin definicion de variables para el response

      // ========DEFINICION DE FUENTES=============
      // create 2 fonts objects
      HSSFFont f = wb.createFont(); // Tipo de letra para encabezado de
      // columnas
      HSSFFont f2 = wb.createFont();// Tipo deletra para contenido
      HSSFFont f3 = wb.createFont();// Tipo deletra para nombre reporte
      HSSFFont f4 = wb.createFont();// Tipo deletra para informacion
      // adicional

      // set font 1 to 12 point type
      f.setFontHeightInPoints((short) 11);
      // make it cafe
      f.setColor(HSSFFont.COLOR_NORMAL);
      // make it bold
      // arial is the default font
      f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

      // set font 2 to 10 point type
      f2.setFontHeightInPoints((short) 10);
      // make it negro
      f2.setColor(HSSFFont.COLOR_NORMAL);
      // make it normal
      f2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

      // set font 2 to 10 point type
      f3.setFontHeightInPoints((short) 15);
      // make it negro
      f3.setColor((short) 0x10);
      // make it normal
      f3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

      // set font 2 to 10 point type
      f4.setFontHeightInPoints((short) 11);
      // make it negro
      f4.setColor((short) 0x10);
      // make it normal
      f4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

      // ==========DEFINICION DE ESTILOS PARA ARCHIVO XLS================
      // set cell stlye
      cs.setFont(f);
      // set the cell format
      cs.setDataFormat(df.getFormat("#,##0.0"));
      cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      cs.setBorderBottom(cs2.BORDER_THIN);
      cs.setBorderLeft(cs2.BORDER_THIN);
      cs.setBorderRight(cs2.BORDER_THIN);
      cs.setBorderTop(cs2.BORDER_THIN);

      // set a thin border
      cs2.setBorderBottom(cs2.BORDER_THIN);
      cs2.setBorderLeft(cs2.BORDER_THIN);
      cs2.setBorderRight(cs2.BORDER_THIN);
      cs2.setBorderTop(cs2.BORDER_THIN);
      // set the cell format to text see HSSFDataFormat for a full list
      cs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
      // set the font
      cs2.setFont(f2);
      cs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

      cs3.setFont(f3);
      cs3.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

      cs4.setFont(f4);
      cs4.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

      // Obtiene el numero de hojas del libro de excel necesarias para
      // mostrar los datos.
      int numeroHojas = 1;
      double aux;
      if (listaProductosDocumento.size() < numeroFilaTope) {
        aux = 1;
        numeroFilaTope = listaProductosDocumento.size();
      } else {
        aux = listaProductosDocumento.size() / numeroFilaTope;
      }
      while (aux != 0) {
        if (aux >= 1) {
          aux--;
          numeroHojas++;
        } else {
          aux -= aux;
          numeroHojas++;
        }
      }
      int contHojas = 0;

      ProductosXDocumento objproducto = new ProductosXDocumento();
      int cont = 0;
      ArrayList listaAux = new ArrayList();
      while (contHojas != numeroHojas) {
        listaAux.clear();
        for (int i = numeroIncial; i < numeroFilaTope; i++) {
          listaAux.add(listaProductosDocumento.get(i));
        }
        // crear una nueva hoja con sus propiedades
        HSSFSheet s = wb.createSheet("consignaciones_" + contHojas);
        s.setHorizontallyCenter(true);
        s.setMargin((short) 0, 0.1);
        s.setMargin((short) 1, 0.1);
        s.setMargin((short) 2, 1);
        s.setMargin((short) 3, 1);

        // Inicia escritura de archivo excel por lineas
        // Fila 0
        r1 = s.createRow((short) 0);
        c11 = r1.createCell((short) 0);
        c11.setCellValue("DOCUMENTOS");
        //MergeCellsRecord merge = new MergeCellsRecord();
        //merge.addArea(0, (short) 0, 0, (short) 8);
        c11.setCellStyle(cs3);

        // Fila 1
        r2 = s.createRow((short) 1);
        c12b = r2.createCell((short) 0);
        c12b.setCellValue("UBICACION DESTINO: " + seleccionado.getNombreUbicacionDestino());
        //MergeCellsRecord merge11 = new MergeCellsRecord();
        //merge11.addArea(2, (short) 0, 2, (short) 5);
        c12b.setCellStyle(cs4);

        // Fila 2
        r3 = s.createRow((short) 2);
        c12 = r3.createCell((short) 0);
        c12.setCellValue("ID_DOCUMENTO: " + seleccionado.getIdDocumento());
        //MergeCellsRecord merge1b = new MergeCellsRecord();
        //merge11.addArea(2, (short) 0, 2, (short) 5);
        c12.setCellStyle(cs4);

        r3a = s.createRow((short) 3);
        c12a = r3a.createCell((short) 0);
        c12a.setCellValue("CONSECUTIVO_DOCUMENTO: "
            + seleccionado.getConsecutivoDocumento());
        //MergeCellsRecord merge11a = new MergeCellsRecord();
        //merge11a.addArea(2, (short) 0, 2, (short) 5);
        c12a.setCellStyle(cs4);

        // Fila 3
        r4 = s.createRow((short) 4);

        // Fila 4
        r5 = s.createRow((short) 5);
        c13 = r5.createCell((short) 0);
        String strFechaGeneracion = "";
        if (seleccionado.getFechaGeneracion() == null) {
          Calendar fecha = new GregorianCalendar();
          strFechaGeneracion = fecha.get(Calendar.YEAR) + "-"
              + ((fecha.get(Calendar.MONTH)) + 1) + "-"
              + fecha.get(Calendar.DAY_OF_MONTH);
          c13.setCellValue("FECHA GENERACION : " + strFechaGeneracion);
        } else {
          DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
          String fechaGeneracion = fechaHora.format(seleccionado.getFechaGeneracion());
          c13.setCellValue("FECHA GENERACION : " + fechaGeneracion);
        }

        //MergeCellsRecord merge3 = new MergeCellsRecord();
        //merge3.addArea(4, (short) 0, 4, (short) 5);
        c13.setCellStyle(cs4);

        String strFechaEntrega = "";
        c14 = r5.createCell((short) 5);
        if (seleccionado.getFechaEntrega() == null) {
          Calendar fecha = new GregorianCalendar();
          strFechaEntrega = fecha.get(Calendar.YEAR) + "-"
              + ((fecha.get(Calendar.MONTH)) + 1) + "-"
              + fecha.get(Calendar.DAY_OF_MONTH);
          c14.setCellValue("FECHA ENTREGA: " + strFechaEntrega);
        } else {
          DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
          String fechaEntrega = fechaHora.format(seleccionado.getFechaEntrega());
          c14.setCellValue("FECHA ENTREGA: " + fechaEntrega);
        }

        //MergeCellsRecord merge4 = new MergeCellsRecord();
        //merge4.addArea(4, (short) 7, 4, (short) 12);
        c14.setCellStyle(cs4);

        // Fila 6
        r = s.createRow((short) 6);
        c0 = r.createCell((short) 0);
        c0.setCellValue("SKU");
        c1 = r.createCell((short) 1);
        c1.setCellValue("NOMBRE PRODUCTO");
        c2 = r.createCell((short) 2);
        c2.setCellValue("CANTIDAD REQUERIDA");
        c3 = r.createCell((short) 3);
        c3.setCellValue("FECHA ENTREGA");
        c4 = r.createCell((short) 4);
        c4.setCellValue("UNIDAD");
        c5 = r.createCell((short) 5);
        c5.setCellValue("OBSERVACION");

        c0.setCellStyle(cs);
        c1.setCellStyle(cs);
        c2.setCellStyle(cs);
        c3.setCellStyle(cs);
        c4.setCellStyle(cs);
        c5.setCellStyle(cs);

        // Ultima fila
        r7 = s.createRow((short) listaAux.size() + 9);
        c15 = r7.createCell((short) 0);
        Calendar fecha = new GregorianCalendar();
        String strFecha = fecha.get(Calendar.YEAR) + "-"
            + ((fecha.get(Calendar.MONTH)) + 1) + "-"
            + fecha.get(Calendar.DAY_OF_MONTH);
        c15.setCellValue("FECHA: " + strFecha);
        //MergeCellsRecord merge5 = new MergeCellsRecord();
        //merge5.addArea((listaAux.size() + 9), (short) 1, (listaAux
        //	.size() + 9), (short) 10);
        c15.setCellStyle(cs4);

        for (Integer row = new Integer(7); row < listaAux.size() + 7; row++) {

          r = s.createRow(row.shortValue());
          objproducto = (ProductosXDocumento) listaAux.get(cont);

          c = r.createCell((short) 0);
          c.setCellValue(objproducto.getProductosInventario().getSku());
          c.setCellStyle(cs2);

          c = r.createCell((short) 1);
          c.setCellValue(objproducto.getProductosInventario().getNombre());
          c.setCellStyle(cs2);

          c = r.createCell((short) 2);
          c.setCellValue(objproducto.getCantidad1() + "");
          c.setCellStyle(cs2);

          DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
          String strFechaEstimadaEntrega = fechaHora.format(objproducto.getFechaEstimadaEntrega());

          c = r.createCell((short) 3);
          c.setCellValue(strFechaEstimadaEntrega);
          c.setCellStyle(cs2);

          c = r.createCell((short) 4);
          c.setCellValue(objproducto.getUnidade().getNombre());
          c.setCellStyle(cs2);

          c = r.createCell((short) 5);
          c.setCellValue(objproducto.getObservacion1());
          c.setCellStyle(cs2);

          cont += 1;

          // make this column a bit wider
          s.setColumnWidth((short) (0),
              (short) ((55 * 8) / ((double) 1 / 20)));
          s.setColumnWidth((short) (1),
              (short) ((35 * 8) / ((double) 1 / 20)));
          s.setColumnWidth((short) (2),
              (short) ((35 * 8) / ((double) 1 / 20)));
          s.setColumnWidth((short) (3),
              (short) ((60 * 8) / ((double) 1 / 20)));
          s.setColumnWidth((short) (4),
              (short) ((40 * 8) / ((double) 1 / 20)));
          s.setColumnWidth((short) (5),
              (short) ((40 * 8) / ((double) 1 / 20)));

        }
        numeroIncial = numeroFilaTope + 1;
        numeroFilaTope += numeroFilaTope;
        if (numeroFilaTope > listaProductosDocumento.size()) {
          numeroFilaTope = listaProductosDocumento.size();
        }
        contHojas++;
        s = null;
        cont = 0;
      }

      // Envia por doGet el workbook y lo abre.
      exportarExcel pe = new exportarExcel();
      try {
        pe.doGet(request, response, wb, "consultar_documento");
      } catch (ServletException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      context.responseComplete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Cancelar.
   */
  public void cancelar() {
    listaProductosDocumento = null;
    seleccionado = new DocumentoIncontermDTO();
  }

  /**
   * Gets the lista documentos.
   *
   * @return the lista documentos
   */
  public ArrayList<DocumentoIncontermDTO> getListaDocumentos() {
    return listaDocumentos;
  }

  /**
   * Sets the lista documentos.
   *
   * @param listaDocumentos the new lista documentos
   */
  public void setListaDocumentos(ArrayList<DocumentoIncontermDTO> listaDocumentos) {
    this.listaDocumentos = listaDocumentos;
  }

  /**
   * Gets the menu.
   *
   * @return the menu
   */
  public MenuMB getMenu() {
    return menu;
  }

  /**
   * Sets the menu.
   *
   * @param menu the new menu
   */
  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  /**
   * Gets the filtro.
   *
   * @return the filtro
   */
  public FiltroConsultaSolicitudDTO getFiltro() {
    return filtro;
  }

  /**
   * Sets the filtro.
   *
   * @param filtro the new filtro
   */
  public void setFiltro(FiltroConsultaSolicitudDTO filtro) {
    this.filtro = filtro;
  }

  /**
   * Gets the lista ubicaciones.
   *
   * @return the lista ubicaciones
   */
  public List<Ubicacion> getListaUbicaciones() {
    if (listaUbicaciones == null) {
      strUbicaciones = "";
      listaUbicaciones = comercioEjb.consultarUbicacionesPorUsuario(menu.getUsuario().getId());
      for (Ubicacion ubicacion : listaUbicaciones) {
        if (strUbicaciones.length() == 0) {
          strUbicaciones = " " + ubicacion.getId();
        } else {
          strUbicaciones += "," + ubicacion.getId();
        }
      }
      strUbicacionesUsuario = strUbicaciones;
    }
    return listaUbicaciones;
  }

  /**
   * Sets the lista ubicaciones.
   *
   * @param listaUbicaciones the new lista ubicaciones
   */
  public void setListaUbicaciones(List<Ubicacion> listaUbicaciones) {
    this.listaUbicaciones = listaUbicaciones;
  }

  /**
   * Gets the str ubicaciones.
   *
   * @return the str ubicaciones
   */
  public String getStrUbicaciones() {
    return strUbicaciones;
  }

  /**
   * Sets the str ubicaciones.
   *
   * @param strUbicaciones the new str ubicaciones
   */
  public void setStrUbicaciones(String strUbicaciones) {
    this.strUbicaciones = strUbicaciones;
  }

  /**
   * Gets the str ubicaciones usuario.
   *
   * @return the str ubicaciones usuario
   */
  public String getStrUbicacionesUsuario() {
    return strUbicacionesUsuario;
  }

  /**
   * Sets the str ubicaciones usuario.
   *
   * @param strUbicacionesUsuario the new str ubicaciones usuario
   */
  public void setStrUbicacionesUsuario(String strUbicacionesUsuario) {
    this.strUbicacionesUsuario = strUbicacionesUsuario;
  }

  /**
   * Gets the int ubicacion.
   *
   * @return the int ubicacion
   */
  public Long getIntUbicacion() {
    return intUbicacion;
  }

  /**
   * Sets the int ubicacion.
   *
   * @param intUbicacion the new int ubicacion
   */
  public void setIntUbicacion(Long intUbicacion) {
    this.intUbicacion = intUbicacion;
    /*
     * Si la ubicación escogida es diferente a Todas, la asignamos a
     * strUbicaciones De lo contrario asignamos a strUbicaciones la lista de
     * ubicaciones a las cuales tiene acceso el usuario
     */
    if (intUbicacion > 0) {
      strUbicaciones = " " + intUbicacion;
    } else {
      strUbicaciones = strUbicacionesUsuario;
    }
  }

  /**
   * Gets the lista productos documento.
   *
   * @return the lista productos documento
   */
  public List<ProductosXDocumento> getListaProductosDocumento() {
    return listaProductosDocumento;
  }

  /**
   * Sets the lista productos documento.
   *
   * @param listaProductosDocumento the new lista productos documento
   */
  public void setListaProductosDocumento(
      List<ProductosXDocumento> listaProductosDocumento) {
    this.listaProductosDocumento = listaProductosDocumento;
  }

  /**
   * Gets the lista tipo documento.
   *
   * @return the lista tipo documento
   */
  public List<TipoDocumento> getListaTipoDocumento() {
    if (listaTipoDocumento == null) {
      listaTipoDocumento = comunEJBLocal.consultarTiposDocumentos();
    }
    return listaTipoDocumento;
  }

  /**
   * Sets the lista tipo documento.
   *
   * @param listaTipoDocumento the new lista tipo documento
   */
  public void setListaTipoDocumento(List<TipoDocumento> listaTipoDocumento) {
    this.listaTipoDocumento = listaTipoDocumento;
  }

  /**
   * Gets the lista estado.
   *
   * @return the lista estado
   */
  public List<Estado> getListaEstado() {
    if (listaEstado == null) {
      listaEstado = comunEJBLocal.consultarEstados();
    }
    return listaEstado;
  }

  /**
   * Sets the lista estado.
   *
   * @param listaEstado the new lista estado
   */
  public void setListaEstado(List<Estado> listaEstado) {
    this.listaEstado = listaEstado;
  }

  /**
   * Gets the lista proveedor.
   *
   * @return the lista proveedor
   */
  public List<Proveedor> getListaProveedor() {
    if (listaProveedor == null) {
      listaProveedor = comunEJBLocal.consultarProveedores();
    }
    return listaProveedor;
  }

  /**
   * Sets the lista proveedor.
   *
   * @param listaProveedor the new lista proveedor
   */
  public void setListaProveedor(List<Proveedor> listaProveedor) {
    this.listaProveedor = listaProveedor;
  }

  /**
   * @return the listaDocumentosLazyModel
   */
  public LazyDataModel<DocumentoIncontermDTO> getListaDocumentosLazyModel() {
    return listaDocumentosLazyModel;
  }

  /**
   * @param listaDocumentosLazyModel the listaDocumentosLazyModel to set
   */
  public void setListaDocumentosLazyModel(LazyDataModel<DocumentoIncontermDTO> listaDocumentosLazyModel) {
    this.listaDocumentosLazyModel = listaDocumentosLazyModel;
  }

  /**
   * Implementacion de LazyModel
   */
  private class LazyDocumentoDataModel extends LazyDataModel<DocumentoIncontermDTO> {

    /**
     *
     */
    private static final long serialVersionUID = 283497341126330045L;
    private List<DocumentoIncontermDTO> datos;

    @Override
    public Object getRowKey(DocumentoIncontermDTO pi) {
      LOGGER.trace("Metodo: <<getRowKey>>");
      return pi.getIdDocumento();
    }

    @Override
    public DocumentoIncontermDTO getRowData(String rowKey) {
      LOGGER.trace("Metodo: <<getRowData>>");
      for (DocumentoIncontermDTO pi : datos) {
        if (pi.getIdDocumento().toString().equals(rowKey)) {
          return pi;
        }
      }
      return null;
    }

    @Override
    public List<DocumentoIncontermDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
      LOGGER.trace("Metodo: <<load>>");
      Object rta[] = comercioEjb.consultarDocumentosGeneral(filtro, first, pageSize, sortField, sortOrder, true);
      this.setRowCount(((BigInteger) rta[0]).intValue());
      rta = comercioEjb.consultarDocumentosGeneral(filtro, first, pageSize, sortField, sortOrder, false);
      datos = (List<DocumentoIncontermDTO>) rta[1];
      return datos;
    }

  }

}
