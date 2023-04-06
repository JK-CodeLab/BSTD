package com.jk.bstd.components;

import com.jk.bstd.Player;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameMenuButton extends Button {
    private static final String STYLE = "-fx-background-color: transparent;";
    private final String imgName;
    private Image btnImg;
    private Image btnPressedImg;
    private boolean selling = false;

    public GameMenuButton(String imgName) {
        this.imgName = imgName;
        setImagePath();
        setGraphic(new ImageView(btnImg));
        setStyle(STYLE);
        setPrefWidth(64);
        setPrefHeight(72);
        initializeButtonListeners();
    }

    public boolean isSelling() {
        return selling;
    }

    private void setImagePath() {
        btnImg = new Image(getClass().getResource("/images/gameButtons/" + imgName + ".png").toExternalForm());
        btnPressedImg = new Image(getClass().getResource("/images/gameButtons/" + imgName + "Pressed.png").toExternalForm());
    }

    private void setButtonPressedStyle() {
        setGraphic(new ImageView(btnPressedImg));
        setStyle(STYLE);
        setLayoutY(getLayoutY() + 8);
    }

    private void setIdleButtonStyle() {
        setGraphic(new ImageView(btnImg));
        setStyle(STYLE);
        setLayoutY(getLayoutY() - 8);
    }

    private void initializeButtonListeners() {
        if (imgName.equals("sellBtn")) {
            setOnMousePressed(event -> {
                if (selling) {
                    setIdleButtonStyle();
                    selling = false;
                } else {
                    setButtonPressedStyle();
                    selling = true;
                }
            });
        } else {
            setOnMousePressed(event -> {
                setButtonPressedStyle();
            });

            setOnMouseReleased(event -> {
                setIdleButtonStyle();
            });
        }

        setOnMouseEntered(event -> {
            setEffect(new DropShadow());
        });

        setOnMouseExited(event -> {
            setEffect(null);
        });
    }

//    public void sell(Player player) {
//        player.setSelling(selling);
//    }

}
