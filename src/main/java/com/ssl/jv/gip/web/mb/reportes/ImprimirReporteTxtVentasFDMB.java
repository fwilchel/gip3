package com.ssl.jv.gip.web.mb.reportes;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

















import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.negocio.dto.ProductoReporteTxtFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ReporteVentaDTO;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;





import com.ssl.jv.gip.web.mb.util.exportarExcel;
import com.ssl.jv.gip.web.util.Utilidad;
import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJB;

/**
 * <p>Title: Imprimir Reporte Txt Ventas FD MB</p>
 *
 * <p>Description: ManagedBean para Imprimir Reporte Txt Ventas FD MB </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Procafecol</p>
 *
 * @author John Heredia
 * @email jherediab@gmail.com
 * @phone 321 2024867
 * @version 1.0
 */

@ManagedBean(name="imprimirReporteTxtVentasFDMB")
@SessionScoped
public class ImprimirReporteTxtVentasFDMB  extends UtilMB {

	
	private static final Logger LOGGER = Logger
			.getLogger(ImprimirReporteTxtVentasFDMB.class);
	
	private List<ProductoReporteTxtFacturaDirectaDTO> list;
	private StreamedContent reporteTXT;
	private Date fechaInicial;
	private Date fechaFinal;
	
	
	@EJB
	private VentasFacturacionEJB ventasFacturacionEjb;
	

	
	private Boolean renderPanelPrincipal = true;

	
	public void generarReportePlanoFD() throws FileNotFoundException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		
		String DATE_FORMAT = "yyyy-MM-dd";
		//SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		SimpleDateFormat ft = new SimpleDateFormat(DATE_FORMAT);
		String fechaStringGeneracionInicial = this.fechaInicial!=null ? ft.format(this.fechaInicial):"";
		String fechaStringGeneracionFinal = this.fechaFinal!=null ? ft.format(this.fechaFinal):"";

		String fechaFinalTmp = fechaStringGeneracionFinal;
		fechaFinalTmp = this.fechaFinal!=null ? (fechaFinalTmp + " 23:59:59"):"";
		
		 
		parametros.put("tipo", ConstantesTipoDocumento.FACTURA);
		parametros.put("fechaInicial", fechaStringGeneracionInicial);
		parametros.put("fechaFinal",fechaFinalTmp );
		parametros.put("estado",ConstantesDocumento.IMPRESO);
		
		list = ventasFacturacionEjb.consultarReporteTxtVentasFD(parametros);
		
		//System.out.println("lista reporte ventas TXT: "+list.size());
		hacerTxt();
		
		
}
	
	public void hacerTxt() throws FileNotFoundException {
		
		try {
		
		// Definicion de variables para el response
		HttpServletResponse response = null;
		FacesContext context = null;
		HttpServletRequest request = null;

		context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		response = (HttpServletResponse) external.getResponse();
		request = (HttpServletRequest) external.getRequest();
		// fin definicion de variables para el response				
		
	    String DATE_FORMAT = "yyyy-MM-dd";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    Calendar fech = Calendar.getInstance(); //Fecha y Tiempo actual
	    String datatime = sdf.format(fech.getTime());
		
		  
		ByteArrayOutputStream baos =new ByteArrayOutputStream();
		
		ProductoReporteTxtFacturaDirectaDTO pxd = new ProductoReporteTxtFacturaDirectaDTO();
		
		response = Utilidad.configureResponse2(response, "FD-" + datatime);
		response.setContentLength(baos.size());


		
		ServletOutputStream out = null;
		
		BufferedWriter writer = null;
	    try {
			writer = new BufferedWriter(new OutputStreamWriter(baos));
		
			for (int i = 0; i < list.size(); i++) 
			{
				pxd=((ProductoReporteTxtFacturaDirectaDTO) list.get(i));
							
				writer.write(pxd.getIdUbicacionDestino() + "~" +pxd.getSku() + "~" + pxd.getNombre()+ "~" +pxd.getCantidad()+ "~" +pxd.getFechaGeneracion()+ "~" +pxd.getConsecutivoDocumento()+ "~"+ pxd.getValorUnitario()+"~"+pxd.getObservacionDocumento()+"~"+pxd.getClienteIcg() +"~"+pxd.getValorDescuentoProducto()+"~"+pxd.getValorIva());
				
				writer.newLine();
					
				//System.out.println(pxd.getIdUbicacionDestino() + "~" +pxd.getSku() + "~" + pxd.getNombre()+ "~" +pxd.getCantidad()+ "~" +pxd.getFechaGeneracion()+ "~" +pxd.getConsecutivoDocumento()+ "~"+ pxd.getValorUnitario()+"~"+pxd.getObservacionDocumento()+"~"+pxd.getClienteIcg() +"~"+pxd.getValorDescuentoProducto()+"~"+pxd.getValorIva());
				
			}
			
			
			
			writer.close();   
			byte[] buffer = baos.toByteArray(); 	
			response.setContentLength(baos.size());

			out = response.getOutputStream();					
			out.write(buffer);

	    } catch (IOException e) {
			e.printStackTrace();
		}

		baos.flush();
		out.flush();
		out.close();
		context.responseComplete();		
			
			
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
	
	
	


	
	
	public List<ProductoReporteTxtFacturaDirectaDTO> getList() {
		return list;
	}

	public void setList(List<ProductoReporteTxtFacturaDirectaDTO> list) {
		this.list = list;
	}
	
	public Boolean getRenderPanelPrincipal() {
		return renderPanelPrincipal;
	}

	public void setRenderPanelPrincipal(Boolean renderPanelPrincipal) {
		this.renderPanelPrincipal = renderPanelPrincipal;
	}

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

}
