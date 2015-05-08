package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * Title: DocTerminosTransporteDTO</p>
 *
 * <p>
 * Description: DTO para almacenar la informacion de los documentos asociados a unos terminos de transporte</p>
 *
 * <p>
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Soft Studio Ltda.</p>
 *
 * @author Sebastian Gamba Pinilla
 * @email sebas.gamba02@gmail.com
 * @phone 311 89376670
 * @version 1.0
 */
public class DocTerminosTransporteDTO implements Serializable {

  private static final long serialVersionUID = -8632156363499487707L;
  private String factura;
  private String lote;
  private String descripcion;
  private BigDecimal cantCajas;
  private String pedidoNum;
  private String asignacionNum;
  private String avisoNum;

  public DocTerminosTransporteDTO() {
  }

  public String getFactura() {
    return factura;
  }

  public void setFactura(String factura) {
    this.factura = factura;
  }

  public String getLote() {
    return lote;
  }

  public void setLote(String lote) {
    this.lote = lote;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public BigDecimal getCantCajas() {
    return cantCajas;
  }

  public void setCantCajas(BigDecimal cantCajas) {
    this.cantCajas = cantCajas;
  }

  public String getPedidoNum() {
    return pedidoNum;
  }

  public void setPedidoNum(String pedidoNum) {
    this.pedidoNum = pedidoNum;
  }

  public String getAsignacionNum() {
    return asignacionNum;
  }

  public void setAsignacionNum(String asignacionNum) {
    this.asignacionNum = asignacionNum;
  }

  public String getAvisoNum() {
    return avisoNum;
  }

  public void setAvisoNum(String avisoNum) {
    this.avisoNum = avisoNum;
  }

  @Override
  public String toString() {
    return "DocTerminosTransporteDTO [factura=" + factura + ", lote="
        + lote + ", descripcion=" + descripcion + ", cantCajas="
        + cantCajas + ", pedidoNum=" + pedidoNum + ", asignacionNum="
        + asignacionNum + ", avisoNum=" + avisoNum + "]";
  }
}
