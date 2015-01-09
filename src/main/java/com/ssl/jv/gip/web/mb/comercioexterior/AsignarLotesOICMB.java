package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoicPK;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TipoLoteoic;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Utilidad;

/**
 * <p>Title: GIP</p>
 *
 * <p>Description: GIP</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Fredy Giovanny Wilches López
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name="asignarLotesMB")
@SessionScoped
public class AsignarLotesOICMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780795923623719268L;

	private static final Logger LOGGER = Logger.getLogger(AsignarLotesOICMB.class);
	private Timestamp currentTimeStamp;
	private String consecutivoDocumento;
	private Integer language=AplicacionMB.SPANISH;
	private List<Documento> listaDocumentos;
	private Documento documentoSeleccionado;
	private List<ProductoAsignarLoteOICDTO> productos;
	private List<ProductoLoteAsignarLoteOICDTO> lotes;
	private double totalCantidad=0;
	private double totalValorTotal=0;
	private double totalPesoNeto=0;
	private double totalPesoBruto=0;
	private double totalCantidadCajas=0;
	private double totalCantidadTendidos=0;
	private double totalCantidadPallets=0;
	private double totalCostos=0;
	private double totalNegociacion=0;
	private boolean deshabilitado=false;
	
	@EJB
	private ComercioExteriorEJBLocal comercioEjb;
	
	@ManagedProperty(value="#{menuMB}")
	private MenuMB menu;
	
	public AsignarLotesOICMB(){
		
	}
	
	@PostConstruct
	public void init(){
		currentTimeStamp = new Timestamp(System.currentTimeMillis());
		
	}

	public List<ProductoLoteAsignarLoteOICDTO> getLotes() {
		return lotes;
	}

	public void setLotes(List<ProductoLoteAsignarLoteOICDTO> lotes) {
		this.lotes = lotes;
	}

	public Timestamp getCurrentTimeStamp() {
		return currentTimeStamp;
	}

	public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
		this.currentTimeStamp = currentTimeStamp;
	}

	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public String buscarDocumentos(){
		listaDocumentos=this.comercioEjb.consultarDocumentosFacturaPF(consecutivoDocumento);
		this.deshabilitado=false;
		return null;
	}

	public boolean isDeshabilitado() {
		return deshabilitado;
	}

	public void setDeshabilitado(boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	public List<Documento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Documento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public Documento getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(Documento documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
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

	public double getTotalCantidadTendidos() {
		return totalCantidadTendidos;
	}

	public void setTotalCantidadTendidos(double totalCantidadTendidos) {
		this.totalCantidadTendidos = totalCantidadTendidos;
	}

	public double getTotalCantidadPallets() {
		return totalCantidadPallets;
	}

	public void setTotalCantidadPallets(double totalCantidadPallets) {
		this.totalCantidadPallets = totalCantidadPallets;
	}

	public double getTotalCostos() {
		return totalCostos;
	}

	public void setTotalCostos(double totalCostos) {
		this.totalCostos = totalCostos;
	}

	public double getTotalNegociacion() {
		return totalNegociacion;
	}

	public void setTotalNegociacion(double totalNegociacion) {
		this.totalNegociacion = totalNegociacion;
	}

	public String consultarFacturaPF(){
		productos=comercioEjb.consultarProductoPorDocumentoAsignarLotesOIC(this.documentoSeleccionado.getId(), this.documentoSeleccionado.getCliente().getId());
		this.totalCantidad=0;
		this.totalValorTotal=0;
		this.totalPesoNeto=0;
		this.totalPesoBruto=0;
		this.totalCantidadCajas=0;
		this.totalCantidadTendidos=0;
		this.totalCantidadPallets=0;
		for (ProductoAsignarLoteOICDTO p:productos){
			this.totalCantidad+=p.getCantidad().doubleValue();
			this.totalValorTotal+=p.getValorTotal().doubleValue();
			this.totalPesoNeto+=p.getTotalPesoNeto().doubleValue();
			this.totalPesoBruto+=p.getTotalPesoBruto().doubleValue();
			this.totalCantidadCajas+=p.getTotalCajas().doubleValue();
			this.totalCantidadTendidos+=p.getTotalCajasTendido().doubleValue();
			this.totalCantidadPallets+=p.getTotalCajasPallet().doubleValue();
		}
		
		totalCostos=0;
		if (this.documentoSeleccionado.getDocumentoXNegociacions()!=null && this.documentoSeleccionado.getDocumentoXNegociacions().size()>0){
			totalCostos=this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoEntrega().doubleValue() + this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoSeguro().doubleValue() +
					this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoFlete().doubleValue() + this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getOtrosGastos().doubleValue(); 
		}
		totalNegociacion= this.totalCostos + this.totalValorTotal;
		lotes = comercioEjb.consultarProductoPorDocumentoLoteAsignarLotesOIC(this.documentoSeleccionado.getId(), this.documentoSeleccionado.getCliente().getId());
		
		return null;
	}

	public List<ProductoAsignarLoteOICDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoAsignarLoteOICDTO> productos) {
		this.productos = productos;
	}
	
	public String asignarLotes(){
		List<DocumentoXLotesoic> lista=new ArrayList<DocumentoXLotesoic>();
		for (ProductoLoteAsignarLoteOICDTO lote:this.lotes){
			DocumentoXLotesoic docLote = new DocumentoXLotesoic();
			
			docLote.setDocumento(this.documentoSeleccionado);
			docLote.setTipoLoteoic(new TipoLoteoic());
			
			docLote.setId(new DocumentoXLotesoicPK());
			docLote.getId().setIdDocumento(this.documentoSeleccionado.getId());
			docLote.getId().setIdTipoLote(lote.getTipoLote().longValue());
			
			docLote.getTipoLoteoic().setId(lote.getTipoLote().longValue());
			docLote.setFecha(new Timestamp(System.currentTimeMillis()));
			docLote.setTotalCantidad(lote.getTotalCantidad());
			docLote.setTotalCajas(lote.getTotalCajas());
			docLote.setTotalPesoNeto(lote.getTotalPesoNeto());
			docLote.setContribucion(new BigDecimal(0));
			docLote.setDex(new BigDecimal(0));
			lista.add(docLote);
		}
		documentoSeleccionado.getEstadosxdocumento().getId().setIdEstado((long)ConstantesDocumento.ASIGNADA);
		lista=this.comercioEjb.guardarLotes(lista, this.documentoSeleccionado);
		for (int i=0; i<lista.size(); i++){
			this.lotes.get(i).setConsecutivo(lista.get(i).getConsecutivo());
		}
		this.buscarDocumentos();
		this.deshabilitado = true;
		return null;
	
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}
	
}
