package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.UtilMB;


/**
 * The Class SolicitudPedidoMB.
 */
@ManagedBean(name="solicitudPedidoMB")
@SessionScoped
public class SolicitudPedidoMB extends UtilMB{

	/** The lista documentos. */
	private ArrayList<DocumentoIncontermDTO> listaDocumentos = new ArrayList<DocumentoIncontermDTO>();

	private List<TerminoIncoterm> listaTerminoInconterm;

	private List<Ubicacion> listaUbicaciones;

	private List<ProductoPorClienteComExtDTO> listaSolicitudPedido = new ArrayList<ProductoPorClienteComExtDTO>();

	private List<ProductoPorClienteComExtDTO> listaProductosMostrarUno = new ArrayList<ProductoPorClienteComExtDTO>();

	/** The seleccionado. */
	private DocumentoIncontermDTO seleccionado;

	private BigDecimal totalValorT  =  new BigDecimal(0.00);
	private BigDecimal cantidadValorT  =  new BigDecimal(0.00);
	private BigDecimal valorPesoNetoT  =  new BigDecimal(0.00);
	private BigDecimal valorPesoBrutoT  =  new BigDecimal(0.00);
	private BigDecimal valorCajasPalletT =  new BigDecimal(0.00); 
	private BigDecimal valorCajasTendidoT =  new BigDecimal(0.00); 
	private BigDecimal valorCajasT =  new BigDecimal(0.00); 
	private BigDecimal dblTotalPrecio =  new BigDecimal(0.00);

	/** The comercio ejb. */
	@EJB
	private ComercioExteriorEJB comercioEjb;	

	/**
	 * Instantiates a new ingresar costos inconterm mb.
	 */
	public SolicitudPedidoMB(){

	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init(){
		listaDocumentos = (ArrayList<DocumentoIncontermDTO>) comercioEjb.consultarDocumentosSolicitudPedido();
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

	public void consultarListaProductosxCli(){

		listaProductosMostrarUno = new ArrayList<ProductoPorClienteComExtDTO>();

		StringBuilder cadenaIdPrds = new StringBuilder("");

		if(listaSolicitudPedido!=null && !listaSolicitudPedido.isEmpty()){
			int x = 0;
			for(ProductoPorClienteComExtDTO prdxcom:listaSolicitudPedido){
				if (x == listaSolicitudPedido.size() - 1){
					cadenaIdPrds.append(prdxcom.getIntIdProductoInventario());
				}
				else{
					cadenaIdPrds.append(prdxcom.getIntIdProductoInventario() + ",");
				}
				x++;
			}
		}

		listaProductosMostrarUno = comercioEjb.consultarListaProductosPorClienteCE(seleccionado.getClientesId(),cadenaIdPrds.toString(),seleccionado.getSolicitudCafe() );

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
		//Consultar la lista inconterm poor cliente
		listaTerminoInconterm = comercioEjb.consultarListaIncontermPorCliente(seleccionado.getClientesId());
		listaSolicitudPedido = comercioEjb.consultarListaSolicitudesPedido(seleccionado.getIdDocumento(), seleccionado.getClientesId());
		listaProductosMostrarUno = new ArrayList<ProductoPorClienteComExtDTO>();

		return "";
	}

	/**
	 * Insertar producto tabla.
	 */
	public void insertarProductoTabla() {
		Iterator<ProductoPorClienteComExtDTO> iterator = listaProductosMostrarUno.iterator();
		ProductoPorClienteComExtDTO objProducto = null;

		Boolean yaEsta = false;
		while (iterator.hasNext()) {
			objProducto = iterator.next();

			if (objProducto.isBlnIncluirBusqueda()) {		

				for(ProductoPorClienteComExtDTO vProducto:listaSolicitudPedido){
					if(vProducto.getIntIdProductoInventario().equals(objProducto.getIntIdProductoInventario())){
						yaEsta = true;
						break;
					}
				}

				if (!yaEsta) {
					listaSolicitudPedido.add(objProducto);
				}
			}
			yaEsta = false;
		}
		objProducto = null;
	}	

	/**
	 * Guardar ajustes pedido.
	 */
	public void guardarAjustesSP(){

		this.refrescarTotales();

		seleccionado.setValorTotalDocumento(totalValorT);
		seleccionado.setIdUbicacionOrigen(seleccionado.getIdUbicacionDestino());
		seleccionado.setTotalPesoNeto(valorPesoNetoT);
		seleccionado.setTotalPesoBruto(valorPesoBrutoT);
		seleccionado.setTotalTendidos(valorCajasTendidoT);
		seleccionado.setTotalPallets(valorCajasPalletT);

		comercioEjb.guardarSolicitudPedido(seleccionado, listaSolicitudPedido);

		this.addMensajeInfo("Se almaceno la solicitud de pedido exitosamente");
		listaDocumentos = (ArrayList<DocumentoIncontermDTO>) comercioEjb.consultarDocumentosCostosInconterm();

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

	public List<TerminoIncoterm> getListaTerminoInconterm() {
		return listaTerminoInconterm;
	}

	public void setListaTerminoInconterm(
			List<TerminoIncoterm> listaTerminoInconterm) {
		this.listaTerminoInconterm = listaTerminoInconterm;
	}

	public List<ProductoPorClienteComExtDTO> getListaSolicitudPedido() {
		return listaSolicitudPedido;
	}

	public void setListaSolicitudPedido(
			List<ProductoPorClienteComExtDTO> listaSolicitudPedido) {
		this.listaSolicitudPedido = listaSolicitudPedido;
	}

	public BigDecimal getTotalValorT() {
		return totalValorT;
	}

	public void setTotalValorT(BigDecimal totalValorT) {
		this.totalValorT = totalValorT;
	}

	public BigDecimal getCantidadValorT() {
		return cantidadValorT;
	}

	public void setCantidadValorT(BigDecimal cantidadValorT) {
		this.cantidadValorT = cantidadValorT;
	}

	public BigDecimal getValorPesoNetoT() {
		return valorPesoNetoT;
	}

	public void setValorPesoNetoT(BigDecimal valorPesoNetoT) {
		this.valorPesoNetoT = valorPesoNetoT;
	}

	public BigDecimal getValorPesoBrutoT() {
		return valorPesoBrutoT;
	}

	public void setValorPesoBrutoT(BigDecimal valorPesoBrutoT) {
		this.valorPesoBrutoT = valorPesoBrutoT;
	}

	public BigDecimal getValorCajasPalletT() {
		return valorCajasPalletT;
	}

	public void setValorCajasPalletT(BigDecimal valorCajasPalletT) {
		this.valorCajasPalletT = valorCajasPalletT;
	}

	public BigDecimal getValorCajasTendidoT() {
		return valorCajasTendidoT;
	}

	public void setValorCajasTendidoT(BigDecimal valorCajasTendidoT) {
		this.valorCajasTendidoT = valorCajasTendidoT;
	}

	public BigDecimal getValorCajasT() {
		return valorCajasT;
	}

	public void setValorCajasT(BigDecimal valorCajasT) {
		this.valorCajasT = valorCajasT;
	}

	public BigDecimal getDblTotalPrecio() {
		return dblTotalPrecio;
	}

	public void setDblTotalPrecio(BigDecimal dblTotalPrecio) {
		this.dblTotalPrecio = dblTotalPrecio;
	}

	public List<ProductoPorClienteComExtDTO> getListaProductosMostrarUno() {
		return listaProductosMostrarUno;
	}

	public void setListaProductosMostrarUno(
			List<ProductoPorClienteComExtDTO> listaProductosMostrarUno) {
		this.listaProductosMostrarUno = listaProductosMostrarUno;
	}

	public List<Ubicacion> getListaUbicaciones() {
		if(listaUbicaciones==null){
			Usuario objUsuario;

			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			HttpSession session = (HttpSession) context.getSession(true);
			objUsuario = (Usuario) session.getAttribute("usuarioLogin");

			//TODO Cambiar cuando este el usuario en sesion
			listaUbicaciones = comercioEjb.consultarUbicacionesPorUsuario("79893101");
		}
		return listaUbicaciones;
	}

	public void setListaUbicaciones(List<Ubicacion> listaUbicaciones) {
		this.listaUbicaciones = listaUbicaciones;
	}




}
