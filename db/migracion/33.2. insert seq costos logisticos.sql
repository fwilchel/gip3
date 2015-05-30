SELECT setval('categorias_costos_id_seq', MAX(id)) FROM categorias_costos_logisticos;

SELECT setval('items_costos_id_seq', MAX(id)) FROM item_costo_logistico;

SELECT setval('rangos_costos_logisticos_id_seq', MAX(id)) FROM rango_costo_logistico;
