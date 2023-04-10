package com.jk.bstd.entities;

import com.jk.bstd.GameLogic;
import javafx.geometry.Point2D;
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
    private static final int HEALTH = 200;
    private static final int ATTACK = 10;
    private static final int GOLD_DROPPED = 1;
    private final ImageView imgView;
    private final Image chickenRight = new Image(
            Objects.requireNonNull(
                    getClass().getResource("/images/entities/ChickenRight.png")).toExternalForm());

    private final Image chickenLeft = new Image(
            Objects.requireNonNull(
                    getClass().getResource("/images/entities/ChickenLeft.png")).toExternalForm());
    private final Image chickenUp = new Image(
            Objects.requireNonNull(
                    getClass().getResource("/images/entities/ChickenUp.png")).toExternalForm());
    private final Image chickenDown = new Image(
            Objects.requireNonNull(
                    getClass().getResource("/images/entities/ChickenDown.png")).toExternalForm());

    /**
     * Constructor for Chicken.
     */
    public Chicken() {
        super(HEALTH, ATTACK, GOLD_DROPPED);
        imgView = new ImageView(chickenDown);
    }

    /**
     * Returns an image of the chicken based on the direction it is moving.
     *
     * @param currPoint the current point of the chicken
     * @param nextPoint the next point of the chicken
     * @return image of the chicken
     */
    public Image getImg(Point2D currPoint, Point2D nextPoint) {
        if (currPoint.getX() < nextPoint.getX()) {
            return chickenRight;
        } else if (currPoint.getX() > nextPoint.getX()) {
            return chickenLeft;
        } else if (currPoint.getY() < nextPoint.getY()) {
            return chickenDown;
        } else {
            return chickenUp;
        }
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
