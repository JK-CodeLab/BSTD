package com.jk.bstd.components;

import javafx.geometry.Insets;
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
    private Image shopBtnImg;
    private Image itemImg;

    public ShopButton(String imgName) {
        this.imgName = imgName;
        this.itemName = imgName.substring(4);
        setImagePath();
        setGraphic(new ImageView(shopBtnImg));
        setStyle(STYLE);
        setPadding(new Insets(0));
        initializeButtonListeners(this);
    }

    private void setImagePath() {
        shopBtnImg = new Image(getClass().getResource("/images/gameScreen/" + imgName + ".png").toExternalForm());
        itemImg = new Image(getClass().getResource("/images/gameScreen/" + "Path" + ".png").toExternalForm());
    }

    private void initializeButtonListeners(Node node) {
        node.setOnDragDetected(event -> {
            Dragboard db = startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemName);
            db.setContent(content);


            ImageView iv = new ImageView(itemImg);
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
        return itemImg;
    }
}
