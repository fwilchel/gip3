package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the nivel_inventarioxubicacion_temp database table.
 * 
 */
@Embeddable
public class NivelInventarioxubicacionTempPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_producto")
	private Long idProducto;

	@Column(name="id_ubicacion")
	private Long idUbicacion;

	public NivelInventarioxubicacionTempPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NivelInventarioxubicacionTempPK)) {
			return false;
		}
		NivelInventarioxubicacionTempPK castOther = (NivelInventarioxubicacionTempPK)other;
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