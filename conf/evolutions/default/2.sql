# Marketplace schema inserts

# === !Ups
INSERT INTO productos (id,descripcion,precio) VALUES (1,'Lámpara', 25000);
INSERT INTO productos (id,descripcion,precio) VALUES (2,'Bombillo', 4200);
INSERT INTO productos (id,descripcion,precio) VALUES (3,'Lámpara Rústica', 60000);
INSERT INTO productos (id,descripcion,precio) VALUES (4,'Lámpara Moderna', 80000);

INSERT INTO proveedores (id,nombre,apellido) VALUES (1,'Camilo', 'Tangarife');
INSERT INTO proveedores (id,nombre,apellido) VALUES (2,'Juan', 'Florez');

INSERT into inventarios (id, id_proveedor, id_producto, cantidad) VALUES (1,1,1,12);
INSERT into inventarios (id, id_proveedor, id_producto, cantidad) VALUES (2,1,2,54);
INSERT into inventarios (id, id_proveedor, id_producto, cantidad) VALUES (3,2,3,89);
INSERT into inventarios (id, id_proveedor, id_producto, cantidad) VALUES (4,2,4,98);

# === !Downs
DELETE FROM compras_de_productos;
DELETE FROM compras;
DELETE FROM usuarios;
DELETE FROM productos;