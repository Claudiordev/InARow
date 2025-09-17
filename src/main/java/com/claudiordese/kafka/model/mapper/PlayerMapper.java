package com.claudiordese.kafka.model.mapper;

import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.model.entity.Player;

public class PlayerMapper {

    public static PlayerDTO toPlayerDTO(Player player) {
        if (player == null) {  return null; }
        return new PlayerDTO(
                player.getId(),
                player.getUsername(),
                player.getGamePoints(),
                player.getScore());
    }
}
