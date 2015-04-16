package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the comext_formato_novedades database table.
 *
 */
@Entity
@Table(name = "comext_formato_novedades")
@NamedQuery(name = "ComextFormatoNovedade.findAll", query = "SELECT c FROM ComextFormatoNovedade c")
public class ComextFormatoNovedade implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "consecutivo_documento")
  private Long consecutivoDocumento;

  @Column(name = "distribucion_optima")
  private Boolean distribucionOptima;

  @Column(name = "documentos_solicitados")
  private Boolean documentosSolicitados;

  private Timestamp fechacreacion;

  private Timestamp fechadiligencia;

  private Boolean formatodiligenciado;

  @Id
  private Long id;

  private String obsdistoptima;

  private String obsdocsolicitados;

  private String obsprodbuenestado;

  private String obsprodcompleto;

  private String obsprodtiempo;

  @Column(name = "producto_completo")
  private Boolean productoCompleto;

  @Column(name = "productos_buen_estado")
  private Boolean productosBuenEstado;

  @Column(name = "productos_tiempo")
  private Boolean productosTiempo;

  @Column(name = "user_id")
  private Long userId;

  public ComextFormatoNovedade() {
  }

  public Long getConsecutivoDocumento() {
    return this.consecutivoDocumento;
  }

  public void setConsecutivoDocumento(Long consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public Boolean getDistribucionOptima() {
    return this.distribucionOptima;
  }

  public void setDistribucionOptima(Boolean distribucionOptima) {
    this.distribucionOptima = distribucionOptima;
  }

  public Boolean getDocumentosSolicitados() {
    return this.documentosSolicitados;
  }

  public void setDocumentosSolicitados(Boolean documentosSolicitados) {
    this.documentosSolicitados = documentosSolicitados;
  }

  public Timestamp getFechacreacion() {
    return this.fechacreacion;
  }

  public void setFechacreacion(Timestamp fechacreacion) {
    this.fechacreacion = fechacreacion;
  }

  public Timestamp getFechadiligencia() {
    return this.fechadiligencia;
  }

  public void setFechadiligencia(Timestamp fechadiligencia) {
    this.fechadiligencia = fechadiligencia;
  }

  public Boolean getFormatodiligenciado() {
    return this.formatodiligenciado;
  }

  public void setFormatodiligenciado(Boolean formatodiligenciado) {
    this.formatodiligenciado = formatodiligenciado;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getObsdistoptima() {
    return this.obsdistoptima;
  }

  public void setObsdistoptima(String obsdistoptima) {
    this.obsdistoptima = obsdistoptima;
  }

  public String getObsdocsolicitados() {
    return this.obsdocsolicitados;
  }

  public void setObsdocsolicitados(String obsdocsolicitados) {
    this.obsdocsolicitados = obsdocsolicitados;
  }

  public String getObsprodbuenestado() {
    return this.obsprodbuenestado;
  }

  public void setObsprodbuenestado(String obsprodbuenestado) {
    this.obsprodbuenestado = obsprodbuenestado;
  }

  public String getObsprodcompleto() {
    return this.obsprodcompleto;
  }

  public void setObsprodcompleto(String obsprodcompleto) {
    this.obsprodcompleto = obsprodcompleto;
  }

  public String getObsprodtiempo() {
    return this.obsprodtiempo;
  }

  public void setObsprodtiempo(String obsprodtiempo) {
    this.obsprodtiempo = obsprodtiempo;
  }

  public Boolean getProductoCompleto() {
    return this.productoCompleto;
  }

  public void setProductoCompleto(Boolean productoCompleto) {
    this.productoCompleto = productoCompleto;
  }

  public Boolean getProductosBuenEstado() {
    return this.productosBuenEstado;
  }

  public void setProductosBuenEstado(Boolean productosBuenEstado) {
    this.productosBuenEstado = productosBuenEstado;
  }

  public Boolean getProductosTiempo() {
    return this.productosTiempo;
  }

  public void setProductosTiempo(Boolean productosTiempo) {
    this.productosTiempo = productosTiempo;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

}
