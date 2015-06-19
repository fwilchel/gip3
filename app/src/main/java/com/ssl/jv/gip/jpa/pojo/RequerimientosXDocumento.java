package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the requerimientos_x_documento database table.
 * 
 */
@Entity
@Table(name = "requerimientos_x_documento")
@NamedQuery(name = "RequerimientosXDocumento.findAll", query = "SELECT r FROM RequerimientosXDocumento r")
public class RequerimientosXDocumento implements Serializable {
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private RequerimientosXDocumentoPK id;




  // bi-directional many-to-one association to ComextRequerimientoexportacion
  @ManyToOne
  @JoinColumn(name = "requerimientoexportacion_id", insertable = false, updatable = false)
  private ComextRequerimientoexportacion comextRequerimientoexportacion;

  public RequerimientosXDocumento() {
  }


  public RequerimientosXDocumentoPK getId() {
	return this.id;
  }

  public void setId(RequerimientosXDocumentoPK id) {
	this.id = id;
  }

  public ComextRequerimientoexportacion getComextRequerimientoexportacion() {
	return this.comextRequerimientoexportacion;
  }

  public void setComextRequerimientoexportacion(ComextRequerimientoexportacion comextRequerimientoexportacion) {
	this.comextRequerimientoexportacion = comextRequerimientoexportacion;
  }


}