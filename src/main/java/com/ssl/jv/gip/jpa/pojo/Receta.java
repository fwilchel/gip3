package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the recetas database table.
 *
 */
@Entity
@Table(name = "recetas")
@NamedQuery(name = "Receta.findAll", query = "SELECT r FROM Receta r")
public class Receta implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private String nombre;

  private String preparacion;

  //bi-directional many-to-one association to ProductosXReceta
  @OneToMany(mappedBy = "receta")
  private List<ProductosXReceta> productosxrecetas;

  //bi-directional many-to-one association to CategoriasReceta
  @ManyToOne
  @JoinColumn(name = "id_categoria_receta")
  private CategoriasReceta categoriasReceta;

  public Receta() {
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

  public String getPreparacion() {
    return this.preparacion;
  }

  public void setPreparacion(String preparacion) {
    this.preparacion = preparacion;
  }

  public List<ProductosXReceta> getProductosxrecetas() {
    return this.productosxrecetas;
  }

  public void setProductosxrecetas(List<ProductosXReceta> productosxrecetas) {
    this.productosxrecetas = productosxrecetas;
  }

  public ProductosXReceta addProductosxreceta(ProductosXReceta productosxreceta) {
    getProductosxrecetas().add(productosxreceta);
    productosxreceta.setReceta(this);

    return productosxreceta;
  }

  public ProductosXReceta removeProductosxreceta(ProductosXReceta productosxreceta) {
    getProductosxrecetas().remove(productosxreceta);
    productosxreceta.setReceta(null);

    return productosxreceta;
  }

  public CategoriasReceta getCategoriasReceta() {
    return this.categoriasReceta;
  }

  public void setCategoriasReceta(CategoriasReceta categoriasReceta) {
    this.categoriasReceta = categoriasReceta;
  }

}
