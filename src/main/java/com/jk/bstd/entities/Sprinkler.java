package com.jk.bstd.entities;

public class Sprinkler extends Tower {
    public static final int ATTACK = 5;
    public static final int ATTACK_SPEED = 1;
    public static final int RANGE = 2;
    public static final int COST = 10;

    public Sprinkler(Point point) {
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
