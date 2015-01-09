package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.UtilMB;

/**
 * <p>Title: ListaEmpaqueMB</p>
 *
 * <p>Description: ManagedBean para las Listas de Empaque</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Lorena Salamanca
 * @email tachu.salamanca@gmail.com
 * @phone 316 6537244
 * @version 1.0
 */
@ManagedBean(name="listaEmpaqueMB")
@SessionScoped
public class ListaEmpaqueMB extends UtilMB {
	
	private static final long serialVersionUID = 1L;
	private Timestamp currentTimeStamp;
	private String consecutivoFacturaProforma;
	private List<ListaEmpaqueDTO> listaEmpaqueList;
	private ListaEmpaqueDTO listaEmpaqueSeleccionada;
	private List<ProductoDTO> productoList;
	private Double cantidadTotal;
	private Double unidadesPorEmbalajeTotal;
	private Double cantidadCajasTotal;
	private Double cantidadPalletsTotal;
	private Double pesoNetoTotal;
	private Double pesoBrutoTotal;
	private TabView tabView;
	private Documento docFinal;

	@EJB
	private ComercioExteriorEJB comercioEjb;
	
	@PostConstruct
	public void init(){
		System.out.println("init");
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		currentTimeStamp = new Timestamp(System.currentTimeMillis());
	}
	
	public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(){
		listaEmpaqueList = comercioEjb.consultarDocumentoPorFacturaProforma(consecutivoFacturaProforma);
		return listaEmpaqueList;
	}
	
	public List<ProductoDTO> consultarProductoPorDocumento(){
		productoList = comercioEjb.consultarProductoPorDocumento(listaEmpaqueSeleccionada);
		actualizarTotales();
		tabView.setActiveIndex(1);
		return productoList;
	}
	
	public void actualizarTotales(){
		cantidadTotal = 0.0D;
		unidadesPorEmbalajeTotal = 0.0D;
		cantidadCajasTotal = 0.0D;
		cantidadPalletsTotal = 0.0D;
		pesoNetoTotal = 0.0D;
		pesoBrutoTotal = 0.0D;
		for (ProductoDTO producto : productoList) {
			cantidadTotal = cantidadTotal + producto.getCantidad().doubleValue();
			unidadesPorEmbalajeTotal = unidadesPorEmbalajeTotal + producto.getCantidadPorEmbalaje().doubleValue();
			cantidadCajasTotal = cantidadCajasTotal + producto.getCantidadCajas().doubleValue();
			cantidadPalletsTotal = cantidadPalletsTotal + producto.getCantidadPallets().doubleValue();
			pesoNetoTotal = pesoNetoTotal + producto.getPesoNeto().doubleValue();
			pesoBrutoTotal = pesoBrutoTotal + producto.getPesoBruto().doubleValue();
			System.out.println(producto.getCantidadPorEmbalaje().doubleValue());
			System.out.println(unidadesPorEmbalajeTotal);
		}
	}
	
	public void generarListaEmpaque(){
		actualizarTotales();
		BigInteger consecutivoDocumento = comercioEjb.generarListaEmpaque(listaEmpaqueSeleccionada);
		System.out.println("***REGRESA DE LOS INSERT GRANDES");
		this.docFinal = comercioEjb.consultarDocumentoPorId(consecutivoDocumento.longValue());
		System.out.println("ENCUENTRA EL DOCFINAL " + docFinal.getConsecutivoDocumento());
		for (ProductoDTO producto : productoList) {
			producto.setIdDocumento(consecutivoDocumento.toString());
			comercioEjb.generarListaEmpaque(producto);
			System.out.println("***REGRESA DE LOS INSERT DE PRODUCTO ");
		}
		
	}

	public Timestamp getCurrentTimeStamp() {
		return currentTimeStamp;
	}

	public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
		this.currentTimeStamp = currentTimeStamp;
	}

	public String getConsecutivoFacturaProforma() {
		return consecutivoFacturaProforma;
	}

	public void setConsecutivoFacturaProforma(String consecutivoFacturaProforma) {
		this.consecutivoFacturaProforma = consecutivoFacturaProforma;
	}

	public List<ListaEmpaqueDTO> getListaEmpaqueList() {
		return listaEmpaqueList;
	}

	public void setListaEmpaqueList(List<ListaEmpaqueDTO> listaEmpaqueList) {
		this.listaEmpaqueList = listaEmpaqueList;
	}

	public ListaEmpaqueDTO getListaEmpaqueSeleccionada() {
		return listaEmpaqueSeleccionada;
	}

	public void setListaEmpaqueSeleccionada(ListaEmpaqueDTO listaEmpaqueSeleccionada) {
		this.listaEmpaqueSeleccionada = listaEmpaqueSeleccionada;
	}

	public List<ProductoDTO> getProductoList() {
		return productoList;
	}

	public void setProductoList(List<ProductoDTO> productoList) {
		this.productoList = productoList;
	}

	public Double getCantidadTotal() {
		return cantidadTotal;
	}

	public void setCantidadTotal(Double cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	public Double getUnidadesPorEmbalajeTotal() {
		return unidadesPorEmbalajeTotal;
	}

	public void setUnidadesPorEmbalajeTotal(Double unidadesPorEmbalajeTotal) {
		this.unidadesPorEmbalajeTotal = unidadesPorEmbalajeTotal;
	}

	public Double getCantidadCajasTotal() {
		return cantidadCajasTotal;
	}

	public void setCantidadCajasTotal(Double cantidadCajasTotal) {
		this.cantidadCajasTotal = cantidadCajasTotal;
	}

	public Double getCantidadPalletsTotal() {
		return cantidadPalletsTotal;
	}

	public void setCantidadPalletsTotal(Double cantidadPalletsTotal) {
		this.cantidadPalletsTotal = cantidadPalletsTotal;
	}

	public Double getPesoNetoTotal() {
		return pesoNetoTotal;
	}

	public void setPesoNetoTotal(Double pesoNetoTotal) {
		this.pesoNetoTotal = pesoNetoTotal;
	}

	public Double getPesoBrutoTotal() {
		return pesoBrutoTotal;
	}

	public void setPesoBrutoTotal(Double pesoBrutoTotal) {
		this.pesoBrutoTotal = pesoBrutoTotal;
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {
      this.tabView = tabView;
 }

	public Documento getDocFinal() {
		return docFinal;
	}

	public void setDocFinal(Documento docFinal) {
		this.docFinal = docFinal;
	}
}
