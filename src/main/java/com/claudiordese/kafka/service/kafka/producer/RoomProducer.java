package com.claudiordese.kafka.service.kafka.producer;

import com.claudiordese.kafka.model.Room;
import com.claudiordese.kafka.model.RoomsStatus;
import com.claudiordese.kafka.service.kafka.data.RoomEvent;
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
    private String TOPIC = "room-events";
    private final KafkaTemplate<String, RoomEvent> kafkaTemplate;

    public RoomProducer(KafkaTemplate<String, RoomEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RoomEvent event) {
        logger.info("Adding event to: {} message: {}", TOPIC, event);

        Message<RoomEvent> message = MessageBuilder.withPayload(event).setHeader(KafkaHeaders.TOPIC, TOPIC).build();

        kafkaTemplate.send(message);
    }
}
