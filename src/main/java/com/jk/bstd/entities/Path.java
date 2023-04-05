package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Path {
    private Point point;

    private final Image img;
    private final ImageView imgView;

    public Path(int x, int y) {
        point = new Point(x, y);
        img = new Image(Objects.requireNonNull(getClass()
                .getResource("/images/gameScreen/Path.png")).toExternalForm());
        imgView = new ImageView(img);
    }

    public Point getPoints() {
        return point;
    }

    public void setPoints(int x, int y) {
        this.point.setX(x);
        this.point.setY(y);
    }
}
