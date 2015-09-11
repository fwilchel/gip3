package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the punto_venta database table.
 *
 */
@Entity
@Table(name = "punto_venta")
@NamedQueries({
  @NamedQuery(name = PuntoVenta.FIND_BY_ID, query = "SELECT p FROM PuntoVenta p JOIN FETCH p.cliente JOIN FETCH p.ubicacion WHERE p.id = :id"),
  @NamedQuery(name = "PuntoVenta.findAll", query = "SELECT p FROM PuntoVenta p"),
  @NamedQuery(name = "PuntoVenta.findByCliente", query = "SELECT p FROM PuntoVenta p WHERE p.cliente.id = :idCliente"),
  @NamedQuery(name = PuntoVenta.FIND_BY_USUARIO, query = "SELECT p FROM PuntoVenta p JOIN FETCH p.cliente WHERE p.usuario.id = :idUsuario"),
  @NamedQuery(name = PuntoVenta.FIND_BY_CODIGO_BARRAS, query = "SELECT p FROM PuntoVenta p WHERE p.codigoBarras = :codigoBarras")
})
public class PuntoVenta implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String FIND_BY_ID = "PuntoVenta.findByID";
  public static final String FIND_BY_OBSERVACION_DOCUMENTO = "Documento.findByObservacionDocumento";
  public static final String FIND_BY_USUARIO = "PuntoVenta.findByUsuario";
  public static final String FIND_BY_CODIGO_BARRAS = "PuntoVenta.findByCodigoBarras";
  @Id
  @SequenceGenerator(name = "punto_venta_id_seq1", sequenceName = "punto_venta_id_seq1", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "punto_venta_id_seq1")
  private Long id;

  private Boolean activo;

  @Column(name = "cod_despacho_sap")
  private String codDespachoSap;

  @Column(name = "codigo_barras")
  private Long codigoBarras;

  private String direccion;

  private String nombre;

  private String telefono;

  // bi-directional many-to-one association to Ciudad
  @ManyToOne
  @JoinColumn(name = "id_ciudad")
  private Ciudad ciudade;

  // bi-directional many-to-one association to Cliente
  @ManyToOne
  @JoinColumn(name = "id_cliente")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "usuario")
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "ubicacion")
  private Ubicacion ubicacion;

  public PuntoVenta() {
  }

  public PuntoVenta(Long id) {
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getActivo() {
    return this.activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public String getCodDespachoSap() {
    return this.codDespachoSap;
  }

  public void setCodDespachoSap(String codDespachoSap) {
    this.codDespachoSap = codDespachoSap;
  }

  public Long getCodigoBarras() {
    return this.codigoBarras;
  }

  public void setCodigoBarras(Long codigoBarras) {
    this.codigoBarras = codigoBarras;
  }

  public String getDireccion() {
    return this.direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTelefono() {
    return this.telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public Ciudad getCiudade() {
    return this.ciudade;
  }

  public void setCiudade(Ciudad ciudade) {
    this.ciudade = ciudade;
  }

  public Cliente getCliente() {
    return this.cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  /**
   * @return the usuario
   */
  public Usuario getUsuario() {
    return usuario;
  }

  /**
   * @param usuario the usuario to set
   */
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  /**
   * @return the ubicacion
   */
  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  /**
   * @param ubicacion the ubicacion to set
   */
  public void setUbicacion(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }
}
