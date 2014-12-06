package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the maxfechas database table.
 * 
 */
@Embeddable
public class MaxfechaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private Timestamp fecha;

	@Column(name="id_producto")
	private Long idProducto;

	@Column(name="id_ubicacion")
	private Long idUbicacion;

	public MaxfechaPK() {
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Long getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public Long getIdUbicacion() {
		return this.idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result
				+ ((idProducto == null) ? 0 : idProducto.hashCode());
		result = prime * result
				+ ((idUbicacion == null) ? 0 : idUbicacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaxfechaPK other = (MaxfechaPK) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (idProducto == null) {
			if (other.idProducto != null)
				return false;
		} else if (!idProducto.equals(other.idProducto))
			return false;
		if (idUbicacion == null) {
			if (other.idUbicacion != null)
				return false;
		} else if (!idUbicacion.equals(other.idUbicacion))
			return false;
		return true;
	}

}