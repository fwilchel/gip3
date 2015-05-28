CREATE SEQUENCE public.agente_aduana_id_seq
   START 1;
 select setval('agente_aduana_id_seq', max(id)) from agente_aduana;
 

ALTER TABLE agente_aduana_id_seq
  OWNER TO postgres;