package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ventas database table.
 * 
 */
@Embeddable
public class VentaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_producto")
	private String idProducto;

	@Column(name="id_ubicacion")
	private Long idUbicacion;

	public VentaPK() {
	}
	public String getIdProducto() {
		return this.idProducto;
	}
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}
	public Long getIdUbicacion() {
		return this.idUbicacion;
	}
	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VentaPK)) {
			return false;
		}
		VentaPK castOther = (VentaPK)other;
		return 
			this.idProducto.equals(castOther.idProducto)
			&& this.idUbicacion.equals(castOther.idUbicacion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idProducto.hashCode();
		hash = hash * prime + this.idUbicacion.hashCode();
		
		return hash;
	}
}