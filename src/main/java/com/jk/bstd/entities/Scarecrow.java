package com.jk.bstd.entities;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
/**
 * A scarecrow class for the scarecrow tower.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Scarecrow extends Tower {
    private static final int ATTACK = 10;
    private static final int RANGE = 3;
    private static final int COST = 20;
    private static final int BULLET_RADIUS = 4;
    /**
     * Constructor for a new scarecrow tower.
     *
     * @param point the point to place the tower
     */
    public Scarecrow(final Point point) {
        super(point, ATTACK, RANGE, COST);
    }
    /**
     * Creates a new attack animation for the tower.
     *
     * @param enemyLocation the location of the enemy
     * @param pane the pane to add the animation to
     */
    @Override
    public void attackAnimation(final Point2D enemyLocation, final AnchorPane pane) {
        Circle bullet = new Circle(BULLET_RADIUS, Color.RED);
        bullet.setTranslateX(super.getPoint().getRealX());
        bullet.setTranslateY(super.getPoint().getRealY());
        pane.getChildren().add(bullet);
        super.createTransition(bullet, enemyLocation, pane);
    }
}
