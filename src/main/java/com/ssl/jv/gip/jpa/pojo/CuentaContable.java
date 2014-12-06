package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cuenta_contable database table.
 * 
 */
@Entity
@Table(name="cuenta_contable")
@NamedQuery(name="CuentaContable.findAll", query="SELECT c FROM CuentaContable c")
public class CuentaContable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descripcion;

	@Column(name="indicador_iva")
	private String indicadorIva;

	private String tipo;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="cuentaContable")
	private List<Cliente> clientes;

	//bi-directional many-to-one association to ProductosInventarioComext
	@OneToMany(mappedBy="cuentaContable")
	private List<ProductosInventarioComext> productosInventarioComexts;

	public CuentaContable() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIndicadorIva() {
		return this.indicadorIva;
	}

	public void setIndicadorIva(String indicadorIva) {
		this.indicadorIva = indicadorIva;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setCuentaContable(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setCuentaContable(null);

		return cliente;
	}

	public List<ProductosInventarioComext> getProductosInventarioComexts() {
		return this.productosInventarioComexts;
	}

	public void setProductosInventarioComexts(List<ProductosInventarioComext> productosInventarioComexts) {
		this.productosInventarioComexts = productosInventarioComexts;
	}

	public ProductosInventarioComext addProductosInventarioComext(ProductosInventarioComext productosInventarioComext) {
		getProductosInventarioComexts().add(productosInventarioComext);
		productosInventarioComext.setCuentaContable(this);

		return productosInventarioComext;
	}

	public ProductosInventarioComext removeProductosInventarioComext(ProductosInventarioComext productosInventarioComext) {
		getProductosInventarioComexts().remove(productosInventarioComext);
		productosInventarioComext.setCuentaContable(null);

		return productosInventarioComext;
	}

}