package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DocumentoLotesContribucionCafeteriaDTO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 6826470281110432159L;

  private Long idDocumento;

  private String descripcion;

  private String consecutivo;

  private BigDecimal contribucion;

  private BigDecimal dex;

  private Long tipoLoteId;

  public Long getIdDocumento() {
    return idDocumento;
  }

  public void setIdDocumento(Long idDocumento) {
    this.idDocumento = idDocumento;
  }

  public String getConsecutivo() {
    return consecutivo;
  }

  public void setConsecutivo(String consecutivo) {
    this.consecutivo = consecutivo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public BigDecimal getContribucion() {
    return contribucion;
  }

  public void setContribucion(BigDecimal contribucion) {
    this.contribucion = contribucion;
  }

  public BigDecimal getDex() {
    return dex;
  }

  public void setDex(BigDecimal dex) {
    this.dex = dex;
  }

  public Long getTipoLoteId() {
    return tipoLoteId;
  }

  public void setTipoLoteId(Long tipoLoteId) {
    this.tipoLoteId = tipoLoteId;
  }

}
