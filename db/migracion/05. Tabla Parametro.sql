CREATE TABLE public."parametro"
(
   id integer, 
   nombre character varying(50), 
   valor character varying(100), 
   CONSTRAINT id_pkey PRIMARY KEY (id)
) 
WITH (
  OIDS = FALSE
)
;


insert into parametro values (1,'Contraseña defecto','procafecol2010*');
insert into parametro values (2,'DEBUG','SI');
insert into parametro values (3,'Ambiente','Calidad');
insert into parametro values (4,'Versión','3.0');