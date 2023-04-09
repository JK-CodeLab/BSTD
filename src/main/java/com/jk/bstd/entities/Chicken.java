package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
/**
 * Chicken class.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Chicken extends Animal {
    /**
     * Health of the chicken.
     */
    public static final int HEALTH = 200;
    /**
     * Attack of the chicken.
     */
    public static final int ATTACK = 10;
    /**
     * Gold dropped by the chicken.
     */
    public static final int GOLD_DROPPED = 1;
    /**
     * ImageView of the chicken.
     */
    public final ImageView imgView;
    /**
     * Image of the chicken.
     */
    public final Image img;
    /**
     * Constructor for Chicken.
     */
    public Chicken() {
        super(HEALTH, ATTACK, GOLD_DROPPED);
        img = new Image(Objects.requireNonNull(getClass()
                .getResource("/images/entities/Chicken.png")).toExternalForm());
        imgView = new ImageView(img);
    }
    /**
     * Returns the ImageView of the chicken.
     *
     * @return the ImageView of the chicken
     */
    @Override
    public ImageView getImgView() {
        return imgView;
    }
}
