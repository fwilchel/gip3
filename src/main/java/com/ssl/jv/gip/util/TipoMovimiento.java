package com.ssl.jv.gip.util;

public enum TipoMovimiento {

	ENTRADA(1L, "E"), SALIDA(2L, "S");

	private long codigo;
	private String nombre;

	private TipoMovimiento(long codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public long getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

}
