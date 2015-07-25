ALTER TABLE liquidacion_costo_logistico
   ADD COLUMN valor_fob numeric(19,2);

ALTER TABLE liquidacion_costo_logistico
   ADD COLUMN valor_fletes numeric(19,2);   

ALTER TABLE liquidacion_costo_logistico
   ADD COLUMN valor_seguros numeric(19,2);   

ALTER TABLE liquidacion_costo_logistico
   ADD COLUMN valor_total numeric(19,2);   


insert into funcionalidades values (149, 'Reporte Costos Logísticos', 'Reporte Costos Logísticos', 110, 'reporte_costos_logisticos', 2);
insert into permisos values (149, 149, 'T');
insert into permisos_roles values (149, 1);