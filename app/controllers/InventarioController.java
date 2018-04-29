package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import dao.InventarioDAO;
import model.Inventario;
import model.Producto;
import model.ProductoBodyParser;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import service.inventario.InventarioService;

public class InventarioController extends Controller {

    private final InventarioService inventarioService;

    private final HttpExecutionContext httpExecutionContext;


    @Inject
    public InventarioController(InventarioService inventarioService, HttpExecutionContext httpExecutionContext){
        this.inventarioService = inventarioService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> mostrarInventario(Long idProveedor) {
        return inventarioService.getInventariosByProveedor(idProveedor).thenApplyAsync(inventarios -> {
            Seq<Inventario> inventarioSeq = JavaConverters.asScalaIteratorConverter(inventarios.iterator()).asScala().toSeq();
            return ok(views.html.inventariosProveedor.render(inventarioSeq));
        }, httpExecutionContext.current());
    }


    @BodyParser.Of(ProductoBodyParser.class)
    public CompletionStage<Result> agregarProducto() {
        Http.RequestBody body = request().body();
        Producto producto = body.as(Producto.class);
        return inventarioService.crearProducto(producto).thenApplyAsync(response -> {
            return ok("Producto agregado correctamente");
        }, httpExecutionContext.current());
    }
}
