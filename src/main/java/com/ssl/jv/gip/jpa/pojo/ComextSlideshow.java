package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the comext_slideshow database table.
 * 
 */
@Entity
@Table(name="comext_slideshow")
@NamedQuery(name="ComextSlideshow.findAll", query="SELECT c FROM ComextSlideshow c")
public class ComextSlideshow implements Serializable {
	private static final long serialVersionUID = 1L;

	private String descripcion;

	@Id
	private Long id;

	private String ruta;

	public ComextSlideshow() {
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

}