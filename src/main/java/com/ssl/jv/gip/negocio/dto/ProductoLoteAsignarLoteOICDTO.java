package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * The Class ProductoLoteAsignarLoteOICDTO.
 */
@Entity
public class ProductoLoteAsignarLoteOICDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private BigInteger tipoLote;
  private String descripcionLote;

  private BigDecimal totalPesoNeto;
  private BigDecimal totalPesoBruto;
  private BigDecimal totalCajas;
  private BigDecimal totalCantidad;
  private BigDecimal totalCajasPallet;

  @Transient
  private String consecutivo;

  public BigInteger getTipoLote() {
    return tipoLote;
  }

  public void setTipoLote(BigInteger tipoLote) {
    this.tipoLote = tipoLote;
  }

  public String getDescripcionLote() {
    return descripcionLote;
  }

  public void setDescripcionLote(String descripcionLote) {
    this.descripcionLote = descripcionLote;
  }

  public BigDecimal getTotalPesoNeto() {
    return totalPesoNeto;
  }

  public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
    this.totalPesoNeto = totalPesoNeto;
  }

  public BigDecimal getTotalPesoBruto() {
    return totalPesoBruto;
  }

  public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
    this.totalPesoBruto = totalPesoBruto;
  }

  public BigDecimal getTotalCajas() {
    return totalCajas;
  }

  public void setTotalCajas(BigDecimal totalCajas) {
    this.totalCajas = totalCajas;
  }

  public BigDecimal getTotalCantidad() {
    return totalCantidad;
  }

  public void setTotalCantidad(BigDecimal totalCantidad) {
    this.totalCantidad = totalCantidad;
  }

  public BigDecimal getTotalCajasPallet() {
    return totalCajasPallet;
  }

  public void setTotalCajasPallet(BigDecimal totalCajasPallet) {
    this.totalCajasPallet = totalCajasPallet;
  }

  public String getConsecutivo() {
    return consecutivo;
  }

  public void setConsecutivo(String consecutivo) {
    this.consecutivo = consecutivo;
  }

}
