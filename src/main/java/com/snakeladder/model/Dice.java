package com.snakeladder.model;

import java.util.Random;

public class Dice {
    private Random rand;
    
    public Dice() {
        this.rand = new Random();
    }
    
    public Dice(Random r) {
        rand = r;  // no this keyword needed here
    }
    
    public int roll() {
        // Generate random number from 1-6
        int result = rand.nextInt(6) + 1;
        return result;
    }
    
    public static boolean isSix(int diceValue) {
        if (diceValue == 6) 
            return true;
        else 
            return false;
    }
}

