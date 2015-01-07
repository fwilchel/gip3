package com.ssl.jv.gip.web.mb.comercioexterior;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
public class ListaEmpaqueMB extends UtilMB{
	
	private static final long serialVersionUID = 1L;
	private Timestamp currentTimeStamp;
	private String consecutivoFacturaProforma;
	private List<ListaEmpaqueDTO> listaEmpaqueList;
	private ListaEmpaqueDTO listaEmpaqueSeleccionada;
	private List<ProductoDTO> productoList;
	@EJB
	private ComercioExteriorEJB comercioEjb;
	
	@PostConstruct
	public void init(){
		currentTimeStamp = new Timestamp(System.currentTimeMillis());
	}
	
	public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(){
		listaEmpaqueList = comercioEjb.consultarDocumentoPorFacturaProforma(consecutivoFacturaProforma);
		return listaEmpaqueList;
	}
	
	public List<ProductoDTO> consultarProductoPorDocumento(){
		productoList = comercioEjb.consultarProductoPorDocumento(listaEmpaqueSeleccionada.getIdDocumento(), listaEmpaqueSeleccionada.getCliente().getId());
		return productoList;
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

}
