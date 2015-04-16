package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * The Class ProductoDTO.
 */
@Entity
public class ProductoODDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private BigInteger id;
  private String sku;
  private String nombre;
  private BigDecimal cantidad;
  private BigDecimal cantidadCajas;
  private BigDecimal cantidadPorEmbalaje;
  private BigDecimal muestrasFITOYANTICO;
  private BigDecimal muestrasCalidades;
  private BigInteger idCliente;
  @Transient
  private String descripcion;

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

  public BigDecimal getMuestrasFITOYANTICO() {
    return muestrasFITOYANTICO;
  }

  public void setMuestrasFITOYANTICO(BigDecimal muestrasFITOYANTICO) {
    this.muestrasFITOYANTICO = muestrasFITOYANTICO;
  }

  public BigDecimal getMuestrasCalidades() {
    return muestrasCalidades;
  }

  public void setMuestrasCalidades(BigDecimal muestrasCalidades) {
    this.muestrasCalidades = muestrasCalidades;
  }

  public BigInteger getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(BigInteger idCliente) {
    this.idCliente = idCliente;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion the descripcion to set
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
