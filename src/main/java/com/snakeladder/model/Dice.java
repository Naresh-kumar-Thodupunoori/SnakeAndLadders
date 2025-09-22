package com.snakeladder.model;

import java.util.Random;

public class Dice {
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 6;
    private final Random random;
    
    public Dice() {
        this.random = new Random();
    }
    
    public Dice(Random random) {
        this.random = random;
    }
    
    public int roll() {
        return random.nextInt(MAX_VALUE) + MIN_VALUE;
    }
    
    public static boolean isSix(int value) {
        return value == MAX_VALUE;
    }
}

