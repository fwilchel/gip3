package com.ssl.jv.gip.web.mb.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.CintaMagneticaDTO;
import com.ssl.jv.gip.negocio.dto.ComprobanteInformeDiarioDTO;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJB;

/**
 * <p>
 * Title: ImprimirComprobanteInformeDiarioMB</p>
 *
 * <p>
 * Description: ManagedBean para Imprimir Comprobante de Informe DiarioMB</p>
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
@ManagedBean(name = "imprimirComprobanteInformeDiarioMB")
@SessionScoped
public class ImprimirComprobanteInformeDiarioMB extends UtilMB {

  private List<CintaMagneticaDTO> list;
  private StreamedContent reportePDF;
  private String strFechaInicial;
  private String strFechaFinal;
  private List<ComprobanteInformeDiarioDTO> list2;

  @EJB
  private VentasFacturacionEJB ventasFacturacionEjb;

  public List<CintaMagneticaDTO> consultarDocumento() {
    Map<String, Object> parametros = new HashMap<String, Object>();
    strFechaInicial = this.strFechaInicial + " 00:00:00";
    strFechaFinal = this.strFechaFinal + " 23:59:59";

    //System.out.println("tipo"+  ConstantesTipoDocumento.FACTURA);
    //System.out.println("strFechaInicial"+ strFechaInicial);
    //System.out.println("strFechaFinal"+ strFechaFinal);
    parametros.put("tipo", ConstantesTipoDocumento.FACTURA);
    parametros.put("fechaInicial", strFechaInicial);
    parametros.put("fechaFinal", strFechaFinal);
    list = ventasFacturacionEjb.consultarCintaTestigoMagnetica(parametros);

    //System.out.println("lista cinta Magnetica"+list.size());
    return list;
  }

  public StreamedContent getReportePDF() {
    Map<String, Object> parametros = new HashMap<String, Object>();

    String strFechaIni = this.strFechaInicial + " 00:00:00";
    String strFechaFin = this.strFechaFinal + " 23:59:59";
    String consecIni = "";
    String consecFin = "";
    Integer sizeList = 0;

    Double resSuma1 = 0.0;
    Double resSuma2 = 0.0;
    Double resSuma3 = 0.0;
    Double resSuma4 = 0.0;
    Double resSuma5 = 0.0;
    Double resSuma6 = 0.0;
    Double resSuma7 = 0.0;

    /* System.out.println("tipo"+  ConstantesTipoDocumento.FACTURA);
     System.out.println("strFechaInicial"+ strFechaInicial);
     System.out.println("strFechaFinal"+ strFechaFinal);
     */
    parametros.put("tipo", ConstantesTipoDocumento.FACTURA);
    parametros.put("fechaInicial", strFechaIni);
    parametros.put("fechaFinal", strFechaFin);

    list = ventasFacturacionEjb.consultarCintaTestigoMagnetica(parametros);

    list2 = ventasFacturacionEjb.consultarComprobanteInformeDiario(parametros);

    sizeList = list.size();

    CintaMagneticaDTO doc = new CintaMagneticaDTO();
    doc = (CintaMagneticaDTO) list.get(0);
    consecIni = doc.getConsecutivoDocumento();

    doc = (CintaMagneticaDTO) list.get(sizeList - 1);
    consecFin = doc.getConsecutivoDocumento();

    Map<String, Object> parametros2 = new HashMap<String, Object>();
    parametros2.put("size", sizeList);
    parametros2.put("cIni", consecIni);
    parametros2.put("cFin", consecFin);
    parametros2.put("fechaIni", this.strFechaInicial);
    parametros2.put("fechaFin", this.strFechaFinal);
    parametros2.put("ubicacion", "Oficina Central");

    /*	System.out.println("size"+ sizeList);
     System.out.println("cIni"+ consecIni);
     System.out.println("cFin"+ consecFin);
     System.out.println("fechaIni"+ this.strFechaInicial );
     System.out.println("fechaFin"+ this.strFechaFinal);		 
     System.out.println("ubicacion"+ "Oficina Central");
     */
    ComprobanteInformeDiarioDTO doc2 = new ComprobanteInformeDiarioDTO();

    for (int i = 0; i < list2.size(); i++) {
      doc2 = (ComprobanteInformeDiarioDTO) list2.get(i);
      resSuma1 = resSuma1 + doc2.getValorTotal().doubleValue();
    }

    for (int n = 0; n < list.size(); n++) {
      doc = (CintaMagneticaDTO) list.get(n);
      resSuma2 = resSuma2 + doc.getValorDescuento().doubleValue();
      resSuma3 = resSuma3 + doc.getValorIva16().doubleValue();
      resSuma4 = resSuma4 + doc.getValorIva10().doubleValue();

    }

    resSuma5 = resSuma1 - resSuma2;
    resSuma6 = resSuma3 + resSuma4;
    resSuma7 = resSuma5 + resSuma6;

    parametros2.put("suma1", resSuma1);
    parametros2.put("suma2", resSuma2);
    parametros2.put("suma3", resSuma3);
    parametros2.put("suma4", resSuma4);
    parametros2.put("suma5", resSuma5);
    parametros2.put("suma6", resSuma6);
    parametros2.put("suma7", resSuma7);

    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list2);
    try {

      Hashtable<String, String> parametrosR = new Hashtable<String, String>();
      parametrosR.put("tipo", "pdf");
      String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/report_ComprobanteInfDiario.jasper");
      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros2, ds);
      reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "ComprobanteInfDiario.pdf");

    } catch (Exception e) {
      this.addMensajeError(e);
    }

    return reportePDF;
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

  public List<CintaMagneticaDTO> getList() {
    return list;
  }

  public void setList(List<CintaMagneticaDTO> list) {
    this.list = list;
  }

  public List<ComprobanteInformeDiarioDTO> getList2() {
    return list2;
  }

  public void setList2(List<ComprobanteInformeDiarioDTO> list2) {
    this.list2 = list2;
  }

}
