-- para agregar la columna unidad_min_despacho_x_tendido
ALTER TABLE productos_inventario
  ADD COLUMN unidad_min_despacho_x_tendido numeric(8,0);