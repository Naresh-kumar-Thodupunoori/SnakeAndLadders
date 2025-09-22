package com.snakeladder.model;

public enum GameLevel implements GameLevelInterface {
    EASY("Easy", 0.1, 0.15),    // 10% snakes, 15% ladders
    MEDIUM("Medium", 0.15, 0.12), // 15% snakes, 12% ladders
    HARD("Hard", 0.2, 0.1);      // 20% snakes, 10% ladders
    
    private final String displayName;
    private final double snakeRatio;
    private final double ladderRatio;
    
    GameLevel(String displayName, double snakeRatio, double ladderRatio) {
        this.displayName = displayName;
        this.snakeRatio = snakeRatio;
        this.ladderRatio = ladderRatio;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public double getSnakeRatio() {
        return snakeRatio;
    }
    
    public double getLadderRatio() {
        return ladderRatio;
    }
}

