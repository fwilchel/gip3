package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the item_costo_logistico database table.
 * 
 */
@Entity
@Table(name="item_costo_logistico")
@NamedQuery(name="ItemCostoLogistico.findAll", query="SELECT i FROM ItemCostoLogistico i")
public class ItemCostoLogistico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ITEM_COSTO_LOGISTICO_ID_GENERATOR", sequenceName="ITEMS_COSTOS_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_COSTO_LOGISTICO_ID_GENERATOR")
	private Long id;

	@Column(name="aplica_cfa")
	private Boolean aplicaCfa;

	@Column(name="aplica_cfr")
	private Boolean aplicaCfr;

	@Column(name="aplica_cif")
	private Boolean aplicaCif;

	@Column(name="aplica_cip")
	private Boolean aplicaCip;

	@Column(name="aplica_dap")
	private Boolean aplicaDap;

	@Column(name="aplica_fob")
	private Boolean aplicaFob;

	private String descripcion;

	@Column(name="id_pais_destino")
	private String idPaisDestino;

	private String nombre;

	@Column(name="nombre_puerto_nal")
	private String nombrePuertoNal;

	@Column(name="nombre_puertos_nal_internal")
	private String nombrePuertosNalInternal;

	private Integer tipo;

	@Column(name="tipo_contenedor")
	private Integer tipoContenedor;

	@Column(name="valor_minimo_usd")
	private BigDecimal valorMinimoUsd;

	@Column(name="valor_usd")
	private BigDecimal valorUsd;

	//bi-directional many-to-one association to CategoriasCostosLogistico
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private CategoriaCostoLogistico categoriasCostosLogistico;

	//bi-directional many-to-one association to RangoCostoLogistico
	@OneToMany(mappedBy="itemCostoLogistico")
	private List<RangoCostoLogistico> rangoCostoLogisticos;

	public ItemCostoLogistico() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAplicaCfa() {
		return this.aplicaCfa;
	}

	public void setAplicaCfa(Boolean aplicaCfa) {
		this.aplicaCfa = aplicaCfa;
	}

	public Boolean getAplicaCfr() {
		return this.aplicaCfr;
	}

	public void setAplicaCfr(Boolean aplicaCfr) {
		this.aplicaCfr = aplicaCfr;
	}

	public Boolean getAplicaCif() {
		return this.aplicaCif;
	}

	public void setAplicaCif(Boolean aplicaCif) {
		this.aplicaCif = aplicaCif;
	}

	public Boolean getAplicaCip() {
		return this.aplicaCip;
	}

	public void setAplicaCip(Boolean aplicaCip) {
		this.aplicaCip = aplicaCip;
	}

	public Boolean getAplicaDap() {
		return this.aplicaDap;
	}

	public void setAplicaDap(Boolean aplicaDap) {
		this.aplicaDap = aplicaDap;
	}

	public Boolean getAplicaFob() {
		return this.aplicaFob;
	}

	public void setAplicaFob(Boolean aplicaFob) {
		this.aplicaFob = aplicaFob;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdPaisDestino() {
		return this.idPaisDestino;
	}

	public void setIdPaisDestino(String idPaisDestino) {
		this.idPaisDestino = idPaisDestino;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombrePuertoNal() {
		return this.nombrePuertoNal;
	}

	public void setNombrePuertoNal(String nombrePuertoNal) {
		this.nombrePuertoNal = nombrePuertoNal;
	}

	public String getNombrePuertosNalInternal() {
		return this.nombrePuertosNalInternal;
	}

	public void setNombrePuertosNalInternal(String nombrePuertosNalInternal) {
		this.nombrePuertosNalInternal = nombrePuertosNalInternal;
	}

	public Integer getTipo() {
		return this.tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getTipoContenedor() {
		return this.tipoContenedor;
	}

	public void setTipoContenedor(Integer tipoContenedor) {
		this.tipoContenedor = tipoContenedor;
	}

	public BigDecimal getValorMinimoUsd() {
		return this.valorMinimoUsd;
	}

	public void setValorMinimoUsd(BigDecimal valorMinimoUsd) {
		this.valorMinimoUsd = valorMinimoUsd;
	}

	public BigDecimal getValorUsd() {
		return this.valorUsd;
	}

	public void setValorUsd(BigDecimal valorUsd) {
		this.valorUsd = valorUsd;
	}

	public CategoriaCostoLogistico getCategoriasCostosLogistico() {
		return this.categoriasCostosLogistico;
	}

	public void setCategoriasCostosLogistico(CategoriaCostoLogistico categoriasCostosLogistico) {
		this.categoriasCostosLogistico = categoriasCostosLogistico;
	}

	public List<RangoCostoLogistico> getRangoCostoLogisticos() {
		return this.rangoCostoLogisticos;
	}

	public void setRangoCostoLogisticos(List<RangoCostoLogistico> rangoCostoLogisticos) {
		this.rangoCostoLogisticos = rangoCostoLogisticos;
	}

	public RangoCostoLogistico addRangoCostoLogistico(RangoCostoLogistico rangoCostoLogistico) {
		getRangoCostoLogisticos().add(rangoCostoLogistico);
		rangoCostoLogistico.setItemCostoLogistico(this);

		return rangoCostoLogistico;
	}

	public RangoCostoLogistico removeRangoCostoLogistico(RangoCostoLogistico rangoCostoLogistico) {
		getRangoCostoLogisticos().remove(rangoCostoLogistico);
		rangoCostoLogistico.setItemCostoLogistico(null);

		return rangoCostoLogistico;
	}

}