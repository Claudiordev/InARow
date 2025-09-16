package com.claudiordese.kafka.service.rest;

import com.claudiordese.kafka.model.Room;
import com.claudiordese.kafka.model.RoomsStatus;
import com.claudiordese.kafka.service.game.RoomStateService;
import com.claudiordese.kafka.service.kafka.producer.RoomProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/kafka")
public class Rooms {

    private final RoomProducer roomProducer;
    private final RoomStateService roomStateService;
    private final Logger logger = LoggerFactory.getLogger(Rooms.class);

    public Rooms(RoomProducer roomProducer, RoomStateService roomStateService) {
        this.roomProducer = roomProducer;
        this.roomStateService = roomStateService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRoom() {
        roomStateService.addRoom(new Room(String.valueOf(Random.)));
    }

    @GetMapping("/status")
    public ResponseEntity<String> getRoomsStatus() {
        try {
            return ResponseEntity.ok(new ObjectMapper().writeValueAsString(roomStateService.getAllRooms()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.ok("{}");
        }
    }
}
