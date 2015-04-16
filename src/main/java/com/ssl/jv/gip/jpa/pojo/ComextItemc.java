package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the comext_itemc database table.
 *
 */
@Entity
@Table(name = "comext_itemc")
@NamedQuery(name = "ComextItemc.findAll", query = "SELECT c FROM ComextItemc c")
public class ComextItemc implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private Integer quantity;

  private BigDecimal total;

  @Column(name = "unit_price")
  private BigDecimal unitPrice;

  //bi-directional many-to-one association to ComextC
  @ManyToOne
  @JoinColumn(name = "cart_id")
  private ComextC comextC;

  //bi-directional many-to-one association to ProductosInventario
  @ManyToOne
  @JoinColumn(name = "id_producto")
  private ProductosInventario productosInventario;

  //bi-directional many-to-one association to ProductosInventarioComext
  @ManyToOne
  @JoinColumn(name = "id_prodcomext")
  private ProductosInventarioComext productosInventarioComext;

  public ComextItemc() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getQuantity() {
    return this.quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getTotal() {
    return this.total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public BigDecimal getUnitPrice() {
    return this.unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public ComextC getComextC() {
    return this.comextC;
  }

  public void setComextC(ComextC comextC) {
    this.comextC = comextC;
  }

  public ProductosInventario getProductosInventario() {
    return this.productosInventario;
  }

  public void setProductosInventario(ProductosInventario productosInventario) {
    this.productosInventario = productosInventario;
  }

  public ProductosInventarioComext getProductosInventarioComext() {
    return this.productosInventarioComext;
  }

  public void setProductosInventarioComext(ProductosInventarioComext productosInventarioComext) {
    this.productosInventarioComext = productosInventarioComext;
  }

}
