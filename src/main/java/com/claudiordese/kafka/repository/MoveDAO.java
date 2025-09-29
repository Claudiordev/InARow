package com.claudiordese.kafka.repository;

import com.claudiordese.kafka.model.domain.Move;
import com.claudiordese.kafka.model.entity.MoveEntity;
import com.claudiordese.kafka.model.enums.MoveEventType;

import java.util.List;
import java.util.UUID;

public interface MoveDAO {

    void save(Move move);

    List<MoveEntity> findAll();

    List<MoveEntity> findAllByPlayer(UUID uuid);

    void updateMoveType(UUID moveId, MoveEventType moveEventType);

    void deleteMove(UUID moveID);
}
