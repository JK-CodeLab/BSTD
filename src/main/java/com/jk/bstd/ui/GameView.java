package com.jk.bstd.ui;

import com.jk.bstd.components.ShopButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameView extends View {

    private Stage gameStage;
    private List<ShopButton> shopButtons;
    private Image cursorImg;
    private final int SHOP_BTN_START_X = 1000;
    private final int SHOP_BTN_START_Y = 64;

    public GameView() {
        super();
        shopButtons = new ArrayList<>();
        cursorImg = new Image(Objects.requireNonNull(getClass().getResource("/images/cursor.png")).toExternalForm());
        createBackground("gameScreen/gameBg.png");
        addShop();
        createGameGrid();
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

    public void addShop() {
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

    private void createGameGrid() {
        GridPane gameGrid = new GridPane();
        gameGrid.setPrefSize(960, 120);
        gameGrid.setLayoutX(0);
        gameGrid.setLayoutY(192);

        int rows = 8;
        int columns = 15;

        // TODO: delete this
        gameGrid.setGridLinesVisible(true);

        for (int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(64);
            gameGrid.getRowConstraints().add(row);
        }

        for (int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(64);
            gameGrid.getColumnConstraints().add(column);
        }

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
                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();
                int x = (int) (mouseX - (mouseX % 64) - 8);
                int y = (int) (mouseY - (mouseY % 64) - 4);
                newItem.setLayoutX(x);
                newItem.setLayoutY(y);
                super.getMainPane().getChildren().add(newItem);
            }

            event.setDropCompleted(true);
            event.consume();
        });

        super.getMainPane().getChildren().add(gameGrid);
    }
}


//public class GameView extends View {
//
//    private Stage gameStage;
//    private List<ShopButton> shopButtons;
//    private final int SHOP_BTN_START_X = 1000;
//    private final int SHOP_BTN_START_Y = 64;
//
//    public GameView() {
//        super();
//        shopButtons = new ArrayList<>();
//        createBackground("gameScreen/gameBg.png");
//        addShop();
//        createGameGrid();
//    }
//
//    public void createNewGame(Stage mainMenu) {
//        mainMenu.hide();
//        gameStage = super.getMainStage();
//        gameStage.show();
//    }
//
//    /// TODO: Implement this method
//    public void loadGame(Stage mainMenu) {
//        mainMenu.hide();
//        super.getMainStage().show();
//    }
//
//    public void exitGame() {
//        /// TODO: Implement this method
//        gameStage.close();
//    }
//
//    public void addShop() {
//        Image shopImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameScreen/shopBg.png")).toExternalForm());
//        ImageView shopView = new ImageView(shopImg);
//        shopView.setX(960);
//        shopView.setY(0);
//        super.getMainPane().getChildren().add(shopView);
//
//        createShopButtons();
//    }
//
//    public void addShopButtons(ShopButton shopBtn) {
//        shopBtn.setLayoutX(SHOP_BTN_START_X);
//        shopBtn.setLayoutY(SHOP_BTN_START_Y + shopButtons.size() * 128);
//        shopButtons.add(shopBtn);
//        super.getMainPane().getChildren().add(shopBtn);
//    }
//
//    public void createShopButtons() {
//        ShopButton shopPath = new ShopButton("Path");
//        ShopButton shopSprinkler = new ShopButton("Sprinkler");
//        ShopButton shopScarecrow = new ShopButton("Scarecrow");
//        ShopButton shopDog = new ShopButton("Dog");
//        ShopButton shopFarmer = new ShopButton("Farmer");
//
//        addShopButtons(shopPath);
//        addShopButtons(shopSprinkler);
//        addShopButtons(shopScarecrow);
//        addShopButtons(shopDog);
//        addShopButtons(shopFarmer);
//    }
//
//    private void createGameGrid() {
//        GridPane gameGrid = new GridPane();
//        gameGrid.setPrefSize(960, 120);
//        gameGrid.setLayoutX(0);
//        gameGrid.setLayoutY(192);
//
//        int rows = 8;
//        int columns = 15;
//
//        // TODO: delete this
//        gameGrid.setGridLinesVisible(true);
//
//        for (int i = 0; i < rows; i++) {
//            RowConstraints row = new RowConstraints(64);
//            gameGrid.getRowConstraints().add(row);
//        }
//
//        for (int i = 0; i < columns; i++) {
//            ColumnConstraints column = new ColumnConstraints(64);
//            gameGrid.getColumnConstraints().add(column);
//        }
//
//        gameGrid.setOnDragOver(event -> {
//            if (event.getGestureSource() instanceof ShopButton) {
//                event.acceptTransferModes(TransferMode.COPY);
//            }
//
//            event.consume();
//        });
//
//        gameGrid.setOnDragDropped(event -> {
//            if (event.getGestureSource() instanceof ShopButton) {
//                String item = event.getDragboard().getString();
//                ShopButton newItem = new ShopButton(item);
//                double x = event.getSceneX();
//                double y = event.getSceneY();
//                newItem.setLayoutX(x);
//                newItem.setLayoutY(y);
//                super.getMainPane().getChildren().add(newItem);
//            }
//
//            event.setDropCompleted(true);
//            event.consume();
////            ShopButton shopBtn = (ShopButton) event.getGestureSource();
////            Label label = new Label(shopBtn.getBtnName());
////            gameGrid.add(label, (int) event.getX() / 64, (int) event.getY() / 64);
////            event.consume();
//        });
//
//        super.getMainPane().getChildren().add(gameGrid);
//    }
//}
