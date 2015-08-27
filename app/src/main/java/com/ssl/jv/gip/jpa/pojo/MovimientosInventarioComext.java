package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the movimientos_inventario database table.
 *
 */
@Entity
@Table(name = "movimientos_inventario_comext")
@NamedQueries({
  @NamedQuery(name = "MovimientosInventarioComext.findAll", query = "SELECT m FROM MovimientosInventarioComext m"),
  @NamedQuery(name = "MovimientosInventarioComext.ultimosSaldos", query = "SELECT m FROM MovimientosInventarioComext m WHERE m.fecha = (SELECT max(m2.fecha) FROM MovimientosInventarioComext m2 WHERE m2.productosInventarioComext.idProducto = m.productosInventarioComext.idProducto) "),
  @NamedQuery(name = MovimientosInventarioComext.FIND_BY_SKU, query = "SELECT m FROM MovimientosInventarioComext m JOIN m.productosInventarioComext pice JOIN pice.productosInventario pi WHERE UPPER(pi.sku) LIKE UPPER(:sku) ORDER BY m.fecha DESC ")})
public class MovimientosInventarioComext implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -6177643071198857825L;

  public static final String FIND_BY_SKU = "MovimientosInventarioComext.findBySku";

  @Id
  @SequenceGenerator(name = "movimientosInventarioComExtSeq", sequenceName = "movimientos_inventario_comext_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "movimientosInventarioComExtSeq", strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "consecutivo_documento")
  private String consecutivoDocumento;

  @ManyToOne
  @JoinColumn(name = "id_tipo_movimiento")
  private TipoMovimiento tipoMovimiento;

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "id_producto")
  private ProductosInventarioComext productosInventarioComext;

  private BigDecimal cantidad;

  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;

  private BigDecimal saldo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public TipoMovimiento getTipoMovimiento() {
    return tipoMovimiento;
  }

  public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
    this.tipoMovimiento = tipoMovimiento;
  }

  public ProductosInventarioComext getProductosInventarioComext() {
    return productosInventarioComext;
  }

  public void setProductosInventarioComext(
      ProductosInventarioComext productosInventarioComext) {
    this.productosInventarioComext = productosInventarioComext;
  }

  public BigDecimal getCantidad() {
    return cantidad;
  }

  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }

}
