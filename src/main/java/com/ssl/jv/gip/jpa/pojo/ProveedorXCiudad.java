package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the proveedorxciudad database table.
 * 
 */
@Entity
@NamedQuery(name="ProveedorXCiudad.findAll", query="SELECT p FROM ProveedorXCiudad p")
public class ProveedorXCiudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProveedorXCiudadPK id;

	private String fax;

	private String telefono;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne(optional=false)
	@JoinColumn(name="id_proveedor", referencedColumnName="id", insertable=false, updatable=false)
	private Proveedor proveedore;

	public ProveedorXCiudad() {
	}

	public ProveedorXCiudadPK getId() {
		return this.id;
	}

	public void setId(ProveedorXCiudadPK id) {
		this.id = id;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Proveedor getProveedore() {
		return this.proveedore;
	}

	public void setProveedore(Proveedor proveedore) {
		this.proveedore = proveedore;
	}

}