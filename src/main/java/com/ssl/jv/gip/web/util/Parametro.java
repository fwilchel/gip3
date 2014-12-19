package com.ssl.jv.gip.web.util;

public enum Parametro {
	CONTRASENA_DEFECTO(1L);
	
	private Long id;
	
	private Parametro(Long id){
		this.id=id;
	}

	public Long getId() {
		return id;
	}
	
	
}
