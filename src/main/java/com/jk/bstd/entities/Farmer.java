package com.jk.bstd.entities;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * A farmer class for the farmer tower.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Farmer extends Tower {
    private static final int ATTACK = 20;
    private static final int RANGE = 5;
    private static final int COST = 50;
    private static final int BULLET_VERTICAL_SIZE = 8;
    private static final int BULLET_HORIZONTAL_SIZE = 4;
    private static final int BULLET_OFFSET_X = 10;
    private static final int BULLET_OFFSET_Y = 7;
    /**
     * Constructor for a new farmer tower.
     *
     * @param point the point to place the tower
     */
    public Farmer(final Point point) {
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
        Rectangle bullet = new Rectangle(BULLET_VERTICAL_SIZE, BULLET_HORIZONTAL_SIZE, Color.BLACK);
        bullet.setTranslateX(super.getPoint().getRealX() + BULLET_OFFSET_X);
        bullet.setTranslateY(super.getPoint().getRealY() + BULLET_OFFSET_Y);
        pane.getChildren().add(bullet);
        super.createTransition(bullet, enemyLocation, pane);
    }
}
