package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.Date;

public class DatoContribucionCafeteraDTO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 6086879143813479457L;

  private Long idDocumento;

  private Long idDocumentoFX;

  private String consecutivo;

  private Date fechaGeneracion;

  public Long getIdDocumento() {
    return idDocumento;
  }

  public void setIdDocumento(Long idDocumento) {
    this.idDocumento = idDocumento;
  }

  public Long getIdDocumentoFX() {
    return idDocumentoFX;
  }

  public void setIdDocumentoFX(Long idDocumentoFX) {
    this.idDocumentoFX = idDocumentoFX;
  }

  public String getConsecutivo() {
    return consecutivo;
  }

  public void setConsecutivo(String consecutivo) {
    this.consecutivo = consecutivo;
  }

  public Date getFechaGeneracion() {
    return fechaGeneracion;
  }

  public void setFechaGeneracion(Date fechaGeneracion) {
    this.fechaGeneracion = fechaGeneracion;
  }

}
