package back.vybz.comment_service.kafka.config;

import back.vybz.comment_service.kafka.event.CommentDeltaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class CommentDeltaEventKafkaConfig {

    private final CommonKafkaProducerConfig commonKafkaProducerConfig;

    @Bean
    public ProducerFactory<String, CommentDeltaEvent> commentCountProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonKafkaProducerConfig.producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, CommentDeltaEvent> commentCountKafkaTemplate() {
        return new KafkaTemplate<>(commentCountProducerFactory());
    }
}
