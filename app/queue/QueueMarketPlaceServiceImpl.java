package queue;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import model.Producto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class QueueMarketPlaceServiceImpl implements QueueMarketPlaceService {

    private String sqsUrl = "https://sqs.us-east-1.amazonaws.com/803611297537/proveedores_grupo4";
    private String sqsRegion = "us-east-1";

    private AmazonSQSAsync sqsClient = AmazonSQSAsyncClientBuilder
            .standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsUrl, sqsRegion))
            .build();


    public CompletionStage<Integer> sendProducto(Producto producto) {

        String json = "{id:" + producto.id.toString() + ",descripcion:" + producto.descripcion + ",precio:" + producto.precio +"}";

        Future<SendMessageResult> result = sqsClient.sendMessageAsync(new SendMessageRequest(sqsUrl, json));
        return CompletableFuture.supplyAsync(() -> {
            try {
                result.get();
                return 1;
            } catch (InterruptedException|ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

    }
}
