package com.ssl.jv.gip.negocio.dto;

import java.math.BigDecimal;

public class CostoLogisticoDTO {

	private Integer tipo;
	private String categoria;
	private String item;
	private Double cantidad;
	private BigDecimal valor;
	private BigDecimal valorMinimo;
	private Boolean baseFob;
	
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	public Boolean getBaseFob() {
		return baseFob;
	}
	public void setBaseFob(Boolean baseFob) {
		this.baseFob = baseFob;
	}
	
	
	
}
