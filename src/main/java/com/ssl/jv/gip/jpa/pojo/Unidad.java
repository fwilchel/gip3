package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the unidades database table.
 *
 */
@Entity
@Table(name = "unidades")
@NamedQuery(name = "Unidad.findAll", query = "SELECT u FROM Unidad u")
public class Unidad implements Serializable, Comparable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private String abreviacion;

  private String nombre;

  @Column(name = "nombre_ingles")
  private String nombreIngles;

  //bi-directional many-to-one association to Conteo
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<Conteo> conteos;

  //bi-directional many-to-one association to CostoVenta
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<CostoVenta> costoVentas;

  //bi-directional many-to-one association to Costo
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<Costo> costos;

  //bi-directional many-to-one association to MovimientosInventario
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<MovimientosInventario> movimientosInventarios;

  //bi-directional many-to-one association to ProductosInventario
  //@OneToMany(mappedBy="unidade1")
  //private List<ProductosInventario> productosInventarios1;
  //bi-directional many-to-one association to ProductosInventario
  //@OneToMany(mappedBy="unidade2")
  //private List<ProductosInventario> productosInventarios2;
  //bi-directional many-to-one association to ProductosInventarioComext
  @OneToMany(mappedBy = "unidadEmbalaje", fetch = FetchType.LAZY)
  private List<ProductosInventarioComext> productosInventarioComexts;

  //bi-directional many-to-one association to ProductosXDocumento
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<ProductosXDocumento> productosxdocumentos;

  //bi-directional many-to-one association to ProductosXProveedor
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<ProductosXProveedor> productosxproveedors;

  //bi-directional many-to-one association to ProductosXReceta
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<ProductosXReceta> productosxrecetas;

  //bi-directional many-to-one association to Saldo
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<Saldo> saldos;

  //bi-directional many-to-one association to SaldosFranquicia
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<SaldosFranquicia> saldosFranquicias;

	//bi-directional many-to-one association to TempCosto
	/*@OneToMany(mappedBy="unidade")
   private List<TempCosto> tempCostos;*/
  //bi-directional many-to-one association to Venta
  @OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY)
  private List<Venta> ventas;

  public Unidad() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAbreviacion() {
    return this.abreviacion;
  }

  public void setAbreviacion(String abreviacion) {
    this.abreviacion = abreviacion;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombreIngles() {
    return this.nombreIngles;
  }

  public void setNombreIngles(String nombreIngles) {
    this.nombreIngles = nombreIngles;
  }

  public List<Conteo> getConteos() {
    return this.conteos;
  }

  public void setConteos(List<Conteo> conteos) {
    this.conteos = conteos;
  }

  public Conteo addConteo(Conteo conteo) {
    getConteos().add(conteo);
    conteo.setUnidade(this);

    return conteo;
  }

  public Conteo removeConteo(Conteo conteo) {
    getConteos().remove(conteo);
    conteo.setUnidade(null);

    return conteo;
  }

  public List<CostoVenta> getCostoVentas() {
    return this.costoVentas;
  }

  public void setCostoVentas(List<CostoVenta> costoVentas) {
    this.costoVentas = costoVentas;
  }

  public CostoVenta addCostoVenta(CostoVenta costoVenta) {
    getCostoVentas().add(costoVenta);
    costoVenta.setUnidade(this);

    return costoVenta;
  }

  public CostoVenta removeCostoVenta(CostoVenta costoVenta) {
    getCostoVentas().remove(costoVenta);
    costoVenta.setUnidade(null);

    return costoVenta;
  }

  public List<Costo> getCostos() {
    return this.costos;
  }

  public void setCostos(List<Costo> costos) {
    this.costos = costos;
  }

  public Costo addCosto(Costo costo) {
    getCostos().add(costo);
    costo.setUnidade(this);

    return costo;
  }

  public Costo removeCosto(Costo costo) {
    getCostos().remove(costo);
    costo.setUnidade(null);

    return costo;
  }

  public List<MovimientosInventario> getMovimientosInventarios() {
    return this.movimientosInventarios;
  }

  public void setMovimientosInventarios(List<MovimientosInventario> movimientosInventarios) {
    this.movimientosInventarios = movimientosInventarios;
  }

  public MovimientosInventario addMovimientosInventario(MovimientosInventario movimientosInventario) {
    getMovimientosInventarios().add(movimientosInventario);
    movimientosInventario.setUnidade(this);

    return movimientosInventario;
  }

  public MovimientosInventario removeMovimientosInventario(MovimientosInventario movimientosInventario) {
    getMovimientosInventarios().remove(movimientosInventario);
    movimientosInventario.setUnidade(null);

    return movimientosInventario;
  }

  /*public List<ProductosInventario> getProductosInventarios1() {
   return this.productosInventarios1;
   }

   public void setProductosInventarios1(List<ProductosInventario> productosInventarios1) {
   this.productosInventarios1 = productosInventarios1;
   }

   public ProductosInventario addProductosInventarios1(ProductosInventario productosInventarios1) {
   getProductosInventarios1().add(productosInventarios1);
   productosInventarios1.setUnidade1(this);

   return productosInventarios1;
   }

   public ProductosInventario removeProductosInventarios1(ProductosInventario productosInventarios1) {
   getProductosInventarios1().remove(productosInventarios1);
   productosInventarios1.setUnidade1(null);

   return productosInventarios1;
   }

   public List<ProductosInventario> getProductosInventarios2() {
   return this.productosInventarios2;
   }

   public void setProductosInventarios2(List<ProductosInventario> productosInventarios2) {
   this.productosInventarios2 = productosInventarios2;
   }

   public ProductosInventario addProductosInventarios2(ProductosInventario productosInventarios2) {
   getProductosInventarios2().add(productosInventarios2);
   productosInventarios2.setUnidade2(this);

   return productosInventarios2;
   }

   public ProductosInventario removeProductosInventarios2(ProductosInventario productosInventarios2) {
   getProductosInventarios2().remove(productosInventarios2);
   productosInventarios2.setUnidade2(null);

   return productosInventarios2;
   }*/
  public List<ProductosInventarioComext> getProductosInventarioComexts() {
    return this.productosInventarioComexts;
  }

  public void setProductosInventarioComexts(List<ProductosInventarioComext> productosInventarioComexts) {
    this.productosInventarioComexts = productosInventarioComexts;
  }

  public ProductosInventarioComext addProductosInventarioComext(ProductosInventarioComext productosInventarioComext) {
    getProductosInventarioComexts().add(productosInventarioComext);
    productosInventarioComext.setUnidadEmbalaje(this);

    return productosInventarioComext;
  }

  public ProductosInventarioComext removeProductosInventarioComext(ProductosInventarioComext productosInventarioComext) {
    getProductosInventarioComexts().remove(productosInventarioComext);
    productosInventarioComext.setUnidadEmbalaje(null);

    return productosInventarioComext;
  }

  public List<ProductosXDocumento> getProductosxdocumentos() {
    return this.productosxdocumentos;
  }

  public void setProductosxdocumentos(List<ProductosXDocumento> productosxdocumentos) {
    this.productosxdocumentos = productosxdocumentos;
  }

  public ProductosXDocumento addProductosxdocumento(ProductosXDocumento productosxdocumento) {
    getProductosxdocumentos().add(productosxdocumento);
    productosxdocumento.setUnidade(this);

    return productosxdocumento;
  }

  public ProductosXDocumento removeProductosxdocumento(ProductosXDocumento productosxdocumento) {
    getProductosxdocumentos().remove(productosxdocumento);
    productosxdocumento.setUnidade(null);

    return productosxdocumento;
  }

  public List<ProductosXProveedor> getProductosxproveedors() {
    return this.productosxproveedors;
  }

  public void setProductosxproveedors(List<ProductosXProveedor> productosxproveedors) {
    this.productosxproveedors = productosxproveedors;
  }

  public ProductosXProveedor addProductosxproveedor(ProductosXProveedor productosxproveedor) {
    getProductosxproveedors().add(productosxproveedor);
    productosxproveedor.setUnidade(this);

    return productosxproveedor;
  }

  public ProductosXProveedor removeProductosxproveedor(ProductosXProveedor productosxproveedor) {
    getProductosxproveedors().remove(productosxproveedor);
    productosxproveedor.setUnidade(null);

    return productosxproveedor;
  }

  public List<ProductosXReceta> getProductosxrecetas() {
    return this.productosxrecetas;
  }

  public void setProductosxrecetas(List<ProductosXReceta> productosxrecetas) {
    this.productosxrecetas = productosxrecetas;
  }

  public ProductosXReceta addProductosxreceta(ProductosXReceta productosxreceta) {
    getProductosxrecetas().add(productosxreceta);
    productosxreceta.setUnidade(this);

    return productosxreceta;
  }

  public ProductosXReceta removeProductosxreceta(ProductosXReceta productosxreceta) {
    getProductosxrecetas().remove(productosxreceta);
    productosxreceta.setUnidade(null);

    return productosxreceta;
  }

  public List<Saldo> getSaldos() {
    return this.saldos;
  }

  public void setSaldos(List<Saldo> saldos) {
    this.saldos = saldos;
  }

  public Saldo addSaldo(Saldo saldo) {
    getSaldos().add(saldo);
    saldo.setUnidade(this);

    return saldo;
  }

  public Saldo removeSaldo(Saldo saldo) {
    getSaldos().remove(saldo);
    saldo.setUnidade(null);

    return saldo;
  }

  public List<SaldosFranquicia> getSaldosFranquicias() {
    return this.saldosFranquicias;
  }

  public void setSaldosFranquicias(List<SaldosFranquicia> saldosFranquicias) {
    this.saldosFranquicias = saldosFranquicias;
  }

  public SaldosFranquicia addSaldosFranquicia(SaldosFranquicia saldosFranquicia) {
    getSaldosFranquicias().add(saldosFranquicia);
    saldosFranquicia.setUnidade(this);

    return saldosFranquicia;
  }

  public SaldosFranquicia removeSaldosFranquicia(SaldosFranquicia saldosFranquicia) {
    getSaldosFranquicias().remove(saldosFranquicia);
    saldosFranquicia.setUnidade(null);

    return saldosFranquicia;
  }

  /*public List<TempCosto> getTempCostos() {
   return this.tempCostos;
   }

   public void setTempCostos(List<TempCosto> tempCostos) {
   this.tempCostos = tempCostos;
   }

   public TempCosto addTempCosto(TempCosto tempCosto) {
   getTempCostos().add(tempCosto);
   tempCosto.setUnidade(this);

   return tempCosto;
   }

   public TempCosto removeTempCosto(TempCosto tempCosto) {
   getTempCostos().remove(tempCosto);
   tempCosto.setUnidade(null);

   return tempCosto;
   }*/
  public List<Venta> getVentas() {
    return this.ventas;
  }

  public void setVentas(List<Venta> ventas) {
    this.ventas = ventas;
  }

  public Venta addVenta(Venta venta) {
    getVentas().add(venta);
    venta.setUnidade(this);

    return venta;
  }

  public Venta removeVenta(Venta venta) {
    getVentas().remove(venta);
    venta.setUnidade(null);

    return venta;
  }

  @Override
  public int compareTo(Object o) {
    Unidad u = (Unidad) o;
    return this.getNombre().compareTo(u.getNombre());
  }

}
