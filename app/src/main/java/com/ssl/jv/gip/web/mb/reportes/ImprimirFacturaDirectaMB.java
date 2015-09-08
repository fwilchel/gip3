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
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

import com.ssl.jv.gip.web.util.Numero_a_Letra;

import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJB;

/**
 * <p>
 * Title: ImprimirFacturaDirectaMB
 * </p>
 *
 * <p>
 * Description: ManagedBean para las imprimir Factura Directa
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 *
 * <p>
 * Company: Procafecol
 * </p>
 *
 * @author John Heredia
 * @email jherediab@gmail.com
 * @phone 321 2024867
 * @version 1.0
 */
@ManagedBean(name = "imprimirFacturaDirectaMB")
@SessionScoped
public class ImprimirFacturaDirectaMB extends UtilMB {

  @EJB
  private VentasFacturacionEJB ventasFacturacionEjb;
  private List<Documento> list;
  private StreamedContent reportePDF;
  private String consecutivoDocumento;
  private Documento fd;
  private FacturaDirectaDTO fdDTO;
  private List<ProductoFacturaDirectaDTO> listaDetalle;
  private List<ProductoLoteAsignarLoteOICDTO> lotes;

  public List<Documento> consultarDocumento() {
    Map<String, Object> parametros = new HashMap<>();
    String parametroConseDoc;
    if (this.consecutivoDocumento.equals("")) {
      parametroConseDoc = "%";
    } else {
      parametroConseDoc = "%" + this.consecutivoDocumento + "%";
    }
    parametros.put("tipo", ConstantesTipoDocumento.FACTURA);
    Long[] array = new Long[2];
    array[0] = (long) ConstantesDocumento.IMPRESO;
    array[1] = (long) ConstantesDocumento.ANULADO;
    parametros.put("estado", array);
    parametros.put("parametroConseDoc", parametroConseDoc);
    list = ventasFacturacionEjb.consultarDocumento(parametros, array);
    return list;
  }

  public void setFd(Documento fd) {
    this.fd = fd;
    this.setFdDTO(this.ventasFacturacionEjb.consultarDocumentoFacturaDirecta(fd.getConsecutivoDocumento()));
    listaDetalle = this.ventasFacturacionEjb.consultarProductoFacturaDirecta(fd.getConsecutivoDocumento());
    System.out.println("doc: " + fd.getId());
    System.out.println("cliente: " + fd.getCliente().getId());
  }

  public StreamedContent getReportePDF() {
    Map<String, Object> parametros = new HashMap<>();
    int n = 0;
    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
    String fechaStringGeneracion = ft.format(fdDTO.getFechaGeneracion());
    parametros.put("cliente", fdDTO.getNombreCliente());
    parametros.put("nit", fdDTO.getNitCliente());
    parametros.put("ciudad", fdDTO.getNombreCiudadCliente());
    parametros.put("direccion", fdDTO.getDireccionCliente());
    parametros.put("telefono", fdDTO.getTelefonoCliente());
    parametros.put("numFactura", fdDTO.getConsecutivoDocumento());
    parametros.put("despachado_a", fdDTO.getNombrePuntoVenta());
    parametros.put("direccionpv", fdDTO.getDireccionPuntoVenta());
    parametros.put("telefonopv", "Teléfono: " + fdDTO.getTelefonoPuntoVenta());
    parametros.put("ciudadpv", fdDTO.getNombreCiudadPuntoVenta());
    parametros.put("documento", fd.getDocumentoCliente());
    parametros.put("fecha", fechaStringGeneracion);
    parametros.put("tipoImp", "Copia");
    //parametros.put("tipoImp", "Original");
    parametros.put("valorSubtotal", fdDTO.getValorSubtotal());
    parametros.put("valorDescuento", fdDTO.getValorDescuento());
    parametros.put("valorIva5", fdDTO.getValorIva5());
    parametros.put("valorIva16", fdDTO.getValorIva16());
    parametros.put("valorTotal", fdDTO.getValorTotal());
    Numero_a_Letra NumLetra = new Numero_a_Letra();
    parametros.put("valorLetras", NumLetra.Convertir(fdDTO.getValorTotal().toString(), true));
    if (fdDTO.getEstado() == ConstantesDocumento.ANULADO) {
      parametros.put("anulada", "ANULADA");
    } else {
      parametros.put("anulada", "");
    }
    for (ProductoFacturaDirectaDTO p : listaDetalle) {
      String mark = p.getMarca();
      if (mark.equals("(*)")) {
        n = n + 1;
      }
    }
    if (n > 0) { // Existe algún registro con descuento adicioanl
      parametros.put("descuentoCliente", fdDTO.getDescuentoCliente().toString() + "%");
      parametros.put("observaciones", "Los productos marcados con (*) incluyen descuento adicional.");
      parametros.put("mark", "(*)");
    } else {
      parametros.put("descuentoCliente", "");
      parametros.put("observaciones", "");
      parametros.put("mark", "");
    }
    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaDetalle);
    try {
      Hashtable<String, String> parametrosR = new Hashtable<>();
      parametrosR.put("tipo", "pdf");
      String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/report2.jasper");
      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
      reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", fdDTO.getConsecutivoDocumento() + "_copia.pdf");
    } catch (Exception e) {
      this.addMensajeError(e);
    }
    return reportePDF;
  }

  public List<Documento> getList() {
    return list;
  }

  public void setList(List<Documento> list) {
    this.list = list;
  }

  public FacturaDirectaDTO getFdDTO() {
    return fdDTO;
  }

  public void setFdDTO(FacturaDirectaDTO fdDTO) {
    this.fdDTO = fdDTO;
  }

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

  public Documento getFd() {
    return fd;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }
}
