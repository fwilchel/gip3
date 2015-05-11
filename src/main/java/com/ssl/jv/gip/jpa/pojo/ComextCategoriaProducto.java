package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the comext_categoria_producto database table.
 *
 */
@Entity
@Table(name = "comext_categoria_producto")
@NamedQuery(name = "ComextCategoriaProducto.findAll", query = "SELECT c FROM ComextCategoriaProducto c")
public class ComextCategoriaProducto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private String nombre;

  public ComextCategoriaProducto() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

}
