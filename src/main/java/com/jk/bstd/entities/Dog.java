package com.jk.bstd.entities;

public class Dog extends Tower {
    public static final int ATTACK = 15;
    public static final int ATTACK_SPEED = 5;
    public static final int RANGE = 2;
    public static final int COST = 20;

    public Dog(Point point) {
        super(point, ATTACK, ATTACK_SPEED, RANGE, COST);
    }

    public int getCost() {
        return COST;
    }

    @Override
    void attack() {
        return;
    }

    @Override
    void upgrade() {
        return;
    }

    @Override
    Entity selectTarget() {
        return null;
    }
}
