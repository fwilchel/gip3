package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the historial_costos database table.
 * 
 */
@Entity
@Table(name="historial_costos")
@NamedQuery(name="HistorialCosto.findAll", query="SELECT h FROM HistorialCosto h")
public class HistorialCosto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="es_definitivo")
	private Boolean esDefinitivo;

	@Column(name="fecha_final_costos")
	private Timestamp fechaFinalCostos;

	@Column(name="fecha_inicial_costos")
	private Timestamp fechaInicialCostos;

	//bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name="id_empresa")
	private Empresa empresa;

	public HistorialCosto() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEsDefinitivo() {
		return this.esDefinitivo;
	}

	public void setEsDefinitivo(Boolean esDefinitivo) {
		this.esDefinitivo = esDefinitivo;
	}

	public Timestamp getFechaFinalCostos() {
		return this.fechaFinalCostos;
	}

	public void setFechaFinalCostos(Timestamp fechaFinalCostos) {
		this.fechaFinalCostos = fechaFinalCostos;
	}

	public Timestamp getFechaInicialCostos() {
		return this.fechaInicialCostos;
	}

	public void setFechaInicialCostos(Timestamp fechaInicialCostos) {
		this.fechaInicialCostos = fechaInicialCostos;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}