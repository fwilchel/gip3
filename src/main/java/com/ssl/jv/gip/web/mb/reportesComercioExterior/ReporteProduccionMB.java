package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ReporteProduccionDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

@ManagedBean(name = "reporteProduccionCeMB")
@ViewScoped
public class ReporteProduccionMB extends UtilMB {

  /**
	 * 
	 */
  private static final long serialVersionUID = -2245622085667338473L;

  @EJB
  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;

  private Date fechaInicial;

  private Date fechaFinal;

  private boolean esSolicitudCafe;

  private FiltroDocumentoDTO filtroDocumentoDTO;

  private StreamedContent reporteExcel;

  private List<Documento> listaFacturasExportacion;

  private List<ProductosXDocumento> listaProductosxDocumento;

  @PostConstruct
  public void init() {

  }

  private static final Logger LOGGER = Logger.getLogger(ReporteProduccionMB.class);

  public Date getFechaInicial() {
	return fechaInicial;
  }

  public void setFechaInicial(Date fechaInicial) {
	this.fechaInicial = fechaInicial;
  }

  public Date getFechaFinal() {
	return fechaFinal;
  }

  public void setFechaFinal(Date fechaFinal) {
	this.fechaFinal = fechaFinal;
  }

  public boolean isEsSolicitudCafe() {
	return esSolicitudCafe;
  }

  public void setEsSolicitudCafe(boolean esSolicitudCafe) {
	this.esSolicitudCafe = esSolicitudCafe;
  }

  public StreamedContent getReporteExcel() {
	Map<String, Object> parametros = new HashMap<String, Object>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	parametros.put("fecha_inicial", sdf.format(fechaInicial));
	parametros.put("fecha_final", sdf.format(fechaFinal));
	List<ReporteProduccionDTO> registros = new ArrayList<ReporteProduccionDTO>();
	try {
	  filtroDocumentoDTO = new FiltroDocumentoDTO();
	  filtroDocumentoDTO.setTipoDocumento((long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
	  filtroDocumentoDTO.setFechaGeneracionInicio(fechaInicial);
	  filtroDocumentoDTO.setFechaGeneracionFin(fechaFinal);
	  filtroDocumentoDTO.setSolicitudCafe(esSolicitudCafe);
	  filtroDocumentoDTO.setEstado(Estado.IMPRESO.getCodigo());
	  filtroDocumentoDTO.setOrdenCampo("fechaGeneracion");
	  // filtroDocumentoDTO.setIdTipoDocumento((long)
	  // ConstantesTipoDocumento.FACTURA_EXPORTACION);
	  // filtroDocumentoDTO.setFechaInicio(fechaInicial);
	  // filtroDocumentoDTO.setFechaFin(fechaFinal);
	  // filtroDocumentoDTO.setSolicitudCafe(esSolicitudCafe);
	  // filtroDocumentoDTO.setIdEstado(Estado.IMPRESO.getCodigo());
	  this.listaFacturasExportacion = this.reportesComercioExteriorEJBLocal.consultarDocumentosPorTipoDocumentoEstadoSolicitudCafeFechas(filtroDocumentoDTO);
	  this.listaProductosxDocumento = new ArrayList<ProductosXDocumento>();
	  for (Documento doc : listaFacturasExportacion) {
		this.listaProductosxDocumento.addAll(this.reportesComercioExteriorEJBLocal.consultarProductosPorDocumento(doc.getId()));
		for (ProductosXDocumento prod : listaProductosxDocumento) {
		  ReporteProduccionDTO registro = new ReporteProduccionDTO();
		  registro.setCantidad1(prod.getCantidad1().doubleValue());
		  registro.setClNombre(doc.getCliente().getNombre());
		  registro.setClNombrePais(doc.getCliente().getCiudad().getPais().getNombre());
		  registro.setClNombreTipoCanal(doc.getCliente().getTipoCanal().getNombre());
		  registro.setDocConsecutivo(doc.getConsecutivoDocumento());
		  registro.setPiNombre(prod.getProductosInventario().getNombre());
		  registro.setPiSKU(prod.getProductosInventario().getSku());
		  registro.setUbicacion(doc.getUbicacionDestino().getNombre());
		  registros.add(registro);
		}
	  }
	} catch (Exception e) {
	  LOGGER.error(e.getMessage());
	}
	JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(registros);
	try {
	  Hashtable<String, String> parametrosR = new Hashtable<String, String>();
	  parametrosR.put("tipo", "xls");
	  String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_PCE.jasper");
	  ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
	  reporteExcel = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/x-msexcel", "Reporte_PCE.xls");
	} catch (Exception e) {
	  this.addMensajeError(e);
	}
	return reporteExcel;
  }

  public void setReporteExcel(StreamedContent reporteExcel) {
	this.reporteExcel = reporteExcel;
  }

}
