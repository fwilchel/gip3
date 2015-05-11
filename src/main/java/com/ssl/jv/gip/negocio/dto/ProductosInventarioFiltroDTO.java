package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

public class ProductosInventarioFiltroDTO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 316599998274005112L;

  private Long idCategoria;

  private String nombre;

  private Boolean estado;

  private String sku;

  private String idUsuario;

  private Boolean controlStock;

  public Long getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(Long idCategoria) {
    this.idCategoria = idCategoria;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public Boolean getControlStock() {
    return controlStock;
  }

  public void setControlStock(Boolean controlStock) {
    this.controlStock = controlStock;
  }

}
