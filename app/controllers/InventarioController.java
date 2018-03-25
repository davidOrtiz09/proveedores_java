package controllers;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import static play.mvc.Results.*;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import dao.InventarioDAO;
import service.inventario.InventarioService;

public class InventarioController {

    private final InventarioService inventarioService;

    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public InventarioController(InventarioService inventarioService, HttpExecutionContext httpExecutionContext){
        this.inventarioService = inventarioService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> mostrarInventario(Long idProveedor) {
        return inventarioService.getInventariosByProveedor(idProveedor).thenApplyAsync(inventarios -> {
            return ok(views.html.inventariosProveedor.render(inventarios));
        }, httpExecutionContext.current());
    }
}
