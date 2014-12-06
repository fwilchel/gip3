package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the metodo_pago database table.
 * 
 */
@Entity
@Table(name="metodo_pago")
@NamedQuery(name="MetodoPago.findAll", query="SELECT m FROM MetodoPago m")
public class MetodoPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descripcion;

	@Column(name="descripcion_ingles")
	private String descripcionIngles;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="metodoPago")
	private List<Cliente> clientes;

	public MetodoPago() {
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

	public String getDescripcionIngles() {
		return this.descripcionIngles;
	}

	public void setDescripcionIngles(String descripcionIngles) {
		this.descripcionIngles = descripcionIngles;
	}

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setMetodoPago(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setMetodoPago(null);

		return cliente;
	}

}