package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the estandar_conteos database table.
 * 
 */
@Entity
@Table(name="estandar_conteos")
@NamedQuery(name="EstandarConteo.findAll", query="SELECT e FROM EstandarConteo e")
public class EstandarConteo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	//bi-directional many-to-one association to CategoriasInventario
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private CategoriasInventario categoriasInventario;

	//bi-directional many-to-one association to ProductosInventario
	@ManyToOne
	@JoinColumn(name="id_producto")
	private ProductosInventario productosInventario;

	//bi-directional many-to-one association to TipoConteo
	@ManyToOne
	@JoinColumn(name="id_tipo_conteo")
	private TipoConteo tipoConteo;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name="id_ubicacion")
	private Ubicacion ubicacione;

	public EstandarConteo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CategoriasInventario getCategoriasInventario() {
		return this.categoriasInventario;
	}

	public void setCategoriasInventario(CategoriasInventario categoriasInventario) {
		this.categoriasInventario = categoriasInventario;
	}

	public ProductosInventario getProductosInventario() {
		return this.productosInventario;
	}

	public void setProductosInventario(ProductosInventario productosInventario) {
		this.productosInventario = productosInventario;
	}

	public TipoConteo getTipoConteo() {
		return this.tipoConteo;
	}

	public void setTipoConteo(TipoConteo tipoConteo) {
		this.tipoConteo = tipoConteo;
	}

	public Ubicacion getUbicacione() {
		return this.ubicacione;
	}

	public void setUbicacione(Ubicacion ubicacione) {
		this.ubicacione = ubicacione;
	}

}