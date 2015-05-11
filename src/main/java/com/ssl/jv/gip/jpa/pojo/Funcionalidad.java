package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the funcionalidades database table.
 *
 */
@Entity
@Table(name = "funcionalidades")
@NamedQuery(name = "Funcionalidad.findAll", query = "SELECT f FROM Funcionalidad f")
public class Funcionalidad implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private String descripcion;

  private String nombre;

  private Integer ordenar;

  private String ruta;

  //bi-directional many-to-one association to Funcionalidad
  @ManyToOne
  @JoinColumn(name = "id_funcionalidad_padre")
  private Funcionalidad funcionalidade;

  //bi-directional many-to-one association to Funcionalidad
  @OneToMany(mappedBy = "funcionalidade")
  private List<Funcionalidad> funcionalidades;

  //bi-directional many-to-one association to Permiso
  @OneToMany(mappedBy = "funcionalidade")
  private List<Permiso> permisos;

  public Funcionalidad() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Integer getOrdenar() {
    return this.ordenar;
  }

  public void setOrdenar(Integer ordenar) {
    this.ordenar = ordenar;
  }

  public String getRuta() {
    return this.ruta;
  }

  public void setRuta(String ruta) {
    this.ruta = ruta;
  }

  public Funcionalidad getFuncionalidade() {
    return this.funcionalidade;
  }

  public void setFuncionalidade(Funcionalidad funcionalidade) {
    this.funcionalidade = funcionalidade;
  }

  public List<Funcionalidad> getFuncionalidades() {
    return this.funcionalidades;
  }

  public void setFuncionalidades(List<Funcionalidad> funcionalidades) {
    this.funcionalidades = funcionalidades;
  }

  public Funcionalidad addFuncionalidade(Funcionalidad funcionalidade) {
    getFuncionalidades().add(funcionalidade);
    funcionalidade.setFuncionalidade(this);

    return funcionalidade;
  }

  public Funcionalidad removeFuncionalidade(Funcionalidad funcionalidade) {
    getFuncionalidades().remove(funcionalidade);
    funcionalidade.setFuncionalidade(null);

    return funcionalidade;
  }

  public List<Permiso> getPermisos() {
    return this.permisos;
  }

  public void setPermisos(List<Permiso> permisos) {
    this.permisos = permisos;
  }

  public Permiso addPermiso(Permiso permiso) {
    getPermisos().add(permiso);
    permiso.setFuncionalidade(this);

    return permiso;
  }

  public Permiso removePermiso(Permiso permiso) {
    getPermisos().remove(permiso);
    permiso.setFuncionalidade(null);

    return permiso;
  }

}
