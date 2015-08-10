package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Title: InstruccionEmbarqueDTO</p>
 *
 * <p>
 * Description: </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Soft Studio Ltda.</p>
 *
 * @author Alejandro Poveda
 * @email diego.poveda@softstudio.co
 * @phone 3192594013
 * @version 1.0
 */
public class DetalleDocumentoDTO implements Serializable {

  private static final long serialVersionUID = 5772395465483898864L;

  public static final String DETALLE_DOCUMENTO = "";

  private Long id;
  private String consecutivoDocumento;
  private String sku;
  private String descripcionProducto;
  private BigDecimal cantidad;
  private String estadoDocumento;
  private String tipoDocumento;
  private Date fechaGeneracion;
  private String ubicacionDestino;
  private String ubicacionOrigen;
  private String observaciones;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the consecutivoDocumento
   */
  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  /**
   * @param consecutivoDocumento the consecutivoDocumento to set
   */
  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  /**
   * @return the sku
   */
  public String getSku() {
    return sku;
  }

  /**
   * @param sku the sku to set
   */
  public void setSku(String sku) {
    this.sku = sku;
  }

  /**
   * @return the descripcionProducto
   */
  public String getDescripcionProducto() {
    return descripcionProducto;
  }

  /**
   * @param descripcionProducto the descripcionProducto to set
   */
  public void setDescripcionProducto(String descripcionProducto) {
    this.descripcionProducto = descripcionProducto;
  }

  /**
   * @return the cantidad
   */
  public BigDecimal getCantidad() {
    return cantidad;
  }

  /**
   * @param cantidad the cantidad to set
   */
  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

  /**
   * @return the estadoDocumento
   */
  public String getEstadoDocumento() {
    return estadoDocumento;
  }

  /**
   * @param estadoDocumento the estadoDocumento to set
   */
  public void setEstadoDocumento(String estadoDocumento) {
    this.estadoDocumento = estadoDocumento;
  }

  /**
   * @return the tipoDocumento
   */
  public String getTipoDocumento() {
    return tipoDocumento;
  }

  /**
   * @param tipoDocumento the tipoDocumento to set
   */
  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  /**
   * @return the fechaGeneracion
   */
  public Date getFechaGeneracion() {
    return fechaGeneracion;
  }

  /**
   * @param fechaGeneracion the fechaGeneracion to set
   */
  public void setFechaGeneracion(Date fechaGeneracion) {
    this.fechaGeneracion = fechaGeneracion;
  }

  /**
   * @return the ubicacionDestino
   */
  public String getUbicacionDestino() {
    return ubicacionDestino;
  }

  /**
   * @param ubicacionDestino the ubicacionDestino to set
   */
  public void setUbicacionDestino(String ubicacionDestino) {
    this.ubicacionDestino = ubicacionDestino;
  }

  /**
   * @return the ubicacionOrigen
   */
  public String getUbicacionOrigen() {
    return ubicacionOrigen;
  }

  /**
   * @param ubicacionOrigen the ubicacionOrigen to set
   */
  public void setUbicacionOrigen(String ubicacionOrigen) {
    this.ubicacionOrigen = ubicacionOrigen;
  }

  /**
   * @return the observaciones
   */
  public String getObservaciones() {
    return observaciones;
  }

  /**
   * @param observaciones the observaciones to set
   */
  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
}
