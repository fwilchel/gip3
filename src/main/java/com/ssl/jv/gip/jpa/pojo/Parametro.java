package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the paises database table.
 * 
 */
@Entity
@Table(name="parametro")
public class Parametro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String nombre;
	private String valor;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}


}