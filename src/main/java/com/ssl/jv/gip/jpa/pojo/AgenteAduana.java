package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the agente_aduana database table.
 *
 */
@Entity
@Table(name = "agente_aduana")
@NamedQueries({
  @NamedQuery(name = "AgenteAduana.findAll", query = "SELECT a FROM AgenteAduana a"),
  @NamedQuery(name = "AgenteAduana.findAllActive", query = "SELECT a FROM AgenteAduana a WHERE a.activo = true")
})
public class AgenteAduana implements Serializable, Comparable<AgenteAduana> {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "agente_aduana_id_seq", sequenceName = "agente_aduana_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agente_aduana_id_seq")
  private Long id;

  private Boolean activo;

  private String celular;

  private String contacto;

  private String direccion;

  private String email;

  @Column(name = "lugar_entrega")
  private String lugarEntrega;

  private String nombre;

  private String telefono;

  //bi-directional many-to-one association to Cliente
  @OneToMany(mappedBy = "agenteAduana")
  private List<Cliente> clientes;

  public AgenteAduana() {
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

  public String getCelular() {
    return this.celular;
  }

  public void setCelular(String celular) {
    this.celular = celular;
  }

  public String getContacto() {
    return this.contacto;
  }

  public void setContacto(String contacto) {
    this.contacto = contacto;
  }

  public String getDireccion() {
    return this.direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLugarEntrega() {
    return this.lugarEntrega;
  }

  public void setLugarEntrega(String lugarEntrega) {
    this.lugarEntrega = lugarEntrega;
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

  public List<Cliente> getClientes() {
    return this.clientes;
  }

  public void setClientes(List<Cliente> clientes) {
    this.clientes = clientes;
  }

  public Cliente addCliente(Cliente cliente) {
    getClientes().add(cliente);
    cliente.setAgenteAduana(this);

    return cliente;
  }

  public Cliente removeCliente(Cliente cliente) {
    getClientes().remove(cliente);
    cliente.setAgenteAduana(null);

    return cliente;
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   */
  @Override
  public int compareTo(AgenteAduana o) {
    return nombre.compareTo(o.nombre);
  }

}
