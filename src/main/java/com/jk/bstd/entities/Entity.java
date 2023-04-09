package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * An entity class to represent each entity in the game.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Entity {
    private static final int OFFSET = 64;
    private static final int OFFSET_X = 1;
    private static final int OFFSET_Y = 4;
    private Point point;
    private final String name;
    private final ImageView imgView;

    /**
     * Constructor for Entity.
     *
     * @param point the location of the entity
     */
    public Entity(final Point point) {
        this.point = point;
        this.name = this.getClass().getSimpleName();
        Image img = new Image(
                Objects.requireNonNull(
                        this.getClass().getResource("/images/gameScreen/" + this.name + ".png")
                ).toExternalForm()
        );
        this.imgView = new ImageView(img);
    }
    /**
     * Returns the ImageView of the entity.
     *
     * @return the ImageView of the entity
     */
    public ImageView getImgView() {
        int pointX = point.getX();
        int pointY = point.getY();
        int x;
        int y;

        if (name.equals("Tile") || name.equals("Sprinkler")) {
             x = (pointX + OFFSET_X) * OFFSET;
             y = (pointY + OFFSET_Y) * OFFSET;
        } else {
             x = (pointX + OFFSET_X) * OFFSET;
             y = (pointY + OFFSET_Y) * OFFSET - OFFSET;
        }

        imgView.setLayoutX(x);
        imgView.setLayoutY(y);
        return imgView;
    }
    /**
     * Returns the name of the entity.
     *
     * @return the name of the entity
     */
    public String getName() {
        return this.name;
    }
    /**
     * Returns the point of the entity.
     *
     * @return the point of the entity
     */
    public Point getPoint() {
        return this.point;
    }
    /**
     * Sets the point of the entity.
     *
     * @param point the point of the entity
     */
    public void setPoint(final Point point) {
        this.point = point;
    }
    /**
     * Returns the x coordinate of the entity.
     *
     * @return the x coordinate of the entity
     */
    public int getX() {
        return this.point.getX();
    }
    /**
     * Returns the y coordinate of the entity.
     *
     * @return the y coordinate of the entity
     */
    public int getY() {
        return this.point.getY();
    }
    /**
     * Returns the cost of the entity.
     *
     * @return the cost of the entity
     */
    public int getCost() {
        return 0;
    }
}
