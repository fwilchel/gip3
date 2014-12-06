package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the productos_x_cliente_comext database table.
 * 
 */
@Entity
@Table(name="productos_x_cliente_comext")
@NamedQuery(name="ProductosXClienteComext.findAll", query="SELECT p FROM ProductosXClienteComext p")
public class ProductosXClienteComext implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductosXClienteComextPK pk;

	private Boolean activo;

	private BigDecimal descuentoxproducto;

	private Long id;

	@Column(name="id_moneda")
	private String idMoneda;

	private BigDecimal iva;

	@Column(name="otros_descuentos")
	private BigDecimal otrosDescuentos;

	private BigDecimal precio;

	@Column(name="reg_sanitario")
	private String regSanitario;

	//bi-directional many-to-one association to Cliente
	@ManyToOne(optional=false)
	@JoinColumn(name="id_cliente", referencedColumnName="id", insertable=false, updatable=false)
	private Cliente cliente;

	//bi-directional many-to-one association to ProductosInventario
	@ManyToOne(optional=false)
	@JoinColumn(name="id_producto", referencedColumnName="id", insertable=false, updatable=false)
	private ProductosInventario productosInventario;

	public ProductosXClienteComext() {
	}

	public ProductosXClienteComextPK getPk() {
		return this.pk;
	}

	public void setPk(ProductosXClienteComextPK pk) {
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

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdMoneda() {
		return this.idMoneda;
	}

	public void setIdMoneda(String idMoneda) {
		this.idMoneda = idMoneda;
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

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getRegSanitario() {
		return this.regSanitario;
	}

	public void setRegSanitario(String regSanitario) {
		this.regSanitario = regSanitario;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ProductosInventario getProductosInventario() {
		return this.productosInventario;
	}

	public void setProductosInventario(ProductosInventario productosInventario) {
		this.productosInventario = productosInventario;
	}

}