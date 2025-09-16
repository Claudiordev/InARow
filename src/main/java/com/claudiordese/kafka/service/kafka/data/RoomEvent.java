package com.claudiordese.kafka.service.kafka.data;

import com.claudiordese.kafka.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class RoomEvent {
    private Room room;
    private RoomEventType type;

    public RoomEvent(Room room, RoomEventType type) {
        this.room = room;
        this.type = type;
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
