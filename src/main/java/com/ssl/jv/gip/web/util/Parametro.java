package com.ssl.jv.gip.web.util;

public enum Parametro {

  CONTRASENA_DEFECTO(1L, "Contraseña defecto"), DEBUG(2L, "DEBUG"), AMBIENTE(
      3L, "Ambiente"), VERSION(4L, "Versión"), PATH_SERVIDOR(5L,
          "pathServidor"), PATH_FOTOS(6L, "pathFotos");

  private Long id;
  private String nombre;

  private Parametro(Long id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

}
