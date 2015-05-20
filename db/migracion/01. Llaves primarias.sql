ALTER TABLE historial_contrasena
  ADD CONSTRAINT historial_contrasena_pk UNIQUE (id_usuario, contrasena);

ALTER TABLE conteos2
  ADD CONSTRAINT conteo2_pk PRIMARY KEY (id);  
  
ALTER TABLE comext_categoria_producto
  ADD CONSTRAINT comext_categoria_producto_pk PRIMARY KEY (id);

ALTER TABLE comext_directorio
  ADD CONSTRAINT comext_directorio_pk PRIMARY KEY (id);

ALTER TABLE comext_formato_novedades
  ADD CONSTRAINT comext_formato_novedades_pk PRIMARY KEY (id);

ALTER TABLE comext_roles
  ADD CONSTRAINT comext_roles_pk PRIMARY KEY (id_rol);

ALTER TABLE comext_slideshow
  ADD CONSTRAINT comext_slideshow_pk PRIMARY KEY (id);

ALTER TABLE comext_user_role
  ADD CONSTRAINT comext_user_role_pk PRIMARY KEY (user_id, role_id);

ALTER TABLE comext_usuarios
  ADD CONSTRAINT comext_usuarios_pk PRIMARY KEY (id);

ALTER TABLE documento_x_negociacion
  ADD CONSTRAINT documento_x_negociacion_pk PRIMARY KEY (id_documento, id_termino_incoterm);

ALTER TABLE comext_formato_novedades
  ADD CONSTRAINT comext_formato_novedades_pk PRIMARY KEY (id);


select * from comext_formato_novedades order by id, user_id
select max(id) from comext_formato_novedades

select * from comext_formato_novedades where id=77 and user_id=798
update comext_formato_novedades set id=82 where id=76 and user_id=798

update comext_formato_novedades set id=132 where id=79 and user_id=796
update comext_formato_novedades set id=133 where id=79 and user_id=804
update comext_formato_novedades set id=134 where id=76 and user_id=798
update comext_formato_novedades set id=135 where id=77 and user_id=798
update comext_formato_novedades set id=136 where id=80 and user_id=804


  