package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estadosxdocumento database table.
 * 
 */
@Entity
@NamedQuery(name="Estadosxdocumento.findAll", query="SELECT e FROM Estadosxdocumento e")
public class Estadosxdocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EstadosxdocumentoPK id;

	//bi-directional many-to-one association to Documento
	@OneToMany(mappedBy="estadosxdocumento")
	private List<Documento> documentos;

	//bi-directional many-to-one association to Documento2
	@OneToMany(mappedBy="estadosxdocumento")
	private List<Documento2> documentos2s;

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

	public List<Documento2> getDocumentos2s() {
		return this.documentos2s;
	}

	public void setDocumentos2s(List<Documento2> documentos2s) {
		this.documentos2s = documentos2s;
	}

	public Documento2 addDocumentos2(Documento2 documentos2) {
		getDocumentos2s().add(documentos2);
		documentos2.setEstadosxdocumento(this);

		return documentos2;
	}

	public Documento2 removeDocumentos2(Documento2 documentos2) {
		getDocumentos2s().remove(documentos2);
		documentos2.setEstadosxdocumento(null);

		return documentos2;
	}

}