package com.claudiordese.kafka.service.game;

import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.model.entity.Message;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Data
public class LobbyRegistry {

    private final Set<Message> messageList = new HashSet<>();

    public void addMessage(Message message) {
        messageList.add(message);
    }
}
