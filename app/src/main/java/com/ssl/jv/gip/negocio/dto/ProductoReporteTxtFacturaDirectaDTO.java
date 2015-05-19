package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Class ProductoGenerarFacturaPFDTO.
 */
@Entity
public class ProductoReporteTxtFacturaDirectaDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private BigInteger id;
  private BigInteger idUbicacionDestino;
  private String sku;
  private String nombre;
  private BigDecimal cantidad;
  private String fechaGeneracion;
  private String ConsecutivoDocumento;
  private BigDecimal valorUnitario;
  private String observacionDocumento;
  private BigInteger clienteIcg;
  private BigDecimal valorDescuentoProducto;
  private BigDecimal valorIva;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigInteger getIdUbicacionDestino() {
    return idUbicacionDestino;
  }

  public void setIdUbicacionDestino(BigInteger idUbicacionDestino) {
    this.idUbicacionDestino = idUbicacionDestino;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public BigDecimal getCantidad() {
    return cantidad;
  }

  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

  public String getFechaGeneracion() {
    return fechaGeneracion;
  }

  public void setFechaGeneracion(String fechaGeneracion) {
    this.fechaGeneracion = fechaGeneracion;
  }

  public String getConsecutivoDocumento() {
    return ConsecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    ConsecutivoDocumento = consecutivoDocumento;
  }

  public BigDecimal getValorUnitario() {
    return valorUnitario;
  }

  public void setValorUnitario(BigDecimal valorUnitario) {
    this.valorUnitario = valorUnitario;
  }

  public String getObservacionDocumento() {
    return observacionDocumento;
  }

  public void setObservacionDocumento(String observacionDocumento) {
    this.observacionDocumento = observacionDocumento;
  }

  public BigInteger getClienteIcg() {
    return clienteIcg;
  }

  public void setClienteIcg(BigInteger clienteIcg) {
    this.clienteIcg = clienteIcg;
  }

  public BigDecimal getValorDescuentoProducto() {
    return valorDescuentoProducto;
  }

  public void setValorDescuentoProducto(BigDecimal valorDescuentoProducto) {
    this.valorDescuentoProducto = valorDescuentoProducto;
  }

  public BigDecimal getValorIva() {
    return valorIva;
  }

  public void setValorIva(BigDecimal valorIva) {
    this.valorIva = valorIva;
  }

}
