package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the productosxdocumentos database table.
 * 
 */
@Embeddable
public class ProductosXDocumentoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_producto")
	private Long idProducto;

	@Column(name="id_documento")
	private Long idDocumento;

	public ProductosXDocumentoPK() {
	}
	public Long getIdProducto() {
		return this.idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public Long getIdDocumento() {
		return this.idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductosXDocumentoPK)) {
			return false;
		}
		ProductosXDocumentoPK castOther = (ProductosXDocumentoPK)other;
		return 
			this.idProducto.equals(castOther.idProducto)
			&& this.idDocumento.equals(castOther.idDocumento);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idProducto.hashCode();
		hash = hash * prime + this.idDocumento.hashCode();
		
		return hash;
	}
}