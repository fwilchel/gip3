package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ventas database table.
 * 
 */
@Entity
@Table(name="ventas")
@NamedQuery(name="Venta.findAll", query="SELECT v FROM Venta v")
public class Venta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VentaPK id;

	private double cantidad;

	@Column(name="fecha_venta")
	private Boolean fechaVenta;

	@Column(name="precio_unitario_ml")
	private BigDecimal precioUnitarioMl;

	@Column(name="precio_unitario_usd")
	private BigDecimal precioUnitarioUsd;

	//bi-directional many-to-one association to Moneda
	@ManyToOne
	@JoinColumn(name="id_ml")
	private Moneda moneda;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne(optional=false)
	@JoinColumn(name="id_ubicacion", referencedColumnName="id", insertable=false, updatable=false)
	private Ubicacion ubicacione;

	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="id_unidad")
	private Unidad unidade;

	public Venta() {
	}

	public VentaPK getId() {
		return this.id;
	}

	public void setId(VentaPK id) {
		this.id = id;
	}

	public double getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public Boolean getFechaVenta() {
		return this.fechaVenta;
	}

	public void setFechaVenta(Boolean fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public BigDecimal getPrecioUnitarioMl() {
		return this.precioUnitarioMl;
	}

	public void setPrecioUnitarioMl(BigDecimal precioUnitarioMl) {
		this.precioUnitarioMl = precioUnitarioMl;
	}

	public BigDecimal getPrecioUnitarioUsd() {
		return this.precioUnitarioUsd;
	}

	public void setPrecioUnitarioUsd(BigDecimal precioUnitarioUsd) {
		this.precioUnitarioUsd = precioUnitarioUsd;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Ubicacion getUbicacione() {
		return this.ubicacione;
	}

	public void setUbicacione(Ubicacion ubicacione) {
		this.ubicacione = ubicacione;
	}

	public Unidad getUnidade() {
		return this.unidade;
	}

	public void setUnidade(Unidad unidade) {
		this.unidade = unidade;
	}

}