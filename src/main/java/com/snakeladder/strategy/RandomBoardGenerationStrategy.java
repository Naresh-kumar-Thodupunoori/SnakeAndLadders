package com.snakeladder.strategy;

import com.snakeladder.model.*;
import java.util.*;

public class RandomBoardGenerationStrategy implements BoardGenerationStrategy {
    private final Random random;
    
    public RandomBoardGenerationStrategy() {
        this.random = new Random();
    }
    
    public RandomBoardGenerationStrategy(long seed) {
        this.random = new Random(seed);
    }
    
    public RandomBoardGenerationStrategy(Random random) {
        this.random = random;
    }
    
    @Override
    public List<BoardEntity> generateEntities(int totalCells, GameLevelInterface level) {
        List<BoardEntity> entities = new ArrayList<>();
        Set<Integer> occupiedPositions = new HashSet<>();
        int snakeCount = (int) (totalCells * level.getSnakeRatio());
        int ladderCount = (int) (totalCells * level.getLadderRatio());
        
        for (int i = 0; i < snakeCount; i++) {
            Snake snake = generateRandomSnake(totalCells, occupiedPositions);
            if (snake != null) {
                entities.add(snake);
                occupiedPositions.add(snake.getStartPosition());
                occupiedPositions.add(snake.getEndPosition());
            }
        }
        
        for (int i = 0; i < ladderCount; i++) {
            Ladder ladder = generateRandomLadder(totalCells, occupiedPositions);
            if (ladder != null) {
                entities.add(ladder);
                occupiedPositions.add(ladder.getStartPosition());
                occupiedPositions.add(ladder.getEndPosition());
            }
        }
        
        return entities;
    }
    
    private Snake generateRandomSnake(int totalCells, Set<Integer> occupiedPositions) {
        int attempts = 0;
        int maxAttempts = 100;
        
        int minDistance = Math.max(3, totalCells / 20);
        
        while (attempts < maxAttempts) {
            int minHead = Math.max(10, totalCells / 4);
            int head = random.nextInt(totalCells - minHead) + minHead;
            
            int maxTail = head - minDistance;
            if (maxTail <= 1) {
                attempts++;
                continue;
            }
            
            int tail = random.nextInt(maxTail) + 1;
            
            if (!occupiedPositions.contains(head) && !occupiedPositions.contains(tail)) {
                return new Snake(head, tail);
            }
            attempts++;
        }
        return null;
    }
    
    private Ladder generateRandomLadder(int totalCells, Set<Integer> occupiedPositions) {
        int attempts = 0;
        int maxAttempts = 100;
        
        int minDistance = Math.max(3, totalCells / 20);
        
        while (attempts < maxAttempts) {
            int maxBottom = Math.max(totalCells * 3 / 4, totalCells - 10);
            int bottom = random.nextInt(maxBottom) + 1;
            
            int minTop = bottom + minDistance;
            if (minTop >= totalCells) {
                attempts++;
                continue;
            }
            
            int top = random.nextInt(totalCells - minTop) + minTop;
            
            if (top < totalCells && !occupiedPositions.contains(bottom) && !occupiedPositions.contains(top)) {
                return new Ladder(bottom, top);
            }
            attempts++;
        }
        return null;
    }
}

