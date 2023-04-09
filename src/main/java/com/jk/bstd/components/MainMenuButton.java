package com.jk.bstd.components;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import java.util.Objects;

/**
 * Class that represents a button in the main menu.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class MainMenuButton extends Button {
    private static final String STYLE = "-fx-background-color: transparent;";
    private static final int PREF_WIDTH = 252;
    private static final int PREF_HEIGHT = 68;
    private static final int BUTTON_Y_OFFSET = 8;
    private final String imgName;
    private Image btnImg;
    private Image btnImgHover;

    /**
     * Constructor for a new main menu button.
     *
     * @param imgName the name of the image
     */
    public MainMenuButton(final String imgName) {
        this.imgName = imgName;
        setImagePath();
        setGraphic(new ImageView(btnImg));
        setStyle(STYLE);
        setPrefWidth(PREF_WIDTH);
        setPrefHeight(PREF_HEIGHT);
        initializeButtonListeners();
    }

    private void setImagePath() {
        btnImg = new Image(
                Objects.requireNonNull(getClass().getResource("/images/mainMenu/" + imgName + ".png")
                ).toExternalForm()
        );
        btnImgHover = new Image(
                Objects.requireNonNull(getClass().getResource("/images/mainMenu/" + imgName + "Pressed.png")
                ).toExternalForm()
        );
    }

    private void setButtonPressedStyle() {
        setGraphic(new ImageView(btnImgHover));
        setStyle(STYLE);
        setLayoutY(getLayoutY() + BUTTON_Y_OFFSET);
    }

    private void setIdleButtonStyle() {
        setGraphic(new ImageView(btnImg));
        setStyle(STYLE);
        setLayoutY(getLayoutY() - BUTTON_Y_OFFSET);
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

        setOnMouseEntered(event -> setEffect(new DropShadow()));

        setOnMouseExited(event -> setEffect(null));
    }
}
