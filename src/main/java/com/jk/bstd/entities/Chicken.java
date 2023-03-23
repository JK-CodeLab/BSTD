package com.jk.bstd.entities;

public class Chicken extends Animal {
    public static final int HEALTH = 30;
    public static final int SPEED = 4;
    public static final int ATTACK = 10;
    public static final int GOLD_DROPPED = 15;
    public Chicken() {
        // TODO: Change spawn point
        super(HEALTH, SPEED, ATTACK, GOLD_DROPPED);
    }
    // TODO: Implement move method
    // chicken should move 1 space per 1 unit of time
    @Override
    public void move() {}
}
