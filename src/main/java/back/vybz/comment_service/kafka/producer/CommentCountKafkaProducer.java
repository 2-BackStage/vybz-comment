package back.vybz.comment_service.kafka.producer;

import back.vybz.comment_service.kafka.event.CommentCountEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentCountKafkaProducer {

    private final KafkaTemplate<String, CommentCountEvent> commentCountKafkaTemplate;
    private static final String TOPIC_NAME = "comment-count";

    public void send(CommentCountEvent event) {
        log.info("[Kafka] sending CommentCountEvent to topic '{}': {}", TOPIC_NAME, event);

        CompletableFuture<SendResult<String, CommentCountEvent>> future =
                commentCountKafkaTemplate.send(TOPIC_NAME, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send CommentCountEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent CommentCountEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
