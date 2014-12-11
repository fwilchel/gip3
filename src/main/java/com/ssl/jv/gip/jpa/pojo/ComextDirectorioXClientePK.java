package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the comext_directorio_x_cliente database table.
 * 
 */
@Embeddable
public class ComextDirectorioXClientePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="id_directorio")
	private Integer idDirectorio;

	@Column(name="user_id")
	private Long userId;

	public ComextDirectorioXClientePK() {
	}

	public Integer getIdDirectorio() {
		return this.idDirectorio;
	}

	public void setIdDirectorio(Integer idDirectorio) {
		this.idDirectorio = idDirectorio;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDirectorio == null) ? 0 : idDirectorio.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		ComextDirectorioXClientePK other = (ComextDirectorioXClientePK) obj;
		if (idDirectorio == null) {
			if (other.idDirectorio != null)
				return false;
		} else if (!idDirectorio.equals(other.idDirectorio))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}