package com.ssl.jv.gip.web.util;

public enum Parametro {
	CONTRASENA_DEFECTO(1L), DEBUG(2L), AMBIENTE(3L), VERSION(4L);
	
	private Long id;
	
	private Parametro(Long id){
		this.id=id;
	}

	public Long getId() {
		return id;
	}
	
	
}
