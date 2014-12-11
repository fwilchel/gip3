package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the agencia_carga database table.
 * 
 */ 
@Entity
@Table(name="agencia_carga")
@NamedQuery(name="AgenciaCarga.findAll", query="SELECT a FROM AgenciaCarga a")
public class AgenciaCarga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Boolean activo;

	private String celular;

	private String contacto;

	private String direccion;

	private String email;

	@Column(name="lugar_entrega")
	private String lugarEntrega;

	private String nombre;

	private String telefono;

	//bi-directional many-to-one association to DocumentoXNegociacion
	@OneToMany(mappedBy="agenciaCarga")
	private List<DocumentoXNegociacion> documentoXNegociacions;

	public AgenciaCarga() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLugarEntrega() {
		return this.lugarEntrega;
	}

	public void setLugarEntrega(String lugarEntrega) {
		this.lugarEntrega = lugarEntrega;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<DocumentoXNegociacion> getDocumentoXNegociacions() {
		return this.documentoXNegociacions;
	}

	public void setDocumentoXNegociacions(List<DocumentoXNegociacion> documentoXNegociacions) {
		this.documentoXNegociacions = documentoXNegociacions;
	}

	public DocumentoXNegociacion addDocumentoXNegociacion(DocumentoXNegociacion documentoXNegociacion) {
		getDocumentoXNegociacions().add(documentoXNegociacion);
		documentoXNegociacion.setAgenciaCarga(this);

		return documentoXNegociacion;
	}

	public DocumentoXNegociacion removeDocumentoXNegociacion(DocumentoXNegociacion documentoXNegociacion) {
		getDocumentoXNegociacions().remove(documentoXNegociacion);
		documentoXNegociacion.setAgenciaCarga(null);

		return documentoXNegociacion;
	}

}