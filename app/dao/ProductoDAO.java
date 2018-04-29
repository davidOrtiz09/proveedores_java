package dao;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import model.Producto;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;


public class ProductoDAO {
    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public ProductoDAO(DatabaseExecutionContext executionContext, EbeanConfig ebeanConfig){

        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<List<Producto>> getProductos(){
        return supplyAsync(() -> {
            return ebeanServer.find(Producto.class).findList();
        }, executionContext);
    }

    public CompletionStage<Integer> addProducto(Producto producto) throws Exception {
        return supplyAsync(() -> {
            if (producto.precio > 75000D) {
                throw new IllegalStateException("Precio superado por producto: " + producto.id);
            }
             ebeanServer.save(producto);
             return 1;
        }, executionContext);
    }
}
