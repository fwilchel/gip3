package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * The Class ProductoDTO.
 */
@Entity
public class ProductoODDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * param idDocumento param idCliente
   */
  public static final String BUSCAR_PRODUCTOS_ORDEN_DESPACHO = "SELECT productos_inventario.id as id, productos_inventario.nombre as nombre, productos_inventario.sku as sku, productos_inventario.id_uv as idUV, productosXdocumentos.cantidad1 as cantidad, pi_ce.cantidad_x_embalaje as cantidadPorEmbalaje, (CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pi_ce.cantidad_x_embalaje) END) as cantidadCajas, ((CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (pi_ce.peso_neto_embalaje/pi_ce.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as totalPesoNeto, ((CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (pi_ce.peso_bruto_embalaje/pi_ce.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as totalPesoBruto, ((CASE WHEN (pi_ce.cantidad_x_embalaje = 0 or pi_ce.total_cajas_x_pallet = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pi_ce.cantidad_x_embalaje)/pi_ce.total_cajas_x_pallet END)) AS totalCajasPallet, tl.descripcion as descripcionLote, dxl.consecutivo as consecutivoLote , dxl.total_cantidad as totalCantidadLote, documento_x_negociacion.solicitud_cafe as solicitudCafe FROM productosXdocumentos LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id LEFT JOIN productos_x_cliente_comext ON productos_x_cliente_comext.id_producto=productosXdocumentos.id_producto LEFT JOIN productos_inventario_comext pi_ce ON pi_ce.id_producto=productos_inventario.id left join tipo_loteoic tl on pi_ce.id_tipo_loteoic=tl.id LEFT join documento_x_lotesoic dxl on dxl.id_tipo_lote=tl.id LEFT JOIN documento_x_negociacion on productosXdocumentos.id_documento = documento_x_negociacion.id_documento WHERE productosXdocumentos.id_documento=:idDocumento AND (dxl.id_documento=:idDocumento or dxl.id_documento is null) AND (productos_x_cliente_comext.id_cliente=:idCliente or productos_x_cliente_comext.id_cliente is null) order by consecutivoLote ";
  @Id
  private Long id;
  private String nombre;
  private String sku;
  private Long idUV;
  private BigDecimal cantidad;
  private BigDecimal cantidadPorEmbalaje;
  private BigDecimal cantidadCajas;
  private BigDecimal totalPesoNeto;
  private BigDecimal totalPesoBruto;
  private BigDecimal totalCajasPallet;
  private String descripcionLote;
  private String consecutivoLote;
  private BigDecimal totalCantidadLote;
  private boolean solicitudCafe;
  @Transient
  private BigDecimal muestrasFITOYANTICO;
  @Transient
  private BigDecimal muestrasCalidades;

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
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param nombre the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
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
   * @return the idUV
   */
  public Long getIdUV() {
    return idUV;
  }

  /**
   * @param idUV the idUV to set
   */
  public void setIdUV(Long idUV) {
    this.idUV = idUV;
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
   * @return the cantidadPorEmbalaje
   */
  public BigDecimal getCantidadPorEmbalaje() {
    return cantidadPorEmbalaje;
  }

  /**
   * @param cantidadPorEmbalaje the cantidadPorEmbalaje to set
   */
  public void setCantidadPorEmbalaje(BigDecimal cantidadPorEmbalaje) {
    this.cantidadPorEmbalaje = cantidadPorEmbalaje;
  }

  /**
   * @return the cantidadCajas
   */
  public BigDecimal getCantidadCajas() {
    return cantidadCajas;
  }

  /**
   * @param cantidadCajas the cantidadCajas to set
   */
  public void setCantidadCajas(BigDecimal cantidadCajas) {
    this.cantidadCajas = cantidadCajas;
  }

  /**
   * @return the totalPesoNeto
   */
  public BigDecimal getTotalPesoNeto() {
    return totalPesoNeto;
  }

  /**
   * @param totalPesoNeto the totalPesoNeto to set
   */
  public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
    this.totalPesoNeto = totalPesoNeto;
  }

  /**
   * @return the totalPesoBruto
   */
  public BigDecimal getTotalPesoBruto() {
    return totalPesoBruto;
  }

  /**
   * @param totalPesoBruto the totalPesoBruto to set
   */
  public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
    this.totalPesoBruto = totalPesoBruto;
  }

  /**
   * @return the totalCajasPallet
   */
  public BigDecimal getTotalCajasPallet() {
    return totalCajasPallet;
  }

  /**
   * @param totalCajasPallet the totalCajasPallet to set
   */
  public void setTotalCajasPallet(BigDecimal totalCajasPallet) {
    this.totalCajasPallet = totalCajasPallet;
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
   * @return the totalCantidadLote
   */
  public BigDecimal getTotalCantidadLote() {
    return totalCantidadLote;
  }

  /**
   * @param totalCantidadLote the totalCantidadLote to set
   */
  public void setTotalCantidadLote(BigDecimal totalCantidadLote) {
    this.totalCantidadLote = totalCantidadLote;
  }

  /**
   * @return the solicitudCafe
   */
  public boolean isSolicitudCafe() {
    return solicitudCafe;
  }

  /**
   * @param solicitudCafe the solicitudCafe to set
   */
  public void setSolicitudCafe(boolean solicitudCafe) {
    this.solicitudCafe = solicitudCafe;
  }

  /**
   * @return the muestrasFITOYANTICO
   */
  public BigDecimal getMuestrasFITOYANTICO() {
    return muestrasFITOYANTICO;
  }

  /**
   * @param muestrasFITOYANTICO the muestrasFITOYANTICO to set
   */
  public void setMuestrasFITOYANTICO(BigDecimal muestrasFITOYANTICO) {
    this.muestrasFITOYANTICO = muestrasFITOYANTICO;
  }

  /**
   * @return the muestrasCalidades
   */
  public BigDecimal getMuestrasCalidades() {
    return muestrasCalidades;
  }

  /**
   * @param muestrasCalidades the muestrasCalidades to set
   */
  public void setMuestrasCalidades(BigDecimal muestrasCalidades) {
    this.muestrasCalidades = muestrasCalidades;
  }

}
