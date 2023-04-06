package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Pig extends Animal {
    public static final int HEALTH = 30;
    public static final int SPEED = 4;
    public static final int ATTACK = 10;
    public static final int GOLD_DROPPED = 15;
    public ImageView imgView;
    public Image img;

    public Pig() {
        // TODO: Change spawn point
        // TODO: CHange image
        super(HEALTH, SPEED, ATTACK, GOLD_DROPPED);
        img = new Image(Objects.requireNonNull(getClass()
                .getResource("/images/gameScreen/Farmer.png")).toExternalForm());
        imgView = new ImageView(img);
    }

    // TODO: Fix get imgview
    @Override
    public ImageView getImgView() {
        return imgView;
    }
}
