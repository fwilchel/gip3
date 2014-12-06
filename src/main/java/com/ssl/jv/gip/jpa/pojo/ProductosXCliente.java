package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the productosxcliente database table.
 * 
 */
@Entity
@NamedQuery(name="ProductosXCliente.findAll", query="SELECT p FROM ProductosXCliente p")
public class ProductosXCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductosXClientePK pk;

	private Boolean activo;

	private BigDecimal descuentoxproducto;

	@Column(name="fecha_final_vigencia")
	private Timestamp fechaFinalVigencia;

	@Column(name="fecha_inicial_vigencia")
	private Timestamp fechaInicialVigencia;

	private Long id;

	private BigDecimal iva;

	@Column(name="otros_descuentos")
	private BigDecimal otrosDescuentos;

	@Column(name="precio_ml")
	private BigDecimal precioMl;

	@Column(name="precio_usd")
	private BigDecimal precioUsd;

	private Boolean vigente;

	//bi-directional many-to-one association to Cliente
	@ManyToOne(optional=false)
	@JoinColumn(name="id_cliente", referencedColumnName="id", insertable=false, updatable=false)
	private Cliente cliente;

	//bi-directional many-to-one association to Moneda
	@ManyToOne
	@JoinColumn(name="id_ml")
	private Moneda moneda;

	//bi-directional many-to-one association to ProductosInventario
	@ManyToOne(optional=false)
	@JoinColumn(name="id_producto", referencedColumnName="id", insertable=false, updatable=false)
	private ProductosInventario productosInventario;

	public ProductosXCliente() {
	}

	public ProductosXClientePK getPk() {
		return this.pk;
	}

	public void setPk(ProductosXClientePK pk) {
		this.pk = pk;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public BigDecimal getDescuentoxproducto() {
		return this.descuentoxproducto;
	}

	public void setDescuentoxproducto(BigDecimal descuentoxproducto) {
		this.descuentoxproducto = descuentoxproducto;
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

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getIva() {
		return this.iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getOtrosDescuentos() {
		return this.otrosDescuentos;
	}

	public void setOtrosDescuentos(BigDecimal otrosDescuentos) {
		this.otrosDescuentos = otrosDescuentos;
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

	public Boolean getVigente() {
		return this.vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

}