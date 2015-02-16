package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the categorias_inventario database table.
 * 
 */
@Entity
@Table(name="categorias_inventario")
@NamedQuery(name="CategoriasInventario.findAll", query="SELECT c FROM CategoriasInventario c")
public class CategoriasInventario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="incluir_en_cierre")
	private Boolean incluirEnCierre;

	private String nombre;

	//bi-directional many-to-one association to CategoriasInventario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_categoria_padre")
	private CategoriasInventario categoriasInventario;

	//bi-directional many-to-one association to CategoriasInventario
	@OneToMany(mappedBy="categoriasInventario", fetch=FetchType.LAZY)
	private List<CategoriasInventario> categoriasInventarios;

	//bi-directional many-to-one association to CostoVenta
	@OneToMany(mappedBy="categoriasInventario", fetch=FetchType.LAZY)
	private List<CostoVenta> costoVentas;

	//bi-directional many-to-one association to EstandarConteo
	@OneToMany(mappedBy="categoriasInventario", fetch=FetchType.LAZY)
	private List<EstandarConteo> estandarConteos;

	//bi-directional many-to-one association to EstandarPedido
	//@OneToMany(mappedBy="categoriasInventario")
	//private List<EstandarPedido> estandarPedidos;

	//bi-directional many-to-one association to ProductosInventario
	@OneToMany(mappedBy="categoriasInventario", fetch=FetchType.LAZY)
	private List<ProductosInventario> productosInventarios;

	public CategoriasInventario() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIncluirEnCierre() {
		return this.incluirEnCierre;
	}

	public void setIncluirEnCierre(Boolean incluirEnCierre) {
		this.incluirEnCierre = incluirEnCierre;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CategoriasInventario getCategoriasInventario() {
		return this.categoriasInventario;
	}

	public void setCategoriasInventario(CategoriasInventario categoriasInventario) {
		this.categoriasInventario = categoriasInventario;
	}

	public List<CategoriasInventario> getCategoriasInventarios() {
		return this.categoriasInventarios;
	}

	public void setCategoriasInventarios(List<CategoriasInventario> categoriasInventarios) {
		this.categoriasInventarios = categoriasInventarios;
	}

	public CategoriasInventario addCategoriasInventario(CategoriasInventario categoriasInventario) {
		getCategoriasInventarios().add(categoriasInventario);
		categoriasInventario.setCategoriasInventario(this);

		return categoriasInventario;
	}

	public CategoriasInventario removeCategoriasInventario(CategoriasInventario categoriasInventario) {
		getCategoriasInventarios().remove(categoriasInventario);
		categoriasInventario.setCategoriasInventario(null);

		return categoriasInventario;
	}

	public List<CostoVenta> getCostoVentas() {
		return this.costoVentas;
	}

	public void setCostoVentas(List<CostoVenta> costoVentas) {
		this.costoVentas = costoVentas;
	}

	public CostoVenta addCostoVenta(CostoVenta costoVenta) {
		getCostoVentas().add(costoVenta);
		costoVenta.setCategoriasInventario(this);

		return costoVenta;
	}

	public CostoVenta removeCostoVenta(CostoVenta costoVenta) {
		getCostoVentas().remove(costoVenta);
		costoVenta.setCategoriasInventario(null);

		return costoVenta;
	}

	public List<EstandarConteo> getEstandarConteos() {
		return this.estandarConteos;
	}

	public void setEstandarConteos(List<EstandarConteo> estandarConteos) {
		this.estandarConteos = estandarConteos;
	}

	public EstandarConteo addEstandarConteo(EstandarConteo estandarConteo) {
		getEstandarConteos().add(estandarConteo);
		estandarConteo.setCategoriasInventario(this);

		return estandarConteo;
	}

	public EstandarConteo removeEstandarConteo(EstandarConteo estandarConteo) {
		getEstandarConteos().remove(estandarConteo);
		estandarConteo.setCategoriasInventario(null);

		return estandarConteo;
	}

	/*public List<EstandarPedido> getEstandarPedidos() {
		return this.estandarPedidos;
	}

	public void setEstandarPedidos(List<EstandarPedido> estandarPedidos) {
		this.estandarPedidos = estandarPedidos;
	}

	public EstandarPedido addEstandarPedido(EstandarPedido estandarPedido) {
		getEstandarPedidos().add(estandarPedido);
		estandarPedido.setCategoriasInventario(this);

		return estandarPedido;
	}

	public EstandarPedido removeEstandarPedido(EstandarPedido estandarPedido) {
		getEstandarPedidos().remove(estandarPedido);
		estandarPedido.setCategoriasInventario(null);

		return estandarPedido;
	}*/

	public List<ProductosInventario> getProductosInventarios() {
		return this.productosInventarios;
	}

	public void setProductosInventarios(List<ProductosInventario> productosInventarios) {
		this.productosInventarios = productosInventarios;
	}

	public ProductosInventario addProductosInventario(ProductosInventario productosInventario) {
		getProductosInventarios().add(productosInventario);
		productosInventario.setCategoriasInventario(this);

		return productosInventario;
	}

	public ProductosInventario removeProductosInventario(ProductosInventario productosInventario) {
		getProductosInventarios().remove(productosInventario);
		productosInventario.setCategoriasInventario(null);

		return productosInventario;
	}

}