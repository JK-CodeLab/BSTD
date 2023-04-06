package com.jk.bstd.entities;

public class Tile extends Entity{
    private static final int COST = 5;

    public Tile(Point point) {
        super(point);
    }

    @Override
    public int getCost() {
        return COST;
    }
}
