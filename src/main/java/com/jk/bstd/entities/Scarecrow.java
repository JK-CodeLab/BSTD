package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Scarecrow extends Tower {
    public static final int ATTACK = 5;
    public static final int ATTACK_SPEED = 3;
    public static final int RANGE = 3;
    public static final int COST = 10;

    private final Image img;
    private final ImageView imgView;

    //TODO: change the point
    public Scarecrow(Point point) {
        super(point, ATTACK, ATTACK_SPEED, RANGE, COST);
        img = new Image(Objects.requireNonNull(getClass()
                .getResource("/images/gameScreen/Scarecrow.png")).toExternalForm());
        imgView = new ImageView(img);
    }

    public ImageView getImgView() {
        int row = super.getX();
        int col = super.getY();

        int x = col * 64;
        int y = (row + 3) * 64 - 64;

        imgView.setLayoutX(x);
        imgView.setLayoutY(y);
        return imgView;
    }

    public int getCost() {
        return COST;
    }

    @Override
    void attack() {
        return;
    }

    @Override
    void upgrade() {
        return;
    }

    @Override
    Entity selectTarget() {
        return null;
    }
}
