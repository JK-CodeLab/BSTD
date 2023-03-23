package com.jk.bstd.entities;

public class Sprinkler extends Tower {
    public static final int ATTACK = 5;
    public static final int ATTACK_SPEED = 3;
    public static final int RANGE = 3;
    public static final int COST = 10;

    //TODO: change the point
    public Sprinkler(Point point) {
        super(point, ATTACK, ATTACK_SPEED, RANGE, COST);
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
