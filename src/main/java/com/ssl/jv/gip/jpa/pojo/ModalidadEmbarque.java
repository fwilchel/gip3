package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the modalidad_embarque database table.
 * 
 */
@Entity
@Table(name="modalidad_embarque")
@NamedQuery(name="ModalidadEmbarque.findAll", query="SELECT m FROM ModalidadEmbarque m")
public class ModalidadEmbarque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descripcion;

	//bi-directional many-to-one association to TerminosTransporte
	@OneToMany(mappedBy="modalidadEmbarque")
	private List<TerminosTransporte> terminosTransportes;

	public ModalidadEmbarque() {
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

	public List<TerminosTransporte> getTerminosTransportes() {
		return this.terminosTransportes;
	}

	public void setTerminosTransportes(List<TerminosTransporte> terminosTransportes) {
		this.terminosTransportes = terminosTransportes;
	}

	public TerminosTransporte addTerminosTransporte(TerminosTransporte terminosTransporte) {
		getTerminosTransportes().add(terminosTransporte);
		terminosTransporte.setModalidadEmbarque(this);

		return terminosTransporte;
	}

	public TerminosTransporte removeTerminosTransporte(TerminosTransporte terminosTransporte) {
		getTerminosTransportes().remove(terminosTransporte);
		terminosTransporte.setModalidadEmbarque(null);

		return terminosTransporte;
	}

}