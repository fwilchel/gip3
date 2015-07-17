package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the productosxcliente database table.
 *
 */
@Entity
@NamedQueries(value = {
  @NamedQuery(name = "ProductosXCliente.findAll", query = "SELECT p FROM ProductosXCliente p"),

  
  @NamedQuery(name = "ProductosXCliente.findByClientePuntoVenta", query = "SELECT p FROM ProductosXCliente p JOIN FETCH p.productosInventario pi JOIN FETCH p.cliente c JOIN FETCH pi.unidadVenta uv WHERE p.pk.idCliente= :idCliente AND p.pk.idPuntoVenta= :idPuntoVenta AND p.vigente = true AND pi.desactivado = true AND p.activo = true ORDER BY pi.sku"),
  @NamedQuery(name = ProductosXCliente.NQ_BUSCAR_PRODUCTOS_X_CLIENTE_Y_PUNTO_VENTA_ACTIVOS_Y_VIGENTES, query = "SELECT pxc FROM ProductosXCliente pxc JOIN FETCH pxc.productosInventario pi JOIN FETCH pi.unidadReceta WHERE pxc.cliente.id = :idCliente AND pxc.puntoVenta.id = :idPuntoVenta AND pxc.activo = true AND :fechaActual BETWEEN pxc.fechaInicialVigencia and pxc.fechaFinalVigencia ORDER BY pxc.productosInventario.sku"),
  @NamedQuery(name = ProductosXCliente.BUSCAR_POR_PRODUCTO_POR_CLIENTE_Y_PUNTO_VENTA, query = "SELECT pxc FROM ProductosXCliente pxc WHERE pxc.productosInventario.id = :idProducto AND pxc.cliente.id = :idCliente AND pxc.puntoVenta.id = :idPuntoVenta AND pxc.activo = true AND pxc.vigente = true")
})
public class ProductosXCliente implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String BUSCAR_PRODUCTOS_X_CLIENTES = "select pxc.id as id, pxc.activo as activo, pxc.descuentoxproducto as descuento, pxc.fecha_final_vigencia as fechaFinalVigencia, pxc.fecha_inicial_vigencia as fechaInicialVigencia, pxc.iva as iva, pxc.otros_descuentos as otrosDescuentos, pxc.precio_ml as precioML, pxc.precio_usd as precioUSD, pxc.vigente as vigente, pxc.id_cliente as idCliente, c.nombre as nombreCliente, pxc.id_producto as idProducto, pi.sku as sku, pi.nombre as nombreProducto, pxc.id_punto_venta as idPuntoVenta, pv.nombre as nombrePuntoventa, pxc.id_ml as idMoneda from ProductosXCliente pxc cross join productos_inventario pi cross join clientes c cross join punto_venta pv where pxc.id_producto=pi.id and pxc.id_cliente=c.id and pxc.id_punto_venta=pv.id and (upper(pi.sku) like upper(:sku)) and (upper(c.nombre) like upper(:nombreCliente)) and (upper(pv.nombre) like upper(:nombrePuntoVenta)) order by pi.sku";
  public static final String BUSCAR_PRODUCTOS_X_CLIENTES_ACTIVOS = "select pxc.id as id, pxc.activo as activo, pxc.descuentoxproducto as descuento, pxc.fecha_final_vigencia as fechaFinalVigencia, pxc.fecha_inicial_vigencia as fechaInicialVigencia, pxc.iva as iva, pxc.otros_descuentos as otrosDescuentos, pxc.precio_ml as precioML, pxc.precio_usd as precioUSD, pxc.vigente as vigente, pxc.id_cliente as idCliente, c.nombre as nombreCliente, pxc.id_producto as idProducto, pi.sku as sku, pi.nombre as nombreProducto, pxc.id_punto_venta as idPuntoVenta, pv.nombre as nombrePuntoventa, pxc.id_ml as idMoneda from ProductosXCliente pxc cross join productos_inventario pi cross join clientes c cross join punto_venta pv where pxc.id_producto=pi.id and pxc.id_cliente=c.id and pxc.id_punto_venta=pv.id and (upper(pi.sku) like upper(:sku)) and (upper(c.nombre) like upper(:nombreCliente)) and (upper(pv.nombre) like upper(:nombrePuntoVenta)) and pxc.activo=true order by pi.sku";
  public static final String NQ_BUSCAR_PRODUCTOS_X_CLIENTE_Y_PUNTO_VENTA_ACTIVOS_Y_VIGENTES = "ProductosXCliente.buscarProductosXClienteYPuntoVentaActivos";
  public static final String BUSCAR_POR_PRODUCTO_POR_CLIENTE_Y_PUNTO_VENTA = "ProductosXCliente.buscarXProductoXClienteYPuntoVenta";

  @EmbeddedId
  private ProductosXClientePK pk;
  @Id
  @SequenceGenerator(name = "prodxcliente_id_seq", sequenceName = "productosxcliente_id_seq1", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodxcliente_id_seq")
  private Long id;
  private Boolean activo;
  private BigDecimal descuentoxproducto;
  @Column(name = "fecha_final_vigencia")
  private Timestamp fechaFinalVigencia;
  @Column(name = "fecha_inicial_vigencia")
  private Timestamp fechaInicialVigencia;
  private BigDecimal iva;
  @Column(name = "otros_descuentos")
  private BigDecimal otrosDescuentos;
  @Column(name = "precio_ml")
  private BigDecimal precioMl;
  @Column(name = "precio_usd")
  private BigDecimal precioUsd;
  private Boolean vigente;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "id_producto", referencedColumnName = "id", insertable = false, updatable = false)
  private ProductosInventario productosInventario;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "id_cliente", referencedColumnName = "id", insertable = false, updatable = false)
  private Cliente cliente;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "id_punto_venta", referencedColumnName = "id", insertable = false, updatable = false)
  private PuntoVenta puntoVenta;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_ml")
  private Moneda moneda;

  /**
   * @return the pk
   */
  public ProductosXClientePK getPk() {
    return pk;
  }

  /**
   * @param pk the pk to set
   */
  public void setPk(ProductosXClientePK pk) {
    this.pk = pk;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the activo
   */
  public Boolean getActivo() {
    return activo;
  }

  /**
   * @param activo the activo to set
   */
  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  /**
   * @return the descuentoxproducto
   */
  public BigDecimal getDescuentoxproducto() {
    return descuentoxproducto;
  }

  /**
   * @param descuentoxproducto the descuentoxproducto to set
   */
  public void setDescuentoxproducto(BigDecimal descuentoxproducto) {
    this.descuentoxproducto = descuentoxproducto;
  }

  /**
   * @return the fechaFinalVigencia
   */
  public Timestamp getFechaFinalVigencia() {
    return fechaFinalVigencia;
  }

  /**
   * @param fechaFinalVigencia the fechaFinalVigencia to set
   */
  public void setFechaFinalVigencia(Timestamp fechaFinalVigencia) {
    this.fechaFinalVigencia = fechaFinalVigencia;
  }

  /**
   * @return the fechaInicialVigencia
   */
  public Timestamp getFechaInicialVigencia() {
    return fechaInicialVigencia;
  }

  /**
   * @param fechaInicialVigencia the fechaInicialVigencia to set
   */
  public void setFechaInicialVigencia(Timestamp fechaInicialVigencia) {
    this.fechaInicialVigencia = fechaInicialVigencia;
  }

  /**
   * @return the iva
   */
  public BigDecimal getIva() {
    return iva;
  }

  /**
   * @param iva the iva to set
   */
  public void setIva(BigDecimal iva) {
    this.iva = iva;
  }

  /**
   * @return the otrosDescuentos
   */
  public BigDecimal getOtrosDescuentos() {
    return otrosDescuentos;
  }

  /**
   * @param otrosDescuentos the otrosDescuentos to set
   */
  public void setOtrosDescuentos(BigDecimal otrosDescuentos) {
    this.otrosDescuentos = otrosDescuentos;
  }

  /**
   * @return the precioMl
   */
  public BigDecimal getPrecioMl() {
    return precioMl;
  }

  /**
   * @param precioMl the precioMl to set
   */
  public void setPrecioMl(BigDecimal precioMl) {
    this.precioMl = precioMl;
  }

  /**
   * @return the precioUsd
   */
  public BigDecimal getPrecioUsd() {
    return precioUsd;
  }

  /**
   * @param precioUsd the precioUsd to set
   */
  public void setPrecioUsd(BigDecimal precioUsd) {
    this.precioUsd = precioUsd;
  }

  /**
   * @return the vigente
   */
  public Boolean getVigente() {
    return vigente;
  }

  /**
   * @param vigente the vigente to set
   */
  public void setVigente(Boolean vigente) {
    this.vigente = vigente;
  }

  /**
   * @return the productosInventario
   */
  public ProductosInventario getProductosInventario() {
    return productosInventario;
  }

  /**
   * @param productosInventario the productosInventario to set
   */
  public void setProductosInventario(ProductosInventario productosInventario) {
    this.productosInventario = productosInventario;
  }

  /**
   * @return the cliente
   */
  public Cliente getCliente() {
    return cliente;
  }

  /**
   * @param cliente the cliente to set
   */
  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  /**
   * @return the puntoVenta
   */
  public PuntoVenta getPuntoVenta() {
    return puntoVenta;
  }

  /**
   * @param puntoVenta the puntoVenta to set
   */
  public void setPuntoVenta(PuntoVenta puntoVenta) {
    this.puntoVenta = puntoVenta;
  }

  /**
   * @return the moneda
   */
  public Moneda getMoneda() {
    return moneda;
  }

  /**
   * @param moneda the moneda to set
   */
  public void setMoneda(Moneda moneda) {
    this.moneda = moneda;
  }

}
