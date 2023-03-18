package com.jk.bstd.components;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
        btnImgHover = new Image(getClass().getResource("/images/mainMenu/" + imgName + "Hover.png").toExternalForm());
    }

    public void setButtonPressedStyle() {
        setGraphic(new ImageView(btnImgHover));
        setStyle(STYLE);
    }

    public void setIdleButtonStyle() {
        setGraphic(new ImageView(btnImg));
        setStyle(STYLE);
    }

    private void initializeButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setIdleButtonStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}
