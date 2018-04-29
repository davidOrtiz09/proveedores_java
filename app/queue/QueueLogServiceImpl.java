package queue;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class QueueLogServiceImpl implements QueueLogService {

    //TODO: Cambiar nombre la cola
    private String sqsUrl = "https://sqs.us-east-1.amazonaws.com/803611297537/log_grupo4";
    private String sqsRegion = "us-east-1";

    private AmazonSQSAsync sqsClient = AmazonSQSAsyncClientBuilder
            .standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsUrl, sqsRegion))
            .build();


    public CompletionStage<Long> sendLogMessage(String mensaje) {
        Future<SendMessageResult> result = sqsClient.sendMessageAsync(new SendMessageRequest(sqsUrl, mensaje));
        return CompletableFuture.supplyAsync(() -> {
            try {
                result.get();
                return 1L;
            } catch (InterruptedException|ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

    }


}
