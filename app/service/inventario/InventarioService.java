package service.inventario;

import com.google.inject.ImplementedBy;
import model.Inventario;
import model.Producto;

import java.util.List;
import java.util.concurrent.CompletionStage;

@ImplementedBy(InventarioServiceImpl.class)
public interface InventarioService {

    public CompletionStage<List<Inventario>> getInventariosByProveedor(Long id);

    public CompletionStage<Long> cambiarInventario(Long idInventario, int cambio);

    public CompletionStage<Integer> crearProducto(Producto producto);

}

