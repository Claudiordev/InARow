package com.claudiordese.kafka.model.domain;

import com.claudiordese.kafka.global.JSONSerializer;
import com.claudiordese.kafka.model.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class Message extends JSONSerializer {
    private PlayerDTO player;
    private String message;
}
