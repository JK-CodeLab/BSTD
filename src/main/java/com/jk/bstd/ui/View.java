package com.jk.bstd.ui;

import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * A class to represent the view of the game.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class View {
    private static final int LOGO_HEIGHT = 68;
    private static final int LOGO_WIDTH = 252;
    private static final int CURSOR_DIM = 32;
    private static final int HEIGHT = 832;
    private static final int WIDTH = 1280;
    private final AnchorPane mainPane;
    private final Scene mainScene;
    private final Stage mainStage;
    /**
     * Constructor for View.
     */
    public View() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        mainStage.setTitle("Barnyard Shenanigans");
        mainStage.setResizable(false);

        Image logo = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/images/heart.png")
                ).toExternalForm(), LOGO_WIDTH, LOGO_HEIGHT, false, true
        );
        mainStage.getIcons().add(logo);

        Image cursor = new Image(
                Objects.requireNonNull(getClass().getResource("/images/cursor.png")
                ).toExternalForm(), CURSOR_DIM, CURSOR_DIM, false, true
        );
        mainScene.setCursor(new ImageCursor(cursor));
    }
    /**
     * Returns the main stage.
     *
     * @return the main stage
     */
    public Stage getMainStage() {
        return mainStage;
    }
    /**
     * Returns the main pane.
     *
     * @return the main pane
     */
    public AnchorPane getMainPane() {
        return mainPane;
    }
    /**
     * Adds a node to the main pane.
     *
     * @param node the node to be added to the main pane
     */
    public void addToMainPane(final Node node) {
        mainPane.getChildren().add(node);
    }
    /**
     * Adds nodes to the main pane.
     *
     * @param nodes the nodes to be added to the main pane
     */
    public void addToMainPane(final Node... nodes) {
        mainPane.getChildren().addAll(nodes);
    }
    /**
     * Creates a background.
     *
     * @param imgPath the path of the image
     */
    public void createBackground(final String imgPath) {
        Image bgImg = new Image(
                Objects.requireNonNull(getClass().getResource("/images/" + imgPath)).toExternalForm()
        );
        BackgroundImage bgImgObj = new BackgroundImage(
                bgImg,
                null,
                null,
                null,
                null
        );
        mainPane.setBackground(new Background(bgImgObj));
    }
}
