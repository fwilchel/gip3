package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the movimientos_inventario database table.
 * 
 */
@Entity
@Table(name="movimientos_inventario_comext")
@NamedQueries({
	@NamedQuery(name="MovimientosInventarioComext.findAll", query="SELECT m FROM MovimientosInventarioComext m"),
	@NamedQuery(name="MovimientosInventarioComext.ultimosSaldos", query="SELECT m FROM MovimientosInventarioComext m WHERE m.fecha = (SELECT max(m2.fecha) FROM MovimientosInventarioComext m2 WHERE m2.productosInventarioComext.idProducto=m.productosInventarioComext.idProducto) "),
})

public class MovimientosInventarioComext implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Column(name="consecutivo_documento")
	private String consecutivoDocumento;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_movimiento")
	private TipoMovimiento tipoMovimiento;
	
	@ManyToOne
	@JoinColumn(name="id_producto")
	private ProductosInventarioComext productosInventarioComext;

	private BigDecimal cantidad;
	
	private Timestamp fecha;
	
	private BigDecimal saldo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public ProductosInventarioComext getProductosInventarioComext() {
		return productosInventarioComext;
	}

	public void setProductosInventarioComext(
			ProductosInventarioComext productosInventarioComext) {
		this.productosInventarioComext = productosInventarioComext;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
}