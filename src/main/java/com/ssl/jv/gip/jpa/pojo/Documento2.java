package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the documentos2 database table.
 * 
 */
@Entity
@Table(name="documentos2")
@NamedQuery(name="Documento2.findAll", query="SELECT d FROM Documento2 d")
public class Documento2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="consecutivo_documento")
	private String consecutivoDocumento;

	@Column(name="fecha_entrega")
	private Timestamp fechaEntrega;

	@Column(name="fecha_esperada_entrega")
	private Timestamp fechaEsperadaEntrega;

	@Column(name="fecha_generacion")
	private Timestamp fechaGeneracion;

	@Column(name="observacion_documento")
	private String observacionDocumento;

	//bi-directional many-to-one association to Estadosxdocumento
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="id_estado", referencedColumnName="id_estado"),
		@JoinColumn(name="id_tipo_documento", referencedColumnName="id_tipo_documento")
		})
	private Estadosxdocumento estadosxdocumento;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor")
	private Proveedor proveedore;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name="id_ubicacion_destino")
	private Ubicacion ubicacione1;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name="id_ubicacion_origen")
	private Ubicacion ubicacione2;

	public Documento2() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsecutivoDocumento() {
		return this.consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public Timestamp getFechaEntrega() {
		return this.fechaEntrega;
	}

	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Timestamp getFechaEsperadaEntrega() {
		return this.fechaEsperadaEntrega;
	}

	public void setFechaEsperadaEntrega(Timestamp fechaEsperadaEntrega) {
		this.fechaEsperadaEntrega = fechaEsperadaEntrega;
	}

	public Timestamp getFechaGeneracion() {
		return this.fechaGeneracion;
	}

	public void setFechaGeneracion(Timestamp fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public String getObservacionDocumento() {
		return this.observacionDocumento;
	}

	public void setObservacionDocumento(String observacionDocumento) {
		this.observacionDocumento = observacionDocumento;
	}

	public Estadosxdocumento getEstadosxdocumento() {
		return this.estadosxdocumento;
	}

	public void setEstadosxdocumento(Estadosxdocumento estadosxdocumento) {
		this.estadosxdocumento = estadosxdocumento;
	}

	public Proveedor getProveedore() {
		return this.proveedore;
	}

	public void setProveedore(Proveedor proveedore) {
		this.proveedore = proveedore;
	}

	public Ubicacion getUbicacione1() {
		return this.ubicacione1;
	}

	public void setUbicacione1(Ubicacion ubicacione1) {
		this.ubicacione1 = ubicacione1;
	}

	public Ubicacion getUbicacione2() {
		return this.ubicacione2;
	}

	public void setUbicacione2(Ubicacion ubicacione2) {
		this.ubicacione2 = ubicacione2;
	}

}