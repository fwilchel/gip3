package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the nivel_inventarioxubicacion_temp database table.
 * 
 */
@Entity
@Table(name="nivel_inventarioxubicacion_temp")
@NamedQuery(name="NivelInventarioxubicacionTemp.findAll", query="SELECT n FROM NivelInventarioxubicacionTemp n")
public class NivelInventarioxubicacionTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NivelInventarioxubicacionTempPK id;

	private BigDecimal cantidad;

	//bi-directional many-to-one association to ProductosInventario
	@ManyToOne(optional=false)
	@JoinColumn(name="id_producto", referencedColumnName="id", insertable=false, updatable=false)
	private ProductosInventario productosInventario;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name="id_ubicacion", referencedColumnName="id", insertable=false, updatable=false)
	private Ubicacion ubicacione;

	public NivelInventarioxubicacionTemp() {
	}

	public NivelInventarioxubicacionTempPK getId() {
		return this.id;
	}

	public void setId(NivelInventarioxubicacionTempPK id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
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