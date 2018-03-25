package model;

import javax.persistence.*;

import io.ebean.*;
import play.data.validation.*;

@Entity
@Table(name = "proveedores")
public class Proveedor extends BaseModel {

    @Constraints.Required
    public String nombre;

    @Constraints.Required
    public String apellido;

    public static final Finder<Long, Proveedor> find = new Finder<>(Proveedor.class);

}