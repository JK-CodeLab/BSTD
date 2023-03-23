package com.jk.bstd.entities;

public class Pig extends Animal {
    public static final int HEALTH = 30;
    public static final int SPEED = 4;
    public static final int ATTACK = 10;
    public static final int GOLD_DROPPED = 15;
    public Pig() {
        // TODO: Change spawn point
        super(HEALTH, SPEED, ATTACK, GOLD_DROPPED);
    }

    // TODO: Implement move method
    // pig should move 2 space per 0.5 unit of time
    // this makes it so it moves the same speed as the chicken since the model is twice as big
    @Override
    public void move() {}
}
