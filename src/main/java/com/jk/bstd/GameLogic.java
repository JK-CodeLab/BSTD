package com.jk.bstd;

import com.jk.bstd.components.Level;
import com.jk.bstd.entities.*;
import com.jk.bstd.ui.GameGrid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

//TODO: remove, just for test
import javafx.scene.shape.*;
import javafx.animation.*;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;

public final class GameLogic {
    private static final int GRID_SIZE = 64;

    private GameLogic() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate GameLogic");
    }
public static void play(Player player, GameGrid gameGrid, GridPane gridPane, AnchorPane pane) {
        int numEnemies;
        if (player.getLevel() == 1) {
            numEnemies = 10;
        } else {
            numEnemies = 20;
        }

//        create path
        Path path = createPath(gameGrid.getPlacedTiles(), gridPane);
        spawnEnemies(path, numEnemies, pane);


}
    public static void spawnEnemies(Path path, int numEnemies, AnchorPane pane) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
             {
                 // TODO: change this to a random animal
                Chicken chicken = new Chicken();
                spawnEnemy(pane, path, chicken);
            }
        }));
        timeline.setCycleCount(numEnemies);
        timeline.play();
    }

    public static void spawnEnemy(AnchorPane pane, Path path, Animal animal) {
//        Chicken chicken = new Chicken();



        PathTransition pt = new PathTransition(Duration.seconds(path.getElements().size()),path);
        pt.setRate(1);
        pt.setInterpolator(Interpolator.LINEAR);
        ImageView imageView = animal.getImgView();
        imageView.setTranslateX(128);
        imageView.setTranslateY(224);
        pane.getChildren().add(imageView);
        pt.setNode(imageView);
        pt.statusProperty().addListener(new ChangeListener<Animation.Status>() {
            @Override
            public void changed(ObservableValue<? extends Animation.Status> observableValue, Animation.Status status, Animation.Status t1) {
                if (t1 == Animation.Status.STOPPED) {
                    pane.getChildren().remove(imageView);
                }
            }
        });

        pt.play();
    }

    private static Point convertToGridPaneXandY(Point point, GridPane gridPane) {
        double offsetX = gridPane.getLayoutX(); // 64
        double offsetY = gridPane.getLayoutY(); // 256 = 64 * 4

        int pointX = point.getX(); // 1
        int pointY = point.getY(); // 0

        int newY = (int) (pointY * GRID_SIZE + offsetY + 32); // 0 * 64 + 256 + 32 = 288
        int newX = (int) (pointX * GRID_SIZE + offsetX + 32); // 64 + 2 * 64 = 192

        return new Point(newX, newY);
    }


    public static Path createPath(ArrayList<Tile> tiles, GridPane gridPane) {
        Path path = new Path();
        path.setStroke(Color.RED);
        path.setStrokeWidth(5);

        Point point = convertToGridPaneXandY(tiles.get(0).getPoint(), gridPane);
        MoveTo start = new MoveTo(point.getX(), point.getY() - 32);
        path.getElements().add(start);

        for (int i = 1; i < tiles.size(); i++) {
            point = convertToGridPaneXandY(tiles.get(i).getPoint(), gridPane);
            LineTo line = new LineTo(point.getX(), point.getY());
            path.getElements().add(line);

            if (i == tiles.size() - 1) {
                LineTo line1 = new LineTo(point.getX(), point.getY() - 64);
                path.getElements().add(line1);
            }
        }

        System.out.println(path.getElements());
        return path;
    }

    public static void sellEntity(Entity entity, Player player) {
        int cost = entity.getCost();
        player.addMoney(cost);
    }

    public static void buyEntity(AnchorPane pane, Entity entity, ImageView imgView, Player player) {
        pane.getChildren().add(imgView);
        int cost = entity.getCost();
        player.removeMoney(cost);
    }
}
