package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the productos_inventario database table.
 *
 */
@Entity
@Table(name = "productos_inventario")
@NamedQueries({
  @NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ALL, query = "SELECT p FROM ProductosInventario p"),
  @NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ACTIVOS, query = "SELECT p FROM ProductosInventario p WHERE p.desactivado = false"),
  @NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ACTIVOS_DEV, query = "SELECT p.id, p.sku, p.nombre, p.categoriasInventario.id, p.categoriasInventario.nombre, p.unidadVenta.nombre, p.unidadVenta.id FROM ProductosInventario p JOIN p.categoriasInventario ci WHERE p.desactivado = false"),
  @NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ACTIVOS_PAISES, query = "SELECT distinct p.id, p.sku, p.nombre, p.categoriasInventario.id, p.categoriasInventario.nombre, p.unidadVenta.nombre, p.unidadVenta.id FROM ProductosInventario p JOIN p.categoriasInventario ci WHERE p.desactivado = false AND p.pais.id in :paises"),
  @NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_USUARIO_CATEGORIA_SKU_NOMBRE_ESTADO, query = "SELECT p FROM ProductosInventario p LEFT JOIN p.categoriasInventario ci JOIN FETCH p.pais pa JOIN FETCH pa.moneda m JOIN FETCH p.unidadVenta uv JOIN pa.usuarios u WHERE u.id = :idUsuario AND (false = :paramDesactivado OR p.desactivado = :desactivado) AND (false = :paramCategoria OR ci.id = :idCategoria) AND (false = :paramSku OR p.sku like :sku) AND (false = :paramNombre OR p.nombre like :nombre)"),
  @NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_SKU, query = "SELECT p FROM ProductosInventario p JOIN FETCH p.productosInventarioComext pic LEFT JOIN FETCH pic.tipoLoteoic tlo LEFT JOIN FETCH p.unidadVenta uv WHERE p.sku = :sku"),
  @NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_SKUs, query = "SELECT p FROM ProductosInventario p WHERE p.sku in (:skus)"),
  @NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_DESACTIVADO_CATEGORIA_SKU_NOMBRE_AND_CONTROLSTOCK, query = "SELECT p FROM ProductosInventario p JOIN p.productosInventarioComext pice JOIN p.categoriasInventario ci  WHERE p.desactivado = :desactivado AND (false = :paramCategoria OR ci.id = :idCategoria) AND p.sku like :sku AND p.nombre like :nombre AND pice.controlStock = :controlStock ORDER BY p.sku"),
  @NamedQuery(name = ProductosInventario.BUSCAR_PRODUCTOS_REPORTE_VENTAS_CE, query = "SELECT p FROM ProductosInventario p WHERE UPPER (p.sku) LIKE UPPER (:sku) AND UPPER (p.nombre) LIKE UPPER (:nombre) AND p.desactivado = :desactivado"),
  @NamedQuery(name = ProductosInventario.BUSCAR_PRODUCTOS_X_CODIGO_BARRAS_UV, query = "SELECT p FROM ProductosInventario p JOIN FETCH p.unidadVenta WHERE p.codigoBarrasUv = :codigoBarrasUv AND p.desactivado = true")
})
public class ProductosInventario implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -1938090563895800247L;
  public static final String PRODUCTOS_INVENTARIO_FIND_ALL = "ProductosInventario.findAll";
  public static final String PRODUCTOS_INVENTARIO_FIND_ACTIVOS = "ProductosInventario.findActivos";
  public static final String PRODUCTOS_INVENTARIO_FIND_ACTIVOS_DEV = "ProductosInventario.findActivosDev";
  public static final String PRODUCTOS_INVENTARIO_FIND_ACTIVOS_PAISES = "ProductosInventario.findActivosPaises";
  public static final String PRODUCTOS_INVENTARIO_FIND_BY_USUARIO_CATEGORIA_SKU_NOMBRE_ESTADO = "ProductosInventario.findByUsuarioCategoriaSkuNombreEstado";
  public static final String PRODUCTOS_INVENTARIO_FIND_BY_SKU = "ProductosInventario.findBySku";
  public static final String PRODUCTOS_INVENTARIO_FIND_BY_SKUs = "ProductosInventario.findBySkus";
  public static final String PRODUCTOS_INVENTARIO_FIND_BY_DESACTIVADO_CATEGORIA_SKU_NOMBRE_AND_CONTROLSTOCK = "ProductosInventario.findByDesactivadoCategoriaSkuNombreAndControlStock";
  public static final String BUSCAR_PRODUCTOS_REPORTE_VENTAS_CE = "ProductosInventario.buscarProductosReporteVentasCE";
  public static final String BUSCAR_PRODUCTOS_X_CODIGO_BARRAS_UV = "ProductosInventario.buscarProductosPorCodigoBarrasUV";

  @Id
  @SequenceGenerator(name = "productoSeq", sequenceName = "productos_inventario_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "productoSeq", strategy = GenerationType.SEQUENCE)
  private Long id;

  private String abc;

  @Column(name = "codigo_barras_ud")
  private Long codigoBarrasUd;

  @Column(name = "codigo_barras_uv")
  private Long codigoBarrasUv;

  @Column(name = "codigo_pos")
  private String codigoPos;

  private Boolean consumible;

  @Column(name = "conversor_r")
  private Long conversorR;

  private Boolean desactivado;

  @Column(name = "factor_ud_uv")
  private BigDecimal factorUdUv;

  @Column(name = "id_cuenta_contable")
  private Long idCuentaContable;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_ur")
  private Unidad unidadReceta;

  private String nombre;

  private String observaciones;

  private String sku;

  // bi-directional many-to-one association to ComextItemc
  @OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
  private List<ComextItemc> comextItemcs;

	// bi-directional many-to-one association to EstandarPedido
	/*
   * @OneToMany(mappedBy="productosInventario") private List<EstandarPedido>
   * estandarPedidos;
   */
  // bi-directional many-to-one association to MovimientosInventario
  @OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
  private List<MovimientosInventario> movimientosInventarios;

  // bi-directional many-to-one association to CategoriasInventario
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_categoria")
  private CategoriasInventario categoriasInventario;

  // bi-directional many-to-one association to Pais
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_pais")
  private Pais pais;

  // bi-directional many-to-one association to Unidad
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_ud")
  private Unidad unidadDespacho;

  // bi-directional many-to-one association to Unidad
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_uv")
  private Unidad unidadVenta;

  // bi-directional one-to-one association to ProductosInventarioComext
  @OneToOne(mappedBy = "productosInventario", fetch = FetchType.LAZY)
  private ProductosInventarioComext productosInventarioComext;

  // bi-directional many-to-one association to ProductosXClienteComext
  @OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
  private List<ProductosXClienteComext> productosXClienteComexts;

  // bi-directional many-to-one association to ProductosXCliente
  @OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
  private List<ProductosXCliente> productosxclientes;

  // bi-directional many-to-one association to ProductosXDocumento
  @OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
  private List<ProductosXDocumento> productosxdocumentos;

  // bi-directional many-to-one association to Saldo
  @OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
  private List<Saldo> saldos;

  @Column(name = "unidad_min_despacho_x_tendido")
  private BigDecimal unidadMinimaDespachoXTendido;
  
  @Transient
  private boolean incluido;

  public ProductosInventario() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAbc() {
    return this.abc;
  }

  public void setAbc(String abc) {
    this.abc = abc;
  }

  public Long getCodigoBarrasUd() {
    return this.codigoBarrasUd;
  }

  public void setCodigoBarrasUd(Long codigoBarrasUd) {
    this.codigoBarrasUd = codigoBarrasUd;
  }

  public Long getCodigoBarrasUv() {
    return this.codigoBarrasUv;
  }

  public void setCodigoBarrasUv(Long codigoBarrasUv) {
    this.codigoBarrasUv = codigoBarrasUv;
  }

  public String getCodigoPos() {
    return this.codigoPos;
  }

  public void setCodigoPos(String codigoPos) {
    this.codigoPos = codigoPos;
  }

  public Boolean getConsumible() {
    return this.consumible;
  }

  public void setConsumible(Boolean consumible) {
    this.consumible = consumible;
  }

  public Long getConversorR() {
    return this.conversorR;
  }

  public void setConversorR(Long conversorR) {
    this.conversorR = conversorR;
  }

  public Boolean getDesactivado() {
    return this.desactivado;
  }

  public void setDesactivado(Boolean desactivado) {
    this.desactivado = desactivado;
  }

  public BigDecimal getFactorUdUv() {
    return this.factorUdUv;
  }

  public void setFactorUdUv(BigDecimal factorUdUv) {
    this.factorUdUv = factorUdUv;
  }

  public Long getIdCuentaContable() {
    return this.idCuentaContable;
  }

  public void setIdCuentaContable(Long idCuentaContable) {
    this.idCuentaContable = idCuentaContable;
  }

  public Unidad getUnidadReceta() {
    return this.unidadReceta;
  }

  public void setUnidadReceta(Unidad idUr) {
    this.unidadReceta = idUr;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getObservaciones() {
    return this.observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public String getSku() {
    return this.sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public List<ComextItemc> getComextItemcs() {
    return this.comextItemcs;
  }

  public void setComextItemcs(List<ComextItemc> comextItemcs) {
    this.comextItemcs = comextItemcs;
  }

  public ComextItemc addComextItemc(ComextItemc comextItemc) {
    getComextItemcs().add(comextItemc);
    comextItemc.setProductosInventario(this);

    return comextItemc;
  }

  public ComextItemc removeComextItemc(ComextItemc comextItemc) {
    getComextItemcs().remove(comextItemc);
    comextItemc.setProductosInventario(null);

    return comextItemc;
  }

  public List<MovimientosInventario> getMovimientosInventarios() {
    return this.movimientosInventarios;
  }

  public void setMovimientosInventarios(
      List<MovimientosInventario> movimientosInventarios) {
    this.movimientosInventarios = movimientosInventarios;
  }

  public MovimientosInventario addMovimientosInventario(
      MovimientosInventario movimientosInventario) {
    getMovimientosInventarios().add(movimientosInventario);
    movimientosInventario.setProductosInventario(this);

    return movimientosInventario;
  }

  public MovimientosInventario removeMovimientosInventario(
      MovimientosInventario movimientosInventario) {
    getMovimientosInventarios().remove(movimientosInventario);
    movimientosInventario.setProductosInventario(null);

    return movimientosInventario;
  }

  public CategoriasInventario getCategoriasInventario() {
    return this.categoriasInventario;
  }

  public void setCategoriasInventario(
      CategoriasInventario categoriasInventario) {
    this.categoriasInventario = categoriasInventario;
  }

  public Pais getPais() {
    return this.pais;
  }

  public void setPais(Pais pais) {
    this.pais = pais;
  }

  public Unidad getUnidadDespacho() {
    return this.unidadDespacho;
  }

  public void setUnidadDespacho(Unidad unidade1) {
    this.unidadDespacho = unidade1;
  }

  public Unidad getUnidadVenta() {
    return this.unidadVenta;
  }

  public void setUnidadVenta(Unidad unidade2) {
    this.unidadVenta = unidade2;
  }

  public ProductosInventarioComext getProductosInventarioComext() {
    return this.productosInventarioComext;
  }

  public void setProductosInventarioComext(
      ProductosInventarioComext productosInventarioComext) {
    this.productosInventarioComext = productosInventarioComext;
  }

  public List<ProductosXClienteComext> getProductosXClienteComexts() {
    return this.productosXClienteComexts;
  }

  public void setProductosXClienteComexts(
      List<ProductosXClienteComext> productosXClienteComexts) {
    this.productosXClienteComexts = productosXClienteComexts;
  }

  public ProductosXClienteComext addProductosXClienteComext(
      ProductosXClienteComext productosXClienteComext) {
    getProductosXClienteComexts().add(productosXClienteComext);
    productosXClienteComext.setProductosInventario(this);

    return productosXClienteComext;
  }

  public ProductosXClienteComext removeProductosXClienteComext(
      ProductosXClienteComext productosXClienteComext) {
    getProductosXClienteComexts().remove(productosXClienteComext);
    productosXClienteComext.setProductosInventario(null);

    return productosXClienteComext;
  }

  public List<ProductosXCliente> getProductosxclientes() {
    return this.productosxclientes;
  }

  public void setProductosxclientes(List<ProductosXCliente> productosxclientes) {
    this.productosxclientes = productosxclientes;
  }

  public ProductosXCliente addProductosxcliente(
      ProductosXCliente productosxcliente) {
    getProductosxclientes().add(productosxcliente);
    productosxcliente.setProductosInventario(this);

    return productosxcliente;
  }

  public ProductosXCliente removeProductosxcliente(
      ProductosXCliente productosxcliente) {
    getProductosxclientes().remove(productosxcliente);
    productosxcliente.setProductosInventario(null);

    return productosxcliente;
  }

  public List<ProductosXDocumento> getProductosxdocumentos() {
    return this.productosxdocumentos;
  }

  public void setProductosxdocumentos(
      List<ProductosXDocumento> productosxdocumentos) {
    this.productosxdocumentos = productosxdocumentos;
  }

  public ProductosXDocumento addProductosxdocumento(
      ProductosXDocumento productosxdocumento) {
    getProductosxdocumentos().add(productosxdocumento);
    productosxdocumento.setProductosInventario(this);

    return productosxdocumento;
  }

  public ProductosXDocumento removeProductosxdocumento(
      ProductosXDocumento productosxdocumento) {
    getProductosxdocumentos().remove(productosxdocumento);
    productosxdocumento.setProductosInventario(null);

    return productosxdocumento;
  }

  public List<Saldo> getSaldos() {
    return this.saldos;
  }

  public void setSaldos(List<Saldo> saldos) {
    this.saldos = saldos;
  }

  public Saldo addSaldo(Saldo saldo) {
    getSaldos().add(saldo);
    saldo.setProductosInventario(this);

    return saldo;
  }

  public Saldo removeSaldo(Saldo saldo) {
    getSaldos().remove(saldo);
    saldo.setProductosInventario(null);

    return saldo;
  }

  /**
   * @return the unidadMinimaDespachoXTendido
   */
  public BigDecimal getUnidadMinimaDespachoXTendido() {
    return unidadMinimaDespachoXTendido;
  }

  /**
   * @param unidadMinimaDespachoXTendido the unidadMinimaDespachoXTendido to set
   */
  public void setUnidadMinimaDespachoXTendido(BigDecimal unidadMinimaDespachoXTendido) {
    this.unidadMinimaDespachoXTendido = unidadMinimaDespachoXTendido;
  }

  public boolean isIncluido() {
    return incluido;
  }

  public void setIncluido(boolean incluido) {
    this.incluido = incluido;
  }

  /*
   * public List<TempCosto> getTempCostos() { return this.tempCostos; }
   * 
   * public void setTempCostos(List<TempCosto> tempCostos) { this.tempCostos =
   * tempCostos; }
   * 
   * public TempCosto addTempCosto(TempCosto tempCosto) {
   * getTempCostos().add(tempCosto); tempCosto.setProductosInventario(this);
   * 
   * return tempCosto; }
   * 
   * public TempCosto removeTempCosto(TempCosto tempCosto) {
   * getTempCostos().remove(tempCosto);
   * tempCosto.setProductosInventario(null);
   * 
   * return tempCosto; }
   */
}
