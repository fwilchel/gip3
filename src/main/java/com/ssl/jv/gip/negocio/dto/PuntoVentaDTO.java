package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

public class PuntoVentaDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;
	private String direccion;
	private String telefono;
	private Long codigoBarras;
	private Boolean activo;
	private String nombreCiudad;
	private String codigoDespachoSap;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Long getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(Long codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public String getNombreCiudad() {
		return nombreCiudad;
	}
	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}
	public String getCodigoDespachoSap() {
		return codigoDespachoSap;
	}
	public void setCodigoDespachoSap(String codigoDespachoSap) {
		this.codigoDespachoSap = codigoDespachoSap;
	}

	
	//private Long idCiudad;
	//private Integer idCliente;
	//private Ciudad Ciudad;
	//private String NombreCliente;	
	//private Cliente Cliente;
	//private String nitCliente;
		

}
