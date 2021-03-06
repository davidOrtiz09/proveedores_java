package model;

import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto extends BaseModel {
    @Constraints.Required
    public String descripcion;

    @Constraints.Required
    public Double precio;

    public Producto(Long id, String descripcion, Double precio){
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public static final Finder<Long, Producto> find = new Finder<>(Producto.class);
}