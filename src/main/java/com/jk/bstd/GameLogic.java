package com.jk.bstd;

import com.jk.bstd.entities.*;
import com.jk.bstd.ui.GameGrid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public final class GameLogic {
    private static final int GRID_SIZE = 64;

    private GameLogic() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate GameLogic");
    }

    public static void play(Player player, GameGrid gameGrid, GridPane gridPane, AnchorPane pane) {
        int numEnemies;
        if (player.getLevel() == 1) {
            numEnemies = 15;
        } else if (player.getLevel() == 2) {
            numEnemies = 25;
        } else if (player.getLevel() == 3) {
            numEnemies = 35;
        } else {
            numEnemies = 50;
        }

        Path path = createPath(gameGrid.getPlacedTiles(), gridPane);
        spawnEnemies(path, numEnemies, pane, gameGrid.getPlacedTowers(), player);
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
        return path;
    }

    private static void spawnEnemies(Path path, int numEnemies, AnchorPane pane, ArrayList<Tower> towers, Player player) {
        double speed = 1.5 / player.getLevel();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(speed), event -> {
             // TODO: change this to a random animal
            Chicken chicken = new Chicken();
            spawnEnemy(pane, path, chicken, towers, player);
        }));

        timeline.setCycleCount(numEnemies);
        timeline.play();

        timeline.setOnFinished(event -> {
            player.setAlive(player.getHealth() > 0);
        });
    }

    private static void spawnEnemy(AnchorPane pane, Path path, Animal animal, ArrayList<Tower> towers, Player player) {
        PathTransition pt = new PathTransition(Duration.seconds(path.getElements().size()),path);
//        PathTransition pt = new PathTransition(Duration.seconds(5), path); // TODO: remove (for faster testing)

        ImageView imgView = animal.getImgView();
        pt.setNode(imgView);
        pt.setRate(1);
        pt.setInterpolator(Interpolator.LINEAR);

        imgView.setTranslateX(128);
        imgView.setTranslateY(224);
        pane.getChildren().add(imgView);


        pt.currentTimeProperty().addListener((observableValue, duration, t1) -> {
            Point2D enemyLocation = new Point2D(imgView.getTranslateX() + 32, imgView.getTranslateY() + 32);

            for (Tower tower : towers) {
                int towerX = tower.getPoint().getRealX() + 32;
                int towerY = tower.getPoint().getRealY() + 32;
                Point2D towerLocation = new Point2D(towerX, towerY);
                if (enemyLocation.distance(towerLocation) < tower.getRange()  * 64 &&  !tower.getHasTarget()) {
                    tower.setHasTarget(true);
                    tower.attackAnimation(enemyLocation, pane);
                    animal.takeDamage(tower.getAttack());
                    if (animal.getIsDead()) {
                        pt.stop();
                        pane.getChildren().remove(imgView);
                        player.addMoney(animal.getGoldDropped());
                    }
                }
            }
        });
        pt.statusProperty().addListener(new ChangeListener<Animation.Status>() {
            @Override
            public void changed(ObservableValue<? extends Animation.Status> observableValue, Animation.Status status, Animation.Status t1) {
                if (t1 == Animation.Status.STOPPED && !animal.getIsDead()) {
                    pane.getChildren().remove(imgView);
                    player.takeDamage(animal.getAttack());
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




    public static void sellEntity(Entity entity, Player player) {
        int cost = entity.getCost();
        player.addMoney(cost);
    }

    public static boolean buyEntity(Entity entity, Player player) {
        if (player.getMoney() - entity.getCost() > 0) {
            int cost = entity.getCost();
            player.removeMoney(cost);
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkLastTilePosition(ArrayList<Tile> tiles, int x, int y) {
        if (tiles.size() == 0) {
            return false;
        }
        Tile lastTile = tiles.get(tiles.size() - 1);
        return lastTile.getPoint().getX() == x && lastTile.getPoint().getY() == y;
    }

    public static boolean checkIfTilesAreConnected(ArrayList<Tile> tiles) {
        if (tiles.size() < 2) {
            return false;
        }
        for (int i = 0; i < tiles.size() - 1; i++) {
            Tile tile = tiles.get(i);
            Tile otherTile = tiles.get(i + 1);
            if (!isConnectedTo(tile, otherTile)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isConnectedTo(Tile tile, Tile otherTile) {
        int x = tile.getPoint().getX();
        int y = tile.getPoint().getY();
        int otherX = otherTile.getPoint().getX();
        int otherY = otherTile.getPoint().getY();
        return (x == otherX && (y == otherY + 1 || y == otherY - 1)) || (y == otherY && (x == otherX + 1 || x == otherX - 1));
    }
}
