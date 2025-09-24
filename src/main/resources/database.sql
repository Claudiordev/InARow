-- Enable the pgcrypto extension for UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE players (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL UNIQUE,
    game_points INT NOT NULL DEFAULT 0,
    score INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

/*CREATE TABLE game_rooms (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    winner_id UUID NULL,

    CONSTRAINT fk_winner FOREIGN KEY (winner_id) REFERENCES players(id)
);

CREATE TABLE game_room_players (
    game_room_id UUID NOT NULL,
    player_id UUID NOT NULL,

    PRIMARY KEY (game_room_id, player_id),
    CONSTRAINT fk_game_room FOREIGN KEY (game_room_id) REFERENCES game_rooms(id) ON DELETE CASCADE,
    CONSTRAINT fk_player FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE
);*/