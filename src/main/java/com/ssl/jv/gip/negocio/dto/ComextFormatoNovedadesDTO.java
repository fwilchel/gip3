package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.Date;

public class ComextFormatoNovedadesDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2647339829681076341L;

	private Long id;
	
	private String distribucionOptima;
	
	private String docSolicitado;
	
	private String formatoDiligenciado;
	
	private String prodCompleto;
	
	private String prodBuenEstado;
	
	private String prodTiempo;
	
	private String obsdistoptima;
	
	private String obsdocsolicitados;
	
	private String obsprodbuenestado;
	
	private String obsprodcompleto;
	
	private String obsprodtiempo;
	
	private String consecutivo;
	
	private Date fecha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDistribucionOptima() {
		return distribucionOptima;
	}

	public void setDistribucionOptima(String distribucionOptima) {
		this.distribucionOptima = distribucionOptima;
	}

	public String getDocSolicitado() {
		return docSolicitado;
	}

	public void setDocSolicitado(String docSolicitado) {
		this.docSolicitado = docSolicitado;
	}

	public String getFormatoDiligenciado() {
		return formatoDiligenciado;
	}

	public void setFormatoDiligenciado(String formatoDiligenciado) {
		this.formatoDiligenciado = formatoDiligenciado;
	}

	public String getProdCompleto() {
		return prodCompleto;
	}

	public void setProdCompleto(String prodCompleto) {
		this.prodCompleto = prodCompleto;
	}

	public String getProdBuenEstado() {
		return prodBuenEstado;
	}

	public void setProdBuenEstado(String prodBuenEstado) {
		this.prodBuenEstado = prodBuenEstado;
	}

	public String getProdTiempo() {
		return prodTiempo;
	}

	public void setProdTiempo(String prodTiempo) {
		this.prodTiempo = prodTiempo;
	}

	public String getObsdistoptima() {
		return obsdistoptima;
	}

	public void setObsdistoptima(String obsdistoptima) {
		this.obsdistoptima = obsdistoptima;
	}

	public String getObsdocsolicitados() {
		return obsdocsolicitados;
	}

	public void setObsdocsolicitados(String obsdocsolicitados) {
		this.obsdocsolicitados = obsdocsolicitados;
	}

	public String getObsprodbuenestado() {
		return obsprodbuenestado;
	}

	public void setObsprodbuenestado(String obsprodbuenestado) {
		this.obsprodbuenestado = obsprodbuenestado;
	}

	public String getObsprodcompleto() {
		return obsprodcompleto;
	}

	public void setObsprodcompleto(String obsprodcompleto) {
		this.obsprodcompleto = obsprodcompleto;
	}

	public String getObsprodtiempo() {
		return obsprodtiempo;
	}

	public void setObsprodtiempo(String obsprodtiempo) {
		this.obsprodtiempo = obsprodtiempo;
	}

	public String getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
