package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the rango_costo_logistico database table.
 * 
 */
@Entity
@Table(name="rango_costo_logistico")
@NamedQueries({
	@NamedQuery(name="RangoCostoLogistico.findAll", query="SELECT r FROM RangoCostoLogistico r"),
	@NamedQuery(name="RangoCostoLogistico.findByItem", query="SELECT r FROM RangoCostoLogistico r WHERE r.itemCostoLogistico= :itemCostoLogistico")
})

public class RangoCostoLogistico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RANGO_COSTO_LOGISTICO_ID_GENERATOR", sequenceName="RANGOS_COSTOS_LOGISTICOS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RANGO_COSTO_LOGISTICO_ID_GENERATOR")
	private Long id;

	private BigDecimal desde;

	private BigDecimal hasta;

	@Column(name="id_unidad")
	private Long idUnidad;

	@Column(name="valor_usd")
	private BigDecimal valorUsd;

	//bi-directional many-to-one association to ItemCostoLogistico
	@ManyToOne
	@JoinColumn(name="id_item_costo_logistico")
	private ItemCostoLogistico itemCostoLogistico;

	public RangoCostoLogistico() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getDesde() {
		return this.desde;
	}

	public void setDesde(BigDecimal desde) {
		this.desde = desde;
	}

	public BigDecimal getHasta() {
		return this.hasta;
	}

	public void setHasta(BigDecimal hasta) {
		this.hasta = hasta;
	}

	public Long getIdUnidad() {
		return this.idUnidad;
	}

	public void setIdUnidad(Long idUnidad) {
		this.idUnidad = idUnidad;
	}

	public BigDecimal getValorUsd() {
		return this.valorUsd;
	}

	public void setValorUsd(BigDecimal valorUsd) {
		this.valorUsd = valorUsd;
	}

	public ItemCostoLogistico getItemCostoLogistico() {
		return this.itemCostoLogistico;
	}

	public void setItemCostoLogistico(ItemCostoLogistico itemCostoLogistico) {
		this.itemCostoLogistico = itemCostoLogistico;
	}

}