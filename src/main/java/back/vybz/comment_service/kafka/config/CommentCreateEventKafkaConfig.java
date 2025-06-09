package back.vybz.comment_service.kafka.config;

import back.vybz.comment_service.kafka.event.CommentCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class CommentCreateEventKafkaConfig {

    private final CommonKafkaProducerConfig commonKafkaProducerConfig;

    @Bean
    public ProducerFactory<String, CommentCreateEvent> commentCreateEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonKafkaProducerConfig.producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, CommentCreateEvent> commentCreateEventKafkaTemplate() {
        return new KafkaTemplate<>(commentCreateEventProducerFactory());
    }
}
