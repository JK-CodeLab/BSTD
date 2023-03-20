package com.jk.bstd.components;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;



public class ShopButton extends Button {
    private static final String STYLE = "-fx-background-color: transparent;";
    private final String imgName;
    private final String itemName;
    private Image btnImgPath;
    private Image itemImgPath;
    private Image cursorImgPath;
    private int mouseX;
    private int mouseY;

    public ShopButton(String imgName) {
        this.imgName = imgName;
        this.itemName = imgName.substring(4);
        setImagePath();
        setGraphic(new ImageView(btnImgPath));
        setStyle(STYLE);
        initializeButtonListeners(this);
    }

    private void setImagePath() {
        btnImgPath = new Image(getClass().getResource("/images/gameScreen/" + imgName + ".png").toExternalForm());
        itemImgPath = new Image(getClass().getResource("/images/gameScreen/" + "Path" + ".png").toExternalForm());
        cursorImgPath = new Image(getClass().getResource("/images/" + "cursor" + ".png").toExternalForm());
    }

    private void initializeButtonListeners(Node node) {
        node.setOnDragDetected(event -> {
            Dragboard db = startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemName);
            db.setContent(content);


            ImageView iv = new ImageView(itemImgPath);
            iv.setFitWidth(64);
            iv.setFitHeight(64);
            db.setDragView(iv.snapshot(null, null));

            event.consume();
        });

        setOnMouseReleased(event -> {
            setEffect(null);
        });

        setOnMouseEntered(event -> {
            setEffect(new DropShadow());
        });

        setOnMouseExited(event -> {
            setEffect(null);
        });
    }

    public Image getImage() {
        return itemImgPath;
    }
}


//public class ShopButton extends Button {
//    private static final String STYLE = "-fx-background-color: transparent;";
//    private final String imgName;
//    private Image btnImgPath;
//    private Image itemImgPath;
//    private double mouseX;
//    private double mouseY;
//
//    public ShopButton(String imgName) {
//        this.imgName = imgName;
//        setImagePath();
//        setGraphic(new ImageView(btnImgPath));
//        setStyle(STYLE);
//        setPrefWidth(224);
//        setPrefHeight(116);
//        initializeButtonListeners(this);
//    }
//
//    private void setImagePath() {
//        btnImgPath = new Image(getClass().getResource("/images/gameScreen/shop" + imgName + ".png").toExternalForm());
//        // TODO: add imgName to the path
//        itemImgPath = new Image(getClass().getResource("/images/entities/Path.png").toExternalForm());
//    }
//
//    private void initializeButtonListeners(Node node) {
////        setOnMousePressed(event -> {
////            setEffect(new DropShadow());
////            System.out.println("Mouse pressed");
////            System.out.println("MouseX: " + event.getSceneX());
////            System.out.println("MouseY: " + event.getSceneY());
////
////            mouseX = event.getSceneX();
////            mouseY = event.getSceneY();
////        });
////
////        setOnMouseDragged(event -> {
////            System.out.println("MouseX: " + event.getSceneX());
////            System.out.println("MouseY: " + event.getSceneY());
////
////            setGraphic(new ImageView(itemImgPath));
////
////            node.setLayoutX(event.getSceneX() - 80);
////            node.setLayoutY(event.getSceneY() - 25);
////        });
//
//        setOnDragDetected(event -> {
//            Dragboard db = startDragAndDrop(TransferMode.COPY);
//            ClipboardContent content = new ClipboardContent();
//            content.putString("Dog");
//            db.setContent(content);
//            event.consume();
//        });
//
//        setOnMouseReleased(event -> {
//            setEffect(null);
//        });
//
//        setOnMouseEntered(event -> {
//            setEffect(new DropShadow());
//        });
//
//        setOnMouseExited(event -> {
//            setEffect(null);
//        });
//    }
//
//}
