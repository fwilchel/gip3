package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the categorias_inventario database table.
 *
 */
@Entity
@Table(name = "categorias_inventario")
@NamedQuery(name = "CategoriasInventario.findAll", query = "SELECT c FROM CategoriasInventario c")
public class CategoriasInventario implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  @Column(name = "incluir_en_cierre")
  private Boolean incluirEnCierre;

  private String nombre;

  //bi-directional many-to-one association to CategoriasInventario
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_categoria_padre")
  private CategoriasInventario categoriasInventario;

  //bi-directional many-to-one association to CategoriasInventario
  @OneToMany(mappedBy = "categoriasInventario", fetch = FetchType.LAZY)
  private List<CategoriasInventario> categoriasInventarios;

  //bi-directional many-to-one association to EstandarPedido
  //@OneToMany(mappedBy="categoriasInventario")
  //private List<EstandarPedido> estandarPedidos;
  //bi-directional many-to-one association to ProductosInventario
  @OneToMany(mappedBy = "categoriasInventario", fetch = FetchType.LAZY)
  private List<ProductosInventario> productosInventarios;

  public CategoriasInventario() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getIncluirEnCierre() {
    return this.incluirEnCierre;
  }

  public void setIncluirEnCierre(Boolean incluirEnCierre) {
    this.incluirEnCierre = incluirEnCierre;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public CategoriasInventario getCategoriasInventario() {
    return this.categoriasInventario;
  }

  public void setCategoriasInventario(CategoriasInventario categoriasInventario) {
    this.categoriasInventario = categoriasInventario;
  }

  public List<CategoriasInventario> getCategoriasInventarios() {
    return this.categoriasInventarios;
  }

  public void setCategoriasInventarios(List<CategoriasInventario> categoriasInventarios) {
    this.categoriasInventarios = categoriasInventarios;
  }

  public CategoriasInventario addCategoriasInventario(CategoriasInventario categoriasInventario) {
    getCategoriasInventarios().add(categoriasInventario);
    categoriasInventario.setCategoriasInventario(this);

    return categoriasInventario;
  }

  public CategoriasInventario removeCategoriasInventario(CategoriasInventario categoriasInventario) {
    getCategoriasInventarios().remove(categoriasInventario);
    categoriasInventario.setCategoriasInventario(null);

    return categoriasInventario;
  }

  public List<ProductosInventario> getProductosInventarios() {
    return this.productosInventarios;
  }

  public void setProductosInventarios(List<ProductosInventario> productosInventarios) {
    this.productosInventarios = productosInventarios;
  }

  public ProductosInventario addProductosInventario(ProductosInventario productosInventario) {
    getProductosInventarios().add(productosInventario);
    productosInventario.setCategoriasInventario(this);

    return productosInventario;
  }

  public ProductosInventario removeProductosInventario(ProductosInventario productosInventario) {
    getProductosInventarios().remove(productosInventario);
    productosInventario.setCategoriasInventario(null);

    return productosInventario;
  }

}
