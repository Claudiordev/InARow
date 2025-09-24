package com.claudiordese.kafka.service.game;

import com.claudiordese.kafka.model.domain.GameRoom;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GamesRegistry {
    private final Map<UUID,GameRoom> gameRooms = new ConcurrentHashMap<>();

    public void addGameRoom(UUID uuid, GameRoom gameRoom) {
        gameRooms.putIfAbsent(uuid, gameRoom);
    }

    public GameRoom getGameRoom(UUID uuid) throws NullPointerException{
        return gameRooms.get(uuid);
    }
}
