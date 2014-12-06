package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the costo_ventas database table.
 * 
 */
@Entity
@Table(name="costo_ventas")
@NamedQuery(name="CostoVenta.findAll", query="SELECT c FROM CostoVenta c")
public class CostoVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private BigDecimal ajustes;

	private BigDecimal bajas;

	private BigDecimal compras;

	private BigDecimal consumos;

	@Column(name="consumos_ce")
	private BigDecimal consumosCe;

	@Column(name="consumos_gp")
	private BigDecimal consumosGp;

	@Column(name="consumos_ventas")
	private BigDecimal consumosVentas;

	@Column(name="costo_compras_ml")
	private BigDecimal costoComprasMl;

	@Column(name="costo_final_ml")
	private BigDecimal costoFinalMl;

	@Column(name="costo_inicial_ml")
	private BigDecimal costoInicialMl;

	@Column(name="costo_mercancia_vendida_ml")
	private BigDecimal costoMercanciaVendidaMl;

	@Column(name="costo_ml")
	private double costoMl;

	@Column(name="costos_ajustes_ml")
	private BigDecimal costosAjustesMl;

	@Column(name="costos_bj_ml")
	private BigDecimal costosBjMl;

	@Column(name="costos_ce_ml")
	private BigDecimal costosCeMl;

	@Column(name="costos_gp_ml")
	private BigDecimal costosGpMl;

	@Column(name="costos_traslados_entradas_ml")
	private BigDecimal costosTrasladosEntradasMl;

	@Column(name="costos_traslados_salidas_ml")
	private BigDecimal costosTrasladosSalidasMl;

	@Column(name="costos_ventas_ml")
	private BigDecimal costosVentasMl;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicial")
	private Date fechaInicial;

	@Column(name="inventario_final")
	private BigDecimal inventarioFinal;

	@Column(name="inventario_inicial")
	private BigDecimal inventarioInicial;

	@Column(name="traslados_entradas")
	private BigDecimal trasladosEntradas;

	@Column(name="traslados_salidas")
	private BigDecimal trasladosSalidas;

	//bi-directional many-to-one association to CategoriasInventario
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private CategoriasInventario categoriasInventario;

	//bi-directional many-to-one association to Moneda
	@ManyToOne
	@JoinColumn(name="id_ml")
	private Moneda moneda;

	//bi-directional many-to-one association to ProductosInventario
	@ManyToOne
	@JoinColumn(name="id_producto")
	private ProductosInventario productosInventario;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name="id_ubicacion")
	private Ubicacion ubicacione;

	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="id_unidad")
	private Unidad unidade;

	public CostoVenta() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAjustes() {
		return this.ajustes;
	}

	public void setAjustes(BigDecimal ajustes) {
		this.ajustes = ajustes;
	}

	public BigDecimal getBajas() {
		return this.bajas;
	}

	public void setBajas(BigDecimal bajas) {
		this.bajas = bajas;
	}

	public BigDecimal getCompras() {
		return this.compras;
	}

	public void setCompras(BigDecimal compras) {
		this.compras = compras;
	}

	public BigDecimal getConsumos() {
		return this.consumos;
	}

	public void setConsumos(BigDecimal consumos) {
		this.consumos = consumos;
	}

	public BigDecimal getConsumosCe() {
		return this.consumosCe;
	}

	public void setConsumosCe(BigDecimal consumosCe) {
		this.consumosCe = consumosCe;
	}

	public BigDecimal getConsumosGp() {
		return this.consumosGp;
	}

	public void setConsumosGp(BigDecimal consumosGp) {
		this.consumosGp = consumosGp;
	}

	public BigDecimal getConsumosVentas() {
		return this.consumosVentas;
	}

	public void setConsumosVentas(BigDecimal consumosVentas) {
		this.consumosVentas = consumosVentas;
	}

	public BigDecimal getCostoComprasMl() {
		return this.costoComprasMl;
	}

	public void setCostoComprasMl(BigDecimal costoComprasMl) {
		this.costoComprasMl = costoComprasMl;
	}

	public BigDecimal getCostoFinalMl() {
		return this.costoFinalMl;
	}

	public void setCostoFinalMl(BigDecimal costoFinalMl) {
		this.costoFinalMl = costoFinalMl;
	}

	public BigDecimal getCostoInicialMl() {
		return this.costoInicialMl;
	}

	public void setCostoInicialMl(BigDecimal costoInicialMl) {
		this.costoInicialMl = costoInicialMl;
	}

	public BigDecimal getCostoMercanciaVendidaMl() {
		return this.costoMercanciaVendidaMl;
	}

	public void setCostoMercanciaVendidaMl(BigDecimal costoMercanciaVendidaMl) {
		this.costoMercanciaVendidaMl = costoMercanciaVendidaMl;
	}

	public double getCostoMl() {
		return this.costoMl;
	}

	public void setCostoMl(double costoMl) {
		this.costoMl = costoMl;
	}

	public BigDecimal getCostosAjustesMl() {
		return this.costosAjustesMl;
	}

	public void setCostosAjustesMl(BigDecimal costosAjustesMl) {
		this.costosAjustesMl = costosAjustesMl;
	}

	public BigDecimal getCostosBjMl() {
		return this.costosBjMl;
	}

	public void setCostosBjMl(BigDecimal costosBjMl) {
		this.costosBjMl = costosBjMl;
	}

	public BigDecimal getCostosCeMl() {
		return this.costosCeMl;
	}

	public void setCostosCeMl(BigDecimal costosCeMl) {
		this.costosCeMl = costosCeMl;
	}

	public BigDecimal getCostosGpMl() {
		return this.costosGpMl;
	}

	public void setCostosGpMl(BigDecimal costosGpMl) {
		this.costosGpMl = costosGpMl;
	}

	public BigDecimal getCostosTrasladosEntradasMl() {
		return this.costosTrasladosEntradasMl;
	}

	public void setCostosTrasladosEntradasMl(BigDecimal costosTrasladosEntradasMl) {
		this.costosTrasladosEntradasMl = costosTrasladosEntradasMl;
	}

	public BigDecimal getCostosTrasladosSalidasMl() {
		return this.costosTrasladosSalidasMl;
	}

	public void setCostosTrasladosSalidasMl(BigDecimal costosTrasladosSalidasMl) {
		this.costosTrasladosSalidasMl = costosTrasladosSalidasMl;
	}

	public BigDecimal getCostosVentasMl() {
		return this.costosVentasMl;
	}

	public void setCostosVentasMl(BigDecimal costosVentasMl) {
		this.costosVentasMl = costosVentasMl;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public BigDecimal getInventarioFinal() {
		return this.inventarioFinal;
	}

	public void setInventarioFinal(BigDecimal inventarioFinal) {
		this.inventarioFinal = inventarioFinal;
	}

	public BigDecimal getInventarioInicial() {
		return this.inventarioInicial;
	}

	public void setInventarioInicial(BigDecimal inventarioInicial) {
		this.inventarioInicial = inventarioInicial;
	}

	public BigDecimal getTrasladosEntradas() {
		return this.trasladosEntradas;
	}

	public void setTrasladosEntradas(BigDecimal trasladosEntradas) {
		this.trasladosEntradas = trasladosEntradas;
	}

	public BigDecimal getTrasladosSalidas() {
		return this.trasladosSalidas;
	}

	public void setTrasladosSalidas(BigDecimal trasladosSalidas) {
		this.trasladosSalidas = trasladosSalidas;
	}

	public CategoriasInventario getCategoriasInventario() {
		return this.categoriasInventario;
	}

	public void setCategoriasInventario(CategoriasInventario categoriasInventario) {
		this.categoriasInventario = categoriasInventario;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public ProductosInventario getProductosInventario() {
		return this.productosInventario;
	}

	public void setProductosInventario(ProductosInventario productosInventario) {
		this.productosInventario = productosInventario;
	}

	public Ubicacion getUbicacione() {
		return this.ubicacione;
	}

	public void setUbicacione(Ubicacion ubicacione) {
		this.ubicacione = ubicacione;
	}

	public Unidad getUnidade() {
		return this.unidade;
	}

	public void setUnidade(Unidad unidade) {
		this.unidade = unidade;
	}

}