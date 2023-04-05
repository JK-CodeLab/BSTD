package com.jk.bstd.entities;

public abstract class Entity {
    // TODO: This needs to change depending on how we are implementing the grid system
    private Point point;

    public Entity(Point point) {
        this.point = point;
    }
    public Point getPoint() {
        return this.point;
    };
    public void setPoint(Point point) {
        this.point = point;
    }
    public int getX() {
        return this.point.getX();
    }
    public int getY() {
        return this.point.getY();
    }
    public void setX(int x) {
        this.point.setX(x);
    }
    public void setY(int y) {
        this.point.setY(y);
    }
}
