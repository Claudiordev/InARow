package com.claudiordese.kafka.layers.rest;

import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.model.Room;
import com.claudiordese.kafka.model.entity.Player;
import com.claudiordese.kafka.model.mapper.PlayerMapper;
import com.claudiordese.kafka.repository.PlayerRepository;
import com.claudiordese.kafka.service.game.PlayerRegistry;
import com.claudiordese.kafka.service.game.RoomStateService;
import com.claudiordese.kafka.model.event.RoomEvent;
import com.claudiordese.kafka.model.enums.RoomEventType;
import com.claudiordese.kafka.messages.producer.RoomProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/game")
public class Rooms {

    private final RoomProducer roomProducer;
    private final RoomStateService roomStateService;
    private final PlayerRegistry playerRegistry;
    private final PlayerRepository playerRepository;
    private final Logger logger = LoggerFactory.getLogger(Rooms.class);

    public Rooms(RoomProducer roomProducer, RoomStateService roomStateService, PlayerRegistry playerRegistry, PlayerRepository playerRepository) {
        this.roomProducer = roomProducer;
        this.roomStateService = roomStateService;
        this.playerRegistry = playerRegistry;
        this.playerRepository = playerRepository;
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
