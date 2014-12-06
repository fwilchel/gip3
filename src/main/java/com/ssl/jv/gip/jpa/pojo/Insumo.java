package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the insumos database table.
 * 
 */
@Entity
@Table(name="insumos")
@NamedQuery(name="Insumo.findAll", query="SELECT i FROM Insumo i")
public class Insumo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to ProductosXReceta
	@OneToMany(mappedBy="insumo")
	private List<ProductosXReceta> productosxrecetas;

	public Insumo() {
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

	public List<ProductosXReceta> getProductosxrecetas() {
		return this.productosxrecetas;
	}

	public void setProductosxrecetas(List<ProductosXReceta> productosxrecetas) {
		this.productosxrecetas = productosxrecetas;
	}

	public ProductosXReceta addProductosxreceta(ProductosXReceta productosxreceta) {
		getProductosxrecetas().add(productosxreceta);
		productosxreceta.setInsumo(this);

		return productosxreceta;
	}

	public ProductosXReceta removeProductosxreceta(ProductosXReceta productosxreceta) {
		getProductosxrecetas().remove(productosxreceta);
		productosxreceta.setInsumo(null);

		return productosxreceta;
	}

}