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

        gridPane.setPrefSize(ROWS * GRID_SIZE, COLUMNS * GRID_SIZE);
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

    private void setFirstTile() {
        Tile firstTile = new Tile(new Point(0, 2));
        placedTiles.add(firstTile);
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
}
