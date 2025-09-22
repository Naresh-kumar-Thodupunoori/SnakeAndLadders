package com.snakeladder.model;

import com.snakeladder.strategy.BoardGenerationStrategy;
import java.util.*;

public class Board {
    private int boardSize;
    private int numberOfCells;
    private Map<Integer, BoardEntity> boardEntities;
    private BoardGenerationStrategy strategy;
    
    public Board(int sz, GameLevelInterface gameLevel, BoardGenerationStrategy genStrategy) {
        this.boardSize = sz;
        numberOfCells = sz * sz;  // calculate total cells
        strategy = genStrategy;
        boardEntities = new HashMap<Integer, BoardEntity>();
        setupBoard(gameLevel);
    }
    
    private void setupBoard(GameLevelInterface level) {
        List<BoardEntity> entityList = strategy.generateEntities(numberOfCells, level);
        // put entities in the map
        for (BoardEntity e : entityList) {
            boardEntities.put(e.getStartPosition(), e);
        }
    }
    
    public int getSize() {
        return this.boardSize;
    }
    
    public int getTotalCells() {
        return numberOfCells;
    }
    
    public Position getPosition(int cellNum) {
        if (cellNum < 1 || cellNum > numberOfCells) {
            throw new IllegalArgumentException("Invalid cell number: " + cellNum);
        }
        
        int r = (cellNum - 1) / boardSize;
        int c;
        
        // snake pattern - odd rows go right to left, even rows go left to right  
        if (r % 2 == 0) {
            c = (cellNum - 1) % boardSize;
        } else {
            c = boardSize - 1 - ((cellNum - 1) % boardSize);
        }
        
        return new Position(r, c, cellNum);
    }
    
    public int transformPosition(int pos) {
        BoardEntity entity = boardEntities.get(pos);
        if (entity != null) {
            return entity.transform(pos);
        } else {
            return pos;
        }
    }
    
    public BoardEntity getEntityAt(int pos) {
        return boardEntities.get(pos);
    }
    
    public List<BoardEntity> getAllEntities() {
        List<BoardEntity> allEntities = new ArrayList<>();
        allEntities.addAll(boardEntities.values());
        return allEntities;
    }
    
    public boolean isValidPosition(int pos) {
        if (pos >= 0 && pos <= numberOfCells) {
            return true;
        }
        return false;
    }
}

