CREATE SEQUENCE public.log_auditoria_id_seq
   START 1;
 select setval('log_auditoria_id_seq', max(id_log)) from log_auditoria;