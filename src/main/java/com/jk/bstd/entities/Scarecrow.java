package com.jk.bstd.entities;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Scarecrow extends Tower {
    public static final int ATTACK = 10;
    public static final int ATTACK_SPEED = 3;
    public static final int RANGE = 3;
    public static final int COST = 20;

    public Scarecrow(Point point) {
        super(point, ATTACK, ATTACK_SPEED, RANGE, COST);
    }

    @Override
    public void attackAnimation(Point2D enemyLocation, AnchorPane pane) {
        //TODO: add projectile image to the game remove temp rectangle
        Circle bullet = new Circle(4, Color.RED);
        bullet.setTranslateX(super.getPoint().getRealX());
        bullet.setTranslateY(super.getPoint().getRealY());
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
