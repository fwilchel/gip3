package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the comext_roles database table.
 * 
 */
@Entity
@Table(name="comext_roles")
@NamedQuery(name="ComextRole.findAll", query="SELECT c FROM ComextRole c")
public class ComextRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_rol")
	private Long idRol;

	private String nombre;

	public ComextRole() {
	}

	public Long getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}