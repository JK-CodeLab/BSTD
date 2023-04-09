package com.jk.bstd.entities;
/**
 * A point class to represent the location of each object.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Point {
    private static final int OFFSET = 64;
    private static final int OFFSET_X = 1;
    private static final int OFFSET_Y = 4;
    private final int x;
    private final int y;
    /**
     * Default constructor for Point.
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }
    /**
     * Constructor for Point.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Returns the x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }
    /**
     * Returns the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
    /**
     * Returns the real x coordinate.
     *
     * @return the real x coordinate
     */
    public int getRealX() {
        return (x + OFFSET_X) * OFFSET;
    }
    /**
     * Returns the real y coordinate.
     *
     * @return the real y coordinate
     */
    public int getRealY() {
        return (y + OFFSET_Y) * OFFSET;
    }
    /**
     * Returns the string representation of the point.
     *
     * @return the string representation of the point
     */
    @Override
    public String toString() {
        return "Point{"
                + "x=" + x
                + ", y=" + y
                + '}';
    }
}
