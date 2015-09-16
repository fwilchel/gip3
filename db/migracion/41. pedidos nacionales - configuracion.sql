-- para habilitar una opción del menu
SELECT * FROM funcionalidades ORDER BY id DESC;
INSERT INTO funcionalidades (id, descripcion, nombre) VALUES (144, 'Pedidos Nacionales', 'Pedidos Nacionales');
INSERT INTO funcionalidades (id, descripcion, nombre, id_funcionalidad_padre, ruta) VALUES (145, 'Ingresar Solicitud', 'Ingresar Solicitud', 144, 'ingresar_solicitud_pedido_cn');
SELECT * FROM permisos ORDER BY id DESC;
INSERT INTO permisos  VALUES (144,144,'T');
INSERT INTO permisos  VALUES (145,145,'T');
SELECT * FROM permisos_roles ORDER BY id_permiso DESC;
INSERT INTO permisos_roles VALUES (144,1); -- role administrador 
INSERT INTO permisos_roles VALUES (145,1); -- role administrador
INSERT INTO roles VALUES (40, 'Pedidos Nacionales');
INSERT INTO permisos_roles VALUES (144,40); -- role administrador 
INSERT INTO permisos_roles VALUES (145,40); -- role administrador
-- insertar usuario para pruebas.
-- asignar el usuario a un punto de venta
-- en la consulta deben aparecer los productos por cliente y punto de venta vigentes 