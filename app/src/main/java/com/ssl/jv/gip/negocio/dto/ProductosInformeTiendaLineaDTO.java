package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductosInformeTiendaLineaDTO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  public static final String PRODUCTOS_POR_LISTA_EMPAQUE = "SELECT row_number() over() as id, tl.descripcion as descripcionLote, documento_x_lotesoic.consecutivo as consecutivoLote, sum(productosxdocumentos.total_peso_neto_item) as pesoNeto, sum(round(round(productosxdocumentos.cantidad_pallets_item/(select sum(cantidad_pallets_item) from productosxdocumentos where id_documento = documentos.id),5) * (documento_x_negociacion.peso_bruto_estibas),5) + (productosxdocumentos.total_peso_bruto_item)) as pesoBruto, sum(productosxdocumentos.cantidad_cajas_item) as numeroCajas, documento_x_lotesoic.aviso as aviso, documento_x_lotesoic.asignacion as asignacion, documento_x_lotesoic.pedido as pedido from productosxdocumentos inner join productos_inventario on productosxdocumentos.id_producto = productos_inventario.id inner join documentos on productosxdocumentos.id_documento = documentos.id inner join documento_x_negociacion on documentos.id = documento_x_negociacion.id_documento inner join productos_inventario_comext pic on pic.id_producto = productos_inventario.id inner join tipo_loteoic tl on pic.id_tipo_loteoic = tl.id inner join documento_x_lotesoic on documento_x_lotesoic.id_tipo_lote = tl.id where documento_x_lotesoic.id_documento = (select doc.id from documentos doc where doc.consecutivo_documento = documentos.observacion_documento) and documentos.id = :idDocumento group by tl.descripcion, documento_x_lotesoic.consecutivo, documento_x_lotesoic.aviso, documento_x_lotesoic.asignacion, documento_x_lotesoic.pedido";
  @Id
  private Long id;
  private String descripcionLote;
  private String consecutivoLote;
  private Double pesoBruto;
  private Double pesoNeto;
  private Double numeroCajas;
  private String aviso;
  private String asignacion;
  private String pedido;

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
   * @return the descripcionLote
   */
  public String getDescripcionLote() {
    return descripcionLote;
  }

  /**
   * @param descripcionLote the descripcionLote to set
   */
  public void setDescripcionLote(String descripcionLote) {
    this.descripcionLote = descripcionLote;
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
   * @return the pesoBruto
   */
  public Double getPesoBruto() {
    return pesoBruto;
  }

  /**
   * @param pesoBruto the pesoBruto to set
   */
  public void setPesoBruto(Double pesoBruto) {
    this.pesoBruto = pesoBruto;
  }

  /**
   * @return the pesoNeto
   */
  public Double getPesoNeto() {
    return pesoNeto;
  }

  /**
   * @param pesoNeto the pesoNeto to set
   */
  public void setPesoNeto(Double pesoNeto) {
    this.pesoNeto = pesoNeto;
  }

  /**
   * @return the numeroCajas
   */
  public Double getNumeroCajas() {
    return numeroCajas;
  }

  /**
   * @param numeroCajas the numeroCajas to set
   */
  public void setNumeroCajas(Double numeroCajas) {
    this.numeroCajas = numeroCajas;
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

}
