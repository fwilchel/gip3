package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <p>
 * Title: GIP</p>
 *
 * <p>
 * Description: GIP</p>
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
public class DocumentoCintaTestigoMagneticaDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static final String REPORTE_CINTA_TESTIGO_MAGNETICA = "SELECT doc.id AS id, doc.fecha_generacion AS fechaGeneracion, doc.consecutivo_documento AS consecutivo, CASE WHEN doc.id_estado=11 THEN 'ANULADA' ELSE cli.nombre END AS clienteNombre, CASE WHEN doc.id_estado=11 OR doc.subtotal IS NULL THEN 0 ELSE doc.subtotal END AS subtotal, CASE WHEN doc.id_estado=11 OR doc.descuento IS NULL THEN 0 ELSE doc.descuento END AS descuento, CASE WHEN doc.id_estado=11 OR (doc.valor_iva5 IS NULL AND doc.valor_iva16 IS NULL) THEN 0 ELSE doc.valor_iva5 + doc.valor_iva16 END AS valorImpuestos, CASE WHEN doc.id_estado=11 OR doc.valor_total IS NULL THEN 0 ELSE doc.valor_total END AS valorTotal, CASE WHEN doc.id_estado=11 OR doc.valor_iva5 IS NULL THEN 0 ELSE doc.valor_iva5 END AS valorIVA10, CASE WHEN doc.id_estado=11 OR doc.valor_iva16 IS NULL THEN 0 ELSE doc.valor_iva16 END AS valorIVA16 "
          + "FROM documentos AS doc INNER JOIN clientes AS cli ON doc.id_cliente = cli.id "
          + "WHERE doc.fecha_generacion BETWEEN :fechaInicial AND :fechaFinal AND doc.id_tipo_documento = :tipoDocumento "
          + "ORDER BY doc.fecha_generacion ";

  @Id
  private Long id;
  private Timestamp fechaGeneracion;
  private String consecutivo;
  private String clienteNombre;
  private Double subtotal;
  private Double descuento;
  private Double valorImpuestos;
  private Double valorTotal;
  private Double valorIVA10;
  private Double valorIVA16;

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
   * @return the fechaGeneracion
   */
  public Timestamp getFechaGeneracion() {
    return fechaGeneracion;
  }

  /**
   * @param fechaGeneracion the fechaGeneracion to set
   */
  public void setFechaGeneracion(Timestamp fechaGeneracion) {
    this.fechaGeneracion = fechaGeneracion;
  }

  /**
   * @return the consecutivo
   */
  public String getConsecutivo() {
    return consecutivo;
  }

  /**
   * @param consecutivo the consecutivo to set
   */
  public void setConsecutivo(String consecutivo) {
    this.consecutivo = consecutivo;
  }

  /**
   * @return the clienteNombre
   */
  public String getClienteNombre() {
    return clienteNombre;
  }

  /**
   * @param clienteNombre the clienteNombre to set
   */
  public void setClienteNombre(String clienteNombre) {
    this.clienteNombre = clienteNombre;
  }

  /**
   * @return the subtotal
   */
  public Double getSubtotal() {
    return subtotal;
  }

  /**
   * @param subtotal the subtotal to set
   */
  public void setSubtotal(Double subtotal) {
    this.subtotal = subtotal;
  }

  /**
   * @return the descuento
   */
  public Double getDescuento() {
    return descuento;
  }

  /**
   * @param descuento the descuento to set
   */
  public void setDescuento(Double descuento) {
    this.descuento = descuento;
  }

  /**
   * @return the valorImpuestos
   */
  public Double getValorImpuestos() {
    return valorImpuestos;
  }

  /**
   * @param valorImpuestos the valorImpuestos to set
   */
  public void setValorImpuestos(Double valorImpuestos) {
    this.valorImpuestos = valorImpuestos;
  }

  /**
   * @return the valorTotal
   */
  public Double getValorTotal() {
    return valorTotal;
  }

  /**
   * @param valorTotal the valorTotal to set
   */
  public void setValorTotal(Double valorTotal) {
    this.valorTotal = valorTotal;
  }

  /**
   * @return the valorIVA10
   */
  public Double getValorIVA10() {
    return valorIVA10;
  }

  /**
   * @param valorIVA10 the valorIVA10 to set
   */
  public void setValorIVA10(Double valorIVA10) {
    this.valorIVA10 = valorIVA10;
  }

  /**
   * @return the valorIVA16
   */
  public Double getValorIVA16() {
    return valorIVA16;
  }

  /**
   * @param valorIVA16 the valorIVA16 to set
   */
  public void setValorIVA16(Double valorIVA16) {
    this.valorIVA16 = valorIVA16;
  }

}
