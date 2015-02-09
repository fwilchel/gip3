package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;



/**
 * The Class CintaMagneticaDTO.
 */
@Entity
public class CintaMagneticaDTO implements Serializable {

	private static final long serialVersionUID = 6826470281110432159L;
	@Id
	private String consecutivoDocumento;
	private Timestamp fechaGeneracion;
	private BigDecimal  valorDescuento;
	private BigDecimal  valorTotal;
	private BigDecimal  valorSubtotal;
	private String nombreCliente;
	private BigDecimal  valorImpuestos;
	private BigDecimal  valorIva10;
	private BigDecimal  valorIva16;

	
	
	
	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	
	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public Timestamp getFechaGeneracion() {
		return fechaGeneracion;
	}

	public void setFechaGeneracion(Timestamp fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	
	public BigDecimal getValorDescuento() {
		return valorDescuento;
	}

	public void setValorDescuento(BigDecimal valorDescuento) {
		this.valorDescuento = valorDescuento;
	}


	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorSubtotal() {
		return valorSubtotal;
	}

	public void setValorSubtotal(BigDecimal valorSubtotal) {
		this.valorSubtotal = valorSubtotal;
	}


	public BigDecimal getValorImpuestos() {
		return valorImpuestos;
	}

	public void setValorImpuestos(BigDecimal valorImpuestos) {
		this.valorImpuestos = valorImpuestos;
	}

	public BigDecimal getValorIva10() {
		return valorIva10;
	}

	public void setValorIva10(BigDecimal valorIva10) {
		this.valorIva10 = valorIva10;
	}

	public BigDecimal getValorIva16() {
		return valorIva16;
	}

	public void setValorIva16(BigDecimal valorIva16) {
		this.valorIva16 = valorIva16;
	}


	
}
