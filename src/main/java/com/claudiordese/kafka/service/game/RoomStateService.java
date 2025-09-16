package com.claudiordese.kafka.service.game;

import com.claudiordese.kafka.model.Room;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomStateService {
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public void addRoom(Room room) {
        rooms.put(room.getRoomId(), room);
    }

    public Map<String, Room> getAllRooms() {
        return rooms;
    }

    public void playerJoined(String roomId, String playerId) {
        rooms.get(roomId);
    }
}
