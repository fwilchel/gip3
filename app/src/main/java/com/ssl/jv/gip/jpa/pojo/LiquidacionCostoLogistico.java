package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the liquidacion_costo_logistico database table.
 *
 */
@Entity
@Table(name = "liquidacion_costo_logistico")
@NamedQuery(name = "LiquidacionCostoLogistico.findAll", query = "SELECT l FROM LiquidacionCostoLogistico l")
public class LiquidacionCostoLogistico implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name = "LIQUIDACION_COSTO_LOGISTICO_ID_GENERATOR", sequenceName = "LIQUIDACION_COSTOS_LOGISTICOS_ID_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIQUIDACION_COSTO_LOGISTICO_ID_GENERATOR")
  private Long id;

  @Column(name = "cantidad_1")
  private BigDecimal cantidad1;

  @Column(name = "cantidad_2")
  private BigDecimal cantidad2;

  @Column(name = "cliente_id")
  private Long clienteId;

  @Column(name = "incoterm_transporte_id")
  private Long incotermTransporteId;

  @Column(name = "pais_id")
  private String paisId;

  @Column(name = "puerto_internal")
  private String puertoInternal;

  @Column(name = "puerto_nal")
  private String puertoNal;

  @Column(name = "tipo_contenedor_1")
  private Integer tipoContenedor1;

  @Column(name = "tipo_contenedor_2")
  private Integer tipoContenedor2;
  
  @Column(name = "valor_fob")
  private BigDecimal valorFob;
  
  @Column(name = "valor_fletes")
  private BigDecimal valorFletes;

  @Column(name = "valor_seguros")
  private BigDecimal valorSeguros;

  @Column(name = "valor_total")
  private BigDecimal valorTotal;

  //bi-directional many-to-one association to LiquidacionDocumento
  @OneToMany(mappedBy = "liquidacionCostoLogistico", cascade = CascadeType.PERSIST)
  private List<LiquidacionDocumento> liquidacionDocumentos;

  //bi-directional many-to-one association to LiquidacionItem
  @OneToMany(mappedBy = "liquidacionCostoLogistico", cascade = CascadeType.PERSIST)
  private List<LiquidacionItem> liquidacionItems;

  public LiquidacionCostoLogistico() {
    this.liquidacionDocumentos = new ArrayList<LiquidacionDocumento>();
    this.liquidacionItems = new ArrayList<LiquidacionItem>();
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getCantidad1() {
    return this.cantidad1;
  }

  public void setCantidad1(BigDecimal cantidad1) {
    this.cantidad1 = cantidad1;
  }

  public BigDecimal getCantidad2() {
    return this.cantidad2;
  }

  public void setCantidad2(BigDecimal cantidad2) {
    this.cantidad2 = cantidad2;
  }

  public Long getClienteId() {
    return this.clienteId;
  }

  public void setClienteId(Long clienteId) {
    this.clienteId = clienteId;
  }

  public Long getIncotermTransporteId() {
    return this.incotermTransporteId;
  }

  public void setIncotermTransporteId(Long incotermTransporteId) {
    this.incotermTransporteId = incotermTransporteId;
  }

  public String getPaisId() {
    return this.paisId;
  }

  public void setPaisId(String paisId) {
    this.paisId = paisId;
  }

  public String getPuertoInternal() {
    return this.puertoInternal;
  }

  public void setPuertoInternal(String puertoInternal) {
    this.puertoInternal = puertoInternal;
  }

  public String getPuertoNal() {
    return this.puertoNal;
  }

  public void setPuertoNal(String puertoNal) {
    this.puertoNal = puertoNal;
  }

  public Integer getTipoContenedor1() {
    return this.tipoContenedor1;
  }

  public void setTipoContenedor1(Integer tipoContenedor1) {
    this.tipoContenedor1 = tipoContenedor1;
  }

  public Integer getTipoContenedor2() {
    return this.tipoContenedor2;
  }

  public void setTipoContenedor2(Integer tipoContenedor2) {
    this.tipoContenedor2 = tipoContenedor2;
  }

  public List<LiquidacionDocumento> getLiquidacionDocumentos() {
    return this.liquidacionDocumentos;
  }

  public void setLiquidacionDocumentos(List<LiquidacionDocumento> liquidacionDocumentos) {
    this.liquidacionDocumentos = liquidacionDocumentos;
  }

  public LiquidacionDocumento addLiquidacionDocumento(LiquidacionDocumento liquidacionDocumento) {
    getLiquidacionDocumentos().add(liquidacionDocumento);
    liquidacionDocumento.setLiquidacionCostoLogistico(this);

    return liquidacionDocumento;
  }

  public LiquidacionDocumento removeLiquidacionDocumento(LiquidacionDocumento liquidacionDocumento) {
    getLiquidacionDocumentos().remove(liquidacionDocumento);
    liquidacionDocumento.setLiquidacionCostoLogistico(null);

    return liquidacionDocumento;
  }

  public List<LiquidacionItem> getLiquidacionItems() {
    return this.liquidacionItems;
  }

  public void setLiquidacionItems(List<LiquidacionItem> liquidacionItems) {
    this.liquidacionItems = liquidacionItems;
  }

  public LiquidacionItem addLiquidacionItem(LiquidacionItem liquidacionItem) {
    getLiquidacionItems().add(liquidacionItem);
    liquidacionItem.setLiquidacionCostoLogistico(this);

    return liquidacionItem;
  }

  public LiquidacionItem removeLiquidacionItem(LiquidacionItem liquidacionItem) {
    getLiquidacionItems().remove(liquidacionItem);
    liquidacionItem.setLiquidacionCostoLogistico(null);

    return liquidacionItem;
  }

	public BigDecimal getValorFob() {
		return valorFob;
	}
	
	public void setValorFob(BigDecimal valorFob) {
		this.valorFob = valorFob;
	}
	
	public BigDecimal getValorFletes() {
		return valorFletes;
	}
	
	public void setValorFletes(BigDecimal valorFletes) {
		this.valorFletes = valorFletes;
	}
	
	public BigDecimal getValorSeguros() {
		return valorSeguros;
	}
	
	public void setValorSeguros(BigDecimal valorSeguros) {
		this.valorSeguros = valorSeguros;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

}
