package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Tile extends Entity{
    private final Image img;
    private final ImageView imgView;

    public int cost = 5;


    public Tile(Point point) {
        super(point);
        img = new Image(Objects.requireNonNull(getClass()
                .getResource("/images/gameScreen/Path.png")).toExternalForm());
        imgView = new ImageView(img);
    }

    @Override
    public ImageView getImgView() {
        int row = super.getX();
        int col = super.getY();

        int x = col * 64;
        int y = (row + 3) * 64;

        imgView.setLayoutX(x);
        imgView.setLayoutY(y);
        return imgView;
    }

    public Point getPoint() {
        return super.getPoint();
    }

    @Override
    public int getCost() {
        return cost;
    }

}
