package back.vybz.comment_service.kafka.producer;

import back.vybz.comment_service.kafka.event.CommentDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentDeleteEventKafkaProducer {

    private final KafkaTemplate<String, CommentDeleteEvent> commentDeleteEventKafkaTemplate;
    private static final String TOPIC_NAME = "comment-delete-event";

    public void send(CommentDeleteEvent event){
        log.info("[Kafka] sending CommentDeleteEvent to topic '{}': {}", TOPIC_NAME, event);

        CompletableFuture<SendResult<String, CommentDeleteEvent>> future =
                commentDeleteEventKafkaTemplate.send(TOPIC_NAME, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send CommentDeleteEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent CommentDeleteEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
