package com.ssl.jv.gip.web.mb.comercioexterior;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.negocio.dto.DocumentoInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoPorLotesInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Utilidad;

@ManagedBean(name = "generarInstruccionEmbarqueMB")
@SessionScoped
public class GenerarInstruccionEmbarqueMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4940573106269006240L;

	private static final Logger LOGGER = Logger
			.getLogger(GenerarInstruccionEmbarqueMB.class);

	@EJB
	private ComercioExteriorEJBLocal comercioExteriorEJBLocal;

	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;
	private String idUsuario;

	private List<Cliente> listadoClientes;

	private List<DocumentoInstruccionEmbarqueDTO> listadoDocumentos =  new ArrayList<DocumentoInstruccionEmbarqueDTO>();

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;

	private Timestamp currentTimeStamp;

	private Long cliente;	

	private boolean generarInstruccion;

	private Cliente clienteDetalle;

	private List<DocumentoPorLotesInstruccionEmbarqueDTO> listadoDocumentoLotes = new ArrayList<DocumentoPorLotesInstruccionEmbarqueDTO>();

	private SelectItem[] listaAgenteAduana;
	private SelectItem[] listaAgenteAduana2;

	private SelectItem[] listaModalidadEmbarque;
	private SelectItem[] listaIncotermDespacho;	

	private SelectItem[] listaPais;	
	private SelectItem[] listaCiudad;

	private Date dateFechaETA;
	private String strNumeroBooking;
	private String strFleteExterno;	
	private String strIdPais;
	private Long lngIdCiudadDestino;
	private Long intIdAgenteAduana1;
	private Long intIdAgenteAduana2;
	private String strMesEmbarque;
	private String strPuertoEmbarque;
	private Date dateFechaEmbarque;
	private String strNaviera;
	private String strLinea;
	private String strBuque;
	private String strSeguro;
	private Integer intTipoContenedor;	
	private Integer intCantidadContenedores;
	private String strNumeroContenedor;
	private String strSellosSeg;
	private String strPrecintos;
	private Long intIdModalidadEmbarque;
	private Long intIdIncotermDespacho;
	private String strObservacionTerminosTransporte;
	private String strObservacionTerminosTransporte2;

	@PostConstruct
	public void init() {

		generarInstruccion = false;

		currentTimeStamp = new Timestamp(System.currentTimeMillis());
		idUsuario = menu.getUsuario().getId();
		listadoClientes = comercioExteriorEJBLocal.listadoClientesInstruccionEmbarque(idUsuario);
	}

	public void guardarInstruccionEmbarque(ActionEvent ae){

		try{

			TerminosTransporte terminostransporte = new TerminosTransporte();
			terminostransporte.setNaviera(this.strNaviera);
			terminostransporte.setLinea(this.strLinea);
			terminostransporte.setBuque(this.strBuque);
			terminostransporte.setFleteExterno(this.strFleteExterno);
			terminostransporte.setSeguro(this.strSeguro);
			terminostransporte.setTipoContenedor(this.intTipoContenedor);
			terminostransporte.setCantidadContenedores(this.intCantidadContenedores);
			terminostransporte.setNumeroContenedor(this.strNumeroContenedor);
			terminostransporte.setSellosSeg(this.strSellosSeg);
			terminostransporte.setPrecintos(this.strPrecintos);

			ModalidadEmbarque modalidad = new ModalidadEmbarque();
			modalidad.setId(this.intIdModalidadEmbarque);

			terminostransporte.setModalidadEmbarque(modalidad);
			terminostransporte.setObservacion(this.strObservacionTerminosTransporte);
			terminostransporte.setMesEmbarque(this.strMesEmbarque);
			terminostransporte.setIdAgenteAduana1(this.intIdAgenteAduana1);
			terminostransporte.setIdAgenteAduana2(this.intIdAgenteAduana2);
			terminostransporte.setPuertoEmbarque(this.strPuertoEmbarque);

			TerminoIncoterm terminoIncoterm = new TerminoIncoterm();
			terminoIncoterm.setId(this.intIdIncotermDespacho);

			terminostransporte.setTerminoIncoterm(terminoIncoterm);

			Ciudad ciudadDestino = new Ciudad();
			ciudadDestino.setId(this.lngIdCiudadDestino);

			terminostransporte.setCiudade(ciudadDestino);
			terminostransporte.setObservacion2(this.strObservacionTerminosTransporte2);
			terminostransporte.setFechaEmbarqueDate(this.dateFechaEmbarque);
			terminostransporte.setNumeroBooking(this.strNumeroBooking);

			TerminosTransporte terminoNuevo = comercioExteriorEJBLocal.updateTerminoTransporte(terminostransporte);

			comercioExteriorEJBLocal.guardarInstruccionEmbarque(listadoDocumentos, terminoNuevo);

			generarInstruccion = false;
			
			String mensaje = AplicacionMB.getMessage("InstruccionEmbarqueExito_Crear", language);
			String parametros[]=new String[1];
			parametros[0]=""+terminoNuevo.getId();
			mensaje=Utilidad.stringFormat(mensaje, parametros);
			
			this.addMensajeInfo(mensaje);
			
			listadoDocumentos = new ArrayList<DocumentoInstruccionEmbarqueDTO>();
			listadoDocumentoLotes = new ArrayList<DocumentoPorLotesInstruccionEmbarqueDTO>();
			dateFechaETA = null;
			strNumeroBooking = "";
			strFleteExterno = "";	
			strIdPais = "";
			lngIdCiudadDestino = null;
			intIdAgenteAduana1 = null;
			intIdAgenteAduana2 = null;
			strMesEmbarque = "";
			strPuertoEmbarque = "";
			dateFechaEmbarque = null;
			strNaviera = "";
			strLinea = "";
			strBuque = "";
			strSeguro = "";
			intTipoContenedor = null;	
			intCantidadContenedores = null;
			strNumeroContenedor = "";
			strSellosSeg = "";
			strPrecintos = "";
			intIdModalidadEmbarque = null;
			intIdIncotermDespacho = null;
			strObservacionTerminosTransporte = "";
			strObservacionTerminosTransporte2 = "";
		} catch(Exception e){
			LOGGER.error(e);
		}
	}

	public void consultarDocumento(ActionEvent ae){
		listadoDocumentos = comercioExteriorEJBLocal.listadoDocumentosInstruccionEmbarque(cliente);
	}

	public void consultarFactura(ActionEvent ae){

		generarInstruccion = false;

		for(Cliente cli : listadoClientes){
			if(cli.getId().longValue() == cliente.longValue()){
				clienteDetalle = cli;
				generarInstruccion = true;
				break;
			}
		}

		List<DocumentoInstruccionEmbarqueDTO> seleccionados = new ArrayList<DocumentoInstruccionEmbarqueDTO>();

		if(generarInstruccion){

			int conta1 = 0;
			int conta2 = 0;
			StringBuilder cadenaIdDocs = new StringBuilder("");
			StringBuilder cadenaIdDocsMerca = new StringBuilder("");

			if(listadoDocumentos != null && listadoDocumentos.size() > 0){
				for(DocumentoInstruccionEmbarqueDTO dto : listadoDocumentos){
					if(dto.isSeleccionado()){

						if (dto.isSolicitudCafe())//Es de tipo Café
						{
							conta1 = conta1 + 1;
							if (conta1 == 1)
							{
								cadenaIdDocs.append(dto.getId());
							}
							else
							{
								cadenaIdDocs.append("," + dto.getId());  
							}


						}
						else //Mercadeo
						{
							conta2 = conta2 + 1;
							if (conta2 == 1)
							{									   
								cadenaIdDocsMerca.append(dto.getId());
							}
							else
							{
								cadenaIdDocsMerca.append("," + dto.getId());  
							}
						}	

						seleccionados.add(dto);
					}
				}
			}

			String strDocs = cadenaIdDocs.toString();
			String strDocsMerca = cadenaIdDocsMerca.toString();

			listadoDocumentoLotes = comercioExteriorEJBLocal.consultarDocumentosPorLotes(strDocs, strDocsMerca);

		}

	}

	public List<Cliente> getListadoClientes() {
		return listadoClientes;
	}

	public void setListadoClientes(List<Cliente> listadoClientes) {
		this.listadoClientes = listadoClientes;
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public List<DocumentoInstruccionEmbarqueDTO> getListadoDocumentos() {
		return listadoDocumentos;
	}

	public void setListadoDocumentos(
			List<DocumentoInstruccionEmbarqueDTO> listadoDocumentos) {
		this.listadoDocumentos = listadoDocumentos;
	}

	public boolean isGenerarInstruccion() {
		return generarInstruccion;
	}

	public void setGenerarInstruccion(boolean generarInstruccion) {
		this.generarInstruccion = generarInstruccion;
	}

	public Cliente getClienteDetalle() {
		return clienteDetalle;
	}

	public void setClienteDetalle(Cliente clienteDetalle) {
		this.clienteDetalle = clienteDetalle;
	}

	public List<DocumentoPorLotesInstruccionEmbarqueDTO> getListadoDocumentoLotes() {
		return listadoDocumentoLotes;
	}

	public void setListadoDocumentoLotes(
			List<DocumentoPorLotesInstruccionEmbarqueDTO> listadoDocumentoLotes) {
		this.listadoDocumentoLotes = listadoDocumentoLotes;
	}

	public Long getIntIdAgenteAduana1() {
		return intIdAgenteAduana1;
	}

	public void setIntIdAgenteAduana1(Long intIdAgenteAduana1) {
		this.intIdAgenteAduana1 = intIdAgenteAduana1;
	}

	public Long getIntIdAgenteAduana2() {
		return intIdAgenteAduana2;
	}

	public void setIntIdAgenteAduana2(Long intIdAgenteAduana2) {
		this.intIdAgenteAduana2 = intIdAgenteAduana2;
	}

	public String getStrMesEmbarque() {
		return strMesEmbarque;
	}

	public void setStrMesEmbarque(String strMesEmbarque) {
		this.strMesEmbarque = strMesEmbarque;
	}

	public String getStrPuertoEmbarque() {
		return strPuertoEmbarque;
	}

	public void setStrPuertoEmbarque(String strPuertoEmbarque) {
		this.strPuertoEmbarque = strPuertoEmbarque;
	}

	public Date getDateFechaEmbarque() {
		return dateFechaEmbarque;
	}

	public void setDateFechaEmbarque(Date dateFechaEmbarque) {
		this.dateFechaEmbarque = dateFechaEmbarque;
	}

	public String getStrNaviera() {
		return strNaviera;
	}

	public void setStrNaviera(String strNaviera) {
		this.strNaviera = strNaviera;
	}

	public String getStrLinea() {
		return strLinea;
	}

	public void setStrLinea(String strLinea) {
		this.strLinea = strLinea;
	}

	public String getStrBuque() {
		return strBuque;
	}

	public void setStrBuque(String strBuque) {
		this.strBuque = strBuque;
	}

	public String getStrSeguro() {
		return strSeguro;
	}

	public void setStrSeguro(String strSeguro) {
		this.strSeguro = strSeguro;
	}

	public Integer getIntTipoContenedor() {
		return intTipoContenedor;
	}

	public void setIntTipoContenedor(Integer intTipoContenedor) {
		this.intTipoContenedor = intTipoContenedor;
	}

	public Integer getIntCantidadContenedores() {
		return intCantidadContenedores;
	}

	public void setIntCantidadContenedores(Integer intCantidadContenedores) {
		this.intCantidadContenedores = intCantidadContenedores;
	}

	public String getStrNumeroContenedor() {
		return strNumeroContenedor;
	}

	public void setStrNumeroContenedor(String strNumeroContenedor) {
		this.strNumeroContenedor = strNumeroContenedor;
	}

	public String getStrSellosSeg() {
		return strSellosSeg;
	}

	public void setStrSellosSeg(String strSellosSeg) {
		this.strSellosSeg = strSellosSeg;
	}

	public String getStrPrecintos() {
		return strPrecintos;
	}

	public void setStrPrecintos(String strPrecintos) {
		this.strPrecintos = strPrecintos;
	}

	public Long getIntIdModalidadEmbarque() {
		return intIdModalidadEmbarque;
	}

	public void setIntIdModalidadEmbarque(Long intIdModalidadEmbarque) {
		this.intIdModalidadEmbarque = intIdModalidadEmbarque;
	}

	public Long getIntIdIncotermDespacho() {
		return intIdIncotermDespacho;
	}

	public void setIntIdIncotermDespacho(Long intIdIncotermDespacho) {
		this.intIdIncotermDespacho = intIdIncotermDespacho;
	}

	public String getStrObservacionTerminosTransporte() {
		return strObservacionTerminosTransporte;
	}

	public void setStrObservacionTerminosTransporte(
			String strObservacionTerminosTransporte) {
		this.strObservacionTerminosTransporte = strObservacionTerminosTransporte;
	}

	public String getStrObservacionTerminosTransporte2() {
		return strObservacionTerminosTransporte2;
	}

	public void setStrObservacionTerminosTransporte2(
			String strObservacionTerminosTransporte2) {
		this.strObservacionTerminosTransporte2 = strObservacionTerminosTransporte2;
	}

	public Date getDateFechaETA() {
		return dateFechaETA;
	}

	public void setDateFechaETA(Date dateFechaETA) {
		this.dateFechaETA = dateFechaETA;
	}

	public String getStrNumeroBooking() {
		return strNumeroBooking;
	}

	public void setStrNumeroBooking(String strNumeroBooking) {
		this.strNumeroBooking = strNumeroBooking;
	}

	public String getStrFleteExterno() {
		return strFleteExterno;
	}

	public void setStrFleteExterno(String strFleteExterno) {
		this.strFleteExterno = strFleteExterno;
	}

	public String getStrIdPais() {
		return strIdPais;
	}

	public void setStrIdPais(String strIdPais) {
		this.strIdPais = strIdPais;
	}

	public Long getLngIdCiudadDestino() {
		return lngIdCiudadDestino;
	}

	public void setLngIdCiudadDestino(Long lngIdCiudadDestino) {
		this.lngIdCiudadDestino = lngIdCiudadDestino;
	}

	public SelectItem[] getListaAgenteAduana() {
		if(listaAgenteAduana == null){
			List<AgenteAduana> lista = comercioExteriorEJBLocal.consultarAgenteAduana();

			listaAgenteAduana = new SelectItem[lista.size()];

			for (int i = 0; i < lista.size(); i++) {
				listaAgenteAduana[i] = new SelectItem(new Long(((AgenteAduana) lista.get(i)).getId()),
						new String(((AgenteAduana)lista.get(i)).getNombre()));
			}
		}
		return listaAgenteAduana;
	}

	public void setListaAgenteAduana(SelectItem[] listaAgenteAduana) {
		this.listaAgenteAduana = listaAgenteAduana;
	}

	public SelectItem[] getListaAgenteAduana2() {
		if(listaAgenteAduana2 == null){
			List<AgenteAduana> lista = comercioExteriorEJBLocal.consultarAgenteAduana();

			listaAgenteAduana2 = new SelectItem[lista.size()];

			for (int i = 0; i < lista.size(); i++) {
				listaAgenteAduana2[i] = new SelectItem(new Long(((AgenteAduana) lista.get(i)).getId()),
						new String(((AgenteAduana)lista.get(i)).getNombre()));
			}
		}
		return listaAgenteAduana2;
	}

	public void setListaAgenteAduana2(SelectItem[] listaAgenteAduana2) {
		this.listaAgenteAduana2 = listaAgenteAduana2;
	}

	public SelectItem[] getListaModalidadEmbarque() {
		if(listaModalidadEmbarque == null){
			List<ModalidadEmbarque> lista = comercioExteriorEJBLocal.findModalidadEmbarque();

			listaModalidadEmbarque = new SelectItem[lista.size()];

			for (int i = 0; i < lista.size(); i++) {
				listaModalidadEmbarque[i] = new SelectItem(new Long(((ModalidadEmbarque) lista.get(i)).getId()),
						new String(((ModalidadEmbarque)lista.get(i)).getDescripcion()));
			}
		}
		return listaModalidadEmbarque;
	}

	public void setListaModalidadEmbarque(SelectItem[] listaModalidadEmbarque) {
		this.listaModalidadEmbarque = listaModalidadEmbarque;
	}

	public SelectItem[] getListaIncotermDespacho() {
		if(listaIncotermDespacho == null){
			List<TerminoIncoterm> lista = comercioExteriorEJBLocal.findTerminoIncotermAll();
			listaIncotermDespacho = new SelectItem[lista.size()];

			for (int i = 0; i < lista.size(); i++) {
				listaIncotermDespacho[i] = new SelectItem(new Long(((TerminoIncoterm) lista.get(i)).getId()),
						new String(((TerminoIncoterm)lista.get(i)).getDescripcion()));
			}
		}
		return listaIncotermDespacho;
	}

	public void setListaIncotermDespacho(SelectItem[] listaIncotermDespacho) {
		this.listaIncotermDespacho = listaIncotermDespacho;
	}

	public SelectItem[] getListaPais() {

		if(listaPais == null){
			List<Pais> lista = comercioExteriorEJBLocal.findByPaisTodos();
			listaPais = new SelectItem[lista.size()];

			// listaCliente[0] = new SelectItem(new Integer(0), new String("
			// "));

			for (int i = 0; i < lista.size(); i++) {
				listaPais[i] = new SelectItem(new String(((Pais) lista.get(i))
						.getId()), new String(((Pais) lista.get(i))
								.getNombre()));

			}
		}
		return listaPais;
	}

	public void setListaPais(SelectItem[] listaPais) {
		this.listaPais = listaPais;
	}

	public SelectItem[] getListaCiudad() {
		return listaCiudad;
	}

	public void setListaCiudad(SelectItem[] listaCiudad) {
		this.listaCiudad = listaCiudad;
	}

	public void cambioPais(){
		if(strIdPais != null && !strIdPais.equals("")){
			List<Ciudad> lista = comercioExteriorEJBLocal.findCiudadesAll(strIdPais);
			listaCiudad = new SelectItem[lista.size()];

			for (int i = 0; i < lista.size(); i++) {
				listaCiudad[i] = new SelectItem(new Long(
						((Ciudad) lista.get(i)).getId()), new String(
								((Ciudad) lista.get(i)).getNombre()));
			}
		} else {
			listaCiudad = new SelectItem[0];
			lngIdCiudadDestino = null;
		}
	}

	public Timestamp getCurrentTimeStamp() {
		return currentTimeStamp;
	}

	public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
		this.currentTimeStamp = currentTimeStamp;
	}

}
