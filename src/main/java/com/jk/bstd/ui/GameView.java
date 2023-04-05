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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class GameView extends View {

    private Player player;
    private Stage gameStage;
    private GridPane gameGrid;
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

        createGameGrid();
        initializeGameGrid();
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

    private void createGameGrid() {
        gameGrid = new GameGrid().getGameGrid();
        super.getMainPane().getChildren().add(gameGrid);
    }

    public void createShop() {
        Image shopImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameScreen/shopBg.png")).toExternalForm());
        ImageView shopView = new ImageView(shopImg);
        shopView.setX(960);
        shopView.setY(0);
        super.getMainPane().getChildren().add(shopView);

        createShopButtons();
    }

    public void addShopButtons(ShopButton shopBtn) {
        shopBtn.setLayoutX(SHOP_BTN_START_X);
        shopBtn.setLayoutY(SHOP_BTN_START_Y + shopButtons.size() * 128);
        shopButtons.add(shopBtn);
        super.getMainPane().getChildren().add(shopBtn);
    }

    public void createShopButtons() {
        ShopButton shopPath = new ShopButton("shopPath", false);
        ShopButton shopSprinkler = new ShopButton("shopSprinkler", false);
        ShopButton shopScarecrow = new ShopButton("shopScarecrow", false);
        ShopButton shopDog = new ShopButton("shopDog", false);
        ShopButton shopFarmer = new ShopButton("shopFarmer", false);

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
        super.getMainPane().getChildren().add(menuBtn);

        switch (btnName) {
            case "menuBtn" -> menuBtn.setOnMouseClicked(event -> exitGame()); // TODO: implement this method
//            case "playBtn" -> menuBtn.setOnMouseClicked(event -> startRound());
            case "sellBtn" -> menuBtn.setOnMouseClicked(event -> menuBtn.sell(player));
        }
    }

    private void acceptCopy(DragEvent e) {
        if (e.getDragboard().hasString()) {
            e.acceptTransferModes(TransferMode.COPY);
        }
        e.consume();
    }

    private void initializeGameGrid() {
//        gameGrid.setOnDragOver(event -> {
//            if (event.getGestureSource() instanceof ShopButton) {
//                event.acceptTransferModes(TransferMode.COPY);
//            }
//            event.consume();
//        });
        gameGrid.setOnDragOver(this::acceptCopy);

        gameGrid.setOnDragDropped(event -> {
            if (event.getGestureSource() instanceof ShopButton) {
                String item = event.getDragboard().getString();

                // current mouse position
                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();

                // round down to nearest 64 (grid size) to get the top left corner of the grid
                int x = (int) (mouseX - (mouseX % 64));
                int y = (int) (mouseY - (mouseY % 64));

                // get the row and column of the grid
                int row = y/64 - 3;
                int col = x/64;

                // check if the grid is empty
                boolean isEmpty = isGridEmpty(row, col);

                if (isEmpty && item.equals("Path")) {
                    Point lastItem = getLastItem();
                    boolean isAdjacent = isAdjacent(lastItem, row, col);
                    if (isAdjacent) {
                        ShopButton newPath = new ShopButton(item, true);
                        gameGrid.add(newPath, col, row);
                    }
                } else if (isEmpty) {
                    Tower newTower = null;
                    Point point = new Point(row, col);
                    switch (item) {
                        case "Sprinkler" -> {
                            newTower = new Sprinkler(point);
                        }
                        case "Scarecrow" -> {
                            newTower = new Scarecrow(point);
                        }
                        case "Farmer" -> {
                            newTower = new Farmer(point);
                        }
                    }
                    if (newTower != null) {
                        ImageView towerImg = newTower.getImgView();
                        int cost = newTower.getCost();

                        towerImg.setOnMouseClicked(event1 -> {
                            if (event1.getButton() == MouseButton.PRIMARY && player.isSelling()) {
                                gameGrid.getChildren().remove(towerImg);
                                player.addMoney(cost);
                                super.getMainPane().getChildren().remove(towerImg);
                                System.out.println("money: " + player.getMoney()); // TODO: Remove this
                            }
                        });

                        super.getMainPane().getChildren().add(towerImg);
                    }

                }
            }
            event.setDropCompleted(true);
            event.consume();

            // TODO: Remove this (debugging)
            printGrid();
        });

//        gameGrid.setOnDragDropped(event -> {
//            if (event.getGestureSource() instanceof ShopButton) {
//                String item = event.getDragboard().getString();
//                ShopButton newItem = new ShopButton(item, true);
//
//                // current mouse position
//                double mouseX = event.getSceneX();
//                double mouseY = event.getSceneY();
//
//                // round down to nearest 64 (grid size) to get the top left corner of the grid
//                int x = (int) (mouseX - (mouseX % 64));
//                int y = (int) (mouseY - (mouseY % 64));
//
//                // get the row and column of the grid
//                int row = y/64 - 3;
//                int col = x/64;
//
//                // check if the grid is empty
//                boolean isEmpty = isGridEmpty(row, col);
//
//                if (isEmpty && item.equals("Path")) {
//                    Point lastItem = getLastItem();
//                    boolean isAdjacent = isAdjacent(lastItem, row, col);
//                    if (isAdjacent) {
//                        gameGrid.add(newItem, col, row);
//                    }
//                } else if (isEmpty) {
//                    if (item.equals("Sprinkler")) {
//                        newItem.setLayoutX(x);
//                        newItem.setLayoutY(y);
//                    } else {
//                        newItem.setLayoutX(x);
//                        newItem.setLayoutY(y - 64);
//                    }
//                    super.getMainPane().getChildren().add(newItem);
//                }
//            }
//            event.setDropCompleted(true);
//            event.consume();
//
//            // TODO: Remove this (debugging)
//            printGrid();
//        });
    }



    private boolean isGridEmpty(int row, int col) {
        for (Node node : gameGrid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            if (rowIndex != null && colIndex != null) {
                if (rowIndex == row && colIndex == col) {
                    return false;
                }
            }
        }
        return true;
    }

    private Point getLastItem() {
        int size = gameGrid.getChildren().size();
        Node lastItem = gameGrid.getChildren().get(size - 1);
        Integer rowIndex = GridPane.getRowIndex(lastItem);
        Integer colIndex = GridPane.getColumnIndex(lastItem);
        return new Point(rowIndex, colIndex);
    }

    private boolean isAdjacent(Point lastItem, int row, int col) {
        if (lastItem.getX() == row && lastItem.getY() == col - 1) {
            return true;
        } else if (lastItem.getX() == row && lastItem.getY() == col + 1) {
            return true;
        } else if (lastItem.getX() == row - 1 && lastItem.getY() == col) {
            return true;
        } else if (lastItem.getX() == row + 1 && lastItem.getY() == col) {
            return true;
        }
        return false;
    }


    // TODO: Remove this (debugging)
    private void printGrid() {
        for (Node node : gameGrid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            System.out.println("Row: " + rowIndex + " Col: " + colIndex);
        }
        System.out.println("-----");
    }
}
