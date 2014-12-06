package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the productosxproveedor database table.
 * 
 */
@Entity
@NamedQuery(name="ProductosXProveedor.findAll", query="SELECT p FROM ProductosXProveedor p")
public class ProductosXProveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="codigo_barras_uc")
	private Long codigoBarrasUc;

	@Column(name="factor_uc_ud")
	private BigDecimal factorUcUd;

	@Column(name="factor_uc_uv")
	private BigDecimal factorUcUv;

	@Column(name="fecha_final_vigencia")
	private Timestamp fechaFinalVigencia;

	@Column(name="fecha_inicial_vigencia")
	private Timestamp fechaInicialVigencia;

	@Column(name="nivel_tolerancia")
	private BigDecimal nivelTolerancia;

	@Column(name="precio_ml")
	private BigDecimal precioMl;

	@Column(name="precio_usd")
	private BigDecimal precioUsd;

	@Column(name="tiempo_entrega")
	private BigDecimal tiempoEntrega;

	private Boolean vigente;

	//bi-directional many-to-one association to Moneda
	@ManyToOne
	@JoinColumn(name="id_ml")
	private Moneda moneda;

	//bi-directional many-to-one association to ProductosInventario
	@ManyToOne
	@JoinColumn(name="id_producto")
	private ProductosInventario productosInventario;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor")
	private Proveedor proveedore;

	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="id_uc")
	private Unidad unidade;

	public ProductosXProveedor() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigoBarrasUc() {
		return this.codigoBarrasUc;
	}

	public void setCodigoBarrasUc(Long codigoBarrasUc) {
		this.codigoBarrasUc = codigoBarrasUc;
	}

	public BigDecimal getFactorUcUd() {
		return this.factorUcUd;
	}

	public void setFactorUcUd(BigDecimal factorUcUd) {
		this.factorUcUd = factorUcUd;
	}

	public BigDecimal getFactorUcUv() {
		return this.factorUcUv;
	}

	public void setFactorUcUv(BigDecimal factorUcUv) {
		this.factorUcUv = factorUcUv;
	}

	public Timestamp getFechaFinalVigencia() {
		return this.fechaFinalVigencia;
	}

	public void setFechaFinalVigencia(Timestamp fechaFinalVigencia) {
		this.fechaFinalVigencia = fechaFinalVigencia;
	}

	public Timestamp getFechaInicialVigencia() {
		return this.fechaInicialVigencia;
	}

	public void setFechaInicialVigencia(Timestamp fechaInicialVigencia) {
		this.fechaInicialVigencia = fechaInicialVigencia;
	}

	public BigDecimal getNivelTolerancia() {
		return this.nivelTolerancia;
	}

	public void setNivelTolerancia(BigDecimal nivelTolerancia) {
		this.nivelTolerancia = nivelTolerancia;
	}

	public BigDecimal getPrecioMl() {
		return this.precioMl;
	}

	public void setPrecioMl(BigDecimal precioMl) {
		this.precioMl = precioMl;
	}

	public BigDecimal getPrecioUsd() {
		return this.precioUsd;
	}

	public void setPrecioUsd(BigDecimal precioUsd) {
		this.precioUsd = precioUsd;
	}

	public BigDecimal getTiempoEntrega() {
		return this.tiempoEntrega;
	}

	public void setTiempoEntrega(BigDecimal tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}

	public Boolean getVigente() {
		return this.vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public ProductosInventario getProductosInventario() {
		return this.productosInventario;
	}

	public void setProductosInventario(ProductosInventario productosInventario) {
		this.productosInventario = productosInventario;
	}

	public Proveedor getProveedore() {
		return this.proveedore;
	}

	public void setProveedore(Proveedor proveedore) {
		this.proveedore = proveedore;
	}

	public Unidad getUnidade() {
		return this.unidade;
	}

	public void setUnidade(Unidad unidade) {
		this.unidade = unidade;
	}

}