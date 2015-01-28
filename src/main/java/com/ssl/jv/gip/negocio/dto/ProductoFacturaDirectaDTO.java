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
public class ProductoFacturaDirectaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private BigInteger id;
	private String sku;
	private String nombre;
	private BigDecimal cantidad;
	private BigDecimal precio;
	private BigDecimal valorTotal;
	private BigInteger idCliente;
	private BigInteger idUnidad;
	private String unidad;
	private String descripcion;
	
	
	
	private BigDecimal  valorDescuento;
	private BigDecimal  valorIva;
	private BigDecimal  valorIva10;
	private BigDecimal  valorIva16;
	private BigDecimal  valorIva5;
	private BigDecimal  valorOtrosDescuento;
	private String marca;
	
	
	
	public BigDecimal getValorOtrosDescuento() {
		return valorOtrosDescuento;
	}


	public void setValorOtrosDescuento(BigDecimal valorOtrosDescuento) {
		this.valorOtrosDescuento = valorOtrosDescuento;
	}


	public ProductoFacturaDirectaDTO(){
		
	}
	
	private BigDecimal  valorSubTotal;
	public BigDecimal getValorSubTotal() {
		return valorSubTotal;
	}


	public void setValorSubTotal(BigDecimal valorSubTotal) {
		this.valorSubTotal = valorSubTotal;
	}


	public BigDecimal getValorDescuento() {
		return valorDescuento;
	}


	public void setValorDescuento(BigDecimal valorDescuento) {
		this.valorDescuento = valorDescuento;
	}


	public BigDecimal getValorIva() {
		return valorIva;
	}


	public void setValorIva(BigDecimal valorIva) {
		this.valorIva = valorIva;
	}


	public BigDecimal getValorIva10() {
		return valorIva10;
	}


	public void setValorIva10(BigDecimal valorIva10) {
		this.valorIva10 = valorIva10;
	}


	public BigDecimal getValorIva16() {
		return valorIva16;
	}


	public void setValorIva16(BigDecimal valorIva16) {
		this.valorIva16 = valorIva16;
	}


	public BigDecimal getValorIva5() {
		return valorIva5;
	}


	public void setValorIva5(BigDecimal valorIva5) {
		this.valorIva5 = valorIva5;
	}


	
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
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


	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}


	public BigInteger getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(BigInteger idCliente) {
		this.idCliente = idCliente;
	}


	public BigInteger getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(BigInteger idUnidad) {
		this.idUnidad = idUnidad;
	}


	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}



	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}



}
