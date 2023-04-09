package com.jk.bstd;

import com.jk.bstd.ui.MainMenuView;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main class that starts the game.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class Main extends Application {
    private static final int DEFAULT_FONT_SIZE = 24;
    /**
     * Starts the game.
     *
     * @param stage the stage to start the game on
     */
    @Override
    public void start(Stage stage) {
        Font.loadFont(getClass().getResourceAsStream("/fonts/munro.ttf"), DEFAULT_FONT_SIZE);
        Font.loadFont(getClass().getResourceAsStream("/fonts/dogicapixel.ttf"), DEFAULT_FONT_SIZE);
        MainMenuView mainMenuView = new MainMenuView();
        stage = mainMenuView.getMainStage();
        stage.show();
    }

    /**
     * Main method that launches the game.
     *
     * @param args the arguments passed in
     */
    public static void main(final String[] args) {
        launch();
    }
}
