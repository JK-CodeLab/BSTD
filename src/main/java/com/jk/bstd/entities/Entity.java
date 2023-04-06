package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Entity {
    private Point point;
    private String name;
    private Image img;
    private ImageView imgView;

    public Entity(Point point) {
        this.point = point;
        this.name = this.getClass().getSimpleName();
        this.img = new Image(this.getClass().getResource("/images/gameScreen/" + this.name + ".png").toExternalForm());
        this.imgView = new ImageView(img);
    }

    public ImageView getImgView() {
        int pointX = point.getX();
        int pointY = point.getY();
        int x, y;

        if (name.equals("Tile") || name.equals("Sprinkler")) {
             x = (pointX + 1) * 64;
             y = (pointY + 4) * 64;
        } else {
             x = (pointX + 1) * 64;
             y = (pointY + 4) * 64 - 64;
        }

        imgView.setLayoutX(x);
        imgView.setLayoutY(y);
        return imgView;
    }
    public String getName() {
        return this.name;
    }

    public Point getPoint() {
        return this.point;
    };
    public void setPoint(Point point) {
        this.point = point;
    }
    public int getX() {
        return this.point.getX();
    }
    public int getCost() {
        return 0;
    }
    public int getY() {
        return this.point.getY();
    }
    public void setX(int x) {
        this.point.setX(x);
    }
    public void setY(int y) {
        this.point.setY(y);
    }
}
