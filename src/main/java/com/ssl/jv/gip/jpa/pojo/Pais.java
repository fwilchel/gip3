package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the paises database table.
 *
 */
@Entity
@Table(name = "paises")
@NamedQueries({
  @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p"),
  @NamedQuery(name = "Pais.findByRegional", query = "SELECT distinct(p) FROM Pais p inner join p.regiones r")
})
public class Pais implements Serializable, Comparable<Object> {

  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  private String nombre;

  //bi-directional many-to-one association to Moneda
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_ml")
  private Moneda moneda;

  //bi-directional many-to-one association to ProductosInventario
  @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
  private List<ProductosInventario> productosInventarios;

  //bi-directional many-to-one association to Region
  @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
  private List<Region> regiones;

  //bi-directional many-to-one association to TipoConteo
  @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
  private List<TipoConteo> tipoConteos;

  //bi-directional many-to-one association to Usuario
  @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
  private List<Usuario> usuarios;

  public Pais() {
  }

  public Pais(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Moneda getMoneda() {
    return this.moneda;
  }

  public void setMoneda(Moneda moneda) {
    this.moneda = moneda;
  }

  public List<ProductosInventario> getProductosInventarios() {
    return this.productosInventarios;
  }

  public void setProductosInventarios(List<ProductosInventario> productosInventarios) {
    this.productosInventarios = productosInventarios;
  }

  public ProductosInventario addProductosInventario(ProductosInventario productosInventario) {
    getProductosInventarios().add(productosInventario);
    productosInventario.setPais(this);

    return productosInventario;
  }

  public ProductosInventario removeProductosInventario(ProductosInventario productosInventario) {
    getProductosInventarios().remove(productosInventario);
    productosInventario.setPais(null);

    return productosInventario;
  }

  public List<Region> getRegiones() {
    return this.regiones;
  }

  public void setRegiones(List<Region> regiones) {
    this.regiones = regiones;
  }

  public Region addRegione(Region regione) {
    getRegiones().add(regione);
    regione.setPais(this);

    return regione;
  }

  public Region removeRegione(Region regione) {
    getRegiones().remove(regione);
    regione.setPais(null);

    return regione;
  }

  public List<TipoConteo> getTipoConteos() {
    return this.tipoConteos;
  }

  public void setTipoConteos(List<TipoConteo> tipoConteos) {
    this.tipoConteos = tipoConteos;
  }

  public TipoConteo addTipoConteo(TipoConteo tipoConteo) {
    getTipoConteos().add(tipoConteo);
    tipoConteo.setPais(this);

    return tipoConteo;
  }

  public TipoConteo removeTipoConteo(TipoConteo tipoConteo) {
    getTipoConteos().remove(tipoConteo);
    tipoConteo.setPais(null);

    return tipoConteo;
  }

  public List<Usuario> getUsuarios() {
    return this.usuarios;
  }

  public void setUsuarios(List<Usuario> usuarios) {
    this.usuarios = usuarios;
  }

  public Usuario addUsuario(Usuario usuario) {
    getUsuarios().add(usuario);
    usuario.setPais(this);

    return usuario;
  }

  public Usuario removeUsuario(Usuario usuario) {
    getUsuarios().remove(usuario);
    usuario.setPais(null);

    return usuario;
  }

  @Override
  public int compareTo(Object o) {
    if (o instanceof Pais) {
      Pais p = (Pais) o;
      return this.nombre.compareTo(p.getNombre());
    }
    return 0;
  }

}
