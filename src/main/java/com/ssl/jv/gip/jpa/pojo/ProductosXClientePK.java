package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the productosxcliente database table.
 * 
 */
@Embeddable
public class ProductosXClientePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_producto")
	private Long idProducto;

	@Column(name="id_cliente")
	private Long idCliente;

	@Column(name="id_punto_venta")
	private Long idPuntoVenta;

	public ProductosXClientePK() {
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
	public Long getIdPuntoVenta() {
		return this.idPuntoVenta;
	}
	public void setIdPuntoVenta(Long idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductosXClientePK)) {
			return false;
		}
		ProductosXClientePK castOther = (ProductosXClientePK)other;
		return 
			this.idProducto.equals(castOther.idProducto)
			&& this.idCliente.equals(castOther.idCliente)
			&& this.idPuntoVenta.equals(castOther.idPuntoVenta);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idProducto.hashCode();
		hash = hash * prime + this.idCliente.hashCode();
		hash = hash * prime + this.idPuntoVenta.hashCode();
		
		return hash;
	}
}