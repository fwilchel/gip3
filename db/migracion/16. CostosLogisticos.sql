CREATE TABLE public.categorias_costos_logisticos
(
   id bigint, 
   nombre character varying(100)
) 
WITH (
  OIDS = FALSE
)
;

CREATE SEQUENCE public.categorias_costos_id_seq
   INCREMENT 1
   START 1
   MINVALUE 1;

   
CREATE TABLE public.item_costo_logistico
(
   id bigint, 
   nombre character varying(100), 
   descripcion character varying(200), 
   id_categoria bigint, 
   tipo integer, 
   nombre_puerto_nal character varying(100), 
   nombre_puertos_nal_internal character varying(100), 
   tipo_contenedor integer, 
   valor_usd numeric(12,2), 
   valor_minimo_usd numeric(12,2), 
   aplica_fob boolean, 
   aplica_cfr boolean, 
   aplica_cif boolean, 
   aplica_fca boolean, 
   aplica_cip boolean, 
   aplica_dap boolean, 
   id_pais_destino character varying(2)
) 
WITH (
  OIDS = FALSE
)
;


CREATE SEQUENCE public.items_costos_id_seq
   INCREMENT 1
   START 1
   MINVALUE 1;
   
   
ALTER TABLE categorias_costos_logisticos
  ADD CONSTRAINT categorias_costos_logisticos_pkey PRIMARY KEY (id);
   
   
ALTER TABLE item_costo_logistico
  ADD CONSTRAINT item_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES categorias_costos_logisticos (id)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_item_id_categoria_fkey
  ON item_costo_logistico(id_categoria);   
  
  
ALTER TABLE item_costo_logistico
  ADD CONSTRAINT item_costo_logistico_pkey PRIMARY KEY (id);


ALTER TABLE item_costo_logistico
  ADD CONSTRAINT item_id_pais_fkey FOREIGN KEY (id_pais_destino) REFERENCES paises (id)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_item_id_pais_fkey
  ON item_costo_logistico(id_pais_destino);

CREATE SEQUENCE public.rangos_costos_logisticos_id_seq
   INCREMENT 1
   START 1
   MINVALUE 1;
   
 CREATE TABLE public.rango_costo_logistico
(
   id bigint, 
   desde numeric(12,2), 
   hasta numeric(12,2), 
   valor_usd numeric(12,2), 
   id_unidad bigint, 
   id_item_costo_logistico bigint, 
   CONSTRAINT rango_costo_logistico_pkey PRIMARY KEY (id), 
   CONSTRAINT rango_item_costo_logistico_fkey FOREIGN KEY (id_item_costo_logistico) REFERENCES item_costo_logistico (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   CONSTRAINT rango_pais_fkey FOREIGN KEY (id_unidad) REFERENCES unidades (id) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
)
;