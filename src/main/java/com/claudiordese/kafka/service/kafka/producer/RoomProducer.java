package com.claudiordese.kafka.service.kafka.producer;

import com.claudiordese.kafka.model.RoomsStatus;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RoomProducer {
    private static final Logger logger = LoggerFactory.getLogger(RoomProducer.class);

    @Value("${app.kafka.topics.rooms}")
    private String TOPIC = "rooms";
    private final KafkaTemplate<String, RoomsStatus> kafkaTemplate;

    public RoomProducer(KafkaTemplate<String, RoomsStatus> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RoomsStatus status) {
        logger.info("Sending message to topic: {} message: {}", TOPIC, status.toString());

        Message<RoomsStatus> message = MessageBuilder.withPayload(status).setHeader(KafkaHeaders.TOPIC, TOPIC).build();

        kafkaTemplate.send(message);
    }
}
