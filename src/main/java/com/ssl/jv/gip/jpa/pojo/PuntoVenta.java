package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the punto_venta database table.
 * 
 */
@Entity
@Table(name="punto_venta")
@NamedQuery(name="PuntoVenta.findAll", query="SELECT p FROM PuntoVenta p")
public class PuntoVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Boolean activo;

	@Column(name="cod_despacho_sap")
	private String codDespachoSap;

	@Column(name="codigo_barras")
	private Long codigoBarras;

	private String direccion;

	private String nombre;

	private String telefono;

	//bi-directional many-to-one association to Ciudad
	@ManyToOne
	@JoinColumn(name="id_ciudad")
	private Ciudad ciudade;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	public PuntoVenta() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getCodDespachoSap() {
		return this.codDespachoSap;
	}

	public void setCodDespachoSap(String codDespachoSap) {
		this.codDespachoSap = codDespachoSap;
	}

	public Long getCodigoBarras() {
		return this.codigoBarras;
	}

	public void setCodigoBarras(Long codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Ciudad getCiudade() {
		return this.ciudade;
	}

	public void setCiudade(Ciudad ciudade) {
		this.ciudade = ciudade;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}