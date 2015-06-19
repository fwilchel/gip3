package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ComextRequerimientoexportacionDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7683662012688100031L;
	
	  @Id
	  private BigInteger id;
	
	private String consecutivodocumento;
	private String sku;
	private String nombreproducto;
	private Boolean cajamaster;
	private Boolean pallet;
	private Boolean observacion;
	private String observaciones;
	/**
	 * @return the consecutivodocumento
	 */
	public String getConsecutivodocumento() {
		return consecutivodocumento;
	}
	/**
	 * @param consecutivodocumento the consecutivodocumento to set
	 */
	public void setConsecutivodocumento(String consecutivodocumento) {
		this.consecutivodocumento = consecutivodocumento;
	}
	/**
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}
	/**
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}
	/**
	 * @return the nombreproducto
	 */
	public String getNombreproducto() {
		return nombreproducto;
	}
	/**
	 * @param nombreproducto the nombreproducto to set
	 */
	public void setNombreproducto(String nombreproducto) {
		this.nombreproducto = nombreproducto;
	}
	/**
	 * @return the cajamaster
	 */
	public Boolean getCajamaster() {
		return cajamaster;
	}
	/**
	 * @param cajamaster the cajamaster to set
	 */
	public void setCajamaster(Boolean cajamaster) {
		this.cajamaster = cajamaster;
	}
	/**
	 * @return the pallet
	 */
	public Boolean getPallet() {
		return pallet;
	}
	/**
	 * @param pallet the pallet to set
	 */
	public void setPallet(Boolean pallet) {
		this.pallet = pallet;
	}
	/**
	 * @return the observacion
	 */
	public Boolean getObservacion() {
		return observacion;
	}
	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(Boolean observacion) {
		this.observacion = observacion;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the id
	 */
	public BigInteger getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}
	

	
	}
