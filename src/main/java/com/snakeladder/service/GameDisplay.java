package com.snakeladder.service;

import com.snakeladder.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class GameDisplay {
    private static final String EMPTY_CELL = "   ";
    private static final String SNAKE_SYMBOL = " 🐍";
    private static final String LADDER_SYMBOL = " 🪜";
    
    public void displayBoard(Game game) {
        Board board = game.getBoard();
        int size = board.getSize();
        
        System.out.println("\n" + "=".repeat(size * 8) + "=");
        System.out.println("🎲 SNAKES AND LADDERS BOARD 🎲");
        System.out.println("=".repeat(size * 8) + "=");
        
        for (int row = size - 1; row >= 0; row--) {
            displayBoardRow(board, game.getPlayers(), row, size);
            if (row > 0) {
                System.out.println("+" + "-".repeat(size * 8 - 1) + "+");
            }
        }
        
        System.out.println("=".repeat(size * 8) + "=");
    }
    
    private void displayBoardRow(Board board, List<Player> players, int row, int size) {
        StringBuilder topLine = new StringBuilder("|");
        StringBuilder middleLine = new StringBuilder("|");
        StringBuilder bottomLine = new StringBuilder("|");
        
        for (int col = 0; col < size; col++) {
            int cellNumber = calculateCellNumber(row, col, size);
            
            topLine.append(String.format(" %2d    ", cellNumber)).append("|");
            
            String cellContent = getCellContent(cellNumber, players, board);
            middleLine.append(cellContent).append("|");
            
            bottomLine.append("       |");
        }
        
        System.out.println(topLine.toString());
        System.out.println(middleLine.toString());
        System.out.println(bottomLine.toString());
    }
    
    private int calculateCellNumber(int row, int col, int size) {
        if (row % 2 == 0) {
            return row * size + col + 1;
        } else {
            return row * size + (size - col);
        }
    }
    
    private String getCellContent(int cellNumber, List<Player> players, Board board) {
        StringBuilder content = new StringBuilder();
        
        List<Player> playersHere = players.stream()
            .filter(p -> p.getCurrentPosition() == cellNumber)
            .collect(Collectors.toList());
        
        if (!playersHere.isEmpty()) {
            content.append(playersHere.stream()
                .map(Player::getSymbol)
                .collect(Collectors.joining("")));
        }
        
        BoardEntity entity = board.getEntityAt(cellNumber);
        if (entity != null) {
            if ("SNAKE".equals(entity.getType())) {
                content.append(SNAKE_SYMBOL);
            } else if ("LADDER".equals(entity.getType())) {
                content.append(LADDER_SYMBOL);
            }
        }
        
        String result = content.toString();
        if (result.length() > 7) {
            result = result.substring(0, 7);
        }
        while (result.length() < 7) {
            result += " ";
        }
        
        return result;
    }
    
    public void displayPlayerStatus(Game game) {
        System.out.println("\n📊 PLAYER STATUS:");
        System.out.println("-".repeat(50));
        
        List<Player> players = game.getPlayers();
        Player currentPlayer = game.getCurrentPlayer();
        
        for (Player player : players) {
            String status = player.isActive() ? "Active" : "Inactive";
            String current = player.equals(currentPlayer) ? " 👈 CURRENT TURN" : "";
            String consecutive = player.getConsecutiveSixes() > 0 ? 
                String.format(" (🎲x%d)", player.getConsecutiveSixes()) : "";
            
            System.out.printf("%s %s - Position: %d - %s%s%s%n",
                player.getSymbol(),
                player.getName(),
                player.getCurrentPosition(),
                status,
                consecutive,
                current);
        }
    }
    
    public void displayGameEntities(Board board) {
        System.out.println("\n🎯 BOARD ENTITIES:");
        System.out.println("-".repeat(50));
        
        List<BoardEntity> entities = board.getAllEntities();
        Map<String, List<BoardEntity>> groupedEntities = entities.stream()
            .collect(Collectors.groupingBy(BoardEntity::getType));
        
        for (Map.Entry<String, List<BoardEntity>> entry : groupedEntities.entrySet()) {
            System.out.println(entry.getKey() + "S:");
            entry.getValue().forEach(entity -> {
                String symbol = "SNAKE".equals(entity.getType()) ? "🐍" : "🪜";
                System.out.printf("  %s %d → %d%n", symbol, entity.getStartPosition(), entity.getEndPosition());
            });
        }
    }
    
    public void displayGameResult(Game.GameResult result) {
        System.out.println("\n" + "=".repeat(60));
        
        switch (result.getType()) {
            case PLAYER_WON:
                System.out.println("🎉 GAME OVER! 🎉");
                System.out.println("🏆 Winner: " + result.getPlayer().getName() + " " + result.getPlayer().getSymbol());
                break;
            case EXTRA_TURN:
                System.out.println("🎲 Extra Turn!");
                break;
            case TURN_REVOKED:
                System.out.println("❌ Turn Revoked!");
                break;
            case TURN_COMPLETED:
                System.out.println("✅ Turn Completed");
                break;
            case GAME_ENDED:
                System.out.println("🏁 Game Already Ended");
                break;
        }
        
        System.out.println("📝 " + result.getDescription());
        System.out.println("=".repeat(60));
    }
}

