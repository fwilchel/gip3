package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Class ProductoGenerarFacturaPFDTO.
 */
@Entity
public class ProductoDespacharMercanciaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String idDocumento;
	private String sku;
	private String nombre;
	private String unidadVenta;
	private BigDecimal cantidadDespachada;
	private BigDecimal cantidadAdespachar;
	private BigDecimal precioUnitario;
	private String observaciones;
	private boolean seleccionado;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUnidadVenta() {
		return unidadVenta;
	}
	public void setUnidadVenta(String unidadVenta) {
		this.unidadVenta = unidadVenta;
	}
	public BigDecimal getCantidadDespachada() {
		return cantidadDespachada;
	}
	public void setCantidadDespachada(BigDecimal cantidadDespachada) {
		this.cantidadDespachada = cantidadDespachada;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public BigDecimal getCantidadAdespachar() {
		return cantidadAdespachar;
	}
	public void setCantidadAdespachar(BigDecimal cantidadAdespachar) {
		this.cantidadAdespachar = cantidadAdespachar;
	}
	public boolean isSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	
}
