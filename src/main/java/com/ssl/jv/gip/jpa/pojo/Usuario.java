package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQueries({
	@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="Usuario.findByEmail", query="SELECT u FROM Usuario u WHERE u.email= :email")
})

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Boolean activo;

	private String apellidos;

	private String area;

	private String canal;

	private String cargo;

	private String contrasena;

	private String email;

	@Column(name="fecha_ingreso")
	private Timestamp fechaIngreso;

	private Long intentos;

	private String nombre;

	//bi-directional many-to-one association to HistorialContrasena
	/*@OneToMany(mappedBy="pk")
	private List<HistorialContrasena> historialContrasenas;*/

	//bi-directional many-to-many association to TipoCanal
	@ManyToMany
	@JoinTable(
		name="tipocanal_x_usuario"
		, joinColumns={
			@JoinColumn(name="id_usuario")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_tipo_canal")
			}
		)
	private List<TipoCanal> tipoCanals;

	//bi-directional many-to-one association to Pais
	@ManyToOne
	@JoinColumn(name="id_pais")
	private Pais pais;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Rol role;

	//bi-directional many-to-one association to UsuariosXGeografia
	@OneToMany(mappedBy="usuario")
	private List<UsuariosXGeografia> usuariosxgeografias;

	public Usuario() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCanal() {
		return this.canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(Timestamp fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Long getIntentos() {
		return this.intentos;
	}

	public void setIntentos(Long intentos) {
		this.intentos = intentos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/*public List<HistorialContrasena> getHistorialContrasenas() {
		return this.historialContrasenas;
	}

	public void setHistorialContrasenas(List<HistorialContrasena> historialContrasenas) {
		this.historialContrasenas = historialContrasenas;
	}*/

	/*public HistorialContrasena addHistorialContrasena(HistorialContrasena historialContrasena) {
		getHistorialContrasenas().add(historialContrasena);
		historialContrasena.setUsuario(this);

		return historialContrasena;
	}

	public HistorialContrasena removeHistorialContrasena(HistorialContrasena historialContrasena) {
		getHistorialContrasenas().remove(historialContrasena);
		historialContrasena.setUsuario(null);

		return historialContrasena;
	}*/

	public List<TipoCanal> getTipoCanals() {
		return this.tipoCanals;
	}

	public void setTipoCanals(List<TipoCanal> tipoCanals) {
		this.tipoCanals = tipoCanals;
	}

	public Pais getPais() {
		return this.pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Rol getRole() {
		return this.role;
	}

	public void setRole(Rol role) {
		this.role = role;
	}

	public List<UsuariosXGeografia> getUsuariosxgeografias() {
		return this.usuariosxgeografias;
	}

	public void setUsuariosxgeografias(List<UsuariosXGeografia> usuariosxgeografias) {
		this.usuariosxgeografias = usuariosxgeografias;
	}

	public UsuariosXGeografia addUsuariosxgeografia(UsuariosXGeografia usuariosxgeografia) {
		getUsuariosxgeografias().add(usuariosxgeografia);
		usuariosxgeografia.setUsuario(this);

		return usuariosxgeografia;
	}

	public UsuariosXGeografia removeUsuariosxgeografia(UsuariosXGeografia usuariosxgeografia) {
		getUsuariosxgeografias().remove(usuariosxgeografia);
		usuariosxgeografia.setUsuario(null);

		return usuariosxgeografia;
	}

}