package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FiltroDocumentoDTO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -5424938254687892066L;
  @Deprecated
  private Long idTipoDocumento;
  @Deprecated
  private Long idEstado;
  @Deprecated
  private Long idEstado2;
  @Deprecated
  private Date fechaInicio;
  @Deprecated
  private Date fechaFin;
  /**
   * filtra por e solicitud cafe. =
   */
  private Boolean solicitudCafe;
  /**
   * filtra por e consecutivo de documento. LIKE
   */
  private String consecutivoDocumento;
  /**
   * filtra por el tipo o los tipos de documentos enviados. IN
   */
  private List<Long> tipoDocumento;
  /**
   * filtra por el estado o los estados del documento enviados. IN
   */
  private List<Long> estado;
  /**
   * filtra por la fecha de generacion. EQ
   */
  private Date fechaGeneracion;
  /**
   * filtra por la fecha de generacion. BETWEEN
   */
  private Date fechaGeneracionInicio;
  /**
   * filtra por la fecha de generacion. BETWEEN
   */
  private Date fechaGeneracionFin;
  /**
   * filtra por el o los clientes enviados. IN
   */
  private List<Long> cliente;
  /**
   * filtra por e o los punto de ventas enviados. IN
   */
  private List<Long> puntoVenta;
  /**
   * filtra por numero de factura. LIKE
   */
  private String numeroFactura;
  /**
   * filtra por la observacion de documento. LIKE
   */
  private String observacionDocumento;
  /**
   * filtra por el provedor. LIKE
   */
  private Long proveedor;
  /**
   * filtra por la o las ubicaiones enviadas. IN
   */
  private List<Long> ubicacionOrigen;
  /**
   * filtra por la o las ubicaiones enviadas. IN
   */
  private List<Long> ubicacionDestino;
  /**
   * campo por e cual se desea ordenar la consulta. por defecto es el id
   */
  private String ordenCampo = "id";

  /**
   * @return the idTipoDocumento
   */
  @Deprecated
  public Long getIdTipoDocumento() {
    return idTipoDocumento;
  }

  /**
   * @param idTipoDocumento the idTipoDocumento to set
   */
  @Deprecated
  public void setIdTipoDocumento(Long idTipoDocumento) {
    this.idTipoDocumento = idTipoDocumento;
  }

  /**
   * @return the idEstado
   */
  @Deprecated
  public Long getIdEstado() {
    return idEstado;
  }

  /**
   * @param idEstado the idEstado to set
   */
  @Deprecated
  public void setIdEstado(Long idEstado) {
    this.idEstado = idEstado;
  }

  /**
   * @return the idEstado2
   */
  @Deprecated
  public Long getIdEstado2() {
    return idEstado2;
  }

  /**
   * @param idEstado2 the idEstado2 to set
   */
  @Deprecated
  public void setIdEstado2(Long idEstado2) {
    this.idEstado2 = idEstado2;
  }

  /**
   * @return the fechaInicio
   */
  @Deprecated
  public Date getFechaInicio() {
    return fechaInicio;
  }

  /**
   * @param fechaInicio the fechaInicio to set
   */
  @Deprecated
  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  /**
   * @return the fechaFin
   */
  @Deprecated
  public Date getFechaFin() {
    return fechaFin;
  }

  /**
   * @param fechaFin the fechaFin to set
   */
  @Deprecated
  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
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
   * @return the solicitudCafe
   */
  public Boolean isSolicitudCafe() {
    return solicitudCafe;
  }

  /**
   * @param solicitudCafe the solicitudCafe to set
   */
  public void setSolicitudCafe(Boolean solicitudCafe) {
    this.solicitudCafe = solicitudCafe;
  }

  /**
   * @return the tipoDocumento
   */
  public List<Long> getTipoDocumento() {
    return tipoDocumento;
  }

  /**
   * @param tipoDocumento the tipoDocumento to set
   */
  public void setTipoDocumento(List<Long> tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  /**
   * @param tipoDocumento the tipoDocumento to set
   */
  public void setTipoDocumento(Long... tipoDocumento) {
    this.tipoDocumento = Arrays.asList(tipoDocumento);
  }

  /**
   * @return the estado
   */
  public List<Long> getEstado() {
    return estado;
  }

  /**
   * @param estado the estado to set
   */
  public void setEstado(List<Long> estado) {
    this.estado = estado;
  }

  /**
   * @param estado the estado to set
   */
  public void setEstado(Long... estado) {
    this.estado = Arrays.asList(estado);
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
   * @return the fechaGeneracionInicio
   */
  public Date getFechaGeneracionInicio() {
    return fechaGeneracionInicio;
  }

  /**
   * @param fechaGeneracionInicio the fechaGeneracionInicio to set
   */
  public void setFechaGeneracionInicio(Date fechaGeneracionInicio) {
    this.fechaGeneracionInicio = fechaGeneracionInicio;
  }

  /**
   * @return the fechaGeneracionFin
   */
  public Date getFechaGeneracionFin() {
    return fechaGeneracionFin;
  }

  /**
   * @param fechaGeneracionFin the fechaGeneracionFin to set
   */
  public void setFechaGeneracionFin(Date fechaGeneracionFin) {
    this.fechaGeneracionFin = fechaGeneracionFin;
  }

  /**
   * @return the cliente
   */
  public List<Long> getCliente() {
    return cliente;
  }

  /**
   * @param cliente the cliente to set
   */
  public void setCliente(List<Long> cliente) {
    this.cliente = cliente;
  }

  /**
   * @param cliente the cliente to set
   */
  public void setCliente(Long... cliente) {
    this.cliente = Arrays.asList(cliente);
  }

  /**
   * @return the puntoVenta
   */
  public List<Long> getPuntoVenta() {
    return puntoVenta;
  }

  /**
   * @param puntoVenta the puntoVenta to set
   */
  public void setPuntoVenta(List<Long> puntoVenta) {
    this.puntoVenta = puntoVenta;
  }

  /**
   * @param puntoVenta the puntoVenta to set
   */
  public void setPuntoVenta(Long... puntoVenta) {
    this.puntoVenta = Arrays.asList(puntoVenta);
  }

  /**
   * @return the numeroFactura
   */
  public String getNumeroFactura() {
    return numeroFactura;
  }

  /**
   * @param numeroFactura the numeroFactura to set
   */
  public void setNumeroFactura(String numeroFactura) {
    this.numeroFactura = numeroFactura;
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
   * @return the proveedor
   */
  public Long getProveedor() {
    return proveedor;
  }

  /**
   * @param proveedor the proveedor to set
   */
  public void setProveedor(Long proveedor) {
    this.proveedor = proveedor;
  }

  /**
   * @return the ubicacionOrigen
   */
  public List<Long> getUbicacionOrigen() {
    return ubicacionOrigen;
  }

  /**
   * @param ubicacionOrigen the ubicacionOrigen to set
   */
  public void setUbicacionOrigen(List<Long> ubicacionOrigen) {
    this.ubicacionOrigen = ubicacionOrigen;
  }

  /**
   * @param ubicacionOrigen the ubicacionOrigen to set
   */
  public void setUbicacionOrigen(Long... ubicacionOrigen) {
    this.ubicacionOrigen = Arrays.asList(ubicacionOrigen);
  }

  /**
   * @return the ubicacionDestino
   */
  public List<Long> getUbicacionDestino() {
    return ubicacionDestino;
  }

  /**
   * @param ubicacionDestino the ubicacionDestino to set
   */
  public void setUbicacionDestino(List<Long> ubicacionDestino) {
    this.ubicacionDestino = ubicacionDestino;
  }

  /**
   * @param ubicacionDestino the ubicacionDestino to set
   */
  public void setUbicacionDestino(Long... ubicacionDestino) {
    this.ubicacionDestino = Arrays.asList(ubicacionDestino);
  }

  /**
   * @return the ordenCampo
   */
  public String getOrdenCampo() {
    return ordenCampo;
  }

  /**
   * @param ordenCampo the ordenCampo to set
   */
  public void setOrdenCampo(String ordenCampo) {
    this.ordenCampo = ordenCampo;
  }

}
