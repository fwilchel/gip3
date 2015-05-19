package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the empresa database table.
 *
 */
@Entity
@NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")
public class Empresa implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private String nombre;

  //bi-directional many-to-one association to Moneda
  @ManyToOne
  @JoinColumn(name = "id_ml")
  private Moneda moneda;

	//bi-directional many-to-one association to TempCosto
	/*@OneToMany(mappedBy="empresa")
   private List<TempCosto> tempCostos;*/
  //bi-directional many-to-one association to Ubicacion
  @OneToMany(mappedBy = "empresa")
  private List<Ubicacion> ubicaciones;

  public Empresa() {
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

  public Moneda getMoneda() {
    return this.moneda;
  }

  public void setMoneda(Moneda moneda) {
    this.moneda = moneda;
  }

  public List<Ubicacion> getUbicaciones() {
    return this.ubicaciones;
  }

  public void setUbicaciones(List<Ubicacion> ubicaciones) {
    this.ubicaciones = ubicaciones;
  }

  public Ubicacion addUbicacione(Ubicacion ubicacione) {
    getUbicaciones().add(ubicacione);
    ubicacione.setEmpresa(this);

    return ubicacione;
  }

  public Ubicacion removeUbicacione(Ubicacion ubicacione) {
    getUbicaciones().remove(ubicacione);
    ubicacione.setEmpresa(null);

    return ubicacione;
  }

}
