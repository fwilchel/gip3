package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuariosxgeografias database table.
 * 
 */
@Entity
@Table(name="usuariosxgeografias")
@NamedQuery(name="UsuariosXGeografia.findAll", query="SELECT u FROM UsuariosXGeografia u")
public class UsuariosXGeografia implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuariosXGeografiaPK id;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(optional=false)
	@JoinColumn(name="id_usuario", referencedColumnName="id", insertable=false, updatable=false)
	private Usuario usuario;

	public UsuariosXGeografia() {
	}

	public UsuariosXGeografiaPK getId() {
		return this.id;
	}

	public void setId(UsuariosXGeografiaPK id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}