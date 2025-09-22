package com.snakeladder.model;

public class Ladder implements BoardEntity {
    private final int start;
    private final int end;
    
    public Ladder(int startPos, int endPos) {
        if(startPos >= endPos) {
            throw new IllegalArgumentException("Ladder bottom must be less than top");
        }
        start = startPos;
        end = endPos;
    }
    
    @Override
    public int getStartPosition() {
        return this.start;
    }
    
    @Override
    public int getEndPosition() {
        return this.end;
    }
    
    @Override
    public String getType() {
        return "LADDER";
    }
    
    @Override
    public int transform(int playerPos) {
        // Check if player is at bottom of ladder
        if(playerPos == start) {
            return end; // climb up!
        }
        return playerPos; // no change
    }
    
    @Override  
    public String toString() {
        return String.format("Ladder{bottom=%d, top=%d}", start, end);
    }
}

