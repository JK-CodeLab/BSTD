package com.jk.bstd.ui;

import com.jk.bstd.Player;
import com.jk.bstd.components.MainMenuButton;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static com.jk.bstd.LoadGame.*;

public class MainMenuView {

    private static final int HEIGHT = 832;
    private static final int WIDTH = 1280;
    private static final int BTN_START_X = 500;
    private static final int BTN_START_Y = 250;

    private final AnchorPane mainPane;
    private final Scene mainScene;
    private final Stage mainStage;
    private List<MainMenuButton> menuButtons;

    public MainMenuView() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        menuButtons = new ArrayList<>();

        Image logo = new Image(getClass().getResource("/images/heart.png").toExternalForm(), 252, 68, false, true);
        mainStage.getIcons().add(logo);
        mainStage.setTitle("Barnyard Shenanigans");

        Image cursor = new Image(getClass().getResource("/images/cursor.png").toExternalForm(), 32, 32, false, true);
        mainScene.setCursor(new ImageCursor(cursor));

        mainStage.setResizable(false);

        createButtons();
        createBackground();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addButtons(MainMenuButton btn) {
        btn.setLayoutX(BTN_START_X);
        btn.setLayoutY(BTN_START_Y + menuButtons.size() * 100);
        menuButtons.add(btn);
        mainPane.getChildren().add(btn);
    }

    private void createButtons() {
        MainMenuButton newGame = new MainMenuButton("newGameBtn");
        MainMenuButton loadGame = new MainMenuButton("loadGameBtn");
        MainMenuButton exit = new MainMenuButton("exitBtn");

        addButtons(newGame);
        addButtons(loadGame);
        addButtons(exit);

        newGame.setOnAction(event -> {
            GameView gameView = new GameView(false);
            gameView.createNewGame(mainStage);
        });

        loadGame.setOnAction(event -> {
            File f = new File("src/main/resources/save.json");
            if (f.exists()) {
                GameView gameView = new GameView(true);
                gameView.createNewGame(mainStage);
            } else {
                // TODO: add popup
                System.out.println("Does not exist");
            }
        });

        exit.setOnAction(event -> {
            mainStage.close();
        });
    }

    private void createBackground() {
        Image bgImg = new Image(getClass().getResource("/images/mainMenu/mainMenuBg.png").toExternalForm(), WIDTH, HEIGHT, false, true);
        BackgroundImage bg = new BackgroundImage(bgImg, null, null, null, null);
        mainPane.setBackground(new Background(bg));
    }
}
