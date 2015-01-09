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
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
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
	private double totalCantidad=0;
	private double totalValorTotal=0;
	private double totalPesoNeto=0;
	private double totalPesoBruto=0;
	private double totalCantidadCajas=0;
	private double totalCantidadPallets=0;
	private double totalCantidadPorEmbalaje=0;
	
	public double getTotalCantidadPorEmbalaje() {
		return totalCantidadPorEmbalaje;
	}

	public void setTotalCantidadPorEmbalaje(double totalCantidadPorEmbalaje) {
		this.totalCantidadPorEmbalaje = totalCantidadPorEmbalaje;
	}

	public double getTotalCantidad() {
		return totalCantidad;
	}

	public void setTotalCantidad(double totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

	public double getTotalValorTotal() {
		return totalValorTotal;
	}

	public void setTotalValorTotal(double totalValorTotal) {
		this.totalValorTotal = totalValorTotal;
	}

	public double getTotalPesoNeto() {
		return totalPesoNeto;
	}

	public void setTotalPesoNeto(double totalPesoNeto) {
		this.totalPesoNeto = totalPesoNeto;
	}

	public double getTotalPesoBruto() {
		return totalPesoBruto;
	}

	public void setTotalPesoBruto(double totalPesoBruto) {
		this.totalPesoBruto = totalPesoBruto;
	}

	public double getTotalCantidadCajas() {
		return totalCantidadCajas;
	}

	public void setTotalCantidadCajas(double totalCantidadCajas) {
		this.totalCantidadCajas = totalCantidadCajas;
	}


	public double getTotalCantidadPallets() {
		return totalCantidadPallets;
	}

	public void setTotalCantidadPallets(double totalCantidadPallets) {
		this.totalCantidadPallets = totalCantidadPallets;
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
		
		this.totalCantidad=0;
		this.totalValorTotal=0;
		this.totalPesoNeto=0;
		this.totalPesoBruto=0;
		this.totalCantidadCajas=0;
		this.totalCantidadPallets=0;
		this.totalCantidadPorEmbalaje=0;
		
		
		
		
		this.setSeleccionado2(this.comercioEjb.consultarDocumentoListaEmpaque(seleccionado.getConsecutivoDocumento()));
		listaEmpaqueDetalle = comercioEjb.consultarProductoListaEmpaque(seleccionado.getConsecutivoDocumento());
		
		
		System.out.println("doc: "+seleccionado.getId());
		System.out.println("cliente: "+ seleccionado.getCliente().getId());
		
		lotes = comercioEjb.consultarProductoPorDocumentoLoteAsignarLotesOIC(seleccionado.getId(), seleccionado.getCliente().getId());
		
		
		for (ProductoImprimirLEDTO p:listaEmpaqueDetalle){
			this.totalCantidad+=p.getCantidad().doubleValue();
			this.totalPesoNeto+=p.getPesoNeto().doubleValue();
			this.totalPesoBruto+=p.getPesoBruto().doubleValue();
			this.totalCantidadCajas+=p.getCantidadCajas().doubleValue();
			this.totalCantidadPallets+=p.getCajasPorPallets().doubleValue();
			this.totalCantidadPorEmbalaje+=p.getCantidadPorEmbalaje().doubleValue();
			
			
		}
		
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
