package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the bodegas_logicas database table.
 *
 */
@Entity
@Table(name = "bodegas_logicas")
@NamedQuery(name = "BodegasLogica.findAll", query = "SELECT b FROM BodegasLogica b")
public class BodegasLogica implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private String nombre;

  @Column(name = "tipo_bodega")
  private String tipoBodega;

  // bi-directional many-to-one association to MovimientosInventario
  @OneToMany(mappedBy = "bodegasLogica1")
  private List<MovimientosInventario> movimientosInventarios1;

  // bi-directional many-to-one association to MovimientosInventario
  @OneToMany(mappedBy = "bodegasLogica2")
  private List<MovimientosInventario> movimientosInventarios2;

  // bi-directional many-to-one association to ProductosXDocumento
  @OneToMany(mappedBy = "bodegasLogica1")
  private List<ProductosXDocumento> productosxdocumentos1;

  // bi-directional many-to-one association to ProductosXDocumento
  @OneToMany(mappedBy = "bodegasLogica2")
  private List<ProductosXDocumento> productosxdocumentos2;

  // bi-directional many-to-one association to Saldo
  @OneToMany(mappedBy = "bodegasLogica")
  private List<Saldo> saldos;

  public BodegasLogica() {
  }

  public BodegasLogica(Long id) {
    this.id = id;
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

  public String getTipoBodega() {
    return this.tipoBodega;
  }

  public void setTipoBodega(String tipoBodega) {
    this.tipoBodega = tipoBodega;
  }

  public List<MovimientosInventario> getMovimientosInventarios1() {
    return this.movimientosInventarios1;
  }

  public void setMovimientosInventarios1(
      List<MovimientosInventario> movimientosInventarios1) {
    this.movimientosInventarios1 = movimientosInventarios1;
  }

  public MovimientosInventario addMovimientosInventarios1(
      MovimientosInventario movimientosInventarios1) {
    getMovimientosInventarios1().add(movimientosInventarios1);
    movimientosInventarios1.setBodegasLogica1(this);

    return movimientosInventarios1;
  }

  public MovimientosInventario removeMovimientosInventarios1(
      MovimientosInventario movimientosInventarios1) {
    getMovimientosInventarios1().remove(movimientosInventarios1);
    movimientosInventarios1.setBodegasLogica1(null);

    return movimientosInventarios1;
  }

  public List<MovimientosInventario> getMovimientosInventarios2() {
    return this.movimientosInventarios2;
  }

  public void setMovimientosInventarios2(
      List<MovimientosInventario> movimientosInventarios2) {
    this.movimientosInventarios2 = movimientosInventarios2;
  }

  public MovimientosInventario addMovimientosInventarios2(
      MovimientosInventario movimientosInventarios2) {
    getMovimientosInventarios2().add(movimientosInventarios2);
    movimientosInventarios2.setBodegasLogica2(this);

    return movimientosInventarios2;
  }

  public MovimientosInventario removeMovimientosInventarios2(
      MovimientosInventario movimientosInventarios2) {
    getMovimientosInventarios2().remove(movimientosInventarios2);
    movimientosInventarios2.setBodegasLogica2(null);

    return movimientosInventarios2;
  }

  public List<ProductosXDocumento> getProductosxdocumentos1() {
    return this.productosxdocumentos1;
  }

  public void setProductosxdocumentos1(
      List<ProductosXDocumento> productosxdocumentos1) {
    this.productosxdocumentos1 = productosxdocumentos1;
  }

  public ProductosXDocumento addProductosxdocumentos1(
      ProductosXDocumento productosxdocumentos1) {
    getProductosxdocumentos1().add(productosxdocumentos1);
    productosxdocumentos1.setBodegasLogica1(this);

    return productosxdocumentos1;
  }

  public ProductosXDocumento removeProductosxdocumentos1(
      ProductosXDocumento productosxdocumentos1) {
    getProductosxdocumentos1().remove(productosxdocumentos1);
    productosxdocumentos1.setBodegasLogica1(null);

    return productosxdocumentos1;
  }

  public List<ProductosXDocumento> getProductosxdocumentos2() {
    return this.productosxdocumentos2;
  }

  public void setProductosxdocumentos2(
      List<ProductosXDocumento> productosxdocumentos2) {
    this.productosxdocumentos2 = productosxdocumentos2;
  }

  public ProductosXDocumento addProductosxdocumentos2(
      ProductosXDocumento productosxdocumentos2) {
    getProductosxdocumentos2().add(productosxdocumentos2);
    productosxdocumentos2.setBodegasLogica2(this);

    return productosxdocumentos2;
  }

  public ProductosXDocumento removeProductosxdocumentos2(
      ProductosXDocumento productosxdocumentos2) {
    getProductosxdocumentos2().remove(productosxdocumentos2);
    productosxdocumentos2.setBodegasLogica2(null);

    return productosxdocumentos2;
  }

  public List<Saldo> getSaldos() {
    return this.saldos;
  }

  public void setSaldos(List<Saldo> saldos) {
    this.saldos = saldos;
  }

  public Saldo addSaldo(Saldo saldo) {
    getSaldos().add(saldo);
    saldo.setBodegasLogica(this);

    return saldo;
  }

  public Saldo removeSaldo(Saldo saldo) {
    getSaldos().remove(saldo);
    saldo.setBodegasLogica(null);

    return saldo;
  }
}
