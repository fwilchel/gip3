package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The persistent class for the liquidacion_documentos database table.
 *
 */
@Entity
@Table(name = "liquidacion_documentos")
@NamedQuery(name = "LiquidacionDocumento.findAll", query = "SELECT l FROM LiquidacionDocumento l")
public class LiquidacionDocumento implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "LIQUIDACION_DOCUMENTOS_ID_GENERATOR", sequenceName = "LIQUIDACION_DOCUMENTO_ID_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIQUIDACION_DOCUMENTOS_ID_GENERATOR")
  private Long id;

  @Column(name = "consecutivo_documento")
  private String consecutivoDocumento;

  private BigDecimal etiquetas;

  //bi-directional many-to-one association to LiquidacionCostoLogistico
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "liquidacion_id")
  private LiquidacionCostoLogistico liquidacionCostoLogistico;

  public LiquidacionDocumento() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getConsecutivoDocumento() {
    return this.consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public LiquidacionCostoLogistico getLiquidacionCostoLogistico() {
    return this.liquidacionCostoLogistico;
  }

  public void setLiquidacionCostoLogistico(LiquidacionCostoLogistico liquidacionCostoLogistico) {
    this.liquidacionCostoLogistico = liquidacionCostoLogistico;
  }

  public BigDecimal getEtiquetas() {
    return etiquetas;
  }

  public void setEtiquetas(BigDecimal etiquetas) {
    this.etiquetas = etiquetas;
  }

}
