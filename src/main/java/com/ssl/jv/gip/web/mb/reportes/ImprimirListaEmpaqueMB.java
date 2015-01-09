package com.ssl.jv.gip.web.mb.reportes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;


import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;


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
public class ImprimirListaEmpaqueMB {
	
	
	
	private List<Documento> listaEmpaqueList;
	
	private String strConsecutivoDocumento;
	private Documento seleccionado;
	private ListaEmpaqueDTO seleccionado2;
	private boolean blnActivo = false;
	
	
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

}
