package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReporteProduccionDTO implements Serializable {

  private static final long serialVersionUID = -5411565999770669228L;
  public static final String BUSCAR_PRODUCTOS_REPORTE_PRODUCCION = "SELECT pxd.id_producto || '-' || pxd.id_documento AS id, d.consecutivo_documento AS consecutivoDocumento, pi.sku AS sku, pi.nombre AS productoNombre, p.nombre AS paisNombre, tc.nombre AS tipoCanal, u.nombre AS canal, pxd.cantidad1 AS cantidad, c.nombre AS clienteNombre FROM productosxdocumentos pxd inner join documentos d on d.id=pxd.id_documento inner join productos_inventario pi on pi.id=pxd.id_producto inner join clientes c on c.id=d.id_cliente inner join ciudades c2 on c2.id=c.id_ciudad inner join tipo_canal tc on tc.id=c.id_tipo_canal inner join paises p on p.id=c2.id_pais inner join ubicaciones u on u.id=d.id_ubicacion_origen inner join documento_x_negociacion dxn on dxn.id_documento=d.id WHERE d.id_tipo_documento=:tipoDocumento and d.id_estado=:estado and dxn.solicitud_cafe=:solicitudCafe and d.fecha_generacion between :fechaInicial and :fechaFinal";
  @Id
  private String id;
  private Double cantidad;
  private String sku;
  private String productoNombre;
  private String paisNombre;
  private String tipoCanal;
  private String canal;
  private String clienteNombre;
  private String consecutivoDocumento;

  /**
   * @return the id
   */
  public String getId() {
	return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(String id) {
	this.id = id;
  }

  /**
   * @return the cantidad
   */
  public Double getCantidad() {
	return cantidad;
  }

  /**
   * @param cantidad
   *          the cantidad to set
   */
  public void setCantidad(Double cantidad) {
	this.cantidad = cantidad;
  }

  /**
   * @return the sku
   */
  public String getSku() {
	return sku;
  }

  /**
   * @param sku
   *          the sku to set
   */
  public void setSku(String sku) {
	this.sku = sku;
  }

  /**
   * @return the productoNombre
   */
  public String getProductoNombre() {
	return productoNombre;
  }

  /**
   * @param productoNombre
   *          the productoNombre to set
   */
  public void setProductoNombre(String productoNombre) {
	this.productoNombre = productoNombre;
  }

  /**
   * @return the paisNombre
   */
  public String getPaisNombre() {
	return paisNombre;
  }

  /**
   * @param paisNombre
   *          the paisNombre to set
   */
  public void setPaisNombre(String paisNombre) {
	this.paisNombre = paisNombre;
  }

  /**
   * @return the tipoCanal
   */
  public String getTipoCanal() {
	return tipoCanal;
  }

  /**
   * @param tipoCanal
   *          the tipoCanal to set
   */
  public void setTipoCanal(String tipoCanal) {
	this.tipoCanal = tipoCanal;
  }

  /**
   * @return the canal
   */
  public String getCanal() {
	return canal;
  }

  /**
   * @param canal
   *          the canal to set
   */
  public void setCanal(String canal) {
	this.canal = canal;
  }

  /**
   * @return the clienteNombre
   */
  public String getClienteNombre() {
	return clienteNombre;
  }

  /**
   * @param clienteNombre
   *          the clienteNombre to set
   */
  public void setClienteNombre(String clienteNombre) {
	this.clienteNombre = clienteNombre;
  }

  /**
   * @return the consecutivoDocumento
   */
  public String getConsecutivoDocumento() {
	return consecutivoDocumento;
  }

  /**
   * @param consecutivoDocumento
   *          the consecutivoDocumento to set
   */
  public void setConsecutivoDocumento(String consecutivoDocumento) {
	this.consecutivoDocumento = consecutivoDocumento;
  }

}
