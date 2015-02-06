package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

public class ReporteInformeTiendaLineaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3904395049480093683L;
	
	private String tipoLoteOICDesc;
	
	private String docXLotesOICConsec;
	
	private Double totalPesoBrutoItem;
	
	private Double totalPesoNetoItem;
	
	private Double totaCajasItem;
	
	private String docXLotesOICAviso;
	
	private String docXLotesOICAsignacion;
	
	private String docXLotesOICPedido;

	public String getTipoLoteOICDesc() {
		return tipoLoteOICDesc;
	}

	public void setTipoLoteOICDesc(String tipoLoteOICDesc) {
		this.tipoLoteOICDesc = tipoLoteOICDesc;
	}

	public String getDocXLotesOICConsec() {
		return docXLotesOICConsec;
	}

	public void setDocXLotesOICConsec(String docXLotesOICConsec) {
		this.docXLotesOICConsec = docXLotesOICConsec;
	}

	public Double getTotalPesoBrutoItem() {
		return totalPesoBrutoItem;
	}

	public void setTotalPesoBrutoItem(Double totalPesoBrutoItem) {
		this.totalPesoBrutoItem = totalPesoBrutoItem;
	}

	public Double getTotalPesoNetoItem() {
		return totalPesoNetoItem;
	}

	public void setTotalPesoNetoItem(Double totalPesoNetoItem) {
		this.totalPesoNetoItem = totalPesoNetoItem;
	}

	public Double getTotaCajasItem() {
		return totaCajasItem;
	}

	public void setTotaCajasItem(Double totaCajasItem) {
		this.totaCajasItem = totaCajasItem;
	}

	public String getDocXLotesOICAviso() {
		return docXLotesOICAviso;
	}

	public void setDocXLotesOICAviso(String docXLotesOICAviso) {
		this.docXLotesOICAviso = docXLotesOICAviso;
	}

	public String getDocXLotesOICAsignacion() {
		return docXLotesOICAsignacion;
	}

	public void setDocXLotesOICAsignacion(String docXLotesOICAsignacion) {
		this.docXLotesOICAsignacion = docXLotesOICAsignacion;
	}

	public String getDocXLotesOICPedido() {
		return docXLotesOICPedido;
	}

	public void setDocXLotesOICPedido(String docXLotesOICPedido) {
		this.docXLotesOICPedido = docXLotesOICPedido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
