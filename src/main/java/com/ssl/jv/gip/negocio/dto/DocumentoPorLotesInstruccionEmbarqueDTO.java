package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;

public class DocumentoPorLotesInstruccionEmbarqueDTO implements Serializable {

  private static final long serialVersionUID = 5772395465483898864L;

  private Long idDocumento;

  private String consecutivo;

  private Long idTipoLote;

  private Double totalCajas;

  private String pedido;

  private String asignacion;

  private String aviso;

  private String descripcion;

  private Double totalCantidad;

  private String consecutivoDocumento;

  private String observacionDocumento;

  private String facturaProforma;

  private Date fechaEta;

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

  public Long getIdTipoLote() {
    return idTipoLote;
  }

  public void setIdTipoLote(Long idTipoLote) {
    this.idTipoLote = idTipoLote;
  }

  public Double getTotalCajas() {
    return totalCajas;
  }

  public void setTotalCajas(Double totalCajas) {
    this.totalCajas = totalCajas;
  }

  public String getPedido() {
    return pedido;
  }

  public void setPedido(String pedido) {
    this.pedido = pedido;
  }

  public String getAsignacion() {
    return asignacion;
  }

  public void setAsignacion(String asignacion) {
    this.asignacion = asignacion;
  }

  public String getAviso() {
    return aviso;
  }

  public void setAviso(String aviso) {
    this.aviso = aviso;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Double getTotalCantidad() {
    return totalCantidad;
  }

  public void setTotalCantidad(Double totalCantidad) {
    this.totalCantidad = totalCantidad;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public String getObservacionDocumento() {
    return observacionDocumento;
  }

  public void setObservacionDocumento(String observacionDocumento) {
    this.observacionDocumento = observacionDocumento;
  }

  public String getFacturaProforma() {
    return facturaProforma;
  }

  public void setFacturaProforma(String facturaProforma) {
    this.facturaProforma = facturaProforma;
  }

  public Date getFechaEta() {
    return fechaEta;
  }

  public void setFechaEta(Date fechaEta) {
    this.fechaEta = fechaEta;
  }

}
