package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the comext_directorio database table.
 *
 */
@Entity
@Table(name = "comext_directorio")
@NamedQuery(name = "ComextDirectorio.findAll", query = "SELECT c FROM ComextDirectorio c")
public class ComextDirectorio implements Serializable {

  private static final long serialVersionUID = 1L;

  private String email;

  @Id
  private Integer id;

  private String nombre;

  private Boolean reportegensp;

  private Boolean reportenovhabilitado;

  public ComextDirectorio() {
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Boolean getReportegensp() {
    return this.reportegensp;
  }

  public void setReportegensp(Boolean reportegensp) {
    this.reportegensp = reportegensp;
  }

  public Boolean getReportenovhabilitado() {
    return this.reportenovhabilitado;
  }

  public void setReportenovhabilitado(Boolean reportenovhabilitado) {
    this.reportenovhabilitado = reportenovhabilitado;
  }

}
