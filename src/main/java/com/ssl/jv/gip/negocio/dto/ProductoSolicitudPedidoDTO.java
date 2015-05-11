package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;

/**
 * The Class ProductoGenerarFacturaPFDTO.
 */
@Entity
public class ProductoSolicitudPedidoDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private Long id; // OK
  private String sku;
  private String nombre;
  private BigDecimal cantidad; //OK
  private BigDecimal pesoNeto;
  private BigDecimal pesoBruto;
  private BigDecimal cantidadCajas;
  private BigDecimal cantidadPorEmbalaje;
  private BigInteger idCliente;
  private BigDecimal cajasPorPallets;
  private Long idUnidad;
  private String posicionArancelaria;
  private String unidad;
  private String nombreIngles;
  private String descripcion;
  private BigDecimal pesoNetoEmbalaje;
  private BigDecimal pesoBrutoEmbalaje;
  private BigDecimal saldoAnterior;
  private Boolean controlStock;
  private boolean seleccionado;
  private String observaciones;
  private boolean desactivado;
  private String estilo;
  private ProductosInventarioComext productoInventarioComext;
  private BigDecimal saldo;

  public ProductoSolicitudPedidoDTO() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public Long getIdUnidad() {
    return idUnidad;
  }

  public void setIdUnidad(Long idUnidad) {
    this.idUnidad = idUnidad;
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

  public Boolean getControlStock() {
    return controlStock;
  }

  public void setControlStock(Boolean controlStock) {
    this.controlStock = controlStock;
  }

  public boolean isSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(boolean seleccionado) {
    this.seleccionado = seleccionado;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public boolean isDesactivado() {
    return desactivado;
  }

  public void setDesactivado(boolean desactivado) {
    this.desactivado = desactivado;
  }

  public String getEstilo() {
    return estilo;
  }

  public void setEstilo(String estilo) {
    this.estilo = estilo;
  }

  public ProductosInventarioComext getProductoInventarioComext() {
    return productoInventarioComext;
  }

  public void setProductoInventarioComext(
      ProductosInventarioComext productoInventarioComext) {
    this.productoInventarioComext = productoInventarioComext;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }

  public BigDecimal getSaldoAnterior() {
    return saldoAnterior;
  }

  public void setSaldoAnterior(BigDecimal saldoAnterior) {
    this.saldoAnterior = saldoAnterior;
  }

}
