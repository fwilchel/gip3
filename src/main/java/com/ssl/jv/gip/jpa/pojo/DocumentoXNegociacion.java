package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the documento_x_negociacion database table.
 * 
 */
@Entity
@Table(name="documento_x_negociacion")
@NamedQuery(name="DocumentoXNegociacion.findAll", query="SELECT d FROM DocumentoXNegociacion d")
public class DocumentoXNegociacion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private DocumentoXNegociacionPK pk;

	@Column(name="cantidad_contenedores_de_20")
	private BigDecimal cantidadContenedoresDe20;

	@Column(name="cantidad_contenedores_de_40")
	private BigDecimal cantidadContenedoresDe40;

	@Column(name="cantidad_dias_vigencia")
	private Integer cantidadDiasVigencia;

	@Column(name="cantidad_estibas")
	private Integer cantidadEstibas;

	@Column(name="costo_entrega")
	private BigDecimal costoEntrega;

	@Column(name="costo_flete")
	private BigDecimal costoFlete;

	@Column(name="costo_seguro")
	private BigDecimal costoSeguro;

	private String descripcion;



	@Column(name="lugar_incoterm")
	private String lugarIncoterm;

	@Column(name="observaciones_marcacion_2")
	private String observacionesMarcacion2;

	@Column(name="otros_gastos")
	private BigDecimal otrosGastos;

	@Column(name="peso_bruto_estibas")
	private Integer pesoBrutoEstibas;

	@Column(name="solicitud_cafe")
	private Boolean solicitudCafe;

	@Column(name="tipo_solicitud")
	private String tipoSolicitud;

	@Column(name="total_pallets")
	private BigDecimal totalPallets;

	@Column(name="total_peso_bruto")
	private BigDecimal totalPesoBruto;

	@Column(name="total_peso_neto")
	private BigDecimal totalPesoNeto;

	@Column(name="total_tendidos")
	private BigDecimal totalTendidos;

	//bi-directional many-to-one association to AgenciaCarga
	@ManyToOne
	@JoinColumn(name="id_agencia_carga")
	private AgenciaCarga agenciaCarga;

	//bi-directional many-to-one association to Documento
	@ManyToOne(optional=false)
	@JoinColumn(name="id_documento", referencedColumnName="id", updatable=false, insertable=false)
	private Documento documento;
	
	public DocumentoXNegociacion() {
	}

	public BigDecimal getCantidadContenedoresDe20() {
		return this.cantidadContenedoresDe20;
	}

	public void setCantidadContenedoresDe20(BigDecimal cantidadContenedoresDe20) {
		this.cantidadContenedoresDe20 = cantidadContenedoresDe20;
	}

	public BigDecimal getCantidadContenedoresDe40() {
		return this.cantidadContenedoresDe40;
	}

	public void setCantidadContenedoresDe40(BigDecimal cantidadContenedoresDe40) {
		this.cantidadContenedoresDe40 = cantidadContenedoresDe40;
	}

	public Integer getCantidadDiasVigencia() {
		return this.cantidadDiasVigencia;
	}

	public void setCantidadDiasVigencia(Integer cantidadDiasVigencia) {
		this.cantidadDiasVigencia = cantidadDiasVigencia;
	}

	public Integer getCantidadEstibas() {
		return this.cantidadEstibas;
	}

	public void setCantidadEstibas(Integer cantidadEstibas) {
		this.cantidadEstibas = cantidadEstibas;
	}

	public BigDecimal getCostoEntrega() {
		return this.costoEntrega;
	}

	public void setCostoEntrega(BigDecimal costoEntrega) {
		this.costoEntrega = costoEntrega;
	}

	public BigDecimal getCostoFlete() {
		return this.costoFlete;
	}

	public void setCostoFlete(BigDecimal costoFlete) {
		this.costoFlete = costoFlete;
	}

	public BigDecimal getCostoSeguro() {
		return this.costoSeguro;
	}

	public void setCostoSeguro(BigDecimal costoSeguro) {
		this.costoSeguro = costoSeguro;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLugarIncoterm() {
		return this.lugarIncoterm;
	}

	public void setLugarIncoterm(String lugarIncoterm) {
		this.lugarIncoterm = lugarIncoterm;
	}

	public String getObservacionesMarcacion2() {
		return this.observacionesMarcacion2;
	}

	public void setObservacionesMarcacion2(String observacionesMarcacion2) {
		this.observacionesMarcacion2 = observacionesMarcacion2;
	}

	public BigDecimal getOtrosGastos() {
		return this.otrosGastos;
	}

	public void setOtrosGastos(BigDecimal otrosGastos) {
		this.otrosGastos = otrosGastos;
	}

	public Integer getPesoBrutoEstibas() {
		return this.pesoBrutoEstibas;
	}

	public void setPesoBrutoEstibas(Integer pesoBrutoEstibas) {
		this.pesoBrutoEstibas = pesoBrutoEstibas;
	}

	public Boolean getSolicitudCafe() {
		return this.solicitudCafe;
	}

	public void setSolicitudCafe(Boolean solicitudCafe) {
		this.solicitudCafe = solicitudCafe;
	}

	public String getTipoSolicitud() {
		return this.tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public BigDecimal getTotalPallets() {
		return this.totalPallets;
	}

	public void setTotalPallets(BigDecimal totalPallets) {
		this.totalPallets = totalPallets;
	}

	public BigDecimal getTotalPesoBruto() {
		return this.totalPesoBruto;
	}

	public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
		this.totalPesoBruto = totalPesoBruto;
	}

	public BigDecimal getTotalPesoNeto() {
		return this.totalPesoNeto;
	}

	public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
		this.totalPesoNeto = totalPesoNeto;
	}

	public BigDecimal getTotalTendidos() {
		return this.totalTendidos;
	}

	public void setTotalTendidos(BigDecimal totalTendidos) {
		this.totalTendidos = totalTendidos;
	}

	public AgenciaCarga getAgenciaCarga() {
		return this.agenciaCarga;
	}

	public void setAgenciaCarga(AgenciaCarga agenciaCarga) {
		this.agenciaCarga = agenciaCarga;
	}

	public DocumentoXNegociacionPK getPk() {
		return pk;
	}

	public void setPk(DocumentoXNegociacionPK pk) {
		this.pk = pk;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}




}