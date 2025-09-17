package com.claudiordese.kafka.layers.rest;

import com.claudiordese.kafka.exceptions.LoggedInException;
import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.model.entity.Player;
import com.claudiordese.kafka.model.mapper.PlayerMapper;
import com.claudiordese.kafka.repository.PlayerRepository;
import com.claudiordese.kafka.service.game.PlayerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/players")
public class Players {
    private final PlayerRegistry playerRegistry;
    private final PlayerRepository playerRepository;
    private final Logger logger = LoggerFactory.getLogger(Players.class);

    public Players(PlayerRepository playerRepository, PlayerRegistry playerRegistry) {
        this.playerRepository = playerRepository;
        this.playerRegistry = playerRegistry;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        try {
            Player player = playerRepository.findByUsername(username).orElseGet(() -> {
                Player newPlayer = new Player();
                newPlayer.setUsername(username);
                newPlayer.setGamePoints(0);
                newPlayer.setScore(0);
                newPlayer.setCreatedAt(Instant.now());
                newPlayer.setUpdatedAt(Instant.now());

                return playerRepository.save(newPlayer);
            });

            PlayerDTO playerDTO = PlayerMapper.toPlayerDTO(player);

            if (playerRegistry.isLoggedIn(playerDTO.id())) {
                throw new LoggedInException(username);
            }

            playerRegistry.add(playerDTO);

            return new ResponseEntity<>(playerDTO.toString(), HttpStatus.OK);
        } catch (LoggedInException loggedInException) {
            logger.warn(loggedInException.getMessage());

            return new ResponseEntity<>(loggedInException.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error logging in for {} , message: {}", username, e.getMessage());

            return new ResponseEntity<>("Error logging in", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
