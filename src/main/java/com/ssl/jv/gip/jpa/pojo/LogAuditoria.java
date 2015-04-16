package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the log_auditoria database table.
 *
 */
@Entity
@Table(name = "log_auditoria")
@NamedQuery(name = "LogAuditoria.findAll", query = "SELECT l FROM LogAuditoria l")
public class LogAuditoria implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id_log")
  @SequenceGenerator(name = "logAuditoriaSeq", sequenceName = "log_auditoria_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "logAuditoriaSeq", strategy = GenerationType.SEQUENCE)
  private Long idLog;

  private String accion;

  private String campo;

  private Timestamp fecha;

  @Column(name = "id_funcionalidad")
  private Long idFuncionalidad;

  @Column(name = "id_reg_tabla")
  private Long idRegTabla;

  @Column(name = "id_usuario")
  private String idUsuario;

  private String tabla;

  @Column(name = "valor_anterior")
  private String valorAnterior;

  @Column(name = "valor_nuevo")
  private String valorNuevo;

  public LogAuditoria() {
  }

  public Long getIdLog() {
    return this.idLog;
  }

  public void setIdLog(Long idLog) {
    this.idLog = idLog;
  }

  public String getAccion() {
    return this.accion;
  }

  public void setAccion(String accion) {
    this.accion = accion;
  }

  public String getCampo() {
    return this.campo;
  }

  public void setCampo(String campo) {
    this.campo = campo;
  }

  public Timestamp getFecha() {
    return this.fecha;
  }

  public void setFecha(Timestamp fecha) {
    this.fecha = fecha;
  }

  public Long getIdFuncionalidad() {
    return this.idFuncionalidad;
  }

  public void setIdFuncionalidad(Long idFuncionalidad) {
    this.idFuncionalidad = idFuncionalidad;
  }

  public Long getIdRegTabla() {
    return this.idRegTabla;
  }

  public void setIdRegTabla(Long idRegTabla) {
    this.idRegTabla = idRegTabla;
  }

  public String getIdUsuario() {
    return this.idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getTabla() {
    return this.tabla;
  }

  public void setTabla(String tabla) {
    this.tabla = tabla;
  }

  public String getValorAnterior() {
    return this.valorAnterior;
  }

  public void setValorAnterior(String valorAnterior) {
    this.valorAnterior = valorAnterior;
  }

  public String getValorNuevo() {
    return this.valorNuevo;
  }

  public void setValorNuevo(String valorNuevo) {
    this.valorNuevo = valorNuevo;
  }

}
