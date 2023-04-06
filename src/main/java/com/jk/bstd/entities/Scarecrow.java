package com.jk.bstd.entities;

public class Scarecrow extends Tower {
    public static final int ATTACK = 5;
    public static final int ATTACK_SPEED = 3;
    public static final int RANGE = 3;
    public static final int COST = 10;

    public Scarecrow(Point point) {
        super(point, ATTACK, ATTACK_SPEED, RANGE, COST);
    }

    public int getCost() {
        return COST;
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
