package com.snakeladder.service;

import com.snakeladder.model.*;
import com.snakeladder.builder.BoardConfiguration;
import com.snakeladder.builder.BoardConfigurationBuilder;
import com.snakeladder.factory.BoardGeneratorFactory;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private static final int CONSECUTIVE_SIX_LIMIT = 3;
    private static final String[] DEFAULT_SYMBOLS = {"🔵", "🔴", "🟢", "🟡", "🟣", "🟠"};
    
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private int currentPlayerIndex;
    private boolean gameEnded;
    private Player winner;
    
    public Game(int boardSize, GameLevel level, List<String> playerNames) {
        BoardConfiguration config = new BoardConfigurationBuilder()
            .withSize(boardSize)
            .withLevel(level)
            .withGeneratorType(BoardGeneratorFactory.getDefaultGeneratorType())
            .build();
        
        this.board = new Board(config.getBoardSize(), config.getGameLevel(), config.getStrategy());
        this.players = createPlayers(playerNames);
        this.dice = new Dice();
        this.currentPlayerIndex = 0;
        this.gameEnded = false;
    }
    
    public Game(BoardConfiguration config, List<String> playerNames) {
        this.board = new Board(config.getBoardSize(), config.getGameLevel(), config.getStrategy());
        this.players = createPlayers(playerNames);
        this.dice = new Dice();
        this.currentPlayerIndex = 0;
        this.gameEnded = false;
    }
    
    private List<Player> createPlayers(List<String> playerNames) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            String symbol = i < DEFAULT_SYMBOLS.length ? DEFAULT_SYMBOLS[i] : "P" + (i + 1);
            playerList.add(new Player(playerNames.get(i), symbol));
        }
        return playerList;
    }
    
    public GameResult playTurn() {
        if (gameEnded) {
            return new GameResult(GameResultType.GAME_ENDED, winner, 0, "Game has already ended");
        }
        
        Player currentPlayer = getCurrentPlayer();
        if (!currentPlayer.isActive()) {
            moveToNextPlayer();
            return playTurn();
        }
        
        int diceRoll = dice.roll();
        String moveDescription = processPlayerMove(currentPlayer, diceRoll);
        
        if (currentPlayer.hasWon(board.getTotalCells())) {
            gameEnded = true;
            winner = currentPlayer;
            return new GameResult(GameResultType.PLAYER_WON, currentPlayer, diceRoll, moveDescription);
        }
        
        if (Dice.isSix(diceRoll)) {
            currentPlayer.incrementConsecutiveSixes();
            if (currentPlayer.getConsecutiveSixes() >= CONSECUTIVE_SIX_LIMIT) {
                currentPlayer.resetConsecutiveSixes();
                moveToNextPlayer();
                return new GameResult(GameResultType.TURN_REVOKED, currentPlayer, diceRoll, 
                    moveDescription + " - Turn revoked due to three consecutive sixes!");
            }
            return new GameResult(GameResultType.EXTRA_TURN, currentPlayer, diceRoll, 
                moveDescription + " - Extra turn for rolling a six!");
        } else {
            currentPlayer.resetConsecutiveSixes();
            moveToNextPlayer();
            return new GameResult(GameResultType.TURN_COMPLETED, currentPlayer, diceRoll, moveDescription);
        }
    }
    
    private String processPlayerMove(Player player, int diceRoll) {
        int oldPosition = player.getCurrentPosition();
        int newPosition = oldPosition + diceRoll;
        
        if (newPosition > board.getTotalCells()) {
            return String.format("%s rolled %d but can't move beyond the board (position %d)", 
                player.getName(), diceRoll, oldPosition);
        }
        
        Player targetPlayer = getPlayerAtPosition(newPosition);
        String killMessage = "";
        if (targetPlayer != null && !targetPlayer.equals(player)) {
            targetPlayer.setCurrentPosition(0); // Send back to start
            killMessage = String.format(" and killed %s (sent back to start)", targetPlayer.getName());
        }
        
        player.setCurrentPosition(newPosition);
        
        int transformedPosition = board.transformPosition(newPosition);
        String transformMessage = "";
        if (transformedPosition != newPosition) {
            player.setCurrentPosition(transformedPosition);
            BoardEntity entity = board.getEntityAt(newPosition);
            if (entity != null) {
                transformMessage = String.format(" -> %s from %d to %d", 
                    entity.getType().toLowerCase(), newPosition, transformedPosition);
            }
        }
        
        return String.format("%s rolled %d, moved from %d to %d%s%s", 
            player.getName(), diceRoll, oldPosition, player.getCurrentPosition(), killMessage, transformMessage);
    }
    
    private Player getPlayerAtPosition(int position) {
        return players.stream()
            .filter(Player::isActive)
            .filter(p -> p.getCurrentPosition() == position)
            .findFirst()
            .orElse(null);
    }
    
    private void moveToNextPlayer() {
        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } while (!getCurrentPlayer().isActive() && !gameEnded);
    }
    
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
    
    public List<Player> getActivePlayers() {
        return players.stream()
            .filter(Player::isActive)
            .collect(Collectors.toList());
    }
    
    public Board getBoard() {
        return board;
    }
    
    public boolean isGameEnded() {
        return gameEnded;
    }
    
    public Player getWinner() {
        return winner;
    }
    
    public static class GameResult {
        private final GameResultType type;
        private final Player player;
        private final int diceRoll;
        private final String description;
        
        public GameResult(GameResultType type, Player player, int diceRoll, String description) {
            this.type = type;
            this.player = player;
            this.diceRoll = diceRoll;
            this.description = description;
        }
        
        public GameResultType getType() { return type; }
        public Player getPlayer() { return player; }
        public int getDiceRoll() { return diceRoll; }
        public String getDescription() { return description; }
    }
    
    public enum GameResultType {
        TURN_COMPLETED,
        EXTRA_TURN,
        TURN_REVOKED,
        PLAYER_WON,
        GAME_ENDED
    }
}

