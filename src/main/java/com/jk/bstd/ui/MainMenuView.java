package com.jk.bstd.ui;

import com.jk.bstd.components.MainMenuButton;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class to represent the main menu view of the game.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class MainMenuView {
    private static final int HEIGHT = 832;
    private static final int WIDTH = 1280;
    private static final int BTN_START_X = 500;
    private static final int BTN_START_Y = 250;
    private static final int LOGO_HEIGHT = 68;
    private static final int LOGO_WIDTH = 252;
    private static final int CURSOR_DIM = 32;
    private static final int BUTTON_SIZE_MODIFIER = 100;
    private final AnchorPane mainPane;
    private final Stage mainStage;
    private List<MainMenuButton> menuButtons;

    /**
     * Constructor for MainMenuView.
     */
    public MainMenuView() {
        mainPane = new AnchorPane();
        Scene mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        menuButtons = new ArrayList<>();

        Image logo = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/images/heart.png")
                ).toExternalForm(), LOGO_WIDTH, LOGO_HEIGHT, false, true
        );
        mainStage.getIcons().add(logo);
        mainStage.setTitle("Barnyard Shenanigans");

        Image cursor = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/images/cursor.png")
                ).toExternalForm(), CURSOR_DIM, CURSOR_DIM, false, true
        );
        mainScene.setCursor(new ImageCursor(cursor));

        mainStage.setResizable(false);

        createButtons();
        createBackground();
    }
    /**
     * Returns the main stage.
     *
     * @return the main stage
     */
    public Stage getMainStage() {
        return mainStage;
    }

    /**
     * Creates the background for the main menu.
     *
     * @param btn the button to be added
     */
    private void addButtons(final MainMenuButton btn) {
        btn.setLayoutX(BTN_START_X);
        btn.setLayoutY(BTN_START_Y + menuButtons.size() * BUTTON_SIZE_MODIFIER);
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
                System.out.println("Does not exist");
            }
        });

        exit.setOnAction(event -> mainStage.close());
    }

    private void createBackground() {
        Image bgImg = new Image(
                Objects.requireNonNull(getClass().getResource("/images/mainMenu/mainMenuBg.png")
                ).toExternalForm(), WIDTH, HEIGHT, false, true
        );
        BackgroundImage bg = new BackgroundImage(
                bgImg,
                null,
                null,
                null,
                null
        );
        mainPane.setBackground(new Background(bg));
    }
}
