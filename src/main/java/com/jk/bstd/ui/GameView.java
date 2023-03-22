package com.jk.bstd.ui;

import com.jk.bstd.components.ShopButton;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameView extends View {

    private Stage gameStage;
    private GridPane gameGrid;
    private List<ShopButton> shopButtons;
    private Image cursorImg;
    private final int SHOP_BTN_START_X = 1008;
    private final int SHOP_BTN_START_Y = 66;

    public GameView() {
        super();
        shopButtons = new ArrayList<>();
        cursorImg = new Image(Objects.requireNonNull(getClass().getResource("/images/cursor.png")).toExternalForm());
        createBackground("gameScreen/gameBg.png");
        createShop();
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
        ShopButton shopPath = new ShopButton("shopPath");
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

    private void initializeGameGrid() {
        gameGrid.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof ShopButton) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        gameGrid.setOnDragDropped(event -> {
            if (event.getGestureSource() instanceof ShopButton) {
                String item = event.getDragboard().getString();
                ShopButton newItem = new ShopButton(item);

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
                boolean isEmpty = true;
                for (Node node : gameGrid.getChildren()) {
                    Integer rowIndex = GridPane.getRowIndex(node);
                    Integer colIndex = GridPane.getColumnIndex(node);
                    if (rowIndex != null && colIndex != null) {
                        if (rowIndex == row && colIndex == col) {
                            isEmpty = false;
                        }
                    }
                }

                // if the grid is empty, add the item to the grid
                if (isEmpty) {
                    gameGrid.add(newItem, col, row);
                }
            }
            event.setDropCompleted(true);
            event.consume();

            // TODO: Remove this (debugging)
            printGrid();
        });
    }

    // TODO: Remove this (debugging)
    private void printGrid() {
        for (Node node : gameGrid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            System.out.println("Row: " + rowIndex + " Col: " + colIndex);
        }
    }
}
