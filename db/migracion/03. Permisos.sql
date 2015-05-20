--Permisos

--116,1,'T'
--117,2,'T'
--118,3,'T'
--119,4,'T'
--120,26,'T'
--121,60,'T'
--122,61,'T'
--123,62,'T'
--124,65,'T'
--125,82,'T'
--126,99,'T'
--127,110,'T'


--permisos_roles

--116,1
--117,1
--118,1
--119,1
--120,1
--121,1
--122,1
--123,1
--124,1
--125,1
--126,1
--127,1


--Funcionalidades

--34,"BORRAR","BORRAR"
--50,"Ruta de acceso de email","emailConfig"
--2,"Compras","Compras"
--3,"Costos","Costos"
--4,"Inventario","Inventario"
--26,"Abastecimiento","Abastecimiento"
--60,"Devoluciones","Devoluciones"
--61,"Reportes","Reportes"
--62,"Maestros","Maestros"
--65,"Financiero","Financiero"
--82,"Ventas y facturaci�n","Ventas y Facturaci�n"
--99,"Comercio exterior","Comercio exterior"
--110,"Comercio Exterior Reportes","Reportes Comercio Exterior"
--1,"Administraci�n del sistema","Seguridad"


INSERT INTO PERMISOS VALUES (128,1,'T');
INSERT INTO PERMISOS VALUES (129,2,'T');
INSERT INTO PERMISOS VALUES (130,3,'T');
INSERT INTO PERMISOS VALUES (131,4,'T');

INSERT INTO PERMISOS VALUES (120,26,'T');
INSERT INTO PERMISOS VALUES (121,60,'T');
INSERT INTO PERMISOS VALUES (122,61,'T');
INSERT INTO PERMISOS VALUES (123,62,'T');
INSERT INTO PERMISOS VALUES (124,65,'T');
INSERT INTO PERMISOS VALUES (125,82,'T');
INSERT INTO PERMISOS VALUES (126,99,'T');
INSERT INTO PERMISOS VALUES (127,110,'T');

INSERT INTO permisos_roles VALUES (128,1);
INSERT INTO permisos_roles VALUES (129,1);
INSERT INTO permisos_roles VALUES (130,1);
INSERT INTO permisos_roles VALUES (131,1);

INSERT INTO permisos_roles VALUES (120,1);
INSERT INTO permisos_roles VALUES (121,1);
INSERT INTO permisos_roles VALUES (122,1);
INSERT INTO permisos_roles VALUES (123,1);
INSERT INTO permisos_roles VALUES (124,1);
INSERT INTO permisos_roles VALUES (125,1);
INSERT INTO permisos_roles VALUES (126,1);
INSERT INTO permisos_roles VALUES (127,1);

UPDATE FUNCIONALIDADES SET Descripcion='BORRAR', Nombre='BORRAR' WHERE ID=34;
UPDATE FUNCIONALIDADES SET Descripcion='Ruta de acceso de email', Nombre='emailConfig' WHERE ID=50;
UPDATE FUNCIONALIDADES SET Descripcion='Compras', Nombre='Compras' WHERE ID=2;
UPDATE FUNCIONALIDADES SET Descripcion='Costos', Nombre='Costos' WHERE ID=3;
UPDATE FUNCIONALIDADES SET Descripcion='Inventario', Nombre='Inventario' WHERE ID=4;
UPDATE FUNCIONALIDADES SET Descripcion='Abastecimiento', Nombre='Abastecimiento' WHERE ID=26;
UPDATE FUNCIONALIDADES SET Descripcion='Devoluciones', Nombre='Devoluciones' WHERE ID=60;
UPDATE FUNCIONALIDADES SET Descripcion='Reportes', Nombre='Reportes' WHERE ID=61;
UPDATE FUNCIONALIDADES SET Descripcion='Maestros', Nombre='Maestros' WHERE ID=62;
UPDATE FUNCIONALIDADES SET Descripcion='Financiero', Nombre='Financiero' WHERE ID=65;
UPDATE FUNCIONALIDADES SET Descripcion='Ventas y facturación', Nombre='Ventas y Facturación' WHERE ID=82;
UPDATE FUNCIONALIDADES SET Descripcion='Comercio exterior', Nombre='Comercio exterior' WHERE ID=99;



