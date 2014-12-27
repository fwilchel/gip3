package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the termino_incoterm_x_medio_transporte database table.
 * 
 */
@Entity
@Table(name="termino_incoterm_x_medio_transporte")
@NamedQuery(name="TerminoIncotermXMedioTransporte.findAll", query="SELECT t FROM TerminoIncotermXMedioTransporte t")
public class TerminoIncotermXMedioTransporte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator( name = "termino_incoterm_x_medio_transporte_id_seq", sequenceName = "termino_incoterm_x_medio_transporte_id_seq", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "termino_incoterm_x_medio_transporte_id_seq" )
	private Long id;

	private Boolean activo;

	//bi-directional many-to-one association to MedioTransporte
	@ManyToOne
	@JoinColumn(name="id_medio_transporte")
	private MedioTransporte medioTransporte;

	//bi-directional many-to-one association to TerminoIncoterm
	@ManyToOne
	@JoinColumn(name="id_termino_incoterm")
	private TerminoIncoterm terminoIncoterm;

	public TerminoIncotermXMedioTransporte() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public MedioTransporte getMedioTransporte() {
		return this.medioTransporte;
	}

	public void setMedioTransporte(MedioTransporte medioTransporte) {
		this.medioTransporte = medioTransporte;
	}

	public TerminoIncoterm getTerminoIncoterm() {
		return this.terminoIncoterm;
	}

	public void setTerminoIncoterm(TerminoIncoterm terminoIncoterm) {
		this.terminoIncoterm = terminoIncoterm;
	}

}