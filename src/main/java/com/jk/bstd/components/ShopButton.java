package com.jk.bstd.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
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
    private String itemName = null;
    private boolean isItem;
    private Image shopBtnImg;
    private Image itemImg;

    public ShopButton(String imgName, boolean isItem) {
        this.imgName = imgName;
        this.isItem = isItem;
        if (isItem) {
            this.itemName = imgName;
        } else {
            this.itemName = imgName.substring(4);
            initializeButtonListeners(this);
        }
        setImagePath();
        setGraphic(new ImageView(shopBtnImg));
        setStyle(STYLE);
        setPadding(new Insets(0));
    }

    private void setImagePath() {
        shopBtnImg = new Image(getClass().getResource("/images/gameScreen/" + imgName + ".png").toExternalForm());
        itemImg = new Image(getClass().getResource("/images/gameScreen/" + itemName + ".png").toExternalForm());
    }

    private void initializeButtonListeners(Node node) {
        node.setOnDragDetected(event -> {
            Dragboard db = startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemName);
            db.setContent(content);


            ImageView iv = new ImageView(itemImg);
            double offsetX = 32;
            double offsetY = 32;

            if (itemName.equals("Path") || itemName.equals("Sprinkler")) {
                iv.setFitHeight(64);
            } else {
                iv.setFitHeight(128);
                offsetY *= 3;
            }
            iv.setFitWidth(64);

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(javafx.scene.paint.Color.TRANSPARENT);

            db.setDragView(iv.snapshot(params, null), offsetX, offsetY);

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
        return itemImg;
    }
}
