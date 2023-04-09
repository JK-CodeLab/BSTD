package com.jk.bstd.entities;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * A dog class for the dog tower.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Dog extends Tower {
    private static final int ATTACK = 15;
    private static final int RANGE = 4;
    private static final int COST = 30;
    private static final int BULLET_VERTICAL_SIZE = 8;
    private static final int BULLET_HORIZONTAL_SIZE = 4;
    private static final int BULLET_OFFSET_X = 16;
    private static final int BULLET_OFFSET_Y = 7;
    /**
     * Constructor for a new dog tower.
     *
     * @param point the point to place the tower
     */
    public Dog(final Point point) {
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
