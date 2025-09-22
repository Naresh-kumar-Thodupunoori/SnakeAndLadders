package com.snakeladder.model;

public class Snake implements BoardEntity {
    private final int headPos;
    private final int tailPos;
    
    public Snake(int head, int tail) {
        // make sure head is higher than tail
        if (head <= tail) {
            throw new IllegalArgumentException("Snake head must be greater than tail");
        }
        this.headPos = head;
        this.tailPos = tail;
    }
    
    @Override
    public int getStartPosition() {
        return headPos;
    }
    
    @Override
    public int getEndPosition() {
        return tailPos;
    }
    
    @Override
    public String getType() {
        return "SNAKE";
    }
    
    @Override
    public int transform(int pos) {
        if (pos == headPos) {
            // player landed on snake head, send to tail
            return tailPos;
        } else {
            return pos;
        }
    }
    
    @Override
    public String toString() {
        return "Snake{head=" + headPos + ", tail=" + tailPos + "}";
    }
}

