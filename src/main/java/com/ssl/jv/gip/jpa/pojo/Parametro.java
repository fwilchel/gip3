package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the paises database table.
 * 
 */
@Entity
@Table(name = "parametro")
@NamedQueries({ @NamedQuery(name = Parametro.FIND_BY_NOMBRE, query = "SELECT p FROM Parametro p WHERE p.nombre IN (:nombres)") })
public class Parametro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8241650816255553210L;

	public static final String FIND_BY_NOMBRE = "findByNombre";

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