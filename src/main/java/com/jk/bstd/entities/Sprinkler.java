package com.jk.bstd.entities;
/**
 * A sprinkler class for the sprinkler tower.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Sprinkler extends Tower {
    private static final int ATTACK = 5;
    private static final int RANGE = 2;
    private static final int COST = 10;
    /**
     * Constructor for a new sprinkler tower.
     *
     * @param point the point to place the tower
     */
    public Sprinkler(final Point point) {
        super(point, ATTACK, RANGE, COST);
    }
}
