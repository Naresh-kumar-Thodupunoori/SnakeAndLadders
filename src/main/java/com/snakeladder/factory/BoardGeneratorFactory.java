package com.snakeladder.factory;

import com.snakeladder.strategy.BoardGenerationStrategy;
import com.snakeladder.strategy.RandomBoardGenerationStrategy;
import com.snakeladder.strategy.BalancedBoardGenerationStrategy;

public class BoardGeneratorFactory {
    
    public enum GeneratorType {
        RANDOM("Random placement"),
        BALANCED("Balanced distribution");
        
        private final String description;
        
        GeneratorType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    public static BoardGenerationStrategy createGenerator(GeneratorType type) {
        switch (type) {
            case RANDOM:
                return new RandomBoardGenerationStrategy();
            case BALANCED:
                return new BalancedBoardGenerationStrategy();
            default:
                throw new IllegalArgumentException("Unknown generator type: " + type);
        }
    }
    
    public static BoardGenerationStrategy createGenerator(GeneratorType type, long seed) {
        switch (type) {
            case RANDOM:
                return new RandomBoardGenerationStrategy(seed);
            case BALANCED:
                return new BalancedBoardGenerationStrategy(seed);
            default:
                throw new IllegalArgumentException("Unknown generator type: " + type);
        }
    }
    
    public static GeneratorType getDefaultGeneratorType() {
        return GeneratorType.BALANCED;
    }
}
