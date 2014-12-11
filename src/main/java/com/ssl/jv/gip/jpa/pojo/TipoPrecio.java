package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_precio database table.
 * 
 */
@Entity
@Table(name="tipo_precio")
@NamedQuery(name="TipoPrecio.findAll", query="SELECT t FROM TipoPrecio t")
public class TipoPrecio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="tipoPrecio")
	private List<Cliente> clientes;

	public TipoPrecio() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setTipoPrecio(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setTipoPrecio(null);

		return cliente;
	}

}