package com.jk.bstd.ui;

import com.jk.bstd.Player;
import com.jk.bstd.components.GameMenuButton;
import com.jk.bstd.components.ShopButton;
import com.jk.bstd.entities.*;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.jk.bstd.GameLogic;
import javafx.util.Duration;

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

    public GameView() {
        super();
        Tower tower = new Scarecrow(new Point(0, 0));
        player = new Player(); // TODO: get player from main menu, or create new player
        shopButtons = new ArrayList<>();
        playerStats = player.getStats();
        createBackground("gameScreen/gameBg.png");
        createShop();

        for (int i = 0; i < gameMenuButtons.size(); i++) {
            createMenuButtons(gameMenuButtons.get(i), i);
        }

        addGridPaneToMainPane();
        initializeGameGrid();
        addFirstTileToMainPane();
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
        /// TODO: Implement this method
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
                saveGame(player, gameGrid.getPlacedTowers(), gameGrid.getPlacedTiles());
            });
            case "playBtn" -> menuBtn.setOnMouseClicked(event -> {

                boolean tilesConnected = GameLogic.checkIfTilesAreConnected(gameGrid.getPlacedTiles());
                boolean lastTileCorrect = GameLogic.checkLastTilePosition(gameGrid.getPlacedTiles(), 12, 0);

                if (tilesConnected && lastTileCorrect) {
                    if (player.getHealth() >= 0) {
                        player.setLevel(player.getLevel() + 1);
                    } else {
                        player.resetLevel();
                    }
                    GameLogic.play(player, gameGrid, gridPane, super.getMainPane());
                } else {
                    System.out.println("Tiles are no good");
                    // TODO: Display popup message
                }
            });
            case "sellBtn" -> menuBtn.setOnMouseClicked(event -> {
                player.setSelling(menuBtn.isSelling());
            });
        }
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
                            System.out.println("Not enough money");
                            // TODO: Display popup message
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
}
