package com.ssl.jv.gip.util;

public enum TipoMovimiento {

	ENTRADA(1L), SALIDA(2L);

	private long codigo;

	private TipoMovimiento(long codigo) {
		this.codigo = codigo;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

}
