package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CostoLogisticoDTOPK implements Serializable{

	private Integer tipo;
	private String categoria;
	private String item;

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
	
}
