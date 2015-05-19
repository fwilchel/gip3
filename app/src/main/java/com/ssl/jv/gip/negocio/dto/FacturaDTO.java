package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Title: GIP</p>
 * <p>
 * Description: GIP</p>
 * <p>
 * Copyright: Copyright (c) 2014</p>
 * <p>
 * Company: Soft Studio Ltda.</p>
 *
 * @author Diego Poveda
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
public class FacturaDTO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -7621285605701774144L;

  private Documento documento;
  private List<ProductosXDocumento> productos;
  private BigDecimal subtotal;
  private BigDecimal descuento;
  private BigDecimal totalIva10;
  private BigDecimal totalIva16;
  private BigDecimal totalIva5;
  private BigDecimal total;

  /**
   * @return the documento
   */
  public Documento getDocumento() {
    return documento;
  }

  /**
   * @param documento the documento to set
   */
  public void setDocumento(Documento documento) {
    this.documento = documento;
  }

  /**
   * @return the productos
   */
  public List<ProductosXDocumento> getProductos() {
    return productos;
  }

  /**
   * @param productos the productos to set
   */
  public void setProductos(List<ProductosXDocumento> productos) {
    this.productos = productos;
  }

  /**
   * @return the subtotal
   */
  public BigDecimal getSubtotal() {
    return subtotal;
  }

  /**
   * @param subtotal the subtotal to set
   */
  public void setSubtotal(BigDecimal subtotal) {
    this.subtotal = subtotal;
  }

  /**
   * @return the descuento
   */
  public BigDecimal getDescuento() {
    return descuento;
  }

  /**
   * @param descuento the descuento to set
   */
  public void setDescuento(BigDecimal descuento) {
    this.descuento = descuento;
  }

  /**
   * @return the totalIva10
   */
  public BigDecimal getTotalIva10() {
    return totalIva10;
  }

  /**
   * @param totalIva10 the totalIva10 to set
   */
  public void setTotalIva10(BigDecimal totalIva10) {
    this.totalIva10 = totalIva10;
  }

  /**
   * @return the totalIva16
   */
  public BigDecimal getTotalIva16() {
    return totalIva16;
  }

  /**
   * @param totalIva16 the totalIva16 to set
   */
  public void setTotalIva16(BigDecimal totalIva16) {
    this.totalIva16 = totalIva16;
  }

  /**
   * @return the totalIva5
   */
  public BigDecimal getTotalIva5() {
    return totalIva5;
  }

  /**
   * @param totalIva5 the totalIva5 to set
   */
  public void setTotalIva5(BigDecimal totalIva5) {
    this.totalIva5 = totalIva5;
  }

  /**
   * @return the total
   */
  public BigDecimal getTotal() {
    return total;
  }

  /**
   * @param total the total to set
   */
  public void setTotal(BigDecimal total) {
    this.total = total;
  }

}
