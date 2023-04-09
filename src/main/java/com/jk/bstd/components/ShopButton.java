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

import java.util.Objects;

/**
 * Class that represents a button in the shop.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class ShopButton extends Button {
    private static final int BEGINNING_INDEX = 4;
    private static final int OFFSET_X = 32;
    private static final int OFFSET_Y = 32;
    private static final int HEIGHT = 64;
    private static final int Y_MODIFIER = 3;
    private static final String STYLE = "-fx-background-color: transparent;";
    private final String imgName;
    private final String itemName;
    private Image shopBtnImg;
    private Image itemImg;

    /**
     * Constructor for a new shop button.
     *
     * @param imgName the name of the image
     */
    public ShopButton(final String imgName) {
        this.imgName = imgName;

        this.itemName = imgName.substring(BEGINNING_INDEX);
        initializeButtonListeners(this);

        setImagePath();
        setGraphic(new ImageView(shopBtnImg));
        setStyle(STYLE);
        setPadding(new Insets(0));
    }

    private void setImagePath() {
        shopBtnImg = new Image(
                Objects.requireNonNull(getClass().getResource("/images/gameScreen/" + imgName + ".png")
                ).toExternalForm()
        );
        itemImg = new Image(
                Objects.requireNonNull(getClass().getResource("/images/gameScreen/" + itemName + ".png")
                ).toExternalForm()
        );
    }
    private void initializeButtonListeners(final Node node) {
        node.setOnDragDetected(event -> {
            Dragboard db = startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(itemName);
            db.setContent(content);

            ImageView iv = new ImageView(itemImg);
            boolean tileOrSprinkler = itemName.equals("Tile") || itemName.equals("Sprinkler");
            double offsetX = OFFSET_X;
            double offsetY = OFFSET_Y;
            if (OSPlatform.isWindows()) {
                if (!tileOrSprinkler) {
                    offsetY *= Y_MODIFIER;
                }
            } else {
                offsetX = 0;
                offsetY = 0;
                if (!tileOrSprinkler) {
                    offsetY += OFFSET_Y;
                }
            }

            if (tileOrSprinkler) {
                iv.setFitHeight(HEIGHT);
            } else {
                iv.setFitHeight(HEIGHT * 2);
            }
            iv.setFitWidth(HEIGHT);

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(javafx.scene.paint.Color.TRANSPARENT);

            db.setDragView(iv.snapshot(params, null), offsetX, offsetY);

            event.consume();
        });

        setOnMouseReleased(event -> setEffect(null));

        setOnMouseEntered(event -> setEffect(new DropShadow()));

        setOnMouseExited(event -> setEffect(null));
    }
    /**
     * Returns the image of the button.
     *
     * @return the image of the button
     */
    public Image getImage() {
        return itemImg;
    }
}
