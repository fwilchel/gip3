package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * The persistent class for the productos_inventario database table.
 * 
 */
@Entity
@Table(name = "productos_inventario")
@NamedQueries({
		@NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ALL, query = "SELECT p FROM ProductosInventario p"),
		@NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ACTIVOS, query = "SELECT p FROM ProductosInventario p WHERE p.desactivado = false"),
		@NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_CATEGORIA_SKU_NOMBRE_ESTADO, query = "SELECT p FROM ProductosInventario p LEFT JOIN p.categoriasInventario ci JOIN p.pais pa JOIN pa.usuarios u WHERE u.id = :idUsuario AND (false = :paramDesactivado OR p.desactivado = :desactivado) AND (false = :paramCategoria OR ci.id = :idCategoria) AND (false = :paramSku OR p.sku like :sku) AND (false = :paramNombre OR p.nombre like :nombre)"),
		@NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_SKU, query = "SELECT p FROM ProductosInventario p WHERE p.sku = :sku"),
		@NamedQuery(name = ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_SKUs, query = "SELECT p FROM ProductosInventario p WHERE p.sku in (:skus)") })
public class ProductosInventario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1938090563895800247L;
	public static final String PRODUCTOS_INVENTARIO_FIND_ALL = "ProductosInventario.findAll";
	public static final String PRODUCTOS_INVENTARIO_FIND_ACTIVOS = "ProductosInventario.findActivos";
	public static final String PRODUCTOS_INVENTARIO_FIND_BY_CATEGORIA_SKU_NOMBRE_ESTADO = "ProductosInventario.findByCategoriaSkuNombreEstado";
	public static final String PRODUCTOS_INVENTARIO_FIND_BY_SKU = "ProductosInventario.findBySku";
	public static final String PRODUCTOS_INVENTARIO_FIND_BY_SKUs = "ProductosInventario.findBySkus";

	@Id
	private Long id;

	private String abc;

	@Column(name = "codigo_barras_ud")
	private Long codigoBarrasUd;

	@Column(name = "codigo_barras_uv")
	private Long codigoBarrasUv;

	@Column(name = "codigo_pos")
	private String codigoPos;

	private Boolean consumible;

	@Column(name = "conversor_r")
	private Long conversorR;

	private Boolean desactivado;

	@Column(name = "factor_ud_uv")
	private BigDecimal factorUdUv;

	@Column(name = "id_cuenta_contable")
	private Long idCuentaContable;

	@ManyToOne
	@JoinColumn(name = "id_ur")
	private Unidad unidadReceta;

	private String nombre;

	private String observaciones;

	private String sku;

	// bi-directional many-to-one association to ComextItemc
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<ComextItemc> comextItemcs;

	// bi-directional many-to-one association to Conteo
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<Conteo> conteos;

	// bi-directional many-to-one association to CostoVenta
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<CostoVenta> costoVentas;

	// bi-directional many-to-one association to Costo
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<Costo> costos;

	// bi-directional many-to-one association to EstandarConteo
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<EstandarConteo> estandarConteos;

	// bi-directional many-to-one association to EstandarPedido
	/*
	 * @OneToMany(mappedBy="productosInventario") private List<EstandarPedido>
	 * estandarPedidos;
	 */

	// bi-directional many-to-one association to MovimientosInventario
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<MovimientosInventario> movimientosInventarios;

	// bi-directional many-to-one association to NivelInventarioxubicacion
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<NivelInventarioxubicacion> nivelInventarioxubicacions;

	// bi-directional many-to-one association to NivelInventarioxubicacionTemp
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<NivelInventarioxubicacionTemp> nivelInventarioxubicacionTemps;

	// bi-directional many-to-one association to CategoriasInventario
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private CategoriasInventario categoriasInventario;

	// bi-directional many-to-one association to Pais
	@ManyToOne
	@JoinColumn(name = "id_pais")
	private Pais pais;

	// bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name = "id_ud")
	private Unidad unidadDespacho;

	// bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name = "id_uv")
	private Unidad unidadVenta;

	// bi-directional one-to-one association to ProductosInventarioComext
	@OneToOne(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private ProductosInventarioComext productosInventarioComext;

	// bi-directional many-to-one association to ProductosXClienteComext
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<ProductosXClienteComext> productosXClienteComexts;

	// bi-directional many-to-one association to ProductosXCliente
	@OneToMany(mappedBy = "productosInventario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ProductosXCliente> productosxclientes;

	// bi-directional many-to-one association to ProductosXDocumento
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<ProductosXDocumento> productosxdocumentos;

	// bi-directional many-to-one association to ProductosXProveedor
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<ProductosXProveedor> productosxproveedors;

	// bi-directional many-to-one association to ProductosXReceta
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<ProductosXReceta> productosxrecetas;

	// bi-directional many-to-one association to Saldo
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<Saldo> saldos;

	// bi-directional many-to-one association to SaldosFranquicia
	@OneToMany(mappedBy = "productosInventario", fetch = FetchType.LAZY)
	private List<SaldosFranquicia> saldosFranquicias;

	// bi-directional many-to-one association to TempCosto
	/*
	 * @OneToMany(mappedBy="productosInventario") private List<TempCosto>
	 * tempCostos;
	 */

	@Transient
	private boolean incluido;

	public ProductosInventario() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbc() {
		return this.abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	public Long getCodigoBarrasUd() {
		return this.codigoBarrasUd;
	}

	public void setCodigoBarrasUd(Long codigoBarrasUd) {
		this.codigoBarrasUd = codigoBarrasUd;
	}

	public Long getCodigoBarrasUv() {
		return this.codigoBarrasUv;
	}

	public void setCodigoBarrasUv(Long codigoBarrasUv) {
		this.codigoBarrasUv = codigoBarrasUv;
	}

	public String getCodigoPos() {
		return this.codigoPos;
	}

	public void setCodigoPos(String codigoPos) {
		this.codigoPos = codigoPos;
	}

	public Boolean getConsumible() {
		return this.consumible;
	}

	public void setConsumible(Boolean consumible) {
		this.consumible = consumible;
	}

	public Long getConversorR() {
		return this.conversorR;
	}

	public void setConversorR(Long conversorR) {
		this.conversorR = conversorR;
	}

	public Boolean getDesactivado() {
		return this.desactivado;
	}

	public void setDesactivado(Boolean desactivado) {
		this.desactivado = desactivado;
	}

	public BigDecimal getFactorUdUv() {
		return this.factorUdUv;
	}

	public void setFactorUdUv(BigDecimal factorUdUv) {
		this.factorUdUv = factorUdUv;
	}

	public Long getIdCuentaContable() {
		return this.idCuentaContable;
	}

	public void setIdCuentaContable(Long idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}

	public Unidad getUnidadReceta() {
		return this.unidadReceta;
	}

	public void setUnidadReceta(Unidad idUr) {
		this.unidadReceta = idUr;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public List<ComextItemc> getComextItemcs() {
		return this.comextItemcs;
	}

	public void setComextItemcs(List<ComextItemc> comextItemcs) {
		this.comextItemcs = comextItemcs;
	}

	public ComextItemc addComextItemc(ComextItemc comextItemc) {
		getComextItemcs().add(comextItemc);
		comextItemc.setProductosInventario(this);

		return comextItemc;
	}

	public ComextItemc removeComextItemc(ComextItemc comextItemc) {
		getComextItemcs().remove(comextItemc);
		comextItemc.setProductosInventario(null);

		return comextItemc;
	}

	public List<Conteo> getConteos() {
		return this.conteos;
	}

	public void setConteos(List<Conteo> conteos) {
		this.conteos = conteos;
	}

	public Conteo addConteo(Conteo conteo) {
		getConteos().add(conteo);
		conteo.setProductosInventario(this);

		return conteo;
	}

	public Conteo removeConteo(Conteo conteo) {
		getConteos().remove(conteo);
		conteo.setProductosInventario(null);

		return conteo;
	}

	public List<CostoVenta> getCostoVentas() {
		return this.costoVentas;
	}

	public void setCostoVentas(List<CostoVenta> costoVentas) {
		this.costoVentas = costoVentas;
	}

	public CostoVenta addCostoVenta(CostoVenta costoVenta) {
		getCostoVentas().add(costoVenta);
		costoVenta.setProductosInventario(this);

		return costoVenta;
	}

	public CostoVenta removeCostoVenta(CostoVenta costoVenta) {
		getCostoVentas().remove(costoVenta);
		costoVenta.setProductosInventario(null);

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
		costo.setProductosInventario(this);

		return costo;
	}

	public Costo removeCosto(Costo costo) {
		getCostos().remove(costo);
		costo.setProductosInventario(null);

		return costo;
	}

	public List<EstandarConteo> getEstandarConteos() {
		return this.estandarConteos;
	}

	public void setEstandarConteos(List<EstandarConteo> estandarConteos) {
		this.estandarConteos = estandarConteos;
	}

	public EstandarConteo addEstandarConteo(EstandarConteo estandarConteo) {
		getEstandarConteos().add(estandarConteo);
		estandarConteo.setProductosInventario(this);

		return estandarConteo;
	}

	public EstandarConteo removeEstandarConteo(EstandarConteo estandarConteo) {
		getEstandarConteos().remove(estandarConteo);
		estandarConteo.setProductosInventario(null);

		return estandarConteo;
	}

	/*
	 * public List<EstandarPedido> getEstandarPedidos() { return
	 * this.estandarPedidos; }
	 * 
	 * public void setEstandarPedidos(List<EstandarPedido> estandarPedidos) {
	 * this.estandarPedidos = estandarPedidos; }
	 * 
	 * public EstandarPedido addEstandarPedido(EstandarPedido estandarPedido) {
	 * getEstandarPedidos().add(estandarPedido);
	 * estandarPedido.setProductosInventario(this);
	 * 
	 * return estandarPedido; }
	 * 
	 * public EstandarPedido removeEstandarPedido(EstandarPedido estandarPedido)
	 * { getEstandarPedidos().remove(estandarPedido);
	 * estandarPedido.setProductosInventario(null);
	 * 
	 * return estandarPedido; }
	 */

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
		movimientosInventario.setProductosInventario(this);

		return movimientosInventario;
	}

	public MovimientosInventario removeMovimientosInventario(
			MovimientosInventario movimientosInventario) {
		getMovimientosInventarios().remove(movimientosInventario);
		movimientosInventario.setProductosInventario(null);

		return movimientosInventario;
	}

	public List<NivelInventarioxubicacion> getNivelInventarioxubicacions() {
		return this.nivelInventarioxubicacions;
	}

	public void setNivelInventarioxubicacions(
			List<NivelInventarioxubicacion> nivelInventarioxubicacions) {
		this.nivelInventarioxubicacions = nivelInventarioxubicacions;
	}

	public NivelInventarioxubicacion addNivelInventarioxubicacion(
			NivelInventarioxubicacion nivelInventarioxubicacion) {
		getNivelInventarioxubicacions().add(nivelInventarioxubicacion);
		nivelInventarioxubicacion.setProductosInventario(this);

		return nivelInventarioxubicacion;
	}

	public NivelInventarioxubicacion removeNivelInventarioxubicacion(
			NivelInventarioxubicacion nivelInventarioxubicacion) {
		getNivelInventarioxubicacions().remove(nivelInventarioxubicacion);
		nivelInventarioxubicacion.setProductosInventario(null);

		return nivelInventarioxubicacion;
	}

	public List<NivelInventarioxubicacionTemp> getNivelInventarioxubicacionTemps() {
		return this.nivelInventarioxubicacionTemps;
	}

	public void setNivelInventarioxubicacionTemps(
			List<NivelInventarioxubicacionTemp> nivelInventarioxubicacionTemps) {
		this.nivelInventarioxubicacionTemps = nivelInventarioxubicacionTemps;
	}

	public NivelInventarioxubicacionTemp addNivelInventarioxubicacionTemp(
			NivelInventarioxubicacionTemp nivelInventarioxubicacionTemp) {
		getNivelInventarioxubicacionTemps().add(nivelInventarioxubicacionTemp);
		nivelInventarioxubicacionTemp.setProductosInventario(this);

		return nivelInventarioxubicacionTemp;
	}

	public NivelInventarioxubicacionTemp removeNivelInventarioxubicacionTemp(
			NivelInventarioxubicacionTemp nivelInventarioxubicacionTemp) {
		getNivelInventarioxubicacionTemps().remove(
				nivelInventarioxubicacionTemp);
		nivelInventarioxubicacionTemp.setProductosInventario(null);

		return nivelInventarioxubicacionTemp;
	}

	public CategoriasInventario getCategoriasInventario() {
		return this.categoriasInventario;
	}

	public void setCategoriasInventario(
			CategoriasInventario categoriasInventario) {
		this.categoriasInventario = categoriasInventario;
	}

	public Pais getPais() {
		return this.pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Unidad getUnidadDespacho() {
		return this.unidadDespacho;
	}

	public void setUnidadDespacho(Unidad unidade1) {
		this.unidadDespacho = unidade1;
	}

	public Unidad getUnidadVenta() {
		return this.unidadVenta;
	}

	public void setUnidadVenta(Unidad unidade2) {
		this.unidadVenta = unidade2;
	}

	public ProductosInventarioComext getProductosInventarioComext() {
		return this.productosInventarioComext;
	}

	public void setProductosInventarioComext(
			ProductosInventarioComext productosInventarioComext) {
		this.productosInventarioComext = productosInventarioComext;
	}

	public List<ProductosXClienteComext> getProductosXClienteComexts() {
		return this.productosXClienteComexts;
	}

	public void setProductosXClienteComexts(
			List<ProductosXClienteComext> productosXClienteComexts) {
		this.productosXClienteComexts = productosXClienteComexts;
	}

	public ProductosXClienteComext addProductosXClienteComext(
			ProductosXClienteComext productosXClienteComext) {
		getProductosXClienteComexts().add(productosXClienteComext);
		productosXClienteComext.setProductosInventario(this);

		return productosXClienteComext;
	}

	public ProductosXClienteComext removeProductosXClienteComext(
			ProductosXClienteComext productosXClienteComext) {
		getProductosXClienteComexts().remove(productosXClienteComext);
		productosXClienteComext.setProductosInventario(null);

		return productosXClienteComext;
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
		productosxcliente.setProductosInventario(this);

		return productosxcliente;
	}

	public ProductosXCliente removeProductosxcliente(
			ProductosXCliente productosxcliente) {
		getProductosxclientes().remove(productosxcliente);
		productosxcliente.setProductosInventario(null);

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
		productosxdocumento.setProductosInventario(this);

		return productosxdocumento;
	}

	public ProductosXDocumento removeProductosxdocumento(
			ProductosXDocumento productosxdocumento) {
		getProductosxdocumentos().remove(productosxdocumento);
		productosxdocumento.setProductosInventario(null);

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
		productosxproveedor.setProductosInventario(this);

		return productosxproveedor;
	}

	public ProductosXProveedor removeProductosxproveedor(
			ProductosXProveedor productosxproveedor) {
		getProductosxproveedors().remove(productosxproveedor);
		productosxproveedor.setProductosInventario(null);

		return productosxproveedor;
	}

	public List<ProductosXReceta> getProductosxrecetas() {
		return this.productosxrecetas;
	}

	public void setProductosxrecetas(List<ProductosXReceta> productosxrecetas) {
		this.productosxrecetas = productosxrecetas;
	}

	public ProductosXReceta addProductosxreceta(
			ProductosXReceta productosxreceta) {
		getProductosxrecetas().add(productosxreceta);
		productosxreceta.setProductosInventario(this);

		return productosxreceta;
	}

	public ProductosXReceta removeProductosxreceta(
			ProductosXReceta productosxreceta) {
		getProductosxrecetas().remove(productosxreceta);
		productosxreceta.setProductosInventario(null);

		return productosxreceta;
	}

	public List<Saldo> getSaldos() {
		return this.saldos;
	}

	public void setSaldos(List<Saldo> saldos) {
		this.saldos = saldos;
	}

	public Saldo addSaldo(Saldo saldo) {
		getSaldos().add(saldo);
		saldo.setProductosInventario(this);

		return saldo;
	}

	public Saldo removeSaldo(Saldo saldo) {
		getSaldos().remove(saldo);
		saldo.setProductosInventario(null);

		return saldo;
	}

	public List<SaldosFranquicia> getSaldosFranquicias() {
		return this.saldosFranquicias;
	}

	public void setSaldosFranquicias(List<SaldosFranquicia> saldosFranquicias) {
		this.saldosFranquicias = saldosFranquicias;
	}

	public SaldosFranquicia addSaldosFranquicia(
			SaldosFranquicia saldosFranquicia) {
		getSaldosFranquicias().add(saldosFranquicia);
		saldosFranquicia.setProductosInventario(this);

		return saldosFranquicia;
	}

	public SaldosFranquicia removeSaldosFranquicia(
			SaldosFranquicia saldosFranquicia) {
		getSaldosFranquicias().remove(saldosFranquicia);
		saldosFranquicia.setProductosInventario(null);

		return saldosFranquicia;
	}

	public boolean isIncluido() {
		return incluido;
	}

	public void setIncluido(boolean incluido) {
		this.incluido = incluido;
	}

	/*
	 * public List<TempCosto> getTempCostos() { return this.tempCostos; }
	 * 
	 * public void setTempCostos(List<TempCosto> tempCostos) { this.tempCostos =
	 * tempCostos; }
	 * 
	 * public TempCosto addTempCosto(TempCosto tempCosto) {
	 * getTempCostos().add(tempCosto); tempCosto.setProductosInventario(this);
	 * 
	 * return tempCosto; }
	 * 
	 * public TempCosto removeTempCosto(TempCosto tempCosto) {
	 * getTempCostos().remove(tempCosto);
	 * tempCosto.setProductosInventario(null);
	 * 
	 * return tempCosto; }
	 */

}