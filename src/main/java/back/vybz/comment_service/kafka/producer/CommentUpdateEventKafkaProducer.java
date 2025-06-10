package back.vybz.comment_service.kafka.producer;

import back.vybz.comment_service.kafka.event.CommentUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentUpdateEventKafkaProducer {

    private final KafkaTemplate<String, CommentUpdateEvent> commentUpdateEventKafkaTemplate;
    private static final String TOPIC_NAME = "comment-update-event";

    public void send(CommentUpdateEvent event){
        log.info("[Kafka] sending CommentUpdateEvent to topic '{}': {}", TOPIC_NAME, event);

        CompletableFuture<SendResult<String, CommentUpdateEvent>> future =
                commentUpdateEventKafkaTemplate.send(TOPIC_NAME, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send CommentUpdateEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent CommentUpdateEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
