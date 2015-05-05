package com.ssl.jv.gip.util;

public enum TipoItemCostoLogistico {

  NO_APLICA(0, "No aplica"),
  MULTIPLICA_CONTENEDORES_20(1, "Multiplica por contenedores de 20"),
  MULTIPLICA_CONTENEDORES_40(2, "Multiplica por contenedores de 40"),
  MULTIPLICA_PALLET(3, "Multiplica por Pallets"),
  MULTIPLICA_CATEGORIA(4, "Multiplica por Cantidad de Solicitudes"),
  MULTIPLICA_SAES(5, "Multiplica por Numero SAE (Cantidad lotes OIC)"),
  MULTIPLICA_FOB(6, "Porcentaje por el FOB"),
  POR_RANGOS(7, "Segun rangos de peso"),
  MULTIPLICA_PESO_BRUTO(8, "Multiplica por peso Bruto"),
  MULTIPLICA_PESO_NETO(9, "Multiplica por peso Neto"),
  MULTIPLICA_CONTENEDOR(10, "Multiplica por Cantidad contenedores"),
  VALOR_UNICO(11, "Valor único")
  
  /*, 
   A_PUERTO_NAL(11,"A puerto Nacional"), 
   A_PUERTO_INTERNAL(12,"Puerto Nal a Puerto Internacional")*/;

  private Integer id;
  private String descripcion;

  private TipoItemCostoLogistico(Integer id, String descripcion) {
    this.id = id;
    this.descripcion = descripcion;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
