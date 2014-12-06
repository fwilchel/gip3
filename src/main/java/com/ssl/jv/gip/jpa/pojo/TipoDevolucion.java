package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_devolucion database table.
 * 
 */
@Entity
@Table(name="tipo_devolucion")
@NamedQuery(name="TipoDevolucion.findAll", query="SELECT t FROM TipoDevolucion t")
public class TipoDevolucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to ProductosXDocumento
	@OneToMany(mappedBy="tipoDevolucion")
	private List<ProductosXDocumento> productosxdocumentos;

	public TipoDevolucion() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<ProductosXDocumento> getProductosxdocumentos() {
		return this.productosxdocumentos;
	}

	public void setProductosxdocumentos(List<ProductosXDocumento> productosxdocumentos) {
		this.productosxdocumentos = productosxdocumentos;
	}

	public ProductosXDocumento addProductosxdocumento(ProductosXDocumento productosxdocumento) {
		getProductosxdocumentos().add(productosxdocumento);
		productosxdocumento.setTipoDevolucion(this);

		return productosxdocumento;
	}

	public ProductosXDocumento removeProductosxdocumento(ProductosXDocumento productosxdocumento) {
		getProductosxdocumentos().remove(productosxdocumento);
		productosxdocumento.setTipoDevolucion(null);

		return productosxdocumento;
	}

}