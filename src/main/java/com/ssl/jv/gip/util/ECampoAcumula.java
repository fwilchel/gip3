package com.ssl.jv.gip.util;

public enum ECampoAcumula {
	
	FOB("FOB"), FLETES("Fletes"), SEGURO("Seguro");
	
	private String descripcion;
	
	private ECampoAcumula(String descripcion){
		this.descripcion=descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	
}
