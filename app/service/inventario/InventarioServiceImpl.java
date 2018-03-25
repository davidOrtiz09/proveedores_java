package service.inventario;

import dao.InventarioDAO;
import model.Inventario;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class InventarioServiceImpl implements InventarioService {


    private InventarioDAO inventarioDAO;

    @Inject
    public InventarioServiceImpl(InventarioDAO inventarioDAO) {
        this.inventarioDAO = inventarioDAO;
    }

    public CompletionStage<List<Inventario>> getInventariosByProveedor(Long id){
        return inventarioDAO.getInventarioByProveedor(id);
    }

    public CompletionStage<Long> cambiarInventario(Long idInventario, int cambio){
        //TODO Implementar query
        return null;
    }
}