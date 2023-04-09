package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Pig extends Animal {
    private static final int HEALTH = 30;
    private static final int ATTACK = 10;
    private static final int GOLD_DROPPED = 15;
    private final ImageView imgView;

    public Pig() {
        // TODO: Change spawn point
        // TODO: CHange image
        super(HEALTH, ATTACK, GOLD_DROPPED);
        Image img = new Image(Objects.requireNonNull(getClass()
                .getResource("/images/gameScreen/Farmer.png")).toExternalForm());
        imgView = new ImageView(img);
    }
    @Override
    public ImageView getImgView() {
        return imgView;
    }
}
