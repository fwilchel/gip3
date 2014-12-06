package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the documento_x_negociacion database table.
 * 
 */
@Embeddable
public class DocumentoXNegociacionPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="id_documento")
	private Long idDocumento;

	@Column(name="id_termino_incoterm")
	private Long idTerminoIncoterm;
	

	public DocumentoXNegociacionPK() {
	}


	public Long getIdDocumento() {
		return idDocumento;
	}


	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}


	public Long getIdTerminoIncoterm() {
		return idTerminoIncoterm;
	}


	public void setIdTerminoIncoterm(Long idTerminoIncoterm) {
		this.idTerminoIncoterm = idTerminoIncoterm;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDocumento == null) ? 0 : idDocumento.hashCode());
		result = prime
				* result
				+ ((idTerminoIncoterm == null) ? 0 : idTerminoIncoterm
						.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoXNegociacionPK other = (DocumentoXNegociacionPK) obj;
		if (idDocumento == null) {
			if (other.idDocumento != null)
				return false;
		} else if (!idDocumento.equals(other.idDocumento))
			return false;
		if (idTerminoIncoterm == null) {
			if (other.idTerminoIncoterm != null)
				return false;
		} else if (!idTerminoIncoterm.equals(other.idTerminoIncoterm))
			return false;
		return true;
	}


}