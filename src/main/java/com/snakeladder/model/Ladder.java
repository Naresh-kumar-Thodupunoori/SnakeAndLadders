package com.snakeladder.model;

public class Ladder implements BoardEntity {
    private final int bottom;
    private final int top;
    
    public Ladder(int bottom, int top) {
        if (bottom >= top) {
            throw new IllegalArgumentException("Ladder bottom must be less than top");
        }
        this.bottom = bottom;
        this.top = top;
    }
    
    @Override
    public int getStartPosition() {
        return bottom;
    }
    
    @Override
    public int getEndPosition() {
        return top;
    }
    
    @Override
    public String getType() {
        return "LADDER";
    }
    
    @Override
    public int transform(int currentPosition) {
        if (currentPosition == bottom) {
            return top;
        }
        return currentPosition;
    }
    
    @Override
    public String toString() {
        return String.format("Ladder{bottom=%d, top=%d}", bottom, top);
    }
}

