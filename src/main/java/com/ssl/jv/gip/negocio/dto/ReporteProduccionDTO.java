package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

public class ReporteProduccionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5411565999770669228L;
	
	private double cantidad1;
	
	private String piSKU;
	
	private String piNombre;
	
	private String clNombrePais;
	
	private String clNombreTipoCanal;
	
	private String ubicacion;
	
	private String clNombre;
	
	private String docConsecutivo;

	public double getCantidad1() {
		return cantidad1;
	}

	public void setCantidad1(double cantidad1) {
		this.cantidad1 = cantidad1;
	}

	public String getPiSKU() {
		return piSKU;
	}

	public void setPiSKU(String piSKU) {
		this.piSKU = piSKU;
	}

	public String getPiNombre() {
		return piNombre;
	}

	public void setPiNombre(String piNombre) {
		this.piNombre = piNombre;
	}

	public String getClNombrePais() {
		return clNombrePais;
	}

	public void setClNombrePais(String clNombrePais) {
		this.clNombrePais = clNombrePais;
	}

	public String getClNombreTipoCanal() {
		return clNombreTipoCanal;
	}

	public void setClNombreTipoCanal(String clNombreTipoCanal) {
		this.clNombreTipoCanal = clNombreTipoCanal;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getClNombre() {
		return clNombre;
	}

	public void setClNombre(String clNombre) {
		this.clNombre = clNombre;
	}

	public String getDocConsecutivo() {
		return docConsecutivo;
	}

	public void setDocConsecutivo(String docConsecutivo) {
		this.docConsecutivo = docConsecutivo;
	}
	
	
	
}


