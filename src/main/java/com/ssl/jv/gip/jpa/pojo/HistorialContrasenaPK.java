package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the historial_contrasena database table.
 * 
 */
@Embeddable
public class HistorialContrasenaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private String contrasena;

	//bi-directional many-to-one association to Usuario
	@Column(name="id_usuario")
	private String usuarioId;

	public HistorialContrasenaPK() {
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contrasena == null) ? 0 : contrasena.hashCode());
		result = prime * result
				+ ((usuarioId == null) ? 0 : usuarioId.hashCode());
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
		HistorialContrasenaPK other = (HistorialContrasenaPK) obj;
		if (contrasena == null) {
			if (other.contrasena != null)
				return false;
		} else if (!contrasena.equals(other.contrasena))
			return false;
		if (usuarioId == null) {
			if (other.usuarioId != null)
				return false;
		} else if (!usuarioId.equals(other.usuarioId))
			return false;
		return true;
	}


}