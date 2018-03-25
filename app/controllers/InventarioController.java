package controllers;

import model.Inventario;
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
}
