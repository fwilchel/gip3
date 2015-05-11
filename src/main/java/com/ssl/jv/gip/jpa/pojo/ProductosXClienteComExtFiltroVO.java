package com.ssl.jv.gip.jpa.pojo;

public class ProductosXClienteComExtFiltroVO {

  private Boolean activo;
  private String skuProducto;
  private String nombreCliente;
  private String estado;

  public String getSkuProducto() {
    return skuProducto;
  }

  public void setSkuProducto(String skuProducto) {
    this.skuProducto = skuProducto;
  }

  public String getNombreCliente() {
    return nombreCliente;
  }

  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activos) {
    this.activo = activos;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

}
