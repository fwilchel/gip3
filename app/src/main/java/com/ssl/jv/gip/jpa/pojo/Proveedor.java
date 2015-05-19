package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the proveedores database table.
 *
 */
@Entity
@Table(name = "proveedores")
@NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")
public class Proveedor implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  private String ciudad;

  @Column(name = "codigo_postal")
  private String codigoPostal;

  @Column(name = "codigo_sap")
  private String codigoSap;

  private Long consecutivo;

  private String contacto;

  private String direccion;

  private String email;

  private String fax;

  private String nit;

  private String nombre;

  private String pais;

  private String telefono;

  @Column(name = "tipo_documento")
  private String tipoDocumento;

  //bi-directional many-to-one association to Documento
  @OneToMany(mappedBy = "proveedore")
  private List<Documento> documentos;

  public Proveedor() {
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCiudad() {
    return this.ciudad;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public String getCodigoPostal() {
    return this.codigoPostal;
  }

  public void setCodigoPostal(String codigoPostal) {
    this.codigoPostal = codigoPostal;
  }

  public String getCodigoSap() {
    return this.codigoSap;
  }

  public void setCodigoSap(String codigoSap) {
    this.codigoSap = codigoSap;
  }

  public Long getConsecutivo() {
    return this.consecutivo;
  }

  public void setConsecutivo(Long consecutivo) {
    this.consecutivo = consecutivo;
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

  public String getFax() {
    return this.fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getNit() {
    return this.nit;
  }

  public void setNit(String nit) {
    this.nit = nit;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getPais() {
    return this.pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getTelefono() {
    return this.telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getTipoDocumento() {
    return this.tipoDocumento;
  }

  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public List<Documento> getDocumentos() {
    return this.documentos;
  }

  public void setDocumentos(List<Documento> documentos) {
    this.documentos = documentos;
  }

  public Documento addDocumento(Documento documento) {
    getDocumentos().add(documento);
    documento.setProveedore(this);

    return documento;
  }

  public Documento removeDocumento(Documento documento) {
    getDocumentos().remove(documento);
    documento.setProveedore(null);

    return documento;
  }
}
