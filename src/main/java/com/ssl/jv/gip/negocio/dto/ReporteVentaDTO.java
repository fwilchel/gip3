package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReporteVentaDTO implements Serializable {

  private static final long serialVersionUID = 3020144764605752606L;

  @Id
  private Long id;

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
  private BigDecimal valorDescuentoCliente;
  private BigDecimal valorPorcentajeIva;
  private String OrdenCompra;
  private String NumeroEntregaSap;
  private String ConsecutivoOD;
  private String NumeroPedidoSap;
  private BigDecimal valorDescuentoProducto;
  private BigDecimal valorOtrosDescuentos;
  private String nitCliente;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public BigDecimal getValorDescuentoCliente() {
    return valorDescuentoCliente;
  }

  public void setValorDescuentoCliente(BigDecimal valorDescuentoCliente) {
    this.valorDescuentoCliente = valorDescuentoCliente;
  }

  public BigDecimal getValorPorcentajeIva() {
    return valorPorcentajeIva;
  }

  public void setValorPorcentajeIva(BigDecimal valorPorcentajeIva) {
    this.valorPorcentajeIva = valorPorcentajeIva;
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

  public BigDecimal getValorDescuentoProducto() {
    return valorDescuentoProducto;
  }

  public void setValorDescuentoProducto(BigDecimal valorDescuentoProducto) {
    this.valorDescuentoProducto = valorDescuentoProducto;
  }

  public BigDecimal getValorOtrosDescuentos() {
    return valorOtrosDescuentos;
  }

  public void setValorOtrosDescuentos(BigDecimal valorOtrosDescuentos) {
    this.valorOtrosDescuentos = valorOtrosDescuentos;
  }

  public String getNitCliente() {
    return nitCliente;
  }

  public void setNitCliente(String nitCliente) {
    this.nitCliente = nitCliente;
  }

}
