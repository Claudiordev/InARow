package com.claudiordese.kafka.model.event;

import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.model.Room;
import com.claudiordese.kafka.model.enums.PlayerEventType;
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
