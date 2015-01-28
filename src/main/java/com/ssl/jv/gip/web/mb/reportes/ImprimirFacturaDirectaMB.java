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

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;

import com.ssl.jv.gip.web.util.Numero_a_Letra;




import com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJB;

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

@ManagedBean(name="imprimirFacturaDirectaMB")
@SessionScoped
public class ImprimirFacturaDirectaMB  extends UtilMB {
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
		//listaEmpaqueDetalle = comercioEjb.consultarProductoListaEmpaque(seleccionado.getConsecutivoDocumento());
		
		listaDetalle = this.ventasFacturacionEjb.consultarProductoFacturaDirecta(seleccionado.getConsecutivoDocumento());
		
		
		System.out.println("doc: "+seleccionado.getId());
		System.out.println("cliente: "+ seleccionado.getCliente().getId());
		
		//lotes = comercioEjb.consultarProductoPorDocumentoLoteAsignarLotesOIC(seleccionado.getId(), seleccionado.getCliente().getId());
		
		
		
		
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
	private ComercioExteriorEJB  comercioEjb;
	
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
		
		
		parametros.put("tipo", ConstantesTipoDocumento.FACTURA);
		//parametros.put("estado", ConstantesDocumento.IMPRESO);
		//parametros.put("estado2", ConstantesDocumento.ANULADO);
		
					
					Long[] array = new Long[2];
					array[0]=(long) ConstantesDocumento.IMPRESO;
					array[1]= (long) ConstantesDocumento.ANULADO;
					parametros.put("estado",array);
					
		parametros.put("parametroConseDoc",parametroConseDoc);
		
		list = ventasFacturacionEjb.consultarDocumento(parametros,array);
		
		
		return list;
	}

	
	public StreamedContent getReportePDF() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		//Cliente c=seleccionado.getCliente();
		
		
		
		 /*parametros.put("despachado_a",pv.getStrNombre());
		 parametros.put("direccionpv",pv.getStrDireccion());
		 parametros.put("telefonopv","Teléfono: " + pv.getStrTelefono());		 
		 parametros.put("ciudadpv",pv.getStrNomCiudad());
		 parametros.put("documento",docCabecera.getStrDocumentoCliente());
		 parametros.put("fecha",fechaStringGeneracion);
		 parametros.put("sku","A28");*/
		//parametros.put("numFactura", docCabecera.getStrConsecutivoDocumento());
		 //parametros.put("tipoImp", "Copia");
		 
		 SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		 String fechaStringGeneracion = ft.format( seleccionado2.getFechaGeneracion());
		
		 
		    parametros.put("cliente",seleccionado2.getNombreCliente());		 
			parametros.put("nit", seleccionado2.getNitCliente());
			parametros.put("ciudad",seleccionado2.getNombreCiudadCliente());		 
			parametros.put("direccion", seleccionado2.getDireccionCliente());
			parametros.put("telefono", seleccionado2.getTelefonoCliente());
			//parametros.put("contacto", c.getContacto());
			//parametros.put("id_documento", seleccionado.getConsecutivoDocumento());
			//parametros.put("solicitud", seleccionado.getObservacionDocumento());
			parametros.put("numFactura",seleccionado2.getConsecutivoDocumento());
		 
			
			/*System.out.println("cliente: "+c.getNombre());
			System.out.println("NIT: "+c.getNit());
			System.out.println("ciudad: "+c.getCiudad().getNombre());
			System.out.println("numFactura"+seleccionado.getConsecutivoDocumento());
			
			System.out.println("direccion"+ c.getDireccion());
			System.out.println("telefono"+ c.getTelefono());
			System.out.println("fecha"+fechaStringGeneracion);
			System.out.println("documento"+seleccionado.getDocumentoCliente());*/
			//System.out.println("contacto"+ c.getContacto());
			//parametros.put("id_documento", seleccionado.getConsecutivoDocumento());
			
			
			
			
			
			 parametros.put("despachado_a",seleccionado2.getNombrePuntoVenta());
			 parametros.put("direccionpv",seleccionado2.getDireccionPuntoVenta());
			 parametros.put("telefonopv","Teléfono: " + seleccionado2.getTelefonoPuntoVenta());		 
			 parametros.put("ciudadpv",seleccionado2.getNombreCiudadPuntoVenta());
			 parametros.put("documento",seleccionado.getDocumentoCliente());
			 parametros.put("fecha",fechaStringGeneracion);
			 parametros.put("tipoImp", "Copia");
			 
			 
			 parametros.put("valorSubtotal", seleccionado2.getValorSubtotal());
			 parametros.put("valorDescuento", seleccionado2.getValorDescuento());
			 parametros.put("valorIva5", seleccionado2.getValorIva5());
			 parametros.put("valorIva16", seleccionado2.getValorIva16());
			 parametros.put("valorTotal", seleccionado2.getValorTotal());
			 
			 
			 
					 

						Numero_a_Letra NumLetra = new Numero_a_Letra();										
						parametros.put("valorLetras",NumLetra.Convertir(seleccionado2.getValorTotal().toString(),true));
			 
			 if (seleccionado2.getEstado()==ConstantesDocumento.ANULADO)
				 
			 {
				 parametros.put("anulada", "ANULADA");
			 }
			 else
			 {
				 parametros.put("anulada", "");
			 }
			 
			 
			 
			 parametros.put("mark", "(*)");	
			 parametros.put("descuentoCliente","%");
			 parametros.put("observaciones", "Los productos marcados con (*) incluyen descuento adicional.");
			 
			 JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaDetalle);
				try {
					
					Hashtable<String, String> parametrosR=new Hashtable<String, String>();
					parametrosR.put("tipo", "pdf");
					String reporte=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/report2.jasper");
					
					
					
					ByteArrayOutputStream os=(ByteArrayOutputStream)com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
					reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "report2.pdf");
					
					
					/*String xmlfile = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/report2.jrxml");
					JasperReport reporte = JasperCompileManager.compileReport(xmlfile);
					JasperPrint print = JasperFillManager.fillReport(reporte, parametros, ds);
					ByteArrayOutputStream baos =new ByteArrayOutputStream();
					net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream(print,baos);
					*/
					
				} catch (Exception e) {
					this.addMensajeError(e);
				}
			 
		
		return reportePDF;
}
	
}
