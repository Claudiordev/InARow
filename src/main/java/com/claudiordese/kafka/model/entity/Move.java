package com.claudiordese.kafka.model.entity;

import com.claudiordese.kafka.model.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Move {
    private PlayerDTO player;
    private int x;
    private int y;
    private char symbol;
}
