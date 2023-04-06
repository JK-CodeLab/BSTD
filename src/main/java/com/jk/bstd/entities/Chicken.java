package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Chicken extends Animal {
    public static final int HEALTH = 30;
    public static final int SPEED = 4;
    public static final int ATTACK = 10;
    public static final int GOLD_DROPPED = 15;

    public Chicken() {
        // TODO: Change spawn point
        // TODO: Change image
        super(HEALTH, SPEED, ATTACK, GOLD_DROPPED);
    }
}
