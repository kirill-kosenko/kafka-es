package teg.kafka;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import teg.domain.commands.ItemCommand;

import java.io.IOException;

@Component
public class TransactionalCommandPublisher {
    private static final Logger log = LoggerFactory.getLogger(TransactionalItemEventPublisher.class);

    private final String topicName = "command.item";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public TransactionalCommandPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void log(final ItemCommand itemCommand) {
        log.info("Attempting to log {} to topic {}.", itemCommand, topicName);
        kafkaTemplate.executeInTransaction(operations -> {
            try {
                operations
                        .send(topicName, topicName, objectMapper.writeValueAsString(itemCommand))
                        .addCallback(this::onSuccess, this::onFailure);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        });
    }

    private void onSuccess(final SendResult<String, String> result) {
        log.info("Command '{}' has been written to topic-partition {}-{} with ingestion timestamp {}.",
                result.getProducerRecord().key(),
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().timestamp());
    }

    private void onFailure(final Throwable t) {
        log.warn("Unable to write Command to topic {}.", topicName, t);
    }
}
