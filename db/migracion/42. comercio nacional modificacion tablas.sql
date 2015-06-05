-- para agregar la columna usuario
ALTER TABLE punto_venta
  ADD COLUMN usuario character varying(40);
ALTER TABLE punto_venta
  ADD CONSTRAINT punto_venta_usuario_fk FOREIGN KEY (usuario) REFERENCES usuarios (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE punto_venta
  ADD CONSTRAINT punto_venta_usuario_unique UNIQUE (usuario);

﻿-- para agregar la columna ubicacion 
ALTER TABLE clientes
  ADD COLUMN ubicacion bigint;
ALTER TABLE clientes
  ADD CONSTRAINT cliente_ubicacion_fk FOREIGN KEY (ubicacion) REFERENCES ubicaciones (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

﻿-- para agregar la columna unidad_min_despacho_x_tendido
ALTER TABLE productos_inventario
  ADD COLUMN unidad_min_despacho_x_tendido numeric(8,0);

