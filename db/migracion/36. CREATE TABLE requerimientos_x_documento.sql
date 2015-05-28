-- Table: requerimientos_x_documento

-- DROP TABLE requerimientos_x_documento;

CREATE TABLE requerimientos_x_documento
(
  documento_id bigint NOT NULL,
  requerimientoexportacion_id integer NOT NULL,
  CONSTRAINT requerimientos_x_documento_pkey PRIMARY KEY (documento_id, requerimientoexportacion_id),
  CONSTRAINT fk_39088fa945c0cf75 FOREIGN KEY (documento_id)
      REFERENCES documentos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_39088fa9b8988920 FOREIGN KEY (requerimientoexportacion_id)
      REFERENCES comext_requerimientoexportacion (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE requerimientos_x_documento
  OWNER TO postgres;

-- Index: idx_39088fa945c0cf75

-- DROP INDEX idx_39088fa945c0cf75;

CREATE INDEX idx_39088fa945c0cf75
  ON requerimientos_x_documento
  USING btree
  (documento_id);

-- Index: idx_39088fa9b8988920

-- DROP INDEX idx_39088fa9b8988920;

CREATE INDEX idx_39088fa9b8988920
  ON requerimientos_x_documento
  USING btree
  (requerimientoexportacion_id);

