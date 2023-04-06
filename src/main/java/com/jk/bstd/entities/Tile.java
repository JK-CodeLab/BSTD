package com.jk.bstd.entities;

public class Tile extends Entity{
    public int cost = 5;

    public Tile(Point point) {
        super(point);
    }

    public Point getPoint() {
        return super.getPoint();
    }

    @Override
    public int getCost() {
        return cost;
    }

}
