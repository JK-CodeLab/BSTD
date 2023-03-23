package com.jk.bstd.entities;

abstract class Tower extends Entity{
    int attack;
    int attackSpeed;
    int range;
    int cost;

    // TODO: change the point
    public Tower(Point point, int attack, int attackSpeed, int range, int cost) {
        super(point);
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.range = range;
        this.cost = cost;
    }

    abstract void attack();
    abstract void upgrade();
    abstract Entity selectTarget();
}
