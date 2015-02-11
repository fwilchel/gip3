package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the clientes database table.
 * 
 */
@Entity
@Table(name = "clientes")
@NamedQueries({
		@NamedQuery(name = Cliente.CLIENTE_FIND_ALL, query = "SELECT c FROM Cliente c"),
		@NamedQuery(name = Cliente.CLIENTE_ACTIVO_FIND_BY_USUARIO, query = "SELECT c FROM Cliente c LEFT JOIN c.tipoCanal tc LEFT JOIN tc.usuarios u WHERE c.activo = true AND u.id = :idUsuario ORDER BY c.nombre ASC"),
		@NamedQuery(name = Cliente.BUSCAR_CLIENTES_REPORTE_VENTAS_CE, query = "SELECT c FROM Cliente c LEFT JOIN c.tipoCanal tc LEFT JOIN tc.usuarios u WHERE c.nombre LIKE UPPER (:nombre) AND c.activo = :activo AND u.id = :idUsuario ORDER BY c.nombre ASC") })
public class Cliente implements Serializable, Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 466740775247498616L;
	public static final String CLIENTE_FIND_ALL = "Cliente.findAll";
	public static final String CLIENTE_ACTIVO_FIND_BY_USUARIO = "Cliente.findActivoFindByUsuario";
	public static final String BUSCAR_CLIENTES_REPORTE_VENTAS_CE = "Cliente.buscarClientesReporteVentasCE";

	@Id
	@SequenceGenerator( name = "clientes_id_seq", sequenceName = "clientes_id_seq", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "clientes_id_seq" )
	private Long id;

	private Boolean activo;

	private String celular;

	@Column(name = "codigo_barras")
	private Long codigoBarras;

	@Column(name = "codigo_postal")
	private String codigoPostal;

	@Column(name = "codigo_sap")
	private String codigoSap;

	private String contacto;

	@Column(name = "descuento_cliente")
	private BigDecimal descuentoCliente;

	private String direccion;

	private String email;

	@Column(name = "factura_ingles")
	private Boolean facturaIngles;

	private String fax;

	@ManyToOne
	@JoinColumn(name = "id_ciudad")
	private Ciudad ciudad;

	@Column(name = "modo_factura")
	private Integer modoFactura;

	private String nit;

	private String nombre;

	@Column(name = "observaciones_marcacion_1")
	private String observacionesMarcacion1;

	private String telefono;

	// bi-directional many-to-one association to AgenteAduana
	@ManyToOne
	@JoinColumn(name = "id_agente_aduana")
	private AgenteAduana agenteAduana;

	// bi-directional many-to-one association to CuentaContable
	@ManyToOne
	@JoinColumn(name = "id_cuenta_cliente")
	private CuentaContable cuentaContable;

	// bi-directional many-to-one association to MetodoPago
	@ManyToOne
	@JoinColumn(name = "id_metodo_pago")
	private MetodoPago metodoPago;

	// bi-directional many-to-one association to TipoCanal
	@ManyToOne
	@JoinColumn(name = "id_tipo_canal")
	private TipoCanal tipoCanal;

	// bi-directional many-to-one association to TipoPrecio
	@ManyToOne
	@JoinColumn(name = "id_tipo_precio")
	private TipoPrecio tipoPrecio;

	// bi-directional many-to-many association to TerminoIncoterm
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="incoterm_x_cliente"
			, joinColumns={
					@JoinColumn(name="id_cliente")
			}
			, inverseJoinColumns={
					@JoinColumn(name="id_incoterm")
			}
			)
	private List<TerminoIncoterm> terminoIncoterms;

	// bi-directional many-to-one association to ProductosXClienteComext
	@OneToMany(mappedBy = "cliente")
	private List<ProductosXClienteComext> productosXClienteComexts;

	// bi-directional many-to-one association to ProductosXCliente
	@OneToMany(mappedBy = "cliente")
	private List<ProductosXCliente> productosxclientes;

	// bi-directional many-to-one association to PuntoVenta
	@OneToMany(mappedBy = "cliente")
	private List<PuntoVenta> puntoVentas;

	public Cliente() {
	}

	public Cliente(Long id) {
		this.id = id;
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

	public Long getCodigoBarras() {
		return this.codigoBarras;
	}

	public void setCodigoBarras(Long codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCodigoSap() {
		return this.codigoSap;
	}

	public void setCodigoSap(String codigoSap) {
		this.codigoSap = codigoSap;
	}

	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public BigDecimal getDescuentoCliente() {
		return this.descuentoCliente;
	}

	public void setDescuentoCliente(BigDecimal descuentoCliente) {
		this.descuentoCliente = descuentoCliente;
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

	public Boolean getFacturaIngles() {
		return this.facturaIngles;
	}

	public void setFacturaIngles(Boolean facturaIngles) {
		this.facturaIngles = facturaIngles;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Integer getModoFactura() {
		return this.modoFactura;
	}

	public void setModoFactura(Integer modoFactura) {
		this.modoFactura = modoFactura;
	}

	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacionesMarcacion1() {
		return this.observacionesMarcacion1;
	}

	public void setObservacionesMarcacion1(String observacionesMarcacion1) {
		this.observacionesMarcacion1 = observacionesMarcacion1;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public AgenteAduana getAgenteAduana() {
		return this.agenteAduana;
	}

	public void setAgenteAduana(AgenteAduana agenteAduana) {
		this.agenteAduana = agenteAduana;
	}

	public CuentaContable getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public MetodoPago getMetodoPago() {
		return this.metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public TipoCanal getTipoCanal() {
		return this.tipoCanal;
	}

	public void setTipoCanal(TipoCanal tipoCanal) {
		this.tipoCanal = tipoCanal;
	}

	public TipoPrecio getTipoPrecio() {
		return this.tipoPrecio;
	}

	public void setTipoPrecio(TipoPrecio tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}

	public List<TerminoIncoterm> getTerminoIncoterms() {
		return this.terminoIncoterms;
	}

	public void setTerminoIncoterms(List<TerminoIncoterm> terminoIncoterms) {
		this.terminoIncoterms = terminoIncoterms;
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
		productosXClienteComext.setCliente(this);

		return productosXClienteComext;
	}

	public ProductosXClienteComext removeProductosXClienteComext(
			ProductosXClienteComext productosXClienteComext) {
		getProductosXClienteComexts().remove(productosXClienteComext);
		productosXClienteComext.setCliente(null);

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
		productosxcliente.setCliente(this);

		return productosxcliente;
	}

	public ProductosXCliente removeProductosxcliente(
			ProductosXCliente productosxcliente) {
		getProductosxclientes().remove(productosxcliente);
		productosxcliente.setCliente(null);

		return productosxcliente;
	}

	public List<PuntoVenta> getPuntoVentas() {
		return this.puntoVentas;
	}

	public void setPuntoVentas(List<PuntoVenta> puntoVentas) {
		this.puntoVentas = puntoVentas;
	}

	public PuntoVenta addPuntoVenta(PuntoVenta puntoVenta) {
		getPuntoVentas().add(puntoVenta);
		puntoVenta.setCliente(this);

		return puntoVenta;
	}

	public PuntoVenta removePuntoVenta(PuntoVenta puntoVenta) {
		getPuntoVentas().remove(puntoVenta);
		puntoVenta.setCliente(null);

		return puntoVenta;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	@Override
	public int compareTo(Object o) {
		return this.nombre.compareTo(((Cliente)o).getNombre());
	}
	
	

}