package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the usuariosxgeografias database table.
 * 
 */
@Embeddable
public class UsuariosXGeografiaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_usuario")
	private String idUsuario;

	@Column(name="id_geografia")
	private Long idGeografia;

	@Column(name="tipo_geografia")
	private String tipoGeografia;

	public UsuariosXGeografiaPK() {
	}
	public String getIdUsuario() {
		return this.idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdGeografia() {
		return this.idGeografia;
	}
	public void setIdGeografia(Long idGeografia) {
		this.idGeografia = idGeografia;
	}
	public String getTipoGeografia() {
		return this.tipoGeografia;
	}
	public void setTipoGeografia(String tipoGeografia) {
		this.tipoGeografia = tipoGeografia;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsuariosXGeografiaPK)) {
			return false;
		}
		UsuariosXGeografiaPK castOther = (UsuariosXGeografiaPK)other;
		return 
			this.idUsuario.equals(castOther.idUsuario)
			&& this.idGeografia.equals(castOther.idGeografia)
			&& this.tipoGeografia.equals(castOther.tipoGeografia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idUsuario.hashCode();
		hash = hash * prime + this.idGeografia.hashCode();
		hash = hash * prime + this.tipoGeografia.hashCode();
		
		return hash;
	}
}