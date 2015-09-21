package com.ssl.jv.gip.util;

public enum TipoMovimiento {

  ENTRADA(1L, "E"), SALIDA(2L, "S");

  private final Long codigo;
  private final String nombre;

  private TipoMovimiento(Long codigo, String nombre) {
    this.codigo = codigo;
    this.nombre = nombre;
  }

  public Long getCodigo() {
    return codigo;
  }

  public String getNombre() {
    return nombre;
  }

}
