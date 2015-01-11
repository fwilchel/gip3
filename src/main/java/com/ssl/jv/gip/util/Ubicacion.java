package com.ssl.jv.gip.util;

public enum Ubicacion {

	EXTERNA(-1L), TRANSITO(-2L);

	private Long codigo;

	private Ubicacion(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
