package com.jk.bstd.entities;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
/**
 * A Tower class for the defense towers.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Tower extends Entity {
    private static final int BULLET_SPEED = 150;
    private static final int BULLET_RADIUS = 4;
    private static final int BULLET_OFFSET = 32;
    private final int attack;
    private final int range;
    private final int cost;
    private boolean hasTarget = false;
    /**
     * Constructor for a new tower.
     *
     * @param point the point to place the tower
     * @param attack the attack of the tower
     * @param range the range of the tower
     * @param cost the cost of the tower
     */
    public Tower(final Point point, final int attack, final int range, final int cost) {
        super(point);
        this.attack = attack;
        this.range = range;
        this.cost = cost;
    }

    /**
     * Creates a new attack animation for the tower.
     *
     * @param enemyLocation the location of the enemy
     * @param pane the pane to add the animation to
     */
    public void attackAnimation(final Point2D enemyLocation, final AnchorPane pane) {
        Circle bullet = new Circle(BULLET_RADIUS, Color.BLUE);
        bullet.setTranslateX(super.getPoint().getRealX() + BULLET_OFFSET);
        bullet.setTranslateY(super.getPoint().getRealY() + BULLET_OFFSET);
        pane.getChildren().add(bullet);
        createTransition(bullet, enemyLocation, pane);
    }
    /**
     * Creates a new transition for the bullet.
     *
     * @param bullet the bullet to create the transition for
     * @param enemyLocation the location of the enemy
     * @param pane the pane to add the animation to
     */
    protected void createTransition(final Node bullet, final Point2D enemyLocation, final AnchorPane pane) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(BULLET_SPEED), bullet);
        double enemyX = enemyLocation.getX();
        double enemyY = enemyLocation.getY();
        tt.setToX(enemyX);
        tt.setToY(enemyY);
        tt.setCycleCount(1);
        tt.setOnFinished(finishedEvent -> {
            pane.getChildren().remove(bullet);
            this.hasTarget = false;
        });
        tt.play();
    }
    /**
     * Gets the attack of the tower.
     *
     * @return the attack of the tower
     */
    public int getAttack() {
        return attack;
    }
    /**
     * Gets the cost of the tower.
     *
     * @return the cost of the tower
     */
    public int getCost() {
        return cost;
    }
    /**
     * Gets the range of the tower.
     *
     * @return the range of the tower
     */
    public int getRange() {
        return range;
    }
    /**
     * Gets whether the tower has a target.
     *
     * @return whether the tower has a target
     */
    public boolean getHasTarget() {
        return hasTarget;
    }
    /**
     * Sets whether the tower has a target.
     *
     * @param hasTarget whether the tower has a target
     */
    public void setHasTarget(final boolean hasTarget) {
        this.hasTarget = hasTarget;
    }
}
