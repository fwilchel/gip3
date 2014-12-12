package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the documento_x_lotesoic database table.
 * 
 */
@Embeddable
public class DocumentoXLotesoicPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_documento")
	private Long idDocumento;

	@Column(name="id_tipo_lote")
	private Long idTipoLote;

	public DocumentoXLotesoicPK() {
	}
	public Long getIdDocumento() {
		return this.idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Long getIdTipoLote() {
		return this.idTipoLote;
	}
	public void setIdTipoLote(Long idTipoLote) {
		this.idTipoLote = idTipoLote;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DocumentoXLotesoicPK)) {
			return false;
		}
		DocumentoXLotesoicPK castOther = (DocumentoXLotesoicPK)other;
		return 
			this.idDocumento.equals(castOther.idDocumento)
			&& this.idTipoLote.equals(castOther.idTipoLote);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idDocumento.hashCode();
		hash = hash * prime + this.idTipoLote.hashCode();
		
		return hash;
	}
}