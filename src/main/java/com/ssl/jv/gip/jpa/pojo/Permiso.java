package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the permisos database table.
 * 
 */
@Entity
@Table(name="permisos")
@NamedQuery(name="Permiso.findAll", query="SELECT p FROM Permiso p")
public class Permiso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="tipo_acceso")
	private String tipoAcceso;

	//bi-directional many-to-one association to Funcionalidad
	@ManyToOne
	@JoinColumn(name="id_funcionalidad")
	private Funcionalidad funcionalidade;

	//bi-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(
		name="permisos_roles"
		, joinColumns={
			@JoinColumn(name="id_permiso")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_rol")
			}
		)
	private List<Rol> roles;

	public Permiso() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoAcceso() {
		return this.tipoAcceso;
	}

	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	public Funcionalidad getFuncionalidade() {
		return this.funcionalidade;
	}

	public void setFuncionalidade(Funcionalidad funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	public List<Rol> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

}