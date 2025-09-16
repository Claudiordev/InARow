package com.claudiordese.kafka.service.game;

import com.claudiordese.kafka.model.PlayerDTO;
import com.claudiordese.kafka.service.kafka.data.PlayerEvent;
import com.claudiordese.kafka.service.kafka.data.PlayerEventType;
import com.claudiordese.kafka.service.kafka.producer.PlayerEventsProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerRegistry {
    private Logger logger = LoggerFactory.getLogger(PlayerRegistry.class);

    private final Map<Long, PlayerDTO> players = new ConcurrentHashMap<>();
    private final PlayerEventsProducer playerEventsProducer;

    public PlayerRegistry(PlayerEventsProducer playerEventsProducer) {
        this.playerEventsProducer = playerEventsProducer;
    }

    public void add(PlayerDTO player) {
        try {
            players.putIfAbsent(player.id(), player);

            PlayerEvent playerEvent = new PlayerEvent(null, player, PlayerEventType.LOGIN);
            playerEventsProducer.sendData(playerEvent);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
