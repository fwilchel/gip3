package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the aprobaciones database table.
 * 
 */
@Entity
@Table(name="aprobaciones")
@NamedQuery(name="Aprobacion.findAll", query="SELECT a FROM Aprobacion a")
public class Aprobacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String cargo;

	private String nombre;

	private Boolean vigente;

	public Aprobacion() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getVigente() {
		return this.vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

}