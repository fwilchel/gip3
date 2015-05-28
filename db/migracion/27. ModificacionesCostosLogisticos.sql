CREATE TABLE public.liquidacion_costo_logistico
(
   id bigint, 
   cliente_id bigint, 
   pais_id character varying(2), 
   incoterm_transporte_id bigint, 
   puerto_nal character varying(100), 
   puerto_internal character varying(100), 
   tipo_contenedor_1 integer, 
   tipo_contenedor_2 integer, 
   cantidad_1 numeric(12,2),
   cantidad_2 numeric(12,2),
   CONSTRAINT liquidacion_costos_logisticos_pkey PRIMARY KEY (id), 
   CONSTRAINT liquidacion_costos_logisticos_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   CONSTRAINT liquidacion_costos_logisticos_pais FOREIGN KEY (pais_id) REFERENCES paises (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   CONSTRAINT liquidacion_costos_logisticos_incoterm FOREIGN KEY (incoterm_transporte_id) REFERENCES termino_incoterm_x_medio_transporte (id) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
)
;



CREATE TABLE public.liquidacion_documentos
(
   id bigint, 
   liquidacion_id bigint, 
   consecutivo_documento character varying(40), 
   CONSTRAINT liquidacion_documentos_pkey PRIMARY KEY (id), 
   CONSTRAINT liquidacion_liquidacion FOREIGN KEY (liquidacion_id) REFERENCES liquidacion_costo_logistico (id) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
)
;

update item_costo_logistico set base_fob='T' where id_categoria in (select id from categorias_costos_logisticos where campo_acumula='FOB');
ALTER TABLE categorias_costos_logisticos
  ADD COLUMN orden integer;

ALTER TABLE item_costo_logistico
   ALTER COLUMN valor TYPE numeric(12,3);
ALTER TABLE item_costo_logistico
   ALTER COLUMN valor_minimo TYPE numeric(12,3);

CREATE TABLE public.liquidacion_item
(
   id bigint, 
   liquidacion_id bigint, 
   tipo integer, 
   categoria_id bigint, 
   item_id bigint, 
   cantidad numeric(12,3), 
   valor numeric(12,3), 
   valor_minimo numeric(12,3), 
   base_fob boolean, 
   campo_acumula character varying(20), 
   consecutivo_documento character varying(40), 
   orden integer, 
   CONSTRAINT liquidacion_item_pkey PRIMARY KEY (id), 
   CONSTRAINT liquidacion_categoria FOREIGN KEY (categoria_id) REFERENCES categorias_costos_logisticos (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   CONSTRAINT liquidacion_item FOREIGN KEY (item_id) REFERENCES item_costo_logistico (id) ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITH (
  OIDS = FALSE
)
;



ALTER TABLE liquidacion_item
  ADD CONSTRAINT liquidacion_documento UNIQUE (consecutivo_documento);

ALTER TABLE liquidacion_item
  ADD CONSTRAINT liquidacion_item_costo_logistico FOREIGN KEY (liquidacion_id) REFERENCES liquidacion_costo_logistico (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE SEQUENCE public.liquidacion_costos_logisticos_id_seq
   INCREMENT 1
   START 1
   MINVALUE 1;


CREATE SEQUENCE public.liquidacion_documento_id_seq
   INCREMENT 1
   START 1
   MINVALUE 1;

CREATE SEQUENCE public.liquidacion_item_id_seq
   INCREMENT 1
   START 1
   MINVALUE 1;


ALTER TABLE liquidacion_item
  DROP CONSTRAINT liquidacion_documento;
ALTER TABLE liquidacion_item
  ADD CONSTRAINT liquidacion_documento_item UNIQUE (consecutivo_documento, item_id);


ALTER TABLE liquidacion_documentos
  ADD CONSTRAINT liquidacion_documento UNIQUE (consecutivo_documento);

