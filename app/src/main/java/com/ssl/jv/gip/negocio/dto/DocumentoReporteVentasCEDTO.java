package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

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
public class DocumentoReporteVentasCEDTO implements Serializable {

  public static final String LISTADO_DOCUMENTOS_REPORTE_VENTAS_CE = "SELECT row_number() over(ORDER BY documentos.consecutivo_documento) AS id, "
      + "documentos.fecha_generacion AS fechaGeneracion, "
      + "clientes.nit AS clienteNit, "
      + "clientes.nombre AS clienteNombre, "
      + "documentos.observacion_documento AS consecutivoRM, "
      + "documentos.consecutivo_documento AS consecutivoFDFE, "
      + "documentos.numero_factura AS numeroFDFE, "
      + "productos_inventario.sku AS sku, "
      + "productos_inventario.nombre AS productoNombre, "
      + "productosxdocumentos.cantidad1 AS cantidadFacturada, "
      + "productosxdocumentos.valor_unitario_usd AS valorUnitatrio, "
      + "productosxdocumentos.descuentoxproducto AS porcDescuentoXProducto, "
      + "productosxdocumentos.otros_descuentos AS porcOtrosDescuentos, "
      + "productosxdocumentos.iva AS porcIva, "
      + "productosxdocumentos.valor_total AS valorTotal, "
      + "documentos.descuento_cliente AS porcDecuentoCliente, "
      + "documento_x_negociacion.costo_entrega + documento_x_negociacion.costo_seguro + documento_x_negociacion.costo_flete + documento_x_negociacion.otros_gastos AS costosLogisticos, "
      + "productosxdocumentos.total_peso_neto_item AS pesoNeto, "
      + "(CASE WHEN (SELECT documento_x_lotesoic.consecutivo || ';' || tipo_loteoic.descripcion  || ';' ||documento_x_lotesoic.contribucion || ';' ||documento_x_lotesoic.dex AS cadena FROM documento_x_lotesoic INNER JOIN tipo_loteoic ON tipo_loteoic.id = documento_x_lotesoic.id_tipo_lote INNER JOIN productos_inventario_comext ON productos_inventario_comext.id_tipo_loteoic = documento_x_lotesoic.id_tipo_lote WHERE documento_x_lotesoic.id_documento = (SELECT id FROM documentos doc2 WHERE doc2.consecutivo_documento = (SELECT observacion_documento FROM documentos doc WHERE doc.consecutivo_documento=documentos.observacion_documento)) and productos_inventario_comext.id_producto = productos_inventario.id) IS NULL THEN 'MERCADEO;MERCADEO' ELSE (SELECT documento_x_lotesoic.consecutivo || ';' || tipo_loteoic.descripcion || ';' ||documento_x_lotesoic.contribucion || ';' ||documento_x_lotesoic.dex AS cadena FROM documento_x_lotesoic INNER JOIN tipo_loteoic ON tipo_loteoic.id = documento_x_lotesoic.id_tipo_lote INNER JOIN productos_inventario_comext ON productos_inventario_comext.id_tipo_loteoic = documento_x_lotesoic.id_tipo_lote WHERE documento_x_lotesoic.id_documento = (SELECT id FROM documentos doc2 WHERE doc2.consecutivo_documento = (SELECT observacion_documento FROM documentos doc WHERE doc.consecutivo_documento=documentos.observacion_documento)) AND productos_inventario_comext.id_producto = productos_inventario.id) END) AS lote, "
      + "terminos_transporte.numero_contenedor AS numeroContenedor, "
      + "terminos_transporte.numero_booking AS numBooking, "
      + "productos_inventario_comext.posicion_arancelaria AS posicionArancelaria "
      + "FROM documentos "
      + "LEFT OUTER JOIN terminos_transporte_x_documento ttxd ON documentos.id= ttxd.id_documento "
      + "LEFT JOIN terminos_transporte ON ttxd.id_terminos_transporte=terminos_transporte.id, "
      + "clientes, "
      + "productosxdocumentos, "
      + "productos_inventario, "
      + "documento_x_negociacion, "
      + "productos_inventario_comext "
      + "WHERE productosxdocumentos.id_documento = documentos.id "
      + "AND documentos.id_cliente = clientes.id "
      + "AND productosxdocumentos.id_producto = productos_inventario.id "
      + "AND productos_inventario_comext.id_producto=productos_inventario.id "
      + "AND documentos.id = documento_x_negociacion.id_documento "
      + "AND documentos.fecha_generacion BETWEEN :fechaInicial AND :fechaFinal "
      + "AND documentos.id_tipo_documento = :tipoDocumento "
      + "AND documentos.id_estado in (:estadosDocumento) ";
  public static final String CONDICIONAL_CLIENTES = " AND clientes.id in (:idsClientes)";
  public static final String CONDICIONAL_PRODUCTOS = " AND productos_inventario.id in (:idsProductos)";

  @Id
  private Long id;
  private Timestamp fechaGeneracion;
  private String clienteNit;
  private String clienteNombre;
  private String consecutivoRM;
  private String consecutivoFDFE;
  private String numeroFDFE;
  private String sku;
  private String productoNombre;
  private Double cantidadFacturada;
  private Double valorUnitatrio;
  private Double porcDescuentoXProducto;
  private Double porcOtrosDescuentos;
  private Double porcIva;
  private Double valorTotal;
  private Double porcDecuentoCliente;
  private Double costosLogisticos;
  private Double pesoNeto;
  private String lote;
  @Transient
  private String loteCodigo;
  @Transient
  private String loteNombre;
  @Transient
  private Double contribucion;
  @Transient
  private Double dex;
  private String numeroContenedor;
  private String numBooking;
  private String posicionArancelaria;

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
   * @return the clienteNit
   */
  public String getClienteNit() {
    return clienteNit;
  }

  /**
   * @param clienteNit the clienteNit to set
   */
  public void setClienteNit(String clienteNit) {
    this.clienteNit = clienteNit;
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
   * @return the consecutivoRM
   */
  public String getConsecutivoRM() {
    return consecutivoRM;
  }

  /**
   * @param consecutivoRM the consecutivoRM to set
   */
  public void setConsecutivoRM(String consecutivoRM) {
    this.consecutivoRM = consecutivoRM;
  }

  /**
   * @return the consecutivoFDFE
   */
  public String getConsecutivoFDFE() {
    return consecutivoFDFE;
  }

  /**
   * @param consecutivoFDFE the consecutivoFDFE to set
   */
  public void setConsecutivoFDFE(String consecutivoFDFE) {
    this.consecutivoFDFE = consecutivoFDFE;
  }

  /**
   * @return the numeroFDFE
   */
  public String getNumeroFDFE() {
    return numeroFDFE;
  }

  /**
   * @param numeroFDFE the numeroFDFE to set
   */
  public void setNumeroFDFE(String numeroFDFE) {
    this.numeroFDFE = numeroFDFE;
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
   * @return the productoNombre
   */
  public String getProductoNombre() {
    return productoNombre;
  }

  /**
   * @param productoNombre the productoNombre to set
   */
  public void setProductoNombre(String productoNombre) {
    this.productoNombre = productoNombre;
  }

  /**
   * @return the cantidadFacturada
   */
  public Double getCantidadFacturada() {
    return cantidadFacturada;
  }

  /**
   * @param cantidadFacturada the cantidadFacturada to set
   */
  public void setCantidadFacturada(Double cantidadFacturada) {
    this.cantidadFacturada = cantidadFacturada;
  }

  /**
   * @return the valorUnitatrio
   */
  public Double getValorUnitatrio() {
    return valorUnitatrio;
  }

  /**
   * @param valorUnitatrio the valorUnitatrio to set
   */
  public void setValorUnitatrio(Double valorUnitatrio) {
    this.valorUnitatrio = valorUnitatrio;
  }

  /**
   * @return the porcDescuentoXProducto
   */
  public Double getPorcDescuentoXProducto() {
    return porcDescuentoXProducto;
  }

  /**
   * @param porcDescuentoXProducto the porcDescuentoXProducto to set
   */
  public void setPorcDescuentoXProducto(Double porcDescuentoXProducto) {
    this.porcDescuentoXProducto = porcDescuentoXProducto;
  }

  /**
   * @return the porcOtrosDescuentos
   */
  public Double getPorcOtrosDescuentos() {
    return porcOtrosDescuentos;
  }

  /**
   * @param porcOtrosDescuentos the porcOtrosDescuentos to set
   */
  public void setPorcOtrosDescuentos(Double porcOtrosDescuentos) {
    this.porcOtrosDescuentos = porcOtrosDescuentos;
  }

  /**
   * @return the porcIva
   */
  public Double getPorcIva() {
    return porcIva;
  }

  /**
   * @param porcIva the porcIva to set
   */
  public void setPorcIva(Double porcIva) {
    this.porcIva = porcIva;
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
   * @return the porcDecuentoCliente
   */
  public Double getPorcDecuentoCliente() {
    return porcDecuentoCliente;
  }

  /**
   * @param porcDecuentoCliente the porcDecuentoCliente to set
   */
  public void setPorcDecuentoCliente(Double porcDecuentoCliente) {
    this.porcDecuentoCliente = porcDecuentoCliente;
  }

  /**
   * @return the costosLogisticos
   */
  public Double getCostosLogisticos() {
    return costosLogisticos;
  }

  /**
   * @param costosLogisticos the costosLogisticos to set
   */
  public void setCostosLogisticos(Double costosLogisticos) {
    this.costosLogisticos = costosLogisticos;
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
   * @return the lote
   */
  public String getLote() {
    return lote;
  }

  /**
   * @param lote the lote to set
   */
  public void setLote(String lote) {
    this.lote = lote;
    String[] arrayLote = lote.split(";");
    setLoteCodigo(arrayLote[0]);
    setLoteNombre(arrayLote[1]);
    if (arrayLote.length == 2) {
      setContribucion(new Double(0));
      setDex(new Double(0));
    } else if (arrayLote.length == 4) {
      setContribucion(new Double(arrayLote[2]));
      setDex(new Double(arrayLote[3]));
    }
  }

  /**
   * @return the loteCodigo
   */
  public String getLoteCodigo() {
    String[] arrayLote = getLote().split(";");
    setLoteCodigo(arrayLote[0]);
    return loteCodigo;
  }

  /**
   * @param loteCodigo the loteCodigo to set
   */
  public void setLoteCodigo(String loteCodigo) {
    this.loteCodigo = loteCodigo;
  }

  /**
   * @return the loteNombre
   */
  public String getLoteNombre() {
    String[] arrayLote = getLote().split(";");
    setLoteNombre(arrayLote[1]);
    return loteNombre;
  }

  /**
   * @param loteNombre the loteNombre to set
   */
  public void setLoteNombre(String loteNombre) {
    this.loteNombre = loteNombre;
  }

  /**
   * @return the contribucion
   */
  public Double getContribucion() {
    String[] arrayLote = getLote().split(";");
    if (arrayLote.length == 2) {
      setContribucion(new Double(0));
    } else if (arrayLote.length == 4) {
      setContribucion(new Double(arrayLote[2]));
    }
    return contribucion;
  }

  /**
   * @param contribucion the contribucion to set
   */
  public void setContribucion(Double contribucion) {
    this.contribucion = contribucion;
  }

  /**
   * @return the dex
   */
  public Double getDex() {
    String[] arrayLote = getLote().split(";");
    if (arrayLote.length == 2) {
      setDex(new Double(0));
    } else if (arrayLote.length == 4) {
      setDex(new Double(arrayLote[3]));
    }
    return dex;
  }

  /**
   * @param dex the dex to set
   */
  public void setDex(Double dex) {
    this.dex = dex;
  }

  /**
   * @return the numeroContenedor
   */
  public String getNumeroContenedor() {
    return numeroContenedor;
  }

  /**
   * @param numeroContenedor the numeroContenedor to set
   */
  public void setNumeroContenedor(String numeroContenedor) {
    this.numeroContenedor = numeroContenedor;
  }

  /**
   * @return the numBooking
   */
  public String getNumBooking() {
    return numBooking;
  }

  /**
   * @param numBooking the numBooking to set
   */
  public void setNumBooking(String numBooking) {
    this.numBooking = numBooking;
  }

  /**
   * @return the posicionArancelaria
   */
  public String getPosicionArancelaria() {
    return posicionArancelaria;
  }

  /**
   * @param posicionArancelaria the posicionArancelaria to set
   */
  public void setPosicionArancelaria(String posicionArancelaria) {
    this.posicionArancelaria = posicionArancelaria;
  }

}
