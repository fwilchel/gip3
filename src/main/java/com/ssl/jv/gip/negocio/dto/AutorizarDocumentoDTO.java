package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

/**
 * The Class DocumentoIncontermDTO.
 */
public class AutorizarDocumentoDTO implements Serializable {

  /**
   * The Constant serialVersionUID.
   */
  private static final long serialVersionUID = 6826470281110432159L;

  /**
   * The id documento.
   */
  private Long idDocumento;

  /**
   * The consecutivo documento.
   */
  private String consecutivoDocumento;

  /**
   * The fecha generacion.
   */
  private Date fechaGeneracion;

  private boolean seleccionado;

  private String documentoCliente;

  public Long getIdDocumento() {
    return idDocumento;
  }

  public void setIdDocumento(Long idDocumento) {
    this.idDocumento = idDocumento;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public Date getFechaGeneracion() {
    return fechaGeneracion;
  }

  public void setFechaGeneracion(Date fechaGeneracion) {
    this.fechaGeneracion = fechaGeneracion;
  }

  public boolean isSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(boolean seleccionado) {
    this.seleccionado = seleccionado;
  }

  public String getDocumentoCliente() {
    return documentoCliente;
  }

  public void setDocumentoCliente(String documentoCliente) {
    this.documentoCliente = documentoCliente;
  }

}
