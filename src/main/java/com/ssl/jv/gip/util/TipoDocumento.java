package com.ssl.jv.gip.util;

public enum TipoDocumento {

	SUGERENCIA_DE_REAPROVICIONAMIENTO(1, "SR"), SUGERENCIA_DE_COMPRAS(2, "SC"), ORDEN_DE_DESPACHO(
			3, "OD"), ORDEN_DE_COMPRA(4, "OC"), REMISION(5, "RM"), ORDEN_DE_REQUISICION(
			6, "RQ"), DEVOLUCIÓN(7, "DV"), CONVERSIÓN(8, "CV"), BAJA(9, "BJ"), VENTA_EN_TIENDA(
			10, "VT"), VENTA_DIRECTA(11, "VD"), TRASLADO_BODEGAS(12, "TB"), AJUSTES_INVENTARIO(
			14, "AJ"), AJUSTE_VIRTUAL(15, "AV"), COMIDA_EMPLEADO(16, "CE"), GASTO_PUBLICIDAD(
			17, "GP"), VENTA_FRANQUICIA(18, "VF"), DEVOLUCION_VENTA_FRANQUICIA(
			19, "DF"), FACTURA_DIRECTA(20, "FD"), FACTURA_ESPECIAL(21, "FE"), SOLICITUD_PEDIDO(
			22, "SP"), FACTURA_PROFORMA(23, "FP"), LISTA_EMPAQUE(24, "LE"), FACTURA_EXPORTACION(
			25, "FX");

	private long codigo;
	private String abreviatura;

	TipoDocumento(int id, String abreviatura) {
		this.codigo = id;
		this.abreviatura = abreviatura;
	}

	public long getCodigo() {
		return codigo;
	}

	public String getAbreviatura() {
		return abreviatura;
	}
}
