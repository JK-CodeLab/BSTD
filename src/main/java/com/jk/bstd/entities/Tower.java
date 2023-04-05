package com.jk.bstd.entities;

import javafx.scene.image.ImageView;

public abstract class Tower extends Entity{
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

    public abstract ImageView getImgView();
    public abstract int getCost();
    abstract void attack();
    abstract void upgrade();
    abstract Entity selectTarget();
}
