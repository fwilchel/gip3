package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tipo_documento database table.
 *
 */
@Entity
@Table(name = "tipo_documento")
@NamedQuery(name = "TipoDocumento.findAll", query = "SELECT t FROM TipoDocumento t")
public class TipoDocumento implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  private String abreviatura;

  private String nombre;

  //bi-directional many-to-many association to Estado
  @ManyToMany
  @JoinTable(
      name = "estadosxdocumento", joinColumns = {
        @JoinColumn(name = "id_tipo_documento")
      }, inverseJoinColumns = {
        @JoinColumn(name = "id_estado")
      }
  )
  private List<Estado> estados;

  public TipoDocumento() {
  }

  /**
   *
   * @param id
   */
  public TipoDocumento(Long id) {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAbreviatura() {
    return this.abreviatura;
  }

  public void setAbreviatura(String abreviatura) {
    this.abreviatura = abreviatura;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<Estado> getEstados() {
    return this.estados;
  }

  public void setEstados(List<Estado> estados) {
    this.estados = estados;
  }

}
