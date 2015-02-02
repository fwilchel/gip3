package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

public class ReporteComprobanteInformeDiarioDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8141860862272863492L;
	String descripcion;
	Double total;
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	

}
