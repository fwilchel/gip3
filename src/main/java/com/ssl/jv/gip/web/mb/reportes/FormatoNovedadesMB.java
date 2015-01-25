package com.ssl.jv.gip.web.mb.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesEJB;
import com.ssl.jv.gip.web.mb.UtilMB;

@ManagedBean(name="formatoNovedadesMB")
@SessionScoped
public class FormatoNovedadesMB extends UtilMB{
	
	private List<ComextFormatoNovedadesDTO> listado;
		
	@EJB
	private ReportesEJB reportesEJB;
	
	private StreamedContent reportePDF;
		
	public FormatoNovedadesMB(){
		
	}
	
	@PostConstruct
	public void init(){
		listado = reportesEJB.consultarComextFormatoNovedades();
	}
	
	public void previsualizarFN(ActionEvent ae){
		
	}

	public List<ComextFormatoNovedadesDTO> getListado() {
		return listado;
	}

	public void setListado(List<ComextFormatoNovedadesDTO> listado) {
		this.listado = listado;
	}
	
	public StreamedContent getReportePDF() {
		Map<String, Object> parametros = new HashMap<String, Object>();
//		Cliente c=seleccionado.getCliente();
		
//		parametros.put("cliente",c.getNombre());		 
//		parametros.put("nit", c.getNit());
//		parametros.put("ciudad",c.getCiudad().getNombre());		 
//		parametros.put("direccion", c.getDireccion());
//		parametros.put("telefono", c.getTelefono());
//		parametros.put("contacto", c.getContacto());
//		parametros.put("id_documento", seleccionado.getConsecutivoDocumento());
//		parametros.put("solicitud", seleccionado.getObservacionDocumento());
//		
//		parametros.put("qEstibas", 0.0); //dblCantidadEstibas	
//		parametros.put("PesoBrutoEstibas", 0.0 ); //dblPesoBrutoEstibas 
//		
//		parametros.put("observacion", ""); // seleccionado2.getDocumentoXNegociacions().get(0).getDescripcion()
		
//		if (this.blnActivo ) {
//			 parametros.put("titulo","PACKING LIST No. "+seleccionado.getConsecutivoDocumento());
//			 parametros.put("t1","SHIPPER");
//			 parametros.put("t2","CONSIGNEE");
//			 parametros.put("t3","Sku");
//			 parametros.put("t4","Description");
//			 parametros.put("t5","Quantity");
//			 parametros.put("t6","UnitXPackaging");
//			 parametros.put("t7","Boxes");
//			 parametros.put("t8","Net Weight(Kg)");
//			 parametros.put("t9","Gross Weight(Kg)");
//			 parametros.put("t10","Pallets");
//			 parametros.put("t11","Reg.San.");
//			 parametros.put("t12","Signature");
//			 parametros.put("t13","AUTHORIZED SIGNATURE");
//			 parametros.put("t14","Order No. "+seleccionado.getObservacionDocumento());
//			 parametros.put("t15","Subtotal Packing Material");
//			 
//		 }
//		 else {
//			 parametros.put("titulo","PACKING LIST No."+seleccionado.getConsecutivoDocumento());
//			 parametros.put("t1","SHIPPER");
//			 parametros.put("t2","CONSIGNEE");
//			 parametros.put("t3","Sku");
//			 parametros.put("t4","Descripcion");
//			 parametros.put("t5","Cantidad");
//			 parametros.put("t6","UnidadXEmbalaje");
//			 parametros.put("t7","Cantidad Cajas");
//			 parametros.put("t8","Peso Neto(Kg)");
//			 parametros.put("t9","Peso Bruto(Kg)");
//			 parametros.put("t10","Pallets");
//			 parametros.put("t11","Reg.San.");
//			 parametros.put("t12","Firma");
//			 parametros.put("t13","FIRMA AUTORIZADA");
//			 parametros.put("t14","Orden No. "+seleccionado.getObservacionDocumento());
//			 parametros.put("t15","Subtotal Estibas");
//				 
//				 
//				 
//		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listado);
		try {
			
			Hashtable<String, String> parametrosR=new Hashtable<String, String>();
			parametrosR.put("tipo", "xls");
			String reporte=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_FN.jasper");
			ByteArrayOutputStream os=(ByteArrayOutputStream)com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
			reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/vnd.ms-excel ", "Reporte_FN.xls");
			
		} catch (Exception e) {
			this.addMensajeError(e);
		}
		return reportePDF;
	}

}
