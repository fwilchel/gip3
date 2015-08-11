SELECT setval('auditoria_id_seq', MAX(id_log)) FROM log_auditoria;

SELECT setval('productosxcliente_id_seq1', MAX(id)) FROM productosxcliente;
