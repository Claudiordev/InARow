package com.claudiordese.kafka.service.game;

import com.claudiordese.kafka.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
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

    /**
     * Generate a new UUID for a Room
     * @return String UUID
     */
    public String generateUUID() {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (rooms.containsKey(id));
        return id;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            return "{}";
        }
    }
}
