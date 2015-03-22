package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the liquidacion_item database table.
 * 
 */
@Entity
@Table(name="liquidacion_item")
@NamedQuery(name="LiquidacionItem.findAll", query="SELECT l FROM LiquidacionItem l")
public class LiquidacionItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LIQUIDACION_ITEM_ID_GENERATOR", sequenceName="LIQUIDACION_ITEM_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LIQUIDACION_ITEM_ID_GENERATOR")
	private Long id;

	@Column(name="base_fob")
	private Boolean baseFob;

	@Column(name="campo_acumula")
	private String campoAcumula;

	private BigDecimal cantidad;

	@Column(name="categoria_id")
	private Long categoriaId;

	@Column(name="consecutivo_documento")
	private String consecutivoDocumento;

	@Column(name="item_id")
	private Long itemId;

	private Integer orden;

	private Integer tipo;

	private BigDecimal valor;

	@Column(name="valor_minimo")
	private BigDecimal valorMinimo;

	//bi-directional many-to-one association to LiquidacionCostoLogistico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="liquidacion_id")
	private LiquidacionCostoLogistico liquidacionCostoLogistico;

	public LiquidacionItem() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getBaseFob() {
		return this.baseFob;
	}

	public void setBaseFob(Boolean baseFob) {
		this.baseFob = baseFob;
	}

	public String getCampoAcumula() {
		return this.campoAcumula;
	}

	public void setCampoAcumula(String campoAcumula) {
		this.campoAcumula = campoAcumula;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public Long getCategoriaId() {
		return this.categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getConsecutivoDocumento() {
		return this.consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getTipo() {
		return this.tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorMinimo() {
		return this.valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public LiquidacionCostoLogistico getLiquidacionCostoLogistico() {
		return this.liquidacionCostoLogistico;
	}

	public void setLiquidacionCostoLogistico(LiquidacionCostoLogistico liquidacionCostoLogistico) {
		this.liquidacionCostoLogistico = liquidacionCostoLogistico;
	}

}