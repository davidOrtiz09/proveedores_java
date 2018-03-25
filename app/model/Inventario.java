package model;

import io.ebean.Finder;
import play.data.validation.Constraints;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="inventarios")
public class Inventario extends BaseModel {
    @ManyToOne
    @JoinColumn(name="id_proveedor")
    public Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name="id_producto")
    public Producto producto;

    @Constraints.Required
    public int cantidad;

    public Inventario(Long id, Proveedor proveedor, Producto producto, int cantidad) {
        this.id = id;
        this.proveedor = proveedor;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public void cambiarCantidad(int cambio){
        this.cantidad += cambio;
    }

    public static final Finder<Long, Inventario> find = new Finder<>(Inventario.class);
}
