package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductoDevolucionDTO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -4215474239289218901L;

  private Long id;

  private String sku;

  private String nombre;

  private Long categoriasInventarioId;

  private String categoriasInventarioNombre;

  private double cantidadDevolver = 0.0;

  private String observacion;

  private String unidadNombre;

  private Long unidadId;

  private boolean incluido;

  public ProductoDevolucionDTO(Long id, String sku, String nombre,
      Long categoriasInventarioId, String categoriasInventarioNombre, String unidadNombre, Long unidadId) {
    super();
    this.id = id;
    this.sku = sku;
    this.nombre = nombre;
    this.categoriasInventarioId = categoriasInventarioId;
    this.categoriasInventarioNombre = categoriasInventarioNombre;
    this.unidadNombre = unidadNombre;
    this.unidadId = unidadId;
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

  public Long getCategoriasInventarioId() {
    return categoriasInventarioId;
  }

  public void setCategoriasInventarioId(Long categoriasInventarioId) {
    this.categoriasInventarioId = categoriasInventarioId;
  }

  public String getCategoriasInventarioNombre() {
    return categoriasInventarioNombre;
  }

  public void setCategoriasInventarioNombre(String categoriasInventarioNombre) {
    this.categoriasInventarioNombre = categoriasInventarioNombre;
  }

  public boolean isIncluido() {
    return incluido;
  }

  public void setIncluido(boolean incluido) {
    this.incluido = incluido;
  }

  public double getCantidadDevolver() {
    return cantidadDevolver;
  }

  public void setCantidadDevolver(double cantidadDevolver) {
    this.cantidadDevolver = cantidadDevolver;
  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public String getUnidadNombre() {
    return unidadNombre;
  }

  public void setUnidadNombre(String unidadNombre) {
    this.unidadNombre = unidadNombre;
  }

  public Long getUnidadId() {
    return unidadId;
  }

  public void setUnidadId(Long unidadId) {
    this.unidadId = unidadId;
  }

}
