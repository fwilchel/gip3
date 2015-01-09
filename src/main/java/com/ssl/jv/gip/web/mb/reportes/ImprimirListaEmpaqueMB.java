package com.ssl.jv.gip.web.mb.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;


/**
 * <p>Title: ImprimirListaEmpaqueMB</p>
 *
 * <p>Description: ManagedBean para las imprimir Listas de Empaque</p>
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

@ManagedBean(name="imprimirListaEmpaqueMB")
@SessionScoped
public class ImprimirListaEmpaqueMB extends UtilMB{
	
	
	
	private List<Documento> listaEmpaqueList;
	
	private String strConsecutivoDocumento;
	private Documento seleccionado;
	private ListaEmpaqueDTO seleccionado2;
	private boolean blnActivo = false;
	private StreamedContent reportePDF;
	
	
	private List<ProductoImprimirLEDTO> listaEmpaqueDetalle;
	
	public List<ProductoImprimirLEDTO> getListaEmpaqueDetalle() {
		return listaEmpaqueDetalle;
	}

	public void setListaEmpaqueDetalle(
			List<ProductoImprimirLEDTO> listaEmpaqueDetalle) {
		this.listaEmpaqueDetalle = listaEmpaqueDetalle;
	}

	public boolean isBlnActivo() {
		return blnActivo;
	}

	public void setBlnActivo(boolean blnActivo) {
		this.blnActivo = blnActivo;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
		
		
		//this.seleccionado2  = this.comercioEjb.consultarDocumentoListaEmpaque(this.seleccionado.getConsecutivoDocumento());
		
		System.out.println("docSel: "+seleccionado.getConsecutivoDocumento());
		this.setSeleccionado2(this.comercioEjb.consultarDocumentoListaEmpaque(seleccionado.getConsecutivoDocumento()));
		
		
		listaEmpaqueDetalle = comercioEjb.consultarProductoListaEmpaque(seleccionado.getConsecutivoDocumento());
		
	}

	public String getStrConsecutivoDocumento() {
		return strConsecutivoDocumento;
	}

	public void setStrConsecutivoDocumento(String strConsecutivoDocumento) {
		this.strConsecutivoDocumento = strConsecutivoDocumento;
	}


	@EJB
	private ComercioExteriorEJB comercioEjb;

	
	public List<Documento> getListaEmpaqueList() {
		return listaEmpaqueList;
	}

	public void setListaEmpaqueList(List<Documento> listaEmpaqueList) {
		this.listaEmpaqueList = listaEmpaqueList;
	}

	
	public List<Documento> consultarDocumento(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		
		// Se Define la cadena de busqueda del nombre segun lo ingresado en el
		// campo de Consecutivo Pedido. 
		String parametroConseDoc;
		if (this.strConsecutivoDocumento.equals("")) {
			parametroConseDoc = "%";
		} else {
			parametroConseDoc = "%" + this.strConsecutivoDocumento + "%";
		}
		
		
		parametros.put("tipo", ConstantesTipoDocumento.LISTA_EMPAQUE);
		parametros.put("estado", ConstantesDocumento.ACTIVO);
		parametros.put("parametroConseDoc",parametroConseDoc);
		
		listaEmpaqueList = comercioEjb.consultarDocumento(parametros);
		
		System.out.println("tamaña lista:"+listaEmpaqueList.size());
		
		return listaEmpaqueList;
	}

	public ListaEmpaqueDTO getSeleccionado2() {
		return seleccionado2;
	}

	public void setSeleccionado2(ListaEmpaqueDTO seleccionado2) {
		this.seleccionado2 = seleccionado2;
	}

	public StreamedContent getReportePDF() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		Cliente c=seleccionado.getCliente();
		
		parametros.put("cliente",c.getNombre());		 
		parametros.put("nit", c.getNit());
		parametros.put("ciudad",c.getCiudad().getNombre());		 
		parametros.put("direccion", c.getDireccion());
		parametros.put("telefono", c.getTelefono());
		parametros.put("contacto", c.getContacto());
		parametros.put("id_documento", seleccionado.getConsecutivoDocumento());
		parametros.put("solicitud", seleccionado.getObservacionDocumento());
		
		parametros.put("qEstibas", 0.0); //dblCantidadEstibas	
		parametros.put("PesoBrutoEstibas", 0.0 ); //dblPesoBrutoEstibas 
		
		parametros.put("observacion", ""); // seleccionado2.getDocumentoXNegociacions().get(0).getDescripcion()
		
		if (this.blnActivo ) {
			 parametros.put("titulo","PACKING LIST No. "+seleccionado.getConsecutivoDocumento());
			 parametros.put("t1","SHIPPER");
			 parametros.put("t2","CONSIGNEE");
			 parametros.put("t3","Sku");
			 parametros.put("t4","Description");
			 parametros.put("t5","Quantity");
			 parametros.put("t6","UnitXPackaging");
			 parametros.put("t7","Boxes");
			 parametros.put("t8","Net Weight(Kg)");
			 parametros.put("t9","Gross Weight(Kg)");
			 parametros.put("t10","Pallets");
			 parametros.put("t11","Reg.San.");
			 parametros.put("t12","Signature");
			 parametros.put("t13","AUTHORIZED SIGNATURE");
			 parametros.put("t14","Order No. "+seleccionado.getObservacionDocumento());
			 parametros.put("t15","Subtotal Packing Material");
			 
		 }
		 else {
			 parametros.put("titulo","PACKING LIST No."+seleccionado.getConsecutivoDocumento());
			 parametros.put("t1","SHIPPER");
			 parametros.put("t2","CONSIGNEE");
			 parametros.put("t3","Sku");
			 parametros.put("t4","Descripcion");
			 parametros.put("t5","Cantidad");
			 parametros.put("t6","UnidadXEmbalaje");
			 parametros.put("t7","Cantidad Cajas");
			 parametros.put("t8","Peso Neto(Kg)");
			 parametros.put("t9","Peso Bruto(Kg)");
			 parametros.put("t10","Pallets");
			 parametros.put("t11","Reg.San.");
			 parametros.put("t12","Firma");
			 parametros.put("t13","FIRMA AUTORIZADA");
			 parametros.put("t14","Orden No. "+seleccionado.getObservacionDocumento());
			 parametros.put("t15","Subtotal Estibas");
				 
				 
				 
		}
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaEmpaqueDetalle);
		try {
			
			Hashtable<String, String> parametrosR=new Hashtable<String, String>();
			parametrosR.put("tipo", "pdf");
			String reporte=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_LE.jasper");
			ByteArrayOutputStream os=(ByteArrayOutputStream)com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
			reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "Reporte_LE.pdf");
			
		} catch (Exception e) {
			this.addMensajeError(e);
		}
		return reportePDF;
	}

	
	
	
	
}
