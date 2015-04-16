package com.ssl.jv.gip.negocio.dto;

public class ProductosInventarioComextFiltroVO {

  private String nombreProducto;
  private String skuProducto;
  private Long idCategoria;

  public String getNombreProducto() {
    return nombreProducto;
  }

  public void setNombreProducto(String nombreProducto) {
    this.nombreProducto = nombreProducto;
  }

  public String getSkuProducto() {
    return skuProducto;
  }

  public void setSkuProducto(String skuProducto) {
    this.skuProducto = skuProducto;
  }

  public Long getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(Long idCategoria) {
    this.idCategoria = idCategoria;
  }

}
