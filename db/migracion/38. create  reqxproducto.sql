-- Table: reqxproducto

-- DROP TABLE reqxproducto;

CREATE TABLE reqxproducto
(
  id integer NOT NULL,
  documento bigint,
  producto bigint,
  tienemarcacion boolean NOT NULL,
  mproducto boolean NOT NULL,
  mcajamaster boolean NOT NULL,
  mpallet boolean NOT NULL,
  requerimiento integer,
  observaciones character varying(100) DEFAULT NULL::character varying,
  CONSTRAINT reqxproducto_pkey PRIMARY KEY (id),
  CONSTRAINT fk_e3090cc754e92412 FOREIGN KEY (requerimiento)
      REFERENCES comext_requerimientoexportacion (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_e3090cc7a7bb0615 FOREIGN KEY (producto)
      REFERENCES productos_inventario_comext (id_producto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_e3090cc7b6b12ec7 FOREIGN KEY (documento)
      REFERENCES documentos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE reqxproducto
  OWNER TO postgres;

-- Index: idx_e3090cc754e92412

-- DROP INDEX idx_e3090cc754e92412;

CREATE INDEX idx_e3090cc754e92412
  ON reqxproducto
  USING btree
  (requerimiento);

-- Index: idx_e3090cc7a7bb0615

-- DROP INDEX idx_e3090cc7a7bb0615;

CREATE INDEX idx_e3090cc7a7bb0615
  ON reqxproducto
  USING btree
  (producto);

-- Index: idx_e3090cc7b6b12ec7

-- DROP INDEX idx_e3090cc7b6b12ec7;

CREATE INDEX idx_e3090cc7b6b12ec7
  ON reqxproducto
  USING btree
  (documento);

