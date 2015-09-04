SELECT setval('auditoria_id_seq', MAX(id_log)) FROM log_auditoria;

SELECT setval('productosxcliente_id_seq1', MAX(id)) FROM productosxcliente;

SELECT setval('terminos_transporte_id_seq', MAX(id)) FROM terminos_transporte;

SELECT setval('movimientos_inventario_comext_id_seq', MAX(id)) FROM movimientos_inventario_comext;
