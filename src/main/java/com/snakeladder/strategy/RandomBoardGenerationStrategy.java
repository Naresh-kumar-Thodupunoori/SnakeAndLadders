package com.snakeladder.strategy;

import com.snakeladder.model.*;
import java.util.*;

public class RandomBoardGenerationStrategy implements BoardGenerationStrategy {
    private Random rng;
    
    public RandomBoardGenerationStrategy() {
        rng = new Random();
    }
    
    public RandomBoardGenerationStrategy(long seedValue) {
        this.rng = new Random(seedValue);
    }
    
    public RandomBoardGenerationStrategy(Random randomGen) {
        this.rng = randomGen;
    }
    
    @Override
    public List<BoardEntity> generateEntities(int totalCells, GameLevelInterface level) {
        ArrayList<BoardEntity> entityList = new ArrayList<BoardEntity>();
        Set<Integer> usedPositions = new HashSet<Integer>();
        
        // Calculate how many snakes and ladders we need
        int numSnakes = (int) (totalCells * level.getSnakeRatio());
        int numLadders = (int) (totalCells * level.getLadderRatio());
        
        // Generate snakes first
        for (int i = 0; i < numSnakes; i++) {
            Snake s = makeRandomSnake(totalCells, usedPositions);
            if (s != null) {
                entityList.add(s);
                usedPositions.add(s.getStartPosition());
                usedPositions.add(s.getEndPosition());
            }
        }
        
        // Now generate ladders
        for (int j = 0; j < numLadders; j++) {
            Ladder l = makeRandomLadder(totalCells, usedPositions);
            if (l != null) {
                entityList.add(l);
                usedPositions.add(l.getStartPosition());
                usedPositions.add(l.getEndPosition());
            }
        }
        
        return entityList;
    }
    
    private Snake makeRandomSnake(int totalCells, Set<Integer> occupiedPositions) {
        int attempts = 0;
        int maxAttempts = 100;
        
        int minDistance = Math.max(3, totalCells / 20);
        
        while (attempts < maxAttempts) {
            int minHead = Math.max(10, totalCells / 4);
            int head = rng.nextInt(totalCells - minHead) + minHead;
            
            int maxTail = head - minDistance;
            if (maxTail <= 1) {
                attempts++;
                continue;
            }
            
            int tail = rng.nextInt(maxTail) + 1;
            
            if (!occupiedPositions.contains(head) && !occupiedPositions.contains(tail)) {
                return new Snake(head, tail);
            }
            attempts++;
        }
        return null;
    }
    
    private Ladder makeRandomLadder(int totalCells, Set<Integer> occupiedPositions) {
        int attempts = 0;
        int maxAttempts = 100;
        
        int minDistance = Math.max(3, totalCells / 20);
        
        while (attempts < maxAttempts) {
            int maxBottom = Math.max(totalCells * 3 / 4, totalCells - 10);
            int bottom = rng.nextInt(maxBottom) + 1;
            
            int minTop = bottom + minDistance;
            if (minTop >= totalCells) {
                attempts++;
                continue;
            }
            
            int top = rng.nextInt(totalCells - minTop) + minTop;
            
            if (top < totalCells && !occupiedPositions.contains(bottom) && !occupiedPositions.contains(top)) {
                return new Ladder(bottom, top);
            }
            attempts++;
        }
        return null;
    }
}

