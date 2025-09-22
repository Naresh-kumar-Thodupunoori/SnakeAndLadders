package com.snakeladder.model;

public class Snake implements BoardEntity {
    private final int head;
    private final int tail;
    
    public Snake(int head, int tail) {
        if (head <= tail) {
            throw new IllegalArgumentException("Snake head must be greater than tail");
        }
        this.head = head;
        this.tail = tail;
    }
    
    @Override
    public int getStartPosition() {
        return head;
    }
    
    @Override
    public int getEndPosition() {
        return tail;
    }
    
    @Override
    public String getType() {
        return "SNAKE";
    }
    
    @Override
    public int transform(int currentPosition) {
        if (currentPosition == head) {
            return tail;
        }
        return currentPosition;
    }
    
    @Override
    public String toString() {
        return String.format("Snake{head=%d, tail=%d}", head, tail);
    }
}

