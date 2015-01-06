package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Class ProductoDTO.
 */
public class ProductoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String sku;
	private String nombre;
	private BigDecimal cantidad;
	private BigDecimal pesoNeto;
	private BigDecimal pesoBruto;
	private BigDecimal cantidadCajas;
	private BigDecimal cantidadPorEmbalaje;
	private BigDecimal cantidadPallets;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPesoNeto() {
		return pesoNeto;
	}

	public void setPesoNeto(BigDecimal pesoNeto) {
		this.pesoNeto = pesoNeto;
	}

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public BigDecimal getCantidadCajas() {
		return cantidadCajas;
	}

	public void setCantidadCajas(BigDecimal cantidadCajas) {
		this.cantidadCajas = cantidadCajas;
	}

	public BigDecimal getCantidadPorEmbalaje() {
		return cantidadPorEmbalaje;
	}

	public void setCantidadPorEmbalaje(BigDecimal cantidadPorEmbalaje) {
		this.cantidadPorEmbalaje = cantidadPorEmbalaje;
	}

	public BigDecimal getCantidadPallets() {
		return cantidadPallets;
	}

	public void setCantidadPallets(BigDecimal cantidadPallets) {
		this.cantidadPallets = cantidadPallets;
	}

}
