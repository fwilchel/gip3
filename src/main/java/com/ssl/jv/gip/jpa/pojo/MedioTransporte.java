package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the medio_transporte database table.
 *
 */
@Entity
@Table(name = "medio_transporte")
@NamedQuery(name = "MedioTransporte.findAll", query = "SELECT m FROM MedioTransporte m")
public class MedioTransporte implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private Boolean activo;

  private String descripcion;

  public MedioTransporte() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getActivo() {
    return this.activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
