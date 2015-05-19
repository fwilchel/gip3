package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the historial_contrasena database table.
 *
 */
@Entity
@Table(name = "historial_contrasena")

@NamedQueries({
  @NamedQuery(name = "HistorialContrasena.findAll", query = "SELECT h FROM HistorialContrasena h"),
  @NamedQuery(name = "HistorialContrasena.findByUsuarioPwdHoy", query = "SELECT h FROM HistorialContrasena h WHERE h.pk.usuarioId= :id AND h.pk.contrasena= :contrasena AND h.fechaCaducidad>= :hoy "),
  @NamedQuery(name = "HistorialContrasena.findByUsuarioPwd", query = "SELECT h FROM HistorialContrasena h WHERE h.pk.usuarioId= :id AND h.pk.contrasena= :contrasena"),
  @NamedQuery(name = "HistorialContrasena.findByUsuarioId", query = "SELECT h FROM HistorialContrasena h WHERE h.pk.usuarioId= :id")
})

public class HistorialContrasena implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "fecha_caducidad")
  private Timestamp fechaCaducidad;

  @EmbeddedId
  private HistorialContrasenaPK pk;

  public HistorialContrasena() {
  }

  public HistorialContrasenaPK getPk() {
    return pk;
  }

  public void setPk(HistorialContrasenaPK pk) {
    this.pk = pk;
  }

  public Timestamp getFechaCaducidad() {
    return this.fechaCaducidad;
  }

  public void setFechaCaducidad(Timestamp fechaCaducidad) {
    this.fechaCaducidad = fechaCaducidad;
  }

}
