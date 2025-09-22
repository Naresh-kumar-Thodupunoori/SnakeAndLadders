package com.snakeladder.builder;

import com.snakeladder.model.GameLevelInterface;
import com.snakeladder.strategy.BoardGenerationStrategy;

public class BoardConfiguration {
    private final int boardSize;
    private final GameLevelInterface gameLevel;
    private final BoardGenerationStrategy strategy;
    
    public BoardConfiguration(int boardSize, GameLevelInterface gameLevel, BoardGenerationStrategy strategy) {
        this.boardSize = boardSize;
        this.gameLevel = gameLevel;
        this.strategy = strategy;
    }
    
    public int getBoardSize() {
        return boardSize;
    }
    
    public GameLevelInterface getGameLevel() {
        return gameLevel;
    }
    
    public BoardGenerationStrategy getStrategy() {
        return strategy;
    }
    
    @Override
    public String toString() {
        return String.format("BoardConfiguration{size=%dx%d, level=%s, strategy=%s}", 
            boardSize, boardSize, gameLevel.getDisplayName(), strategy.getClass().getSimpleName());
    }
}
