package com.jk.bstd.entities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class Tower extends Entity{
    int attack;
    int attackSpeed;
    int range;
    int cost;
    boolean hasTarget = false;

    // TODO: change the point
    public Tower(Point point, int attack, int attackSpeed, int range, int cost) {
        super(point);
        this.attack = attack;
        this.attackSpeed = attackSpeed;
        this.range = range;
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return cost;
    }
    public int getRange() {
        return range;
    }
    public void attackAnimation(Point2D enemyLocation, AnchorPane pane) {
        //TODO: add projectile image to the game remove temp rectangle
        Rectangle bullet = new Rectangle(8, 8, Color.YELLOW);
        bullet.setTranslateX(super.getPoint().getRealX() + 32);
        bullet.setTranslateY(super.getPoint().getRealY() + 32);
        pane.getChildren().add(bullet);
        TranslateTransition tt = new TranslateTransition(Duration.millis(150), bullet);
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
    public int getAttack() {
        return attack;
    }
    public boolean getHasTarget() {
        return hasTarget;
    }
    public void setHasTarget(boolean hasTarget) {
        this.hasTarget = hasTarget;
    }
    abstract void upgrade();
    abstract Entity selectTarget();
}
