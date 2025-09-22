package com.snakeladder.model;

public interface BoardEntity {
    int getStartPosition();
    int getEndPosition();
    String getType();
    int transform(int currentPosition);
}

