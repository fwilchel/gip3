insert into funcionalidades values (148,'Modificar Requerimiento de Exportacion','Modificar Requerimiento de Exportacion',99,'modificarRequerimientoExportacion',22)
insert into  permisos values(148,148,'T')
insert into permisos_roles values (148,1)


insert into funcionalidades values (147,'Generar Requerimiento de Exportacion','Generar Requerimiento de Exportacion',99,'generarRequerimientoExportacion',21)
insert into  permisos values(147,147,'T')
insert into permisos_roles values (147,1)

insert into funcionalidades values (146,'Reporte Requerimiento Exportacion','Reporte Requerimiento Exportacion',110,'listado_Reporte_Requerimiento_Exportacion',21)
insert into  permisos values(146,146,'T')
insert into permisos_roles values (146,1)




-- Sequence: reqxproducto_id_seq

-- DROP SEQUENCE reqxproducto_id_seq;

CREATE SEQUENCE reqxproducto_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE reqxproducto_id_seq
  OWNER TO postgres;