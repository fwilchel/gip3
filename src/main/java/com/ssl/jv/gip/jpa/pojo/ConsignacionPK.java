package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the consignaciones database table.
 * 
 */
@Embeddable
public class ConsignacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fecha;

	@Column(name="id_ubicacion")
	private Long idUbicacion;

	@Column(name="id_ml")
	private String idMl;

	public ConsignacionPK() {
	}
	public java.util.Date getFecha() {
		return this.fecha;
	}
	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}
	public Long getIdUbicacion() {
		return this.idUbicacion;
	}
	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
	public String getIdMl() {
		return this.idMl;
	}
	public void setIdMl(String idMl) {
		this.idMl = idMl;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConsignacionPK)) {
			return false;
		}
		ConsignacionPK castOther = (ConsignacionPK)other;
		return 
			this.fecha.equals(castOther.fecha)
			&& this.idUbicacion.equals(castOther.idUbicacion)
			&& this.idMl.equals(castOther.idMl);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fecha.hashCode();
		hash = hash * prime + this.idUbicacion.hashCode();
		hash = hash * prime + this.idMl.hashCode();
		
		return hash;
	}
}