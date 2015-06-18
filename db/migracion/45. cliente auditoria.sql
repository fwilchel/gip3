ALTER TABLE clientes
  ADD COLUMN id_usuario character varying(40);
ALTER TABLE clientes
  ADD COLUMN id_funcionalidad bigint;


CREATE OR  REPLACE FUNCTION fn_modified_func() RETURNS TRIGGER AS $body$
	DECLARE
		v_diferente RECORD;
		d_date timestamp:=now();
		v_viejo text;
	BEGIN
	IF NEW.id_usuario is not null and NEW.id_funcionalidad is not null THEN -- campos sgregado solo en clientes
		-- insercion
		IF  TG_OP='INSERT' THEN 
			FOR v_diferente IN select * from json_each_text((select row_to_json(NEW))) LOOP
				IF (v_diferente.value is not null) THEN
					INSERT INTO log_auditoria(id_log, id_usuario, id_funcionalidad, tabla, accion, id_reg_tabla, campo, valor_anterior, valor_nuevo, fecha)
					VALUES (NEXTVAL('auditoria_id_seq'),NEW.id_usuario,NEW.id_funcionalidad,TG_TABLE_NAME,'CRE',NEW.id,v_diferente.key,null,v_diferente.value,d_date);
				END IF;
			END LOOP;	
		ELSIF  TG_OP='UPDATE' THEN 
			FOR v_diferente IN select * from json_each_text((select row_to_json(NEW))) except select * from json_each_text((select row_to_json(OLD))) LOOP
				 EXECUTE 'SELECT ($1).' || v_diferente.key  || '::text' INTO v_viejo USING OLD; 
				INSERT INTO log_auditoria(id_log, id_usuario, id_funcionalidad, tabla, accion, id_reg_tabla, campo, valor_anterior, valor_nuevo, fecha)
				VALUES (NEXTVAL('auditoria_id_seq'),NEW.id_usuario,NEW.id_funcionalidad,TG_TABLE_NAME,'MOD',NEW.id,v_diferente.key,v_viejo,v_diferente.value,d_date);
				--raise notice 'key,value:%,%',v_diferente.key,v_diferente.value;
			END LOOP;
		END IF;
	ELSE 
		raise notice 'id_usuario o id_funcionalidad nulos';
	END IF;
	RETURN NULL;
	END;
$body$
LANGUAGE plpgsql; 

CREATE TRIGGER tg_if_modified_trg_clientes
 AFTER INSERT OR UPDATE OR DELETE ON clientes
 FOR EACH ROW EXECUTE PROCEDURE fn_modified_func();