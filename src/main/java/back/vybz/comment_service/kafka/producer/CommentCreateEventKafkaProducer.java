package back.vybz.comment_service.kafka.producer;

import back.vybz.comment_service.kafka.event.CommentCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentCreateEventKafkaProducer {

    private final KafkaTemplate<String, CommentCreateEvent> commentCreateEventKafkaTemplate;
    private static final String TOPIC_NAME = "comment-create-event";

    public void send(CommentCreateEvent event){
        log.info("[Kafka] sending CommentCreateEvent to topic '{}': {}", TOPIC_NAME, event);

        CompletableFuture<SendResult<String, CommentCreateEvent>> future =
                commentCreateEventKafkaTemplate.send(TOPIC_NAME, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send CommentCreateEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent CommentCreateEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
