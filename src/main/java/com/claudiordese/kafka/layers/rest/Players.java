package com.claudiordese.kafka.layers.rest;

import com.claudiordese.kafka.exceptions.LoggedInException;
import com.claudiordese.kafka.messages.producer.PlayerEventsProducer;
import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.model.entity.Player;
import com.claudiordese.kafka.model.enums.PlayerEventType;
import com.claudiordese.kafka.model.event.PlayerEvent;
import com.claudiordese.kafka.model.mapper.PlayerMapper;
import com.claudiordese.kafka.repository.PlayerRepository;
import com.claudiordese.kafka.service.game.PlayerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/players")
@CrossOrigin(origins = "http://localhost:3000") // frontend origin
public class Players {
    private final PlayerRegistry playerRegistry;
    private final PlayerRepository playerRepository;
    private final Logger logger = LoggerFactory.getLogger(Players.class);
    private final PlayerEventsProducer playerEventsProducer;

    public Players(PlayerRepository playerRepository, PlayerRegistry playerRegistry, PlayerEventsProducer playerEventsProducer) {
        this.playerRepository = playerRepository;
        this.playerRegistry = playerRegistry;
        this.playerEventsProducer = playerEventsProducer;
    }

    @GetMapping("/loggedIn")
    public ResponseEntity<List<PlayerDTO>> getLoggedInPlayers() {
        return new ResponseEntity<>(playerRegistry.getLoggedInPlayers(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO playerDTO, @RequestParam String newUsername) {
        if (playerRegistry.isLoggedIn(playerDTO.id())) {
            return playerRepository.findByUsername(playerDTO.username()).map(
                    player -> {
                        player.setUsername(newUsername);
                        playerRepository.save(player);

                        return new ResponseEntity<>(PlayerMapper.toPlayerDTO(player), HttpStatus.OK);
                    })
                    .orElseGet(() -> new ResponseEntity<>(playerDTO, HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(playerDTO, HttpStatus.UNAUTHORIZED);
        }
    }
}
