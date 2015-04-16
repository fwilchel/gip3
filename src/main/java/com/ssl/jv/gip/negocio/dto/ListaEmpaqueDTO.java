package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The Class ListaEmpaqueDTO.
 */
public class ListaEmpaqueDTO implements Serializable {

  private static final long serialVersionUID = 6826470281110432159L;

  private ClienteDTO cliente;
  private String idDocumento;
  private String consecutivoDocumento;
  private String numeroPedidoWeb;
  private Timestamp fechaGeneracion;
  private Timestamp fechaEntrega;
  private Timestamp fechaEsperadaEntrega;
  private String idTipoDocumento;
  private String idTerminoIncoterm;
  private String idUbicacionOrigen;
  private String idUbicacionDestino;
  private String idProveedor;
  private String idEstado;
  private String descripcionTerminoIncoterm;
  private BigDecimal valorTotalDocumento;
  private BigDecimal costoEntrega;
  private BigDecimal costoFlete;
  private BigDecimal costoSeguro;
  private BigDecimal otrosGastos;
  private BigDecimal cantidadContenedores20;
  private BigDecimal cantidadContenedores40;
  private String ciudadNombre;
  private String lugarIncoterm;
  private String estadoNombre;
  private Boolean solicitudCafe;
  private String observacionDocumento;
  private String observacionMarcacion;
  private String sitioEntrega;
  private String canal;
  private Double cantidadEstibas;
  private Double pesoBrutoEstibas;
  private Double totalPallets;
  private Double totalTendidos;
  private Double totalPesoNeto;
  private Double totalPesoBruto;
  private String cantidadDiasVigencia;

  public String getIdDocumento() {
    return idDocumento;
  }

  public void setIdDocumento(String idDocumento) {
    this.idDocumento = idDocumento;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public Timestamp getFechaGeneracion() {
    return fechaGeneracion;
  }

  public void setFechaGeneracion(Timestamp fechaGeneracion) {
    this.fechaGeneracion = fechaGeneracion;
  }

  public ClienteDTO getCliente() {
    return cliente;
  }

  public void setCliente(ClienteDTO cliente) {
    this.cliente = cliente;
  }

  public String getIdUbicacionOrigen() {
    return idUbicacionOrigen;
  }

  public void setIdUbicacionOrigen(String idUbicacionOrigen) {
    this.idUbicacionOrigen = idUbicacionOrigen;
  }

  public String getIdUbicacionDestino() {
    return idUbicacionDestino;
  }

  public void setIdUbicacionDestino(String idUbicacionDestino) {
    this.idUbicacionDestino = idUbicacionDestino;
  }

  public String getIdTipoDocumento() {
    return idTipoDocumento;
  }

  public void setIdTipoDocumento(String idTipoDocumento) {
    this.idTipoDocumento = idTipoDocumento;
  }

  public Timestamp getFechaEntrega() {
    return fechaEntrega;
  }

  public void setFechaEntrega(Timestamp fechaEntrega) {
    this.fechaEntrega = fechaEntrega;
  }

  public String getIdProveedor() {
    return idProveedor;
  }

  public void setIdProveedor(String idProveedor) {
    this.idProveedor = idProveedor;
  }

  public String getIdEstado() {
    return idEstado;
  }

  public void setIdEstado(String idEstado) {
    this.idEstado = idEstado;
  }

  public String getIdTerminoIncoterm() {
    return idTerminoIncoterm;
  }

  public Double getTotalPallets() {
    return totalPallets;
  }

  public Timestamp getFechaEsperadaEntrega() {
    return fechaEsperadaEntrega;
  }

  public void setFechaEsperadaEntrega(Timestamp fechaEsperadaEntrega) {
    this.fechaEsperadaEntrega = fechaEsperadaEntrega;
  }

  public void setTotalPallets(Double totalPallets) {
    this.totalPallets = totalPallets;
  }

  public void setIdTerminoIncoterm(String idTerminoIncoterm) {
    this.idTerminoIncoterm = idTerminoIncoterm;
  }

  public String getDescripcionTerminoIncoterm() {
    return descripcionTerminoIncoterm;
  }

  public void setDescripcionTerminoIncoterm(String descripcionTerminoIncoterm) {
    this.descripcionTerminoIncoterm = descripcionTerminoIncoterm;
  }

  public String getCantidadDiasVigencia() {
    return cantidadDiasVigencia;
  }

  public void setCantidadDiasVigencia(String cantidadDiasVigencia) {
    this.cantidadDiasVigencia = cantidadDiasVigencia;
  }

  public BigDecimal getValorTotalDocumento() {
    return valorTotalDocumento;
  }

  public void setValorTotalDocumento(BigDecimal valorTotalDocumento) {
    this.valorTotalDocumento = valorTotalDocumento;
  }

  public BigDecimal getCostoEntrega() {
    return costoEntrega;
  }

  public void setCostoEntrega(BigDecimal costoEntrega) {
    this.costoEntrega = costoEntrega;
  }

  public BigDecimal getCostoFlete() {
    return costoFlete;
  }

  public void setCostoFlete(BigDecimal costoFlete) {
    this.costoFlete = costoFlete;
  }

  public BigDecimal getCostoSeguro() {
    return costoSeguro;
  }

  public void setCostoSeguro(BigDecimal costoSeguro) {
    this.costoSeguro = costoSeguro;
  }

  public BigDecimal getOtrosGastos() {
    return otrosGastos;
  }

  public void setOtrosGastos(BigDecimal otrosGastos) {
    this.otrosGastos = otrosGastos;
  }

  public BigDecimal getCantidadContenedores20() {
    return cantidadContenedores20;
  }

  public void setCantidadContenedores20(BigDecimal cantidadContenedores20) {
    this.cantidadContenedores20 = cantidadContenedores20;
  }

  public BigDecimal getCantidadContenedores40() {
    return cantidadContenedores40;
  }

  public void setCantidadContenedores40(BigDecimal cantidadContenedores40) {
    this.cantidadContenedores40 = cantidadContenedores40;
  }

  public String getCiudadNombre() {
    return ciudadNombre;
  }

  public void setCiudadNombre(String ciudadNombre) {
    this.ciudadNombre = ciudadNombre;
  }

  public String getLugarIncoterm() {
    return lugarIncoterm;
  }

  public void setLugarIncoterm(String lugarIncoterm) {
    this.lugarIncoterm = lugarIncoterm;
  }

  public String getEstadoNombre() {
    return estadoNombre;
  }

  public Double getTotalTendidos() {
    return totalTendidos;
  }

  public void setTotalTendidos(Double totalTendidos) {
    this.totalTendidos = totalTendidos;
  }

  public Double getTotalPesoNeto() {
    return totalPesoNeto;
  }

  public void setTotalPesoNeto(Double totalPesoNeto) {
    this.totalPesoNeto = totalPesoNeto;
  }

  public Double getTotalPesoBruto() {
    return totalPesoBruto;
  }

  public void setTotalPesoBruto(Double totalPesoBruto) {
    this.totalPesoBruto = totalPesoBruto;
  }

  public String getCanal() {
    return canal;
  }

  public void setCanal(String canal) {
    this.canal = canal;
  }

  public void setEstadoNombre(String estadoNombre) {
    this.estadoNombre = estadoNombre;
  }

  public Boolean getSolicitudCafe() {
    return solicitudCafe;
  }

  public void setSolicitudCafe(Boolean solicitudCafe) {
    this.solicitudCafe = solicitudCafe;
  }

  public String getObservacionDocumento() {
    return observacionDocumento;
  }

  public Double getCantidadEstibas() {
    return cantidadEstibas;
  }

  public void setCantidadEstibas(Double cantidadEstibas) {
    this.cantidadEstibas = cantidadEstibas;
  }

  public Double getPesoBrutoEstibas() {
    return pesoBrutoEstibas;
  }

  public void setPesoBrutoEstibas(Double pesoBrutoEstibas) {
    this.pesoBrutoEstibas = pesoBrutoEstibas;
  }

  public void setObservacionDocumento(String observacionDocumento) {
    this.observacionDocumento = observacionDocumento;
  }

  public String getObservacionMarcacion() {
    return observacionMarcacion;
  }

  public void setObservacionMarcacion(String observacionesMarcacion2) {
    this.observacionMarcacion = observacionesMarcacion2;
  }

  public String getNumeroPedidoWeb() {
    return numeroPedidoWeb;
  }

  public void setNumeroPedidoWeb(String numeroPedidoWeb) {
    this.numeroPedidoWeb = numeroPedidoWeb;
  }

  public String getSitioEntrega() {
    return sitioEntrega;
  }

  public void setSitioEntrega(String sitioEntrega) {
    this.sitioEntrega = sitioEntrega;
  }

}
