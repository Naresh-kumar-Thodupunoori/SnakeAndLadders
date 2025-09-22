package com.snakeladder.strategy;

import com.snakeladder.model.*;
import java.util.*;

/**
 * Balanced board generation strategy using Template Method Pattern
 * Ensures even distribution of snakes and ladders across the board
 */
public class BalancedBoardGenerationStrategy extends AbstractBoardGenerationStrategy {
    
    public BalancedBoardGenerationStrategy() {
        super();
    }
    
    public BalancedBoardGenerationStrategy(long seed) {
        super(seed);
    }
    
    @Override
    protected List<Snake> generateSnakes(int totalCells, int count, Set<Integer> occupiedPositions) {
        List<Snake> snakes = new ArrayList<>();
        
        // Divide board into zones for balanced distribution
        int zones = Math.min(4, (int) Math.sqrt(totalCells / 10)); // 4 zones max
        int snakesPerZone = Math.max(1, count / zones);
        
        for (int zone = 0; zone < zones && snakes.size() < count; zone++) {
            int zoneStart = (totalCells * zone / zones) + 1;
            int zoneEnd = totalCells * (zone + 1) / zones;
            
            for (int i = 0; i < snakesPerZone && snakes.size() < count; i++) {
                Snake snake = generateSnakeInZone(zoneStart, zoneEnd, occupiedPositions);
                if (snake != null) {
                    snakes.add(snake);
                    markPositionsOccupied(occupiedPositions, snake.getStartPosition(), snake.getEndPosition());
                }
            }
        }
        
        return snakes;
    }
    
    @Override
    protected List<Ladder> generateLadders(int totalCells, int count, Set<Integer> occupiedPositions) {
        List<Ladder> ladders = new ArrayList<>();
        
        // Divide board into zones for balanced distribution
        int zones = Math.min(4, (int) Math.sqrt(totalCells / 10));
        int laddersPerZone = Math.max(1, count / zones);
        
        for (int zone = 0; zone < zones && ladders.size() < count; zone++) {
            int zoneStart = (totalCells * zone / zones) + 1;
            int zoneEnd = totalCells * (zone + 1) / zones;
            
            for (int i = 0; i < laddersPerZone && ladders.size() < count; i++) {
                Ladder ladder = generateLadderInZone(zoneStart, zoneEnd, totalCells, occupiedPositions);
                if (ladder != null) {
                    ladders.add(ladder);
                    markPositionsOccupied(occupiedPositions, ladder.getStartPosition(), ladder.getEndPosition());
                }
            }
        }
        
        return ladders;
    }
    
    private Snake generateSnakeInZone(int zoneStart, int zoneEnd, Set<Integer> occupiedPositions) {
        int attempts = 0;
        int maxAttempts = 50;
        
        // Minimum meaningful distance for snakes
        int minDistance = Math.max(2, (zoneEnd - zoneStart) / 8);
        
        while (attempts < maxAttempts) {
            // Head should be in upper part of zone, but constrained to zone
            int headStart = Math.max(zoneStart + minDistance, zoneStart + (zoneEnd - zoneStart) / 2);
            int headEnd = Math.min(zoneEnd, zoneEnd);
            
            if (headStart >= headEnd) {
                attempts++;
                continue;
            }
            
            int head = random.nextInt(headEnd - headStart) + headStart;
            
            // Tail should be lower than head
            int maxTail = head - minDistance;
            if (maxTail <= zoneStart) {
                attempts++;
                continue;
            }
            
            int tail = random.nextInt(Math.min(maxTail, zoneEnd) - zoneStart) + zoneStart;
            
            if (!occupiedPositions.contains(head) && !occupiedPositions.contains(tail) && head != tail) {
                return new Snake(head, tail);
            }
            attempts++;
        }
        
        return null;
    }
    
    private Ladder generateLadderInZone(int zoneStart, int zoneEnd, int totalCells, Set<Integer> occupiedPositions) {
        int attempts = 0;
        int maxAttempts = 50;
        
        // Minimum meaningful distance for ladders
        int minDistance = Math.max(3, (zoneEnd - zoneStart) / 10);
        
        while (attempts < maxAttempts) {
            // Bottom should be in lower part of zone
            int bottomEnd = zoneStart + (zoneEnd - zoneStart) / 2;
            int bottom = random.nextInt(bottomEnd - zoneStart) + zoneStart;
            
            // Top should be higher than bottom
            int minTop = bottom + minDistance;
            if (minTop >= totalCells) {
                attempts++;
                continue;
            }
            
            int top = Math.min(random.nextInt(totalCells - minTop) + minTop, totalCells - 1);
            
            if (isValidPosition(bottom, totalCells, occupiedPositions) && 
                isValidPosition(top, totalCells, occupiedPositions)) {
                return new Ladder(bottom, top);
            }
            attempts++;
        }
        
        return null;
    }
    
    @Override
    protected void postProcessEntities(List<BoardEntity> entities, int totalCells) {
        // Balanced strategy post-processing: ensure no clustering
        // Remove entities that are too close to each other
        entities.removeIf(entity1 -> {
            return entities.stream()
                    .anyMatch(entity2 -> entity1 != entity2 && 
                             Math.abs(entity1.getStartPosition() - entity2.getStartPosition()) < 3);
        });
    }
}
