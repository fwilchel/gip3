package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the conteos database table.
 * 
 */
@Entity
@Table(name="conteos")
@NamedQuery(name="Conteo.findAll", query="SELECT c FROM Conteo c")
public class Conteo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="cantidad_reportada")
	private BigDecimal cantidadReportada;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_conteo")
	private Date fechaConteo;

	private Boolean revisado;

	//bi-directional many-to-one association to BodegasLogica
	@ManyToOne
	@JoinColumn(name="id_bodega_logica")
	private BodegasLogica bodegasLogica;

	//bi-directional many-to-one association to ProductosInventario
	@ManyToOne
	@JoinColumn(name="id_producto")
	private ProductosInventario productosInventario;

	//bi-directional many-to-one association to TipoConteo
	@ManyToOne
	@JoinColumn(name="id_tipo_conteo")
	private TipoConteo tipoConteo;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name="id_ubicacion")
	private Ubicacion ubicacione;

	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="id_unidad")
	private Unidad unidade;

	public Conteo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getRevisado() {
		return this.revisado;
	}

	public void setRevisado(Boolean revisado) {
		this.revisado = revisado;
	}

	public BodegasLogica getBodegasLogica() {
		return this.bodegasLogica;
	}

	public void setBodegasLogica(BodegasLogica bodegasLogica) {
		this.bodegasLogica = bodegasLogica;
	}

	public ProductosInventario getProductosInventario() {
		return this.productosInventario;
	}

	public void setProductosInventario(ProductosInventario productosInventario) {
		this.productosInventario = productosInventario;
	}

	public TipoConteo getTipoConteo() {
		return this.tipoConteo;
	}

	public void setTipoConteo(TipoConteo tipoConteo) {
		this.tipoConteo = tipoConteo;
	}

	public Ubicacion getUbicacione() {
		return this.ubicacione;
	}

	public void setUbicacione(Ubicacion ubicacione) {
		this.ubicacione = ubicacione;
	}

	public Unidad getUnidade() {
		return this.unidade;
	}

	public void setUnidade(Unidad unidade) {
		this.unidade = unidade;
	}

}