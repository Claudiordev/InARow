package com.claudiordese.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Room {
    private String roomId;
    private int players;
    private final int maxPlayers;
    private int spectators;
    private int maxSpectators;
    private String status;
    private List<PlayerDTO> playersList = new ArrayList<>();

    public boolean add(PlayerDTO player) {
        if (players < maxPlayers) {
            playersList.add(player);
            players++;
            return true;
        }

        return false;
    }

    public boolean remove(PlayerDTO player) {
        if (playersList != null && playersList.remove(player)) {
            players--;
            return true;
        }

        return false;
    }
}
