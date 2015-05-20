ALTER TABLE facts_currency_conversion
   ADD COLUMN type numeric NOT NULL DEFAULT 1;

   COMMENT ON COLUMN facts_currency_conversion.type
  IS '1: Conversiones moneda
2: TRM DIAN';   

insert into facts_currency_conversion (id_source_currency, id_destination_currency, source_destination_exchange_rate, destination_source_exchange_rate, pg_catalog, type) values ('COP','USD', 1/2397.35, 2397.35, '2015-02-02',2);

