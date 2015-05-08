package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the productos_x_cliente_comext database table.
 *
 */
@Embeddable
public class ProductosXClienteComextPK implements Serializable {

  // default serial version id, required for serializable classes.
  private static final long serialVersionUID = 1L;

  @Column(name = "id_producto")
  private Long idProducto;

  @Column(name = "id_cliente")
  private Long idCliente;

  public ProductosXClienteComextPK() {
  }

  public ProductosXClienteComextPK(Long idProducto, Long idCliente) {
    this.idProducto = idProducto;
    this.idCliente = idCliente;
  }

  public Long getIdProducto() {
    return this.idProducto;
  }

  public void setIdProducto(Long idProducto) {
    this.idProducto = idProducto;
  }

  public Long getIdCliente() {
    return this.idCliente;
  }

  public void setIdCliente(Long idCliente) {
    this.idCliente = idCliente;
  }

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof ProductosXClienteComextPK)) {
      return false;
    }
    ProductosXClienteComextPK castOther = (ProductosXClienteComextPK) other;
    return this.idProducto.equals(castOther.idProducto)
        && this.idCliente.equals(castOther.idCliente);
  }

  public int hashCode() {
    final int prime = 31;
    int hash = 17;
    hash = hash * prime + this.idProducto.hashCode();
    hash = hash * prime + this.idCliente.hashCode();

    return hash;
  }
}
