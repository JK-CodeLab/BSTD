package com.jk.bstd.entities;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Farmer extends Tower {
    public static final int ATTACK = 20;
    public static final int ATTACK_SPEED = 5;
    public static final int RANGE = 5;
    public static final int COST = 50;

    public Farmer(Point point) {
        super(point, ATTACK, ATTACK_SPEED, RANGE, COST);
    }

    @Override
    public void attackAnimation(Point2D enemyLocation, AnchorPane pane) {
        //TODO: add projectile image to the game remove temp rectangle
        Rectangle bullet = new Rectangle(8, 4, Color.BLACK);
        bullet.setTranslateX(super.getPoint().getRealX() + 10);
        bullet.setTranslateY(super.getPoint().getRealY() + 7);
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

    public int getCost() {
        return COST;
    }

    @Override
    void upgrade() {
        return;
    }

    @Override
    Entity selectTarget() {
        return null;
    }
}
