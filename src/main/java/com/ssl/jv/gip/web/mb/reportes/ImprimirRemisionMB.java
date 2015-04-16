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
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJB;

/**
 * <p>
 * Title: ImprimirRemisionMB</p>
 *
 * <p>
 * Description: ManagedBean para las imprimir Remision</p>
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
@ManagedBean(name = "imprimirRemisionMB")
@SessionScoped
public class ImprimirRemisionMB extends UtilMB {

  private List<Documento> list;
  private StreamedContent reportePDF;

  public List<Documento> getList() {
    return list;
  }

  public void setList(List<Documento> list) {
    this.list = list;
  }

  private String strConsecutivoDocumento;
  private Documento seleccionado;
  private FacturaDirectaDTO seleccionado2;

  public FacturaDirectaDTO getSeleccionado2() {
    return seleccionado2;
  }

  public void setSeleccionado2(FacturaDirectaDTO seleccionado2) {
    this.seleccionado2 = seleccionado2;
  }

  private List<ProductoFacturaDirectaDTO> listaDetalle;

  private List<ProductoLoteAsignarLoteOICDTO> lotes;

  public List<ProductoLoteAsignarLoteOICDTO> getLotes() {
    return lotes;
  }

  public void setLotes(List<ProductoLoteAsignarLoteOICDTO> lotes) {
    this.lotes = lotes;
  }

  public List<ProductoFacturaDirectaDTO> getListaDetalle() {
    return listaDetalle;
  }

  public void setListaDetalle(List<ProductoFacturaDirectaDTO> listaDetalle) {
    this.listaDetalle = listaDetalle;
  }

  public Documento getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(Documento seleccionado) {

    this.seleccionado = seleccionado;
    this.setSeleccionado2(this.ventasFacturacionEjb.consultarDocumentoFacturaDirecta(seleccionado.getConsecutivoDocumento()));
    listaDetalle = this.ventasFacturacionEjb.consultarProductoFacturaDirecta(seleccionado.getConsecutivoDocumento());

    System.out.println("doc: " + seleccionado.getId());
    System.out.println("cliente: " + seleccionado.getCliente().getId());

    //this.getReportePDF();
  }

  public String getStrConsecutivoDocumento() {
    return strConsecutivoDocumento;
  }

  public void setStrConsecutivoDocumento(String strConsecutivoDocumento) {
    this.strConsecutivoDocumento = strConsecutivoDocumento;
  }

  @EJB
  private VentasFacturacionEJB ventasFacturacionEjb;

  @EJB
  private ComercioExteriorEJB comercioEjb;

  public List<Documento> consultarDocumento() {
    Map<String, Object> parametros = new HashMap<String, Object>();

    // Se Define la cadena de busqueda del nombre segun lo ingresado en el
    // campo de Consecutivo Pedido. 
    String parametroConseDoc;
    if (this.strConsecutivoDocumento.equals("")) {
      parametroConseDoc = "%";
    } else {
      parametroConseDoc = "%" + this.strConsecutivoDocumento + "%";
    }

    parametros.put("tipo", ConstantesTipoDocumento.REMISION);

    Long[] array = new Long[1];
    array[0] = (long) ConstantesDocumento.PENDIENTE_POR_RECIBIR;
    parametros.put("estado", array);

    parametros.put("parametroConseDoc", parametroConseDoc);

    list = ventasFacturacionEjb.consultarDocumento(parametros, array);

    return list;
  }

  public StreamedContent getReportePDF() {
    Map<String, Object> parametros = new HashMap<String, Object>();

    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
    String fechaStringGeneracion = ft.format(seleccionado2.getFechaGeneracion());
    String fechaStringGeneracionVD = ft.format(seleccionado2.getFechaGeneracionVD());

    parametros.put("cliente", seleccionado2.getNombreCliente());
    parametros.put("nit", seleccionado2.getNitCliente());
    parametros.put("ciudad", seleccionado2.getNombreCiudadCliente());
    parametros.put("direccion", seleccionado2.getDireccionCliente());
    parametros.put("telefono", seleccionado2.getTelefonoCliente());
    parametros.put("numRemision", seleccionado2.getConsecutivoDocumento());
    parametros.put("numVD", seleccionado.getObservacionDocumento());

    parametros.put("despachado_a", seleccionado2.getNombrePuntoVenta());
    parametros.put("direccionpv", seleccionado2.getDireccionPuntoVenta());
    parametros.put("telefonopv", "Teléfono: " + seleccionado2.getTelefonoPuntoVenta());
    parametros.put("ciudadpv", seleccionado2.getNombreCiudadPuntoVenta());
    parametros.put("documento", seleccionado.getDocumentoCliente());
    parametros.put("fecha", fechaStringGeneracion);
    parametros.put("fechaVentaDirecta", fechaStringGeneracionVD);

    //System.out.println("contacto"+ c.getContacto());
    //parametros.put("id_documento", seleccionado.getConsecutivoDocumento());
    parametros.put("despachado_a", seleccionado2.getNombrePuntoVenta());
    parametros.put("direccionpv", seleccionado2.getDireccionPuntoVenta());
    parametros.put("telefonopv", "Teléfono: " + seleccionado2.getTelefonoPuntoVenta());
    parametros.put("ciudadpv", seleccionado2.getNombreCiudadPuntoVenta());
    parametros.put("documento", seleccionado.getDocumentoCliente());
    parametros.put("fecha", fechaStringGeneracion);
    parametros.put("fechaVentaDirecta", fechaStringGeneracion);

    /*System.out.println("cliente: "+seleccionado2.getNombreCliente());
     System.out.println("NIT: "+seleccionado2.getNitCliente());
     System.out.println("ciudad: "+seleccionado2.getNombreCiudadCliente());
     System.out.println("numFactura"+seleccionado.getConsecutivoDocumento());
     System.out.println("direccion"+ seleccionado2.getDireccionCliente());
     System.out.println("telefono"+seleccionado2.getTelefonoCliente());
     System.out.println("fecha"+fechaStringGeneracion);
     System.out.println("documento"+seleccionado.getDocumentoCliente());
     System.out.println("despachado_a"+seleccionado2.getNombrePuntoVenta());
				
     System.out.println("direccionpv"+seleccionado2.getDireccionPuntoVenta());
     System.out.println("telefonopv"+"Teléfono: " + seleccionado2.getTelefonoPuntoVenta());		 
     System.out.println("ciudadpv"+seleccionado2.getNombreCiudadPuntoVenta());
     System.out.println("documento"+seleccionado.getDocumentoCliente());
     System.out.println("fechaVentaDirecta"+fechaStringGeneracionVD);*/
    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaDetalle);


    /*
     if (n > 0){ //Existe algún registro con descuento adicioanl
     parametros.put("descuentoCliente",seleccionado2.getDescuentoCliente().toString()+ "%");
     parametros.put("observaciones", "Los productos marcados con (*) incluyen descuento adicional.");
     parametros.put("mark", "(*)");				 
     }
     else{
     parametros.put("descuentoCliente","");
     parametros.put("observaciones", "");
     parametros.put("mark", "");				 
     }		 
			 
     */
    try {
      Hashtable<String, String> parametrosR = new Hashtable<String, String>();
      parametrosR.put("tipo", "pdf");

      String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/report_Remision.jasper");

      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
      reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "remision.pdf");
    } catch (Exception e) {
      this.addMensajeError(e);
    }

    return reportePDF;
  }

}
