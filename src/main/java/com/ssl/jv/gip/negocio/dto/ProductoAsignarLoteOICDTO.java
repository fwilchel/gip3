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
public class ProductoAsignarLoteOICDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private BigInteger id;
  private String sku;
  private String nombre;
  private BigDecimal cantidad;
  private BigDecimal pesoNeto;
  private BigDecimal pesoBruto;
  private BigDecimal cantidadCajas;
  private BigDecimal cantidadPorEmbalaje;
  private BigDecimal precio;
  private BigDecimal valorTotal;
  private BigDecimal totalPesoNeto;
  private BigDecimal totalPesoBruto;
  private BigInteger idCliente;
  private BigDecimal cajasPorPallets;
  private BigInteger idUnidad;
  private BigDecimal totalCajas;
  private BigDecimal totalCajasTendido;
  private BigDecimal totalCajasPallet;
  private String posicionArancelaria;
  private String unidad;
  private String nombreIngles;
  private String descripcion;
  private BigDecimal pesoNetoEmbalaje;
  private BigDecimal pesoBrutoEmbalaje;
  private BigInteger tipoLote;
  private String descripcionLote;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
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

  public BigDecimal getPesoNeto() {
    return pesoNeto;
  }

  public void setPesoNeto(BigDecimal pesoNeto) {
    this.pesoNeto = pesoNeto;
  }

  public BigDecimal getPesoBruto() {
    return pesoBruto;
  }

  public void setPesoBruto(BigDecimal pesoBruto) {
    this.pesoBruto = pesoBruto;
  }

  public BigDecimal getCantidadCajas() {
    return cantidadCajas;
  }

  public void setCantidadCajas(BigDecimal cantidadCajas) {
    this.cantidadCajas = cantidadCajas;
  }

  public BigDecimal getCantidadPorEmbalaje() {
    return cantidadPorEmbalaje;
  }

  public void setCantidadPorEmbalaje(BigDecimal cantidadPorEmbalaje) {
    this.cantidadPorEmbalaje = cantidadPorEmbalaje;
  }

  public BigDecimal getPrecio() {
    return precio;
  }

  public void setPrecio(BigDecimal precio) {
    this.precio = precio;
  }

  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
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

  public BigInteger getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(BigInteger idCliente) {
    this.idCliente = idCliente;
  }

  public BigDecimal getCajasPorPallets() {
    return cajasPorPallets;
  }

  public void setCajasPorPallets(BigDecimal cajasPorPallets) {
    this.cajasPorPallets = cajasPorPallets;
  }

  public BigInteger getIdUnidad() {
    return idUnidad;
  }

  public void setIdUnidad(BigInteger idUnidad) {
    this.idUnidad = idUnidad;
  }

  public BigDecimal getTotalCajas() {
    return totalCajas;
  }

  public void setTotalCajas(BigDecimal totalCajas) {
    this.totalCajas = totalCajas;
  }

  public BigDecimal getTotalCajasTendido() {
    return totalCajasTendido;
  }

  public void setTotalCajasTendido(BigDecimal totalCajasTendido) {
    this.totalCajasTendido = totalCajasTendido;
  }

  public BigDecimal getTotalCajasPallet() {
    return totalCajasPallet;
  }

  public void setTotalCajasPallet(BigDecimal totalCajasPallet) {
    this.totalCajasPallet = totalCajasPallet;
  }

  public String getPosicionArancelaria() {
    return posicionArancelaria;
  }

  public void setPosicionArancelaria(String posicionArancelaria) {
    this.posicionArancelaria = posicionArancelaria;
  }

  public String getUnidad() {
    return unidad;
  }

  public void setUnidad(String unidad) {
    this.unidad = unidad;
  }

  public String getNombreIngles() {
    return nombreIngles;
  }

  public void setNombreIngles(String nombreIngles) {
    this.nombreIngles = nombreIngles;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public BigDecimal getPesoNetoEmbalaje() {
    return pesoNetoEmbalaje;
  }

  public void setPesoNetoEmbalaje(BigDecimal pesoNetoEmbalaje) {
    this.pesoNetoEmbalaje = pesoNetoEmbalaje;
  }

  public BigDecimal getPesoBrutoEmbalaje() {
    return pesoBrutoEmbalaje;
  }

  public void setPesoBrutoEmbalaje(BigDecimal pesoBrutoEmbalaje) {
    this.pesoBrutoEmbalaje = pesoBrutoEmbalaje;
  }

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

}
