package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.ArrayList;
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
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;
import com.ssl.jv.gip.negocio.dto.CostoLogisticoDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoCostosLogisticosDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
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
	private List<TerminoIncotermXMedioTransporte> incoterms;
	private List<String> puertosNacionales;
	private List<String> puertosInternacionales;
	private List<Pais> paises;
	private Long terminoIncoterm;
	private FactsCurrencyConversion trm;
	private String puertoNal="";
	private String puertoInternal="";
	private String pais="";
	private Long cliente;
	private List<CostoLogisticoDTO> costos;
	private List<DocumentoCostosLogisticosDTO> solicitudes = new ArrayList<DocumentoCostosLogisticosDTO>();
	
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
	
	public GenerarCostosLogisticosMB(){
		
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		clientes=this.maestrosEjb.consultarClientes();
		Collections.sort(clientes);
		trm=this.maestrosEjb.getTRMDian(new Date());
		paises = comunEJB.consultarPaisesTodos();
		Collections.sort(paises);
		this.incoterms=this.maestrosEjb.consultarTerminoIncotermXMedioTransporte();
		this.puertosNacionales = this.comercioEjb.consultarPuertosNacionales();
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

	public List<DocumentoCostosLogisticosDTO> getSolicitudes() {
		return solicitudes;
	}

	public List<CostoLogisticoDTO> getCostos() {
		return costos;
	}

	public void setCostos(List<CostoLogisticoDTO> costos) {
		this.costos = costos;
	}

	public void setSolicitudes(List<DocumentoCostosLogisticosDTO> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public void generarCostosLogisticos(){
		List<Long> documentos = new ArrayList<Long>();
		for (DocumentoCostosLogisticosDTO r:this.solicitudes){
			if (r.getSeleccionada()){
				documentos.add(r.getIdDocumento());
			}
		}
		TerminoIncotermXMedioTransporte timt1=null;
		for (TerminoIncotermXMedioTransporte timt:this.incoterms){
			if (timt.getId().equals(this.terminoIncoterm)){
				timt1=timt;
				break;
			}
		}
		costos=this.comercioEjb.generarCostosLogisticos(cliente, documentos, timt1, puertoNal, puertoInternal, this.trm.getId());
	}

	public Long getTerminoIncoterm() {
		return terminoIncoterm;
	}

	public void setTerminoIncoterm(Long terminoIncoterm) {
		this.terminoIncoterm = terminoIncoterm;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public List<TerminoIncotermXMedioTransporte> getIncoterms() {
		return incoterms;
	}

	public void setIncoterms(List<TerminoIncotermXMedioTransporte> incoterms) {
		this.incoterms = incoterms;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPuertoNal() {
		return puertoNal;
	}

	public void setPuertoNal(String puertoNal) {
		this.puertoNal = puertoNal;
	}

	public String getPuertoInternal() {
		return puertoInternal;
	}

	public void setPuertoInternal(String puertoInternal) {
		this.puertoInternal = puertoInternal;
	}

	public List<String> getPuertosNacionales() {
		return puertosNacionales;
	}

	public void setPuertosNacionales(List<String> puertosNacionales) {
		this.puertosNacionales = puertosNacionales;
	}

	public List<String> getPuertosInternacionales() {
		return puertosInternacionales;
	}

	public void setPuertosInternacionales(List<String> puertosInternacionales) {
		this.puertosInternacionales = puertosInternacionales;
	}
	
	public void cargarPuertosInternacionales(){
		this.puertosInternacionales=this.comercioEjb.consultarPuertosInternacionales(this.pais);
	}
	
	public void consultarSolicitudes(){
		this.solicitudes=this.comercioEjb.consultarDocumentosCostosLogisticos(this.cliente);
	}

}
