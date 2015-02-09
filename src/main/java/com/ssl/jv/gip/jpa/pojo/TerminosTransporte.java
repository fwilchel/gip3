package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ssl.jv.gip.web.util.Utilidad;
import javax.persistence.NamedQueries;

/**
 * The persistent class for the terminos_transporte database table.
 *
 */
@Entity
@Table(name = "terminos_transporte")
@NamedQueries({
  @NamedQuery(name = "TerminosTransporte.findAll", query = "SELECT t FROM TerminosTransporte t"),
  @NamedQuery(name = TerminosTransporte.LISTADO_IMPRIMIR_INSTRUCCION_EMBARQUE, query = "SELECT t FROM TerminosTransporte t")
})
public class TerminosTransporte implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String LISTADO_IMPRIMIR_INSTRUCCION_EMBARQUE = "SELECT    \n"
          + " terminos_transporte.id AS id,    \n"
          + " terminos_transporte.naviera AS naviera,    \n"
          + " terminos_transporte.linea AS linea,    \n"
          + " terminos_transporte.buque AS buque,    \n"
          + " terminos_transporte.flete_externo AS fleteExterno,    \n"
          + " terminos_transporte.seguro AS seguro,    \n"
          + " terminos_transporte.tipo_contenedor AS tipoContenedor,    \n"
          + " terminos_transporte.cantidad_contenedores AS cantidadContenedores,    \n"
          + " terminos_transporte.numero_contenedor AS numeroContenedor,    \n"
          + " terminos_transporte.sellos_seg AS selloSeguridad,    \n"
          + " terminos_transporte.precintos AS precintos,    \n"
          + " CASE WHEN terminos_transporte.observacion IS NULL THEN ' ' ELSE terminos_transporte.observacion END AS observacion,    \n"
          + " terminos_transporte.mes_embarque AS mesEmbarque,    \n"
          + " terminos_transporte.puerto_embarque AS puertoEmbarque,    \n"
          + " terminos_transporte.fecha_embarque AS fechaEmbarque,    \n"
          + " terminos_transporte.id_ciudad_destino AS ciudadDestinoId,    \n"
          + " CASE WHEN terminos_transporte.observacion2 IS NULL THEN ' ' ELSE terminos_transporte.observacion2 END AS observacion2,    \n"
          + " upper(ciu.nombre) AS ciudadDestinoNombre,    \n"
          + " paises.id AS paisDestinoId,    \n"
          + " upper(paises.nombre) AS paisDestinoNombre,    \n"
          + " terminos_transporte.id_modalidad_embarque AS modalidadEmbarqueId,    \n"
          + " modalidad_embarque.descripcion AS modalidadEmbarqueDescripcion,    \n"
          + " terminos_transporte.id_incoterm_despacho AS incotermDespachoId,    \n"
          + " termino_incoterm.descripcion AS incotermDespachoDecripcion,    \n"
          + " cli.id AS clienteId,    \n"
          + " cli.nit AS clienteNit,    \n"
          + " cli.nombre AS clienteNombre,    \n"
          + " cli.direccion AS clienteDireccion,    \n"
          + " cli.telefono AS clienteTelefono,    \n"
          + " cli.contacto AS clienteContacto,    \n"
          + " upper(ciu2.nombre) AS clienteCiudad,    \n"
          + " upper(pai2.nombre) AS clientePais,    \n"
          + " agente_aduana.nombre AS clienteAgenteAduanaNombre,    \n"
          + " agente_aduana.direccion AS clienteAgenteAduanaDireccion,    \n"
          + " terminos_transporte.id_agente_aduana1 AS agenteAduana1Id,    \n"
          + " aa1.nombre AS agenteAduana1Nombre,    \n"
          + " terminos_transporte.id_agente_aduana2 AS agenteAduana2Id,    \n"
          + " (CASE WHEN aa2.nombre IS NULL THEN ' ' ELSE aa2.nombre END) AS agenteAduana2Nombre,    \n"
          + " terminos_transporte.numero_booking AS numeroBooking    \n"
          + " from terminos_transporte    \n"
          + " inner join terminos_transporte_x_documento ON terminos_transporte_x_documento.id_terminos_transporte = terminos_transporte.id    \n"
          + " inner join documentos doc ON terminos_transporte_x_documento.id_documento = doc.id    \n"
          + " inner join clientes cli ON doc.id_cliente = cli.id    \n"
          + " inner join agente_aduana ON cli.id_agente_aduana = agente_aduana.id    \n"
          + " inner join ciudades ciu ON terminos_transporte.id_ciudad_destino = ciu.id    \n"
          + " inner join paises ON ciu.id_pais = paises.id    \n"
          + " inner join ciudades ciu2 ON cli.id_ciudad = ciu2.id    \n"
          + " inner join paises pai2 ON ciu2.id_pais = pai2.id    \n"
          + " inner join modalidad_embarque ON terminos_transporte.id_modalidad_embarque = modalidad_embarque.id    \n"
          + " inner join termino_incoterm ON terminos_transporte.id_incoterm_despacho = termino_incoterm.id    \n"
          + " left join agente_aduana aa1 ON terminos_transporte.id_agente_aduana1 = aa1.id    \n"
          + " left join agente_aduana aa2 ON terminos_transporte.id_agente_aduana2 = aa2.id    \n"
          + " GROUP BY    \n"
          + " terminos_transporte.id,    \n"
          + " terminos_transporte.naviera,    \n"
          + " terminos_transporte.linea, terminos_transporte.buque,    \n"
          + " terminos_transporte.flete_externo,    \n"
          + " terminos_transporte.seguro,    \n"
          + " terminos_transporte.tipo_contenedor,    \n"
          + " terminos_transporte.cantidad_contenedores,    \n"
          + " terminos_transporte.numero_contenedor,    \n"
          + " terminos_transporte.sellos_seg,    \n"
          + " terminos_transporte.precintos,    \n"
          + " terminos_transporte.id_modalidad_embarque,    \n"
          + " terminos_transporte.observacion,    \n"
          + " terminos_transporte.mes_embarque,    \n"
          + " terminos_transporte.puerto_embarque,    \n"
          + " terminos_transporte.id_incoterm_despacho,    \n"
          + " terminos_transporte.fecha_embarque,    \n"
          + " terminos_transporte.id_ciudad_destino,    \n"
          + " terminos_transporte.observacion2,    \n"
          + " ciu.nombre,    \n"
          + " paises.id,    \n"
          + " paises.nombre,    \n"
          + " modalidad_embarque.descripcion,    \n"
          + " termino_incoterm.descripcion,    \n"
          + " cli.id,    \n"
          + " cli.nit,    \n"
          + " cli.nombre,    \n"
          + " cli.direccion,    \n"
          + " cli.telefono,    \n"
          + " cli.contacto,    \n"
          + " ciu2.nombre,    \n"
          + " pai2.nombre,    \n"
          + " agente_aduana.nombre,    \n"
          + " agente_aduana.direccion,    \n"
          + " terminos_transporte.id_agente_aduana1,    \n"
          + " aa1.nombre,    \n"
          + " terminos_transporte.id_agente_aduana2,    \n"
          + " aa2.nombre,    \n"
          + " terminos_transporte.numero_booking    \n"
          + " ORDER BY id DESC";

  @Id
  @SequenceGenerator(name = "terminos_transporte_id_seq", sequenceName = "terminos_transporte_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "terminos_transporte_id_seq")
  private Long id;

  @Column(name = "afialiado_a")
  private String afialiadoA;

  private String buque;

  @Column(name = "cantidad_contenedores")
  private Integer cantidadContenedores;

  private String celular;

  private String chasis;

  private String color;

  @Column(name = "empresa_transportadora")
  private String empresaTransportadora;

  @Column(name = "fecha_embarque")
  private Timestamp fechaEmbarque;

  @Column(name = "flete_externo")
  private String fleteExterno;

  @Column(name = "id_agente_aduana1")
  private Long idAgenteAduana1;

  @Column(name = "id_agente_aduana2")
  private Long idAgenteAduana2;

  @Column(name = "identificacion_conductor")
  private String identificacionConductor;

  private String linea;

  private String marca;

  @Column(name = "mes_embarque")
  private String mesEmbarque;

  private BigDecimal modelo;

  private String motor;

  private String naviera;

  @Column(name = "nombre_conductor")
  private String nombreConductor;

  @Column(name = "numero_booking")
  private String numeroBooking;

  @Column(name = "numero_contenedor")
  private String numeroContenedor;

  private String observacion;

  private String observacion2;

  private String placa;

  private String precintos;

  @Column(name = "puerto_embarque")
  private String puertoEmbarque;

  private String seguro;

  @Column(name = "sellos_seg")
  private String sellosSeg;

  private String soat;

  @Column(name = "tipo_contenedor")
  private Integer tipoContenedor;

  private String trayler;

  //bi-directional many-to-one association to Ciudad
  @ManyToOne
  @JoinColumn(name = "id_ciudad_destino")
  private Ciudad ciudade;

  //bi-directional many-to-one association to ModalidadEmbarque
  @ManyToOne
  @JoinColumn(name = "id_modalidad_embarque")
  private ModalidadEmbarque modalidadEmbarque;

  //bi-directional many-to-one association to TerminoIncoterm
  @ManyToOne
  @JoinColumn(name = "id_incoterm_despacho")
  private TerminoIncoterm terminoIncoterm;

  //bi-directional many-to-many association to Documento
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "terminos_transporte_x_documento", joinColumns = {
    @JoinColumn(name = "id_terminos_transporte")}, inverseJoinColumns = {
    @JoinColumn(name = "id_documento")})
  private List<Documento> documentos;

  public TerminosTransporte() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAfialiadoA() {
    return this.afialiadoA;
  }

  public void setAfialiadoA(String afialiadoA) {
    this.afialiadoA = afialiadoA;
  }

  public String getBuque() {
    return this.buque;
  }

  public void setBuque(String buque) {
    this.buque = buque;
  }

  public Integer getCantidadContenedores() {
    return this.cantidadContenedores;
  }

  public void setCantidadContenedores(Integer cantidadContenedores) {
    this.cantidadContenedores = cantidadContenedores;
  }

  public String getCelular() {
    return this.celular;
  }

  public void setCelular(String celular) {
    this.celular = celular;
  }

  public String getChasis() {
    return this.chasis;
  }

  public void setChasis(String chasis) {
    this.chasis = chasis;
  }

  public String getColor() {
    return this.color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getEmpresaTransportadora() {
    return this.empresaTransportadora;
  }

  public void setEmpresaTransportadora(String empresaTransportadora) {
    this.empresaTransportadora = empresaTransportadora;
  }

  public Timestamp getFechaEmbarque() {
    return this.fechaEmbarque;
  }

  public void setFechaEmbarque(Timestamp fechaEmbarque) {
    this.fechaEmbarque = fechaEmbarque;
  }

  public String getFleteExterno() {
    return this.fleteExterno;
  }

  public void setFleteExterno(String fleteExterno) {
    this.fleteExterno = fleteExterno;
  }

  public Long getIdAgenteAduana1() {
    return this.idAgenteAduana1;
  }

  public void setIdAgenteAduana1(Long idAgenteAduana1) {
    this.idAgenteAduana1 = idAgenteAduana1;
  }

  public Long getIdAgenteAduana2() {
    return this.idAgenteAduana2;
  }

  public void setIdAgenteAduana2(Long idAgenteAduana2) {
    this.idAgenteAduana2 = idAgenteAduana2;
  }

  public String getIdentificacionConductor() {
    return this.identificacionConductor;
  }

  public void setIdentificacionConductor(String identificacionConductor) {
    this.identificacionConductor = identificacionConductor;
  }

  public String getLinea() {
    return this.linea;
  }

  public void setLinea(String linea) {
    this.linea = linea;
  }

  public String getMarca() {
    return this.marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public String getMesEmbarque() {
    return this.mesEmbarque;
  }

  public void setMesEmbarque(String mesEmbarque) {
    this.mesEmbarque = mesEmbarque;
  }

  public BigDecimal getModelo() {
    return this.modelo;
  }

  public void setModelo(BigDecimal modelo) {
    this.modelo = modelo;
  }

  public String getMotor() {
    return this.motor;
  }

  public void setMotor(String motor) {
    this.motor = motor;
  }

  public String getNaviera() {
    return this.naviera;
  }

  public void setNaviera(String naviera) {
    this.naviera = naviera;
  }

  public String getNombreConductor() {
    return this.nombreConductor;
  }

  public void setNombreConductor(String nombreConductor) {
    this.nombreConductor = nombreConductor;
  }

  public String getNumeroBooking() {
    return this.numeroBooking;
  }

  public void setNumeroBooking(String numeroBooking) {
    this.numeroBooking = numeroBooking;
  }

  public String getNumeroContenedor() {
    return this.numeroContenedor;
  }

  public void setNumeroContenedor(String numeroContenedor) {
    this.numeroContenedor = numeroContenedor;
  }

  public String getObservacion() {
    return this.observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public String getObservacion2() {
    return this.observacion2;
  }

  public void setObservacion2(String observacion2) {
    this.observacion2 = observacion2;
  }

  public String getPlaca() {
    return this.placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public String getPrecintos() {
    return this.precintos;
  }

  public void setPrecintos(String precintos) {
    this.precintos = precintos;
  }

  public String getPuertoEmbarque() {
    return this.puertoEmbarque;
  }

  public void setPuertoEmbarque(String puertoEmbarque) {
    this.puertoEmbarque = puertoEmbarque;
  }

  public String getSeguro() {
    return this.seguro;
  }

  public void setSeguro(String seguro) {
    this.seguro = seguro;
  }

  public String getSellosSeg() {
    return this.sellosSeg;
  }

  public void setSellosSeg(String sellosSeg) {
    this.sellosSeg = sellosSeg;
  }

  public String getSoat() {
    return this.soat;
  }

  public void setSoat(String soat) {
    this.soat = soat;
  }

  public Integer getTipoContenedor() {
    return this.tipoContenedor;
  }

  public void setTipoContenedor(Integer tipoContenedor) {
    this.tipoContenedor = tipoContenedor;
  }

  public String getTrayler() {
    return this.trayler;
  }

  public void setTrayler(String trayler) {
    this.trayler = trayler;
  }

  public Ciudad getCiudade() {
    return this.ciudade;
  }

  public void setCiudade(Ciudad ciudade) {
    this.ciudade = ciudade;
  }

  public ModalidadEmbarque getModalidadEmbarque() {
    return this.modalidadEmbarque;
  }

  public void setModalidadEmbarque(ModalidadEmbarque modalidadEmbarque) {
    this.modalidadEmbarque = modalidadEmbarque;
  }

  public TerminoIncoterm getTerminoIncoterm() {
    return this.terminoIncoterm;
  }

  public void setTerminoIncoterm(TerminoIncoterm terminoIncoterm) {
    this.terminoIncoterm = terminoIncoterm;
  }

  public List<Documento> getDocumentos() {
    return this.documentos;
  }

  public void setDocumentos(List<Documento> documentos) {
    this.documentos = documentos;
  }

  /**
   * Metodos para poder actualizar la fecha embarque desde la vista :: Sebastian Gamba
   */
  public Date getFechaEmbarqueDate() {
    return new Date(this.fechaEmbarque.getTime());
  }

  public void setFechaEmbarqueDate(Date date) {
    this.fechaEmbarque = Utilidad.convertirDateTotimestamp(date);
  }
}
