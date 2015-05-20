***************SI EJECUTAR*******************
ALTER TABLE public.categorias_costos_logisticos
   ADD COLUMN campo_acumula character varying(20);
***************SI EJECUTAR*******************


*******************NO EJECUTAR************************
   update categorias_costos_logisticos set campo_acumula='SEGURO' where id=6;
   update categorias_costos_logisticos set campo_acumula='FLETES' where id=5;
   update categorias_costos_logisticos set campo_acumula='FOB' where id not in(5,6);

*******************NO EJECUTAR************************




