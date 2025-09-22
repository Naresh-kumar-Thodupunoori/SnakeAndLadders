package com.snakeladder.model;

public class Player {
    private final String name;
    private final String symbol;
    private int currentPosition;
    private int consecutiveSixes;
    private boolean isActive;
    
    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.currentPosition = 0; // Start before board
        this.consecutiveSixes = 0;
        this.isActive = true;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public int getCurrentPosition() {
        return currentPosition;
    }
    
    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }
    
    public int getConsecutiveSixes() {
        return consecutiveSixes;
    }
    
    public void incrementConsecutiveSixes() {
        this.consecutiveSixes++;
    }
    
    public void resetConsecutiveSixes() {
        this.consecutiveSixes = 0;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    public boolean hasWon(int boardSize) {
        return currentPosition >= boardSize;
    }
    
    @Override
    public String toString() {
        return String.format("%s(%s) at position %d", name, symbol, currentPosition);
    }
}

