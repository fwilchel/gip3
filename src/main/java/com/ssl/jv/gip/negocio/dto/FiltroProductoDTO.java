package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

public class FiltroProductoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4718485839592479544L;
	
	private String nombre;
	
	private String sku;
	
	private Long idCategoria;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	
	
	

}
