package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the categorias_receta database table.
 *
 */
@Entity
@Table(name = "categorias_receta")
@NamedQuery(name = "CategoriasReceta.findAll", query = "SELECT c FROM CategoriasReceta c")
public class CategoriasReceta implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private String nombre;

  //bi-directional many-to-one association to Receta
  @OneToMany(mappedBy = "categoriasReceta")
  private List<Receta> recetas;

  public CategoriasReceta() {
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

  public List<Receta> getRecetas() {
    return this.recetas;
  }

  public void setRecetas(List<Receta> recetas) {
    this.recetas = recetas;
  }

  public Receta addReceta(Receta receta) {
    getRecetas().add(receta);
    receta.setCategoriasReceta(this);

    return receta;
  }

  public Receta removeReceta(Receta receta) {
    getRecetas().remove(receta);
    receta.setCategoriasReceta(null);

    return receta;
  }

}
