package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the comida_empleado database table.
 *
 */
@Entity
@Table(name = "comida_empleado")
@NamedQuery(name = "ComidaEmpleado.findAll", query = "SELECT c FROM ComidaEmpleado c")
public class ComidaEmpleado implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private String descripcion;

  private String nombre;

  public ComidaEmpleado() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

}
