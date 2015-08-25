package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the productosxdocumentos database table.
 *
 */
@Entity
@Table(name = "productosxdocumentos")
@NamedQueries({
  @NamedQuery(name = "ProductosXDocumento.findAll", query = "SELECT p FROM ProductosXDocumento p"),
  @NamedQuery(name = ProductosXDocumento.FIND_BY_DOCUMENTO, query = "SELECT p FROM ProductosXDocumento p JOIN FETCH p.productosInventario pi JOIN FETCH pi.unidadVenta uv WHERE p.id.idDocumento = :idDocumento ORDER BY p.productosInventario.nombre"),
  @NamedQuery(name = ProductosXDocumento.FIND_BY_DOCUMENTO_ORDER_BY_SKU, query = "SELECT p FROM ProductosXDocumento p WHERE p.id.idDocumento = :idDocumento ORDER BY p.productosInventario.sku"),
  @NamedQuery(name = ProductosXDocumento.FIND_BY_DOCUMENTO_COLECCIONES, query = "SELECT distinct p FROM ProductosXDocumento p JOIN FETCH p.productosInventario pi JOIN FETCH pi.productosxclientes pxc JOIN FETCH pi.productosInventarioComext pic JOIN FETCH pic.tipoLoteoic tl JOIN FETCH pic.cuentaContable  cc WHERE p.id.idDocumento = :idDocumento ORDER BY pic.tipoLoteoic.id"),
  @NamedQuery(name = ProductosXDocumento.FIND_BY_DOCUMENTO_AND_CLIENTE, query = "SELECT p FROM ProductosXDocumento p LEFT JOIN p.productosInventario.productosXClienteComexts pcce WHERE p.id.idDocumento = :idDocumento AND pcce.cliente.id = :idCliente ORDER BY pcce.regSanitario"),
  @NamedQuery(name = ProductosXDocumento.FIND_PRODUCTOS_BY_DOCUMENTO_CE, query = "SELECT distinct p FROM ProductosXDocumento p JOIN FETCH p.productosInventario pi JOIN FETCH pi.productosXClienteComexts pxc JOIN FETCH pi.productosInventarioComext pic JOIN FETCH pic.tipoLoteoic tl JOIN FETCH pic.cuentaContable  cc WHERE p.id.idDocumento = :idDocumento ORDER BY pic.tipoLoteoic.id"),
  @NamedQuery(name = ProductosXDocumento.BUSCAR_PRODUCTOS_X_DOCUMENTO_LE, query = "SELECT distinct p FROM ProductosXDocumento p JOIN FETCH p.productosInventario pi JOIN FETCH pi.productosInventarioComext pic JOIN FETCH pic.tipoLoteoic tl WHERE p.id.idDocumento = :idDocumento ORDER BY pic.tipoLoteoic.id"),
  @NamedQuery(name = ProductosXDocumento.ELIMINAR_REGISTROS_POR_DOCUMENTO, query = "DELETE FROM ProductosXDocumento pxd WHERE pxd.id.idDocumento = :idDocumento"),
  @NamedQuery(name = ProductosXDocumento.FIND_BY_DOCUMENTO_PICE, query = "SELECT p FROM ProductosXDocumento p   JOIN FETCH p.productosInventario pi JOIN FETCH pi.productosInventarioComext pic WHERE p.id.idDocumento = :idDocumento ORDER BY p.productosInventario.nombre")
})
public class ProductosXDocumento implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1420770172594917035L;

  public static final String FIND_BY_DOCUMENTO = "ProductosXDocumento.findByDocumento";
  public static final String FIND_BY_DOCUMENTO_AND_CLIENTE = "ProductosXDocumento.findByDocumentoAndCliente";
  public static final String FIND_BY_DOCUMENTO_COLECCIONES = "ProductosXDocumento.findByDocumentoColecciones";
  public static final String FIND_BY_DOCUMENTO_ORDER_BY_SKU = "ProductosXDocumento.findByDocumentoOrderBySKU";
  public static final String ELIMINAR_REGISTROS_POR_DOCUMENTO = "ProductosXDocumento.eliminarRegistrosPorDocumento";
  public static final String FIND_PRODUCTOS_BY_DOCUMENTO_CE = "ProductosXDocumento.findProductosByDocumentoCE";
  public static final String BUSCAR_PRODUCTOS_X_DOCUMENTO_LE = "ProductosXDocumento.findProductosCafeLE";
  public static final String FIND_BY_DOCUMENTO_PICE = "ProductosXDocumento.findByDocumento_PICE";

  @EmbeddedId
  private ProductosXDocumentoPK id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_producto", referencedColumnName = "id", insertable = false, updatable = false)
  private ProductosInventario productosInventario;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_documento", referencedColumnName = "id", insertable = false, updatable = false)
  private Documento documento;

  private Boolean calidad;

  @Column(name = "cantidad_cajas_item")
  private BigDecimal cantidadCajasItem;

  @Column(name = "cantidad_pallets_item")
  private BigDecimal cantidadPalletsItem;

  @Column(name = "cantidad_x_embalaje")
  private BigDecimal cantidadXEmbalaje;

  private BigDecimal cantidad1;

  private BigDecimal cantidad2;

  private BigDecimal descuentoxproducto;

  @Column(name = "fecha_entrega")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEntrega;

  @Column(name = "fecha_estimada_entrega")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEstimadaEntrega;

  private Boolean informacion;

  private BigDecimal iva;

  private String observacion1;

  private String observacion2;

  @Column(name = "otros_descuentos")
  private BigDecimal otrosDescuentos;

  @Column(name = "total_peso_bruto_item")
  private BigDecimal totalPesoBrutoItem;

  @Column(name = "total_peso_neto_item")
  private BigDecimal totalPesoNetoItem;

  @Column(name = "valor_descuentoxproducto")
  private BigDecimal valorDescuentoxproducto;

  @Column(name = "valor_total")
  private BigDecimal valorTotal;

  @Column(name = "valor_unitario_usd")
  private BigDecimal valorUnitarioUsd;

  @Column(name = "valor_unitatrio_ml")
  private BigDecimal valorUnitatrioMl;

  // bi-directional many-to-one association to BodegasLogica
  @ManyToOne
  @JoinColumn(name = "id_bodega_logica_origen")
  private BodegasLogica bodegasLogica1;

  // bi-directional many-to-one association to BodegasLogica
  @ManyToOne
  @JoinColumn(name = "id_bodega_logica_destino")
  private BodegasLogica bodegasLogica2;

  // bi-directional many-to-one association to Moneda
  @ManyToOne
  @JoinColumn(name = "id_ml")
  private Moneda moneda;

  // bi-directional many-to-one association to TipoDevolucion
  @ManyToOne
  @JoinColumn(name = "id_tipo_devolucion")
  private TipoDevolucion tipoDevolucion;

  // bi-directional many-to-one association to Unidad
  @ManyToOne
  @JoinColumn(name = "id_unidades")
  private Unidad unidade;

  @Transient
  private BigDecimal cantidadVD;

  public ProductosXDocumento() {
  }

  public ProductosXDocumentoPK getId() {
    return this.id;
  }

  public void setId(ProductosXDocumentoPK id) {
    this.id = id;
  }

  public Documento getDocumento() {
    return documento;
  }

  public void setDocumento(Documento documento) {
    this.documento = documento;
  }

  public ProductosInventario getProductosInventario() {
    return this.productosInventario;
  }

  public void setProductosInventario(ProductosInventario productosInventario) {
    this.productosInventario = productosInventario;
  }

  public Boolean getCalidad() {
    return this.calidad;
  }

  public void setCalidad(Boolean calidad) {
    this.calidad = calidad;
  }

  public BigDecimal getCantidadCajasItem() {
    return this.cantidadCajasItem;
  }

  public void setCantidadCajasItem(BigDecimal cantidadCajasItem) {
    this.cantidadCajasItem = cantidadCajasItem;
  }

  public BigDecimal getCantidadPalletsItem() {
    return this.cantidadPalletsItem;
  }

  public void setCantidadPalletsItem(BigDecimal cantidadPalletsItem) {
    this.cantidadPalletsItem = cantidadPalletsItem;
  }

  public BigDecimal getCantidadXEmbalaje() {
    return this.cantidadXEmbalaje;
  }

  public void setCantidadXEmbalaje(BigDecimal cantidadXEmbalaje) {
    this.cantidadXEmbalaje = cantidadXEmbalaje;
  }

  public BigDecimal getCantidad1() {
    return this.cantidad1;
  }

  public void setCantidad1(BigDecimal cantidad1) {
    this.cantidad1 = cantidad1;
  }

  public BigDecimal getCantidad2() {
    return this.cantidad2;
  }

  public void setCantidad2(BigDecimal cantidad2) {
    this.cantidad2 = cantidad2;
  }

  public BigDecimal getDescuentoxproducto() {
    return this.descuentoxproducto;
  }

  public void setDescuentoxproducto(BigDecimal descuentoxproducto) {
    this.descuentoxproducto = descuentoxproducto;
  }

  public Date getFechaEntrega() {
    return this.fechaEntrega;
  }

  public void setFechaEntrega(Date fechaEntrega) {
    this.fechaEntrega = fechaEntrega;
  }

  public Date getFechaEstimadaEntrega() {
    return this.fechaEstimadaEntrega;
  }

  public void setFechaEstimadaEntrega(Date fechaEstimadaEntrega) {
    this.fechaEstimadaEntrega = fechaEstimadaEntrega;
  }

  public Boolean getInformacion() {
    return this.informacion;
  }

  public void setInformacion(Boolean informacion) {
    this.informacion = informacion;
  }

  public BigDecimal getIva() {
    return this.iva;
  }

  public void setIva(BigDecimal iva) {
    this.iva = iva;
  }

  public String getObservacion1() {
    return this.observacion1;
  }

  public void setObservacion1(String observacion1) {
    this.observacion1 = observacion1;
  }

  public String getObservacion2() {
    return this.observacion2;
  }

  public void setObservacion2(String observacion2) {
    this.observacion2 = observacion2;
  }

  public BigDecimal getOtrosDescuentos() {
    return this.otrosDescuentos;
  }

  public void setOtrosDescuentos(BigDecimal otrosDescuentos) {
    this.otrosDescuentos = otrosDescuentos;
  }

  public BigDecimal getTotalPesoBrutoItem() {
    return this.totalPesoBrutoItem;
  }

  public void setTotalPesoBrutoItem(BigDecimal totalPesoBrutoItem) {
    this.totalPesoBrutoItem = totalPesoBrutoItem;
  }

  public BigDecimal getTotalPesoNetoItem() {
    return this.totalPesoNetoItem;
  }

  public void setTotalPesoNetoItem(BigDecimal totalPesoNetoItem) {
    this.totalPesoNetoItem = totalPesoNetoItem;
  }

  public BigDecimal getValorDescuentoxproducto() {
    return this.valorDescuentoxproducto;
  }

  public void setValorDescuentoxproducto(BigDecimal valorDescuentoxproducto) {
    this.valorDescuentoxproducto = valorDescuentoxproducto;
  }

  public BigDecimal getValorTotal() {
    return this.valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }

  public BigDecimal getValorUnitarioUsd() {
    return this.valorUnitarioUsd;
  }

  public void setValorUnitarioUsd(BigDecimal valorUnitarioUsd) {
    this.valorUnitarioUsd = valorUnitarioUsd;
  }

  public BigDecimal getValorUnitatrioMl() {
    return this.valorUnitatrioMl;
  }

  public void setValorUnitatrioMl(BigDecimal valorUnitatrioMl) {
    this.valorUnitatrioMl = valorUnitatrioMl;
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

  public Moneda getMoneda() {
    return this.moneda;
  }

  public void setMoneda(Moneda moneda) {
    this.moneda = moneda;
  }

  public TipoDevolucion getTipoDevolucion() {
    return this.tipoDevolucion;
  }

  public void setTipoDevolucion(TipoDevolucion tipoDevolucion) {
    this.tipoDevolucion = tipoDevolucion;
  }

  public Unidad getUnidade() {
    return this.unidade;
  }

  public void setUnidade(Unidad unidade) {
    this.unidade = unidade;
  }

  /**
   * @return the cantidadVD
   */
  public BigDecimal getCantidadVD() {
    return cantidadVD;
  }

  /**
   * @param cantidadVD the cantidadVD to set
   */
  public void setCantidadVD(BigDecimal cantidadVD) {
    this.cantidadVD = cantidadVD;
  }

}
