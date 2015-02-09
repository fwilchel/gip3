package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import com.ssl.jv.gip.util.TipoItemCostoLogistico;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the item_costo_logistico database table.
 * 
 */
@Entity
@Table(name="item_costo_logistico")
@NamedQuery(name="ItemCostoLogistico.findAll", query="SELECT DISTINCT i FROM ItemCostoLogistico i LEFT JOIN FETCH i.categoriaCostoLogistico ccl LEFT JOIN FETCH i.rangoCostoLogisticos r LEFT JOIN FETCH r.unidad u LEFT JOIN FETCH i.moneda m")
public class ItemCostoLogistico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ITEM_COSTO_LOGISTICO_ID_GENERATOR", sequenceName="ITEMS_COSTOS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_COSTO_LOGISTICO_ID_GENERATOR")
	private Long id;

	@Column(name="aplica_fca")
	private Boolean aplicaFca;

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

	@Column(name="aplica_fob_in")
	private Boolean aplicaFobIn;
	
	@Column(name="base_fob")
	private Boolean baseFob;
	
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

	@Column(name="valor_minimo")
	private BigDecimal valorMinimo;

	@Column(name="valor")
	private BigDecimal valor;

	//bi-directional many-to-one association to CategoriasCostosLogistico
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private CategoriaCostoLogistico categoriaCostoLogistico;
	
	@ManyToOne
	@JoinColumn(name="id_moneda")
	private Moneda moneda;

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

	public Boolean getAplicaFca() {
		return this.aplicaFca;
	}

	public void setAplicaFca(Boolean aplicaFca) {
		this.aplicaFca = aplicaFca;
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

	public BigDecimal getValorMinimo() {
		return this.valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public CategoriaCostoLogistico getCategoriaCostoLogistico() {
		return this.categoriaCostoLogistico;
	}

	public void setCategoriaCostoLogistico(CategoriaCostoLogistico categoriaCostoLogistico) {
		this.categoriaCostoLogistico = categoriaCostoLogistico;
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
	
	public Boolean getBaseFob() {
		return baseFob;
	}

	public void setBaseFob(Boolean baseFob) {
		this.baseFob = baseFob;
	}

	@Transient
	public String getTipoNombre(){
		for (TipoItemCostoLogistico i:TipoItemCostoLogistico.values()){
			if (i.getId().equals(this.tipo))
				return i.getDescripcion();
		}
		return null;
	}

	public Boolean getAplicaFobIn() {
		return aplicaFobIn;
	}

	public void setAplicaFobIn(Boolean aplicaFobIn) {
		this.aplicaFobIn = aplicaFobIn;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

}