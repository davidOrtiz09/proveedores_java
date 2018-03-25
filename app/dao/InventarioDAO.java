package dao;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Transaction;
import model.Inventario;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
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


}
