package com.jk.bstd.ui;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameView {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private AnchorPane gamePane;
    private Stage gameStage;
    private Scene gameScene;

    public GameView() {
        initializeStage();
        createMouseEvents();
    }

    private void createMouseEvents() {
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage mainStage) {
        mainStage.hide();
        gameStage.show();
    }

    public void loadGame(Stage mainStage) {
        mainStage.hide();
        gameStage.show();
    }
}
