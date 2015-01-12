package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ubicaciones database table.
 * 
 */
@Entity
@Table(name = "ubicaciones")
@NamedQuery(name = "Ubicacion.findAll", query = "SELECT u FROM Ubicacion u")
public class Ubicacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ubicacion_id_seq", sequenceName = "ubicacion_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ubicacion_id_seq")
	private Long id;

	@Column(name = "cliente_icg")
	private Long clienteIcg;

	@Column(name = "despacho_domingo")
	private Boolean despachoDomingo;

	@Column(name = "despacho_jueves")
	private Boolean despachoJueves;

	@Column(name = "despacho_lunes")
	private Boolean despachoLunes;

	@Column(name = "despacho_martes")
	private Boolean despachoMartes;

	@Column(name = "despacho_miercoles")
	private Boolean despachoMiercoles;

	@Column(name = "despacho_sabado")
	private Boolean despachoSabado;

	@Column(name = "despacho_viernes")
	private Boolean despachoViernes;

	private String direccion;

	@Column(name = "es_franquicia")
	private Boolean esFranquicia;

	@Column(name = "es_tienda")
	private Boolean esTienda;

	@Column(name = "id_ciudad")
	private Long idCiudad;

	@Column(name = "id_ubicacion_padre")
	private Long idUbicacionPadre;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "objeto_co")
	private Long objetoCo;

	@Column(name = "telefono")
	private String telefono;

	// bi-directional many-to-one association to Consignacion
	@OneToMany(mappedBy = "ubicacione")
	private List<Consignacion> consignaciones;

	// bi-directional many-to-one association to Conteo
	@OneToMany(mappedBy = "ubicacione")
	private List<Conteo> conteos;

	// bi-directional many-to-one association to CostoVenta
	@OneToMany(mappedBy = "ubicacione")
	private List<CostoVenta> costoVentas;

	// bi-directional many-to-one association to Documento
	@OneToMany(mappedBy = "ubicacionDestino")
	private List<Documento> documentos1;

	// bi-directional many-to-one association to Documento
	@OneToMany(mappedBy = "ubicacionOrigen")
	private List<Documento> documentos2;

	// bi-directional many-to-one association to Documento2
	@OneToMany(mappedBy = "ubicacionDestino")
	private List<Documento2> documentos2s1;

	// bi-directional many-to-one association to Documento2
	@OneToMany(mappedBy = "ubicacionOrigen")
	private List<Documento2> documentos2s2;

	// bi-directional many-to-one association to EstandarConteo
	@OneToMany(mappedBy = "ubicacione")
	private List<EstandarConteo> estandarConteos;

	// bi-directional many-to-one association to EstandarPedido
	/*
	 * @OneToMany(mappedBy="ubicacione") private List<EstandarPedido>
	 * estandarPedidos;
	 */

	// bi-directional many-to-one association to HistorialCierre
	@OneToMany(mappedBy = "ubicacione")
	private List<HistorialCierre> historialCierres;

	// bi-directional many-to-one association to MovimientosInventario
	@OneToMany(mappedBy = "ubicacionDestino")
	private List<MovimientosInventario> movimientosInventarios1;

	// bi-directional many-to-one association to MovimientosInventario
	@OneToMany(mappedBy = "ubicacionOrigen")
	private List<MovimientosInventario> movimientosInventarios2;

	// bi-directional many-to-one association to NivelInventarioxubicacion
	@OneToMany(mappedBy = "ubicacione")
	private List<NivelInventarioxubicacion> nivelInventarioxubicacions;

	// bi-directional many-to-one association to NivelInventarioxubicacionTemp
	@OneToMany(mappedBy = "ubicacione")
	private List<NivelInventarioxubicacionTemp> nivelInventarioxubicacionTemps;

	// bi-directional many-to-one association to Saldo
	@OneToMany(mappedBy = "ubicacione")
	private List<Saldo> saldos;

	// bi-directional many-to-one association to SaldosFranquicia
	@OneToMany(mappedBy = "ubicacione")
	private List<SaldosFranquicia> saldosFranquicias;

	// bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	// bi-directional many-to-one association to Region
	@ManyToOne
	@JoinColumn(name = "id_region")
	private Region regione;

	// bi-directional many-to-one association to TipoCanal
	@ManyToOne
	@JoinColumn(name = "id_tipo_canal")
	private TipoCanal tipoCanal;

	// bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name = "id_bodega_abastecedora")
	private Ubicacion ubicacione;

	// bi-directional many-to-one association to Ubicacion
	@OneToMany(mappedBy = "ubicacione")
	private List<Ubicacion> ubicaciones;

	// bi-directional many-to-one association to VariablesPedidoSugerido
	/*
	 * @OneToMany(mappedBy="ubicacione") private List<VariablesPedidoSugerido>
	 * variablesPedidoSugeridos;
	 */

	// bi-directional many-to-one association to Venta
	@OneToMany(mappedBy = "ubicacione")
	private List<Venta> ventas;

	public Ubicacion() {
	}

	public Ubicacion(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClienteIcg() {
		return this.clienteIcg;
	}

	public void setClienteIcg(Long clienteIcg) {
		this.clienteIcg = clienteIcg;
	}

	public Boolean getDespachoDomingo() {
		return this.despachoDomingo;
	}

	public void setDespachoDomingo(Boolean despachoDomingo) {
		this.despachoDomingo = despachoDomingo;
	}

	public Boolean getDespachoJueves() {
		return this.despachoJueves;
	}

	public void setDespachoJueves(Boolean despachoJueves) {
		this.despachoJueves = despachoJueves;
	}

	public Boolean getDespachoLunes() {
		return this.despachoLunes;
	}

	public void setDespachoLunes(Boolean despachoLunes) {
		this.despachoLunes = despachoLunes;
	}

	public Boolean getDespachoMartes() {
		return this.despachoMartes;
	}

	public void setDespachoMartes(Boolean despachoMartes) {
		this.despachoMartes = despachoMartes;
	}

	public Boolean getDespachoMiercoles() {
		return this.despachoMiercoles;
	}

	public void setDespachoMiercoles(Boolean despachoMiercoles) {
		this.despachoMiercoles = despachoMiercoles;
	}

	public Boolean getDespachoSabado() {
		return this.despachoSabado;
	}

	public void setDespachoSabado(Boolean despachoSabado) {
		this.despachoSabado = despachoSabado;
	}

	public Boolean getDespachoViernes() {
		return this.despachoViernes;
	}

	public void setDespachoViernes(Boolean despachoViernes) {
		this.despachoViernes = despachoViernes;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getEsFranquicia() {
		return this.esFranquicia;
	}

	public void setEsFranquicia(Boolean esFranquicia) {
		this.esFranquicia = esFranquicia;
	}

	public Boolean getEsTienda() {
		return this.esTienda;
	}

	public void setEsTienda(Boolean esTienda) {
		this.esTienda = esTienda;
	}

	public Long getIdCiudad() {
		return this.idCiudad;
	}

	public void setIdCiudad(Long idCiudad) {
		this.idCiudad = idCiudad;
	}

	public Long getIdUbicacionPadre() {
		return this.idUbicacionPadre;
	}

	public void setIdUbicacionPadre(Long idUbicacionPadre) {
		this.idUbicacionPadre = idUbicacionPadre;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getObjetoCo() {
		return this.objetoCo;
	}

	public void setObjetoCo(Long objetoCo) {
		this.objetoCo = objetoCo;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Consignacion> getConsignaciones() {
		return this.consignaciones;
	}

	public void setConsignaciones(List<Consignacion> consignaciones) {
		this.consignaciones = consignaciones;
	}

	public Consignacion addConsignacione(Consignacion consignacione) {
		getConsignaciones().add(consignacione);
		consignacione.setUbicacione(this);

		return consignacione;
	}

	public Consignacion removeConsignacione(Consignacion consignacione) {
		getConsignaciones().remove(consignacione);
		consignacione.setUbicacione(null);

		return consignacione;
	}

	public List<Conteo> getConteos() {
		return this.conteos;
	}

	public void setConteos(List<Conteo> conteos) {
		this.conteos = conteos;
	}

	public Conteo addConteo(Conteo conteo) {
		getConteos().add(conteo);
		conteo.setUbicacione(this);

		return conteo;
	}

	public Conteo removeConteo(Conteo conteo) {
		getConteos().remove(conteo);
		conteo.setUbicacione(null);

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
		costoVenta.setUbicacione(this);

		return costoVenta;
	}

	public CostoVenta removeCostoVenta(CostoVenta costoVenta) {
		getCostoVentas().remove(costoVenta);
		costoVenta.setUbicacione(null);

		return costoVenta;
	}

	public List<Documento> getDocumentos1() {
		return this.documentos1;
	}

	public void setDocumentos1(List<Documento> documentos1) {
		this.documentos1 = documentos1;
	}

	public Documento addDocumentos1(Documento documentos1) {
		getDocumentos1().add(documentos1);
		documentos1.setUbicacionDestino(this);

		return documentos1;
	}

	public Documento removeDocumentos1(Documento documentos1) {
		getDocumentos1().remove(documentos1);
		documentos1.setUbicacionDestino(null);

		return documentos1;
	}

	public List<Documento> getDocumentos2() {
		return this.documentos2;
	}

	public void setDocumentos2(List<Documento> documentos2) {
		this.documentos2 = documentos2;
	}

	public Documento addDocumentos2(Documento documentos2) {
		getDocumentos2().add(documentos2);
		documentos2.setUbicacionOrigen(this);

		return documentos2;
	}

	public Documento removeDocumentos2(Documento documentos2) {
		getDocumentos2().remove(documentos2);
		documentos2.setUbicacionOrigen(null);

		return documentos2;
	}

	public List<Documento2> getDocumentos2s1() {
		return this.documentos2s1;
	}

	public void setDocumentos2s1(List<Documento2> documentos2s1) {
		this.documentos2s1 = documentos2s1;
	}

	public Documento2 addDocumentos2s1(Documento2 documentos2s1) {
		getDocumentos2s1().add(documentos2s1);
		documentos2s1.setUbicacionDestino(this);

		return documentos2s1;
	}

	public Documento2 removeDocumentos2s1(Documento2 documentos2s1) {
		getDocumentos2s1().remove(documentos2s1);
		documentos2s1.setUbicacionDestino(null);

		return documentos2s1;
	}

	public List<Documento2> getDocumentos2s2() {
		return this.documentos2s2;
	}

	public void setDocumentos2s2(List<Documento2> documentos2s2) {
		this.documentos2s2 = documentos2s2;
	}

	public Documento2 addDocumentos2s2(Documento2 documentos2s2) {
		getDocumentos2s2().add(documentos2s2);
		documentos2s2.setUbicacionOrigen(this);

		return documentos2s2;
	}

	public Documento2 removeDocumentos2s2(Documento2 documentos2s2) {
		getDocumentos2s2().remove(documentos2s2);
		documentos2s2.setUbicacionOrigen(null);

		return documentos2s2;
	}

	public List<EstandarConteo> getEstandarConteos() {
		return this.estandarConteos;
	}

	public void setEstandarConteos(List<EstandarConteo> estandarConteos) {
		this.estandarConteos = estandarConteos;
	}

	public EstandarConteo addEstandarConteo(EstandarConteo estandarConteo) {
		getEstandarConteos().add(estandarConteo);
		estandarConteo.setUbicacione(this);

		return estandarConteo;
	}

	public EstandarConteo removeEstandarConteo(EstandarConteo estandarConteo) {
		getEstandarConteos().remove(estandarConteo);
		estandarConteo.setUbicacione(null);

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
	 * estandarPedido.setUbicacione(this);
	 * 
	 * return estandarPedido; }
	 * 
	 * public EstandarPedido removeEstandarPedido(EstandarPedido estandarPedido)
	 * { getEstandarPedidos().remove(estandarPedido);
	 * estandarPedido.setUbicacione(null);
	 * 
	 * return estandarPedido; }
	 */

	public List<HistorialCierre> getHistorialCierres() {
		return this.historialCierres;
	}

	public void setHistorialCierres(List<HistorialCierre> historialCierres) {
		this.historialCierres = historialCierres;
	}

	public HistorialCierre addHistorialCierre(HistorialCierre historialCierre) {
		getHistorialCierres().add(historialCierre);
		historialCierre.setUbicacione(this);

		return historialCierre;
	}

	public HistorialCierre removeHistorialCierre(HistorialCierre historialCierre) {
		getHistorialCierres().remove(historialCierre);
		historialCierre.setUbicacione(null);

		return historialCierre;
	}

	public List<MovimientosInventario> getMovimientosInventarios1() {
		return this.movimientosInventarios1;
	}

	public void setMovimientosInventarios1(
			List<MovimientosInventario> movimientosInventarios1) {
		this.movimientosInventarios1 = movimientosInventarios1;
	}

	public MovimientosInventario addMovimientosInventarios1(
			MovimientosInventario movimientosInventarios1) {
		getMovimientosInventarios1().add(movimientosInventarios1);
		movimientosInventarios1.setUbicacionDestino(this);

		return movimientosInventarios1;
	}

	public MovimientosInventario removeMovimientosInventarios1(
			MovimientosInventario movimientosInventarios1) {
		getMovimientosInventarios1().remove(movimientosInventarios1);
		movimientosInventarios1.setUbicacionDestino(null);

		return movimientosInventarios1;
	}

	public List<MovimientosInventario> getMovimientosInventarios2() {
		return this.movimientosInventarios2;
	}

	public void setMovimientosInventarios2(
			List<MovimientosInventario> movimientosInventarios2) {
		this.movimientosInventarios2 = movimientosInventarios2;
	}

	public MovimientosInventario addMovimientosInventarios2(
			MovimientosInventario movimientosInventarios2) {
		getMovimientosInventarios2().add(movimientosInventarios2);
		movimientosInventarios2.setUbicacionOrigen(this);

		return movimientosInventarios2;
	}

	public MovimientosInventario removeMovimientosInventarios2(
			MovimientosInventario movimientosInventarios2) {
		getMovimientosInventarios2().remove(movimientosInventarios2);
		movimientosInventarios2.setUbicacionOrigen(null);

		return movimientosInventarios2;
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
		nivelInventarioxubicacion.setUbicacione(this);

		return nivelInventarioxubicacion;
	}

	public NivelInventarioxubicacion removeNivelInventarioxubicacion(
			NivelInventarioxubicacion nivelInventarioxubicacion) {
		getNivelInventarioxubicacions().remove(nivelInventarioxubicacion);
		nivelInventarioxubicacion.setUbicacione(null);

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
		nivelInventarioxubicacionTemp.setUbicacione(this);

		return nivelInventarioxubicacionTemp;
	}

	public NivelInventarioxubicacionTemp removeNivelInventarioxubicacionTemp(
			NivelInventarioxubicacionTemp nivelInventarioxubicacionTemp) {
		getNivelInventarioxubicacionTemps().remove(
				nivelInventarioxubicacionTemp);
		nivelInventarioxubicacionTemp.setUbicacione(null);

		return nivelInventarioxubicacionTemp;
	}

	public List<Saldo> getSaldos() {
		return this.saldos;
	}

	public void setSaldos(List<Saldo> saldos) {
		this.saldos = saldos;
	}

	public Saldo addSaldo(Saldo saldo) {
		getSaldos().add(saldo);
		saldo.setUbicacione(this);

		return saldo;
	}

	public Saldo removeSaldo(Saldo saldo) {
		getSaldos().remove(saldo);
		saldo.setUbicacione(null);

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
		saldosFranquicia.setUbicacione(this);

		return saldosFranquicia;
	}

	public SaldosFranquicia removeSaldosFranquicia(
			SaldosFranquicia saldosFranquicia) {
		getSaldosFranquicias().remove(saldosFranquicia);
		saldosFranquicia.setUbicacione(null);

		return saldosFranquicia;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Region getRegione() {
		return this.regione;
	}

	public void setRegione(Region regione) {
		this.regione = regione;
	}

	public TipoCanal getTipoCanal() {
		return this.tipoCanal;
	}

	public void setTipoCanal(TipoCanal tipoCanal) {
		this.tipoCanal = tipoCanal;
	}

	public Ubicacion getUbicacione() {
		return this.ubicacione;
	}

	public void setUbicacione(Ubicacion ubicacione) {
		this.ubicacione = ubicacione;
	}

	public List<Ubicacion> getUbicaciones() {
		return this.ubicaciones;
	}

	public void setUbicaciones(List<Ubicacion> ubicaciones) {
		this.ubicaciones = ubicaciones;
	}

	public Ubicacion addUbicacione(Ubicacion ubicacione) {
		getUbicaciones().add(ubicacione);
		ubicacione.setUbicacione(this);

		return ubicacione;
	}

	public Ubicacion removeUbicacione(Ubicacion ubicacione) {
		getUbicaciones().remove(ubicacione);
		ubicacione.setUbicacione(null);

		return ubicacione;
	}

	/*
	 * public List<VariablesPedidoSugerido> getVariablesPedidoSugeridos() {
	 * return this.variablesPedidoSugeridos; }
	 * 
	 * public void setVariablesPedidoSugeridos(List<VariablesPedidoSugerido>
	 * variablesPedidoSugeridos) { this.variablesPedidoSugeridos =
	 * variablesPedidoSugeridos; }
	 * 
	 * public VariablesPedidoSugerido
	 * addVariablesPedidoSugerido(VariablesPedidoSugerido
	 * variablesPedidoSugerido) {
	 * getVariablesPedidoSugeridos().add(variablesPedidoSugerido);
	 * variablesPedidoSugerido.setUbicacione(this);
	 * 
	 * return variablesPedidoSugerido; }
	 * 
	 * public VariablesPedidoSugerido
	 * removeVariablesPedidoSugerido(VariablesPedidoSugerido
	 * variablesPedidoSugerido) {
	 * getVariablesPedidoSugeridos().remove(variablesPedidoSugerido);
	 * variablesPedidoSugerido.setUbicacione(null);
	 * 
	 * return variablesPedidoSugerido; }
	 */

	public List<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Venta addVenta(Venta venta) {
		getVentas().add(venta);
		venta.setUbicacione(this);

		return venta;
	}

	public Venta removeVenta(Venta venta) {
		getVentas().remove(venta);
		venta.setUbicacione(null);

		return venta;
	}

}