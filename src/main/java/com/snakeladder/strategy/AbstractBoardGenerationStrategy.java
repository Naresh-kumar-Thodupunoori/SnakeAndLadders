package com.snakeladder.strategy;

import com.snakeladder.model.*;
import java.util.*;

public abstract class AbstractBoardGenerationStrategy implements BoardGenerationStrategy {
    protected final Random random;
    
    public AbstractBoardGenerationStrategy() {
        this.random = new Random();
    }
    
    public AbstractBoardGenerationStrategy(long seed) {
        this.random = new Random(seed);
    }
    
    @Override
    public final List<BoardEntity> generateEntities(int totalCells, GameLevelInterface level) {
        List<BoardEntity> entities = new ArrayList<>();
        Set<Integer> occupiedPositions = new HashSet<>();
        
        EntityCounts counts = calculateEntityCounts(totalCells, level);
        
        List<Snake> snakes = generateSnakes(totalCells, counts.snakeCount, occupiedPositions);
        entities.addAll(snakes);
        
        List<Ladder> ladders = generateLadders(totalCells, counts.ladderCount, occupiedPositions);
        entities.addAll(ladders);
        
        postProcessEntities(entities, totalCells);
        
        return entities;
    }
    
    protected EntityCounts calculateEntityCounts(int totalCells, GameLevelInterface level) {
        int snakeCount = (int) (totalCells * level.getSnakeRatio());
        int ladderCount = (int) (totalCells * level.getLadderRatio());
        return new EntityCounts(snakeCount, ladderCount);
    }
    
    protected abstract List<Snake> generateSnakes(int totalCells, int count, Set<Integer> occupiedPositions);
    
    protected abstract List<Ladder> generateLadders(int totalCells, int count, Set<Integer> occupiedPositions);
    
    protected void postProcessEntities(List<BoardEntity> entities, int totalCells) {
    }
    
    protected boolean isValidPosition(int position, int totalCells, Set<Integer> occupiedPositions) {
        return position > 0 && position < totalCells && !occupiedPositions.contains(position);
    }
    
    protected void markPositionsOccupied(Set<Integer> occupiedPositions, int... positions) {
        for (int pos : positions) {
            occupiedPositions.add(pos);
        }
    }
    
    protected static class EntityCounts {
        final int snakeCount;
        final int ladderCount;
        
        EntityCounts(int snakeCount, int ladderCount) {
            this.snakeCount = snakeCount;
            this.ladderCount = ladderCount;
        }
    }
}
