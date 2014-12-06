package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the saldos database table.
 * 
 */
@Entity
@Table(name="saldos")
@NamedQuery(name="Saldo.findAll", query="SELECT s FROM Saldo s")
public class Saldo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SaldoPK id;

	private BigDecimal saldo;

	//bi-directional many-to-one association to BodegasLogica
	@ManyToOne(optional=false)
	@JoinColumn(name="id_bodega_logica", referencedColumnName="id", insertable=false, updatable=false)
	private BodegasLogica bodegasLogica;

	//bi-directional many-to-one association to ProductosInventario
	@ManyToOne(optional=false)
	@JoinColumn(name="id_producto", referencedColumnName="id", insertable=false, updatable=false)
	private ProductosInventario productosInventario;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne(optional=false)
	@JoinColumn(name="id_ubicacion", referencedColumnName="id", insertable=false, updatable=false)
	private Ubicacion ubicacione;

	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="id_unidad")
	private Unidad unidade;

	public Saldo() {
	}

	public SaldoPK getId() {
		return this.id;
	}

	public void setId(SaldoPK id) {
		this.id = id;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BodegasLogica getBodegasLogica() {
		return this.bodegasLogica;
	}

	public void setBodegasLogica(BodegasLogica bodegasLogica) {
		this.bodegasLogica = bodegasLogica;
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

	public Unidad getUnidade() {
		return this.unidade;
	}

	public void setUnidade(Unidad unidade) {
		this.unidade = unidade;
	}

}