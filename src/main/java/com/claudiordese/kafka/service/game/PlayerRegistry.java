package com.claudiordese.kafka.service.game;

import com.claudiordese.kafka.model.PlayerDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerRegistry {
    private final Map<Long, PlayerDTO> players = new ConcurrentHashMap<>();
}
