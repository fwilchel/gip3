package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the termino_incoterm database table.
 * 
 */
@Entity
@Table(name="termino_incoterm")
@NamedQueries(value={
@NamedQuery(name="TerminoIncoterm.findAll", query="SELECT t FROM TerminoIncoterm t"),
@NamedQuery(name="TerminoIncoterm.findByMedioTrans", query="SELECT t FROM TerminoIncoterm t join t.terminosTransportes r "),
@NamedQuery(name="TerminoIncoterm.findByCliente", query="SELECT DISTINCT t FROM TerminoIncoterm t JOIN t.clientes c WHERE c.id= :idCliente ORDER BY t.descripcion"),
})
public class TerminoIncoterm implements Serializable, Comparable<TerminoIncoterm> {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descripcion;

	//bi-directional many-to-many association to Cliente
	@ManyToMany(mappedBy = "terminoIncoterms")
	private List<Cliente> clientes;

	//bi-directional many-to-one association to TerminosTransporte
	@OneToMany(mappedBy="terminoIncoterm")
	private List<TerminosTransporte> terminosTransportes;
	
	@Transient
	private boolean seleccionado;

	

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

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public int compareTo(TerminoIncoterm o) {
		return descripcion.compareTo(o.descripcion);
	}

	



}