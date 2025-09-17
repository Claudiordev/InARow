package com.claudiordese.kafka.messages.consumer;

import com.claudiordese.kafka.model.RoomsStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RoomConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomConsumer.class);

    @KafkaListener(topics = "rooms", groupId = "inARow")
    public void listen(RoomsStatus message) {
        LOGGER.info("Received rooms status: {}", message);
    }
}
