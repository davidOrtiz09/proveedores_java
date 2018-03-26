package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import dao.InventarioDAO;
import model.Inventario;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import service.inventario.InventarioService;
import service.inventario.SQSConsumerActor;

public class InventarioController extends Controller {

    private final InventarioService inventarioService;

    private final HttpExecutionContext httpExecutionContext;

    final ActorRef helloActor;

    private InventarioDAO inventarioDAO;


    @Inject
    public InventarioController(ActorSystem system, InventarioService inventarioService, HttpExecutionContext httpExecutionContext, InventarioDAO inventarioDAO){
        this.inventarioService = inventarioService;
        this.httpExecutionContext = httpExecutionContext;
        this.inventarioDAO = inventarioDAO;
        helloActor = system.actorOf(SQSConsumerActor.props(inventarioDAO));
    }

    public CompletionStage<Result> mostrarInventario(Long idProveedor) {
        return inventarioService.getInventariosByProveedor(idProveedor).thenApplyAsync(inventarios -> {
            Seq<Inventario> inventarioSeq = JavaConverters.asScalaIteratorConverter(inventarios.iterator()).asScala().toSeq();
            return ok(views.html.inventariosProveedor.render(inventarioSeq));
        }, httpExecutionContext.current());
    }
}
