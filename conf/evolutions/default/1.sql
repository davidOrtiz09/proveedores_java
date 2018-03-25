# Proveedores schema creation

# === !Ups
CREATE TABLE "proveedores" (
  "id" SERIAL NOT NULL PRIMARY KEY,
  "nombre" VARCHAR NOT NULL,
  "apellido" VARCHAR NOT NULL
);

CREATE TABLE "productos" (
  "id" SERIAL NOT NULL PRIMARY KEY,
  "descripcion" VARCHAR NOT NULL,
  "precio" NUMERIC(21,8) NOT NULL
);

CREATE TABLE "inventarios" (
  "id" SERIAL NOT NULL PRIMARY KEY,
  "id_proveedor" INTEGER NOT NULL REFERENCES "proveedores",
  "id_producto" INTEGER NOT NULL REFERENCES "productos",
  "cantidad" INTEGER NOT NULL
);

# === !Downs
DROP TABLE "inventarios";
DROP TABLE "proveedores";
DROP TABLE "productos";