package back.vybz.comment_service.kafka.producer;

import back.vybz.comment_service.kafka.event.CommentDeltaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentDeltaEventKafkaProducer {

    private final KafkaTemplate<String, CommentDeltaEvent> commentDeltaEventKafkaTemplate;
    private static final String TOPIC_NAME = "comment-delta";

    public void send(CommentDeltaEvent event) {
        log.info("[Kafka] sending CommentDeltaEvent to topic '{}': {}", TOPIC_NAME, event);

        CompletableFuture<SendResult<String, CommentDeltaEvent>> future =
                commentDeltaEventKafkaTemplate.send(TOPIC_NAME, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send CommentDeltaEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent CommentDeltaEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
