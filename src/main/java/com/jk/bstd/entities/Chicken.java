package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Chicken extends Animal {
    public static final int HEALTH = 200;
    public static final int SPEED = 4;
    public static final int ATTACK = 10;
    public static final int GOLD_DROPPED = 1;
    public ImageView imgView;
    public Image img;

    public Chicken() {
        // TODO: Change spawn point
        // TODO: Change image
        super(HEALTH, SPEED, ATTACK, GOLD_DROPPED);
        img = new Image(Objects.requireNonNull(getClass()
                .getResource("/images/entities/Chicken.png")).toExternalForm());
        imgView = new ImageView(img);
    }
    // TODO: Implement move method
    // chicken should move 1 space per 1 unit of time
    @Override
    public void move() {}
    // TODO: fix get imgview
    @Override
    public ImageView getImgView() {
        return imgView;
    }
}
