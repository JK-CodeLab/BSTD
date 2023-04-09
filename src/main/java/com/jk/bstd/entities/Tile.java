package com.jk.bstd.entities;
/**
 * A tile class for the tiles on the map.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Tile extends Entity {
    private static final int COST = 5;
    /**
     * Constructor for a new tile.
     *
     * @param point the point to place the tile
     */
    public Tile(final Point point) {
        super(point);
    }
    /**
     * Returns the cost of the tile.
     *
     * @return the cost of the tile
     */
    public int getCost() {
        return COST;
    }
}
