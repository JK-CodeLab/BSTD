package com.jk.bstd.entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Farmer extends Tower {
    public static final int ATTACK = 15;
    public static final int ATTACK_SPEED = 5;
    public static final int RANGE = 2;
    public static final int COST = 20;

//    private final Image img;
//    private final ImageView imgView;

    //TODO: change the point
    public Farmer(Point point) {
        super(point, ATTACK, ATTACK_SPEED, RANGE, COST);
//        img = new Image(Objects.requireNonNull(getClass()
//                .getResource("/images/gameScreen/Farmer.png")).toExternalForm());
//        imgView = new ImageView(img);
    }

//    public ImageView getImgView() {
//        int row = super.getX();
//        int col = super.getY();
//
//        int x = (col + 1) * 64;
//        int y = (row + 4) * 64 - 64;
//
//        imgView.setLayoutX(x);
//        imgView.setLayoutY(y);
//        return imgView;
//    }

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
