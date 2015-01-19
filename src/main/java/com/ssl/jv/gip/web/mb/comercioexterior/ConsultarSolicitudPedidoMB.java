package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;


/**
 * The Class SolicitudPedidoMB.
 */
@ManagedBean(name="consultarSolicitudPedidoMB")
@ViewScoped
public class ConsultarSolicitudPedidoMB extends UtilMB{

	/** The lista documentos. */
	private ArrayList<DocumentoIncontermDTO> listaDocumentos = new ArrayList<DocumentoIncontermDTO>();

	/** The lista termino inconterm. */
	private List<TerminoIncoterm> listaTerminoInconterm;

	/** The lista ubicaciones. */
	private List<Ubicacion> listaUbicaciones;

	/** The lista solicitud pedido. */
	private List<ProductoPorClienteComExtDTO> listaSolicitudPedido = new ArrayList<ProductoPorClienteComExtDTO>();

	/** The seleccionado. */
	private DocumentoIncontermDTO seleccionado;
	
	private FiltroConsultaSolicitudDTO filtro;

	/** The total valor t. */
	private BigDecimal totalValorT  =  new BigDecimal(0.00);
	
	/** The cantidad valor t. */
	private BigDecimal cantidadValorT  =  new BigDecimal(0.00);
	
	/** The valor peso neto t. */
	private BigDecimal valorPesoNetoT  =  new BigDecimal(0.00);
	
	/** The valor peso bruto t. */
	private BigDecimal valorPesoBrutoT  =  new BigDecimal(0.00);
	
	/** The valor cajas pallet t. */
	private BigDecimal valorCajasPalletT =  new BigDecimal(0.00); 
	
	/** The valor cajas tendido t. */
	private BigDecimal valorCajasTendidoT =  new BigDecimal(0.00); 
	
	/** The valor cajas t. */
	private BigDecimal valorCajasT =  new BigDecimal(0.00); 
	
	/** The dbl total precio. */
	private BigDecimal dblTotalPrecio =  new BigDecimal(0.00);
	
	/** The dbl valor total neg. */
	private BigDecimal dblValorTotalNeg;
	
	/** The dbl valor fob. */
	private BigDecimal dblValorFOB;
	
	/** The dbl valor fletes. */
	private BigDecimal dblValorFletes;
	
	/** The dbl valor seguro. */
	private BigDecimal dblValorSeguro;
	
	/** The dbl valor otros gastos. */
	private BigDecimal dblValorOtrosGastos;
	
	/** The dbl total valor t. */
	private BigDecimal dblTotalValorT;

	/** The comercio ejb. */
	@EJB
	private ComercioExteriorEJB comercioEjb;	
	
	/** The menu. */
	@ManagedProperty(value="#{menuMB}")
	 private MenuMB menu;

	/**
	 * Instantiates a new ingresar costos inconterm mb.
	 */
	public ConsultarSolicitudPedidoMB(){

	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init(){
		filtro = new FiltroConsultaSolicitudDTO();
	}

	public void consultarDocumentos(){
		listaDocumentos = (ArrayList<DocumentoIncontermDTO>) comercioEjb.consultarDocumentosSolicitudPedido(filtro);		
	}
	
	
	/**
	 * Refrescar totales.
	 */
	public void refrescarTotales() {
		BigDecimal ValorTotal = new BigDecimal(0);
		BigDecimal ValorPesoNeto = new BigDecimal(0);
		BigDecimal ValorPesoBruto = new BigDecimal(0);
		BigDecimal ValorCajas = new BigDecimal(0);		
		BigDecimal ValorCajasTendido = new BigDecimal(0);
		BigDecimal ValorCajasPallet = new BigDecimal(0);

		totalValorT=new BigDecimal(0);
		cantidadValorT=new BigDecimal(0);
		valorPesoNetoT=new BigDecimal(0);
		valorPesoBrutoT=new BigDecimal(0);
		valorCajasTendidoT=new BigDecimal(0);
		valorCajasPalletT=new BigDecimal(0);
		valorCajasT=new BigDecimal(0);
		dblTotalPrecio=new BigDecimal(0);

		if(listaSolicitudPedido!=null && !listaSolicitudPedido.isEmpty()){
			for(ProductoPorClienteComExtDTO pxc:listaSolicitudPedido){
				if (pxc.isBlnIncluirBusqueda()) {

					ValorTotal = pxc.getDblCantidad1ProductoxDocumento().multiply(pxc.getDblPrecioUSD());

					MathContext mc = new MathContext(2, RoundingMode.HALF_UP);

					if (pxc.getDblCantidadXEmbalajeProductoInventarioCE().compareTo(new BigDecimal(0))==1)
					{
						ValorPesoNeto = (pxc.getDblPesoNetoEmbalajeProductoInventarioCE().divide(pxc.getDblCantidadXEmbalajeProductoInventarioCE(),mc))
								.multiply(pxc.getDblCantidad1ProductoxDocumento());
						ValorPesoBruto = (pxc.getDblPesoBrutoEmbalajeProductoInventarioCE().divide(pxc.getDblCantidadXEmbalajeProductoInventarioCE(),mc))
								.multiply(pxc.getDblCantidad1ProductoxDocumento());
						ValorCajas = pxc.getDblCantidad1ProductoxDocumento().divide(pxc.getDblCantidadXEmbalajeProductoInventarioCE(),mc);
					}					
					else
					{
						ValorPesoNeto = new BigDecimal(0);
						ValorPesoBruto = new BigDecimal(0);
						ValorCajas = new BigDecimal(0);
					}				

					if (pxc.getDblCantidadXEmbalajeProductoInventarioCE().compareTo(new BigDecimal(0))==1 
							&& pxc.getDblCantCajasXTendidoProductoInventarioCE() .compareTo(new BigDecimal(0))==1 )
					{
						ValorCajasTendido = (pxc.getDblCantidad1ProductoxDocumento().divide(pxc.getDblCantidadXEmbalajeProductoInventarioCE(),mc))
								.divide(pxc.getDblCantCajasXTendidoProductoInventarioCE(),mc);
					}
					else
					{
						ValorCajasTendido = new BigDecimal(0);
					}

					if (pxc.getDblCantidadXEmbalajeProductoInventarioCE().compareTo(new BigDecimal(0))==1 
							&& pxc.getDblTotalCajasXPalletProductoInventarioCE().compareTo(new BigDecimal(0))==1)
					{
						ValorCajasPallet = (pxc.getDblCantidad1ProductoxDocumento().divide(pxc.getDblCantidadXEmbalajeProductoInventarioCE(),mc))
								.divide(pxc.getDblTotalCajasXPalletProductoInventarioCE(),mc);
					}
					else
					{
						ValorCajasPallet = new BigDecimal(0);
					}

					totalValorT =totalValorT.add(ValorTotal);

					cantidadValorT=cantidadValorT.add(pxc.getDblCantidad1ProductoxDocumento());

					valorPesoNetoT=valorPesoNetoT.add(ValorPesoNeto);
					valorPesoBrutoT=valorPesoBrutoT.add(ValorPesoBruto);
					valorCajasTendidoT=valorCajasTendidoT.add(ValorCajasTendido);
					valorCajasPalletT=valorCajasPalletT.add(ValorCajasPallet);
					valorCajasT=valorCajasT.add(ValorCajas);


					dblTotalPrecio=dblTotalPrecio.add(pxc.getDblPrecioUSD());

					pxc.setDblValorTotalProductoxDocumento(ValorTotal);

					pxc.setDblTotalPesoNeto(ValorPesoNeto);

					pxc.setDblTotalPesoBruto(ValorPesoBruto);

					pxc.setDblTotalCajas(ValorCajas);

					pxc.setDblTotalCajasTendido(ValorCajasTendido);

					pxc.setDblTotalCajasPallet(ValorCajasPallet);

				}
			}
		}


	}


	/**
	 * Gets the seleccionado.
	 *
	 * @return the seleccionado
	 */
	public DocumentoIncontermDTO getSeleccionado() {
		return seleccionado;
	}

	/**
	 * Sets the seleccionado.
	 *
	 * @param seleccionado the new seleccionado
	 */
	public void setSeleccionado(DocumentoIncontermDTO seleccionado) {
		this.seleccionado = seleccionado;
	}

	/**
	 * Consultar solicitud pedido.
	 *
	 * @return the string
	 */
	public String consultarSolicitudPedido(){
		dblValorFOB=seleccionado.getCostoEntrega();
		dblValorFletes=seleccionado.getCostoFlete();
		dblValorSeguro=seleccionado.getCostoSeguro();
		dblValorOtrosGastos=seleccionado.getOtrosGastos();
		dblTotalValorT=dblValorFletes.add(dblValorSeguro).add(dblValorOtrosGastos).add(dblValorFOB);
		dblValorTotalNeg=seleccionado.getValorTotalDocumento().add(dblValorFletes).add(dblValorSeguro).add(dblValorOtrosGastos).add(dblValorFOB);
		//Consultar la lista inconterm poor cliente
		listaTerminoInconterm = comercioEjb.consultarListaIncontermPorCliente(seleccionado.getClientesId());
		listaSolicitudPedido = comercioEjb.consultarListaSolicitudesPedido(seleccionado.getIdDocumento(), seleccionado.getClientesId());
		refrescarTotales();

		return "";
	}
	
	public void cancelar(){
		listaTerminoInconterm = null;
		listaSolicitudPedido = new ArrayList<ProductoPorClienteComExtDTO>();
		totalValorT  =  new BigDecimal(0.00);
		cantidadValorT  =  new BigDecimal(0.00);
		valorPesoNetoT  =  new BigDecimal(0.00);
		valorPesoBrutoT  =  new BigDecimal(0.00);
		valorCajasPalletT =  new BigDecimal(0.00); 
		valorCajasTendidoT =  new BigDecimal(0.00); 
		valorCajasT =  new BigDecimal(0.00); 
		dblTotalPrecio =  new BigDecimal(0.00);
		seleccionado = new DocumentoIncontermDTO();
	}


	/**
	 * Gets the lista documentos.
	 *
	 * @return the lista documentos
	 */
	public ArrayList<DocumentoIncontermDTO> getListaDocumentos() {
		return listaDocumentos;
	}

	/**
	 * Sets the lista documentos.
	 *
	 * @param listaDocumentos the new lista documentos
	 */
	public void setListaDocumentos(ArrayList<DocumentoIncontermDTO> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	/**
	 * Gets the lista termino inconterm.
	 *
	 * @return the lista termino inconterm
	 */
	public List<TerminoIncoterm> getListaTerminoInconterm() {
		return listaTerminoInconterm;
	}

	/**
	 * Sets the lista termino inconterm.
	 *
	 * @param listaTerminoInconterm the new lista termino inconterm
	 */
	public void setListaTerminoInconterm(
			List<TerminoIncoterm> listaTerminoInconterm) {
		this.listaTerminoInconterm = listaTerminoInconterm;
	}

	/**
	 * Gets the lista solicitud pedido.
	 *
	 * @return the lista solicitud pedido
	 */
	public List<ProductoPorClienteComExtDTO> getListaSolicitudPedido() {
		return listaSolicitudPedido;
	}

	/**
	 * Sets the lista solicitud pedido.
	 *
	 * @param listaSolicitudPedido the new lista solicitud pedido
	 */
	public void setListaSolicitudPedido(
			List<ProductoPorClienteComExtDTO> listaSolicitudPedido) {
		this.listaSolicitudPedido = listaSolicitudPedido;
	}

	/**
	 * Gets the total valor t.
	 *
	 * @return the total valor t
	 */
	public BigDecimal getTotalValorT() {
		return totalValorT;
	}

	/**
	 * Sets the total valor t.
	 *
	 * @param totalValorT the new total valor t
	 */
	public void setTotalValorT(BigDecimal totalValorT) {
		this.totalValorT = totalValorT;
	}

	/**
	 * Gets the cantidad valor t.
	 *
	 * @return the cantidad valor t
	 */
	public BigDecimal getCantidadValorT() {
		return cantidadValorT;
	}

	/**
	 * Sets the cantidad valor t.
	 *
	 * @param cantidadValorT the new cantidad valor t
	 */
	public void setCantidadValorT(BigDecimal cantidadValorT) {
		this.cantidadValorT = cantidadValorT;
	}

	/**
	 * Gets the valor peso neto t.
	 *
	 * @return the valor peso neto t
	 */
	public BigDecimal getValorPesoNetoT() {
		return valorPesoNetoT;
	}

	/**
	 * Sets the valor peso neto t.
	 *
	 * @param valorPesoNetoT the new valor peso neto t
	 */
	public void setValorPesoNetoT(BigDecimal valorPesoNetoT) {
		this.valorPesoNetoT = valorPesoNetoT;
	}

	/**
	 * Gets the valor peso bruto t.
	 *
	 * @return the valor peso bruto t
	 */
	public BigDecimal getValorPesoBrutoT() {
		return valorPesoBrutoT;
	}

	/**
	 * Sets the valor peso bruto t.
	 *
	 * @param valorPesoBrutoT the new valor peso bruto t
	 */
	public void setValorPesoBrutoT(BigDecimal valorPesoBrutoT) {
		this.valorPesoBrutoT = valorPesoBrutoT;
	}

	/**
	 * Gets the valor cajas pallet t.
	 *
	 * @return the valor cajas pallet t
	 */
	public BigDecimal getValorCajasPalletT() {
		return valorCajasPalletT;
	}

	/**
	 * Sets the valor cajas pallet t.
	 *
	 * @param valorCajasPalletT the new valor cajas pallet t
	 */
	public void setValorCajasPalletT(BigDecimal valorCajasPalletT) {
		this.valorCajasPalletT = valorCajasPalletT;
	}

	/**
	 * Gets the valor cajas tendido t.
	 *
	 * @return the valor cajas tendido t
	 */
	public BigDecimal getValorCajasTendidoT() {
		return valorCajasTendidoT;
	}

	/**
	 * Sets the valor cajas tendido t.
	 *
	 * @param valorCajasTendidoT the new valor cajas tendido t
	 */
	public void setValorCajasTendidoT(BigDecimal valorCajasTendidoT) {
		this.valorCajasTendidoT = valorCajasTendidoT;
	}

	/**
	 * Gets the valor cajas t.
	 *
	 * @return the valor cajas t
	 */
	public BigDecimal getValorCajasT() {
		return valorCajasT;
	}

	/**
	 * Sets the valor cajas t.
	 *
	 * @param valorCajasT the new valor cajas t
	 */
	public void setValorCajasT(BigDecimal valorCajasT) {
		this.valorCajasT = valorCajasT;
	}

	/**
	 * Gets the dbl total precio.
	 *
	 * @return the dbl total precio
	 */
	public BigDecimal getDblTotalPrecio() {
		return dblTotalPrecio;
	}

	/**
	 * Sets the dbl total precio.
	 *
	 * @param dblTotalPrecio the new dbl total precio
	 */
	public void setDblTotalPrecio(BigDecimal dblTotalPrecio) {
		this.dblTotalPrecio = dblTotalPrecio;
	}


	/**
	 * Gets the lista ubicaciones.
	 *
	 * @return the lista ubicaciones
	 */
	public List<Ubicacion> getListaUbicaciones() {
		if(listaUbicaciones==null){
			Usuario objUsuario;

			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			HttpSession session = (HttpSession) context.getSession(true);
			objUsuario = (Usuario) session.getAttribute("usuarioLogin");

			listaUbicaciones = comercioEjb.consultarUbicacionesPorUsuario(menu.getUsuario().getId());
		}
		return listaUbicaciones;
	}

	/**
	 * Sets the lista ubicaciones.
	 *
	 * @param listaUbicaciones the new lista ubicaciones
	 */
	public void setListaUbicaciones(List<Ubicacion> listaUbicaciones) {
		this.listaUbicaciones = listaUbicaciones;
	}

	/**
	 * Gets the menu.
	 *
	 * @return the menu
	 */
	public MenuMB getMenu() {
		return menu;
	}

	/**
	 * Sets the menu.
	 *
	 * @param menu the new menu
	 */
	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public FiltroConsultaSolicitudDTO getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroConsultaSolicitudDTO filtro) {
		this.filtro = filtro;
	}

	public BigDecimal getDblValorTotalNeg() {
		return dblValorTotalNeg;
	}

	public void setDblValorTotalNeg(BigDecimal dblValorTotalNeg) {
		this.dblValorTotalNeg = dblValorTotalNeg;
	}

	public BigDecimal getDblValorFOB() {
		return dblValorFOB;
	}

	public void setDblValorFOB(BigDecimal dblValorFOB) {
		this.dblValorFOB = dblValorFOB;
	}

	public BigDecimal getDblValorFletes() {
		return dblValorFletes;
	}

	public void setDblValorFletes(BigDecimal dblValorFletes) {
		this.dblValorFletes = dblValorFletes;
	}

	public BigDecimal getDblValorSeguro() {
		return dblValorSeguro;
	}

	public void setDblValorSeguro(BigDecimal dblValorSeguro) {
		this.dblValorSeguro = dblValorSeguro;
	}

	public BigDecimal getDblValorOtrosGastos() {
		return dblValorOtrosGastos;
	}

	public void setDblValorOtrosGastos(BigDecimal dblValorOtrosGastos) {
		this.dblValorOtrosGastos = dblValorOtrosGastos;
	}

	public BigDecimal getDblTotalValorT() {
		return dblTotalValorT;
	}

	public void setDblTotalValorT(BigDecimal dblTotalValorT) {
		this.dblTotalValorT = dblTotalValorT;
	}

	


}
