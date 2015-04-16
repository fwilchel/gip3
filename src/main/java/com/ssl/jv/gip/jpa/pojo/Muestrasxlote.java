package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the muestrasxlote database table.
 *
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "Muestrasxlote.findAll", query = "SELECT m FROM Muestrasxlote m"),
  @NamedQuery(name = "Muestrasxlote.findByCantidad", query = "SELECT m FROM Muestrasxlote m where :cantidad >= rangoInicial and :cantidad1 <= rangoFinal")
})
public class Muestrasxlote implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private BigDecimal factor;

  private BigDecimal muestras;

  @Column(name = "rango_final")
  private Long rangoFinal;

  @Column(name = "rango_inicial")
  private Long rangoInicial;

  private String tipo;

  public Muestrasxlote() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getFactor() {
    return this.factor;
  }

  public void setFactor(BigDecimal factor) {
    this.factor = factor;
  }

  public BigDecimal getMuestras() {
    return this.muestras;
  }

  public void setMuestras(BigDecimal muestras) {
    this.muestras = muestras;
  }

  public Long getRangoFinal() {
    return this.rangoFinal;
  }

  public void setRangoFinal(Long rangoFinal) {
    this.rangoFinal = rangoFinal;
  }

  public Long getRangoInicial() {
    return this.rangoInicial;
  }

  public void setRangoInicial(Long rangoInicial) {
    this.rangoInicial = rangoInicial;
  }

  public String getTipo() {
    return this.tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

}
