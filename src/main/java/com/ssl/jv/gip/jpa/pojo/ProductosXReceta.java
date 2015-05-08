package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the productosxrecetas database table.
 *
 */
@Entity
@Table(name = "productosxrecetas")
@NamedQuery(name = "ProductosXReceta.findAll", query = "SELECT p FROM ProductosXReceta p")
public class ProductosXReceta implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private ProductosXRecetaPK id;

  private BigDecimal cantidad;

  private Long subcomponente;

  //bi-directional many-to-one association to Insumo
  @ManyToOne
  @JoinColumn(name = "id_insumo")
  private Insumo insumo;

  //bi-directional many-to-one association to ProductosInventario
  @ManyToOne(optional = false)
  @JoinColumn(name = "id_material", referencedColumnName = "id", insertable = false, updatable = false)
  private ProductosInventario productosInventario;

  //bi-directional many-to-one association to Receta
  @ManyToOne(optional = false)
  @JoinColumn(name = "micros", referencedColumnName = "id", insertable = false, updatable = false)
  private Receta receta;

  //bi-directional many-to-one association to Unidad
  @ManyToOne(optional = false)
  @JoinColumn(name = "id_unidad", referencedColumnName = "id", insertable = false, updatable = false)
  private Unidad unidade;

  public ProductosXReceta() {
  }

  public ProductosXRecetaPK getId() {
    return this.id;
  }

  public void setId(ProductosXRecetaPK id) {
    this.id = id;
  }

  public BigDecimal getCantidad() {
    return this.cantidad;
  }

  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

  public Long getSubcomponente() {
    return this.subcomponente;
  }

  public void setSubcomponente(Long subcomponente) {
    this.subcomponente = subcomponente;
  }

  public Insumo getInsumo() {
    return this.insumo;
  }

  public void setInsumo(Insumo insumo) {
    this.insumo = insumo;
  }

  public ProductosInventario getProductosInventario() {
    return this.productosInventario;
  }

  public void setProductosInventario(ProductosInventario productosInventario) {
    this.productosInventario = productosInventario;
  }

  public Receta getReceta() {
    return this.receta;
  }

  public void setReceta(Receta receta) {
    this.receta = receta;
  }

  public Unidad getUnidade() {
    return this.unidade;
  }

  public void setUnidade(Unidad unidade) {
    this.unidade = unidade;
  }

}
