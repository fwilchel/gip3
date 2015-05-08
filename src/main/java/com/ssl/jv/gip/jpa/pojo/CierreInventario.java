package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the cierre_inventario database table.
 *
 */
@Entity
@Table(name = "cierre_inventario")
@NamedQuery(name = "CierreInventario.findAll", query = "SELECT c FROM CierreInventario c")
public class CierreInventario implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha_cierre")
  private Date fechaCierre;

  public CierreInventario() {
  }

  public Date getFechaCierre() {
    return this.fechaCierre;
  }

  public void setFechaCierre(Date fechaCierre) {
    this.fechaCierre = fechaCierre;
  }

}
