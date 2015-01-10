package com.ssl.jv.gip.util;

public enum Estado {

	ACTIVO(1), INACTIVO(2), CERRADA(3), COSTEADO(4), GENERADO(5), REVISADO_TIENDA(
			6), PENDIENTE_RECIBIR(7), RECIBIDO(8), RECIBIDO_PARCIAL(9), DEVUELTO_A_PROV(
			10), ANULADO(11), IMPRESO(12), REMISIONADA(13), VERIFICADO(14), APROBADA(
			15), ASIGNADA(16), LISTADA(17), AUTORIZADO(18), DESPACHADA(19);

	private long codigo;

	private Estado(long codigo) {
		this.codigo = codigo;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
