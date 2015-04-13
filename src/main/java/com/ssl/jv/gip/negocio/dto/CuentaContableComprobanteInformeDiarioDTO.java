package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CuentaContableComprobanteInformeDiarioDTO implements Serializable {

  public static final String CUENTA_CONTABLE_COMPROBANTE_INFORME_DIARIO = "select row_number() OVER (order by productos_inventario_comext.id_cuenta_contable_ce, documentos.id asc) AS id, documentos.id as idDocumento, productos_inventario_comext.id_cuenta_contable_ce as idCuentaContable, cuenta_contable.descripcion as descripcionCuentaContable, productosXdocumentos.valor_total as valorTotal, productosXdocumentos.valor_total + documento_x_negociacion.costo_entrega + documento_x_negociacion.costo_seguro + documento_x_negociacion.costo_flete + documento_x_negociacion.otros_gastos as valorTotalConCostos from productos_inventario_comext, productosXdocumentos, cuenta_contable, documentos, documento_x_negociacion where productos_inventario_comext.id_producto = productosXdocumentos.id_producto and cuenta_contable.id = productos_inventario_comext.id_cuenta_contable_ce and documentos.id = documento_x_negociacion.id_documento and documentos.id = productosXdocumentos.id_documento and documentos.fecha_generacion between '2014-05-01 00:00:00' and '2014-05-31 23:59:59' and documentos.id_tipo_documento = 25 and productos_inventario_comext.id_cuenta_contable_ce is not null and documentos.id_estado in (5,12)";
  @Id
  private Long id;
  private Long idDocumento;
  private Long idCuentaContable;
  private String descripcionCuentaContable;
  private BigDecimal valorTotal;
  private BigDecimal valorTotalConCostos;

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
   * @return the idCuentaContable
   */
  public Long getIdCuentaContable() {
    return idCuentaContable;
  }

  /**
   * @param idCuentaContable the idCuentaContable to set
   */
  public void setIdCuentaContable(Long idCuentaContable) {
    this.idCuentaContable = idCuentaContable;
  }

  /**
   * @return the descripcionCuentaContable
   */
  public String getDescripcionCuentaContable() {
    return descripcionCuentaContable;
  }

  /**
   * @param descripcionCuentaContable the descripcionCuentaContable to set
   */
  public void setDescripcionCuentaContable(String descripcionCuentaContable) {
    this.descripcionCuentaContable = descripcionCuentaContable;
  }

  /**
   * @return the valorTotal
   */
  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  /**
   * @param valorTotal the valorTotal to set
   */
  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }

  /**
   * @return the valorTotalConCostos
   */
  public BigDecimal getValorTotalConCostos() {
    return valorTotalConCostos;
  }

  /**
   * @param valorTotalConCostos the valorTotalConCostos to set
   */
  public void setValorTotalConCostos(BigDecimal valorTotalConCostos) {
    this.valorTotalConCostos = valorTotalConCostos;
  }

}
