package queue;

import com.google.inject.ImplementedBy;
import model.Producto;

import java.util.concurrent.CompletionStage;

@ImplementedBy(QueueMarketPlaceServiceImpl.class)
public interface QueueMarketPlaceService {

    public CompletionStage<Integer> sendProducto(Producto producto);
}
