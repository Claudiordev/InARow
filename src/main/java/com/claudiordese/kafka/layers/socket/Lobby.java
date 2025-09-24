package com.claudiordese.kafka.layers.socket;

import com.claudiordese.kafka.messages.producer.MessagesProducer;
import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.model.domain.GameRoom;
import com.claudiordese.kafka.model.domain.Message;
import com.claudiordese.kafka.model.domain.Move;
import com.claudiordese.kafka.service.game.PlayerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class Lobby {
    private final Logger logger = LoggerFactory.getLogger(Lobby.class);
    private final PlayerRegistry playerRegistry;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessagesProducer messagesProducer;
    private GameRoom gameRoom = null;

    public Lobby(PlayerRegistry playerRegistry, SimpMessagingTemplate simpMessagingTemplate, MessagesProducer messagesProducer) {
        this.playerRegistry = playerRegistry;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messagesProducer = messagesProducer;
    }

    @MessageMapping("/chat")
    public void lobby(Message message) {
        try {
            if (!playerRegistry.isLoggedIn(message.getPlayer().id())) {
                logger.warn("Player {} tried to send a message without being logged in", message.getPlayer().username());

                return;
            }

            logger.info("Player {} send the message: {}", message.getPlayer().username(), message.getMessage());

            if (message.getMessage().contains("GAME")) gameHandle(message);

            simpMessagingTemplate.convertAndSend("/topic/lobby", message);
            messagesProducer.sendDataAsync(message);
        } catch (MessagingException messagingException) {
            logger.error(messagingException.getMessage(), messagingException);
        }
    }

    public void gameHandle(Message message) {
        String messageTxt = message.getMessage();
        Message adminMessage = new Message(new PlayerDTO(UUID.randomUUID(),"Admin",0,0),"");

        messageTxt = messageTxt.replace("GAME","");
        if (messageTxt.contains("JOIN")) {
            messageTxt = messageTxt.replace("JOIN", "");
            gameRoom = new GameRoom("Example", playerRegistry);

            adminMessage.setMessage("You joined the game on room Example");
            simpMessagingTemplate.convertAndSend("/topic/lobby", adminMessage);
        }

        if (messageTxt.contains("PRINT")) {
            adminMessage.setMessage(gameRoom.boardToString());
            simpMessagingTemplate.convertAndSend("/topic/lobby", adminMessage);
        }

        if (messageTxt.contains("PLAY")) {
            messageTxt = messageTxt.replace("PLAY", "");
            messageTxt = messageTxt.trim();
            int y = Character.getNumericValue(messageTxt.charAt(0));
            int x = Character.getNumericValue(messageTxt.charAt(1));
            char symbol = messageTxt.charAt(2);

            if (gameRoom.move(new Move(message.getPlayer(),x,y,symbol))) {
                adminMessage.setMessage("Played");
            } else {
                adminMessage.setMessage("Not played");
            }

            simpMessagingTemplate.convertAndSend("/topic/lobby", adminMessage);
        }
    }
}
