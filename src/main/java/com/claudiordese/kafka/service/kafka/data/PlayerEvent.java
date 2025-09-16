package com.claudiordese.kafka.service.kafka.data;

import com.claudiordese.kafka.model.PlayerDTO;
import com.claudiordese.kafka.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerEvent {
    private Room room;
    private PlayerDTO playerDTO;
    private PlayerEventType eventType;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            return "{}";
        }
    }
}
