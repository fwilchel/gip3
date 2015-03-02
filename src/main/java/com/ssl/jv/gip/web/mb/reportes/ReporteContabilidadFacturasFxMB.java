package com.ssl.jv.gip.web.mb.reportes;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.CuentaContableDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.comercioexterior.ModificarListaEmpaqueMB;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Utilidad;

@ManagedBean(name = "reporteContabilidadFacturasFxMB")
@SessionScoped
public class ReporteContabilidadFacturasFxMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6136563159395512436L;

	private static final Logger LOGGER = Logger
			.getLogger(ReporteContabilidadFacturasFxMB.class);



	private List<CuentaContableDTO> listaCuentasContables;

	private String consecutivoDocumento;
	private Date fechaInicial;
	private Date fechaFinal;
	private Boolean renderPanelPrincipal = true;
	private Boolean renderGenerarArchivoFechas = true;

	@EJB
	private ReportesEJBLocal reportesEJBLocal;


	@PostConstruct
	public void init() {
	}

	public void irGenerarArchivoFechas(){
		renderGenerarArchivoFechas=true;
		renderPanelPrincipal=false;
		consecutivoDocumento="";
		fechaInicial=null;
		fechaFinal=null;
	}

	public void irGenerarArchivoConsecutivo(){
		renderGenerarArchivoFechas=false;
		renderPanelPrincipal=false;
		consecutivoDocumento="";
		fechaInicial=null;
		fechaFinal=null;
	}

	public void cancelar(){
		renderPanelPrincipal=true;
		renderGenerarArchivoFechas=true;
		consecutivoDocumento="";
		fechaInicial=null;
		fechaFinal=null;
	}


	public void generarReportePlanoCE() {
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

			response = Utilidad.configureResponse2(response, "ContaFact" + "-" + datatime);
			

			// step 8: escribimos el ByteArrayOutputStream en el ServletOutputStream
			ServletOutputStream out = null;
			FileWriter fWriter = null;
			BufferedWriter writer = null;

			SimpleDateFormat ft = new SimpleDateFormat(DATE_FORMAT);
			String fechaStringGeneracionInicial = this.fechaInicial!=null ? ft.format(this.fechaInicial):"";
			String fechaStringGeneracionFinal = this.fechaFinal!=null ? ft.format(this.fechaFinal):"";

			String fechaFinalTmp = fechaStringGeneracionFinal;
			fechaFinalTmp = this.fechaFinal!=null ? (fechaFinalTmp + " 23:59:59"):"";

			try {

				listaCuentasContables = reportesEJBLocal
						.consultarReporteFacturasFX(consecutivoDocumento, fechaStringGeneracionInicial, fechaFinalTmp);

				writer = new BufferedWriter(new OutputStreamWriter(baos));

				String conseFactTmp="";
				String strConsecutivo="0";
				String nomCliente;
				int contaFact = 0;
				int i = 0;

				if(listaCuentasContables!=null && !listaCuentasContables.isEmpty()){
					for(CuentaContableDTO cuentaxfactura:listaCuentasContables){

						conseFactTmp = cuentaxfactura.getStrConsecutivoDoc();

						if (i != 0){
							writer.newLine();
						}

						String fecha = "";
						String[] arrayFecha = cuentaxfactura.getStrFechaGeneracion().split("-");

						for (int n = 0; n < arrayFecha.length; n++) {
							fecha = fecha + arrayFecha[n];
						}

						if (!strConsecutivo.equals(cuentaxfactura.getStrConsecutivoDoc()))
						{
							//Cabecera
							writer.write("1," + fecha + "," + fecha + ",DR,PROC,USD,"+ cuentaxfactura.getStrConsecutivoDoc() + "," + cuentaxfactura.getStrNomUbicacion());
							strConsecutivo=cuentaxfactura.getStrConsecutivoDoc() ;
							writer.newLine();

							//Subcabecera
							contaFact = contaFact + 1;


							if (cuentaxfactura.getBlnSolicitudCafe()) //Factura de Café
							{
								writer.write("2," + contaFact + ",01,1305100001," + cuentaxfactura.getStrCodigoSAP() + ",,,,,," + cuentaxfactura.getDblTotalFactura()+",,,AC,C1,"+cuentaxfactura.getDblTotalFactura()+",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + "VENTAS " + cuentaxfactura.getStrConsecutivoDoc() + "," + cuentaxfactura.getStrNomCliente());	    												

							}
							else //Factura de Mercadeo
							{
								writer.write("2," + contaFact + ",01,1305100003," + cuentaxfactura.getStrCodigoSAP() + ",,,,,," + cuentaxfactura.getDblTotalFactura()+",,,AC,C1,"+cuentaxfactura.getDblTotalFactura()+",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + "VENTAS " + cuentaxfactura.getStrConsecutivoDoc() + "," + cuentaxfactura.getStrNomCliente());	    												
							}

							writer.newLine();

						}

						nomCliente = cuentaxfactura.getStrNomCliente();
						writer.write("2," + contaFact + ",50," + cuentaxfactura.getLngId()+ ",,,,,,," + cuentaxfactura.getDblTotal() + "," + cuentaxfactura.getStrIndicadorIVA() + ",X,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + cuentaxfactura.getLngObjetoCO() + ",,," + "VENTAS " + conseFactTmp + "," + nomCliente);

						i++;

					}			 

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

	public List<CuentaContableDTO> getListaCuentasContables() {
		return listaCuentasContables;
	}

	public void setListaCuentasContables(
			List<CuentaContableDTO> listaCuentasContables) {
		this.listaCuentasContables = listaCuentasContables;
	}

	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
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

	public Boolean getRenderGenerarArchivoFechas() {
		return renderGenerarArchivoFechas;
	}

	public void setRenderGenerarArchivoFechas(Boolean renderGenerarArchivoFechas) {
		this.renderGenerarArchivoFechas = renderGenerarArchivoFechas;
	}

	public Boolean getRenderPanelPrincipal() {
		return renderPanelPrincipal;
	}

	public void setRenderPanelPrincipal(Boolean renderPanelPrincipal) {
		this.renderPanelPrincipal = renderPanelPrincipal;
	}





}
