package com.jk.bstd.ui;

import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;

import java.util.Objects;

public class View {
    private static final int HEIGHT = 720;
    private static final int WIDTH = 1280;
    private final AnchorPane mainPane;
    private final Scene mainScene;
    private final Stage mainStage;

    public View() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        mainStage.setTitle("Barnyard Shenanigans");
        mainStage.setResizable(false);

        Image logo = new Image(Objects.requireNonNull(getClass().getResource("/images/heart.png")).toExternalForm(), 252, 68, false, true);
        mainStage.getIcons().add(logo);

        Image cursor = new Image(Objects.requireNonNull(getClass().getResource("/images/cursor.png")).toExternalForm(), 32, 32, false, true);
        mainScene.setCursor(new ImageCursor(cursor));
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public AnchorPane getMainPane() {
        return mainPane;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void createBackground(String imgPath) {
        Image bgImg = new Image(Objects.requireNonNull(getClass().getResource("/images/" + imgPath)).toExternalForm());
        BackgroundImage bgImgObj = new BackgroundImage(bgImg, null, null, null, null);
        mainPane.setBackground(new Background(bgImgObj));
    }
}
