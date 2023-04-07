package com.jk.bstd.ui;

import com.jk.bstd.Player;
import com.jk.bstd.components.GameMenuButton;
import com.jk.bstd.components.ShopButton;
import com.jk.bstd.entities.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
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
import static com.jk.bstd.LoadGame.*;
import static com.jk.bstd.SaveGame.*;


public class GameView extends View {

    private Player player;
    private Stage gameStage;
    private GameGrid gameGrid = new GameGrid();
    private GridPane gridPane = gameGrid.getGridPane();
    private List<ShopButton> shopButtons;
    private Label playerStats;
    private final List<String> gameMenuButtons = Arrays.asList("menuBtn", "playBtn", "sellBtn");
    private final int SHOP_BTN_START_X = 1008;
    private final int SHOP_BTN_START_Y = 66;

    public GameView(boolean loadGame) {
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
        playerStats = player.getStats();
        displayPlayerStats();
    }

    public void createNewGame(Stage mainMenu) {
        mainMenu.hide();
        gameStage = super.getMainStage();
        gameStage.show();
    }

    /// TODO: Implement this method
    public void loadGame(Stage mainMenu) {
        mainMenu.hide();
        super.getMainStage().show();
    }

    public void exitGame() {
        gameStage.close();
    }

    private void addGridPaneToMainPane() {
        super.addToMainPane(gridPane);
    }

    private void displayPlayerStats() {
        super.addToMainPane(playerStats);
    }

    public void createShop() {
        Image shopImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameScreen/shopBg.png")).toExternalForm());
        ImageView shopView = new ImageView(shopImg);
        shopView.setX(960);
        shopView.setY(0);
        super.addToMainPane(shopView);

        createShopButtons();
    }

    public void addShopButtons(ShopButton shopBtn) {
        shopBtn.setLayoutX(SHOP_BTN_START_X);
        shopBtn.setLayoutY(SHOP_BTN_START_Y + shopButtons.size() * 128);
        shopButtons.add(shopBtn);
        super.addToMainPane(shopBtn);
    }

    public void createShopButtons() {
        ShopButton shopPath = new ShopButton("shopTile");
        ShopButton shopSprinkler = new ShopButton("shopSprinkler");
        ShopButton shopScarecrow = new ShopButton("shopScarecrow");
        ShopButton shopDog = new ShopButton("shopDog");
        ShopButton shopFarmer = new ShopButton("shopFarmer");

        addShopButtons(shopPath);
        addShopButtons(shopSprinkler);
        addShopButtons(shopScarecrow);
        addShopButtons(shopDog);
        addShopButtons(shopFarmer);
    }

    private void createMenuButtons(String btnName, int offsetX) {
        GameMenuButton menuBtn = new GameMenuButton(btnName);
        menuBtn.setLayoutX(270 + offsetX * 120);
        menuBtn.setLayoutY(64);
        super.addToMainPane(menuBtn);

        switch (btnName) {
            case "menuBtn" -> menuBtn.setOnMouseClicked(event -> {
                System.out.println("Menu button clicked");
                createMenuPopup(player, gameGrid.getPlacedTowers(), gameGrid.getPlacedTiles());
            });
            case "playBtn" -> menuBtn.setOnMouseClicked(event -> {

                boolean tilesConnected = GameLogic.checkIfTilesAreConnected(gameGrid.getPlacedTiles());
                boolean lastTileCorrect = GameLogic.checkLastTilePosition(gameGrid.getPlacedTiles(), 12, 0);

                if (tilesConnected && lastTileCorrect && player.isAlive()) {
                    player.setLevel(player.getLevel() + 1);
                    GameLogic.play(player, gameGrid, gridPane, super.getMainPane());
                } else {
                    Label message = GameLogic.createErrorPopup("Tiles are no good");
                    super.addToMainPane(message);
                }
            });
            case "sellBtn" -> menuBtn.setOnMouseClicked(event -> {
                player.setSelling(menuBtn.isSelling());
            });
        }
    }

    private void createMenuPopup(Player player, ArrayList<Tower> towers, ArrayList<Tile> tiles) {
        Stage popupWindow = new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setAlwaysOnTop(true);
        popupWindow.setResizable(false);
        popupWindow.initStyle(StageStyle.TRANSPARENT);

        Button button = new Button("Close");
        Button saveBtn = new Button("save game");
        Button exitBtn = new Button("exit game");

        saveBtn.setTranslateX(50);
        saveBtn.setTranslateY(10);
        exitBtn.setTranslateX(50);
        exitBtn.setTranslateY(15);
        button.setTranslateX(160);
        button.setTranslateY(18);

        saveBtn.setOnAction(e -> {
            saveGame(player, towers, tiles);
            popupWindow.close();
        });
        exitBtn.setOnAction(e -> {
            exitGame();
            popupWindow.close();
        });
        button.setOnAction(e -> popupWindow.close());

        Image bgImg = new Image(Objects.requireNonNull(
                getClass().getResource("/images/gameScreen/ShopDog.png")).toExternalForm());
        BackgroundImage bg = new BackgroundImage(
                bgImg, null, null, null, null);

        VBox layout = new VBox(10);
        layout.setBackground(new Background(bg));
        layout.getChildren().addAll(saveBtn, exitBtn, button);

        Scene scene = new Scene(layout, 224, 116);
        scene.setFill(Color.TRANSPARENT);

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
                    switch (item) {
                        case "Tile" -> {;
                            boolean isAdjacent = isAdjacent(mousePoint);
                            if (isAdjacent) {
                                placedEntity = new Tile(mousePoint);
                            }
                        }
                        case "Sprinkler" -> {
                            placedEntity = new Sprinkler(mousePoint);
                        }
                        case "Scarecrow" -> {
                            placedEntity = new Scarecrow(mousePoint);
                        }
                        case "Farmer" -> {
                            placedEntity = new Farmer(mousePoint);
                        }
                        case "Dog" -> {
                            placedEntity = new Dog(mousePoint);
                        }
                    }
                    if (placedEntity != null) {
                        ImageView entityImgView = placedEntity.getImgView();

                        gameGrid.addEntity(placedEntity);

                        boolean bought = GameLogic.buyEntity(placedEntity, player);
                        if (bought) {
                            super.addToMainPane(entityImgView);
                        } else {
                            Label message = GameLogic.createErrorPopup("Not enough money");
                            super.addToMainPane(message);
                        }

                        Entity finalPlacedEntity = placedEntity;
                        entityImgView.setOnMouseClicked(e -> onEntityClicked(e, finalPlacedEntity));
                    }
                }
            }
            event.setDropCompleted(true);
            event.consume();

            // TODO: Remove this (debugging)
//            printTileLocations();
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

    private boolean isGridEmpty(Point point) {
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

    private boolean isAdjacent(Point currentPosition) {
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

    private void acceptCopy(DragEvent e) {
        if (e.getDragboard().hasString()) {
            e.acceptTransferModes(TransferMode.COPY);
        }
        e.consume();
    }

    private void onEntityClicked(MouseEvent e, Entity entity) {
        if (player.isSelling()) {
            System.out.println("entity sold: " + entity.getName());
            gameGrid.removeEntity(entity);
            super.getMainPane().getChildren().remove(entity.getImgView());

            GameLogic.sellEntity(entity, player);
        }
    }

    private Point getPointFromMousePosition(DragEvent e) {
        // current mouse position
        double mouseX = e.getSceneX();
        double mouseY = e.getSceneY();

        // round down to nearest 64 (grid size) to get the top left corner of the grid
        int x = (int) (mouseX - (mouseX % 64));
        int y = (int) (mouseY - (mouseY % 64));

        // get the row and column of the grid
        int pointX = x/64 - 1;
        int pointY = y/64 - 4;

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
