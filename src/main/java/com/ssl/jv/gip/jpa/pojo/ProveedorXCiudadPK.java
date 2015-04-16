package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the proveedorxciudad database table.
 *
 */
@Embeddable
public class ProveedorXCiudadPK implements Serializable {

  //default serial version id, required for serializable classes.
  private static final long serialVersionUID = 1L;

  @Column(name = "id_proveedor")
  private String idProveedor;

  @Column(name = "id_ciudad")
  private String idCiudad;

  public ProveedorXCiudadPK() {
  }

  public String getIdProveedor() {
    return this.idProveedor;
  }

  public void setIdProveedor(String idProveedor) {
    this.idProveedor = idProveedor;
  }

  public String getIdCiudad() {
    return this.idCiudad;
  }

  public void setIdCiudad(String idCiudad) {
    this.idCiudad = idCiudad;
  }

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof ProveedorXCiudadPK)) {
      return false;
    }
    ProveedorXCiudadPK castOther = (ProveedorXCiudadPK) other;
    return this.idProveedor.equals(castOther.idProveedor)
        && this.idCiudad.equals(castOther.idCiudad);
  }

  public int hashCode() {
    final int prime = 31;
    int hash = 17;
    hash = hash * prime + this.idProveedor.hashCode();
    hash = hash * prime + this.idCiudad.hashCode();

    return hash;
  }
}
