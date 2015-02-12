package com.ssl.jv.gip.web.mb.comercioexterior;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;

/**Managed Bean para ordenes de despacho
 * 
 * @author Daniel Cortes
 * @version 1.0
 * @email danicorc@gmail.com
 *
 */
@ManagedBean(name="ordenesDespachoMB")
@ViewScoped
public class OrdenesDespachoMB extends UtilMB{

	private static final long serialVersionUID = 1L;
	
	private Timestamp currentTimeStamp;
	private String consecutivoDocumento;
	private boolean deshabilitado=false;
	private List<Documento> documentos;
	private List<ProductoGenerarFacturaPFDTO> productos;
	private Documento seleccionado;
	private Documento filtro;
	private double totalCantidad=0;
	private double totalCantidadPorEmbalaje=0;
	private double totalCantidadCajas=0;
	private double muestrasFITOANTICO=0;
	private double muestrasCalidades=0;
	
	@EJB
	private ComercioExteriorEJBLocal comercioEjb;
	
	private Integer language=AplicacionMB.SPANISH;
	
	public OrdenesDespachoMB() {
	}

	@PostConstruct
	public void init(){
		currentTimeStamp = new Timestamp(System.currentTimeMillis());
	}

	public String buscarDocumentos() {
		documentos = this.comercioEjb.consultarFP(consecutivoDocumento);
		this.deshabilitado = false;
		return null;
	}
	
	public void consultarOrdenDeDespacho(){
		productos=comercioEjb.consultarProductoPorDocumentoGenerarFacturaProforma(seleccionado.getId(),seleccionado.getCliente().getId());
		totalCantidad=0;
		totalCantidadCajas=0;
		totalCantidadPorEmbalaje=0;
		muestrasFITOANTICO=0;
		muestrasCalidades=0;
		for (ProductoGenerarFacturaPFDTO p : productos) {
			this.totalCantidad+=p.getCantidad().doubleValue();
			this.totalCantidadCajas+=p.getCantidadCajas().doubleValue();
			this.totalCantidadPorEmbalaje+=p.getCantidadPorEmbalaje().doubleValue();
			this.muestrasCalidades+=p.getTotalCajasPallet().doubleValue();
			this.muestrasFITOANTICO+=p.getTotalCajasTendido().doubleValue();
		}
	}

	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public boolean isDeshabilitado() {
		return deshabilitado;
	}

	public void setDeshabilitado(boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public List<ProductoGenerarFacturaPFDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoGenerarFacturaPFDTO> productos) {
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

	public double getTotalCantidad() {
		return totalCantidad;
	}

	public void setTotalCantidad(double totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

	public double getTotalCantidadPorEmbalaje() {
		return totalCantidadPorEmbalaje;
	}

	public void setTotalCantidadPorEmbalaje(double totalCantidadPorEmbalaje) {
		this.totalCantidadPorEmbalaje = totalCantidadPorEmbalaje;
	}

	public double getTotalCantidadCajas() {
		return totalCantidadCajas;
	}

	public void setTotalCantidadCajas(double totalCantidadCajas) {
		this.totalCantidadCajas = totalCantidadCajas;
	}

	public double getMuestrasFITOANTICO() {
		return muestrasFITOANTICO;
	}

	public void setMuestrasFITOANTICO(double muestrasFITOANTICO) {
		this.muestrasFITOANTICO = muestrasFITOANTICO;
	}

	public double getMuestrasCalidades() {
		return muestrasCalidades;
	}

	public void setMuestrasCalidades(double muestrasCalidades) {
		this.muestrasCalidades = muestrasCalidades;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Timestamp getCurrentTimeStamp() {
		return currentTimeStamp;
	}

	public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
		this.currentTimeStamp = currentTimeStamp;
	}
	
}
