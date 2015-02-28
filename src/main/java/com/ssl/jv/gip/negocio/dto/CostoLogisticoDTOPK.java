package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class CostoLogisticoDTOPK implements Serializable{

	private Integer tipo;
	private String categoria;
	private String item;
	private String descripcion;
	private BigDecimal cantidad;
	private Double valor;
	private Double valorMinimo;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(Double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	public Boolean getBaseFob() {
		return baseFob;
	}
	public void setBaseFob(Boolean baseFob) {
		this.baseFob = baseFob;
	}	
}
