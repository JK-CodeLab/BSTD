package com.jk.bstd.ui;

import com.jk.bstd.Player;
import com.jk.bstd.components.GameMenuButton;
import com.jk.bstd.components.ShopButton;
import com.jk.bstd.entities.*;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class GameView extends View {

    private Player player;
    private Stage gameStage;
    private GameGrid gameGrid = new GameGrid();
    private GridPane gridPane = gameGrid.getGridPane();
    private List<ShopButton> shopButtons;
    private final List<String> gameMenuButtons = Arrays.asList("menuBtn", "playBtn", "sellBtn");
    private final int SHOP_BTN_START_X = 1008;
    private final int SHOP_BTN_START_Y = 66;

    public GameView() {
        super();
        player = new Player(); // TODO: get player from main menu, or create new player
        shopButtons = new ArrayList<>();
        createBackground("gameScreen/gameBg.png");
        createShop();

        for (int i = 0; i < gameMenuButtons.size(); i++) {
            createMenuButtons(gameMenuButtons.get(i), i);
        }

        addGridPaneToMainPane();
        initializeGameGrid();
        addFirstTileToMainPane();
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
            case "menuBtn" -> menuBtn.setOnMouseClicked(event -> exitGame()); // TODO: implement this method
//            case "playBtn" -> menuBtn.setOnMouseClicked(event -> startRound()); // TODO: implement this method
            case "sellBtn" -> menuBtn.setOnMouseClicked(event -> menuBtn.sell(player));
        }
    }


    private void initializeGameGrid() {
        gridPane.setOnDragOver(this::acceptCopy);

        gridPane.setOnDragDropped(event -> {
            System.out.println("drag dropped");
            if (event.getGestureSource() instanceof ShopButton) {
                String item = event.getDragboard().getString();
                Point mousePoint = getPointFromMousePosition(event);

                boolean isEmpty = isGridEmpty(mousePoint);

                Entity placedEntity = null;
                if (isEmpty) {
                    System.out.println("is empty");
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
                    }
                    if (placedEntity != null) {
                        gameGrid.addEntity(placedEntity);
                        super.addToMainPane(placedEntity.getImgView());
                    }

//                    int cost = placedEntity.getCost();
//
//                    entityImage.setOnMouseClicked(event1 -> {
//                        if (event1.getButton() == MouseButton.PRIMARY && player.isSelling()) {
//                            gridPane.getChildren().remove(entityImage);
//                            player.addMoney(cost);
//                            super.getMainPane().getChildren().remove(entityImage);
//                            System.out.println("money: " + player.getMoney()); // TODO: Remove this
//                        }
//                    });

                }
            }
            event.setDropCompleted(true);
            event.consume();

            // TODO: Remove this (debugging)
            printTileLocations();
        });
    }

    private void printTileLocations() {
        System.out.println("Tile locations: ");
        for (Entity entity : gameGrid.getPlacedTiles()) {
            System.out.println("\trow: " + entity.getPoint().getX() + " col: " + entity.getPoint().getY());
        }
    }

    private void addFirstTileToMainPane() {
        ImageView firstTile = gameGrid.getPlacedTiles().get(0).getImgView();
        super.addToMainPane(firstTile);
    }

    private boolean isGridEmpty(Point point) {
        int row = point.getX();
        int col = point.getY();
        for (Entity entity : gameGrid.getPlacedTiles()) {
            if (entity.getX() == row && entity.getY() == col) {
                return false;
            }
        }
        for (Entity entity : gameGrid.getPlacedTowers()) {
            if (entity.getX() == row && entity.getY() == col) {
                return false;
            }
        }
        return true;
    }

    private boolean isAdjacent(Point currentPosition) {
        int indexLastTile = gameGrid.getPlacedTiles().size() - 1;
        Point lastPoint = gameGrid.getPlacedTiles().get(indexLastTile).getPoint();

        int lastRow = lastPoint.getX();
        int lastCol = lastPoint.getY();

        int currRow = currentPosition.getX();
        int currCol = currentPosition.getY();

        if (currRow == lastRow && (currCol == lastCol + 1 || currCol == lastCol - 1)) {
            return true;
        }
        return currCol == lastCol && (currRow == lastRow + 1 || currRow == lastRow - 1);
    }

    private void acceptCopy(DragEvent e) {
        if (e.getDragboard().hasString()) {
            e.acceptTransferModes(TransferMode.COPY);
        }
        e.consume();
        System.out.println("drag over");
    }

    private Point getPointFromMousePosition(DragEvent e) {
        // current mouse position
        double mouseX = e.getSceneX();
        double mouseY = e.getSceneY();

        // round down to nearest 64 (grid size) to get the top left corner of the grid
        int x = (int) (mouseX - (mouseX % 64));
        int y = (int) (mouseY - (mouseY % 64));

        // get the row and column of the grid
        int row = y/64 - 4;
        int col = x/64 - 1;

        return new Point(row, col);
    }
}
