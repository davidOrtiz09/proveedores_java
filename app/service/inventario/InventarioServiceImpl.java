package service.inventario;

import dao.InventarioDAO;
import model.Inventario;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class InventarioServiceImpl implements InventarioService {

    @Inject
    private InventarioDAO inventarioDAO;

    public CompletionStage<List<Inventario>> getInventariosByProveedor(Long id){
        //TODO Implementar query
        return null;
    }

    public CompletionStage<Long> cambiarInventario(Long idInventario, int cambio){
        //TODO Implementar query
        return null;
    }
}