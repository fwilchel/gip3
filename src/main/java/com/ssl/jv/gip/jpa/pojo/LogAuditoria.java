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
@NamedQueries({
  @NamedQuery(name = LogAuditoria.FIND_ALL, query = "SELECT la FROM LogAuditoria la"),
  @NamedQuery(name = LogAuditoria.FIND_BY_PARAMETROS, query = "SELECT la FROM LogAuditoria la WHERE UPPER(la.usuario.nombre) LIKE UPPER(:nombreUsuario) AND UPPER(la.funcionalidad.nombre) LIKE UPPER(:nombreFuncionalidad) ORDER BY la.fecha DESC")
})
public class LogAuditoria implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "LogAuditoria.findAll";
  public static final String FIND_BY_PARAMETROS = "LogAuditoria.findByParametros";

  @Id
  @Column(name = "id_log")
  @SequenceGenerator(name = "logAuditoriaSeq", sequenceName = "log_auditoria_id_seq", allocationSize = 1)
  @GeneratedValue(generator = "logAuditoriaSeq", strategy = GenerationType.SEQUENCE)
  private Long idLog;
  private String accion;
  private String campo;
  private Timestamp fecha;
  @Column(name = "id_reg_tabla")
  private Long idRegTabla;
  private String tabla;
  @Column(name = "valor_anterior")
  private String valorAnterior;
  @Column(name = "valor_nuevo")
  private String valorNuevo;
  @Column(name = "id_usuario")
  private String idUsuario;
  @ManyToOne(optional = false)
  @JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
  private Usuario usuario;
  @Column(name = "id_funcionalidad")
  private Long idFuncionalidad;
  @ManyToOne(optional = false)
  @JoinColumn(name = "id_funcionalidad", referencedColumnName = "id", insertable = false, updatable = false)
  private Funcionalidad funcionalidad;

  public LogAuditoria() {
  }

  /**
   * @return the idLog
   */
  public Long getIdLog() {
    return idLog;
  }

  /**
   * @param idLog the idLog to set
   */
  public void setIdLog(Long idLog) {
    this.idLog = idLog;
  }

  /**
   * @return the accion
   */
  public String getAccion() {
    return accion;
  }

  /**
   * @param accion the accion to set
   */
  public void setAccion(String accion) {
    this.accion = accion;
  }

  /**
   * @return the campo
   */
  public String getCampo() {
    return campo;
  }

  /**
   * @param campo the campo to set
   */
  public void setCampo(String campo) {
    this.campo = campo;
  }

  /**
   * @return the fecha
   */
  public Timestamp getFecha() {
    return fecha;
  }

  /**
   * @param fecha the fecha to set
   */
  public void setFecha(Timestamp fecha) {
    this.fecha = fecha;
  }

  /**
   * @return the idRegTabla
   */
  public Long getIdRegTabla() {
    return idRegTabla;
  }

  /**
   * @param idRegTabla the idRegTabla to set
   */
  public void setIdRegTabla(Long idRegTabla) {
    this.idRegTabla = idRegTabla;
  }

  /**
   * @return the tabla
   */
  public String getTabla() {
    return tabla;
  }

  /**
   * @param tabla the tabla to set
   */
  public void setTabla(String tabla) {
    this.tabla = tabla;
  }

  /**
   * @return the valorAnterior
   */
  public String getValorAnterior() {
    return valorAnterior;
  }

  /**
   * @param valorAnterior the valorAnterior to set
   */
  public void setValorAnterior(String valorAnterior) {
    this.valorAnterior = valorAnterior;
  }

  /**
   * @return the valorNuevo
   */
  public String getValorNuevo() {
    return valorNuevo;
  }

  /**
   * @param valorNuevo the valorNuevo to set
   */
  public void setValorNuevo(String valorNuevo) {
    this.valorNuevo = valorNuevo;
  }

  /**
   * @return the idUsuario
   */
  public String getIdUsuario() {
    return idUsuario;
  }

  /**
   * @param idUsuario the idUsuario to set
   */
  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  /**
   * @return the usuario
   */
  public Usuario getUsuario() {
    return usuario;
  }

  /**
   * @param usuario the usuario to set
   */
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  /**
   * @return the idFuncionalidad
   */
  public Long getIdFuncionalidad() {
    return idFuncionalidad;
  }

  /**
   * @param idFuncionalidad the idFuncionalidad to set
   */
  public void setIdFuncionalidad(Long idFuncionalidad) {
    this.idFuncionalidad = idFuncionalidad;
  }

  /**
   * @return the funcionalidad
   */
  public Funcionalidad getFuncionalidad() {
    return funcionalidad;
  }

  /**
   * @param funcionalidad the funcionalidad to set
   */
  public void setFuncionalidad(Funcionalidad funcionalidad) {
    this.funcionalidad = funcionalidad;
  }

}
