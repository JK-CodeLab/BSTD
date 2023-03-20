package com.jk.bstd.components;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public class MainMenuButton extends Button {
    private static final String STYLE = "-fx-background-color: transparent;";

    private final String imgName;
    private Image btnImg;
    private Image btnImgHover;

    public MainMenuButton(String imgName) {
        this.imgName = imgName;
        setImagePath();
        setGraphic(new ImageView(btnImg));
        setStyle(STYLE);
        setPrefWidth(252);
        setPrefHeight(68);
        initializeButtonListeners();
    }

    private void setImagePath() {
        btnImg = new Image(getClass().getResource("/images/mainMenu/" + imgName + ".png").toExternalForm());
        btnImgHover = new Image(getClass().getResource("/images/mainMenu/" + imgName + "Pressed.png").toExternalForm());
    }

    private void setButtonPressedStyle() {
        setGraphic(new ImageView(btnImgHover));
        setStyle(STYLE);
        setLayoutY(getLayoutY() + 8);
    }

    private void setIdleButtonStyle() {
        setGraphic(new ImageView(btnImg));
        setStyle(STYLE);
        setLayoutY(getLayoutY() - 8);
    }

    private void initializeButtonListeners() {
        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setIdleButtonStyle();
            }
        });

        setOnMouseEntered(event -> {
            setEffect(new DropShadow());
        });

        setOnMouseExited(event -> {
            setEffect(null);
        });
    }
}
