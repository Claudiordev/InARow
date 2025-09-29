package com.claudiordese.kafka.service.game;

import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.model.entity.MoveEntity;
import com.claudiordese.kafka.model.enums.MoveEventType;
import com.claudiordese.kafka.model.event.PlayerEvent;
import com.claudiordese.kafka.model.enums.PlayerEventType;
import com.claudiordese.kafka.messages.producer.PlayerEventsProducer;
import com.claudiordese.kafka.repository.MoveDAO;
import com.claudiordese.kafka.repository.MoveDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerRegistry {
    private Logger logger = LoggerFactory.getLogger(PlayerRegistry.class);

    private final MoveDAO moveDAO;
    private final Map<UUID, PlayerDTO> players = new ConcurrentHashMap<>();
    private final PlayerEventsProducer playerEventsProducer;

    public PlayerRegistry(PlayerEventsProducer playerEventsProducer, MoveDAOImpl moveDAO) {
        this.playerEventsProducer = playerEventsProducer;
        this.moveDAO =  moveDAO;
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

    public List<PlayerDTO> getLoggedInPlayers() {
        return new ArrayList<>(players.values());
    }

    public boolean isLoggedIn(UUID uuid) {
        return players.containsKey(uuid);
    }

    public List<MoveEntity> getMovesByPlayer(UUID uuid) {
        return moveDAO.findAllByPlayer(uuid);
    }
}
