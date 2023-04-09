package com.jk.bstd.components;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Class that represents a button in the game menu.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class GameMenuButton extends Button {
    private static final int PREF_WIDTH = 64;
    private static final int PREF_HEIGHT = 72;
    private static final int BUTTON_Y_OFFSET = 8;
    private static final String STYLE = "-fx-background-color: transparent;";
    private final String imgName;
    private Image btnImg;
    private Image btnPressedImg;

    /**
     * Constructor for a new game menu button.
     *
     * @param imgName the name of the image
     */
    public GameMenuButton(final String imgName) {
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
                Objects.requireNonNull(
                        getClass().getResource("/images/gameButtons/" + imgName + ".png")
                ).toExternalForm()
        );
        btnPressedImg = new Image(
                Objects.requireNonNull(getClass().getResource("/images/gameButtons/" + imgName + "Pressed.png")
        ).toExternalForm()
        );
    }
    /**
     * Sets the button to the pressed style.
     */
    public void setButtonPressedStyle() {
        setGraphic(new ImageView(btnPressedImg));
        setStyle(STYLE);
        setLayoutY(getLayoutY() + BUTTON_Y_OFFSET);
    }
    /**
     * Sets the button to the idle style.
     */
    public void setIdleButtonStyle() {
        setGraphic(new ImageView(btnImg));
        setStyle(STYLE);
        setLayoutY(getLayoutY() - BUTTON_Y_OFFSET);
    }
    /**
     * Initializes the button listeners.
     */
    private void initializeButtonListeners() {
        if (!imgName.equals("sellBtn")) {
            setOnMousePressed(event -> setButtonPressedStyle());

            setOnMouseReleased(event -> setIdleButtonStyle());
        }

        setOnMouseEntered(event -> setEffect(new DropShadow()));

        setOnMouseExited(event -> setEffect(null));
    }
}
