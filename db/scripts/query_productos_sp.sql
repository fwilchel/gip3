SELECT  
productos_inventario.id,
productos_inventario.nombre,
productos_inventario.sku,
productos_inventario.id_ud,
productosXdocumentos.cantidad1,
productos_x_cliente_comext.precio,
productosXdocumentos.cantidad1*productos_x_cliente_comext.precio as VALOR_TOTAL,
(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 ELSE round((productos_inventario_comext.peso_neto_embalaje/productos_inventario_comext.cantidad_x_embalaje),3)*productosXdocumentos.cantidad1 END) as TotalPesoNeto,
(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 ELSE (productos_inventario_comext.peso_bruto_embalaje/productos_inventario_comext.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END) as TotalPesoBruto, 
productos_x_cliente_comext.id_cliente,
productos_inventario_comext.peso_bruto,
productos_inventario_comext.peso_neto,
productos_inventario_comext.cant_cajas_x_tendido,
productos_inventario_comext.total_cajas_x_pallet,
productos_inventario_comext.cantidad_x_embalaje, 
(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje) END) as TotalCajas,
(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0 or productos_inventario_comext.cant_cajas_x_tendido = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje)/productos_inventario_comext.cant_cajas_x_tendido END) AS TotalCajasTendido,
(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0 or productos_inventario_comext.total_cajas_x_pallet = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje)/productos_inventario_comext.total_cajas_x_pallet END) AS TotalCajasPallet,
productos_inventario_comext.posicion_arancelaria, 
unidades.nombre AS NOMBRE_UNIDAD, 
unidades.nombre_ingles, 
productos_inventario_comext.descripcion, 
productos_inventario_comext.peso_neto_embalaje, 
productos_inventario_comext.peso_bruto_embalaje, 
productos_inventario_comext.controlstock, 
productos_inventario_comext.nombre_prd_proveedor as PRODUCTO_CLIENTE 
FROM productosXdocumentos 
LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id 
LEFT JOIN productos_x_cliente_comext ON productos_x_cliente_comext.id_producto=productosXdocumentos.id_producto 
LEFT JOIN productos_inventario_comext ON productos_inventario_comext.id_producto=productos_inventario.id 
INNER JOIN unidades ON productos_inventario.id_uv=unidades.id 
WHERE productosXdocumentos.id_documento=501795 
AND (productos_x_cliente_comext.id_cliente=149 or productos_x_cliente_comext.id_cliente is null)
