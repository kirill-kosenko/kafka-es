package teg.kafka;

import teg.domain.ItemEventPublisher;
import teg.domain.event.ItemEvent;
import teg.kafka.serialization.AvroItemEvent;
import teg.kafka.serialization.ItemEventConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;


@Component
public class TransactionalItemEventPublisher implements ItemEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(TransactionalItemEventPublisher.class);

    private final String topicName;

    private final KafkaTemplate<String, AvroItemEvent> kafkaTemplate;

    private final ItemEventConverter converter;

    public TransactionalItemEventPublisher(@Value("${gtd.topic}") final String topicName,
                                           final KafkaTemplate<String, AvroItemEvent> kafkaTemplate,
                                           final ItemEventConverter converter) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
        this.converter = converter;
    }

    @Override
    public void log(final ItemEvent itemEvent) {
        log.info("Attempting to log {} to topic {}.", itemEvent, topicName);
        kafkaTemplate.executeInTransaction(operations -> {
            final String key = itemEvent.getItemId();
            operations
                .send(topicName, key, converter.from(itemEvent))
                .addCallback(this::onSuccess, this::onFailure);
            return true;
        });
    }

    private void onSuccess(final SendResult<String, AvroItemEvent> result) {
        log.info("AvroItemEvent '{}' has been written to topic-partition {}-{} with ingestion timestamp {}.",
                result.getProducerRecord().key(),
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().timestamp());
    }

    private void onFailure(final Throwable t) {
        log.warn("Unable to write AvroItemEvent to topic {}.", topicName, t);
    }
}
