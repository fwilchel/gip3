
CREATE TABLE medio_transporte(
  id bigint,
  descripcion character varying(100)[],
  activo boolean,
  CONSTRAINT medio_transporte_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE medio_transporte
  OWNER TO postgres;

ALTER TABLE medio_transporte 
ALTER COLUMN descripcion TYPE character varying(100);  

CREATE SEQUENCE medio_transporte_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

ALTER TABLE medio_transporte_id_seq
  OWNER TO postgres;

CREATE TABLE termino_incoterm_x_medio_transporte
(
  id_termino_incoterm bigint NOT NULL,
  id_medio_transporte bigint NOT NULL,
  CONSTRAINT termino_incoterm_x_medio_transporte_pkey PRIMARY KEY (id_termino_incoterm, id_medio_transporte),
  CONSTRAINT fk_termino_incoterm_x_medio_transporte_incoterm FOREIGN KEY (id_termino_incoterm)
      REFERENCES termino_incoterm (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_termino_incoterm_x_medio_transporte_medio FOREIGN KEY (id_medio_transporte)
      REFERENCES medio_transporte (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE termino_incoterm_x_medio_transporte
  OWNER TO postgres; 

ALTER TABLE termino_incoterm_x_medio_transporte DROP CONSTRAINT termino_incoterm_x_medio_transporte_pkey;

ALTER TABLE termino_incoterm_x_medio_transporte ADD COLUMN id bigint;
ALTER TABLE termino_incoterm_x_medio_transporte ADD COLUMN activo boolean;

ALTER TABLE termino_incoterm_x_medio_transporte ADD PRIMARY KEY (id);

ALTER TABLE termino_incoterm_x_medio_transporte ADD CONSTRAINT dist_termino_incoterm_x_medio_transporte_key UNIQUE (id_termino_incoterm, id_medio_transporte);

CREATE SEQUENCE termino_incoterm_x_medio_transporte_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


insert into funcionalidades (id,descripcion,nombre,id_funcionalidad_padre,ruta)
values(140,'Incoterm vs Medio de Transporte', 'Incoterm vs Medio de Transporte',62,'incoterm_medio_transporte');

insert into permisos(id,id_funcionalidad, tipo_acceso) values (140,140,'T');
insert into permisos_roles (id_permiso,id_rol) values (140,1);

INSERT INTO medio_transporte(
            id, descripcion, activo)
    VALUES (1, 'Maritimo', true);

INSERT INTO medio_transporte(
            id, descripcion, activo)
    VALUES (2, 'Terrestre', true);

  