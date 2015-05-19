package com.ssl.jv.gip.web.mb.reportes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.negocio.dto.ReporteVentaDTO;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.mb.util.exportarExcel;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJB;

/**
 * <p>
 * Title: Imprimir Reporte Ventas FE MB</p>
 *
 * <p>
 * Description: ManagedBean para Imprimir Reporte Ventas FE MB </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Procafecol</p>
 *
 * @author John Heredia
 * @email jherediab@gmail.com
 * @phone 321 2024867
 * @version 1.0
 */
@ManagedBean(name = "imprimirReporteVentasFEMB")
@SessionScoped
public class ImprimirReporteVentasFEMB extends UtilMB {

  private List<ReporteVentaDTO> list;
  private StreamedContent reportePDF;
  private String strFechaInicial;
  private String strFechaFinal;

  private Date filtroFechaInicial;
  private Date filtroFechaFinal;

  @EJB
  private VentasFacturacionEJB ventasFacturacionEjb;

  public StreamedContent getReporteXLS() throws FileNotFoundException {
    Map<String, Object> parametros = new HashMap<String, Object>();

    parametros.put("tipo", ConstantesTipoDocumento.FACTURA_ESPECIAL);
   // parametros.put("fechaInicial", strFechaInicial);
    // parametros.put("fechaFinal", strFechaFinal);

    //parametros.put("fechaInicial", filtroFechaInicial);
    //parametros.put("fechaFinal",  filtroFechaFinal);
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    parametros.put("fechaInicial", formatoFecha.format(filtroFechaInicial));
    parametros.put("fechaFinal", formatoFecha.format(filtroFechaFinal));

    System.out.println("fechaIni_1: " + formatoFecha.format(filtroFechaInicial));
    System.out.println("fechaFin_1: " + formatoFecha.format(filtroFechaFinal));

    list = ventasFacturacionEjb.consultarReporteVentasFE(parametros);

    System.out.println("lista reporte ventas" + list.size());

    /* JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
     try {
					
     Hashtable<String, String> parametrosR=new Hashtable<String, String>();
     parametrosR.put("tipo", "xls");
     String reporte=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/report_CintaMagnetica.jasper");
     ByteArrayOutputStream os=(ByteArrayOutputStream)com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros2, ds);
     reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "reporteCintaMagnetica.pdf");
					
					
     } catch (Exception e) {
     this.addMensajeError(e);
     }*/
    hacerExcel();

    return reportePDF;
  }

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
      HSSFRow r1 = null, r2 = null, r3 = null, r4 = null, r5 = null, r6 = null, r7 = null, r3a = null, r8 = null;
      // declare a cell object reference
      HSSFCell c = null;
      HSSFCell c0 = null, c1 = null, c2 = null, c3 = null, c4 = null, c5 = null, c6 = null, c7 = null, c8 = null, c9 = null, c10 = null;
      HSSFCell c11 = null, c12 = null, c12a = null, c13 = null, c14 = null, c15 = null, c16 = null, c17 = null, c18 = null, c19 = null, c12b = null, c20 = null, c21 = null, c22 = null, c23 = null, c24 = null;

      HSSFCellStyle cs = wb.createCellStyle();
      HSSFCellStyle cs2 = wb.createCellStyle();
      HSSFCellStyle cs3 = wb.createCellStyle();
      HSSFCellStyle cs4 = wb.createCellStyle();
      HSSFCellStyle cs5 = wb.createCellStyle();
      HSSFCellStyle cs6 = wb.createCellStyle();
      HSSFCellStyle cs7 = wb.createCellStyle();
      HSSFCellStyle cs8 = wb.createCellStyle();

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

      HSSFFont f5 = wb.createFont();// Tipo deletra para informacion

      HSSFFont f6 = wb.createFont();// Tipo deletra para informacion

      HSSFFont f7 = wb.createFont();// Tipo deletra para informacion
      // adicional

      // set font 1 to 12 point type
      f.setFontHeightInPoints((short) 10);
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

      // set font 2 to 10 point type
      f5.setFontHeightInPoints((short) 8);
      // make it negro
      f5.setColor(HSSFFont.COLOR_NORMAL);
      // make it normal
      f5.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

      f6.setFontHeightInPoints((short) 9);
      // make it negro
      f6.setColor((short) 0x10);
      // make it normal
      f6.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

      f7.setFontHeightInPoints((short) 10);
      // make it negro
      f7.setColor(HSSFFont.COLOR_NORMAL);
      // make it normal
      f7.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

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

      cs5.setFont(f5);
      cs5.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

      cs6.setFont(f6);
      cs6.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

      cs7.setFont(f7);
      cs7.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      cs7.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

      cs8.setFont(f7);
      cs8.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      cs8.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/dd/yy"));

      ReporteVentaDTO objproducto = new ReporteVentaDTO();
      int cont = 0;

      HSSFSheet s;

      // crear una nueva hoja con sus propiedades
      s = wb.createSheet("Reporte de Consumos de Servicio");

      s.setHorizontallyCenter(true);
      s.setMargin((short) 0, 0.1);
      s.setMargin((short) 1, 0.1);
      s.setMargin((short) 2, 1);
      s.setMargin((short) 3, 1);

      // Inicia escritura de archivo excel por lineas
      // Fila 0
      r1 = s.createRow((short) 0);
      c11 = r1.createCell((short) 0);
      c11.setCellValue("REPORTE DE CONSUMOS DE SERVICIO");
      //MergeCellsRecord merge = new MergeCellsRecord();
      //merge.addArea(0, (short) 0, 0, (short) 8);
      c11.setCellStyle(cs3);

      // Fila 1
      r2 = s.createRow((short) 1);
      c12b = r2.createCell((short) 0);
      c12b.setCellValue("Fecha Inicial: ");
      //MergeCellsRecord merge11 = new MergeCellsRecord();
      //merge11.addArea(2, (short) 0, 2, (short) 5);
      c12b.setCellStyle(cs6);

      SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

      c17 = r2.createCell((short) 1);
      c17.setCellValue(formatoFecha.format(filtroFechaInicial));
      //MergeCellsRecord merge9 = new MergeCellsRecord();
      //merge9.addArea(4, (short) 1, 4, (short) 4);
      c17.setCellStyle(cs7);

      // Fila 2
      r3 = s.createRow((short) 2);
      c12 = r3.createCell((short) 0);
      c12.setCellValue("Fecha Final: ");
      //MergeCellsRecord merge1b = new MergeCellsRecord();
      //merge11.addArea(2, (short) 0, 2, (short) 5);
      c12.setCellStyle(cs6);

      c17 = r3.createCell((short) 1);
      c17.setCellValue(formatoFecha.format(filtroFechaFinal));
      //MergeCellsRecord merge10 = new MergeCellsRecord();
      //merge10.addArea(4, (short) 1, 4, (short) 4);
      c17.setCellStyle(cs7);

      // Fila 5
      r = s.createRow((short) 5);
      c0 = r.createCell((short) 0);
      c0.setCellValue("FECHA");
      c1 = r.createCell((short) 1);
      c1.setCellValue("NIT CLIENTE");
      c2 = r.createCell((short) 2);
      c2.setCellValue("NOMBRE CLIENTE");
      c3 = r.createCell((short) 3);
      c3.setCellValue("CONSECUTIVO VD");
      c4 = r.createCell((short) 4);
      c4.setCellValue("CONSECUTIVO RM");
      c5 = r.createCell((short) 5);
      c5.setCellValue("CONSECUTIVO FACTURA");
      c6 = r.createCell((short) 6);
      c6.setCellValue("NUMERO FACTURA");
      c7 = r.createCell((short) 7);
      c7.setCellValue("SKU PRODUCTO");
      c8 = r.createCell((short) 8);
      c8.setCellValue("DESCRIPCION PRODUCTO");
      c9 = r.createCell((short) 9);
      c9.setCellValue("CANTIDAD VD");
      c10 = r.createCell((short) 10);
      c10.setCellValue("CANTIDAD FACTURADA");
      c11 = r.createCell((short) 11);
      c11.setCellValue("VALOR UNITARIO");
      c12 = r.createCell((short) 12);
      c12.setCellValue("VALOR TOTAL");

      c0.setCellStyle(cs);
      c1.setCellStyle(cs);
      c2.setCellStyle(cs);
      c3.setCellStyle(cs);
      c4.setCellStyle(cs);
      c5.setCellStyle(cs);
      c6.setCellStyle(cs);
      c7.setCellStyle(cs);
      c8.setCellStyle(cs);
      c9.setCellStyle(cs);
      c10.setCellStyle(cs);
      c11.setCellStyle(cs);
      c12.setCellStyle(cs);

      //double valorDescXPrd = 0;
      //double valorOtrosDescs = 0;
      //double valorIVA=0.0 ;
      double valorUnitario = 0;
      double valorSubtotal = 0;
      //double valorDescCliente = 0;
      //double valorBaseIVA=0;
      //double porceIVA = 0;
      //double valorTotal =0.0;

      for (Integer row = new Integer(6); row < list.size() + 6; row++) {

        r = s.createRow(row.shortValue());
        objproducto = (ReporteVentaDTO) list.get(cont);

        valorUnitario = Math.round(objproducto.getValorUnitario().doubleValue());

        valorSubtotal = Math.round(valorUnitario * objproducto.getCantidadFD().doubleValue());

        /*DecimalFormat dosDecimales = new DecimalFormat("#,##0.00");
         valorIVA = Double.valueOf(dosDecimales.format(valorIVA));
         valorTotal = Double.valueOf(dosDecimales.format(valorTotal));
         */
        c = r.createCell((short) 0);

        c.setCellValue(objproducto.getFechaGeneracion().toString());
        c.setCellStyle(cs2);

        c = r.createCell((short) 1);
        c.setCellValue(objproducto.getNitCliente());
        c.setCellStyle(cs2);

        c = r.createCell((short) 2);
        c.setCellValue(objproducto.getNombreCliente());
        c.setCellStyle(cs2);

        c = r.createCell((short) 3);
        c.setCellValue(objproducto.getConsecutivoDocumentoVD());
        c.setCellStyle(cs2);

        c = r.createCell((short) 4);
        c.setCellValue(objproducto.getConsecutivoDocumentoRM());
        c.setCellStyle(cs2);

        c = r.createCell((short) 5);
        c.setCellValue(objproducto.getConsecutivoDocumentoFD());
        c.setCellStyle(cs2);

        c = r.createCell((short) 6);
        c.setCellValue(objproducto.getNumeroFactura());
        c.setCellStyle(cs2);

        c = r.createCell((short) 7);
        c.setCellValue(objproducto.getSku());
        c.setCellStyle(cs2);

        c = r.createCell((short) 8);
        c.setCellValue(objproducto.getNombreProducto());
        c.setCellStyle(cs2);

        c = r.createCell((short) 9);
        c.setCellValue(objproducto.getCantidadVD().doubleValue());
        c.setCellStyle(cs2);

        c = r.createCell((short) 10);
        c.setCellValue(objproducto.getCantidadFD().doubleValue());
        c.setCellStyle(cs2);

        c = r.createCell((short) 11);
        c.setCellValue(valorUnitario);
        c.setCellStyle(cs2);

        c = r.createCell((short) 12);
        c.setCellValue(valorSubtotal);
        c.setCellStyle(cs2);

        cont += 1;

        // make this column a bit wider
        s.setColumnWidth((short) (0),
            (short) ((30 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (1),
            (short) ((20 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (2),
            (short) ((60 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (3),
            (short) ((28 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (4),
            (short) ((28 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (5),
            (short) ((40 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (6),
            (short) ((30 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (7),
            (short) ((25 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (8),
            (short) ((50 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (9),
            (short) ((22 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (10),
            (short) ((35 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (11),
            (short) ((27 * 8) / ((double) 1 / 20)));
        s.setColumnWidth((short) (12),
            (short) ((25 * 8) / ((double) 1 / 20)));

      }

      String DATE_FORMAT = "yyyy-MM-dd";
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      Calendar fech = Calendar.getInstance(); //Fecha y Tiempo actual
      String datatime = sdf.format(fech.getTime());

      // Envia por doGet el workbook y lo abre.
      exportarExcel pe = new exportarExcel();
      try {
        pe.doGet(request, response, wb, "ReporteConsumoServicio" + "-" + datatime);
      } catch (ServletException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      context.responseComplete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getStrFechaInicial() {
    return strFechaInicial;
  }

  public void setStrFechaInicial(String strFechaInicial) {
    this.strFechaInicial = strFechaInicial;
  }

  public String getStrFechaFinal() {
    return strFechaFinal;
  }

  public void setStrFechaFinal(String strFechaFinal) {
    this.strFechaFinal = strFechaFinal;
  }

  public List<ReporteVentaDTO> getList() {
    return list;
  }

  public void setList(List<ReporteVentaDTO> list) {
    this.list = list;
  }

  /**
   * @return the filtroFechaInicial
   */
  public Date getFiltroFechaInicial() {
    return filtroFechaInicial;
  }

  /**
   * @param filtroFechaInicial the filtroFechaInicial to set
   */
  public void setFiltroFechaInicial(Date filtroFechaInicial) {
    this.filtroFechaInicial = filtroFechaInicial;
  }

  /**
   * @return the filtroFechaFinal
   */
  public Date getFiltroFechaFinal() {
    return filtroFechaFinal;
  }

  /**
   * @param filtroFechaFinal the filtroFechaFinal to set
   */
  public void setFiltroFechaFinal(Date filtroFechaFinal) {
    this.filtroFechaFinal = filtroFechaFinal;
  }

}
