package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the documento_x_lotesoic database table.
 * 
 */
@Entity
@Table(name = "documento_x_lotesoic")
@NamedQueries({
		@NamedQuery(name = "DocumentoXLotesoic.findAll", query = "SELECT d FROM DocumentoXLotesoic d"),
		@NamedQuery(name = DocumentoXLotesoic.FIND_BY_CONSECUIVO_DOCUMENTO, query = "SELECT dl FROM DocumentoXLotesoic dl JOIN dl.documento d WHERE d.consecutivoDocumento = :consecutivoDocumento ORDER BY dl.consecutivo") })
public class DocumentoXLotesoic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4502146423397280123L;

	public static final String FIND_BY_CONSECUIVO_DOCUMENTO = "DocumentoXLotesoic.findByConsecutivoDocumento";

	@EmbeddedId
	private DocumentoXLotesoicPK id;

	private String asignacion;

	private String aviso;

	private String consecutivo;

	private BigDecimal contribucion;

	private BigDecimal dex;

	private Timestamp fecha;

	private String pedido;

	@Column(name = "total_cajas")
	private BigDecimal totalCajas;

	@Column(name = "total_cantidad")
	private BigDecimal totalCantidad;

	@Column(name = "total_peso_neto")
	private BigDecimal totalPesoNeto;

	// bi-directional many-to-one association to Documento
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name = "id_documento", referencedColumnName = "id", insertable = false, updatable = false)
	private Documento documento;

	// bi-directional many-to-one association to TipoLoteoic
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name = "id_tipo_lote", referencedColumnName = "id", insertable = false, updatable = false)
	private TipoLoteoic tipoLoteoic;

	public DocumentoXLotesoic() {
	}

	public DocumentoXLotesoicPK getId() {
		return this.id;
	}

	public void setId(DocumentoXLotesoicPK id) {
		this.id = id;
	}

	public String getAsignacion() {
		return this.asignacion;
	}

	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}

	public String getAviso() {
		return this.aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public String getConsecutivo() {
		return this.consecutivo;
	}

	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

	public BigDecimal getContribucion() {
		return this.contribucion;
	}

	public void setContribucion(BigDecimal contribucion) {
		this.contribucion = contribucion;
	}

	public BigDecimal getDex() {
		return this.dex;
	}

	public void setDex(BigDecimal dex) {
		this.dex = dex;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getPedido() {
		return this.pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public BigDecimal getTotalCajas() {
		return this.totalCajas;
	}

	public void setTotalCajas(BigDecimal totalCajas) {
		this.totalCajas = totalCajas;
	}

	public BigDecimal getTotalCantidad() {
		return this.totalCantidad;
	}

	public void setTotalCantidad(BigDecimal totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

	public BigDecimal getTotalPesoNeto() {
		return this.totalPesoNeto;
	}

	public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
		this.totalPesoNeto = totalPesoNeto;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public TipoLoteoic getTipoLoteoic() {
		return this.tipoLoteoic;
	}

	public void setTipoLoteoic(TipoLoteoic tipoLoteoic) {
		this.tipoLoteoic = tipoLoteoic;
	}

}