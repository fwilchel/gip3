ALTER TABLE item_costo_logistico RENAME valor_usd TO valor;
ALTER TABLE item_costo_logistico RENAME valor_minimo_usd TO valor_minimo;
ALTER TABLE item_costo_logistico
   ADD COLUMN id_moneda character varying(4);
   
ALTER TABLE item_costo_logistico
  ADD CONSTRAINT item_id_moneda_fkey FOREIGN KEY (id_moneda) REFERENCES monedas (id)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_item_id_moneda_fkey
  ON item_costo_logistico(id_moneda);  
 
ALTER TABLE item_costo_logistico
   ADD COLUMN aplica_fob_in boolean;
ALTER TABLE rango_costo_logistico RENAME valor_usd  TO valor;
ALTER TABLE rango_costo_logistico
   ADD COLUMN id_moneda character varying(4);

ALTER TABLE rango_costo_logistico
  ADD CONSTRAINT rango_moneda_fkey FOREIGN KEY (id_moneda) REFERENCES monedas (id)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_rango_moneda_fkey
  ON rango_costo_logistico(id_moneda); 