package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the saldos database table.
 * 
 */
@Embeddable
public class SaldoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_producto")
	private Long idProducto;

	@Column(name="id_ubicacion")
	private Long idUbicacion;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fecha;

	@Column(name="id_bodega_logica")
	private Long idBodegaLogica;

	public SaldoPK() {
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
	public java.util.Date getFecha() {
		return this.fecha;
	}
	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}
	public Long getIdBodegaLogica() {
		return this.idBodegaLogica;
	}
	public void setIdBodegaLogica(Long idBodegaLogica) {
		this.idBodegaLogica = idBodegaLogica;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SaldoPK)) {
			return false;
		}
		SaldoPK castOther = (SaldoPK)other;
		return 
			this.idProducto.equals(castOther.idProducto)
			&& this.idUbicacion.equals(castOther.idUbicacion)
			&& this.fecha.equals(castOther.fecha)
			&& this.idBodegaLogica.equals(castOther.idBodegaLogica);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idProducto.hashCode();
		hash = hash * prime + this.idUbicacion.hashCode();
		hash = hash * prime + this.fecha.hashCode();
		hash = hash * prime + this.idBodegaLogica.hashCode();
		
		return hash;
	}
}