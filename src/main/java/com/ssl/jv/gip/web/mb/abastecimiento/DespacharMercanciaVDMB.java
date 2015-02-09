package com.ssl.jv.gip.web.mb.abastecimiento;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ProductoDespacharMercanciaDTO;
import com.ssl.jv.gip.negocio.ejb.DespachoMercanciaEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;

@ManagedBean(name="despacharMercanciaMB")
@ViewScoped
public class DespacharMercanciaVDMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Documento> documentos;
	private List<ProductoDespacharMercanciaDTO> productos;
	private List<ProductoDespacharMercanciaDTO> productosDespacho;
	private Documento seleccionado;
	private Documento filtro;
	
	@EJB
	private DespachoMercanciaEJBLocal despachoMercancia;
	
	private Integer language=AplicacionMB.SPANISH;
	
	@PostConstruct
	public void init(){
		documentos = despachoMercancia.consultarVentasDirectas();
	}
	
	public void consultarProductosVentaDirecta(){
		productos=despachoMercancia.consultarProductoPorDocumento(seleccionado.getId()+"",seleccionado.getCliente().getId()+"");
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public List<ProductoDespacharMercanciaDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoDespacharMercanciaDTO> productos) {
		this.productos = productos;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
	}

	public Documento getFiltro() {
		return filtro;
	}

	public void setFiltro(Documento filtro) {
		this.filtro = filtro;
	}

	public DespachoMercanciaEJBLocal getDespachoMercancia() {
		return despachoMercancia;
	}

	public void setDespachoMercancia(DespachoMercanciaEJBLocal despachoMercancia) {
		this.despachoMercancia = despachoMercancia;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public List<ProductoDespacharMercanciaDTO> getProductosDespacho() {
		return productosDespacho;
	}

	public void setProductosDespacho(
			List<ProductoDespacharMercanciaDTO> productosDespacho) {
		this.productosDespacho = productosDespacho;
	}
	
	

}
