package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class ListaEmpaqueDTO.
 */
public class FiltroConsultaSolicitudDTO implements Serializable {

	private static final long serialVersionUID = 6826470281110432159L;

	private String consecutivoDocumento;
	private String numeroPedido;
	private Date fechaBusqueda;
	private String nombreCliente;
	
	
	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}
	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}
	public String getNumeroPedido() {
		return numeroPedido;
	}
	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public Date getFechaBusqueda() {
		return fechaBusqueda;
	}
	public void setFechaBusqueda(Date fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	

}
