package com.claudiordese.kafka.service.kafka.producer;

import com.claudiordese.kafka.service.kafka.data.PlayerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PlayerEventsProducer {
    private final Logger logger = LoggerFactory.getLogger(PlayerEventsProducer.class);

    @Value("${app.kafka.topics.rooms}")
    private String TOPIC;
    private final KafkaTemplate<String, PlayerEvent> kafkaTemplate;

    public PlayerEventsProducer(KafkaTemplate<String, PlayerEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendData(PlayerEvent playerEvent) {
        logger.info("Add data to topic {}, message {}", TOPIC, playerEvent.toString());
        kafkaTemplate.send(TOPIC, "player", playerEvent);
    }
}
