package com.claudiordese.kafka.model.entity;

import com.claudiordese.kafka.global.JSONSerializer;
import com.claudiordese.kafka.model.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Move extends JSONSerializer {
    private PlayerDTO player;
    private int x;
    private int y;
    private char symbol;
}
