package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;



/**
 * The Class ListaEmpaqueDTO.
 */
@Entity
public class FacturaDirectaDTO implements Serializable {

	private static final long serialVersionUID = 6826470281110432159L;
	@Id
	private String idDocumento;
	private String consecutivoDocumento;
	private Timestamp fechaGeneracion;
	//private ClienteDTO cliente;
	//private Long idUbicacionOrigen;
	//private Long idUbicacionDestino;
	//private Long idTipoDocumento;
	//private Timestamp fechaEntrega;
	//private Long idProveedor;
	//private Long idEstado;
	//private String estadoNombre;
	private String observacionDocumento;
	//private UbicacionDTO ubicacion;
	
	private BigDecimal  valorDescuento;
	

	private BigDecimal  valorIva10;
	private BigDecimal  valorIva16;
	private BigDecimal  valorIva5;
	private BigDecimal  valorTotal;
	private BigDecimal  valorSubtotal;
	
	//private PuntoVentaDTO puntoventa;
	
	
	private String telefonoCliente;
	private String direccionCliente;
	private String contactoCliente;
	private String nitCliente;	
	private String nombrePuntoVenta;
	private String direccionPuntoVenta;
	private String telefonoPuntoVenta;
	private String nombreUbicacion;
	private String nombreCliente;
	
	private String nombreCiudadPuntoVenta;
	private String nombreCiudadCliente;
	
	private long estado;
	
	private BigDecimal descuentoCliente;
	
	private Timestamp fechaGeneracionVD;
	
	private String numeroFactura;
	
	

	
	
	public BigDecimal getValorIva5() {
		return valorIva5;
	}

	public void setValorIva5(BigDecimal valorIva5) {
		this.valorIva5 = valorIva5;
	}
	public String getNitCliente() {
		return nitCliente;
	}

	public void setNitCliente(String nitCliente) {
		this.nitCliente = nitCliente;
	}

	
	

	public String getNombreUbicacion() {
		return nombreUbicacion;
	}

	public void setNombreUbicacion(String nombreUbicacion) {
		this.nombreUbicacion = nombreUbicacion;
	}


	
	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getTelefonoCliente() {
		return telefonoCliente;
	}

	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public String getContactoCliente() {
		return contactoCliente;
	}

	public void setContactoCliente(String contactoCliente) {
		this.contactoCliente = contactoCliente;
	}

	public String getNombrePuntoVenta() {
		return nombrePuntoVenta;
	}

	public void setNombrePuntoVenta(String nombrePuntoVenta) {
		this.nombrePuntoVenta = nombrePuntoVenta;
	}

	public String getDireccionPuntoVenta() {
		return direccionPuntoVenta;
	}

	public void setDireccionPuntoVenta(String direccionPuntoVenta) {
		this.direccionPuntoVenta = direccionPuntoVenta;
	}

	public String getTelefonoPuntoVenta() {
		return telefonoPuntoVenta;
	}

	public void setTelefonoPuntoVenta(String telefonoPuntoVenta) {
		this.telefonoPuntoVenta = telefonoPuntoVenta;
	}



	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
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

	/*
	public Long getIdUbicacionOrigen() {
		return idUbicacionOrigen;
	}

	public void setIdUbicacionOrigen(Long idUbicacionOrigen) {
		this.idUbicacionOrigen = idUbicacionOrigen;
	}

	public Long getIdUbicacionDestino() {
		return idUbicacionDestino;
	}

	public void setIdUbicacionDestino(Long idUbicacionDestino) {
		this.idUbicacionDestino = idUbicacionDestino;
	}

	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public Timestamp getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}


	public String getEstadoNombre() {
		return estadoNombre;
	}


	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}
*/
	
	public String getObservacionDocumento() {
		return observacionDocumento;
	}

	
	public void setObservacionDocumento(String observacionDocumento) {
		this.observacionDocumento = observacionDocumento;
	}
	
	public BigDecimal getValorDescuento() {
		return valorDescuento;
	}

	public void setValorDescuento(BigDecimal valorDescuento) {
		this.valorDescuento = valorDescuento;
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

	public String getNombreCiudadPuntoVenta() {
		return nombreCiudadPuntoVenta;
	}

	public void setNombreCiudadPuntoVenta(String nombreCiudadPuntoVenta) {
		this.nombreCiudadPuntoVenta = nombreCiudadPuntoVenta;
	}

	public String getNombreCiudadCliente() {
		return nombreCiudadCliente;
	}

	public void setNombreCiudadCliente(String nombreCiudadCliente) {
		this.nombreCiudadCliente = nombreCiudadCliente;
	}

	public long getEstado() {
		return estado;
	}

	public void setEstado(long estado) {
		this.estado = estado;
	}

	public BigDecimal getDescuentoCliente() {
		return descuentoCliente;
	}

	public void setDescuentoCliente(BigDecimal descuentoCliente) {
		this.descuentoCliente = descuentoCliente;
	}

	public Timestamp getFechaGeneracionVD() {
		return fechaGeneracionVD;
	}

	public void setFechaGeneracionVD(Timestamp fechaGeneracionVD) {
		this.fechaGeneracionVD = fechaGeneracionVD;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}



	
}
