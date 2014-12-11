package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the conteos2 database table.
 * 
 */
@Entity
@Table(name="conteos2")
@NamedQuery(name="Conteo2.findAll", query="SELECT c FROM Conteo2 c")
public class Conteo2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="cantidad_reportada")
	private BigDecimal cantidadReportada;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_conteo")
	private Date fechaConteo;

	@Id
	private Long id;

	@Column(name="id_bodega_logica")
	private Long idBodegaLogica;

	@Column(name="id_producto")
	private Long idProducto;

	@Column(name="id_tipo_conteo")
	private Long idTipoConteo;

	@Column(name="id_ubicacion")
	private Long idUbicacion;

	@Column(name="id_unidad")
	private Long idUnidad;

	private Boolean revisado;

	public Conteo2() {
	}

	public BigDecimal getCantidadReportada() {
		return this.cantidadReportada;
	}

	public void setCantidadReportada(BigDecimal cantidadReportada) {
		this.cantidadReportada = cantidadReportada;
	}

	public Date getFechaConteo() {
		return this.fechaConteo;
	}

	public void setFechaConteo(Date fechaConteo) {
		this.fechaConteo = fechaConteo;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdBodegaLogica() {
		return this.idBodegaLogica;
	}

	public void setIdBodegaLogica(Long idBodegaLogica) {
		this.idBodegaLogica = idBodegaLogica;
	}

	public Long getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public Long getIdTipoConteo() {
		return this.idTipoConteo;
	}

	public void setIdTipoConteo(Long idTipoConteo) {
		this.idTipoConteo = idTipoConteo;
	}

	public Long getIdUbicacion() {
		return this.idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public Long getIdUnidad() {
		return this.idUnidad;
	}

	public void setIdUnidad(Long idUnidad) {
		this.idUnidad = idUnidad;
	}

	public Boolean getRevisado() {
		return this.revisado;
	}

	public void setRevisado(Boolean revisado) {
		this.revisado = revisado;
	}

}