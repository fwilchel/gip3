package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the termino_incoterm database table.
 * 
 */
@Entity
@Table(name="termino_incoterm")
@NamedQuery(name="TerminoIncoterm.findAll", query="SELECT t FROM TerminoIncoterm t")
public class TerminoIncoterm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descripcion;

	//bi-directional many-to-many association to Cliente
	@ManyToMany
	@JoinTable(
		name="incoterm_x_cliente"
		, joinColumns={
			@JoinColumn(name="id_incoterm")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_cliente")
			}
		)
	private List<Cliente> clientes;

	//bi-directional many-to-one association to TerminosTransporte
	@OneToMany(mappedBy="terminoIncoterm")
	private List<TerminosTransporte> terminosTransportes;

	public TerminoIncoterm() {
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

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<TerminosTransporte> getTerminosTransportes() {
		return this.terminosTransportes;
	}

	public void setTerminosTransportes(List<TerminosTransporte> terminosTransportes) {
		this.terminosTransportes = terminosTransportes;
	}

	public TerminosTransporte addTerminosTransporte(TerminosTransporte terminosTransporte) {
		getTerminosTransportes().add(terminosTransporte);
		terminosTransporte.setTerminoIncoterm(this);

		return terminosTransporte;
	}

	public TerminosTransporte removeTerminosTransporte(TerminosTransporte terminosTransporte) {
		getTerminosTransportes().remove(terminosTransporte);
		terminosTransporte.setTerminoIncoterm(null);

		return terminosTransporte;
	}

}