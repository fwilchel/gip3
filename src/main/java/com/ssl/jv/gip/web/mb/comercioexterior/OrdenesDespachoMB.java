package com.ssl.jv.gip.web.mb.comercioexterior;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoODDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;

/**Managed Bean para ordenes de despacho
 * 
 * @author Daniel Cortes
 * @version 1.0
 * @email danicorc@gmail.com
 *
 */
@ManagedBean(name="ordenesDespachoMB")
@ViewScoped
public class OrdenesDespachoMB extends UtilMB{

	private static final long serialVersionUID = 1L;
	
	private Timestamp currentTimeStamp;
	private String consecutivoDocumento;
	private boolean deshabilitado=false;
	private List<Documento> documentos;
	private static List<ProductoODDTO> productos;
	private Documento seleccionado;
	private Documento filtro;
	private double totalCantidad=0;
	private double totalCantidadPorEmbalaje=0;
	private double totalCantidadCajas=0;
	private double muestrasFITOANTICO=0;
	private double muestrasCalidades=0;
	
	private JasperPrint jasperPrint;
	
	@EJB
	private ComercioExteriorEJBLocal comercioEjb;
	
	private Integer language=AplicacionMB.SPANISH;
	
	public OrdenesDespachoMB() {
	}

	@PostConstruct
	public void init(){
		currentTimeStamp = new Timestamp(System.currentTimeMillis());
	}

	public String buscarDocumentos() {
		documentos = this.comercioEjb.consultarFP(consecutivoDocumento,(long)ConstantesDocumento.APROBADA,(long)ConstantesDocumento.ASIGNADA);
		this.deshabilitado = false;
		return null;
	}
	
	public void consultarOrdenDeDespacho(){
		productos=comercioEjb.consultarProductoPorDocumentoOrdenDespacho(seleccionado.getId(),seleccionado.getCliente().getId(),seleccionado.getDocumentoXNegociacions().get(0).getSolicitudCafe());
		totalCantidad=0;
		totalCantidadCajas=0;
		totalCantidadPorEmbalaje=0;
		muestrasFITOANTICO=0;
		muestrasCalidades=0;
		for (ProductoODDTO p : productos) {
			this.totalCantidad+=p.getCantidad().doubleValue();
			this.totalCantidadCajas+=p.getCantidad().doubleValue()/p.getCantidadPorEmbalaje().doubleValue();
			this.totalCantidadPorEmbalaje+=p.getCantidadPorEmbalaje().doubleValue();
			this.muestrasCalidades+=p.getMuestrasCalidades().doubleValue();
			this.muestrasFITOANTICO+=p.getMuestrasFITOYANTICO().doubleValue();
		}
	}
	
	public void generarReportePDF() throws JRException, IOException{
			reportBuilder();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition",
					"attachment; filename="+seleccionado.getId()+".pdf");
			ServletOutputStream servletStream = httpServletResponse
					.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,
					servletStream);
			FacesContext.getCurrentInstance().responseComplete();
	}
	
	@SuppressWarnings("deprecation")
	public void generarReporteExcel() throws JRException, IOException{
		reportBuilder();
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=Employees_List.xlsx");
		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JRXlsxExporter docxExporter = new JRXlsxExporter();
		docxExporter
				.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
				servletOutputStream);
		docxExporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void generarReporteExcel2() throws JRException, IOException {

		try {

			// SERVIDOR LINUX
			String filename = "C:/A/jboss-as-7.1.1.Final/standalone/deployments/gip3.war/reportes/Report_OD.jasper";
			String filepdf = "C:/A/jboss-as-7.1.1.Final/standalone/deployments/gip3.war/reportes/Report_OD.pdf";
			String xmlfile = "C:/A/jboss-as-7.1.1.Final/standalone/deployments/gip3.war/reportes/Report_OD.jrxml";

			Map parametros = new HashMap();

			HttpServletResponse response = null;

			FacesContext context = null;

			context = FacesContext.getCurrentInstance();

			ExternalContext external = context.getExternalContext();
			response = (HttpServletResponse) external.getResponse();

			SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
			String fechaStringGeneracion = ft.format(seleccionado
					.getFechaGeneracion());

			String fechaStringCargue = ft
					.format(seleccionado.getFechaEntrega());

			parametros.put("cliente", seleccionado.getCliente().getNombre());
			parametros.put("nit", seleccionado.getCliente().getNit());
			parametros.put("ciudad", seleccionado.getCliente().getCiudad().getNombre());
			parametros.put("direccion", seleccionado.getCliente().getDireccion());
			parametros.put("telefono", seleccionado.getCliente().getTelefono());
			parametros.put("contacto", seleccionado.getCliente().getContacto());
			parametros.put("documento", seleccionado.getId()+"");
			parametros.put("fecha", seleccionado.getFechaGeneracion().toString());
			parametros.put("numFactura",seleccionado.getConsecutivoDocumento());
			parametros.put("fechaCargue", seleccionado.getFechaEntrega().toString());
			parametros.put("solicitud", seleccionado.getObservacionDocumento());
			parametros.put("observacionDoc",seleccionado.getObservacion2());
			parametros.put("observacionMar", seleccionado.getDocumentoXNegociacions()
					.get(0).getObservacionesMarcacion2());
			parametros.put("despacho",seleccionado.getSitioEntrega());

			JasperReport report = JasperCompileManager.compileReport(xmlfile);

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					productos);
			JasperPrint print = JasperFillManager.fillReport(report,
					parametros, ds);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			String fileName = "ReporteOD";
			// if (strboton.contentEquals("pdf") || strboton=="pdf")
			// {
			// net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream(print,baos);
			// response = Utilidad.configureResponse(response,
			// "Reporte_OD.pdf");
			// }
			// else
			// if (strboton.contentEquals("xls") || strboton=="xls")
			// {
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporterXLS.exportReport();

			response = configureResponse(response, "Reporte_OD.xls");
			// }

			response.setContentLength(baos.size());
			
			ServletOutputStream out = null;
			try {
				out = response.getOutputStream();
				baos.writeTo(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			baos.flush();
			context.responseComplete();
		} catch (JRException se) {
			se.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
//	private List<ProductoReporteDTO> mapearLista(List<ProductoODDTO> productos2) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public void reportBuilder() throws JRException {

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
				productos);

		String report = FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRealPath(
						"/reportes/Report_OD.jasper");

		jasperPrint = JasperFillManager.fillReport(report, new HashMap(),
				beanCollectionDataSource);

	}
	
	public static HttpServletResponse configureResponse(
			HttpServletResponse response, String fileName) {
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setContentType("application/pdf");
		response.addHeader("Content-disposition", "attachment; filename=\""
				+ fileName);
		return response;
	}

	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public boolean isDeshabilitado() {
		return deshabilitado;
	}

	public void setDeshabilitado(boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public List<ProductoODDTO> getProductos() {
		return productos;
	}

	public static void setProductos(List<ProductoODDTO> productos2) {
		productos = productos2;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
	}

	public Documento getFiltro() {
		return filtro;
	}

	public void setFiltro(Documento filtro) {
		this.filtro = filtro;
	}

	public double getTotalCantidad() {
		return totalCantidad;
	}

	public void setTotalCantidad(double totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

	public double getTotalCantidadPorEmbalaje() {
		return totalCantidadPorEmbalaje;
	}

	public void setTotalCantidadPorEmbalaje(double totalCantidadPorEmbalaje) {
		this.totalCantidadPorEmbalaje = totalCantidadPorEmbalaje;
	}

	public double getTotalCantidadCajas() {
		return totalCantidadCajas;
	}

	public void setTotalCantidadCajas(double totalCantidadCajas) {
		this.totalCantidadCajas = totalCantidadCajas;
	}

	public double getMuestrasFITOANTICO() {
		return muestrasFITOANTICO;
	}

	public void setMuestrasFITOANTICO(double muestrasFITOANTICO) {
		this.muestrasFITOANTICO = muestrasFITOANTICO;
	}

	public double getMuestrasCalidades() {
		return muestrasCalidades;
	}

	public void setMuestrasCalidades(double muestrasCalidades) {
		this.muestrasCalidades = muestrasCalidades;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Timestamp getCurrentTimeStamp() {
		return currentTimeStamp;
	}

	public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
		this.currentTimeStamp = currentTimeStamp;
	}
}
