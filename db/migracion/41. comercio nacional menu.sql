-- para habilitar una opción del menu
SELECT * FROM funcionalidades ORDER BY id DESC;
INSERT INTO funcionalidades (id, descripcion, nombre) VALUES (144, 'Comercio Nacional', 'Comercio Nacional');
INSERT INTO funcionalidades (id, descripcion, nombre, id_funcionalidad_padre, ruta) VALUES (145, 'Solicitud Pedido', 'Ingresar Solicitud Pedido Comercio Nacional', 144, 'ingresar_solicitud_pedido_cn');
SELECT * FROM permisos ORDER BY id DESC;
INSERT INTO permisos  VALUES (144,144,'T');
INSERT INTO permisos  VALUES (145,145,'T');
SELECT * FROM permisos_roles ORDER BY id_permiso DESC;
INSERT INTO permisos_roles VALUES (144,1);
INSERT INTO permisos_roles VALUES (145,1);