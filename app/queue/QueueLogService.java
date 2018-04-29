package queue;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;

@ImplementedBy(QueueLogServiceImpl.class)
public interface QueueLogService {
    public CompletionStage<Long> sendLogMessage(String mensaje);
}
