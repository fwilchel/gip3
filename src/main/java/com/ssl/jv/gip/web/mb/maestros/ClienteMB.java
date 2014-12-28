package com.ssl.jv.gip.web.mb.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.jpa.pojo.MetodoPago;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TipoCanal;
import com.ssl.jv.gip.jpa.pojo.TipoPrecio;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.administracion.UsuariosMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="clienteMB")
@ViewScoped
public class ClienteMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(UsuariosMB.class);
	
	private List<Cliente> clientes;
	private Cliente seleccionado;
	
	@EJB
	private MaestrosEJBLocal servicio;
	
	private Modo modo;
	private Integer language=AplicacionMB.SPANISH;
	
	private List<Pais> listaPaises;
	private List<Ciudad> listaCiudades;
	private List<TipoCanal> listaTiposCanal;
	private List<AgenteAduana> listaAgentesAduana;
	private List<MetodoPago> listaMetodosPago;
	private List<CuentaContable> listaCuentaContable;
	private List<TipoPrecio> listaTiposPrecio;
	
	public ClienteMB(){
		
	}
	
	@PostConstruct
	public void init(){
		clientes = servicio.consultarClientes();
	}
	
	public void nuevo(){
		seleccionado=new Cliente();
		seleccionado.setAgenteAduana(new AgenteAduana());
		seleccionado.setCuentaContable(new CuentaContable());
		seleccionado.setMetodoPago(new MetodoPago());
		seleccionado.setTerminoIncoterms(new ArrayList<TerminoIncoterm>());
		seleccionado.setTipoCanal(new TipoCanal());
		seleccionado.setTipoPrecio(new TipoPrecio());
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
		try{
			if (this.modo.equals(Modo.CREACION)){
				this.seleccionado = this.servicio.crearCliente(this.seleccionado);
				if(this.clientes == null){
					this.clientes = new ArrayList<Cliente>();
				}
				clientes = servicio.consultarClientes();
				this.nuevo();
			}else{
				this.servicio.actualizarCliente(this.seleccionado);
				clientes = servicio.consultarClientes();
			}

			this.addMensajeInfo("Cliente almacenado exitosamente");

		}catch(EJBTransactionRolledbackException e){
			if (this.isException(e, "dist_termino_incoterm_x_medio_transporte_key")){
				this.addMensajeError(AplicacionMB.getMessage("maestroTerminoIncotermMedioTransUnique", language));
			}
			LOGGER.error(e);
		}catch(Exception e){
			this.addMensajeError(AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
			LOGGER.error(e);
		}

	}
	
	public boolean isCreacion(){
		if (this.modo!=null && this.modo.equals(Modo.CREACION)){
			return true;
		}else{
			return false;
		}
	}
	

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Cliente seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}

	public List<Pais> getListaPaises() {
		return listaPaises;
	}

	public void setListaPaises(List<Pais> listaPaises) {
		this.listaPaises = listaPaises;
	}

	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public List<TipoCanal> getListaTiposCanal() {
		return listaTiposCanal;
	}

	public void setListaTiposCanal(List<TipoCanal> listaTiposCanal) {
		this.listaTiposCanal = listaTiposCanal;
	}

	public List<AgenteAduana> getListaAgentesAduana() {
		return listaAgentesAduana;
	}

	public void setListaAgentesAduana(List<AgenteAduana> listaAgentesAduana) {
		this.listaAgentesAduana = listaAgentesAduana;
	}

	public List<MetodoPago> getListaMetodosPago() {
		return listaMetodosPago;
	}

	public void setListaMetodosPago(List<MetodoPago> listaMetodosPago) {
		this.listaMetodosPago = listaMetodosPago;
	}

	public List<CuentaContable> getListaCuentaContable() {
		return listaCuentaContable;
	}

	public void setListaCuentaContable(List<CuentaContable> listaCuentaContable) {
		this.listaCuentaContable = listaCuentaContable;
	}

	public List<TipoPrecio> getListaTiposPrecio() {
		return listaTiposPrecio;
	}

	public void setListaTiposPrecio(List<TipoPrecio> listaTiposPrecio) {
		this.listaTiposPrecio = listaTiposPrecio;
	}
	
	
	
	

}
