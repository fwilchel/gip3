package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Class ProductoPorClienteDTO.
 */
public class ProductoPorClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sku;
	private String nombre;
	private BigDecimal cantidadUno;	
	private BigDecimal precioMl;
	private BigDecimal iva;
	private BigDecimal descuentoxproducto;
	private BigDecimal otrosDescuentos;
	private BigDecimal descuentoCliente;
	
	private Boolean seleccionado;

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

	public BigDecimal getCantidadUno() {
		return cantidadUno;
	}

	public void setCantidadUno(BigDecimal cantidadUno) {
		this.cantidadUno = cantidadUno;
	}

	public BigDecimal getPrecioMl() {
		return precioMl;
	}

	public void setPrecioMl(BigDecimal precioMl) {
		this.precioMl = precioMl;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getDescuentoxproducto() {
		return descuentoxproducto;
	}

	public void setDescuentoxproducto(BigDecimal descuentoxproducto) {
		this.descuentoxproducto = descuentoxproducto;
	}

	public BigDecimal getOtrosDescuentos() {
		return otrosDescuentos;
	}

	public void setOtrosDescuentos(BigDecimal otrosDescuentos) {
		this.otrosDescuentos = otrosDescuentos;
	}

	public BigDecimal getDescuentoCliente() {
		return descuentoCliente;
	}

	public void setDescuentoCliente(BigDecimal descuentoCliente) {
		this.descuentoCliente = descuentoCliente;
	}

	public Boolean getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	
	
	




}
