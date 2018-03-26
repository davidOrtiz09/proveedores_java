package dao;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Transaction;
import model.Inventario;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class InventarioDAO {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public InventarioDAO(DatabaseExecutionContext executionContext, EbeanConfig ebeanConfig){
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<List<Inventario>> getInventarioByProveedor(Long id) {
        return supplyAsync(() -> {
            List<Inventario> inventariosByProveedor = new ArrayList<>();
            List<Inventario> inventarios = ebeanServer.find(Inventario.class).findList();
            for(Inventario inventario : inventarios){
                if(Objects.equals(inventario.proveedor.id, id)){
                    inventariosByProveedor.add(inventario);
                }
            }
            return inventariosByProveedor;
                }, executionContext);
    }

    public CompletionStage<Long> updateInventario(Long id, Inventario nuevoInventario) {

        Transaction txn = ebeanServer.beginTransaction();

        return supplyAsync(() -> {
                    try {
                        Inventario inventarioActual = ebeanServer.find(Inventario.class).setId(id).findUnique();
                        if (inventarioActual != null) {
                            inventarioActual.cantidad = nuevoInventario.cantidad;
                            inventarioActual.proveedor = nuevoInventario.proveedor;

                            inventarioActual.update();
                            txn.commit();
                        }
                    } finally {
                        txn.end();
                    }
                    return id;
                }
                , executionContext);

    }

    public CompletionStage<Long> updateProducto(Long idInventario, Long idProducto) {

        Transaction txn = ebeanServer.beginTransaction();

        return supplyAsync(() -> {
                    try {
                        Inventario inventarioActual = ebeanServer.find(Inventario.class).setId(idInventario).findUnique();
                        if (inventarioActual != null) {
                            if(inventarioActual.producto.id == idProducto) {
                                inventarioActual.cantidad = inventarioActual.cantidad -1;
                            }
                            inventarioActual.update();
                            txn.commit();
                        }

                    } finally {
                        txn.end();
                    }
                    return idInventario;
                }
                , executionContext);

    }

}
