package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the export_fx database table.
 * 
 */
@Embeddable
public class ExportFxPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal cantidad1;

	@Column(name="cliente_icg")
	private Long clienteIcg;

	@Column(name="consecutivo_documento")
	private String consecutivoDocumento;

	private BigDecimal descuentoxproducto;

	@Column(name="fecha_generacion")
	private Timestamp fechaGeneracion;

	@Column(name="id_ubicacion_destino")
	private Long idUbicacionDestino;

	private Integer iva;

	private String nombre;

	@Column(name="observacion_documento")
	private String observacionDocumento;

	private String sku;

	@Column(name="valor_unitario_ml")
	private BigDecimal valorUnitarioMl;

	public ExportFxPK() {
	}

	public BigDecimal getCantidad1() {
		return this.cantidad1;
	}

	public void setCantidad1(BigDecimal cantidad1) {
		this.cantidad1 = cantidad1;
	}

	public Long getClienteIcg() {
		return this.clienteIcg;
	}

	public void setClienteIcg(Long clienteIcg) {
		this.clienteIcg = clienteIcg;
	}

	public String getConsecutivoDocumento() {
		return this.consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public BigDecimal getDescuentoxproducto() {
		return this.descuentoxproducto;
	}

	public void setDescuentoxproducto(BigDecimal descuentoxproducto) {
		this.descuentoxproducto = descuentoxproducto;
	}

	public Timestamp getFechaGeneracion() {
		return this.fechaGeneracion;
	}

	public void setFechaGeneracion(Timestamp fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public Long getIdUbicacionDestino() {
		return this.idUbicacionDestino;
	}

	public void setIdUbicacionDestino(Long idUbicacionDestino) {
		this.idUbicacionDestino = idUbicacionDestino;
	}

	public Integer getIva() {
		return this.iva;
	}

	public void setIva(Integer iva) {
		this.iva = iva;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacionDocumento() {
		return this.observacionDocumento;
	}

	public void setObservacionDocumento(String observacionDocumento) {
		this.observacionDocumento = observacionDocumento;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public BigDecimal getValorUnitarioMl() {
		return this.valorUnitarioMl;
	}

	public void setValorUnitarioMl(BigDecimal valorUnitarioMl) {
		this.valorUnitarioMl = valorUnitarioMl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cantidad1 == null) ? 0 : cantidad1.hashCode());
		result = prime * result
				+ ((clienteIcg == null) ? 0 : clienteIcg.hashCode());
		result = prime
				* result
				+ ((consecutivoDocumento == null) ? 0 : consecutivoDocumento
						.hashCode());
		result = prime
				* result
				+ ((descuentoxproducto == null) ? 0 : descuentoxproducto
						.hashCode());
		result = prime * result
				+ ((fechaGeneracion == null) ? 0 : fechaGeneracion.hashCode());
		result = prime
				* result
				+ ((idUbicacionDestino == null) ? 0 : idUbicacionDestino
						.hashCode());
		result = prime * result + ((iva == null) ? 0 : iva.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime
				* result
				+ ((observacionDocumento == null) ? 0 : observacionDocumento
						.hashCode());
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		result = prime * result
				+ ((valorUnitarioMl == null) ? 0 : valorUnitarioMl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportFxPK other = (ExportFxPK) obj;
		if (cantidad1 == null) {
			if (other.cantidad1 != null)
				return false;
		} else if (!cantidad1.equals(other.cantidad1))
			return false;
		if (clienteIcg == null) {
			if (other.clienteIcg != null)
				return false;
		} else if (!clienteIcg.equals(other.clienteIcg))
			return false;
		if (consecutivoDocumento == null) {
			if (other.consecutivoDocumento != null)
				return false;
		} else if (!consecutivoDocumento.equals(other.consecutivoDocumento))
			return false;
		if (descuentoxproducto == null) {
			if (other.descuentoxproducto != null)
				return false;
		} else if (!descuentoxproducto.equals(other.descuentoxproducto))
			return false;
		if (fechaGeneracion == null) {
			if (other.fechaGeneracion != null)
				return false;
		} else if (!fechaGeneracion.equals(other.fechaGeneracion))
			return false;
		if (idUbicacionDestino == null) {
			if (other.idUbicacionDestino != null)
				return false;
		} else if (!idUbicacionDestino.equals(other.idUbicacionDestino))
			return false;
		if (iva == null) {
			if (other.iva != null)
				return false;
		} else if (!iva.equals(other.iva))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (observacionDocumento == null) {
			if (other.observacionDocumento != null)
				return false;
		} else if (!observacionDocumento.equals(other.observacionDocumento))
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		if (valorUnitarioMl == null) {
			if (other.valorUnitarioMl != null)
				return false;
		} else if (!valorUnitarioMl.equals(other.valorUnitarioMl))
			return false;
		return true;
	}
	

}