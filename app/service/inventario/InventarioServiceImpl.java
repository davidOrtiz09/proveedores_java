package service.inventario;

import dao.InventarioDAO;
import dao.ProductoDAO;
import model.Inventario;
import model.Producto;
import queue.QueueLogService;
import queue.QueueMarketPlaceService;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class InventarioServiceImpl implements InventarioService {


    private InventarioDAO inventarioDAO;
    private ProductoDAO productoDAO;
    private QueueLogService queueLogService;
    private QueueMarketPlaceService queueMarketPlaceService;

    @Inject
    public InventarioServiceImpl(InventarioDAO inventarioDAO, ProductoDAO productoDAO, QueueLogService queueLogService, QueueMarketPlaceService queueMarketPlaceService) {
        this.inventarioDAO = inventarioDAO;
        this.productoDAO = productoDAO;
        this.queueLogService = queueLogService;
        this.queueMarketPlaceService = queueMarketPlaceService;
    }

    public CompletionStage<List<Inventario>> getInventariosByProveedor(Long id){
        return inventarioDAO.getInventarioByProveedor(id);
    }

    public CompletionStage<Long> cambiarInventario(Long idInventario, int cambio){
        //TODO Implementar query
        return null;
    }

    public CompletionStage<Integer> crearProducto(Producto producto) {

        try {
            return productoDAO.addProducto(producto)
                    .thenComposeAsync(result -> queueMarketPlaceService.sendProducto(producto))
                    .exceptionally(error -> {
                        try {
                            return queueLogService.sendLogMessage(error.getMessage())
                                    .thenApplyAsync(result -> new Producto(producto.id, producto.descripcion, 2000D))
                                    .thenComposeAsync( newProducto -> {
                                        try {
                                            return productoDAO.addProducto(newProducto);
                                        } catch (Exception e) {
                                            return completedFuture(6);
                                        }
                                    })
                                    .toCompletableFuture().get();
                        } catch (InterruptedException | ExecutionException e) {
                            return 6;
                        }
                    });
        } catch (Exception e) {
            return completedFuture(6);
        }
    }
}