package com.claudiordese.kafka.service.rest;

import com.claudiordese.kafka.model.PlayerDTO;
import com.claudiordese.kafka.model.Room;
import com.claudiordese.kafka.model.RoomsStatus;
import com.claudiordese.kafka.service.game.PlayerRegistry;
import com.claudiordese.kafka.service.game.RoomStateService;
import com.claudiordese.kafka.service.kafka.data.RoomEvent;
import com.claudiordese.kafka.service.kafka.data.RoomEventType;
import com.claudiordese.kafka.service.kafka.producer.RoomProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/kafka")
public class Rooms {

    private final RoomProducer roomProducer;
    private final RoomStateService roomStateService;
    private final PlayerRegistry playerRegistry;
    private final Logger logger = LoggerFactory.getLogger(Rooms.class);

    public Rooms(RoomProducer roomProducer, RoomStateService roomStateService, PlayerRegistry playerRegistry) {
        this.roomProducer = roomProducer;
        this.roomStateService = roomStateService;
        this.playerRegistry = playerRegistry;
    }

    @PostMapping("/login/{username}")
    public ResponseEntity<PlayerDTO> login(@PathVariable String username) {
        PlayerDTO playerDTO = new PlayerDTO(new Random().nextLong(), username, 0, 0);
        playerRegistry.add(playerDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRoom() {
        try {
            Room room = new Room(roomStateService.generateUUID(),5);
            roomStateService.addRoom(room);

            roomProducer.sendMessage(new RoomEvent(room, RoomEventType.CREATE));

            return new ResponseEntity<>(room.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
