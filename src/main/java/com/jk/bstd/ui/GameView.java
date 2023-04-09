package com.jk.bstd.ui;

import com.jk.bstd.Player;
import com.jk.bstd.components.GameMenuButton;
import com.jk.bstd.components.ShopButton;
import com.jk.bstd.entities.Point;
import com.jk.bstd.entities.Tile;
import com.jk.bstd.entities.Entity;
import com.jk.bstd.entities.Sprinkler;
import com.jk.bstd.entities.Farmer;
import com.jk.bstd.entities.Scarecrow;
import com.jk.bstd.entities.Dog;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.jk.bstd.GameLogic;
import javafx.stage.StageStyle;
import org.json.simple.JSONObject;
import static com.jk.bstd.LoadGame.readSave;
import static com.jk.bstd.LoadGame.loadPlayer;
import static com.jk.bstd.LoadGame.loadTowers;
import static com.jk.bstd.LoadGame.loadTiles;
import static com.jk.bstd.SaveGame.saveGame;
/**
 * A class to represent the game view of the game.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class GameView extends View {
    private static final int SHOP_BTN_START_X = 1018;
    private static final int SHOP_BTN_START_Y = 128;
    private static final int PLAYER_STATS_X = 305;
    private static final int PLAYER_STATS_Y = 0;
    private static final int SHOP_VIEW_X = 970;
    private static final int SHOP_VIEW_Y = 60;
    private static final int SHOP_BUTTON_SIZE_MODIFIER = 126;
    private static final int MENU_BUTTON_X = 350;
    private static final int MENU_BUTTON_SIZE_MODIFIER = 130;
    private static final int MENU_BUTTON_Y = 105;
    private static final int TILE_X = 12;
    private static final int LAYOUT_VBOX = 5;
    private static final int SCENE_X = 320;
    private static final int SCENE_Y = 256;
    private static final int MOUSE_OFFSET = 64;
    private static final int Y_OFFSET = 4;
    private Player player;
    private Stage gameStage;
    private final GameGrid gameGrid = new GameGrid();
    private final GridPane gridPane = gameGrid.getGridPane();
    private final List<ShopButton> shopButtons;
    private final Label moneyLabel;
    private final Label healthLabel;
    private final Label levelLabel;
    private final List<String> gameMenuButtons = Arrays.asList("menuBtn", "playBtn", "sellBtn");
    /**
     * Creates a new game view.
     *
     * @param loadGame whether to load a game or not
     */
    public GameView(final boolean loadGame) {
        super();
        shopButtons = new ArrayList<>();

        createBackground("gameScreen/gameBg.png");
        createShop();

        for (int i = 0; i < gameMenuButtons.size(); i++) {
            createMenuButtons(gameMenuButtons.get(i), i);
        }

        addGridPaneToMainPane();
        initializeGameGrid();
        addFirstTileToMainPane();
        if (loadGame) {
            JSONObject saveFile = readSave();
            if (saveFile != null) {
                this.player = loadPlayer(saveFile);
                gameGrid.setPlacedTowers(loadTowers(saveFile));
                gameGrid.setPlacedTiles(loadTiles(saveFile));
                drawTiles();
                drawTowers();
            }
        } else {
            this.player = new Player();
        }
        moneyLabel = player.createStatsLabel("money");
        healthLabel = player.createStatsLabel("health");
        levelLabel = player.createStatsLabel("level");
        displayPlayerStats();
    }
    /**
     * Creates a new game.
     *
     * @param mainMenu the main menu stage
     */
    public void createNewGame(final Stage mainMenu) {
        mainMenu.hide();
        gameStage = super.getMainStage();
        gameStage.show();
    }
    /**
     * Exits the game.
     */
    public void exitGame() {
        gameStage.close();
    }
    /**
     * Saves the game.
     */
    private void addGridPaneToMainPane() {
        super.addToMainPane(gridPane);
    }
    /**
     * Initializes the game grid.
     */
    private void displayPlayerStats() {
        Image statsBg = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/images/gameScreen/playerStatsBg.png")
                ).toExternalForm()
        );
        ImageView statsBgView = new ImageView(statsBg);
        statsBgView.setX(PLAYER_STATS_X);
        statsBgView.setY(PLAYER_STATS_Y);

        super.addToMainPane(statsBgView, moneyLabel, healthLabel, levelLabel);
    }
    /**
     * Creates the shop.
     */
    public void createShop() {
        Image shopImg = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/images/gameScreen/shopBg.png")
                ).toExternalForm()
        );
        ImageView shopView = new ImageView(shopImg);
        shopView.setX(SHOP_VIEW_X);
        shopView.setY(SHOP_VIEW_Y);
        super.addToMainPane(shopView);

        createShopButtons();
    }
    /**
     * Adds a shop button to the shop.
     *
     * @param shopBtn the shop button to add
     */
    public void addShopButtons(final ShopButton shopBtn) {
        shopBtn.setLayoutX(SHOP_BTN_START_X);
        shopBtn.setLayoutY(SHOP_BTN_START_Y + shopButtons.size() * SHOP_BUTTON_SIZE_MODIFIER);

        shopButtons.add(shopBtn);
        super.addToMainPane(shopBtn);
    }
    /**
     * Creates the shop buttons.
     */
    public void createShopButtons() {
        ShopButton shopPath = new ShopButton("shopTile");
        ShopButton shopSprinkler = new ShopButton("shopSprinkler");
        ShopButton shopScarecrow = new ShopButton("shopScarecrow");
        ShopButton shopDog = new ShopButton("shopDog");
        ShopButton shopFarmer = new ShopButton("shopFarmer");

        Tooltip tooltip = new Tooltip("Path");
        shopPath.setTooltip(tooltip);

        addShopButtons(shopPath);
        addShopButtons(shopSprinkler);
        addShopButtons(shopScarecrow);
        addShopButtons(shopDog);
        addShopButtons(shopFarmer);
    }

    private void createMenuButtons(final String btnName, final int offsetX) {
        GameMenuButton menuBtn = new GameMenuButton(btnName);
        menuBtn.setLayoutX(MENU_BUTTON_X + offsetX * MENU_BUTTON_SIZE_MODIFIER);
        menuBtn.setLayoutY(MENU_BUTTON_Y);
        super.addToMainPane(menuBtn);

        switch (btnName) {
            case "menuBtn" -> menuBtn.setOnMouseClicked(event -> createMenuPopup());
            case "playBtn" -> menuBtn.setOnMouseClicked(event -> {

                boolean tilesConnected = GameLogic.checkIfTilesAreConnected(gameGrid.getPlacedTiles());
                boolean lastTileCorrect = GameLogic.checkLastTilePosition(gameGrid.getPlacedTiles(), TILE_X, 0);

                if (!player.isAlive()) {
                    Label message = GameLogic.createErrorPopup("You are dead");
                    super.addToMainPane(message);
                } else if (!tilesConnected) {
                    Label message = GameLogic.createErrorPopup("Tiles are not connected");
                    super.addToMainPane(message);
                } else if (!lastTileCorrect) {
                    Label message = GameLogic.createErrorPopup("Last tile must reach the end");
                    super.addToMainPane(message);
                } else if (player.isPlaying()) {
                    Label message = GameLogic.createErrorPopup("You are already playing");
                    super.addToMainPane(message);
                } else {
                    player.setLevel(player.getLevel() + 1);
                    GameLogic.play(player, gameGrid, gridPane, super.getMainPane());
                }
            });
            case "sellBtn" -> menuBtn.setOnMouseClicked(event -> {
                System.out.println("selling" + player.isSelling());
                if (!player.isPlaying() && !player.isSelling()) {
                    menuBtn.setButtonPressedStyle();
                    player.setSelling(true);
                } else if (player.isSelling()) {
                    menuBtn.setIdleButtonStyle();
                    player.setSelling(false);
                } else {
                    Label message = GameLogic.createErrorPopup("cannot sell while playing");
                    super.addToMainPane(message);
                }
            });
            default -> {
            }
        }
    }
    private void createMenuPopup() {
        Stage popupWindow = new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setAlwaysOnTop(true);
        popupWindow.setResizable(false);
        popupWindow.initStyle(StageStyle.TRANSPARENT);

        Button close = GameLogic.getButton("close");
        Button saveBtn = GameLogic.getButton("save game");
        Button returnBtn = GameLogic.getButton("back home");
        Button quitGame = GameLogic.getButton("quit game");

        saveBtn.setOnAction(e -> {
            boolean saved = saveGame(player, gameGrid.getPlacedTowers(), gameGrid.getPlacedTiles());
            popupWindow.close();

            Label message;
            if (saved) {
                message = GameLogic.createErrorPopup("Game saved");
            } else {
                message = GameLogic.createErrorPopup("Error saving");
            }
            super.addToMainPane(message);
        });
        quitGame.setOnAction(e -> {
            exitGame();
            popupWindow.close();
        });
        returnBtn.setOnAction(e -> {
            popupWindow.close();
            gameStage.hide();
            MainMenuView mainMenu = new MainMenuView();
            mainMenu.getMainStage().show();
        });
        close.setOnAction(e -> popupWindow.close());

        Image bgImg = new Image(Objects.requireNonNull(
                getClass().getResource("/images/gameButtons/menuPopupBg.png")).toExternalForm());
        BackgroundImage bg = new BackgroundImage(
                bgImg, null, null, null, null);
        VBox layout = new VBox(LAYOUT_VBOX);
        layout.setBackground(new Background(bg));
        layout.getChildren().addAll(close, saveBtn, returnBtn, quitGame);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layout, SCENE_X, SCENE_Y);
        scene.setFill(Color.TRANSPARENT);

        // set the location of the popup window to the center of the game window
        popupWindow.setX(gameStage.getX() + (gameStage.getWidth() / 2) - (SCENE_X / 2.0));
        popupWindow.setY(gameStage.getY() + (gameStage.getHeight() / 2) - (SCENE_Y / 2.0));

        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    private void initializeGameGrid() {
        gridPane.setOnDragOver(this::acceptCopy);
        gridPane.setOnDragDropped(event -> {
            if (event.getGestureSource() instanceof ShopButton) {
                String item = event.getDragboard().getString();
                Point mousePoint = getPointFromMousePosition(event);

                boolean isEmpty = isGridEmpty(mousePoint);

                Entity placedEntity = null;
                if (isEmpty) {
                    if (item.equals("Tile")) {
                        boolean isAdjacent = isAdjacent(mousePoint);
                        if (isAdjacent) {
                            placedEntity = new Tile(mousePoint);
                        } else {
                            Label message = GameLogic.createErrorPopup("path must be placed adjacent to the last path");
                            super.addToMainPane(message);
                        }
                    } else if (item.equals("Sprinkler")) {
                        placedEntity = new Sprinkler(mousePoint);
                    } else if (item.equals("Scarecrow")) {
                        placedEntity = new Scarecrow(mousePoint);
                    } else if (item.equals("Farmer")) {
                        placedEntity = new Farmer(mousePoint);
                    } else if (item.equals("Dog")) {
                        placedEntity = new Dog(mousePoint);
                    }
                    if (placedEntity != null) {
                        ImageView entityImgView = placedEntity.getImgView();

                        boolean bought = GameLogic.buyEntity(placedEntity, player);
                        if (bought) {
                            gameGrid.addEntity(placedEntity);
                            super.addToMainPane(entityImgView);
                        } else {
                            Label message = GameLogic.createErrorPopup("Not enough money");
                            super.addToMainPane(message);
                        }

                        Entity finalPlacedEntity = placedEntity;
                        entityImgView.setOnMouseClicked(e -> onEntityClicked(e, finalPlacedEntity));
                    }
                } else {
                    Label message = GameLogic.createErrorPopup("something is already placed here");
                    super.addToMainPane(message);
                }
            }
            event.setDropCompleted(true);
            event.consume();
        });
    }

    private void printTileLocations() {
        System.out.println("Tile locations: ");
        for (Entity entity : gameGrid.getPlacedTiles()) {
            System.out.println("\tx: " + entity.getPoint().getX() + "\ty: " + entity.getPoint().getY());
        }
    }
    private void addFirstTileToMainPane() {
        // TODO: remove & uncomment below (testing)
        for (Entity entity : gameGrid.getPlacedTiles()) {
            entity.getImgView().setOnMouseClicked(e -> onEntityClicked(e, entity));
            super.addToMainPane(entity.getImgView());
        }
//        ImageView firstTile = gameGrid.getPlacedTiles().get(0).getImgView();
//        super.addToMainPane(firstTile);
    }
    private boolean isGridEmpty(final Point point) {
        int pointX = point.getX();
        int pointY = point.getY();
        for (Entity entity : gameGrid.getPlacedTiles()) {
            if (entity.getX() == pointX && entity.getY() == pointY) {
                return false;
            }
        }
        for (Entity entity : gameGrid.getPlacedTowers()) {
            if (entity.getX() == pointX && entity.getY() == pointY) {
                return false;
            }
        }
        return true;
    }
    private boolean isAdjacent(final Point currentPosition) {
        int indexLastTile = gameGrid.getPlacedTiles().size() - 1;
        Point lastPoint = gameGrid.getPlacedTiles().get(indexLastTile).getPoint();

        int lastPointX = lastPoint.getX();
        int lastPointY = lastPoint.getY();

        int currX = currentPosition.getX();
        int currY = currentPosition.getY();

        if (currX == lastPointX && (currY == lastPointY + 1 || currY == lastPointY - 1)) {
            return true;
        }
        return currY == lastPointY && (currX == lastPointX + 1 || currX == lastPointX - 1);
    }
    private void acceptCopy(final DragEvent e) {
        if (e.getDragboard().hasString()) {
            e.acceptTransferModes(TransferMode.COPY);
        }
        e.consume();
    }
    private void onEntityClicked(final MouseEvent e, final Entity entity) {
        Point firstTile = gameGrid.getPlacedTiles().get(0).getPoint();

        if (player.isSelling() && entity.getPoint() != firstTile && !player.isPlaying()) {
            if (entity instanceof Tile) {
                Tile lastTile = gameGrid.getPlacedTiles().get(gameGrid.getPlacedTiles().size() - 1);
                if (entity.getPoint().equals(lastTile.getPoint())) {
                    gameGrid.removeEntity(entity);
                    super.getMainPane().getChildren().remove(entity.getImgView());
                    GameLogic.sellEntity(entity, player);
                } else {
                    Label message = GameLogic.createErrorPopup("start selling from the last tile");
                    super.addToMainPane(message);
                }
            } else {
                gameGrid.removeEntity(entity);
                super.getMainPane().getChildren().remove(entity.getImgView());

                GameLogic.sellEntity(entity, player);
            }
        } else if (entity.getPoint() == firstTile) {
            Label message = GameLogic.createErrorPopup("cannot sell the first tile");
            super.addToMainPane(message);
        } else if (player.isPlaying()) {
            Label message = GameLogic.createErrorPopup("cannot sell while playing");
            super.addToMainPane(message);
        } else {
            Label message = GameLogic.createErrorPopup("click the hammer button to start selling");
            super.addToMainPane(message);
        }
    }
    private Point getPointFromMousePosition(final DragEvent e) {
        // current mouse position
        double mouseX = e.getSceneX();
        double mouseY = e.getSceneY();

        // round down to nearest 64 (grid size) to get the top left corner of the grid
        int x = (int) (mouseX - (mouseX % MOUSE_OFFSET));
        int y = (int) (mouseY - (mouseY % MOUSE_OFFSET));

        // get the row and column of the grid
        int pointX = x / MOUSE_OFFSET - 1;
        int pointY = y / MOUSE_OFFSET - Y_OFFSET;

        return new Point(pointX, pointY);
    }
    private void drawTiles() {
        for (Entity entity : gameGrid.getPlacedTiles()) {
            super.addToMainPane(entity.getImgView());
        }
    }
    private void drawTowers() {
        for (Entity entity : gameGrid.getPlacedTowers()) {
            super.addToMainPane(entity.getImgView());
        }
    }
}
