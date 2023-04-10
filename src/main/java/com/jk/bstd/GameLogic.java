package com.jk.bstd;

import com.jk.bstd.entities.Tower;
import com.jk.bstd.entities.Tile;
import com.jk.bstd.entities.Point;
import com.jk.bstd.entities.Chicken;
import com.jk.bstd.entities.Entity;
import com.jk.bstd.entities.Animal;
import com.jk.bstd.ui.GameGrid;
import com.jk.bstd.ui.MainMenuView;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class is used to create game logic functions.
 * It is a utility class and cannot be instantiated.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public final class GameLogic {
    private static final int ENEMY_PER_LEVEL = 15;
    private static final int GRID_SIZE = 64;
    private static final int STARTING_Y_OFFSET = 32;
    private static final int STARTING_X_OFFSET = 32;
    private static final int Y_OFFSET = 64;
    private static final double SPAWN_SPEED_MODIFIER = 1.5;
    private static final int ENEMY_SPAWN_OFFSET_X = 128;
    private static final int ENEMY_SPAWN_OFFSET_Y = 224;
    private static final String ERROR_POPUP_BG_PATH = "/images/gameButtons/errorPopupBg.png";
    private static final int LABEL_FONT_SIZE = 20;
    private static final int LABEL_LAYOUT_X = 480;
    private static final int LABEL_LAYOUT_Y = 730;
    private static final int LABEL_PREF_WIDTH = 320;
    private static final int LABEL_PREF_HEIGHT = 88;
    private static final int LABEL_PADDING_TOP = 10;
    private static final int LABEL_PADDING_RIGHT = 10;
    private static final int LABEL_PADDING_BOTTOM = 10;
    private static final int LABEL_PADDING_LEFT = 90;
    private static final Duration FADE_IN_DURATION = Duration.seconds(1);
    private static final Duration FADE_OUT_DURATION = Duration.seconds(2);
    private static final Duration FADE_OUT_DELAY = Duration.seconds(4);
    private static final int CLOSE_BTN_SIZE = 50;
    private static final int CLOSE_BTN_X = 135;
    private static final int CLOSE_BTN_Y = 15;
    private static final int BTN_WIDTH = 163;
    private static final int BTN_HEIGHT = 50;
    private static  final int FONT_SIZE = 25;
    private static final int VBOX_SPACING = 13;
    private static final int SCENE_WIDTH = 320;
    private static final int SCENE_HEIGHT = 256;

    private GameLogic() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate GameLogic");
    }

    /**
     * Creates a function for the play button.
     *
     * @param player the player
     * @param gameGrid the game grid
     * @param gridPane the grid pane
     * @param pane the anchor pane
     */
    public static void play(final Player player,
                            final GameGrid gameGrid,
                            final GridPane gridPane,
                            final AnchorPane pane) {
        int numEnemies = player.getLevel() * ENEMY_PER_LEVEL;

        Path path = createPath(gameGrid.getPlacedTiles(), gridPane);
        spawnEnemies(path, numEnemies, pane, gameGrid.getPlacedTowers(), player);
        player.setPlaying(true);
    }
    /**
     * Draws a path for the enemies to follow.
     *
     * @param tiles the tiles
     * @param gridPane the grid pane
     * @return the path
     */
    public static Path createPath(final ArrayList<Tile> tiles, final GridPane gridPane) {
        Path path = new Path();

        Point point = convertToGridPaneXAndY(tiles.get(0).getPoint(), gridPane);
        MoveTo start = new MoveTo(point.getX(), point.getY() - STARTING_Y_OFFSET);
        path.getElements().add(start);

        for (int i = 1; i < tiles.size(); i++) {
            point = convertToGridPaneXAndY(tiles.get(i).getPoint(), gridPane);
            LineTo line = new LineTo(point.getX(), point.getY());
            path.getElements().add(line);

            if (i == tiles.size() - 1) {
                LineTo line1 = new LineTo(point.getX(), point.getY() - Y_OFFSET);
                path.getElements().add(line1);
            }
        }
        return path;
    }

    private static void spawnEnemies(final Path path,
                                     final int numEnemies,
                                     final AnchorPane pane,
                                     final ArrayList<Tower> towers,
                                     final Player player) {
        double speed = SPAWN_SPEED_MODIFIER / player.getLevel();
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(speed), event -> {
            Chicken chicken = new Chicken();
            if (player.getIsAlive()) {
                spawnEnemy(pane, path, chicken, towers, player);
            } else {
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(numEnemies);
        timeline.play();
        timeline.statusProperty().addListener((observableValue, status, t1) -> {
            if (t1 == Animation.Status.STOPPED) {
                player.setPlaying(false);
            }
        });
        timeline.setOnFinished(event -> player.setPlaying(false));
    }
    private static void spawnEnemy(final AnchorPane pane,
                                   final Path path,
                                   final Animal animal,
                                   final ArrayList<Tower> towers,
                                   final Player player) {
        PathTransition pt = new PathTransition(Duration.seconds(path.getElements().size()), path);

        ImageView imgView = animal.getImgView();
        pt.setNode(imgView);
        pt.setRate(1);
        pt.setInterpolator(Interpolator.LINEAR);

        imgView.setTranslateX(ENEMY_SPAWN_OFFSET_X);
        imgView.setTranslateY(ENEMY_SPAWN_OFFSET_Y);
        pane.getChildren().add(imgView);

        final Point2D[] enemyCurrentLocation = {new Point2D(160, 256)};
        pt.currentTimeProperty().addListener((observableValue, duration, t1) -> {
            Point2D enemyLocation = new Point2D(
                    imgView.getTranslateX() + STARTING_X_OFFSET,
                    imgView.getTranslateY() + STARTING_Y_OFFSET
            );
            imgView.setImage(animal.getImg(enemyCurrentLocation[0], enemyLocation));
            enemyCurrentLocation[0] = enemyLocation;

            for (Tower tower : towers) {
//                int towerX = tower.getPoint().getRealX() + STARTING_X_OFFSET;
//                int towerY = tower.getPoint().getRealY() + STARTING_Y_OFFSET;
//                Point2D towerLocation = new Point2D(towerX, towerY);
                if (enemyCurrentLocation[0].distance(tower.getPoint().toPoint2D()) < tower.getRange()  * Y_OFFSET &&  !tower.getHasTarget()) {
                    tower.setHasTarget(true);
                    tower.attackAnimation(enemyCurrentLocation[0], pane);
                    animal.takeDamage(tower.getAttack());
                    if (animal.getIsDead()) {
                        pt.stop();
                        pane.getChildren().remove(imgView);
                        player.addMoney(animal.getGoldDropped());
                    }
                }
            }
            if (!player.getIsAlive()) {
                pt.stop();
                if (!player.isGameOver()) {
                    player.setGameOver(true);
                    createGameOverPopup(pane.getScene().getWindow());
                }
            }
        });
        pt.statusProperty().addListener((observableValue, status, t1) -> {
            if (t1 == Animation.Status.STOPPED && !animal.getIsDead() && player.getIsAlive()) {
                pane.getChildren().remove(imgView);
                player.takeDamage(animal.getAttack());
            }
        });
        pt.play();
    }

    private static Point convertToGridPaneXAndY(final Point point, final GridPane gridPane) {
        double offsetX = gridPane.getLayoutX(); // 64
        double offsetY = gridPane.getLayoutY(); // 256 = 64 * 4

        int pointX = point.getX(); // 1
        int pointY = point.getY(); // 0

        int newY = (int) (pointY * GRID_SIZE + offsetY + STARTING_Y_OFFSET); // 0 * 64 + 256 + 32 = 288
        int newX = (int) (pointX * GRID_SIZE + offsetX + STARTING_X_OFFSET); // 64 + 2 * 64 = 192

        return new Point(newX, newY);
    }
    /**
     * Sells an entity and adds the cost to the player's money.
     *
     * @param entity the entity to be sold
     * @param player the player selling the entity
     */
    public static void sellEntity(final Entity entity, final Player player) {
        int cost = entity.getCost();
        player.addMoney(cost);
    }

    /**
     * Buys an entity and removes the cost from the player's money.
     *
     * @param entity the entity to be bought
     * @param player the player buying the entity
     * @return true if the player has enough money to buy the entity, false otherwise
     */
    public static boolean buyEntity(final Entity entity, final Player player) {
        if (player.getMoney() - entity.getCost() >= 0) {
            int cost = entity.getCost();
            player.removeMoney(cost);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Checks if the last tile in the list is at the given position.
     *
     * @param tiles the list of tiles
     * @param x the x position to check
     * @param y the y position to check
     * @return true if the last tile in the list is at the given position, false otherwise
     */
    public static boolean checkLastTilePosition(final ArrayList<Tile> tiles, final int x, final int y) {
        if (tiles.size() == 0) {
            return false;
        }
        Tile lastTile = tiles.get(tiles.size() - 1);
        return lastTile.getPoint().getX() == x && lastTile.getPoint().getY() == y;
    }
    /**
     * Checks if the tiles in the given list of tiles are connected.
     *
     * @param tiles the list of tiles
     * @return true if the tiles in the given list of tiles are connected, false otherwise
     */
    public static boolean checkIfTilesAreConnected(final ArrayList<Tile> tiles) {
        if (tiles.size() < 2) {
            return false;
        }
        for (int i = 0; i < tiles.size() - 1; i++) {
            Tile tile = tiles.get(i);
            Tile otherTile = tiles.get(i + 1);
            if (!isConnectedTo(tile, otherTile)) {
                return false;
            }
        }
        return true;
    }
    private static boolean isConnectedTo(final Tile tile, final Tile otherTile) {
        int x = tile.getPoint().getX();
        int y = tile.getPoint().getY();
        int otherX = otherTile.getPoint().getX();
        int otherY = otherTile.getPoint().getY();
        return (x == otherX && (y == otherY + 1 || y == otherY - 1))
                || (y == otherY && (x == otherX + 1 || x == otherX - 1));
    }
    private static void  createFadeIn(final Label label) {
        FadeTransition fadeIn = new FadeTransition(FADE_IN_DURATION, label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
    private static void createFadeOut(final Label label) {
        FadeTransition fadeOut = new FadeTransition(FADE_OUT_DURATION, label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(FADE_OUT_DELAY);
        fadeOut.play();
    }
    /**
     * Creates an error popup with the given text.
     *
     * @param text the text to be displayed
     * @return the error popup label
     */
    public static Label createErrorPopup(final String text) {
        Image img = new Image(
                Objects.requireNonNull(
                        GameLogic.class.getResource(ERROR_POPUP_BG_PATH)
                ).toExternalForm()
        );
        BackgroundImage backgroundImage = new BackgroundImage(
                img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        Label label = new Label(text);
        label.setFont(new Font("Munro", LABEL_FONT_SIZE));
        label.setBackground(new Background(backgroundImage));

        label.setLayoutX(LABEL_LAYOUT_X);
        label.setLayoutY(LABEL_LAYOUT_Y);
        label.setPrefWidth(LABEL_PREF_WIDTH);
        label.setPrefHeight(LABEL_PREF_HEIGHT);

        label.setPadding(new Insets(LABEL_PADDING_TOP, LABEL_PADDING_RIGHT, LABEL_PADDING_BOTTOM, LABEL_PADDING_LEFT));
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER_LEFT);
        createFadeIn(label);
        createFadeOut(label);
        return label;
    }
    private static Button createCloseButton() {
        Button btn = new Button();
        btn.setPrefSize(CLOSE_BTN_SIZE, CLOSE_BTN_SIZE);
        btn.setTranslateX(CLOSE_BTN_X);
        btn.setTranslateY(CLOSE_BTN_Y);
        return btn;
    }
    /**
     * Creates a button with the given name.
     *
     * @param btnName the name of the button
     * @return the button
     */
    public static Button getButton(final String btnName) {
        Image btnBg;
        Button btn;
        if (btnName.equals("close")) {
            btnBg = new Image(Objects.requireNonNull(
                    GameLogic.class.getResource("/images/gameButtons/closeBtn.png")).toExternalForm()
            );
            btn = createCloseButton();
        } else {
            btnBg = new Image(Objects.requireNonNull(
                    GameLogic.class.getResource("/images/gameButtons/btnBg.png")).toExternalForm()
            );
            btn = new Button(btnName);
            btn.setPrefSize(BTN_WIDTH, BTN_HEIGHT);
        }
        BackgroundImage backgroundImage = new BackgroundImage(
                btnBg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        Background background = new Background(backgroundImage);

        btn.setBackground(background);
        btn.setAlignment(Pos.TOP_CENTER);
        btn.setFont(Font.font("Munro", FONT_SIZE));
        btn.setOnMouseEntered(event -> btn.setEffect(new DropShadow()));
        btn.setOnMouseExited(event -> btn.setEffect(null));

        return btn;
    }
    /**
     * Creates a popup window for game over.
     *
     * @param gameWindow the game window
     */
    public static void createGameOverPopup(final Window gameWindow) {
        Stage popupWindow = new Stage();
        Stage mainStage = new MainMenuView().getMainStage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setAlwaysOnTop(true);
        popupWindow.setResizable(false);
        popupWindow.initStyle(StageStyle.TRANSPARENT);

        Button returnBtn = getButton("back home");
        Button quitBtn = getButton("quit game");

        returnBtn.setOnAction(e -> {
            popupWindow.close();
            gameWindow.hide();
            mainStage.show();

        });
        quitBtn.setOnAction(e -> {
            popupWindow.close();
            System.exit(0);
        });

        Image bgImg = new Image(Objects.requireNonNull(
                GameLogic.class.getResourceAsStream("/images/gameButtons/gameOverPopupBg.png"))
        );
        BackgroundImage bg = new BackgroundImage(
                bgImg, null, null, null, null);

        VBox layout = new VBox(VBOX_SPACING);
        layout.setBackground(new Background(bg));
        layout.getChildren().addAll(returnBtn, quitBtn);
        layout.setAlignment(Pos.CENTER);
        showPopUp(popupWindow, gameWindow, layout);
    }
    private static void showPopUp(final Stage popupWindow, final Window gameWindow, final VBox layout) {
        Scene scene = new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        popupWindow.setX(gameWindow.getX() + (gameWindow.getWidth() / 2) - (SCENE_WIDTH / 2.0));
        popupWindow.setY(gameWindow.getY() + (gameWindow.getHeight() / 2) - (SCENE_HEIGHT / 2.0));
        popupWindow.setScene(scene);
        popupWindow.show();
    }
}
