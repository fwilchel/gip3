package com.ssl.jv.gip.web.mb.maestros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.MetodoPago;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TipoCanal;
import com.ssl.jv.gip.jpa.pojo.TipoPrecio;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.administracion.UsuariosMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name = "clienteMB")
@ViewScoped
public class ClienteMB extends UtilMB {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = Logger.getLogger(UsuariosMB.class);

  private List<Cliente> clientes;
  private Cliente seleccionado;
  private String idPais;

  @EJB
  private MaestrosEJBLocal servicio;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  private Modo modo;
  private Integer language = AplicacionMB.SPANISH;

  private List<SelectItem> listaPaises;
  private List<Ciudad> listaCiudades;
  private List<TipoCanal> listaTiposCanal;
  private List<AgenteAduana> listaAgentesAduana;
  private List<MetodoPago> listaMetodosPago;
  private List<CuentaContable> listaCuentaContable;
  private List<TipoPrecio> listaTiposPrecio;
  private List<TerminoIncoterm> listaTerminosIncoterm;
  private List<TerminoIncoterm> listaTerminosIncotermSeleccionados = new ArrayList<TerminoIncoterm>();
  private boolean isEditar;

  @PostConstruct
  public void init() {
	// por defecto el pais seleccionado es colombia
	idPais = "CO";
	cargarListaClientes();
  }

  private void cargarListaClientes() {
	clientes = servicio.consultarClientes();
  }

  public void onAddEvent() {
	this.modo = Modo.CREACION;
	seleccionado = new Cliente();
	listaTerminosIncotermSeleccionados = new ArrayList<>();
	this.isEditar = false;
	initEdit();
  }

  public void onEditEvent() {
	this.modo = Modo.EDICION;
	this.isEditar = true;
	this.idPais = this.seleccionado.getCiudad().getIdPais();
	this.listaTerminosIncotermSeleccionados = this.seleccionado.getTerminoIncoterms();
	initEdit();
  }

  private void initEdit() {
	cargarListaPaises();
	cargarListaCiudades();
	cargarListaTerminosIncoterm();
	preLoad();
  }

  public void guardar() {
	try {
	  this.seleccionado.setTerminoIncoterms(listaTerminosIncotermSeleccionados);
	  preSave();
	  // auditoria
	  LogAuditoria auditoria = new LogAuditoria();
	  auditoria.setIdUsuario(menu.getUsuario().getId());
	  auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
	  if (this.modo.equals(Modo.CREACION)) {
		this.seleccionado = this.servicio.crearCliente(this.seleccionado, auditoria);
		onAddEvent();
		this.addMensajeInfo("Cliente adicionado exitosamente");
	  } else {
		this.servicio.actualizarCliente(this.seleccionado, auditoria);
		this.addMensajeInfo("Cliente actualizado exitosamente");
	  }
	  cargarListaClientes();
	} catch (EJBTransactionRolledbackException e) {
	  if (this.isException(e, "dist_termino_incoterm_x_medio_transporte_key")) {
		this.addMensajeError(AplicacionMB.getMessage("maestroClienteMsgValidationID", language));
	  } else if (this.isException(e, "uk_registro_nit")) {
		this.addMensajeError(AplicacionMB.getMessage("maestroClienteMsgValidationNit", language));
	  } else {
		this.addMensajeError("Error");
	  }	  
	  LOGGER.error(e);
	} catch (Exception e) {
	  this.addMensajeError(AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
	  LOGGER.error(e);
	} finally {
	  preLoad();
	}
  }

  private void preLoad() {
	if (seleccionado.getAgenteAduana() == null) {
	  seleccionado.setAgenteAduana(new AgenteAduana());
	}
	if (seleccionado.getMetodoPago() == null) {
	  seleccionado.setMetodoPago(new MetodoPago());
	}
	if (seleccionado.getCuentaContable() == null) {
	  seleccionado.setCuentaContable(new CuentaContable());
	}
	if (seleccionado.getTipoCanal() == null) {
	  seleccionado.setTipoCanal(new TipoCanal());
	}
	if (seleccionado.getTipoPrecio() == null) {
	  seleccionado.setTipoPrecio(new TipoPrecio());
	}
	if (seleccionado.getCiudad() == null) {
	  seleccionado.setCiudad(new Ciudad());
	}
	if (seleccionado.getTerminoIncoterms() == null) {
	  seleccionado.setTerminoIncoterms(new ArrayList<TerminoIncoterm>());
	}
  }

  /**
   * 
   */
  private void preSave() {
	if (this.seleccionado.getAgenteAduana() != null && this.seleccionado.getAgenteAduana().getId().equals(0l)) {
	  this.seleccionado.setAgenteAduana(null);
	}
	if (this.seleccionado.getMetodoPago() != null && this.seleccionado.getMetodoPago().getId().equals(0l)) {
	  this.seleccionado.setMetodoPago(null);
	}
	if (this.seleccionado.getCuentaContable() != null && this.seleccionado.getCuentaContable().getId().equals(0l)) {
	  this.seleccionado.setCuentaContable(null);
	}
	if (this.seleccionado.getTipoPrecio() != null && this.seleccionado.getTipoPrecio().getId().equals(0l)) {
	  this.seleccionado.setTipoPrecio(null);
	}
  }

  public boolean isCreacion() {
	if (this.modo != null && this.modo.equals(Modo.CREACION)) {
	  return true;
	} else {
	  return false;
	}
  }

  public void cambioPais(ValueChangeEvent ev) {
	idPais = (String) ev.getNewValue();
	cargarListaCiudades();
  }

  private void cargarListaPaises() {
	if (listaPaises == null) {
	  listaPaises = new ArrayList<>();
	}
	listaPaises.add(new SelectItem("CO", "Colombia"));
  }

  private void cargarListaCiudades() {
	if (idPais != null && !idPais.isEmpty()) {
	  listaCiudades = servicio.consultarCiudadesPorPais(idPais);
	  Collections.sort(listaCiudades, new Comparator<Ciudad>() {
		@Override
		public int compare(Ciudad ciudad, Ciudad ciudad2) {
		  return ciudad.getNombre().compareTo(ciudad2.getNombre());
		}
	  });
	}
  }

  private void cargarListaTerminosIncoterm() {
	listaTerminosIncoterm = servicio.consultarTerminoIncotermActivo();
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
  }

  public List<SelectItem> getListaPaises() {
	return listaPaises;
  }

  public void setListaPaises(List<SelectItem> listaPaises) {
	this.listaPaises = listaPaises;
  }

  public List<Ciudad> getListaCiudades() {
	return listaCiudades;
  }

  public void setListaCiudades(List<Ciudad> listaCiudades) {
	this.listaCiudades = listaCiudades;
  }

  public List<TipoCanal> getListaTiposCanal() {
	if (listaTiposCanal == null || listaTiposCanal.isEmpty()) {
	  listaTiposCanal = servicio.consultarTiposCanal();

	  Collections.sort(listaTiposCanal, new Comparator<TipoCanal>() {
		@Override
		public int compare(TipoCanal tipoCanal, TipoCanal tipoCanal2) {

		  return tipoCanal.getNombre().compareTo(tipoCanal2.getNombre());
		}
	  });
	}

	return listaTiposCanal;
  }

  public void setListaTiposCanal(List<TipoCanal> listaTiposCanal) {
	this.listaTiposCanal = listaTiposCanal;
  }

  public List<AgenteAduana> getListaAgentesAduana() {
	if (listaAgentesAduana == null || listaAgentesAduana.isEmpty()) {
	  listaAgentesAduana = servicio.consultarAgentesAduana();

	  Collections.sort(listaAgentesAduana, new Comparator<AgenteAduana>() {
		@Override
		public int compare(AgenteAduana agenteAduana, AgenteAduana agenteAduana2) {

		  return agenteAduana.getNombre().compareTo(agenteAduana2.getNombre());
		}
	  });
	}
	return listaAgentesAduana;
  }

  public void setListaAgentesAduana(List<AgenteAduana> listaAgentesAduana) {
	this.listaAgentesAduana = listaAgentesAduana;
  }

  public List<MetodoPago> getListaMetodosPago() {
	if (listaMetodosPago == null || listaMetodosPago.isEmpty()) {
	  listaMetodosPago = servicio.consultarMetodosPago();

	  Collections.sort(listaMetodosPago, new Comparator<MetodoPago>() {
		@Override
		public int compare(MetodoPago metodoPago, MetodoPago metodoPago2) {

		  return metodoPago.getDescripcion().compareTo(metodoPago2.getDescripcion());
		}
	  });
	}
	return listaMetodosPago;
  }

  public void setListaMetodosPago(List<MetodoPago> listaMetodosPago) {
	this.listaMetodosPago = listaMetodosPago;
  }

  public List<CuentaContable> getListaCuentaContable() {
	if (listaCuentaContable == null || listaCuentaContable.isEmpty()) {
	  listaCuentaContable = servicio.consultarCuentasContables();

	  Collections.sort(listaCuentaContable, new Comparator<CuentaContable>() {
		@Override
		public int compare(CuentaContable cuentaContable, CuentaContable cuentaContable2) {

		  return cuentaContable.getDescripcion().compareTo(cuentaContable2.getDescripcion());
		}
	  });
	}
	return listaCuentaContable;
  }

  public void setListaCuentaContable(List<CuentaContable> listaCuentaContable) {
	this.listaCuentaContable = listaCuentaContable;
  }

  public List<TipoPrecio> getListaTiposPrecio() {
	if (listaTiposPrecio == null || listaTiposPrecio.isEmpty()) {
	  listaTiposPrecio = servicio.consultarTiposPrecio();
	}
	return listaTiposPrecio;
  }

  public void setListaTiposPrecio(List<TipoPrecio> listaTiposPrecio) {
	this.listaTiposPrecio = listaTiposPrecio;
  }

  public List<TerminoIncoterm> getListaTerminosIncoterm() {
	return listaTerminosIncoterm;
  }

  public void setListaTerminosIncoterm(List<TerminoIncoterm> listaTerminosIncoterm) {
	this.listaTerminosIncoterm = listaTerminosIncoterm;
  }

  public String getIdPais() {
	return idPais;
  }

  public void setIdPais(String idPais) {
	this.idPais = idPais;
  }

  public List<TerminoIncoterm> getListaTerminosIncotermSeleccionados() {
	return listaTerminosIncotermSeleccionados;
  }

  public void setListaTerminosIncotermSeleccionados(List<TerminoIncoterm> listaTerminosIncotermSeleccionados) {
	this.listaTerminosIncotermSeleccionados = listaTerminosIncotermSeleccionados;
  }

  public boolean isEditar() {
	return isEditar;
  }

  public void setEditar(boolean isEditar) {
	this.isEditar = isEditar;
  }

  /**
   * @param menu
   *          the menu to set
   */
  public void setMenu(MenuMB menu) {
	this.menu = menu;
  }

}
