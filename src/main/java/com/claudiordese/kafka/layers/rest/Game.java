package com.claudiordese.kafka.layers.rest;

import com.claudiordese.kafka.model.entity.Message;
import com.claudiordese.kafka.service.game.PlayerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class Game {
    private final Logger logger = LoggerFactory.getLogger(Game.class);
    private final PlayerRegistry playerRegistry;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public Game(PlayerRegistry playerRegistry, SimpMessagingTemplate simpMessagingTemplate) {
        this.playerRegistry = playerRegistry;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat")
    public void lobby(Message message) {
        if (!playerRegistry.isLoggedIn(message.getPlayer().id())) {
            logger.warn("Player {} tried to send a message without being logged in", message.getPlayer().username());

            return;
        }

        logger.info("Player {} send the message: {}", message.getPlayer().username(), message.getMessage());

        simpMessagingTemplate.convertAndSend("/topic/lobby", message);
    }
}
