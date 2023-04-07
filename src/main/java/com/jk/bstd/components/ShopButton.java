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
    private final String itemName;

    private Image shopBtnImg;
    private Image itemImg;

    public ShopButton(String imgName) {
        this.imgName = imgName;

        this.itemName = imgName.substring(4);
        initializeButtonListeners(this);

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
            boolean tileOrSprinkler = itemName.equals("Tile") || itemName.equals("Sprinkler");
            double offsetX, offsetY;
            if (OSPlatform.isWindows()) {
                System.out.println("Windows");
                offsetX = 32;
                offsetY = 32;
                if (!tileOrSprinkler) {
                    offsetY *= 3;
                }
            } else {
                System.out.println("Mac");
                offsetX = 0;
                offsetY = 0;
                if (!tileOrSprinkler) {
                    offsetY += 32;
                }
            }

            if (tileOrSprinkler) {
                iv.setFitHeight(64);
            } else {
                iv.setFitHeight(128);
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
