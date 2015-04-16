package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

/**
 * The Class ListaEmpaqueDTO.
 */
public class UbicacionDTO implements Serializable {

  private static final long serialVersionUID = 6826470281110432159L;

  private String id;
  private String nombre;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

}
