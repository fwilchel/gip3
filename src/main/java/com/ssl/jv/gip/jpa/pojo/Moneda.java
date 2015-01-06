package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the monedas database table.
 * 
 */
@Entity
@Table(name = "monedas")
@NamedQueries({ @NamedQuery(name = Moneda.MONEDA_FIND_ALL, query = "SELECT m FROM Moneda m") })
public class Moneda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8279587095048055334L;

	public static final String MONEDA_FIND_ALL = "Moneda.findAll";

	@Id
	private String id;

	private String nombre;

	// bi-directional many-to-one association to CostoVenta
	@OneToMany(mappedBy = "moneda")
	private List<CostoVenta> costoVentas;

	// bi-directional many-to-one association to Costo
	@OneToMany(mappedBy = "moneda")
	private List<Costo> costos;

	// bi-directional many-to-one association to Empresa
	@OneToMany(mappedBy = "moneda")
	private List<Empresa> empresas;

	// bi-directional many-to-one association to MovimientosInventario
	@OneToMany(mappedBy = "moneda")
	private List<MovimientosInventario> movimientosInventarios;

	// bi-directional many-to-one association to Pais
	@OneToMany(mappedBy = "moneda")
	private List<Pais> paises;

	// bi-directional many-to-one association to ProductosXCliente
	@OneToMany(mappedBy = "moneda")
	private List<ProductosXCliente> productosxclientes;

	// bi-directional many-to-one association to ProductosXDocumento
	@OneToMany(mappedBy = "moneda")
	private List<ProductosXDocumento> productosxdocumentos;

	// bi-directional many-to-one association to ProductosXProveedor
	@OneToMany(mappedBy = "moneda")
	private List<ProductosXProveedor> productosxproveedors;

	// bi-directional many-to-one association to TempCosto
	/*
	 * @OneToMany(mappedBy="moneda") private List<TempCosto> tempCostos;
	 */

	// bi-directional many-to-one association to Venta
	@OneToMany(mappedBy = "moneda")
	private List<Venta> ventas;

	public Moneda() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CostoVenta> getCostoVentas() {
		return this.costoVentas;
	}

	public void setCostoVentas(List<CostoVenta> costoVentas) {
		this.costoVentas = costoVentas;
	}

	public CostoVenta addCostoVenta(CostoVenta costoVenta) {
		getCostoVentas().add(costoVenta);
		costoVenta.setMoneda(this);

		return costoVenta;
	}

	public CostoVenta removeCostoVenta(CostoVenta costoVenta) {
		getCostoVentas().remove(costoVenta);
		costoVenta.setMoneda(null);

		return costoVenta;
	}

	public List<Costo> getCostos() {
		return this.costos;
	}

	public void setCostos(List<Costo> costos) {
		this.costos = costos;
	}

	public Costo addCosto(Costo costo) {
		getCostos().add(costo);
		costo.setMoneda(this);

		return costo;
	}

	public Costo removeCosto(Costo costo) {
		getCostos().remove(costo);
		costo.setMoneda(null);

		return costo;
	}

	public List<Empresa> getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa addEmpresa(Empresa empresa) {
		getEmpresas().add(empresa);
		empresa.setMoneda(this);

		return empresa;
	}

	public Empresa removeEmpresa(Empresa empresa) {
		getEmpresas().remove(empresa);
		empresa.setMoneda(null);

		return empresa;
	}

	public List<MovimientosInventario> getMovimientosInventarios() {
		return this.movimientosInventarios;
	}

	public void setMovimientosInventarios(
			List<MovimientosInventario> movimientosInventarios) {
		this.movimientosInventarios = movimientosInventarios;
	}

	public MovimientosInventario addMovimientosInventario(
			MovimientosInventario movimientosInventario) {
		getMovimientosInventarios().add(movimientosInventario);
		movimientosInventario.setMoneda(this);

		return movimientosInventario;
	}

	public MovimientosInventario removeMovimientosInventario(
			MovimientosInventario movimientosInventario) {
		getMovimientosInventarios().remove(movimientosInventario);
		movimientosInventario.setMoneda(null);

		return movimientosInventario;
	}

	public List<Pais> getPaises() {
		return this.paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public Pais addPais(Pais pais) {
		getPaises().add(pais);
		pais.setMoneda(this);

		return pais;
	}

	public Pais removePais(Pais pais) {
		getPaises().remove(pais);
		pais.setMoneda(null);

		return pais;
	}

	public List<ProductosXCliente> getProductosxclientes() {
		return this.productosxclientes;
	}

	public void setProductosxclientes(List<ProductosXCliente> productosxclientes) {
		this.productosxclientes = productosxclientes;
	}

	public ProductosXCliente addProductosxcliente(
			ProductosXCliente productosxcliente) {
		getProductosxclientes().add(productosxcliente);
		productosxcliente.setMoneda(this);

		return productosxcliente;
	}

	public ProductosXCliente removeProductosxcliente(
			ProductosXCliente productosxcliente) {
		getProductosxclientes().remove(productosxcliente);
		productosxcliente.setMoneda(null);

		return productosxcliente;
	}

	public List<ProductosXDocumento> getProductosxdocumentos() {
		return this.productosxdocumentos;
	}

	public void setProductosxdocumentos(
			List<ProductosXDocumento> productosxdocumentos) {
		this.productosxdocumentos = productosxdocumentos;
	}

	public ProductosXDocumento addProductosxdocumento(
			ProductosXDocumento productosxdocumento) {
		getProductosxdocumentos().add(productosxdocumento);
		productosxdocumento.setMoneda(this);

		return productosxdocumento;
	}

	public ProductosXDocumento removeProductosxdocumento(
			ProductosXDocumento productosxdocumento) {
		getProductosxdocumentos().remove(productosxdocumento);
		productosxdocumento.setMoneda(null);

		return productosxdocumento;
	}

	public List<ProductosXProveedor> getProductosxproveedors() {
		return this.productosxproveedors;
	}

	public void setProductosxproveedors(
			List<ProductosXProveedor> productosxproveedors) {
		this.productosxproveedors = productosxproveedors;
	}

	public ProductosXProveedor addProductosxproveedor(
			ProductosXProveedor productosxproveedor) {
		getProductosxproveedors().add(productosxproveedor);
		productosxproveedor.setMoneda(this);

		return productosxproveedor;
	}

	public ProductosXProveedor removeProductosxproveedor(
			ProductosXProveedor productosxproveedor) {
		getProductosxproveedors().remove(productosxproveedor);
		productosxproveedor.setMoneda(null);

		return productosxproveedor;
	}

	/*
	 * public List<TempCosto> getTempCostos() { return this.tempCostos; }
	 * 
	 * public void setTempCostos(List<TempCosto> tempCostos) { this.tempCostos =
	 * tempCostos; }
	 * 
	 * public TempCosto addTempCosto(TempCosto tempCosto) {
	 * getTempCostos().add(tempCosto); tempCosto.setMoneda(this);
	 * 
	 * return tempCosto; }
	 * 
	 * public TempCosto removeTempCosto(TempCosto tempCosto) {
	 * getTempCostos().remove(tempCosto); tempCosto.setMoneda(null);
	 * 
	 * return tempCosto; }
	 */

	public List<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Venta addVenta(Venta venta) {
		getVentas().add(venta);
		venta.setMoneda(this);

		return venta;
	}

	public Venta removeVenta(Venta venta) {
		getVentas().remove(venta);
		venta.setMoneda(null);

		return venta;
	}

}