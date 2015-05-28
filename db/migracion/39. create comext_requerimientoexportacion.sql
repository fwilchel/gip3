-- Table: comext_requerimientoexportacion

-- DROP TABLE comext_requerimientoexportacion;

CREATE TABLE comext_requerimientoexportacion
(
  id integer NOT NULL,
  fecha timestamp(0) without time zone DEFAULT NULL::timestamp without time zone,
  codigo character varying(40) DEFAULT NULL::character varying,
  modalidadembarque character varying(15),
  fechasolicitud timestamp(0) without time zone DEFAULT NULL::timestamp without time zone,
  tipocontenedores character varying(15),
  emisionbi character varying(10),
  direccionentregabi character varying(100) DEFAULT NULL::character varying,
  zipcodebi character varying(10) DEFAULT NULL::character varying,
  contactobi character varying(100) DEFAULT NULL::character varying,
  telefonoemailbi character varying(100) DEFAULT NULL::character varying,
  servicecontract character varying(100) DEFAULT NULL::character varying,
  darazonsocial character varying(100) DEFAULT NULL::character varying,
  dacontacto character varying(100) DEFAULT NULL::character varying,
  datelefonoemail character varying(100) DEFAULT NULL::character varying,
  crazonsocial character varying(100) DEFAULT NULL::character varying,
  cnit character varying(100) DEFAULT NULL::character varying,
  cdireccion character varying(100) DEFAULT NULL::character varying,
  ctelefono character varying(100) DEFAULT NULL::character varying,
  cciudadpais character varying(100) DEFAULT NULL::character varying,
  enviaraestadireccion boolean,
  cotradireccion character varying(100) DEFAULT NULL::character varying,
  nrazonsocial character varying(100) DEFAULT NULL::character varying,
  nnit character varying(100) DEFAULT NULL::character varying,
  ndireccion character varying(100) DEFAULT NULL::character varying,
  ntelefono character varying(100) DEFAULT NULL::character varying,
  nciudadpais character varying(100) DEFAULT NULL::character varying,
  danopciones text,
  dancual character varying(100) DEFAULT NULL::character varying,
  danobservaciones boolean,
  danobscuales character varying(100) DEFAULT NULL::character varying,
  marcacionespecial boolean,
  observacioneaadicionales character varying(300) DEFAULT NULL::character varying,
  tipoprecio character varying(10),
  otropago character varying(100) DEFAULT NULL::character varying,
  puertosalida bigint,
  puertollegada bigint,
  terminoincoterm character varying(10) DEFAULT NULL::character varying,
  flete character varying(20) DEFAULT NULL::character varying,
  estado integer,
  id_cliente bigint,
  fomapago character varying(30) DEFAULT NULL::character varying,
  CONSTRAINT comext_requerimientoexportacion_pkey PRIMARY KEY (id),
  CONSTRAINT fk_c53c5d342a813255 FOREIGN KEY (id_cliente)
      REFERENCES clientes (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_c53c5d344f0f5df6 FOREIGN KEY (puertosalida)
      REFERENCES agente_aduana (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_c53c5d3475d3a22 FOREIGN KEY (puertollegada)
      REFERENCES agente_aduana (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE comext_requerimientoexportacion
  OWNER TO postgres;

-- Index: idx_c53c5d342a813255

-- DROP INDEX idx_c53c5d342a813255;

CREATE INDEX idx_c53c5d342a813255
  ON comext_requerimientoexportacion
  USING btree
  (id_cliente);

-- Index: idx_c53c5d344f0f5df6

-- DROP INDEX idx_c53c5d344f0f5df6;

CREATE INDEX idx_c53c5d344f0f5df6
  ON comext_requerimientoexportacion
  USING btree
  (puertosalida);

-- Index: idx_c53c5d3475d3a22

-- DROP INDEX idx_c53c5d3475d3a22;

CREATE INDEX idx_c53c5d3475d3a22
  ON comext_requerimientoexportacion
  USING btree
  (puertollegada);

