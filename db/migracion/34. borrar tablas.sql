--
-- SOC
--
-- script para eliminar las tablas que ya no se estan usando en el aplicativo SOC
-- @author: Diego Poveda, <diego.poveda@softstudio.co>.
-- @author: John Heredia, <diego.poveda@softstudio.co>.
-- @version: 1.0
--

drop  TABLE aprobaciones
--drop TABLE bodegas_logicas
drop TABLE productosxrecetas
drop TABLE recetas
drop TABLE categorias_receta
drop TABLE cierre_inventario
drop TABLE comida_empleado
drop TABLE consignaciones
drop TABLE conteos
drop TABLE conteos2
drop TABLE costo_ventas
drop TABLE costos
drop TABLE documentos2
drop TABLE ean_micros
drop TABLE gasto_publicidad
drop TABLE estandar_conteos
drop TABLE estandar_pedidos
drop TABLE historial_cierre
drop TABLE historial_costos
drop TABLE insumos
drop TABLE nivel_inventarioxubicacion
drop TABLE nivel_inventarioxubicacion_temp
drop TABLE productosxproveedor
--pendiente
--select * from proveedores
--delete from proveedores
drop TABLE proveedorxciudad
drop TABLE remrep
--pendiete
--drop TABLE saldos
drop TABLE saldos_franquicia
drop TABLE saldoscedi
drop TABLE sp
drop TABLE temp_costos
drop TABLE temp_mov_inv
drop TABLE temp_ubicaciones
drop TABLE tipo_conteo
drop TABLE traslados
drop TABLE variables_pedido_sugerido
drop TABLE ventas
drop TABLE pga_diagrams
drop TABLE pga_forms
drop TABLE pga_graphs
drop TABLE pga_images
drop TABLE pga_layout
drop TABLE pga_queries
drop TABLE pga_reports
drop TABLE pga_scripts