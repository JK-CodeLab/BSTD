package com.jk.bstd.ui;

import com.jk.bstd.entities.Entity;
import com.jk.bstd.entities.Point;
import com.jk.bstd.entities.Tile;
import com.jk.bstd.entities.Tower;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameGrid {
    private final GridPane gridPane;
    private static final int ROWS = 7;
    private static final int COLUMNS = 14;
    private static final int GRID_SIZE = 64;
    private ArrayList<Tile> placedTiles = new ArrayList<>();
    private ArrayList<Tower> placedTowers = new ArrayList<>();


    public GameGrid() {
        gridPane = new GridPane();

        // TODO: Remove this line (for testing purposes)
        gridPane.setGridLinesVisible(true);

        gridPane.setPrefSize(COLUMNS * GRID_SIZE, ROWS * GRID_SIZE);
        gridPane.setLayoutY(GRID_SIZE * 4);
        gridPane.setLayoutX(GRID_SIZE);

        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < ROWS; i++) {
            RowConstraints row = new RowConstraints(GRID_SIZE);
            gridPane.getRowConstraints().add(row);
        }

        for (int i = 0; i < COLUMNS; i++) {
            ColumnConstraints column = new ColumnConstraints(GRID_SIZE);
            gridPane.getColumnConstraints().add(column);
        }
        setFirstTile();
    }

    public int getNumTiles() {
        return placedTiles.size();
    }

    private void setFirstTile() {
        Tile firstTile = new Tile(new Point(1, 0));
        placedTiles.add(firstTile);

        //TODO: remove, just for testing
        placedTiles.add(new Tile(new Point(1, 1)));
        placedTiles.add(new Tile(new Point(1, 2)));
        placedTiles.add(new Tile(new Point(2, 2)));
        placedTiles.add(new Tile(new Point(3, 2)));
        placedTiles.add(new Tile(new Point(4, 2)));
        placedTiles.add(new Tile(new Point(4, 1)));
        placedTiles.add(new Tile(new Point(4, 0)));
        placedTiles.add(new Tile(new Point(5, 0)));
        placedTiles.add(new Tile(new Point(6, 0)));
        placedTiles.add(new Tile(new Point(7, 0)));
        placedTiles.add(new Tile(new Point(8, 0)));
        placedTiles.add(new Tile(new Point(8, 1)));
        placedTiles.add(new Tile(new Point(8, 2)));
        placedTiles.add(new Tile(new Point(8, 3)));
        placedTiles.add(new Tile(new Point(8, 4)));
        placedTiles.add(new Tile(new Point(9, 4)));
        placedTiles.add(new Tile(new Point(10, 4)));
        placedTiles.add(new Tile(new Point(11, 4)));
        placedTiles.add(new Tile(new Point(12, 4)));
        placedTiles.add(new Tile(new Point(12, 3)));
        placedTiles.add(new Tile(new Point(12, 2)));
        placedTiles.add(new Tile(new Point(12, 1)));
        placedTiles.add(new Tile(new Point(12, 0)));
    }
    public ArrayList<Tower> getPlacedTowers() {
        return placedTowers;
    }

    public ArrayList<Tile> getPlacedTiles() {
        return placedTiles;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Tile) {
            placedTiles.add((Tile) entity);
        } else if (entity instanceof Tower) {
            placedTowers.add((Tower) entity);
        }
    }

    public void removeEntity(Entity entity) {
        if (entity instanceof Tile) {
            placedTiles.remove(entity);
        } else if (entity instanceof Tower) {
            placedTowers.remove(entity);
        }
    }
}
