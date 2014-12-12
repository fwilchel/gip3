package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the nivel_inventarioxubicacion database table.
 * 
 */
@Entity
@Table(name="nivel_inventarioxubicacion")
@NamedQuery(name="NivelInventarioxubicacion.findAll", query="SELECT n FROM NivelInventarioxubicacion n")
public class NivelInventarioxubicacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NivelInventarioxubicacionPK id;

	private BigDecimal cantidad;

	private BigDecimal saldo;

	private BigDecimal transito;

	@Column(name="unidad_minima_despacho")
	private Long unidadMinimaDespacho;

	//bi-directional many-to-one association to ProductosInventario
	@ManyToOne(optional=false)
	@JoinColumn(name="id_producto", referencedColumnName="id", insertable=false, updatable=false)
	private ProductosInventario productosInventario;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne(optional=false)
	@JoinColumn(name="id_ubicacion", referencedColumnName="id", insertable=false, updatable=false)
	private Ubicacion ubicacione;

	public NivelInventarioxubicacion() {
	}

	public NivelInventarioxubicacionPK getId() {
		return this.id;
	}

	public void setId(NivelInventarioxubicacionPK id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getTransito() {
		return this.transito;
	}

	public void setTransito(BigDecimal transito) {
		this.transito = transito;
	}

	public Long getUnidadMinimaDespacho() {
		return this.unidadMinimaDespacho;
	}

	public void setUnidadMinimaDespacho(Long unidadMinimaDespacho) {
		this.unidadMinimaDespacho = unidadMinimaDespacho;
	}

	public ProductosInventario getProductosInventario() {
		return this.productosInventario;
	}

	public void setProductosInventario(ProductosInventario productosInventario) {
		this.productosInventario = productosInventario;
	}

	public Ubicacion getUbicacione() {
		return this.ubicacione;
	}

	public void setUbicacione(Ubicacion ubicacione) {
		this.ubicacione = ubicacione;
	}

}