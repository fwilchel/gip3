package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the movimientos_inventario database table.
 *
 */
@Entity
@Table(name = "movimientos_inventario")
@NamedQuery(name = MovimientosInventario.ELIMINAR_REGISTROS_POR_DOCUMENTO, query = "DELETE FROM MovimientosInventario mi WHERE mi.documento.id = :idDocumento")
public class MovimientosInventario implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String ELIMINAR_REGISTROS_POR_DOCUMENTO = "MovimientosInventario.eliminarRegistrosPorDocumento";
  @Id
  @SequenceGenerator(name = "movimientosInventarioSeq", sequenceName = "movimientos_inventario_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "movimientosInventarioSeq", strategy = GenerationType.SEQUENCE)
  private Long id;

  private BigDecimal cantidad;

  @Column(name = "corte_cierre")
  private String corteCierre;

  private Timestamp fecha;

  @Column(name = "valor_unitario_ml")
  private double valorUnitarioMl;

  @Column(name = "valot_unitario_usd")
  private double valotUnitarioUsd;

  // bi-directional many-to-one association to BodegasLogica
  @ManyToOne
  @JoinColumn(name = "id_bodega_logica_destino")
  private BodegasLogica bodegasLogica1;

  // bi-directional many-to-one association to BodegasLogica
  @ManyToOne
  @JoinColumn(name = "id_bodega_logica_origen")
  private BodegasLogica bodegasLogica2;

  // bi-directional many-to-one association to Documento
  @ManyToOne
  @JoinColumn(name = "id_documento")
  private Documento documento;

  // bi-directional many-to-one association to Moneda
  @ManyToOne
  @JoinColumn(name = "id_ml")
  private Moneda moneda;

  // bi-directional many-to-one association to ProductosInventario
  @ManyToOne
  @JoinColumn(name = "id_producto")
  private ProductosInventario productosInventario;

  // bi-directional many-to-one association to TipoMovimiento
  @ManyToOne
  @JoinColumn(name = "id_tipo_movimiento")
  private TipoMovimiento tipoMovimiento;

  // bi-directional many-to-one association to Ubicacion
  @ManyToOne
  @JoinColumn(name = "id_ubicacion_destino")
  private Ubicacion ubicacionDestino;

  // bi-directional many-to-one association to Ubicacion
  @ManyToOne
  @JoinColumn(name = "id_ubicacion_origen")
  private Ubicacion ubicacionOrigen;

  // bi-directional many-to-one association to Unidad
  @ManyToOne
  @JoinColumn(name = "id_unidad")
  private Unidad unidade;

  public MovimientosInventario() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getCantidad() {
    return this.cantidad;
  }

  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

  public String getCorteCierre() {
    return this.corteCierre;
  }

  public void setCorteCierre(String corteCierre) {
    this.corteCierre = corteCierre;
  }

  public Timestamp getFecha() {
    return this.fecha;
  }

  public void setFecha(Timestamp fecha) {
    this.fecha = fecha;
  }

  public double getValorUnitarioMl() {
    return this.valorUnitarioMl;
  }

  public void setValorUnitarioMl(double valorUnitarioMl) {
    this.valorUnitarioMl = valorUnitarioMl;
  }

  public double getValotUnitarioUsd() {
    return this.valotUnitarioUsd;
  }

  public void setValotUnitarioUsd(double valotUnitarioUsd) {
    this.valotUnitarioUsd = valotUnitarioUsd;
  }

  public BodegasLogica getBodegasLogica1() {
    return this.bodegasLogica1;
  }

  public void setBodegasLogica1(BodegasLogica bodegasLogica1) {
    this.bodegasLogica1 = bodegasLogica1;
  }

  public BodegasLogica getBodegasLogica2() {
    return this.bodegasLogica2;
  }

  public void setBodegasLogica2(BodegasLogica bodegasLogica2) {
    this.bodegasLogica2 = bodegasLogica2;
  }

  public Documento getDocumento() {
    return this.documento;
  }

  public void setDocumento(Documento documento) {
    this.documento = documento;
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

  public TipoMovimiento getTipoMovimiento() {
    return this.tipoMovimiento;
  }

  public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
    this.tipoMovimiento = tipoMovimiento;
  }

  public Ubicacion getUbicacionDestino() {
    return this.ubicacionDestino;
  }

  public void setUbicacionDestino(Ubicacion ubicacionDestino) {
    this.ubicacionDestino = ubicacionDestino;
  }

  public Ubicacion getUbicacionOrigen() {
    return this.ubicacionOrigen;
  }

  public void setUbicacionOrigen(Ubicacion ubicacionOrigen) {
    this.ubicacionOrigen = ubicacionOrigen;
  }

  public Unidad getUnidade() {
    return this.unidade;
  }

  public void setUnidade(Unidad unidade) {
    this.unidade = unidade;
  }

}
