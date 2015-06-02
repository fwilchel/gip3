-- para habilitar una opción del menu
SELECT * FROM funcionalidades ORDER BY id DESC;
UPDATE funcionalidades SET descripcion = 'Comercio Exterior Reportes', nombre = 'Comercio Exterior Reportes' WHERE id = 110;
UPDATE funcionalidades SET descripcion = 'Administración del sistema', nombre = 'Seguridad' WHERE id = 1;