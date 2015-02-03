package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReporteVentaDTO  implements Serializable{

	
	private static final long serialVersionUID = 3020144764605752606L;


	
	
	@Id
	private Long id;
	private BigDecimal cantidad;
	private BigDecimal cantidadVD;
	private BigDecimal cantidadFD;
	private Timestamp fechaGeneracion;
	private String nombreCliente;
	private String nombrePuntoVenta;
	private String consecutivoDocumentoVD;
	private String consecutivoDocumentoRM; 
	private String consecutivoDocumentoFD;
	private String nombreProducto;
	private String sku;
	private BigDecimal valorUnitario;
	private BigDecimal  valorSubtotal;
	private BigDecimal valorDescuentoCliente;
	private BigDecimal valorBaseIva;
	private BigDecimal  valorPorcentajeIva;
	private BigDecimal  valorIva;
	private BigDecimal  valorTotal;
	private String OrdenCompra;
	private String NumeroEntregaSap;
	private String ConsecutivoOD;
	private String NumeroPedidoSap;
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getCantidadVD() {
		return cantidadVD;
	}
	public void setCantidadVD(BigDecimal cantidadVD) {
		this.cantidadVD = cantidadVD;
	}
	public BigDecimal getCantidadFD() {
		return cantidadFD;
	}
	public void setCantidadFD(BigDecimal cantidadFD) {
		this.cantidadFD = cantidadFD;
	}
	public Timestamp getFechaGeneracion() {
		return fechaGeneracion;
	}
	public void setFechaGeneracion(Timestamp fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getNombrePuntoVenta() {
		return nombrePuntoVenta;
	}
	public void setNombrePuntoVenta(String nombrePuntoVenta) {
		this.nombrePuntoVenta = nombrePuntoVenta;
	}
	public String getConsecutivoDocumentoVD() {
		return consecutivoDocumentoVD;
	}
	public void setConsecutivoDocumentoVD(String consecutivoDocumentoVD) {
		this.consecutivoDocumentoVD = consecutivoDocumentoVD;
	}
	public String getConsecutivoDocumentoRM() {
		return consecutivoDocumentoRM;
	}
	public void setConsecutivoDocumentoRM(String consecutivoDocumentoRM) {
		this.consecutivoDocumentoRM = consecutivoDocumentoRM;
	}
	public String getConsecutivoDocumentoFD() {
		return consecutivoDocumentoFD;
	}
	public void setConsecutivoDocumentoFD(String consecutivoDocumentoFD) {
		this.consecutivoDocumentoFD = consecutivoDocumentoFD;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public BigDecimal getValorSubtotal() {
		return valorSubtotal;
	}
	public void setValorSubtotal(BigDecimal valorSubtotal) {
		this.valorSubtotal = valorSubtotal;
	}
	public BigDecimal getValorDescuentoCliente() {
		return valorDescuentoCliente;
	}
	public void setValorDescuentoCliente(BigDecimal valorDescuentoCliente) {
		this.valorDescuentoCliente = valorDescuentoCliente;
	}
	public BigDecimal getValorBaseIva() {
		return valorBaseIva;
	}
	public void setValorBaseIva(BigDecimal valorBaseIva) {
		this.valorBaseIva = valorBaseIva;
	}
	public BigDecimal getValorPorcentajeIva() {
		return valorPorcentajeIva;
	}
	public void setValorPorcentajeIva(BigDecimal valorPorcentajeIva) {
		this.valorPorcentajeIva = valorPorcentajeIva;
	}
	public BigDecimal getValorIva() {
		return valorIva;
	}
	public void setValorIva(BigDecimal valorIva) {
		this.valorIva = valorIva;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getOrdenCompra() {
		return OrdenCompra;
	}
	public void setOrdenCompra(String ordenCompra) {
		OrdenCompra = ordenCompra;
	}
	public String getNumeroEntregaSap() {
		return NumeroEntregaSap;
	}
	public void setNumeroEntregaSap(String numeroEntregaSap) {
		NumeroEntregaSap = numeroEntregaSap;
	}
	public String getConsecutivoOD() {
		return ConsecutivoOD;
	}
	public void setConsecutivoOD(String consecutivoOD) {
		ConsecutivoOD = consecutivoOD;
	}
	public String getNumeroPedidoSap() {
		return NumeroPedidoSap;
	}
	public void setNumeroPedidoSap(String numeroPedidoSap) {
		NumeroPedidoSap = numeroPedidoSap;
	}
	
	
	
	

}
