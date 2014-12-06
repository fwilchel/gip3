package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the comext_usuarios database table.
 * 
 */
@Entity
@Table(name="comext_usuarios")
@NamedQuery(name="ComextUsuario.findAll", query="SELECT c FROM ComextUsuario c")
public class ComextUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean cambiopassword;

	private Boolean escomercial;

	@Id
	private Long id;

	@Column(name="id_canal")
	private Long idCanal;

	@Column(name="id_cliente")
	private Long idCliente;

	@Column(name="id_usuario")
	private String idUsuario;

	private String password;

	private String salt;

	private String token;

	private String username;

	public ComextUsuario() {
	}

	public Boolean getCambiopassword() {
		return this.cambiopassword;
	}

	public void setCambiopassword(Boolean cambiopassword) {
		this.cambiopassword = cambiopassword;
	}

	public Boolean getEscomercial() {
		return this.escomercial;
	}

	public void setEscomercial(Boolean escomercial) {
		this.escomercial = escomercial;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCanal() {
		return this.idCanal;
	}

	public void setIdCanal(Long idCanal) {
		this.idCanal = idCanal;
	}

	public Long getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}