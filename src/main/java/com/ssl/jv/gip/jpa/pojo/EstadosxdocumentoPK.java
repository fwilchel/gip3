package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the estadosxdocumento database table.
 * 
 */
@Embeddable
public class EstadosxdocumentoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_tipo_documento", insertable=false, updatable=false)
	private Long idTipoDocumento;

	@Column(name="id_estado", insertable=false, updatable=false)
	private Long idEstado;

	public EstadosxdocumentoPK() {
	}
	public Long getIdTipoDocumento() {
		return this.idTipoDocumento;
	}
	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public Long getIdEstado() {
		return this.idEstado;
	}
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EstadosxdocumentoPK)) {
			return false;
		}
		EstadosxdocumentoPK castOther = (EstadosxdocumentoPK)other;
		return 
			this.idTipoDocumento.equals(castOther.idTipoDocumento)
			&& this.idEstado.equals(castOther.idEstado);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idTipoDocumento.hashCode();
		hash = hash * prime + this.idEstado.hashCode();
		
		return hash;
	}
}