package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the productosxrecetas database table.
 *
 */
@Embeddable
public class ProductosXRecetaPK implements Serializable {

  //default serial version id, required for serializable classes.
  private static final long serialVersionUID = 1L;

  @Column
  private Long micros;

  @Column(name = "id_material")
  private Long idMaterial;

  @Column(name = "id_unidad")
  private Long idUnidad;

  public ProductosXRecetaPK() {
  }

  public Long getMicros() {
    return this.micros;
  }

  public void setMicros(Long micros) {
    this.micros = micros;
  }

  public Long getIdMaterial() {
    return this.idMaterial;
  }

  public void setIdMaterial(Long idMaterial) {
    this.idMaterial = idMaterial;
  }

  public Long getIdUnidad() {
    return this.idUnidad;
  }

  public void setIdUnidad(Long idUnidad) {
    this.idUnidad = idUnidad;
  }

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof ProductosXRecetaPK)) {
      return false;
    }
    ProductosXRecetaPK castOther = (ProductosXRecetaPK) other;
    return this.micros.equals(castOther.micros)
        && this.idMaterial.equals(castOther.idMaterial)
        && this.idUnidad.equals(castOther.idUnidad);
  }

  public int hashCode() {
    final int prime = 31;
    int hash = 17;
    hash = hash * prime + this.micros.hashCode();
    hash = hash * prime + this.idMaterial.hashCode();
    hash = hash * prime + this.idUnidad.hashCode();

    return hash;
  }
}
