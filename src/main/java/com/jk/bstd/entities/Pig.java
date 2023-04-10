package com.jk.bstd.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Pig extends Animal {
    private static final int HEALTH = 30;
    private static final int ATTACK = 10;
    private static final int GOLD_DROPPED = 15;
    private final Image img = new Image(
            Objects.requireNonNull(
                    getClass().getResource("/images/entities/Farmer.png")).toExternalForm());
    private final ImageView imgView;

    public Pig() {
        super(HEALTH, ATTACK, GOLD_DROPPED);
        Image img = new Image(Objects.requireNonNull(getClass()
                .getResource("/images/entities/Farmer.png")).toExternalForm());
        imgView = new ImageView(img);
    }

    public Image getImg(Point2D currPoint, Point2D nextPoint){
        return img;
    }


    @Override
    public ImageView getImgView() {
        return imgView;
    }
}
