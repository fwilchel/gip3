package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

public class ReporteReimprimirFacturaDTO implements Serializable, Comparable<Object> {

  /**
   *
   */
  private static final long serialVersionUID = -1999263322261445729L;

  private String productoInventarioNombre;

  private Double cantidad1;

  private Double totalPesoNetoItem;

  private String productoInventarioSku;

  private Double valorTotal;

  private Double precioUSD;

  private String posicionArancelaria;

  private String unidadNombre;

  private String tipoLoteOICDesc;

  private String docxLoteOICConsec;

  private Double valorUnitarioUSD;

  private Double totalCajasPallet;

  public String getProductoInventarioNombre() {
    return productoInventarioNombre;
  }

  public void setProductoInventarioNombre(String productoInventarioNombre) {
    this.productoInventarioNombre = productoInventarioNombre;
  }

  public Double getCantidad1() {
    return cantidad1;
  }

  public void setCantidad1(Double cantidad1) {
    this.cantidad1 = cantidad1;
  }

  public Double getTotalPesoNetoItem() {
    return totalPesoNetoItem;
  }

  public void setTotalPesoNetoItem(Double totalPesoNetoItem) {
    this.totalPesoNetoItem = totalPesoNetoItem;
  }

  public String getProductoInventarioSku() {
    return productoInventarioSku;
  }

  public void setProductoInventarioSku(String productoInventarioSku) {
    this.productoInventarioSku = productoInventarioSku;
  }

  public Double getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(Double valorTotal) {
    this.valorTotal = valorTotal;
  }

  public Double getPrecioUSD() {
    return precioUSD;
  }

  public void setPrecioUSD(Double precioUSD) {
    this.precioUSD = precioUSD;
  }

  public String getPosicionArancelaria() {
    return posicionArancelaria;
  }

  public void setPosicionArancelaria(String posicionArancelaria) {
    this.posicionArancelaria = posicionArancelaria;
  }

  public String getUnidadNombre() {
    return unidadNombre;
  }

  public void setUnidadNombre(String unidadNombre) {
    this.unidadNombre = unidadNombre;
  }

  public String getTipoLoteOICDesc() {
    return tipoLoteOICDesc;
  }

  public void setTipoLoteOICDesc(String tipoLoteOICDesc) {
    this.tipoLoteOICDesc = tipoLoteOICDesc;
  }

  public String getDocxLoteOICConsec() {
    return docxLoteOICConsec;
  }

  public void setDocxLoteOICConsec(String docxLoteOICConsec) {
    this.docxLoteOICConsec = docxLoteOICConsec;
  }

  public Double getValorUnitarioUSD() {
    return valorUnitarioUSD;
  }

  public void setValorUnitarioUSD(Double valorUnitarioUSD) {
    this.valorUnitarioUSD = valorUnitarioUSD;
  }

  public Double getTotalCajasPallet() {
    return totalCajasPallet;
  }

  public void setTotalCajasPallet(Double totalCajasPallet) {
    this.totalCajasPallet = totalCajasPallet;
  }

  @Override
  public int compareTo(Object o) {
    if (o instanceof ReporteReimprimirFacturaDTO) {
      ReporteReimprimirFacturaDTO p = (ReporteReimprimirFacturaDTO) o;
      return this.docxLoteOICConsec.compareTo(p.getDocxLoteOICConsec());
    }
    return 0;
  }
}
