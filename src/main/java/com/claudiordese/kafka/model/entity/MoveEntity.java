package com.claudiordese.kafka.model.entity;

import com.claudiordese.kafka.model.enums.MoveEventType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name="moves")
public class MoveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name="player_id", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(name = "move_type", nullable = false)
    private MoveEventType moveEventType;
}
