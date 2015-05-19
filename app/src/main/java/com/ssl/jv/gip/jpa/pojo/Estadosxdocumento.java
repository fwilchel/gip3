package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * The persistent class for the estadosxdocumento database table.
 *
 */
@Entity
@NamedQuery(name = "Estadosxdocumento.findAll", query = "SELECT e FROM Estadosxdocumento e")
public class Estadosxdocumento implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private EstadosxdocumentoPK id;

  @ManyToOne
  @JoinColumn(name = "id_estado", insertable = false, updatable = false)
  private Estado estado;

  @ManyToOne
  @JoinColumn(name = "id_tipo_documento", insertable = false, updatable = false)
  private TipoDocumento tipoDocumento;

  // bi-directional many-to-one association to Documento
  @OneToMany(mappedBy = "estadosxdocumento")
  private List<Documento> documentos;

  public Estadosxdocumento() {
  }

  public EstadosxdocumentoPK getId() {
    return this.id;
  }

  public void setId(EstadosxdocumentoPK id) {
    this.id = id;
  }

  public List<Documento> getDocumentos() {
    return this.documentos;
  }

  public void setDocumentos(List<Documento> documentos) {
    this.documentos = documentos;
  }

  public Documento addDocumento(Documento documento) {
    getDocumentos().add(documento);
    documento.setEstadosxdocumento(this);

    return documento;
  }

  public Documento removeDocumento(Documento documento) {
    getDocumentos().remove(documento);
    documento.setEstadosxdocumento(null);

    return documento;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public TipoDocumento getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumento tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

}
