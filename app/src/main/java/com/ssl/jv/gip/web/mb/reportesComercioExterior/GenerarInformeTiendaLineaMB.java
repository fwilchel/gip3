package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ProductosInformeTiendaLineaDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

@ViewScoped
@ManagedBean(name = "generarInformeTiendaLineaMB")
public class GenerarInformeTiendaLineaMB extends UtilMB {

  /**
   * <p>
   * Title: GIP
   * </p>
   * <p>
   * Description: GIP
   * </p>
   * <p>
   * Copyright: Copyright (c) 2014
   * </p>
   * <p>
   * Company: Soft Studio Ltda.
   * </p>
   *
   * @author Diego Poveda
   * @email dpoveda@softstudio.co
   * @phone 3192594013
   * @version 1.0
   */
  private static final long serialVersionUID = 5283083824411596113L;

  @EJB
  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
  private static final Logger LOGGER = Logger.getLogger(GenerarInformeTiendaLineaMB.class);
  private FiltroDocumentoDTO filtroDocumentoDTO;
  private List<Documento> listaFacturasExportacion;
  private Documento seleccionado;
  private StreamedContent reporteExcel;

  @PostConstruct
  public void init() {
    LOGGER.trace("Metodo: <<init>>");
    try {
      filtroDocumentoDTO = new FiltroDocumentoDTO();
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
    }
  }

  public void consultarFacturasExportacion() {
    LOGGER.trace("Metodo: <<consultarFacturasExportacion>>");
    try {
      filtroDocumentoDTO.setTipoDocumento((long) ConstantesTipoDocumento.LISTA_EMPAQUE);
      filtroDocumentoDTO.setEstado(Estado.ASIGNADA.getCodigo());
      filtroDocumentoDTO.setSolicitudCafe(true);
      filtroDocumentoDTO.setOrdenCampo("id");
      this.listaFacturasExportacion = this.reportesComercioExteriorEJBLocal.consultarDocumentosPorTipoDocumentoEstadoTipoCafe(filtroDocumentoDTO);
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
    }
  }

  public StreamedContent getReporteExcel() {
    LOGGER.trace("Metodo: <<getReporteExcel>>");
    List<ProductosInformeTiendaLineaDTO> registros = reportesComercioExteriorEJBLocal.consultarProductosPorListaEmpaque(seleccionado.getId());
    Map<String, Object> parametrosReporte = new HashMap<String, Object>();
    parametrosReporte.put("datos", registros);
    try {
      Hashtable<String, String> parametrosConfigReporte = new Hashtable<String, String>();
      parametrosConfigReporte.put("tipo", "jxls");
      String nombreReporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/ReporteTiendaLinea.xls");
      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfigReporte, nombreReporte, null, null, null, parametrosReporte, null);
      reporteExcel = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel", "ReporteTiendaLinea" + seleccionado.getConsecutivoDocumento() + ".xls");
    } catch (Exception e) {
      this.addMensajeError("Ocurrio un error al momento de generar el reporte.");
    }
    return reporteExcel;
  }

  public FiltroDocumentoDTO getFiltroDocumentoDTO() {
    return filtroDocumentoDTO;
  }

  public void setFiltroDocumentoDTO(FiltroDocumentoDTO filtroDocumentoDTO) {
    this.filtroDocumentoDTO = filtroDocumentoDTO;
  }

  public List<Documento> getListaFacturasExportacion() {
    return listaFacturasExportacion;
  }

  public void setListaFacturasExportacion(List<Documento> listaFacturasExportacion) {
    this.listaFacturasExportacion = listaFacturasExportacion;
  }

  public Documento getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(Documento seleccionado) {
    this.seleccionado = seleccionado;
  }
}
