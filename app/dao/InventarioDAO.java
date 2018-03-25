package dao;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
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


}
