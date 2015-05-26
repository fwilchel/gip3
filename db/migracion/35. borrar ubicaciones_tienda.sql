update ubicaciones set es_franquicia=true where id not in (1,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,23,25,26,31,33,34,35,36,37,38,39,40,43,44,45,46,47,48,49,50,51,52,53,54,56,57,59,62,63,64,65,66,68,69,70,71,72,74,75,76,77,79,80,81,82,83,84,85,88,89,90,91,92,93,95,98,100,101,103,104,105,106,107,108,114,115,116,117,118,119,120,127,128,129,131,132,134,136,137,138,139,140,141,142,143,144,
145,146,149,150,151,152,153,154,155,156,158,159,160,164,165,166,167,168,173,174,175,177,178,179,180,181,182,183,184,187,188,189,190,
191,195,197,198,199,211,212,213,214,215,216,217,218,220,224,225,230,231,232,233,234,235,236,237,238,243,244,245,246,247,248,249,250,253,
255,256,257,258,260,262,263,1094,1096,1098,1099,1109,1110,1111,1112,1113)

select * from tipo_documento
select * from documentos where id_tipo_documento in (6,1,2,4,8,9,10,12,16,14,15,17,18,19)

select count (id) from movimientos_inventario where id_documento in (select id from documentos where id_tipo_documento in (6,1,2,4,8,9,10,12,16,14,15,17,18,19))

delete from movimientos_inventario where id_documento in (select id from documentos where id_tipo_documento in (6,1,2,4,8,9,10,12,16,14,15,17,18,19))
Query returned successfully: 332769 rows affected, 162987 ms execution time.
162987 ms

delete from movimientos_inventario where id_documento in (select id from documentos where fecha_generacion<='2011-09-30')
50 MIN
Query returned successfully: 5105380 rows affected, 2963418 ms execution time.
2963418 MS


delete from movimientos_inventario where id_documento in(select id from  documentos where id_ubicacion_origen in (select id from ubicaciones where es_franquicia=false))

delete from movimientos_inventario where id_documento in(select id from  documentos where id_ubicacion_destino in (select id from ubicaciones where es_franquicia=false))
Query returned successfully: 1335852 rows affected, 322894 ms execution time.
322894 ms



select * from documentos where id_ubicacion_origen in (select id from ubicaciones where es_franquicia=false)
--delete from movimientos_inventario where id_ubicacion_origen in (select id from ubicaciones where es_franquicia=false)
Query returned successfully: 96456 rows affected, 71237 ms execution time.
71237 ms


delete from productosxdocumentos where id_documento in(select id from  documentos where id_ubicacion_origen in (select id from ubicaciones where es_franquicia=false))
Query returned successfully: 47138 rows affected, 125205 ms execution time.
125205 ms

delete from productosxdocumentos where id_documento in(select id from  documentos where id_ubicacion_destino in (select id from ubicaciones where es_franquicia=false))
Query returned successfully: 624039 rows affected, 482224 ms execution time.
482224 ms

delete from documentos where id_ubicacion_origen in (select id from ubicaciones where es_franquicia=false)
delete from documentos where id_ubicacion_destino in (select id from ubicaciones where es_franquicia=false)
Query returned successfully: 11753 rows affected, 371011 ms execution time.
371011


delete from documentos where id_tipo_documento in (6,1,2,4,8,9,10,12,16,14,15,17,18,19)

select * from documentos where id_tipo_documento=7



delete from productosxdocumentos where id_documento in (select id from documentos where fecha_generacion<='2011-09-30')
Query returned successfully: 3305455 rows affected, 2004101 ms execution time.
2004101 ms
select * from movimientos_inventario where id_documento=349534
select * from movimientos_inventario limit 10


 delete from documentos where fecha_generacion<='2011-09-30'
Query returned successfully: 232990 rows affected, 53635 ms execution time.
53625 ms


delete from productosxdocumentos where id_documento in (select id from documentos where id_tipo_documento in (6,1,2,4,8,9,10,12,16,14,15,17,18,19))
Query returned successfully: 1261689 rows affected, 170463 ms execution time.
170463 ms

delete from documentos where id_tipo_documento in (6,1,2,4,8,9,10,12,16,14,15,17,18,19)
Query returned successfully: 44448 rows affected, 639505 ms execution time.
639505 ms

select * from documentos where consecutivo_documento ='FD1-1'
"2011-10-04 15:34:05"
select * from productosxdocumentos where id_documento=350669
select * from movimientos_inventario where id_documento=350669

select * from documentos where consecutivo_documento ='FX1-1'
select * from productosxdocumentos where id_documento=408382
select * from movimientos_inventario where id_documento=408382

select count(id) from documentos where fecha_generacion<='2011-09-30'
219088
select count(id) from documentos

select * from documentos where fecha_generacion between '2011-09-30 00:00:00' and '2011-09-30 23:59:59'
219088


select count (id) from movimientos_inventario
8422930


select * from documentos where id_ubicacion_destino in (select id from ubicaciones where es_franquicia=false)

delete from saldos
update ubicaciones set  id_bodega_abastecedora=99 where id_bodega_abastecedora=114
delete from ubicaciones where es_franquicia=false

select * from ubicaciones order by nombre