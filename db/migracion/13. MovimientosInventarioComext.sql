-- Table: movimientos_inventario_comext 

-- DROP TABLE movimientos_inventario_comext; 

CREATE TABLE movimientos_inventario_comext 
( 
  consecutivo_documento character varying(40), 
  id_tipo_movimiento bigint NOT NULL, 
  id_producto bigint NOT NULL, 
  cantidad numeric(12,3), 
  fecha timestamp without time zone, 
  saldo numeric(12,2), 
  id bigserial NOT NULL, 
  CONSTRAINT movimientosinventario_comext_id_tipo_movimiento_fkey FOREIGN KEY (id_tipo_movimiento) 
      REFERENCES tipo_movimiento (id) MATCH SIMPLE 
      ON UPDATE NO ACTION ON DELETE NO ACTION 
) 
WITH ( 
  OIDS=FALSE 
); 
ALTER TABLE movimientos_inventario_comext 
  OWNER TO postgres; 

-- Index: fki_movimientosinventario_comext_id_tipo_movimiento_fkey 

-- DROP INDEX fki_movimientosinventario_comext_id_tipo_movimiento_fkey; 

CREATE INDEX fki_movimientosinventario_comext_id_tipo_movimiento_fkey 
  ON movimientos_inventario_comext 
  USING btree 
  (id_tipo_movimiento); 

