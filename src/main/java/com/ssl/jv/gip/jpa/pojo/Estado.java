package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the estados database table.
 * 
 */
@Entity
@Table(name = "estados")
@NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e")
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descripcion;

	private String nombre;

	// bi-directional many-to-many association to TipoDocumento
	@ManyToMany(mappedBy = "estados")
	private List<TipoDocumento> tipoDocumentos;

	public Estado() {
	}

	public Estado(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<TipoDocumento> getTipoDocumentos() {
		return this.tipoDocumentos;
	}

	public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}

}