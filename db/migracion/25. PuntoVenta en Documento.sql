select id, id_punto_venta from documentos where id_punto_venta=0;

update documentos set id_punto_venta=null where id_punto_venta=0;

ALTER TABLE documentos
ADD CONSTRAINT documentos_punto_venta_fkey FOREIGN KEY (id_punto_venta) REFERENCES punto_venta (id) ON UPDATE NO ACTION ON DELETE NO ACTION;