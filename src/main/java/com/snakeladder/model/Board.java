package com.snakeladder.model;

import com.snakeladder.strategy.BoardGenerationStrategy;
import java.util.*;

public class Board {
    private final int size;
    private final int totalCells;
    private final Map<Integer, BoardEntity> entities;
    private final BoardGenerationStrategy generationStrategy;
    
    public Board(int size, GameLevelInterface level, BoardGenerationStrategy generationStrategy) {
        this.size = size;
        this.totalCells = size * size;
        this.generationStrategy = generationStrategy;
        this.entities = new HashMap<>();
        initializeBoard(level);
    }
    
    private void initializeBoard(GameLevelInterface level) {
        List<BoardEntity> boardEntities = generationStrategy.generateEntities(totalCells, level);
        for (BoardEntity entity : boardEntities) {
            entities.put(entity.getStartPosition(), entity);
        }
    }
    
    public int getSize() {
        return size;
    }
    
    public int getTotalCells() {
        return totalCells;
    }
    
    public Position getPosition(int cellNumber) {
        if (cellNumber < 1 || cellNumber > totalCells) {
            throw new IllegalArgumentException("Invalid cell number: " + cellNumber);
        }
        
        int row = (cellNumber - 1) / size;
        int column;
        
        if (row % 2 == 0) {
            column = (cellNumber - 1) % size;
        } else {
            column = size - 1 - ((cellNumber - 1) % size);
        }
        
        return new Position(row, column, cellNumber);
    }
    
    public int transformPosition(int position) {
        BoardEntity entity = entities.get(position);
        if (entity != null) {
            return entity.transform(position);
        }
        return position;
    }
    
    public BoardEntity getEntityAt(int position) {
        return entities.get(position);
    }
    
    public List<BoardEntity> getAllEntities() {
        return new ArrayList<>(entities.values());
    }
    
    public boolean isValidPosition(int position) {
        return position >= 0 && position <= totalCells;
    }
}

