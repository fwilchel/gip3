package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the lugar_incoterm database table.
 * 
 */
@Entity
@Table(name="lugar_incoterm")
@NamedQuery(name="LugarIncoterm.findAll", query="SELECT t FROM LugarIncoterm t")
public class LugarIncoterm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="activo")
	private Boolean activo;

	public LugarIncoterm() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}


}