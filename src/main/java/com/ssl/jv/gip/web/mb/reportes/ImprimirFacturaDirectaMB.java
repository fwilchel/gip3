package com.ssl.jv.gip.web.mb.reportes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;


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
public class ImprimirFacturaDirectaMB extends UtilMB {
	
	
	
	private List<Documento> list;
	
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

	
	
	private List<ProductoImprimirLEDTO> listaEmpaqueDetalle;

	private List<ProductoLoteAsignarLoteOICDTO> lotes;
	
	
	public List<ProductoLoteAsignarLoteOICDTO> getLotes() {
		return lotes;
	}

	public void setLotes(List<ProductoLoteAsignarLoteOICDTO> lotes) {
		this.lotes = lotes;
	}

	public List<ProductoImprimirLEDTO> getListaEmpaqueDetalle() {
		return listaEmpaqueDetalle;
	}

	public void setListaEmpaqueDetalle(
			List<ProductoImprimirLEDTO> listaEmpaqueDetalle) {
		this.listaEmpaqueDetalle = listaEmpaqueDetalle;
	}

	
	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
		
	
		
		
		
		
		this.setSeleccionado2(this.ventasFacturacionEjb.consultarDocumentoFacturaDirecta(seleccionado.getConsecutivoDocumento()));
		listaEmpaqueDetalle = comercioEjb.consultarProductoListaEmpaque(seleccionado.getConsecutivoDocumento());
		
		
		System.out.println("doc: "+seleccionado.getId());
		System.out.println("cliente: "+ seleccionado.getCliente().getId());
		
		lotes = comercioEjb.consultarProductoPorDocumentoLoteAsignarLotesOIC(seleccionado.getId(), seleccionado.getCliente().getId());
		
		
		
		
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
		parametros.put("estado", ConstantesDocumento.IMPRESO);
		parametros.put("parametroConseDoc",parametroConseDoc);
		
		list = ventasFacturacionEjb.consultarDocumento(parametros);
		
		
		return list;
	}

	

}
