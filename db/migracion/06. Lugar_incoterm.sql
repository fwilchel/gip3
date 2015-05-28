
-- Table: lugar_incoterm

-- DROP TABLE lugar_incoterm;

CREATE TABLE lugar_incoterm
(
  id bigint NOT NULL,
  descripcion character varying(100),
  activo boolean,
  CONSTRAINT lugar_incoterm_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE lugar_incoterm
  OWNER TO postgres;

CREATE SEQUENCE lugar_incoterm_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE lugar_incoterm_id_seq
  OWNER TO postgres;