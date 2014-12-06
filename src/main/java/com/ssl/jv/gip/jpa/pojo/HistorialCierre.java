package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the historial_cierre database table.
 * 
 */
@Entity
@Table(name="historial_cierre")
@NamedQuery(name="HistorialCierre.findAll", query="SELECT h FROM HistorialCierre h")
public class HistorialCierre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="es_definitivo")
	private Boolean esDefinitivo;

	@Column(name="fecha_final_cierre")
	private Timestamp fechaFinalCierre;

	@Column(name="fecha_inicial_cierre")
	private Timestamp fechaInicialCierre;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name="id_ubicacion")
	private Ubicacion ubicacione;

	public HistorialCierre() {
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

	public Timestamp getFechaFinalCierre() {
		return this.fechaFinalCierre;
	}

	public void setFechaFinalCierre(Timestamp fechaFinalCierre) {
		this.fechaFinalCierre = fechaFinalCierre;
	}

	public Timestamp getFechaInicialCierre() {
		return this.fechaInicialCierre;
	}

	public void setFechaInicialCierre(Timestamp fechaInicialCierre) {
		this.fechaInicialCierre = fechaInicialCierre;
	}

	public Ubicacion getUbicacione() {
		return this.ubicacione;
	}

	public void setUbicacione(Ubicacion ubicacione) {
		this.ubicacione = ubicacione;
	}

}