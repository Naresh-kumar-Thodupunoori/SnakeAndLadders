package com.snakeladder.strategy;

import com.snakeladder.model.BoardEntity;
import com.snakeladder.model.GameLevelInterface;
import java.util.List;

public interface BoardGenerationStrategy {
    List<BoardEntity> generateEntities(int totalCells, GameLevelInterface level);
}

