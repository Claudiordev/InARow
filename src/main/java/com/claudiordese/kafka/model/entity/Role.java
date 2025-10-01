package com.claudiordese.kafka.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "roles_idx_1",columnNames = {"username","role"})
})
public class Role {

    @EmbeddedId
    private RoleId id;
}
