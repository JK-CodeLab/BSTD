package com.jk.bstd.ui;

import com.jk.bstd.entities.Entity;
import com.jk.bstd.entities.Point;
import com.jk.bstd.entities.Tile;
import com.jk.bstd.entities.Tower;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

/**
 * A class to represent the game grid.
 *
 * @author Joseph Chun, Kira Yoon
 * @version 1.0
 */
public class GameGrid {
    private static final int ROWS = 7;
    private static final int COLUMNS = 14;
    private static final int GRID_SIZE = 64;
    private static final int GRID_SIZE_MULTIPLIER = 4;
    private final GridPane gridPane;
    private ArrayList<Tile> placedTiles = new ArrayList<>();
    private ArrayList<Tower> placedTowers = new ArrayList<>();
    /**
     * Constructor for GameGrid.
     */
    public GameGrid() {
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        gridPane.setPrefSize(COLUMNS * GRID_SIZE, ROWS * GRID_SIZE);
        gridPane.setLayoutY(GRID_SIZE * GRID_SIZE_MULTIPLIER);
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
        Tile firstTile = new Tile(new Point(1, 0));
        placedTiles.add(firstTile);
    }

    /**
     * Returns an ArrayList of placed towers.
     *
     * @return an ArrayList of placed towers
     */
    public ArrayList<Tower> getPlacedTowers() {
        return placedTowers;
    }
    /**
     * Sets the placed towers.
     *
     * @param placedTowers the placed towers
     */
    public void setPlacedTowers(final ArrayList<Tower> placedTowers) {
        this.placedTowers = placedTowers;
    }
    /**
     * Returns an ArrayList of placed tiles.
     *
     * @return an ArrayList of placed tiles
     */
    public ArrayList<Tile> getPlacedTiles() {
        return placedTiles;
    }
    /**
     * Sets the ArrayList of placed tiles.
     *
     * @param placedTiles the ArrayList of placed tiles
     */
    public void setPlacedTiles(final ArrayList<Tile> placedTiles) {
        this.placedTiles = placedTiles;
    }
    /**
     * Returns the grid pane.
     *
     * @return the grid pane
     */
    public GridPane getGridPane() {
        return gridPane;
    }
    /**
     *  Adds entities into their respective ArrayList.
     *
     * @param entity the entity to be added
     */
    public void addEntity(final Entity entity) {
        if (entity instanceof Tile) {
            placedTiles.add((Tile) entity);
        } else if (entity instanceof Tower) {
            placedTowers.add((Tower) entity);
        }
    }
    /**
     * Removes entities from their respective ArrayList.
     *
     * @param entity the entity to be removed
     */
    public void removeEntity(final Entity entity) {
        if (entity instanceof Tile) {
            placedTiles.remove(entity);
        } else if (entity instanceof Tower) {
            placedTowers.remove(entity);
        }
    }
}
