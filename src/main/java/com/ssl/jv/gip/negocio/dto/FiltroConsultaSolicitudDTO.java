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
	private Date fechaInicio;
	private Date fechaFinal;
	private String ubicaciones;
	private Long tipoDocumento;
	private Long idEstado;
	private String proveedor;
	
	
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
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public String getUbicaciones() {
		return ubicaciones;
	}
	public void setUbicaciones(String ubicaciones) {
		this.ubicaciones = ubicaciones;
	}
	public Long getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(Long tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Long getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	
	

}
