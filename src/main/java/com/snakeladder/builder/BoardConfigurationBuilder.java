package com.snakeladder.builder;

import com.snakeladder.factory.BoardGeneratorFactory;
import com.snakeladder.model.GameLevel;
import com.snakeladder.model.GameLevelInterface;
import com.snakeladder.strategy.BoardGenerationStrategy;

public class BoardConfigurationBuilder {
    private int boardSize = 7; // Default size
    private GameLevel gameLevel = GameLevel.MEDIUM; // Default difficulty
    private BoardGeneratorFactory.GeneratorType generatorType = BoardGeneratorFactory.GeneratorType.BALANCED; // Default generator
    private Long seed = null; // For reproducible generation
    private boolean enableSymmetry = false;
    private boolean enableBalancing = true;
    private double customSnakeRatio = -1; // -1 means use level default
    private double customLadderRatio = -1; // -1 means use level default
    
    public BoardConfigurationBuilder withSize(int size) {
        if (size < 5 || size > 15) {
            throw new IllegalArgumentException("Board size must be between 5 and 15");
        }
        this.boardSize = size;
        return this;
    }
    
    public BoardConfigurationBuilder withLevel(GameLevel level) {
        this.gameLevel = level;
        return this;
    }
    
    public BoardConfigurationBuilder withGeneratorType(BoardGeneratorFactory.GeneratorType type) {
        this.generatorType = type;
        return this;
    }
    
    public BoardConfigurationBuilder withSeed(long seed) {
        this.seed = seed;
        return this;
    }
    
    public BoardConfigurationBuilder withSymmetry(boolean enabled) {
        this.enableSymmetry = enabled;
        if (enabled) {
            this.generatorType = BoardGeneratorFactory.GeneratorType.BALANCED;
        }
        return this;
    }
    
    public BoardConfigurationBuilder withBalancing(boolean enabled) {
        this.enableBalancing = enabled;
        if (enabled && generatorType == BoardGeneratorFactory.GeneratorType.RANDOM) {
            this.generatorType = BoardGeneratorFactory.GeneratorType.BALANCED;
        }
        return this;
    }
    
    public BoardConfigurationBuilder withCustomSnakeRatio(double ratio) {
        if (ratio < 0 || ratio > 0.5) {
            throw new IllegalArgumentException("Snake ratio must be between 0 and 0.5");
        }
        this.customSnakeRatio = ratio;
        return this;
    }
    
    public BoardConfigurationBuilder withCustomLadderRatio(double ratio) {
        if (ratio < 0 || ratio > 0.5) {
            throw new IllegalArgumentException("Ladder ratio must be between 0 and 0.5");
        }
        this.customLadderRatio = ratio;
        return this;
    }
    
    public BoardConfigurationBuilder beginnerPreset() {
        return withSize(7)
               .withLevel(GameLevel.EASY)
               .withGeneratorType(BoardGeneratorFactory.GeneratorType.BALANCED)
               .withBalancing(true)
               .withCustomSnakeRatio(0.08)  // Fewer snakes
               .withCustomLadderRatio(0.18); // More ladders
    }
    
    public BoardConfigurationBuilder expertPreset() {
        return withSize(10)
               .withLevel(GameLevel.HARD)
               .withGeneratorType(BoardGeneratorFactory.GeneratorType.RANDOM)
               .withCustomSnakeRatio(0.25)  // More snakes
               .withCustomLadderRatio(0.08); // Fewer ladders
    }
    
    public BoardConfigurationBuilder aestheticPreset() {
        return withSize(8)
               .withLevel(GameLevel.MEDIUM)
               .withGeneratorType(BoardGeneratorFactory.GeneratorType.BALANCED)
               .withSymmetry(true)
               .withBalancing(true);
    }
    
    public BoardConfiguration build() {
        GameLevelInterface effectiveLevel = gameLevel;
        if (customSnakeRatio >= 0 || customLadderRatio >= 0) {
            double snakeRatio = customSnakeRatio >= 0 ? customSnakeRatio : gameLevel.getSnakeRatio();
            double ladderRatio = customLadderRatio >= 0 ? customLadderRatio : gameLevel.getLadderRatio();
            effectiveLevel = new CustomGameLevel(gameLevel.getDisplayName() + " (Custom)", 
                                                snakeRatio, ladderRatio);
        }
        
        BoardGenerationStrategy strategy;
        if (seed != null) {
            strategy = BoardGeneratorFactory.createGenerator(generatorType, seed);
        } else {
            strategy = BoardGeneratorFactory.createGenerator(generatorType);
        }
        
        return new BoardConfiguration(boardSize, effectiveLevel, strategy);
    }
    
    public boolean isValid() {
        return boardSize >= 5 && boardSize <= 15 
               && gameLevel != null 
               && generatorType != null;
    }
    
    public String getConfigurationSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board Configuration:\n");
        sb.append("- Size: ").append(boardSize).append("x").append(boardSize).append("\n");
        sb.append("- Level: ").append(gameLevel.getDisplayName()).append("\n");
        sb.append("- Generator: ").append(generatorType.getDescription()).append("\n");
        if (seed != null) {
            sb.append("- Seed: ").append(seed).append("\n");
        }
        if (customSnakeRatio >= 0) {
            sb.append("- Custom Snake Ratio: ").append(String.format("%.1f%%", customSnakeRatio * 100)).append("\n");
        }
        if (customLadderRatio >= 0) {
            sb.append("- Custom Ladder Ratio: ").append(String.format("%.1f%%", customLadderRatio * 100)).append("\n");
        }
        return sb.toString();
    }
    
    private static class CustomGameLevel implements GameLevelInterface {
        private final String displayName;
        private final double snakeRatio;
        private final double ladderRatio;
        
        public CustomGameLevel(String displayName, double snakeRatio, double ladderRatio) {
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
}
