package com.claudiordese.kafka.model.entity;

import com.claudiordese.kafka.model.dto.PlayerDTO;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
public class Message {
    private PlayerDTO player;
    private String message;
}
