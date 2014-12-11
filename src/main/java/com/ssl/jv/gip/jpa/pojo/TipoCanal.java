package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_canal database table.
 * 
 */
@Entity
@Table(name="tipo_canal")
@NamedQuery(name="TipoCanal.findAll", query="SELECT t FROM TipoCanal t")
public class TipoCanal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="tipoCanal")
	private List<Cliente> clientes;

	//bi-directional many-to-many association to Usuario
	@ManyToMany(mappedBy="tipoCanals")
	private List<Usuario> usuarios;

	//bi-directional many-to-one association to Ubicacion
	@OneToMany(mappedBy="tipoCanal")
	private List<Ubicacion> ubicaciones;

	public TipoCanal() {
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
		cliente.setTipoCanal(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setTipoCanal(null);

		return cliente;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Ubicacion> getUbicaciones() {
		return this.ubicaciones;
	}

	public void setUbicaciones(List<Ubicacion> ubicaciones) {
		this.ubicaciones = ubicaciones;
	}

	public Ubicacion addUbicacione(Ubicacion ubicacione) {
		getUbicaciones().add(ubicacione);
		ubicacione.setTipoCanal(this);

		return ubicacione;
	}

	public Ubicacion removeUbicacione(Ubicacion ubicacione) {
		getUbicaciones().remove(ubicacione);
		ubicacione.setTipoCanal(null);

		return ubicacione;
	}

}