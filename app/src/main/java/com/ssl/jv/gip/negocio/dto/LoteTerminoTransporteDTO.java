package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * <p>
 * Title: LoteTerminoTransporteDTO</p>
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
 * @author Diego Poveda
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
@Entity
public class LoteTerminoTransporteDTO implements Serializable {

  public static final String LISTADO_LOTES_TERMINO_TRASPORTE_TIPO_SOLICITUD_CAFE = "SELECT row_number() OVER () AS id, documento_x_lotesoic.id_documento AS idDocumento, documento_x_lotesoic.consecutivo AS consecutivoLote, documento_x_lotesoic.id_tipo_lote AS tipoLote, documento_x_lotesoic.total_cajas AS totalCajas, documento_x_lotesoic.pedido AS pedido, documento_x_lotesoic.asignacion AS asignacion, documento_x_lotesoic.aviso AS aviso, tipo_loteoic.descripcion AS descripcion, documento_x_lotesoic.total_cantidad AS cantidadTotal, (select consecutivo_documento from documentos doc2 where observacion_documento = (select doc.consecutivo_documento from documentos doc where doc.observacion_documento = documentos.consecutivo_documento and doc.id_tipo_documento = 24) and doc2.id_estado <> 11 order by doc2.consecutivo_documento desc limit 1) as consecutivoDocumento, (select doc.consecutivo_documento from documentos doc where doc.observacion_documento = documentos.consecutivo_documento and doc.id_tipo_documento = 24 and doc.id_estado <> 11 limit 1) as observacionDocumento, documentos.consecutivo_documento AS sitioEntrega,(select fecha_eta from documentos doc2 where observacion_documento = (select doc.consecutivo_documento from documentos doc where doc.observacion_documento = documentos.consecutivo_documento and doc.id_tipo_documento = 24) and doc2.id_estado <> 11 order by doc2.consecutivo_documento desc limit 1) AS fechaEta FROM documento_x_lotesoic INNER JOIN tipo_loteoic on documento_x_lotesoic.id_tipo_lote = tipo_loteoic.id INNER JOIN documentos on documento_x_lotesoic.id_documento = documentos.id WHERE documento_x_lotesoic.id_documento IN (select documentos.id from documentos where documentos.consecutivo_documento IN (select documentos.observacion_documento from documentos where documentos.consecutivo_documento IN (select documentos.observacion_documento from documentos where documentos.id IN (:documento)))) ";
  public static final String UNION_ALL = "UNION_ALL ";
  public static final String LISTADO_LOTES_TERMINO_TRASPORTE_DOCUMENTO_TIPO_MERCADEO = "SELECT row_number() OVER () AS id, productosXdocumentos.id_documento AS idDocumento, 'MERCADEO' AS consecutivoLote, pic.id_tipo_loteoic AS tipoLote, SUM((CASE WHEN (pic.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pic.cantidad_x_embalaje) END)) AS totalCajas, 'MERCADEO' AS pedido, 'MERCADEO' AS asignacion, 'MERCADEO' AS aviso, tipo_loteoic.descripcion AS descripcion, SUM(productosXdocumentos.cantidad1) AS cantidadTotal, docs.consecutivo_documento AS consecutivoDocumento, docs.observacion_documento AS observacionDocumento, (select observacion_documento from documentos where consecutivo_documento = docs.observacion_documento) AS sitioEntrega, (select fecha_eta from documentos where consecutivo_documento = docs.consecutivo_documento) AS fechaEta FROM productosXdocumentos LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id LEFT JOIN productos_inventario_comext pic ON pic.id_producto=productos_inventario.id LEFT JOIN tipo_loteoic ON tipo_loteoic.id = pic.id_tipo_loteoic INNER JOIN unidades ON productos_inventario.id_uv=unidades.id INNER JOIN documentos docs ON docs.id = productosXdocumentos.id_documento WHERE productosXdocumentos.id_documento IN (:documentosMercadeo) GROUP BY pic.id_tipo_loteoic, tipo_loteoic.descripcion, productosXdocumentos.id_documento, docs.consecutivo_documento, docs.observacion_documento ";
  public static final String ORDER_BY = "ORDER BY idDocumento, consecutivoLote";

  @Id
  private Long id;
  private Long idDocumento;
  private String consecutivoLote;
  private Long tipoLote;
  private BigDecimal totalCajas;
  private String pedido;
  private String asignacion;
  private String aviso;
  private String descripcion;
  private BigDecimal cantidadTotal;
  private String consecutivoDocumento;
  private String observacionDocumento;
  private String sitioEntrega;
  @Temporal(javax.persistence.TemporalType.TIMESTAMP)
  private Date fechaEta;

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
   * @return the idDocumento
   */
  public Long getIdDocumento() {
    return idDocumento;
  }

  /**
   * @param idDocumento the idDocumento to set
   */
  public void setIdDocumento(Long idDocumento) {
    this.idDocumento = idDocumento;
  }

  /**
   * @return the consecutivoLote
   */
  public String getConsecutivoLote() {
    return consecutivoLote;
  }

  /**
   * @param consecutivoLote the consecutivoLote to set
   */
  public void setConsecutivoLote(String consecutivoLote) {
    this.consecutivoLote = consecutivoLote;
  }

  /**
   * @return the tipoLote
   */
  public Long getTipoLote() {
    return tipoLote;
  }

  /**
   * @param tipoLote the tipoLote to set
   */
  public void setTipoLote(Long tipoLote) {
    this.tipoLote = tipoLote;
  }

  /**
   * @return the totalCajas
   */
  public BigDecimal getTotalCajas() {
    return totalCajas;
  }

  /**
   * @param totalCajas the totalCajas to set
   */
  public void setTotalCajas(BigDecimal totalCajas) {
    this.totalCajas = totalCajas;
  }

  /**
   * @return the pedido
   */
  public String getPedido() {
    return pedido;
  }

  /**
   * @param pedido the pedido to set
   */
  public void setPedido(String pedido) {
    this.pedido = pedido;
  }

  /**
   * @return the asignacion
   */
  public String getAsignacion() {
    return asignacion;
  }

  /**
   * @param asignacion the asignacion to set
   */
  public void setAsignacion(String asignacion) {
    this.asignacion = asignacion;
  }

  /**
   * @return the aviso
   */
  public String getAviso() {
    return aviso;
  }

  /**
   * @param aviso the aviso to set
   */
  public void setAviso(String aviso) {
    this.aviso = aviso;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion the descripcion to set
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  /**
   * @return the cantidadTotal
   */
  public BigDecimal getCantidadTotal() {
    return cantidadTotal;
  }

  /**
   * @param cantidadTotal the cantidadTotal to set
   */
  public void setCantidadTotal(BigDecimal cantidadTotal) {
    this.cantidadTotal = cantidadTotal;
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
   * @return the observacionDocumento
   */
  public String getObservacionDocumento() {
    return observacionDocumento;
  }

  /**
   * @param observacionDocumento the observacionDocumento to set
   */
  public void setObservacionDocumento(String observacionDocumento) {
    this.observacionDocumento = observacionDocumento;
  }

  /**
   * @return the sitioEntrega
   */
  public String getSitioEntrega() {
    return sitioEntrega;
  }

  /**
   * @param sitioEntrega the sitioEntrega to set
   */
  public void setSitioEntrega(String sitioEntrega) {
    this.sitioEntrega = sitioEntrega;
  }

  /**
   * @return the fechaEta
   */
  public Date getFechaEta() {
    return fechaEta;
  }

  /**
   * @param fechaEta the fechaEta to set
   */
  public void setFechaEta(Date fechaEta) {
    this.fechaEta = fechaEta;
  }

}
