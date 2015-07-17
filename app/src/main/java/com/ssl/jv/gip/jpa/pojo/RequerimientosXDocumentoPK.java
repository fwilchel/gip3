package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the requerimientos_x_documento database table.
 * 
 */
@Embeddable
public class RequerimientosXDocumentoPK implements Serializable {
  // default serial version id, required for serializable classes.
  private static final long serialVersionUID = 1L;

  @Column(name = "documento_id")
  private Long documentoId;

  @Column(name = "requerimientoexportacion_id")
  private Long requerimientoexportacionId;

  public RequerimientosXDocumentoPK() {
  }

  public Long getDocumentoId() {
	return this.documentoId;
  }

  public void setDocumentoId(Long documentoId) {
	this.documentoId = documentoId;
  }

 

  public boolean equals(Object other) {
	if (this == other) {
	  return true;
	}
	if (!(other instanceof RequerimientosXDocumentoPK)) {
	  return false;
	}
	RequerimientosXDocumentoPK castOther = (RequerimientosXDocumentoPK) other;
	return this.documentoId.equals(castOther.documentoId) && this.requerimientoexportacionId.equals(castOther.requerimientoexportacionId);
  }

  public int hashCode() {
	final int prime = 31;
	int hash = 17;
	hash = hash * prime + this.documentoId.hashCode();
	hash = hash * prime + this.requerimientoexportacionId.hashCode();

	return hash;
  }

/**
 * @return the requerimientoexportacionId
 */
public Long getRequerimientoexportacionId() {
	return requerimientoexportacionId;
}

/**
 * @param requerimientoexportacionId the requerimientoexportacionId to set
 */
public void setRequerimientoexportacionId(Long requerimientoexportacionId) {
	this.requerimientoexportacionId = requerimientoexportacionId;
}
}