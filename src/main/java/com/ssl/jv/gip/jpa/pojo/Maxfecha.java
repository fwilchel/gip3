package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the maxfechas database table.
 *
 */
@Entity
@Table(name = "maxfechas")
@NamedQuery(name = "Maxfecha.findAll", query = "SELECT m FROM Maxfecha m")
public class Maxfecha implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private MaxfechaPK pk;

  public Maxfecha() {
  }

  public MaxfechaPK getPk() {
    return pk;
  }

  public void setPk(MaxfechaPK pk) {
    this.pk = pk;
  }

}
