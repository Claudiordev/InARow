package com.claudiordese.kafka.model.entity;

import com.claudiordese.kafka.model.dto.PlayerDTO;
import com.claudiordese.kafka.service.game.PlayerRegistry;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class GameRoom {
    private UUID uuid;
    private String name;
    private List<PlayerDTO> players;
    private char[][] board = new char[3][3];
    private List<Move> moves;
    private PlayerRegistry playerRegistry;
    private PlayerDTO winner = null;

    public GameRoom(String name, PlayerRegistry playerRegistry) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.players = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.playerRegistry = playerRegistry;

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                this.board[y][x] = '-';
            }
        }
    }

    public boolean move(Move move) {
        char symbol = move.getSymbol();
        int y = move.getY();
        int x = move.getX();

        if (winner != null) {
            return false;
        }

        if (!playerRegistry.isLoggedIn(move.getPlayer().id())) {
            return false;
        }

        if (board[y][x] != '-') {
            return false;
        }

        if (x < 0 || x > 2 || y < 0 || y > 2) {
            return false;
        }

        board[y][x] = symbol;
        moves.add(move);
        return true;
    }

    public void detectWinner() {
        for (int y = 0; y < this.board.length; y++) {
            for (int x = 0; x < this.board[y].length; x++) {
            }
        }
    }
}
