package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the costos database table.
 *
 */
@Entity
@Table(name = "costos")
@NamedQueries({
  @NamedQuery(name = Costo.FIND_ALL, query = "SELECT c FROM Costo c"),
  @NamedQuery(name = Costo.FIND_BY_PRODUCTO_AND_FECHA, query = "SELECT c FROM Costo c WHERE c.productosInventario.id = :idProducto AND c.fecha <= :fecha ORDER BY c.fecha DESC")
})
public class Costo implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "Costo.findAll";
  public static final String FIND_BY_PRODUCTO_AND_FECHA = "Costo.findByProductoYFecha";

  @Id
  private Long id;

  @Column(name = "costo_ml")
  private double costoMl;

  @Column(name = "costo_usd")
  private double costoUsd;

  @Temporal(TemporalType.DATE)
  private Date fecha;

  private double saldo;

  //bi-directional many-to-one association to Empresa
  @ManyToOne
  @JoinColumn(name = "id_empresa")
  private Empresa empresa;

  //bi-directional many-to-one association to Moneda
  @ManyToOne
  @JoinColumn(name = "id_ml")
  private Moneda moneda;

  //bi-directional many-to-one association to ProductosInventario
  @ManyToOne
  @JoinColumn(name = "id_producto")
  private ProductosInventario productosInventario;

  //bi-directional many-to-one association to Unidad
  @ManyToOne
  @JoinColumn(name = "id_unidad")
  private Unidad unidade;

  public Costo() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getCostoMl() {
    return this.costoMl;
  }

  public void setCostoMl(double costoMl) {
    this.costoMl = costoMl;
  }

  public double getCostoUsd() {
    return this.costoUsd;
  }

  public void setCostoUsd(double costoUsd) {
    this.costoUsd = costoUsd;
  }

  public Date getFecha() {
    return this.fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public double getSaldo() {
    return this.saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public Empresa getEmpresa() {
    return this.empresa;
  }

  public void setEmpresa(Empresa empresa) {
    this.empresa = empresa;
  }

  public Moneda getMoneda() {
    return this.moneda;
  }

  public void setMoneda(Moneda moneda) {
    this.moneda = moneda;
  }

  public ProductosInventario getProductosInventario() {
    return this.productosInventario;
  }

  public void setProductosInventario(ProductosInventario productosInventario) {
    this.productosInventario = productosInventario;
  }

  public Unidad getUnidade() {
    return this.unidade;
  }

  public void setUnidade(Unidad unidade) {
    this.unidade = unidade;
  }

}
