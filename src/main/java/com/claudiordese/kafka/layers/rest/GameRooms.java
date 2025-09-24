package com.claudiordese.kafka.layers.rest;

import com.claudiordese.kafka.messages.producer.MessagesProducer;
import com.claudiordese.kafka.messages.producer.PlayerEventsProducer;
import com.claudiordese.kafka.model.domain.GameRoom;
import com.claudiordese.kafka.service.game.GamesRegistry;
import com.claudiordese.kafka.service.game.PlayerRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/game")
public class GameRooms {
    private PlayerEventsProducer playerEventsProducer;
    private MessagesProducer messagesProducer;
    private PlayerRegistry playerRegistry;
    private GamesRegistry gameRegistry;

    public GameRooms(PlayerEventsProducer playerEventsProducer, MessagesProducer messagesProducer,PlayerRegistry playerRegistry) {
        this.playerEventsProducer = playerEventsProducer;
        this.messagesProducer = messagesProducer;
        this.playerRegistry = playerRegistry;
        this.gameRegistry = new GamesRegistry();
    }


    @PostMapping("/addRoom")
    public ResponseEntity<String> addRom(@RequestParam String name) {
        GameRoom gameRoom = new GameRoom(name, playerRegistry);

        gameRegistry.addGameRoom(gameRoom.getUuid(), gameRoom);

        return new  ResponseEntity<>(gameRoom.toString(), HttpStatus.OK);
    }
}
