package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;

public class DocumentoInstruccionEmbarqueDTO implements Serializable {

  private static final long serialVersionUID = 5772395465483898864L;

  private boolean seleccionado;

  private Long id;

  private String consecutivo;

  private Date fechaGeneracion;

  private String documentoCliente;

  private Long idCliente;

  private Long idTipoDocumento;

  private Long idEstado;

  private String nombreCliente;

  private String nombreEstado;

  private String direccion;

  private String telefono;

  private String contacto;

  private Long idIncoterm;

  private String nombreIncoterm;

  private Double costoEntrega;

  private Double costoSeguro;

  private Double costoFlete;

  private Double otrosGastos;

  private String observacionesMarcacion2;

  private Double totalPesoNeto;

  private Double totalPesoBruto;

  private Double totalTendidos;

  private Double totalPallets;

  private Date fechaEsperadaEntrega;

  private Double cantidadContenedoresDe20;

  private Double cantidadContenedoresDe40;

  private String lugarIncoterm;

  private String nombreCiudad;

  private Long cantidadDiasVigencia;

  private String descripcionPago;

  private String obsrevacionDocumento;

  private String descripcionIngles;

  private boolean solicitudCafe;

  private String nombrePais;

  public DocumentoInstruccionEmbarqueDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getConsecutivo() {
    return consecutivo;
  }

  public void setConsecutivo(String consecutivo) {
    this.consecutivo = consecutivo;
  }

  public Date getFechaGeneracion() {
    return fechaGeneracion;
  }

  public void setFechaGeneracion(Date fechaGeneracion) {
    this.fechaGeneracion = fechaGeneracion;
  }

  public String getDocumentoCliente() {
    return documentoCliente;
  }

  public void setDocumentoCliente(String documentoCliente) {
    this.documentoCliente = documentoCliente;
  }

  public Long getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(Long idCliente) {
    this.idCliente = idCliente;
  }

  public Long getIdTipoDocumento() {
    return idTipoDocumento;
  }

  public void setIdTipoDocumento(Long idTipoDocumento) {
    this.idTipoDocumento = idTipoDocumento;
  }

  public Long getIdEstado() {
    return idEstado;
  }

  public void setIdEstado(Long idEstado) {
    this.idEstado = idEstado;
  }

  public String getNombreCliente() {
    return nombreCliente;
  }

  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  public String getNombreEstado() {
    return nombreEstado;
  }

  public void setNombreEstado(String nombreEstado) {
    this.nombreEstado = nombreEstado;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getContacto() {
    return contacto;
  }

  public void setContacto(String contacto) {
    this.contacto = contacto;
  }

  public Long getIdIncoterm() {
    return idIncoterm;
  }

  public void setIdIncoterm(Long idIncoterm) {
    this.idIncoterm = idIncoterm;
  }

  public String getNombreIncoterm() {
    return nombreIncoterm;
  }

  public void setNombreIncoterm(String nombreIncoterm) {
    this.nombreIncoterm = nombreIncoterm;
  }

  public Double getCostoEntrega() {
    return costoEntrega;
  }

  public void setCostoEntrega(Double costoEntrega) {
    this.costoEntrega = costoEntrega;
  }

  public Double getCostoSeguro() {
    return costoSeguro;
  }

  public void setCostoSeguro(Double costoSeguro) {
    this.costoSeguro = costoSeguro;
  }

  public Double getCostoFlete() {
    return costoFlete;
  }

  public void setCostoFlete(Double costoFlete) {
    this.costoFlete = costoFlete;
  }

  public Double getOtrosGastos() {
    return otrosGastos;
  }

  public void setOtrosGastos(Double otrosGastos) {
    this.otrosGastos = otrosGastos;
  }

  public String getObservacionesMarcacion2() {
    return observacionesMarcacion2;
  }

  public void setObservacionesMarcacion2(String observacionesMarcacion2) {
    this.observacionesMarcacion2 = observacionesMarcacion2;
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

  public Double getTotalTendidos() {
    return totalTendidos;
  }

  public void setTotalTendidos(Double totalTendidos) {
    this.totalTendidos = totalTendidos;
  }

  public Double getTotalPallets() {
    return totalPallets;
  }

  public void setTotalPallets(Double totalPallets) {
    this.totalPallets = totalPallets;
  }

  public Date getFechaEsperadaEntrega() {
    return fechaEsperadaEntrega;
  }

  public void setFechaEsperadaEntrega(Date fechaEsperadaEntrega) {
    this.fechaEsperadaEntrega = fechaEsperadaEntrega;
  }

  public Double getCantidadContenedoresDe20() {
    return cantidadContenedoresDe20;
  }

  public void setCantidadContenedoresDe20(Double cantidadContenedoresDe20) {
    this.cantidadContenedoresDe20 = cantidadContenedoresDe20;
  }

  public Double getCantidadContenedoresDe40() {
    return cantidadContenedoresDe40;
  }

  public void setCantidadContenedoresDe40(Double cantidadContenedoresDe40) {
    this.cantidadContenedoresDe40 = cantidadContenedoresDe40;
  }

  public String getLugarIncoterm() {
    return lugarIncoterm;
  }

  public void setLugarIncoterm(String lugarIncoterm) {
    this.lugarIncoterm = lugarIncoterm;
  }

  public String getNombreCiudad() {
    return nombreCiudad;
  }

  public void setNombreCiudad(String nombreCiudad) {
    this.nombreCiudad = nombreCiudad;
  }

  public Long getCantidadDiasVigencia() {
    return cantidadDiasVigencia;
  }

  public void setCantidadDiasVigencia(Long cantidadDiasVigencia) {
    this.cantidadDiasVigencia = cantidadDiasVigencia;
  }

  public String getDescripcionPago() {
    return descripcionPago;
  }

  public void setDescripcionPago(String descripcionPago) {
    this.descripcionPago = descripcionPago;
  }

  public String getObsrevacionDocumento() {
    return obsrevacionDocumento;
  }

  public void setObsrevacionDocumento(String obsrevacionDocumento) {
    this.obsrevacionDocumento = obsrevacionDocumento;
  }

  public String getDescripcionIngles() {
    return descripcionIngles;
  }

  public void setDescripcionIngles(String descripcionIngles) {
    this.descripcionIngles = descripcionIngles;
  }

  public boolean isSolicitudCafe() {
    return solicitudCafe;
  }

  public void setSolicitudCafe(boolean solicitudCafe) {
    this.solicitudCafe = solicitudCafe;
  }

  public String getNombrePais() {
    return nombrePais;
  }

  public void setNombrePais(String nombrePais) {
    this.nombrePais = nombrePais;
  }

  public boolean isSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(boolean seleccionado) {
    this.seleccionado = seleccionado;
  }

}
