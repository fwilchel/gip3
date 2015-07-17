package com.ssl.jv.gip.web.mb.maestros;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.administracion.UsuariosMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name = "puntoEntregaMB")
@ViewScoped
public class PuntoEntregaMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = Logger.getLogger(UsuariosMB.class);

  private List<PuntoVenta> puntoVenta;
  private PuntoVenta seleccionado;
  private String idPais;

  @EJB
  private MaestrosEJBLocal servicio;

  private Modo modo;
  private Integer language = AplicacionMB.SPANISH;

  private List<Ciudad> listaCiudades;
  private List<Cliente> listaClientes;

  private boolean isEditar;

  private LazyDataModel<PuntoVenta> modelo;
  private PuntoVenta filtro;

  private String campoOrden;
  private SortOrder orden;
  private List<PuntoVenta> datos;

  private Modo modoDetalle;

  @EJB
  private MaestrosEJBLocal maestroFacade;

  private UploadedFile uploadedFile;

  public PuntoEntregaMB() {

  }

  @PostConstruct
  public void init() {

	modelo = new LazyPuntoVentaDataModel();

	puntoVenta = servicio.consultarPuntoEntrega();
	// listaTerminosIncoterm = servicio.consultarTerminoIncotermActivo();
  }

  private class LazyPuntoVentaDataModel extends LazyDataModel<PuntoVenta> {

	/**
     *
     */
	private static final long serialVersionUID = 283497341126330045L;
	private List<PuntoVenta> datos;

	@Override
	public Object getRowKey(PuntoVenta pi) {
	  return pi.getId();
	}

	@Override
	public PuntoVenta getRowData(String rowKey) {
	  for (PuntoVenta pi : datos) {
		if (pi.getId().toString().equals(rowKey)) {
		  return pi;
		}
	  }
	  return null;
	}

	@Override
	public List<PuntoVenta> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
	  campoOrden = sortField;
	  orden = sortOrder;
	  // Object rta[]=maestrosEjb.consultarProductos(filtro, first, pageSize,
	  // sortField, sortOrder, true);
	  // Object rta[]= servicio.consultarPuntoEntrega(filtro, first, pageSize,
	  // sortField, sortOrder, true);

	  // this.setRowCount(((Long)rta[0]).intValue());
	  // rta=maestrosEjb.consultarProductos(filtro, first, pageSize, sortField,
	  // sortOrder, false);
	  // rta= servicio.consultarPuntoEntrega(filtro, first, pageSize, sortField,
	  // sortOrder, true);
	  // datos=(List<PuntoVenta>)rta[1];
	  return datos;
	}

  }

  private void initEdit() {
	if (seleccionado.getCiudade() == null) {
	  seleccionado.setCiudade(new Ciudad());
	}
	if (seleccionado.getCliente() == null) {
	  seleccionado.setCliente(new Cliente());
	}
	if (seleccionado.getUsuario() == null) {
	  seleccionado.setUsuario(new Usuario());
	}
	if (seleccionado.getUbicacion() == null) {
	  seleccionado.setUbicacion(new Ubicacion());
	}
  }

  public void nuevo() {
	seleccionado = new PuntoVenta();
	this.modo = Modo.CREAR;

	

	this.initEdit();
  }

  public void guardar() {
	try {
	  if (this.seleccionado.getUbicacion() == null || this.seleccionado.getUbicacion().getId() == null || this.seleccionado.getUbicacion().getId() == 0L) {
		this.seleccionado.setUbicacion(null);
	  }

	  if (this.modo.equals(Modo.CREAR)) {
		this.seleccionado = this.servicio.crearPuntoVenta(this.seleccionado);
		if (this.puntoVenta == null) {
		  this.puntoVenta = new ArrayList<PuntoVenta>();
		}
		this.nuevo();
	  } else {
		this.servicio.actualizarPuntoVenta(this.seleccionado);
	  }

	  puntoVenta = servicio.consultarPuntoEntrega();

	  this.addMensajeInfo("Punto Venta almacenado exitosamente");

	} catch (EJBTransactionRolledbackException e) {
	  if (this.isException(e, "punto_venta_usuario_unique")) {
		this.addMensajeError("Este usuario ya fue asignado a otro Punto");
	  } else {
		this.addMensajeError("Error al guardar el registro");
	  }
	  LOGGER.error(e);
	} catch (Exception e) {
	  this.addMensajeError(AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
	  LOGGER.error(e);
	} finally {
	  this.initEdit();
	}

  }

  public boolean isCreacion() {
	if (this.modo != null && this.modo.equals(Modo.CREAR)) {
	  return true;
	} else {
	  return false;
	}
  }

  public Modo getModo() {
	return modo;
  }

  public void setModo(Modo modo) {
	this.modo = modo;
  }

  public PuntoVenta getSeleccionado() {
	return seleccionado;
  }

  public void setSeleccionado(PuntoVenta seleccionado) {
	this.seleccionado = seleccionado;
	this.modo = Modo.EDITAR;
	this.isEditar = true;

	if (seleccionado.getUsuario() == null) {
	  seleccionado.setUsuario(new Usuario());
	}
	if (seleccionado.getUbicacion() == null) {
	  seleccionado.setUbicacion(new Ubicacion());
	}
	
	
	
	

	this.initEdit();

  }

  public List<Cliente> getListaClientes() {
	if (listaClientes == null || listaClientes.isEmpty()) {
	  listaClientes = servicio.consultarClientes();

	  Collections.sort(listaClientes, new Comparator<Cliente>() {
		@Override
		public int compare(Cliente cliente, Cliente cliente2) {

		  return cliente.getNombre().compareTo(cliente2.getNombre());
		}
	  });
	}

	return listaClientes;
  }

  public void setListaClientes(List<Cliente> listaClientes) {
	this.listaClientes = listaClientes;
  }

  public List<Ciudad> getListaCiudades() {
	if (listaCiudades == null || listaCiudades.isEmpty()) {
	  listaCiudades = servicio.consultarCiudades();

	  Collections.sort(listaCiudades, new Comparator<Ciudad>() {
		@Override
		public int compare(Ciudad ciudad, Ciudad ciudad2) {

		  return ciudad.getNombre().compareTo(ciudad2.getNombre());
		}
	  });

	}
	return listaCiudades;
  }

  public void setListaCiudades(List<Ciudad> listaCiudades) {
	this.listaCiudades = listaCiudades;
  }

  public boolean isEditar() {
	return isEditar;
  }

  public void setEditar(boolean isEditar) {
	this.isEditar = isEditar;
  }

  public List<PuntoVenta> getPuntoVenta() {
	return puntoVenta;
  }

  public void setPuntoVenta(List<PuntoVenta> puntoVenta) {
	this.puntoVenta = puntoVenta;
  }

  public void cargarArchivo(FileUploadEvent fileUploadEvent) {
	try {

	  uploadedFile = fileUploadEvent.getFile();

	  List<String[]> lines = obtenerDatosDesdeArchivo(uploadedFile.getContents());

	  if (lines.isEmpty()) {
		this.addMensajeError("Archivo vacío");
	  } else {

		maestroFacade.cargarPuntoEntregaDesdeArchivo(lines);

		this.addMensajeInfo("Archivo cargado con éxito");

		this.addMensajeInfo("Vista preliminar cargada con éxito");
	  }

	} catch (IOException e) {
	  this.addMensajeError("Error al leer el archivo");
	} catch (Exception e) {
	  LOGGER.error(e);
	  Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
	  if (unrollException != null) {
		this.addMensajeError(unrollException.getLocalizedMessage());
	  } else {
		unrollException = (Exception) this.unrollException(e, RuntimeException.class);
		if (unrollException != null) {
		  this.addMensajeError(unrollException.getLocalizedMessage());
		} else {
		  this.addMensajeError(e);
		}
	  }
	}
  }

  private List<String[]> obtenerDatosDesdeArchivo(byte[] archivo) throws IOException {
	List<String[]> lines = new ArrayList<String[]>();
	BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(archivo)));
	boolean error = false;
	String message = null;
	int numLinea = 0;
	String line = null;

	System.out.println("line0: " + line);

	while ((line = reader.readLine()) != null) {
	  numLinea++;
	  System.out.println("line1: " + line);

	  if (!line.isEmpty()) {
		String[] values = line.split("\\|");

		System.out.println("linea: " + values);

		if (values.length < 6 || values.length > 7) {
		  message = "Error de estructura en la línea " + numLinea;
		  error = true;
		  break;
		}

		System.out.println("linea0: " + values[0].trim());

		if (values[0].trim().isEmpty()) {
		  message = "Error de datos en la línea " + numLinea;
		  error = true;
		  break;
		}

		// if (values[0].trim().isEmpty()) {
		// mensaje = "Error de datos en el archivo";
		// error = true;
		// break;
		// }
		try {
		  // new BigDecimal(values[1].trim());
		  // new BigDecimal(values[2].trim());
		  Long.parseLong(values[2].trim());
		  Long.parseLong(values[4].trim());
		  Long.parseLong(values[5].trim());
		} catch (NumberFormatException e) {
		  message = "Error de datos en la línea " + numLinea;
		  error = true;
		  break;
		}

		lines.add(values);
	  }
	}
	reader.close();

	if (error) {
	  throw new RuntimeException(message);
	}

	return lines;
  }

  public PuntoVenta getFiltro() {
	return filtro;
  }

  public void setFiltro(PuntoVenta filtro) {
	this.filtro = filtro;
  }

  public UploadedFile getUploadedFile() {
	return uploadedFile;
  }

  public void setUploadedFile(UploadedFile uploadedFile) {
	this.uploadedFile = uploadedFile;
  }

}
