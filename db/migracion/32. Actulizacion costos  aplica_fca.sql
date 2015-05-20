--Los siguientes script quitan para FCA Aereo y Terrestre cualquier configuracion relacionada con Transporte Internacional y Seguro Internacional,
--asumiendo que estos dos tienen en la tabla categorias_costos_logisticos los IDs 5 y 6 respectivamente

--Por favor verificar que s� tengan los IDs 5 y 6, sino corregir los scripts

update item_costo_logistico set aplica_fca='F' where id in (select id from item_costo_logistico where aplica_fca='T' and id_categoria in (5,6));
update item_costo_logistico set aplica_fcat='F' where id in (select id from item_costo_logistico where aplica_fcat='T' and id_categoria in (5,6));


select * from categorias_costos_logisticos 


select * from item_costo_logistico   where aplica_fca='F' and id_categoria in (5,6)