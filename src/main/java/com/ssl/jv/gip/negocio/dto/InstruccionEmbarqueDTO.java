package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * Title: InstruccionEmbarqueDTO</p>
 *
 * <p>
 * Description: DTO para almacenar la informacion de las instrucciones de embarque</p>
 *
 * <p>
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Soft Studio Ltda.</p>
 *
 * @author Alejandro Poveda
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
@Entity
public class InstruccionEmbarqueDTO implements Serializable {

  @Id
  private Long id;
  private String naviera;
  private String linea;
  private String buque;
  private String fleteExterno;
  private String seguro;
  private Integer tipoContenedor;
  private Integer cantidadContenedores;
  private String numeroContenedor;
  private String selloSeguridad;
  private String precintos;
  private String observacion;
  private String mesEmbarque;
  private String puertoEmbarque;
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEmbarque;
  private String observacion2;
  private Long ciudadDestinoId;
  private String ciudadDestinoNombre;
  private String paisDestinoId;
  private String paisDestinoNombre;
  private Long modalidadEmbarqueId;
  private String modalidadEmbarqueDescripcion;
  private String incotermDespachoId;
  private String incotermDespachoDecripcion;
  private Long clienteId;
  private String clienteNit;
  private String clienteNombre;
  private String clienteDireccion;
  private String clienteTelefono;
  private String clienteContacto;
  private String clienteCiudad;
  private String clientePais;
  private String clienteAgenteAduanaNombre;
  private String clienteAgenteAduanaDireccion;
  private Long agenteAduana1Id;
  private String agenteAduana1Nombre;
  private Long agenteAduana2Id;
  private String agenteAduana2Nombre;
  private String numeroBooking;

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
   * @return the naviera
   */
  public String getNaviera() {
    return naviera;
  }

  /**
   * @param naviera the naviera to set
   */
  public void setNaviera(String naviera) {
    this.naviera = naviera;
  }

  /**
   * @return the linea
   */
  public String getLinea() {
    return linea;
  }

  /**
   * @param linea the linea to set
   */
  public void setLinea(String linea) {
    this.linea = linea;
  }

  /**
   * @return the buque
   */
  public String getBuque() {
    return buque;
  }

  /**
   * @param buque the buque to set
   */
  public void setBuque(String buque) {
    this.buque = buque;
  }

  /**
   * @return the fleteExterno
   */
  public String getFleteExterno() {
    return fleteExterno;
  }

  /**
   * @param fleteExterno the fleteExterno to set
   */
  public void setFleteExterno(String fleteExterno) {
    this.fleteExterno = fleteExterno;
  }

  /**
   * @return the seguro
   */
  public String getSeguro() {
    return seguro;
  }

  /**
   * @param seguro the seguro to set
   */
  public void setSeguro(String seguro) {
    this.seguro = seguro;
  }

  /**
   * @return the tipoContenedor
   */
  public Integer getTipoContenedor() {
    return tipoContenedor;
  }

  /**
   * @param tipoContenedor the tipoContenedor to set
   */
  public void setTipoContenedor(Integer tipoContenedor) {
    this.tipoContenedor = tipoContenedor;
  }

  /**
   * @return the cantidadContenedores
   */
  public Integer getCantidadContenedores() {
    return cantidadContenedores;
  }

  /**
   * @param cantidadContenedores the cantidadContenedores to set
   */
  public void setCantidadContenedores(Integer cantidadContenedores) {
    this.cantidadContenedores = cantidadContenedores;
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
   * @return the selloSeguridad
   */
  public String getSelloSeguridad() {
    return selloSeguridad;
  }

  /**
   * @param selloSeguridad the selloSeguridad to set
   */
  public void setSelloSeguridad(String selloSeguridad) {
    this.selloSeguridad = selloSeguridad;
  }

  /**
   * @return the precintos
   */
  public String getPrecintos() {
    return precintos;
  }

  /**
   * @param precintos the precintos to set
   */
  public void setPrecintos(String precintos) {
    this.precintos = precintos;
  }

  /**
   * @return the observacion
   */
  public String getObservacion() {
    return observacion;
  }

  /**
   * @param observacion the observacion to set
   */
  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  /**
   * @return the mesEmbarque
   */
  public String getMesEmbarque() {
    return mesEmbarque;
  }

  /**
   * @param mesEmbarque the mesEmbarque to set
   */
  public void setMesEmbarque(String mesEmbarque) {
    this.mesEmbarque = mesEmbarque;
  }

  /**
   * @return the puertoEmbarque
   */
  public String getPuertoEmbarque() {
    return puertoEmbarque;
  }

  /**
   * @param puertoEmbarque the puertoEmbarque to set
   */
  public void setPuertoEmbarque(String puertoEmbarque) {
    this.puertoEmbarque = puertoEmbarque;
  }

  /**
   * @return the fechaEmbarque
   */
  public Date getFechaEmbarque() {
    return fechaEmbarque;
  }

  /**
   * @param fechaEmbarque the fechaEmbarque to set
   */
  public void setFechaEmbarque(Date fechaEmbarque) {
    this.fechaEmbarque = fechaEmbarque;
  }

  /**
   * @return the observacion2
   */
  public String getObservacion2() {
    return observacion2;
  }

  /**
   * @param observacion2 the observacion2 to set
   */
  public void setObservacion2(String observacion2) {
    this.observacion2 = observacion2;
  }

  /**
   * @return the ciudadDestinoId
   */
  public Long getCiudadDestinoId() {
    return ciudadDestinoId;
  }

  /**
   * @param ciudadDestinoId the ciudadDestinoId to set
   */
  public void setCiudadDestinoId(Long ciudadDestinoId) {
    this.ciudadDestinoId = ciudadDestinoId;
  }

  /**
   * @return the ciudadDestinoNombre
   */
  public String getCiudadDestinoNombre() {
    return ciudadDestinoNombre;
  }

  /**
   * @param ciudadDestinoNombre the ciudadDestinoNombre to set
   */
  public void setCiudadDestinoNombre(String ciudadDestinoNombre) {
    this.ciudadDestinoNombre = ciudadDestinoNombre;
  }

  /**
   * @return the paisDestinoId
   */
  public String getPaisDestinoId() {
    return paisDestinoId;
  }

  /**
   * @param paisDestinoId the paisDestinoId to set
   */
  public void setPaisDestinoId(String paisDestinoId) {
    this.paisDestinoId = paisDestinoId;
  }

  /**
   * @return the paisDestinoNombre
   */
  public String getPaisDestinoNombre() {
    return paisDestinoNombre;
  }

  /**
   * @param paisDestinoNombre the paisDestinoNombre to set
   */
  public void setPaisDestinoNombre(String paisDestinoNombre) {
    this.paisDestinoNombre = paisDestinoNombre;
  }

  /**
   * @return the modalidadEmbarqueId
   */
  public Long getModalidadEmbarqueId() {
    return modalidadEmbarqueId;
  }

  /**
   * @param modalidadEmbarqueId the modalidadEmbarqueId to set
   */
  public void setModalidadEmbarqueId(Long modalidadEmbarqueId) {
    this.modalidadEmbarqueId = modalidadEmbarqueId;
  }

  /**
   * @return the modalidadEmbarqueDescripcion
   */
  public String getModalidadEmbarqueDescripcion() {
    return modalidadEmbarqueDescripcion;
  }

  /**
   * @param modalidadEmbarqueDescripcion the modalidadEmbarqueDescripcion to set
   */
  public void setModalidadEmbarqueDescripcion(String modalidadEmbarqueDescripcion) {
    this.modalidadEmbarqueDescripcion = modalidadEmbarqueDescripcion;
  }

  /**
   * @return the incotermDespachoId
   */
  public String getIncotermDespachoId() {
    return incotermDespachoId;
  }

  /**
   * @param incotermDespachoId the incotermDespachoId to set
   */
  public void setIncotermDespachoId(String incotermDespachoId) {
    this.incotermDespachoId = incotermDespachoId;
  }

  /**
   * @return the incotermDespachoDecripcion
   */
  public String getIncotermDespachoDecripcion() {
    return incotermDespachoDecripcion;
  }

  /**
   * @param incotermDespachoDecripcion the incotermDespachoDecripcion to set
   */
  public void setIncotermDespachoDecripcion(String incotermDespachoDecripcion) {
    this.incotermDespachoDecripcion = incotermDespachoDecripcion;
  }

  /**
   * @return the clienteId
   */
  public Long getClienteId() {
    return clienteId;
  }

  /**
   * @param clienteId the clienteId to set
   */
  public void setClienteId(Long clienteId) {
    this.clienteId = clienteId;
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
   * @return the clienteDireccion
   */
  public String getClienteDireccion() {
    return clienteDireccion;
  }

  /**
   * @param clienteDireccion the clienteDireccion to set
   */
  public void setClienteDireccion(String clienteDireccion) {
    this.clienteDireccion = clienteDireccion;
  }

  /**
   * @return the clienteTelefono
   */
  public String getClienteTelefono() {
    return clienteTelefono;
  }

  /**
   * @param clienteTelefono the clienteTelefono to set
   */
  public void setClienteTelefono(String clienteTelefono) {
    this.clienteTelefono = clienteTelefono;
  }

  /**
   * @return the clienteContacto
   */
  public String getClienteContacto() {
    return clienteContacto;
  }

  /**
   * @param clienteContacto the clienteContacto to set
   */
  public void setClienteContacto(String clienteContacto) {
    this.clienteContacto = clienteContacto;
  }

  /**
   * @return the clienteCiudad
   */
  public String getClienteCiudad() {
    return clienteCiudad;
  }

  /**
   * @param clienteCiudad the clienteCiudad to set
   */
  public void setClienteCiudad(String clienteCiudad) {
    this.clienteCiudad = clienteCiudad;
  }

  /**
   * @return the clientePais
   */
  public String getClientePais() {
    return clientePais;
  }

  /**
   * @param clientePais the clientePais to set
   */
  public void setClientePais(String clientePais) {
    this.clientePais = clientePais;
  }

  /**
   * @return the clienteAgenteAduanaNombre
   */
  public String getClienteAgenteAduanaNombre() {
    return clienteAgenteAduanaNombre;
  }

  /**
   * @param clienteAgenteAduanaNombre the clienteAgenteAduanaNombre to set
   */
  public void setClienteAgenteAduanaNombre(String clienteAgenteAduanaNombre) {
    this.clienteAgenteAduanaNombre = clienteAgenteAduanaNombre;
  }

  /**
   * @return the clienteAgenteAduanaDireccion
   */
  public String getClienteAgenteAduanaDireccion() {
    return clienteAgenteAduanaDireccion;
  }

  /**
   * @param clienteAgenteAduanaDireccion the clienteAgenteAduanaDireccion to set
   */
  public void setClienteAgenteAduanaDireccion(String clienteAgenteAduanaDireccion) {
    this.clienteAgenteAduanaDireccion = clienteAgenteAduanaDireccion;
  }

  /**
   * @return the agenteAduana1Id
   */
  public Long getAgenteAduana1Id() {
    return agenteAduana1Id;
  }

  /**
   * @param agenteAduana1Id the agenteAduana1Id to set
   */
  public void setAgenteAduana1Id(Long agenteAduana1Id) {
    this.agenteAduana1Id = agenteAduana1Id;
  }

  /**
   * @return the agenteAduana1Nombre
   */
  public String getAgenteAduana1Nombre() {
    return agenteAduana1Nombre;
  }

  /**
   * @param agenteAduana1Nombre the agenteAduana1Nombre to set
   */
  public void setAgenteAduana1Nombre(String agenteAduana1Nombre) {
    this.agenteAduana1Nombre = agenteAduana1Nombre;
  }

  /**
   * @return the agenteAduana2Id
   */
  public Long getAgenteAduana2Id() {
    return agenteAduana2Id;
  }

  /**
   * @param agenteAduana2Id the agenteAduana2Id to set
   */
  public void setAgenteAduana2Id(Long agenteAduana2Id) {
    this.agenteAduana2Id = agenteAduana2Id;
  }

  /**
   * @return the agenteAduana2Nombre
   */
  public String getAgenteAduana2Nombre() {
    return agenteAduana2Nombre;
  }

  /**
   * @param agenteAduana2Nombre the agenteAduana2Nombre to set
   */
  public void setAgenteAduana2Nombre(String agenteAduana2Nombre) {
    this.agenteAduana2Nombre = agenteAduana2Nombre;
  }

  /**
   * @return the numeroBooking
   */
  public String getNumeroBooking() {
    return numeroBooking;
  }

  /**
   * @param numeroBooking the numeroBooking to set
   */
  public void setNumeroBooking(String numeroBooking) {
    this.numeroBooking = numeroBooking;
  }

}
