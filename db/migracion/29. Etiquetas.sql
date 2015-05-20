ALTER TABLE liquidacion_documentos
  ADD COLUMN etiquetas numeric(12,2);
  
  
ALTER TABLE liquidacion_documentos
  ADD CONSTRAINT liquidacion_documento_unico UNIQUE (consecutivo_documento);

UPDATE ITEM_COSTO_LOGISTICO set nombre_puerto_nal=NULL WHERE nombre_puerto_nal='';
UPDATE ITEM_COSTO_LOGISTICO set nombre_puertos_nal_internal=NULL WHERE nombre_puertos_nal_internal='';