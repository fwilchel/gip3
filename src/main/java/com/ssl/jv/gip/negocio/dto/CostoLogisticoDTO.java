package com.ssl.jv.gip.negocio.dto;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class CostoLogisticoDTO {

	@EmbeddedId
	private CostoLogisticoDTOPK id;
	private String descripcion;
	private BigDecimal cantidad;
	private Double valor;
	private Double valorMinimo;
	private Boolean baseFob;
	
	public CostoLogisticoDTOPK getId() {
		return id;
	}
	public void setId(CostoLogisticoDTOPK id) {
		this.id = id;
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
