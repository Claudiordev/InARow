package com.claudiordese.kafka.layers.rest;

import com.claudiordese.kafka.exceptions.NonValidLoginRequest;
import com.claudiordese.kafka.messages.producer.PlayerEventsProducer;
import com.claudiordese.kafka.model.dto.LoginRequestDTO;
import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.model.entity.Player;
import com.claudiordese.kafka.model.enums.PlayerEventType;
import com.claudiordese.kafka.model.event.PlayerEvent;
import com.claudiordese.kafka.model.mapper.PlayerMapper;
import com.claudiordese.kafka.repository.PlayerRepository;
import com.claudiordese.kafka.service.game.PlayerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/auth")
public class Authentication {
    private final Logger logger = LoggerFactory.getLogger(Authentication.class);
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlayerRegistry playerRegistry;
    private final PlayerEventsProducer playerEventsProducer;


    public Authentication(PlayerRepository playerRepository, PasswordEncoder passwordEncoder, PlayerRegistry playerRegistry, PlayerEventsProducer playerEventsProducer) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.playerRegistry = playerRegistry;
        this.playerEventsProducer = playerEventsProducer;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws RuntimeException {
        Player player = playerRepository.findByUsername(loginRequestDTO.username()).orElseGet(() -> {
            Player newPlayer = new Player();
            newPlayer.setUsername(loginRequestDTO.username());
            newPlayer.setPassword(passwordEncoder.encode(loginRequestDTO.password()));

            return playerRepository.save(newPlayer);
        });

        PlayerDTO playerDTO = PlayerMapper.toPlayerDTO(player);

        if (!passwordEncoder.matches(loginRequestDTO.password(), player.getPassword())) {
            throw new NonValidLoginRequest();
        }

        playerRegistry.add(playerDTO);
        playerEventsProducer.sendData(new PlayerEvent(null,playerDTO, PlayerEventType.LOGIN));

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

}
