package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.FactsCurrencyConversion;
import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ComunEJB;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;

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
@ManagedBean(name="generarCLMB")
@SessionScoped
public class GenerarCostosLogisticosMB extends UtilMB{

	private static final Logger LOGGER = Logger.getLogger(GenerarCostosLogisticosMB.class);
	
	private List<Cliente> clientes;
	private List<TerminoIncoterm> listaTerminoInconterm;
	private List<ItemCostoLogistico> puertosNacionales;
	private List<ItemCostoLogistico> puertosInternacionalesNacionales;
	private List<Pais> paises;
	private Long terminoIncoterm;
	private FactsCurrencyConversion trm;
	private String puerto;
	private String puertos;
	private String pais;
	private Long cliente;
	
	@ManagedProperty(value="#{menuMB}")
	private MenuMB menu;
	
	@EJB
	private MaestrosEJBLocal maestrosEjb;
	
	@ManagedProperty(value="#{aplicacionMB}")
	private AplicacionMB appMB;

	@EJB
	private ComercioExteriorEJBLocal comercioEjb;
	
	@EJB
	private ComunEJB comunEJB;
	
	private Integer language=AplicacionMB.SPANISH;
	private Long idCliente;
	
	public GenerarCostosLogisticosMB(){
		
	}
	
	@PostConstruct
	public void init(){
		clientes=this.maestrosEjb.consultarClientes();
		Collections.sort(clientes);
		trm=this.maestrosEjb.getTRMDian(new Date());
		paises = comunEJB.consultarPaises();
	}
	
	public AplicacionMB getAppMB() {
		return appMB;
	}

	public void setAppMB(AplicacionMB appMB) {
		this.appMB = appMB;
	}
	
	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}
	
	public String cargarTerminos(){
		//Consultar la lista inconterm poor cliente
		listaTerminoInconterm = comercioEjb.consultarListaIncontermPorCliente(idCliente);
		//listaSolicitudPedido = comercioEjb.consultarListaSolicitudesPedido(seleccionado.getIdDocumento(), seleccionado.getClientesId());
		//listaProductosMostrarUno = new ArrayList<ProductoPorClienteComExtDTO>();

		return "";
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<TerminoIncoterm> getListaTerminoInconterm() {
		return listaTerminoInconterm;
	}

	public void setListaTerminoInconterm(List<TerminoIncoterm> listaTerminoInconterm) {
		this.listaTerminoInconterm = listaTerminoInconterm;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}	
	
	public void generarCostosLogisticos(){
		//this.comercioEjb.generarCostosLogisticos(idCliente, documentos, terminoIncoterm, puerto, puertos, this.trm.getId());
	}

	public List<ItemCostoLogistico> getPuertosNacionales() {
		return puertosNacionales;
	}

	public void setPuertosNacionales(List<ItemCostoLogistico> puertosNacionales) {
		this.puertosNacionales = puertosNacionales;
	}

	public List<ItemCostoLogistico> getPuertosInternacionalesNacionales() {
		return puertosInternacionalesNacionales;
	}

	public void setPuertosInternacionalesNacionales(
			List<ItemCostoLogistico> puertosInternacionalesNacionales) {
		this.puertosInternacionalesNacionales = puertosInternacionalesNacionales;
	}

	public Long getTerminoIncoterm() {
		return terminoIncoterm;
	}

	public void setTerminoIncoterm(Long terminoIncoterm) {
		this.terminoIncoterm = terminoIncoterm;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public String getPuertos() {
		return puertos;
	}

	public void setPuertos(String puertos) {
		this.puertos = puertos;
	}

}
